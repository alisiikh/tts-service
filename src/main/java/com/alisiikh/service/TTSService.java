package com.alisiikh.service;

import com.alisiikh.model.TTSRequest;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;

import javax.enterprise.context.ApplicationScoped;
import javax.sound.sampled.AudioFileFormat;
import javax.ws.rs.BadRequestException;
import java.io.*;
import java.util.UUID;

/**
 * @author alisiikh
 */
@ApplicationScoped
public class TTSService implements ITTSService {

    @Override
    public File transformTextToSpeech(TTSRequest ttsRequest) {
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

        return new File(audioFileName + ".wav");
    }
}
