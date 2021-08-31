package com.hms.atbotizmozel.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Batuhan on 16.01.2018.
 */

public class PaymentType implements Serializable {

    public int id;
    public String guid;
    public String startDate;
    public String endDate;
    public int posId;
    public String creditCardBank;
    public String creditCardBankLogo;
    public float month1Rate;
    public float month2Rate;
    public float month3Rate;
    public float month4Rate;
    public float month5Rate;
    public float month6Rate;
    public float month7Rate;
    public float month8Rate;
    public float month9Rate;
    public float month10Rate;
    public float month11Rate;
    public float month12Rate;

    public List<Float> installmentRates = new ArrayList<>();
    public List<Integer> installmentCounts = new ArrayList<>();

    public PaymentType(int id, String guid, String startDate, String endDate, int posId, String creditCardBank, String creditCardBankLogo,
                       float month1Rate, float month2Rate, float month3Rate, float month4Rate, float month5Rate, float month6Rate,
                       float month7Rate, float month8Rate, float month9Rate, float month10Rate, float month11Rate, float month12Rate) {
        this.id = id;
        this.guid = guid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.posId = posId;
        this.creditCardBank = creditCardBank;
        this.creditCardBankLogo = creditCardBankLogo;
        this.month1Rate = month1Rate;
        this.month2Rate = month2Rate;
        this.month3Rate = month3Rate;
        this.month4Rate = month4Rate;
        this.month5Rate = month5Rate;
        this.month6Rate = month6Rate;
        this.month7Rate = month7Rate;
        this.month8Rate = month8Rate;
        this.month9Rate = month9Rate;
        this.month10Rate = month10Rate;
        this.month11Rate = month11Rate;
        this.month12Rate = month12Rate;
    }
}
