package com.alisiikh.resource;

import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


/**
 * @author alisiikh
 */
@ApplicationPath("/api")
public class TTSRestApp extends Application {

    public TTSRestApp() {
        BeanConfig swaggerConfig = new BeanConfig();
        swaggerConfig.setVersion("1.0");
        swaggerConfig.setSchemes(new String[] { "http" });
        swaggerConfig.setHost("localhost:8080");
        swaggerConfig.setBasePath("/api");
        swaggerConfig.setResourcePackage("com.alisiikh.resource");
        swaggerConfig.setScan();
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        // app resources
        resources.add(VersionResource.class);
        resources.add(TTSResource.class);

        // swagger resources and features
        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        return resources;
    }
}
