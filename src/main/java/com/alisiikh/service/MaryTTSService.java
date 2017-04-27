package com.alisiikh.service;

import com.alisiikh.model.TTSRequest;
import com.alisiikh.util.TTSUtils;
import marytts.LocalMaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import marytts.modules.synthesis.Voice;
import marytts.util.data.audio.MaryAudioUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sound.sampled.AudioInputStream;
import javax.ws.rs.InternalServerErrorException;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author alisiikh
 */
@ApplicationScoped
@Named("maryTTSService")
public class MaryTTSService implements TTSService {

    private static final Logger LOG = LoggerFactory.getLogger(MaryTTSService.class);

    @Override
    public InputStream transformTextToSpeech(TTSRequest ttsRequest) {
        LocalMaryInterface mary;
        try {
            mary = new LocalMaryInterface();
        } catch (MaryConfigurationException e) {
            LOG.error("Failed to configure Mary TTS service", e);
            throw new InternalServerErrorException("Failed to configure Mary TTS service");
        }

        AudioInputStream audioInputStream;
        try {
            audioInputStream = mary.generateAudio(ttsRequest.getText());
        } catch (SynthesisException e) {
            LOG.error("Failed to synthesize speech using Mary TTS service", e);
            throw new InternalServerErrorException("Failed to synthesize speech using Mary TTS service");
        }

        String audioFileName = TTSUtils.generateTempFileNameWithoutExt() + ".wav";
        double[] samples = MaryAudioUtils.getSamplesAsDoubleArray(audioInputStream);

        try {
            MaryAudioUtils.writeWavFile(samples, audioFileName, audioInputStream.getFormat());
        } catch (IOException e) {
            throw new InternalServerErrorException("Failed to write speech to .wav file");
        }

        try {
            return new FileInputStream(new File(audioFileName));
        } catch (FileNotFoundException e) {
            throw new InternalServerErrorException("Failed to transform text into speech by FreeTTS");
        }
    }

    @Override
    public List<String> getAvailableVoices() {
        return Voice.getAvailableVoices().parallelStream()
                .map(Voice::getName)
                .collect(Collectors.toList());
    }
}
