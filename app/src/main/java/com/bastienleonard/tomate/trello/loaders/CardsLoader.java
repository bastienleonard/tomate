package com.bastienleonard.tomate.trello.loaders;

import android.content.Context;

import com.bastienleonard.tomate.trello.TrelloCredentials;
import com.bastienleonard.tomate.trello.Webservices;
import com.bastienleonard.tomate.trello.models.Card;

import java.io.IOException;
import java.util.List;

public final class CardsLoader extends BasicLoader<List<Card>> {
    private final String mListId;

    public CardsLoader(Context context, String listId) {
        super(context);
        mListId = listId;
    }

    @Override
    public List<Card> loadInBackground() {
        try {
            return Webservices.getListsCards(getContext(),
                    TrelloCredentials.getAppKey(),
                    TrelloCredentials.getPersistedToken(getContext()),
                    mListId);
        } catch (IOException e) {
            throw new RuntimeException(e); // FIXME
        }
    }
}
