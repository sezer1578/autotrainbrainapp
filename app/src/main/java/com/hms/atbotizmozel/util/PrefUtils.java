package com.hms.atbotizmozel.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hms.atbotizmozel.data.model.ListDataCache;
import com.hms.atbotizmozel.data.model.ListSessionCache;
import com.hms.atbotizmozel.data.model.SummaryModel;
import com.hms.atbotizmozel.data.model.UserModel;

import java.util.ArrayList;

/**
 * Created by Batuhan on 18.11.2017.
 */

public class PrefUtils {
    private static final String PREF_NAME = "AUTO_TRAIN_BRAIN";
    private static final String LIST_SESSION = "LIST_SESSION";
    private static final String USER_LOGIN = "USER_LOGIN";
    private static final String PASSWORD = "PASSWORD";
    private static final String USER_NAME = "USER_NAME";
    private static final String EMAIL = "EMAIL";

    private static PrefUtils ourInstance;

    public static PrefUtils with(Context context) {
        if (ourInstance == null) {
            ourInstance = new PrefUtils(context);
        }
        return ourInstance;
    }

    private Context context;

    private PrefUtils(Context context) {
        this.context = context;
    }

    public void saveUserModel(UserModel userModel) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(USER_LOGIN, userModel.toJson()).apply();
    }

    public UserModel getUserModel() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String userModelStr = prefs.getString(USER_LOGIN, null);
        return UserModel.parse(userModelStr);
    }

    public void removeUserModel() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(USER_LOGIN, "").apply();
    }
    public void savePassword(String password) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(PASSWORD, password).apply();
    }

    public void saveUserName(String userName) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(USER_NAME, userName).apply();
    }
/*
    public void saveEmail(String email) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(EMAIL, email).apply();
    }*/

    public String getEmail() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(EMAIL, null);
    }

    public String getUserName() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(USER_NAME, null);
    }

    public String getPassword() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(PASSWORD, null);
    }

    public void saveAverageData(int session, SummaryModel summaryModel) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(session + "-AverageData", new Gson().toJson(summaryModel, SummaryModel.class)).apply();
    }

    public void saveListData(String session, ListDataCache cacheModel) {
        Gson gson = new Gson();
        String value = gson.toJson(cacheModel, ListDataCache.class);
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(session + "-ActivityListData", value).apply();
    }

    public void saveSession(String session) {
        ListSessionCache sessionCache = getListSession();
        sessionCache.sessions.add(session);
        Gson gson = new Gson();
        String value = gson.toJson(sessionCache, ListSessionCache.class);
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(LIST_SESSION, value).apply();
    }

    public SummaryModel getAverageData(String session) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String value = prefs.getString(session + "-AverageData", "");
        return new Gson().fromJson(value, SummaryModel.class);
    }

    public ListDataCache getListData(String session) {
        Gson gson = new Gson();
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String modelStr = prefs.getString(session + "-ActivityListData", "");
        if (TextUtils.isEmpty(modelStr)) {
            return null;
        } else {
            return gson.fromJson(modelStr, ListDataCache.class);
        }
    }

    public ListSessionCache getListSession() {
        Gson gson = new Gson();
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String modelStr = prefs.getString(LIST_SESSION, "");
        if (TextUtils.isEmpty(modelStr)) {
            return new ListSessionCache(new ArrayList<String>());
        } else {
            return gson.fromJson(modelStr, ListSessionCache.class);
        }
    }

    public void clear() {
        /*UserModel userModel = isLogin();*/
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();

        /*if (userModel != null) {
            login(userModel);
        }*/
    }
}
