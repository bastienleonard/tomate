package com.bastienleonard.tomate.trello;

import android.content.Context;
import android.net.Uri;

import com.bastienleonard.tomate.trello.models.Board;
import com.bastienleonard.tomate.trello.models.TrelloList;
import com.bastienleonard.tomate.trello.parsers.BoardsParser;
import com.bastienleonard.tomate.trello.parsers.ListsParser;

import java.io.IOException;
import java.util.List;

public final class Webservices {
    private Webservices() {
    }

    public static List<Board> getUsersBoards(Context context, String appKey, String token)
            throws IOException {
        String url = new Uri.Builder()
                .scheme("https")
                .authority("api.trello.com")
                .path("1/members/me/boards")
                .appendQueryParameter("key", appKey)
                .appendQueryParameter("token", token)
                .build()
                .toString();
        return new BoardsParser().parse(Http.get(url));
    }

    public static List<TrelloList> getBoardsLists(Context context, String appKey, String token, String boardId)
            throws IOException {
        String url = new Uri.Builder()
                .scheme("https")
                .authority("api.trello.com")
                .path("1/boards/" + boardId + "/lists")
                .appendQueryParameter("key", appKey)
                .appendQueryParameter("token", token)
                .build()
                .toString();
        return new ListsParser().parse(Http.get(url));
    }
}
