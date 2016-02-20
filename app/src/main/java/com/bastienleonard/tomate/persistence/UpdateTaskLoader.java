package com.bastienleonard.tomate.persistence;

import android.content.Context;
import android.support.v4.util.SimpleArrayMap;

import com.bastienleonard.tomate.BasicLoader;
import com.bastienleonard.tomate.TomateApp;
import com.bastienleonard.tomate.models.Task;

public final class UpdateTaskLoader extends BasicLoader<Boolean> {
    private static final String TAG = "UpdateTaskLoader";
    private final String mCardId;
    private final long mTime;
    private final boolean mIncPomodoros;

    public UpdateTaskLoader(Context context, String cardId, long time, boolean incPomodoros) {
        super(context);
        mCardId = cardId;
        mTime = time;
        mIncPomodoros = incPomodoros;
    }

    @Override
    public Boolean loadInBackground() {
        SimpleArrayMap<String, Task> tasks = Facade.getTasks(getContext());

        if (tasks != null) {
            Task task = tasks.get(mCardId);

            if (task == null) {
                tasks.put(mCardId, new Task(
                        mCardId,
                        mIncPomodoros ? 1 : 0,
                        mTime
                ));
            } else {
                int pomodoros = task.getPodomoros();

                if (mIncPomodoros) {
                    ++pomodoros;
                }

                tasks.put(mCardId, new Task(
                        task.getCardId(),
                        pomodoros,
                        task.getTotalTime() + mTime
                ));
            }

            TomateApp.get().getTaskCache().setTasks(tasks);
            return Facade.saveTasks(getContext(), tasks);
        }

        return Boolean.FALSE;
    }
}
