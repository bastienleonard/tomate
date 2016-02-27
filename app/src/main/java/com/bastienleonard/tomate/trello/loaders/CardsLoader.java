package com.bastienleonard.tomate.trello.loaders;

import android.content.Context;

import com.bastienleonard.tomate.BasicLoader;
import com.bastienleonard.tomate.trello.TrelloCredentials;
import com.bastienleonard.tomate.trello.Webservices;
import com.bastienleonard.tomate.trello.models.Card;
import com.bastienleonard.tomate.utils.LogUtils;

import java.io.IOException;
import java.util.List;

public final class CardsLoader extends BasicLoader<List<Card>> {
    private static final String TAG = "CardsLoader";
    private final String mListId;

    public CardsLoader(Context context, String listId) {
        super(context);
        mListId = listId;
    }

    @Override
    public List<Card> loadInBackground() {
        try {
            return Webservices.getListsCards(TrelloCredentials.getAppKey(),
                    TrelloCredentials.getPersistedToken(getContext()),
                    mListId);
        } catch (IOException e) {
            LogUtils.e(TAG, e);
            return null;
        }
    }
}
