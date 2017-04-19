package com.alisiikh.resource;

import com.alisiikh.model.Word;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author alisiikh
 */
@ApplicationScoped
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WordResource implements IWordResource {

    @GET
    public Response getWord() {
        return Response.ok(new Word("dummy")).build();
    }
}
