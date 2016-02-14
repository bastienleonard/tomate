package com.bastienleonard.tomate.persistence;

import android.support.annotation.NonNull;

import com.bastienleonard.tomate.models.Task;
import com.bastienleonard.tomate.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

final class TasksPersistence {
    private static final String TAG = "TasksPersistence";
    private static final String KEY_TASKS = "tasks";
    private static final String KEY_CARD_ID = "cardId";
    private static final String KEY_POMODOROS = "pomodoros";
    private static final String KEY_TOTAL_TIME = "totalTime";

    private TasksPersistence() {
    }

    public static String toJson(List<Task> tasks)
            throws JSONException {
        JSONObject root = new JSONObject();
        JSONArray jsonTasks = new JSONArray();

        for (Task task: tasks) {
            jsonTasks.put(toJson(task));
        }

        root.put(KEY_TASKS, jsonTasks);
        return root.toString();
    }

    public static List<Task> fromJson(String json)
            throws JSONException {
        JSONObject root = new JSONObject(json);
        JSONArray jsonTasks = root.getJSONArray(KEY_TASKS);
        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < jsonTasks.length(); ++i) {
            JSONObject jsonTask = jsonTasks.getJSONObject(i);
            tasks.add(fromJson(jsonTask));
        }

        LogUtils.d(TAG, "Loaded tasks " + tasks);
        return tasks;
    }

    @NonNull
    private static JSONObject toJson(@NonNull Task task)
            throws JSONException {
        JSONObject jsonTask = new JSONObject();
        jsonTask.put(KEY_CARD_ID, task.getCardId());
        jsonTask.put(KEY_POMODOROS, task.getPodomoros());
        jsonTask.put(KEY_TOTAL_TIME, task.getTotalTime());
        return jsonTask;
    }

    @NonNull
    private static Task fromJson(@NonNull JSONObject jsonTask)
            throws JSONException {
        return new Task(jsonTask.getString(KEY_CARD_ID),
                jsonTask.getInt(KEY_POMODOROS),
                jsonTask.getLong(KEY_TOTAL_TIME));
    }
}
