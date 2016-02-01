package com.bastienleonard.tomate.trello.models;

import com.bastienleonard.tomate.ui.trellosetup.Displayable;

public final class TrelloList implements Displayable {
    private final String mId;
    private final String mName;

    public TrelloList(String id, String name) {
        mId = id;
        mName = name;
    }

    @Override
    public String toString() {
        return "TrelloList{" +
                "id='" + mId + '\'' +
                ", name='" + mName + '\'' +
                '}';
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    @Override
    public String displayText() {
        return mName;
    }
}
