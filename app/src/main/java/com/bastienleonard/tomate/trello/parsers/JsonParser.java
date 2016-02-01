package com.bastienleonard.tomate.trello.parsers;

import android.support.annotation.Nullable;

import com.bastienleonard.tomate.utils.LogUtils;
import com.bastienleonard.tomate.utils.StreamUtils;

import org.json.JSONException;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

public abstract class JsonParser<T, U> implements Parser<T> {
    private static final String TAG = "JsonParser";

    abstract T parseJson(U root) throws JSONException, ParseException;

    @Nullable
    @Override
    public T parse(InputStream inputStream) {
        T parsed = null;

        try {
            // TODO: make this type-safe
            U root = (U) new JSONTokener(StreamUtils.inputStreamToString(inputStream)).nextValue();
            parsed = parseJson(root);
        } catch (IOException|JSONException|ParseException e) {
            LogUtils.e(TAG, e);
        }

        return parsed;
    }
}
