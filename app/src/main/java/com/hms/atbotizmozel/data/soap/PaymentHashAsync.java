package com.hms.atbotizmozel.data.soap;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.hms.atbotizmozel.data.model.PaymentModel;
import com.hms.atbotizmozel.util.SoapUtils;

import java.util.HashMap;

/**
 * Created by Batuhan on 17.01.2018.
 */

public class PaymentHashAsync extends AsyncTaskLoader<String> {

    private Context context;
    private String data;
    private boolean isLoading = false;

    public PaymentHashAsync(Context context, PaymentModel paymentModel) {
        super(context);
        this.context = context;
        this.data = SoapUtils.CLIENT_CODE + SoapUtils.GUID + paymentModel.virtualPosId + paymentModel.installment
                + paymentModel.operationAmount + paymentModel.totalAmount + paymentModel.orderId + paymentModel.errorUrl + paymentModel.successUrl;

    }

    protected void onStartLoading() {
        if (!isLoading) {
            isLoading = true;
            forceLoad();
        }
    }

    @Override
    public String loadInBackground() {
        String response = SoapUtils.makeSoapCallHash("SHA2B64", new HashMap<String, String>() {{
            put("Data", data);
        }});
        return response;
    }

    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);
        isLoading = false;
    }
}
