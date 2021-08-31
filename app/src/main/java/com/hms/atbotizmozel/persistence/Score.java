package com.hms.atbotizmozel.persistence;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "family_score")
public class Score {

    @ColumnInfo(name = "date")
    @NonNull
    public String mDate;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uId")
    public Integer uid;

    @ColumnInfo(name = "time")
    @NonNull
    public String mTime;

    @ColumnInfo(name = "testName")
    @NonNull
    public String mTestName;

    @ColumnInfo(name = "result")
    @NonNull
    public String mResult;


    public Score(@NonNull String date, String time, String testName, String result){
        this.mDate= date;
        this.mTime=time;
        this.mTestName=testName;
        this.mResult=result;
    }

    //getter and setter

    @NonNull
    public String getmDate() {
        return mDate;
    }

    public void setmDate(@NonNull String mDate) {
        this.mDate = mDate;
    }

    @NonNull
    public String getmTime() {
        return mTime;
    }

    public void setmTime(@NonNull String mTime) {
        this.mTime = mTime;
    }

    @NonNull
    public String getmTestName() {
        return mTestName;
    }

    public void setmTestName(@NonNull String mTestName) {
        this.mTestName = mTestName;
    }

    @NonNull
    public String getmResult() {
        return mResult;
    }

    public void setmResult(@NonNull String mResult) {
        this.mResult = mResult;
    }

    public Integer getUid() {
        return uid;
    }
}
