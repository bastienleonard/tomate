package com.bastienleonard.tomate.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

public final class Task implements Parcelable {
    private static final String TAG = "Task";
















    // FIXME: reset to normal value
    //public static final long POMODORO_DURATION = 25 * 60 * 1000L;
    public static final long POMODORO_DURATION = 5 * 1000L;













    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    private final String mCardId;
    private final int mPodomoros;
    private final long mTotalTime;

    public Task(String cardId, int podomoros, long totalTime) {
        this.mCardId = cardId;
        this.mPodomoros = podomoros;
        this.mTotalTime = totalTime;
    }

    protected Task(Parcel in) {
        mCardId = in.readString();
        mPodomoros = in.readInt();
        mTotalTime = in.readLong();
    }

    public String getCardId() {
        return mCardId;
    }

    public int getPodomoros() {
        return mPodomoros;
    }

    public long getTotalTime() {
        return mTotalTime;
    }

    public Task incPomodoros() {
        return new Task(mCardId,
                mPodomoros + 1,
                mTotalTime + POMODORO_DURATION);
    }

    public Task addTime(long time) {
        return new Task(mCardId,
                mPodomoros,
                mTotalTime + time);
    }

    public String getPrettyTotalTime() {
        long hours = TimeUnit.MILLISECONDS.toHours(mTotalTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(mTotalTime) - TimeUnit.HOURS.toMinutes(hours);
        return String.format("%dh%02d", hours, minutes);
    }

    @Override
    public String toString() {
        return "Task{" +
                "cardId='" + mCardId + '\'' +
                ", podomoros=" + mPodomoros +
                ", totalTime=" + mTotalTime +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(mCardId);
        dest.writeInt(mPodomoros);
        dest.writeLong(mTotalTime);
    }
}
