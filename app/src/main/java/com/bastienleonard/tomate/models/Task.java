package com.bastienleonard.tomate.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public final class Task implements Parcelable {
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
