package com.alisiikh.resource;

import com.alisiikh.model.TTSRequest;
import com.alisiikh.service.ITTSService;
import org.apache.commons.io.IOUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.StreamingOutput;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author alisiikh
 */
@ApplicationScoped
public class PollyTTSResource implements ITTSResource {

    @Inject
    @Named("pollyTTSService")
    private ITTSService pollyTTSService;

    @Override
    public StreamingOutput convertTextToSpeech(TTSRequest ttsRequest,
                                               @Context HttpServletResponse response) throws IOException {
        validateTTSRequest(ttsRequest);

        InputStream audioStream = pollyTTSService.transformTextToSpeech(ttsRequest);

        byte[] audioBytes = IOUtils.toByteArray(audioStream);
        response.setHeader("Content-Length", String.valueOf(audioBytes.length));
        response.setHeader("Content-Disposition", "attachment; filename=\"text2speech.mp3\"");


        return outputStream -> IOUtils.write(audioBytes, new BufferedOutputStream(outputStream));
    }

    @Override
    public Voices getAvailableVoices() {
        Voices voices = new Voices();
        voices.setVoices(pollyTTSService.getAvailableVoices());
        return voices;
    }
}
