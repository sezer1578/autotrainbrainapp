package com.hms.atbotizmozel.data.soap;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.hms.atbotizmozel.data.model.PaymentModel;
import com.hms.atbotizmozel.util.SoapUtils;

import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

/**
 * Created by Batuhan on 16.01.2018.
 */

public class MakePaymentAsync extends AsyncTaskLoader<String> {

    private Context context;
    private PaymentModel paymentModel;
    private boolean isLoading = false;

    public MakePaymentAsync(Context context, PaymentModel paymentModel) {
        super(context);
        this.context = context;
        this.paymentModel = paymentModel;
    }

    protected void onStartLoading() {
        if (!isLoading) {
            isLoading = true;
            forceLoad();
        }
    }

    @Override
    public String loadInBackground() {
        SoapObject soapResponse = SoapUtils.makeSoapCall("TP_Islem_Odeme", new HashMap<String, String>() {{
            put("GUID", SoapUtils.GUID);
            put("SanalPOS_ID", "" + paymentModel.virtualPosId);
            put("KK_Sahibi", paymentModel.creditCardName);
            put("KK_No", paymentModel.creditCardNo);
            put("KK_SK_Ay", paymentModel.creditCardMonth);
            put("KK_SK_Yil", paymentModel.creditCardYear);
            put("KK_CVC", paymentModel.creditCardCVC);
            put("KK_Sahibi_GSM", paymentModel.gsmNumber);
            put("Hata_URL", paymentModel.errorUrl);
            put("Basarili_URL", paymentModel.successUrl);
            put("Siparis_ID", "" + paymentModel.orderId);
            put("Taksit", "" + paymentModel.installment);
            put("Islem_Tutar", paymentModel.operationAmount);
            put("Toplam_Tutar", paymentModel.totalAmount);
            put("Islem_Hash", paymentModel.operationHash);
            put("IPAdr", paymentModel.ipAddress);
            put("Islem_ID", "" + paymentModel.operationId);
        }});
        if (soapResponse == null) {
            return null;
        }

        return soapResponse.getPropertyAsString(1);
    }

    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);
        isLoading = false;
    }
}
