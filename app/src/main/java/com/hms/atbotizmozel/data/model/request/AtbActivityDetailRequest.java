package com.hms.atbotizmozel.data.model.request;

import com.hms.atbotizmozel.data.model.response.ChannelModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Batuhan on 26.02.2018.
 */

public class AtbActivityDetailRequest implements Serializable {

    public String username;
    public int session;
    public List<ChannelModel> channelList;

    public AtbActivityDetailRequest(String username, int session, List<ChannelModel> channelList) {
        this.username = username;
        this.session = session;
        this.channelList = channelList;
    }
}
