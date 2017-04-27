package com.alisiikh.service;

import com.alisiikh.model.TTSRequest;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * @author alisiikh
 */
public interface TTSService extends Serializable {

    InputStream transformTextToSpeech(TTSRequest ttsRequest);

    List<String> getAvailableVoices();
}
