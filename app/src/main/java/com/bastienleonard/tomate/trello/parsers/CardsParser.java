package com.bastienleonard.tomate.trello.parsers;

import com.bastienleonard.tomate.trello.models.Card;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CardsParser extends JsonParser<List<Card>, JSONArray> {
    @Override
    List<Card> parseJson(JSONArray root) throws JSONException, ParseException {
        List<Card> lists = new ArrayList<>();

        for (int i = 0; i < root.length(); ++i) {
            JSONObject listJson = root.getJSONObject(i);
            lists.add(new Card(listJson.getString("id"),
                    listJson.getString("name")));
        }

        return lists;
    }
}
