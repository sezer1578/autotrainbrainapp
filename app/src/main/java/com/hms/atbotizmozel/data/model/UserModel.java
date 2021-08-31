package com.hms.atbotizmozel.data.model;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Batuhan on 29.11.2017.
 */

public class UserModel implements Serializable {

    public String userName;
    public String email;
    public String password;
    public String userId;
    public String skill;
    public String birthdate;
    public int age;
    public String gender;
    public int session = 0;
    public int maxSession = 5;
    public int subsType = 0;
    public String helper;
    public String token;
    public String subscriptionStartDate;
    public String subscriptionEndDate;
    public String location;

    public UserModel(UserWithDetail userWithDetail) {
        this.userId = userWithDetail.userId;
        this.userName = userWithDetail.username;
        this.email = userWithDetail.email;
        this.password = userWithDetail.password;
        this.skill = userWithDetail.skill;
        this.gender = userWithDetail.gender;
        this.birthdate = userWithDetail.birthDate;
        this.age = userWithDetail.getAge();
        this.maxSession = userWithDetail.maxSession;
        this.session = userWithDetail.totalSession;
        this.subsType = userWithDetail.subscriptionType;
        this.token = userWithDetail.token;
        this.subscriptionStartDate = userWithDetail.subscriptionStartDate;
        this.subscriptionEndDate = userWithDetail.subscriptionEndDate;
        this.location = userWithDetail.location;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this, UserModel.class);
    }

    public static UserModel parse(String json) {
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(json)) {
            return gson.fromJson(json, UserModel.class);
        }
        return null;
    }
}
