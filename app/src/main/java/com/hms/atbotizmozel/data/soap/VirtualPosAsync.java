package com.hms.atbotizmozel.data.soap;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.hms.atbotizmozel.data.model.VirtualPos;
import com.hms.atbotizmozel.util.SoapUtils;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

/**
 * Created by Batuhan on 16.01.2018.
 */

public class VirtualPosAsync extends AsyncTaskLoader<VirtualPos> {

    private Context context;
    public String bin;
    private boolean isLoading = false;

    public VirtualPosAsync(Context context, String bin) {
        super(context);
        this.context = context;
        this.bin = bin;
    }

    protected void onStartLoading() {
        if (!isLoading) {
            isLoading = true;
            forceLoad();
        }
    }

    @Override
    public VirtualPos loadInBackground() {
        VirtualPos virtualPos = null;
        SoapObject soapResponse = SoapUtils.makeSoapCall("BIN_SanalPos", new HashMap<String, String>() {{
            put("BIN", bin);
        }});
        if (soapResponse == null) {
            return null;
        }
        SoapObject response = ((SoapObject) ((SoapObject) ((SoapObject) soapResponse.getProperty(0)).getProperty(1)).getProperty(0));
        for (int i = 0; i < response.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) response.getProperty(i);
            try {
                virtualPos = new VirtualPos(soapObject.getPropertyAsString("BIN"), Integer.parseInt(soapObject.getPropertyAsString("SanalPOS_ID")),
                        soapObject.getPropertyAsString("Kart_Banka"), soapObject.getPropertyAsString("DKK"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return virtualPos;
    }

    @Override
    public void deliverResult(VirtualPos data) {
        super.deliverResult(data);
        isLoading = false;
    }
}
