package com.alisiikh.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * @author alisiikh
 */
@Singleton
@Startup
public class MbrolaInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(MbrolaInitializer.class);

    @PostConstruct
    protected void postConstruct() {
        LOG.info("Running MbrolaInitializer which sets up mbrola.base system variable");

        System.setProperty("mbrola.base", "/opt/mbrola");
    }
}
