package com.hms.atbotizmozel.data.soap;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.hms.atbotizmozel.data.model.PaymentType;
import com.hms.atbotizmozel.util.SoapUtils;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Batuhan on 16.01.2018.
 */

public class PaymentTypesAsync extends AsyncTaskLoader<List<PaymentType>> {

    private Context context;
    private boolean isLoading = false;

    public PaymentTypesAsync(Context context) {
        super(context);
        this.context = context;
    }

    protected void onStartLoading() {
        if (!isLoading) {
            isLoading = true;
            forceLoad();
        }
    }

    @Override
    public List<PaymentType> loadInBackground() {
        List<PaymentType> paymentTypes = new ArrayList<>();
        SoapObject soapResponse = SoapUtils.makeSoapCall("TP_Ozel_Oran_Liste", new HashMap<String, String>() {{
            put("GUID", SoapUtils.GUID);
        }});
        if (soapResponse == null) {
            return null;
        }
        SoapObject response = ((SoapObject) ((SoapObject) ((SoapObject) soapResponse.getProperty(0)).getProperty(1)).getProperty(0));
        for (int i = 0; i < response.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) response.getProperty(i);
            try {
                paymentTypes.add(new PaymentType(Integer.parseInt(soapObject.getPropertyAsString("Ozel_Oran_ID")),
                        soapObject.getPropertyAsString("GUID"),
                        soapObject.getPropertyAsString("Tarih_Bas"),
                        soapObject.getPropertyAsString("Tarih_Bit"),
                        Integer.parseInt(soapObject.getPropertyAsString("SanalPOS_ID")),
                        soapObject.getPropertyAsString("Kredi_Karti_Banka"),
                        soapObject.getPropertyAsString("Kredi_Karti_Banka_Gorsel"),
                        Float.parseFloat(soapObject.getPropertyAsString("MO_01")),
                        Float.parseFloat(soapObject.getPropertyAsString("MO_02")),
                        Float.parseFloat(soapObject.getPropertyAsString("MO_03")),
                        Float.parseFloat(soapObject.getPropertyAsString("MO_04")),
                        Float.parseFloat(soapObject.getPropertyAsString("MO_05")),
                        Float.parseFloat(soapObject.getPropertyAsString("MO_06")),
                        Float.parseFloat(soapObject.getPropertyAsString("MO_07")),
                        Float.parseFloat(soapObject.getPropertyAsString("MO_08")),
                        Float.parseFloat(soapObject.getPropertyAsString("MO_09")),
                        Float.parseFloat(soapObject.getPropertyAsString("MO_10")),
                        Float.parseFloat(soapObject.getPropertyAsString("MO_11")),
                        Float.parseFloat(soapObject.getPropertyAsString("MO_12"))
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return paymentTypes;
    }

    @Override
    public void deliverResult(List<PaymentType> data) {
        super.deliverResult(data);
        isLoading = false;
    }
}
