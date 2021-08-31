package com.hms.atbotizmozel.util;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.data.model.response.BaseResponse;
import com.hms.atbotizmozel.data.model.response.ErrorModel;

/**
 * Created by Batuhan on 28.02.2018.
 */

public class ErrorUtils {

    public static <T> boolean isSuccess(Context context, retrofit2.Response<BaseResponse<T>> response) {
        if (response.isSuccessful() && response.body().success) {
            return true;
        }
        if (!DeviceUtils.isNetworkAvailable(context)) {
            Toast.makeText(context, context.getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!response.isSuccessful() && response.errorBody() != null) {
            ErrorModel errorModel = null;
            try {
                errorModel = new Gson().fromJson(response.errorBody().string(), BaseResponse.class).error;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (errorModel != null) {
                Toast.makeText(context, errorModel.message, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        Toast.makeText(context, context.getString(R.string.error_unexpected), Toast.LENGTH_SHORT).show();
        return false;
    }

    public static void parseThrowable(Context context, Throwable throwable) {
        if (!DeviceUtils.isNetworkAvailable(context)) {
            Toast.makeText(context, context.getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
