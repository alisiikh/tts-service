package com.alisiikh.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author alisiikh
 */
@XmlRootElement
@Data
@AllArgsConstructor
public class AppVersion {
    private String version;
}
