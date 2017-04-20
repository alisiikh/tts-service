package com.alisiikh.resource;

import com.alisiikh.model.TTSRequest;
import com.alisiikh.service.ITTSService;
import org.apache.commons.io.IOUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;


/**
 * @author alisiikh
 */
@ApplicationScoped
public class FreeTTSResource implements ITTSResource {

    @Inject
    @Named("freeTTSService")
    private ITTSService freeTTSService;

    @Override
    public StreamingOutput convertTextToSpeech(TTSRequest ttsRequest,
                                               @Context HttpServletResponse response) throws IOException {
        validateTTSRequest(ttsRequest);

        InputStream audioStream = freeTTSService.transformTextToSpeech(ttsRequest);

        response.setHeader("Content-Length", String.valueOf(audioStream.available()));
        response.setHeader("Content-Disposition", "attachment; filename=\"text2speech.wav\"");

        return outputStream -> {
            byte[] audioBytes = IOUtils.readFully(audioStream, audioStream.available());
            IOUtils.write(audioBytes, new BufferedOutputStream(outputStream));
        };
    }

    @Override
    public Voices getAvailableVoices() {
        Voices voices = new Voices();
        voices.setVoices(freeTTSService.getAvailableVoices());
        return voices;
    }
}
