package com.hms.atbotizmozel.data.model;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Batuhan on 17.01.2018.
 */

public class PaymentModel implements Serializable {

    public int virtualPosId;
    public int installment;
    public String operationAmount;
    public String totalAmount;
    public long orderId;
    public String errorUrl;
    public String successUrl;
    public String creditCardName;
    public String creditCardNo;
    public String creditCardMonth;
    public String creditCardYear;
    public String creditCardCVC;
    public String operationHash;
    public String gsmNumber;
    public String ipAddress = "192.168.2.93";
    public long operationId;

    public PaymentModel(int virtualPosId, int installment, String operationAmount, String totalAmount, long orderId, String errorUrl,
                        String successUrl, String creditCardName, String creditCardNo, String creditCardMonth, String creditCardYear,
                        String creditCardCVC, String gsmNumber, String operationHash) {
        this.virtualPosId = virtualPosId;
        this.installment = installment;
        this.operationAmount = operationAmount;
        this.totalAmount = totalAmount;
        this.orderId = orderId;
        this.errorUrl = errorUrl;
        this.successUrl = successUrl;
        this.creditCardName = creditCardName;
        this.creditCardNo = creditCardNo;
        this.creditCardMonth = creditCardMonth;
        this.creditCardYear = creditCardYear;
        this.creditCardCVC = creditCardCVC;
        this.gsmNumber = gsmNumber;
        this.operationHash = operationHash;
        this.operationId = orderId;
        Log.e("PAYMENTMODEL", "operationId: " + operationId);
    }
}
