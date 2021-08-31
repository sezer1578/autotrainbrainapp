package com.hms.atbotizmozel.data.model;

import java.io.Serializable;

/**
 * Created by Batuhan on 06.12.2017.
 */

public class AtbEntropyModel implements Serializable {

    public String username;
    public long date;
    public int session;
    public String channel;
    public double theta;
    public double alpha;
    public double beta1;
    public double beta2;
    public double gamma;
    public String protocol;
    public String mode;
    public String activity;

    public AtbEntropyModel(String username, long date, int session, String channel, double theta, double alpha, double beta1, double beta2, double gamma,String protocol,String mode, String activity) {
        this.username = username;
        this.date = date;
        this.session = session;
        this.channel = channel;
        this.theta = theta;
        this.alpha = alpha;
        this.beta1 = beta1;
        this.beta2 = beta2;
        this.gamma = gamma;
        this.protocol = protocol;
        this.mode = mode;
        this.activity = activity;
    }
}
