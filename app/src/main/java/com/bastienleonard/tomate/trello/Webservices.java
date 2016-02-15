package com.bastienleonard.tomate.trello;

import android.content.Context;
import android.net.Uri;
import android.util.Pair;

import com.bastienleonard.tomate.trello.models.Board;
import com.bastienleonard.tomate.trello.models.Card;
import com.bastienleonard.tomate.trello.models.TrelloList;
import com.bastienleonard.tomate.trello.parsers.BoardsParser;
import com.bastienleonard.tomate.trello.parsers.CardsParser;
import com.bastienleonard.tomate.trello.parsers.ListsParser;
import com.bastienleonard.tomate.utils.LogUtils;
import com.bastienleonard.tomate.utils.StreamUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: remove context parameters
public final class Webservices {
    private static final String TAG = "Webservices";

    private Webservices() {
    }

    public static List<Board> getUsersBoards(Context context, String appKey, String token)
            throws IOException {
        String url = getBaseUrlBuilder(appKey, token)
                .path("1/members/me/boards")
                .build()
                .toString();
        return new BoardsParser().parse(Http.get(url));
    }

    public static List<TrelloList> getBoardsLists(Context context, String appKey, String token, String boardId)
            throws IOException {
        String url = getBaseUrlBuilder(appKey, token)
                .path("1/boards/" + boardId + "/lists")
                .build()
                .toString();
        return new ListsParser().parse(Http.get(url));
    }

    public static List<Card> getListsCards(Context context, String appKey, String token, String listId)
            throws IOException {
        String url = getBaseUrlBuilder(appKey, token)
                .path("1/lists/" + listId + "/cards")
                .build()
                .toString();
        return new CardsParser().parse(Http.get(url));
    }

    public static void moveCard(Context context, String appKey, String token, String cardId, String listId)
            throws IOException {
        String url = getBaseUrlBuilder(appKey, token)
                .path("1/cards/" + cardId + "/idList")
                .build()
                .toString();
        List<Pair<String, String>> args = new ArrayList<>();
        args.add(Pair.create("value", listId));
        Http.put(url, args).close();
    }

    public static void addComment(Context context, String appKey, String token, String cardId, String text)
            throws IOException {
        String url = getBaseUrlBuilder(appKey, token)
                .path("1/cards/" + cardId + "/actions/comments")
                .build()
                .toString();
        List<Pair<String, String>> args = new ArrayList<>();
        args.add(Pair.create("text", text));
        Http.post(url, args).close();
    }

    private static Uri.Builder getBaseUrlBuilder(String appKey, String token) {
        return new Uri.Builder()
                .scheme("https")
                .authority("api.trello.com")
                .appendQueryParameter("key", appKey)
                .appendQueryParameter("token", token);
    }
}
