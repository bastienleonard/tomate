package com.bastienleonard.tomate.trello.loaders;

import android.content.Context;

import com.bastienleonard.tomate.BasicLoader;
import com.bastienleonard.tomate.trello.TrelloCredentials;
import com.bastienleonard.tomate.trello.Webservices;

import java.io.IOException;

public final class MoveCardLoader extends BasicLoader<Boolean> {
    private final String mCardId;
    private final String mListId;

    public MoveCardLoader(Context context, String cardId, String listId) {
        super(context);
        mCardId = cardId;
        mListId = listId;
    }

    @Override
    public Boolean loadInBackground() {
        try {
            Webservices.moveCard(getContext(),
                    TrelloCredentials.getAppKey(),
                    TrelloCredentials.getPersistedToken(getContext()),
                    mCardId,
                    mListId);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e); // FIXME
        }
    }
}
