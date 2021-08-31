package com.hms.atbotizmozel.persistence;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "family_member")
public class Member {

    @ColumnInfo(name = "name")
    @NonNull
    public String mName;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uId")
    public Integer uid;
    @NonNull
    @ColumnInfo(name = "photo")

    public String mPhoto;


    public Member(@NonNull String name, String photo){
        this.mName=name;
        this.mPhoto=photo;
    }

    @Override
    public String toString() {
        return "Member{" +
                "mName='" + mName + '\'' +
                '}';
    }
//getter setter


    @NonNull
    public String getmName() {
        return mName;
    }

    public void setmName(@NonNull String mName) {
        this.mName = mName;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @NonNull
    public String getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(@NonNull String mPhoto) {
        this.mPhoto = mPhoto;
    }
}
