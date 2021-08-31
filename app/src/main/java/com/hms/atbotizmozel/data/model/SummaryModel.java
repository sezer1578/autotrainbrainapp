package com.hms.atbotizmozel.data.model;

import java.io.Serializable;

/**
 * Created by Batuhan on 17.11.2017.
 */

public class SummaryModel implements Serializable {

    public String username;
    public int session;
    public long sessionStartDate;
    public long sessionEndDate;


    public float workout;
    public float wellness;
    public float score;
    public float avggamma;
    public float avgbeta2;
    public float avgbeta1;
    public float avgalpha;
    public float avgtheta;
    public String protocol;
    public String mode;
    public String activity;

    public SummaryModel(String username, int session, long sessionStartDate, long sessionEndDate,
                        float workout, float wellness, float score, float avggamma, float avgbeta2,
                        float avgbeta1, float avgalpha, float avgtheta, String protocol, String mode, String activity) {
        this.username = username;
        this.session = session;
        this.sessionStartDate = sessionStartDate;
        this.sessionEndDate = sessionEndDate;
        this.workout = workout;
        this.wellness = wellness;
        this.score = score;
        this.avggamma = avggamma;
        this.avgbeta2 = avgbeta2;
        this.avgbeta1 = avgbeta1;
        this.avgalpha = avgalpha;
        this.avgtheta = avgtheta;
        this.protocol = protocol;
        this.mode = mode;
        this.activity = activity;
    }
}
