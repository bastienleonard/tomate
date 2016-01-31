package com.bastienleonard.tomate.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

public final class StreamUtil {
    private static final String TAG = "StreamUtil";

    private StreamUtil() {
    }

    public static String inputStreamToString(InputStream stream)
            throws IOException {
        if (stream == null) {
            return null;
        }

        char[] buffer = new char[4096];
        StringWriter writer = new StringWriter();
        InputStreamReader reader = new InputStreamReader(stream);

        try {
            while (true) {
                int bytesRead = reader.read(buffer);

                if (bytesRead == -1) {
                    break;
                }

                if (bytesRead > 0) {
                    writer.write(buffer, 0, bytesRead);
                }
            }
        } finally {
            try {
                writer.close();
                reader.close();
                stream.close();
            } catch (IOException e) {
                LogUtil.e(TAG, e);
            }
        }

        return writer.toString();
    }
}
