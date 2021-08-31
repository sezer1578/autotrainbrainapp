package com.hms.atbotizmozel.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Batuhan on 18.11.2017.
 */

public class ListDataCache2 implements Serializable {

    public List<AtbEntropyModel> models;

    public ListDataCache2(List<AtbEntropyModel> models){
        this.models = models;
    }

}
