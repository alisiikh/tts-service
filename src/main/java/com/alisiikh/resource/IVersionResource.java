package com.alisiikh.resource;

import javax.json.JsonObject;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author alisiikh
 */
public interface IVersionResource {

    WordResource getWordResource();

    AppVersion getVersion();

    @XmlRootElement
    class AppVersion {
        private String version;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
