package com.hms.atbotizmozel.data.model;

import java.io.Serializable;

/**
 * Created by Batuhan on 16.01.2018.
 */

public class VirtualPos implements Serializable {

    public String bin;
    public int virtualPosId;
    public String cardBank;
    public String dkk;

    public VirtualPos(String bin, int virtualPosId, String cardBank, String dkk) {
        this.bin = bin;
        this.virtualPosId = virtualPosId;
        this.cardBank = cardBank;
        this.dkk = dkk;
    }
}
