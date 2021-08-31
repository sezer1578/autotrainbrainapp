package com.hms.atbotizmozel.data.model;

import java.io.Serializable;

/**
 * Created by Batuhan on 23.01.2018.
 */

public class AtbPaymentModel implements Serializable {

    public String username;
    public double amount = 4200.00;
    public int subscriptionType = 1;
    public int installmentNumber;
    public String billName;
    public String billAddress;
    public String billEmail;
    public String billTelephone;
    public String billCity = "";
    public String helper = "";

    public AtbPaymentModel(String username, String billName, String billAddress, String billEmail, String billTelephone, int installmentNumber) {
        this.username = username;
        this.billName = billName;
        this.billAddress = billAddress;
        this.billEmail = billEmail;
        this.billTelephone = billTelephone;
        this.installmentNumber = installmentNumber;
    }

}
