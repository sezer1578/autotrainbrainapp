package com.hms.atbotizmozel.data.model.request;

import java.io.Serializable;

/**
 * Created by Batuhan on 24.02.2018.
 */

public class LoginRequest implements Serializable {

    public String username;
    public String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
