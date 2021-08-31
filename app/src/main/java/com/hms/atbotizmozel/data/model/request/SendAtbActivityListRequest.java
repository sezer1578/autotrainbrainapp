package com.hms.atbotizmozel.data.model.request;

import com.hms.atbotizmozel.data.model.AtbActivityModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Batuhan on 28.02.2018.
 */

public class SendAtbActivityListRequest implements Serializable {

    public List<AtbActivityModel> atbActivityList;

    public SendAtbActivityListRequest(List<AtbActivityModel> atbActivityList) {
        this.atbActivityList = atbActivityList;
    }
}
