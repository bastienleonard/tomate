package com.bastienleonard.tomate.trello;

import android.util.Pair;

import com.bastienleonard.tomate.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class Http {
    private static final String TAG = "Http";
    private static final OkHttpClient CLIENT = new OkHttpClient();

    private Http() {
    }

    public static InputStream get(String url)
            throws IOException {
        LogUtils.d(TAG, "GET request to " + url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = CLIENT.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        return response.body().byteStream();
    }

    public static InputStream put(String url, List<Pair<String, String>> args)
            throws IOException {
        LogUtils.d(TAG, "PUT request to " + url);
        FormBody.Builder bodyBuilder = new FormBody.Builder();

        for (Pair<String, String> pair: args) {
            bodyBuilder.add(pair.first, pair.second);
        }

        RequestBody formBody = bodyBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .put(formBody)
                .build();

        Response response = CLIENT.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        return response.body().byteStream();
    }
}
