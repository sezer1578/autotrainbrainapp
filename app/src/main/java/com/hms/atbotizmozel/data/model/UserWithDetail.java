package com.hms.atbotizmozel.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Batuhan on 27.02.2018.
 */

public class UserWithDetail implements Serializable {

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    public String username;
    public String email;
    public String password;
    @SerializedName("uid")
    public String userId;
    public String role;
    public String token;
    public int totalSession;
    public String skill;
    public String gender;
    public String birthDate;
    public int subscriptionType;
    public int maxSession;
    private Integer age;
    public String subscriptionStartDate;
    public String subscriptionEndDate;
    public String location;

    public Integer getAge() {
        if (age == null) {
            try {
                Calendar dateOfBirth = Calendar.getInstance();
                dateOfBirth.setTime(format.parse(birthDate));
                Calendar today = Calendar.getInstance();
                age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
                dateOfBirth.add(Calendar.YEAR, age);
                if (today.before(dateOfBirth)) {
                    age--;
                }
            } catch (Exception e) {
                age = 1;
            }
        }
        return age;
    }

}
