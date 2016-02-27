package com.bastienleonard.tomate.trello.loaders;

import android.content.Context;

import com.bastienleonard.tomate.BasicLoader;
import com.bastienleonard.tomate.trello.TrelloCredentials;
import com.bastienleonard.tomate.trello.Webservices;
import com.bastienleonard.tomate.trello.models.TrelloList;
import com.bastienleonard.tomate.utils.LogUtils;

import java.io.IOException;
import java.util.List;

public final class ListsLoader extends BasicLoader<List<TrelloList>> {
    private static final String TAG = "ListsLoader";
    private final String mBoardId;

    public ListsLoader(Context context, String boardId) {
        super(context);
        mBoardId = boardId;
    }

    @Override
    public List<TrelloList> loadInBackground() {
        try {
            return Webservices.getBoardsLists(TrelloCredentials.getAppKey(),
                    TrelloCredentials.getPersistedToken(getContext()),
                    mBoardId);
        } catch (IOException e) {
            LogUtils.e(TAG, e);
            return null;
        }
    }
}
