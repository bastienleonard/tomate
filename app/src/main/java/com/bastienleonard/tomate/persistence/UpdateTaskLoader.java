package com.bastienleonard.tomate.persistence;

import android.content.Context;

import com.bastienleonard.tomate.TomateApp;
import com.bastienleonard.tomate.models.Task;
import com.bastienleonard.tomate.BasicLoader;
import com.bastienleonard.tomate.utils.LogUtils;

import java.util.List;

public final class UpdateTaskLoader extends BasicLoader<Boolean> {
    private static final String TAG = "UpdateTaskLoader";
    private final String mCardId;
    private final long mTime;

    public UpdateTaskLoader(Context context, String cardId, long time) {
        super(context);
        LogUtils.d(TAG, "cardId=" + cardId + ", time=" + time);
        mCardId = cardId;
        mTime = time;
    }

    @Override
    public Boolean loadInBackground() {
        List<Task> tasks = Facade.getTasks(getContext());

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
                        0,
                        mTime));
            } else {
                tasks.set(i, new Task(
                        task.getCardId(),
                        task.getPodomoros(),
                        task.getTotalTime() + mTime
                ));
            }

            TomateApp.get().getTaskCache().setTasks(tasks);
            return Facade.saveTasks(getContext(), tasks);
        }

        return Boolean.FALSE;
    }
}
