package com.alisiikh.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author alisiikh
 */
@XmlRootElement
@Data
public class TTSRequest {

    private String text;
    private String voice = "kevin16";

    public boolean isFilled() {
        return text != null && !text.isEmpty();
    }
}
