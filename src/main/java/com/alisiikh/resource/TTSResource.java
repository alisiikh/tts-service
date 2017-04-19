package com.alisiikh.resource;

import com.alisiikh.model.TTSRequest;
import com.alisiikh.service.ITTSService;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author alisiikh
 */
@ApplicationScoped
public class TTSResource {

    @Inject
    private ITTSService ttsService;

    @POST
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public StreamingOutput convertTextToSpeech(TTSRequest TTSRequest,
                                               @Context HttpServletResponse response) {
        if (TTSRequest == null || !TTSRequest.isFilled()) {
            throw new BadRequestException("TTSRequest is formed not correctly!");
        }

        if (TTSRequest.getText() != null && TTSRequest.getText().length() > 1024) {
            throw new BadRequestException("TTSRequest.text is too large, consider to shorten it to 1024 symbols");
        }

        File outputFile = ttsService.transformTextToSpeech(TTSRequest);

        response.setHeader("Content-Length", String.valueOf(outputFile.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"text2speech.wav\"");

        return outputStream -> {
            byte[] speechBytes = FileUtils.readFileToByteArray(outputFile);
            IOUtils.write(speechBytes, new BufferedOutputStream(outputStream));
        };
    }


    @GET
    @Path("/voices")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Voices getAvailableVoices() {
        VoiceManager voiceManager = VoiceManager.getInstance();

        Voices voices = new Voices();
        voices.setVoices(Stream.of(voiceManager.getVoices())
                .map(Voice::getName)
                .collect(Collectors.toList()));

        return voices;
    }

    @XmlRootElement
    @Data
    private static final class Voices {
        private List<String> voices;
    }
}
