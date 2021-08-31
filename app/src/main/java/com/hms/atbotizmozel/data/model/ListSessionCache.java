package com.hms.atbotizmozel.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Batuhan on 18.11.2017.
 */

public class ListSessionCache implements Serializable {

    public List<String> sessions;

    public ListSessionCache(ArrayList<String> sessions) {
        this.sessions = sessions;
    }
}
