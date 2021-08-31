package com.hms.atbotizmozel.data.model.request;

import com.hms.atbotizmozel.data.model.AtbEntropyModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Batuhan on 28.02.2018.
 */

public class SendAtbEntropyListRequest implements Serializable {

    public List<AtbEntropyModel> atbEntropyList;

    public SendAtbEntropyListRequest(List<AtbEntropyModel> atbEntropyList) {
        this.atbEntropyList = atbEntropyList;
    }
}
