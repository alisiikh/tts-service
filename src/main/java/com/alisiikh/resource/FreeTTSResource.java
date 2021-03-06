package com.alisiikh.resource;

import com.alisiikh.model.TTSRequest;
import com.alisiikh.service.TTSService;
import com.alisiikh.util.TTSResourceUtils;
import org.apache.commons.io.IOUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;


/**
 * @author alisiikh
 */
@ApplicationScoped
public class FreeTTSResource implements TTSResource {

    @Inject
    @Named("freeTTSService")
    private TTSService freeTTSService;

    @Override
    public StreamingOutput convertTextToSpeech(TTSRequest ttsRequest,
                                               HttpServletResponse response) throws IOException {
        validateTTSRequest(ttsRequest);

        InputStream audioStream = freeTTSService.transformTextToSpeech(ttsRequest);
        byte[] audioBytes = IOUtils.toByteArray(audioStream);

        response.setHeader("Content-Length", String.valueOf(audioBytes.length));
        response.setHeader("Content-Disposition", "attachment; filename=\"text2speech.wav\"");

        return TTSResourceUtils.createAudioStreamingOutput(audioBytes);
    }

    @Override
    public Voices getAvailableVoices() {
        Voices voices = new Voices();
        voices.setVoices(freeTTSService.getAvailableVoices());
        return voices;
    }
}
