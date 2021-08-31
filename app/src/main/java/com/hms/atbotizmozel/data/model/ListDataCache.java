package com.hms.atbotizmozel.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Batuhan on 18.11.2017.
 */

public class ListDataCache implements Serializable {

    public List<AtbActivityModel> models;

    public ListDataCache(List<AtbActivityModel> models){
        this.models = models;
    }

}
