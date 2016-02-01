package com.bastienleonard.tomate;

import android.content.Context;
import android.support.annotation.CheckResult;

public final class Persistence {
    private static final String PREF_NAME = "persistence";
    private static final String KEY_BOARD_ID = "boardId";
    private static final String KEY_TODO_LIST_ID = "todoListId";
    private static final String KEY_DOING_LIST_ID = "doingListId";
    private static final String KEY_DONE_LIST_ID = "doneListId";

    private Persistence() {
    }

    @CheckResult
    public static boolean saveBoardId(Context context, String id) {
        return saveString(context, KEY_BOARD_ID, id);
    }

    public static String getBoardId(Context context) {
        return getString(context, KEY_BOARD_ID);
    }

    @CheckResult
    public static boolean saveToDoListId(Context context, String id) {
        return saveString(context, KEY_TODO_LIST_ID, id);
    }

    public static String getToDoListId(Context context) {
        return getString(context, KEY_TODO_LIST_ID);
    }

    @CheckResult
    public static boolean saveDoingListId(Context context, String id) {
        return saveString(context, KEY_DOING_LIST_ID, id);
    }

    public static String getDoingListId(Context context) {
        return getString(context, KEY_DOING_LIST_ID);
    }

    @CheckResult
    public static boolean saveDoneListId(Context context, String id) {
        return saveString(context, KEY_DONE_LIST_ID, id);
    }

    public static String getDoneListId(Context context) {
        return getString(context, KEY_DONE_LIST_ID);
    }

    @CheckResult
    private static boolean saveString(Context context, String key, String value) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(key, value)
                .commit();
    }

    private static String getString(Context context, String key) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                .getString(key, null);
    }
}
