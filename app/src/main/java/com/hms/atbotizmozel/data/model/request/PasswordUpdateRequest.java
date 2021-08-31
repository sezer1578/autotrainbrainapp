package com.hms.atbotizmozel.data.model.request;

import java.io.Serializable;

public class PasswordUpdateRequest implements Serializable {
    public String username;
    public String currentPass;
    public String newPass;

    public PasswordUpdateRequest(String username, String password, String curPass) {
            this.currentPass = curPass;
        this.newPass = password;
        this.username = username;
    }
}
