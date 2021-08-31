package com.hms.atbotizmozel.persistence;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "family_emotion")
public class Emotion {

    @ColumnInfo(name = "EmotionName")
    @NonNull
    public String mEmotionName;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uId")
    public Integer uid;

    @NonNull
    @ColumnInfo(name = "EmotionPhoto")
    public String mEmotionPhoto;

    public Emotion(@NonNull String mEmotionName, @NonNull String mEmotionPhoto) {
        this.mEmotionName = mEmotionName;
        this.mEmotionPhoto = mEmotionPhoto;
    }

    @Override
    public String toString() {
        return "Emotion{" +
                "mEmotionName='" + mEmotionName + '\'' +
                '}';
    }
//getter setter


    @NonNull
    public String getmEmotionName() {
        return mEmotionName;
    }

    public void setmEmotionName(@NonNull String mEmotionName) {
        this.mEmotionName = mEmotionName;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @NonNull
    public String getmEmotionPhoto() {
        return mEmotionPhoto;
    }

    public void setmEmotionPhoto(@NonNull String mEmotionPhoto) {
        this.mEmotionPhoto = mEmotionPhoto;
    }
}
