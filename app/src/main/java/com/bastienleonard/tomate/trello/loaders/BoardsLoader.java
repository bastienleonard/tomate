package com.bastienleonard.tomate.trello.loaders;

import android.content.Context;

import com.bastienleonard.tomate.BasicLoader;
import com.bastienleonard.tomate.trello.TrelloCredentials;
import com.bastienleonard.tomate.trello.Webservices;
import com.bastienleonard.tomate.trello.models.Board;
import com.bastienleonard.tomate.utils.LogUtils;

import java.io.IOException;
import java.util.List;

public final class BoardsLoader extends BasicLoader<List<Board>> {
    private static final String TAG = "BoardsLoader";

    public BoardsLoader(Context context) {
        super(context);
    }

    @Override
    public List<Board> loadInBackground() {
        try {
            return Webservices.getUsersBoards(getContext(), TrelloCredentials.getAppKey(), TrelloCredentials.getPersistedToken(getContext()));
        } catch (IOException e) {
            LogUtils.e(TAG, e);
            return null;
        }
    }
}
