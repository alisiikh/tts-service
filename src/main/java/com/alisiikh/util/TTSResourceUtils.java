package com.alisiikh.util;

import org.apache.commons.io.IOUtils;

import javax.ws.rs.core.StreamingOutput;
import java.io.BufferedOutputStream;

/**
 * @author alisiikh
 */
public final class TTSResourceUtils {

    private TTSResourceUtils() {
    }

    public static StreamingOutput createAudioStreamingOutput(byte[] audioBytes) {
        return outputStream -> IOUtils.write(audioBytes, new BufferedOutputStream(outputStream));
    }
}
