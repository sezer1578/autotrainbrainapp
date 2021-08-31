package com.hms.atbotizmozel.data.model.request;

import java.io.Serializable;

/**
 * Created by Batuhan on 24.02.2018.
 */

public class RegisterRequest implements Serializable {

    public String username;
    public String email;
    public String password;
    public String skill;
    public String gender;
    public String birthDate;
    public int maxSession;
    public String subscriptionStartDate;
    public String subscriptionEndDate;
    public String location;

    public RegisterRequest(String username, String email, String password, String skill, String gender, String birthDate, int maxSession, String subscriptionStartDate, String subscriptionEndDate,String location) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.skill = skill;
        this.gender = gender;
        this.birthDate = birthDate;
        this.maxSession= maxSession;
        this.subscriptionStartDate= subscriptionStartDate;
        this.subscriptionEndDate = subscriptionEndDate;
        this.location = location;
    }
}
