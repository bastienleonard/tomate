package com.bastienleonard.tomate.trello.loaders;

import android.content.Context;

import com.bastienleonard.tomate.BasicLoader;
import com.bastienleonard.tomate.trello.TrelloCredentials;
import com.bastienleonard.tomate.trello.Webservices;
import com.bastienleonard.tomate.utils.LogUtils;

import java.io.IOException;

public final class AddCommentLoader extends BasicLoader<Boolean> {
    private static final String TAG = "AddCommentLoader";
    private final String mCardId;
    private final String mText;

    public AddCommentLoader(Context context, String cardId, String text) {
        super(context);
        mCardId = cardId;
        mText = text;
    }

    @Override
    public Boolean loadInBackground() {
        try {
            Webservices.addComment(TrelloCredentials.getAppKey(),
                    TrelloCredentials.getPersistedToken(getContext()),
                    mCardId,
                    mText);
            return true;
        } catch (IOException e) {
            LogUtils.e(TAG, e);
            return false;
        }
    }
}
