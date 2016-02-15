package com.bastienleonard.tomate.persistence;

import android.content.Context;

import com.bastienleonard.tomate.TomateApp;
import com.bastienleonard.tomate.models.Task;
import com.bastienleonard.tomate.BasicLoader;

import java.util.List;

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
        List<Task> tasks = Facade.getTasks(getContext());
        // TODO: save tasks as a map to avoid this list-map conversion, as well the list search

        if (tasks != null) {
            Task task = null;
            int i = 0;

            for (Task current : tasks) {
                if (mCardId.equals(current.getCardId())) {
                    task = current;
                    break;
                }

                ++i;
            }

            if (task == null) {
                tasks.add(new Task(
                        mCardId,
                        mIncPomodoros ? 1 : 0,
                        mTime));
            } else {
                int pomodoros = task.getPodomoros();

                if (mIncPomodoros) {
                    ++pomodoros;
                }

                tasks.set(i, new Task(
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
