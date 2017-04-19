package com.alisiikh.resource;

import com.alisiikh.model.AppVersion;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * @author alisiikh
 */
@ApplicationScoped
@Path(VersionResource.API_VERSION)
public class VersionResource {

    public static final String API_VERSION = "v1";

    @Inject
    private TTSResource ttsResource;

    @Path("/tts")
    public TTSResource getTextToSpeech() {
        return ttsResource;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public AppVersion getVersion() {
        return new AppVersion(API_VERSION);
    }
}
