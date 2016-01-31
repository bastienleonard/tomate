package com.bastienleonard.tomate.trello;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class Http {
    private static final OkHttpClient CLIENT = new OkHttpClient();

    private Http() {
    }

    public static InputStream get(String url)
            throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = CLIENT.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        return response.body().byteStream();
    }
}
