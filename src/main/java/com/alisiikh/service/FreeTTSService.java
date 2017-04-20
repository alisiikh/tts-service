package com.alisiikh.service;

import com.alisiikh.model.TTSRequest;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.JavaStreamingAudioPlayer;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sound.sampled.AudioFileFormat;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author alisiikh
 */
@ApplicationScoped
@Named("freeTTSService")
public class FreeTTSService implements ITTSService {

    @Override
    public InputStream transformTextToSpeech(TTSRequest ttsRequest) {
        String audioFileName = System.getProperty("java.io.tmpdir")
                + "/output_" + UUID.randomUUID().getMostSignificantBits();

        SingleFileAudioPlayer player = new SingleFileAudioPlayer(audioFileName, AudioFileFormat.Type.WAVE);

        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice voice = voiceManager.getVoice(ttsRequest.getVoice());
        if (voice == null) {
            throw new BadRequestException("Failed to find voice with name: " + ttsRequest.getVoice());
        }

        voice.setAudioPlayer(player);

        try {
            voice.allocate();
            voice.speak(ttsRequest.getText());
        } finally {
            voice.deallocate();
            player.close();
        }

        InputStream audioStream;
        try {
            audioStream = new FileInputStream(new File(audioFileName + ".wav"));
        } catch (FileNotFoundException e) {
            throw new InternalServerErrorException("Failed to transform text into speech by FreeTTS");
        }

        return audioStream;
    }

    @Override
    public List<String> getAvailableVoices() {
        VoiceManager voiceManager = VoiceManager.getInstance();

        return Stream.of(voiceManager.getVoices())
                .map(Voice::getName)
                .collect(Collectors.toList());
    }
}
