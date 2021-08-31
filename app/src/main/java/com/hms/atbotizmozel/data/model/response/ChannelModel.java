package com.hms.atbotizmozel.data.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Batuhan on 26.02.2018.
 */

public class ChannelModel implements Serializable {

    public String name;
    public List<Double> alphaList;
    public List<Double> beta1List;
    public List<Double> beta2List;
    public List<Double> gammaList;
    public List<Double> thetaList;

    public ChannelModel(String name, List<Double> alphaList, List<Double> beta1List, List<Double> beta2List, List<Double> gammaList, List<Double> thetaList) {
        this.name = name;
        this.alphaList = alphaList;
        this.beta1List = beta1List;
        this.beta2List = beta2List;
        this.gammaList = gammaList;
        this.thetaList = thetaList;
    }
}
