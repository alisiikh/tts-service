package com.alisiikh.resource;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;


/**
 * @author alisiikh
 */
@ApplicationScoped
@Path("v1")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class VersionResource implements IVersionResource {

    @Inject
    private WordResource wordResource;

    @Context
    private UriInfo context;

    @GET
    @Path("word")
    @Override
    public WordResource getWordResource() {
        return wordResource;
    }

    @GET
    @Override
    public AppVersion getVersion() {
        AppVersion version = new AppVersion();
        version.setVersion("v1");
        return version;
    }
}
