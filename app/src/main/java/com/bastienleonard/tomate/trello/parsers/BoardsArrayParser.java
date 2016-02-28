package com.bastienleonard.tomate.trello.parsers;

import com.bastienleonard.tomate.trello.models.Board;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public final class BoardsArrayParser extends JsonArrayParser<List<Board>> {
    @Override
    List<Board> parseJson(JSONArray boardsJson) throws JSONException, ParseException {
        List<Board> boards = new ArrayList<>();

        for (int i = 0; i < boardsJson.length(); ++i) {
            JSONObject boardJson = boardsJson.getJSONObject(i);
            boards.add(new Board(boardJson.getString("id"),
                    boardJson.getString("name")));
        }

        return boards;
    }
}
