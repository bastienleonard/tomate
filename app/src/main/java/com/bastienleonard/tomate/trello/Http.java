package com.bastienleonard.tomate.trello;

import android.support.annotation.Nullable;
import android.util.Pair;

import com.bastienleonard.tomate.BuildConfig;
import com.bastienleonard.tomate.utils.LogUtils;
import com.bastienleonard.tomate.utils.StreamUtils;

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
        debugLog("GET", url, null);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = CLIENT.newCall(request).execute();

        if (!response.isSuccessful()) {
            onError(response);
        }

        return response.body().byteStream();
    }

    public static InputStream put(String url, List<Pair<String, String>> args)
            throws IOException {
        debugLog("PUT", url, args);
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
            onError(response);
        }

        return response.body().byteStream();
    }

    public static InputStream post(String url, List<Pair<String, String>> args)
            throws IOException {
        debugLog("POST", url, args);
        FormBody.Builder bodyBuilder = new FormBody.Builder();

        for (Pair<String, String> pair: args) {
            bodyBuilder.add(pair.first, pair.second);
        }

        RequestBody formBody = bodyBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = CLIENT.newCall(request).execute();

        if (!response.isSuccessful()) {
            onError(response);
        }

        return response.body().byteStream();
    }

    private static void debugLog(String method, String url, @Nullable List<Pair<String, String>> args) {
        String argsString;

        if (BuildConfig.DEBUG) {
            StringBuilder sb = new StringBuilder();
            sb.append("{ ");

            if (args != null) {
                for (Pair<String, String> pair : args) {
                    sb.append(pair.first + ": " + pair.second + ", ");
                }
            }

            sb.append(" }");
            argsString = sb.toString();
        } else {
            argsString = "??";
        }

        LogUtils.d(TAG, method + " request to " + url + ", args: " + argsString);
    }

    private static void onError(Response response)
            throws IOException {
        String body = StreamUtils.inputStreamToString(response.body().byteStream());
        throw new IOException("Unexpected code " + response + ", body=" + body);
    }
}
