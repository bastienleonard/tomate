package com.bastienleonard.tomate.trello.models;

import com.bastienleonard.tomate.ui.trellosetup.Displayable;

public final class Board implements Displayable {
    private final String mId;
    private final String mName;

    public Board(String id, String name) {
        mId = id;
        mName = name;
    }

    @Override
    public String toString() {
        return "Board{" +
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
