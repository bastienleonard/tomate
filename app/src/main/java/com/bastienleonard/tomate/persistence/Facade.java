package com.bastienleonard.tomate.persistence;

import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import com.bastienleonard.tomate.models.Task;
import com.bastienleonard.tomate.utils.LogUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public final class Facade {
    private static final String TAG = "Persistence";
    private static final String PREF_NAME = "persistence";
    private static final String KEY_BOARD_ID = "boardId";
    private static final String KEY_TODO_LIST_ID = "todoListId";
    private static final String KEY_DOING_LIST_ID = "doingListId";
    private static final String KEY_DONE_LIST_ID = "doneListId";
    private static final String KEY_TASKS = "tasks";

    private Facade() {
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
    public static boolean saveTasks(Context context, List<Task> tasks) {
        try {
            return saveString(context, KEY_TASKS, TasksPersistence.toJson(tasks));
        } catch (JSONException e) {
            LogUtils.e(TAG, e);
            return false;
        }
    }

    @Nullable
    @WorkerThread
    public static List<Task> getTasks(Context context) {
        LogUtils.d(TAG, "Loading tasks");

        try {
            String json = getString(context, KEY_TASKS);

            if (TextUtils.isEmpty(json)) {
                return new ArrayList<>();
            }

            List<Task> tasks = TasksPersistence.fromJson(json);
            LogUtils.d(TAG, "Loaded tasks");
            return tasks;
        } catch (JSONException e) {
            LogUtils.e(TAG, e);
            return null;
        }
    }

    @CheckResult
    @WorkerThread
    private static boolean saveString(Context context, String key, String value) {
        LogUtils.d(TAG, "Saved string " + value + " for key " + key);
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
