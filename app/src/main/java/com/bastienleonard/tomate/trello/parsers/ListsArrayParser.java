package com.bastienleonard.tomate.trello.parsers;

import com.bastienleonard.tomate.trello.models.TrelloList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ListsArrayParser extends JsonArrayParser<List<TrelloList>> {
    @Override
    List<TrelloList> parseJson(JSONArray root) throws JSONException, ParseException {
        List<TrelloList> lists = new ArrayList<>();

        for (int i = 0; i < root.length(); ++i) {
            JSONObject listJson = root.getJSONObject(i);
            lists.add(new TrelloList(listJson.getString("id"),
                    listJson.getString("name")));
        }

        return lists;
    }
}
