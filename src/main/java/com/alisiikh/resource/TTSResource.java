package com.alisiikh.resource;

import com.alisiikh.model.TTSRequest;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.List;

/**
 * @author alisiikh
 */
public interface TTSResource {

    @POST
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    StreamingOutput convertTextToSpeech(TTSRequest ttsRequest,
                                        @Context HttpServletResponse response) throws IOException;

    @GET
    @Path("/voices")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    Voices getAvailableVoices();

    default void validateTTSRequest(TTSRequest ttsRequest) {
        if (ttsRequest == null || !ttsRequest.isFilled()) {
            throw new BadRequestException("TTSRequest is formed not correctly!");
        }

        if (ttsRequest.getText() != null && ttsRequest.getText().length() > 1024) {
            throw new BadRequestException("TTSRequest.text is too large, consider to shorten it to 1024 symbols");
        }
    }

    @XmlRootElement
    @Data
    final class Voices {
        private List<String> voices;
    }
}
