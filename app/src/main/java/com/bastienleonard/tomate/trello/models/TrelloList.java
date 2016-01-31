package com.bastienleonard.tomate.trello.models;

public final class TrelloList {
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
}
