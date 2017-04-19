package com.alisiikh.service;

import com.alisiikh.model.TTSRequest;

import java.io.File;
import java.io.Serializable;

/**
 * @author alisiikh
 */
public interface ITTSService extends Serializable {

    File transformTextToSpeech(TTSRequest ttsRequest);
}
