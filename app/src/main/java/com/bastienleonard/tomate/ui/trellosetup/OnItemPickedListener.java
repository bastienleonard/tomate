package com.bastienleonard.tomate.ui.trellosetup;

import com.bastienleonard.tomate.trello.models.Board;
import com.bastienleonard.tomate.trello.models.TrelloList;

public interface OnItemPickedListener {
    void onBoardPicked(Board board);
    void onTodoListPicked(TrelloList list);
    void onDoingListPicked(TrelloList list);
    void onDoneListPicked(TrelloList list);
}
