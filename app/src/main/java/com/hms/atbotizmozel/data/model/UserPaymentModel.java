package com.hms.atbotizmozel.data.model;

import java.io.Serializable;

/**
 * Created by Batuhan on 19.01.2018.
 */

public class UserPaymentModel implements Serializable {

    public String username;
    public String name;
    public String phone;
    public String mail;
    public String address;

    public UserPaymentModel(String username, String name, String phone, String mail, String address) {
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
    }
}
