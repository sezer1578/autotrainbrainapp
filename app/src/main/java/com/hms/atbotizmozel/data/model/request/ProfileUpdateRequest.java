package com.hms.atbotizmozel.data.model.request;

import java.io.Serializable;

/**
 * Created by Batuhan on 04.03.2018.
 */

public class ProfileUpdateRequest implements Serializable {
    public String skill;
    public String email;
    public String gender;
    public String birthDate;
    public int maxSession;
    public String subscriptionStartDate;
    public String subscriptionEndDate;
    public String location;

    public ProfileUpdateRequest(String skill, String email, String gender, String birthDate, int maxSession, String subscriptionStartDate, String subscriptionEndDate,String location) {
        this.skill = skill;
        this.email = email;
        this.gender = gender;
        this.birthDate = birthDate;
        this.subscriptionStartDate= subscriptionStartDate;
        this.subscriptionEndDate= subscriptionEndDate;
        this.maxSession= maxSession;
        this.location = location;
    }
}
