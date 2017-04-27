package com.alisiikh.util;

import java.util.UUID;

/**
 * @author alisiikh
 */
public final class TTSUtils {

    private TTSUtils() {
    }

    public static String generateTempFileNameWithoutExt() {
        return System.getProperty("java.io.tmpdir")
                + "/output_" + UUID.randomUUID().getMostSignificantBits();
    }
}
