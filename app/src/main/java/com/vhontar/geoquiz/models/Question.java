package com.vhontar.geoquiz.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mUserCheater;

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mUserCheater = false;
    }

    protected Question(Parcel in) {
        mTextResId = in.readInt();
        mAnswerTrue = in.readByte() != 0;
        mUserCheater = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mTextResId);
        dest.writeByte((byte) (mAnswerTrue ? 1 : 0));
        dest.writeByte((byte) (mUserCheater ? 1 : 0));
    }

    public int getTextResId() {
        return mTextResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public boolean isUserCheater() { return mUserCheater; }

    public void makeUserCheater(boolean isUserCheater) {
        mUserCheater = isUserCheater;
    }
}
