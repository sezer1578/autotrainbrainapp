package com.hms.atbotizmozel.data.call;

import android.content.Context;

import com.hms.atbotizmozel.data.ServiceGenerator;
import com.hms.atbotizmozel.data.model.AtbActivityModel;
import com.hms.atbotizmozel.data.model.AtbEntropyModel;
import com.hms.atbotizmozel.data.model.AtbPaymentModel;
import com.hms.atbotizmozel.data.model.SummaryModel;
import com.hms.atbotizmozel.data.model.UserModel;
import com.hms.atbotizmozel.data.model.UserWithDetail;
import com.hms.atbotizmozel.data.model.request.AtbActivityDetailRequest;
import com.hms.atbotizmozel.data.model.request.ForgotPasswordRequest;
import com.hms.atbotizmozel.data.model.request.LoginRequest;
import com.hms.atbotizmozel.data.model.request.PasswordUpdateRequest;
import com.hms.atbotizmozel.data.model.request.ProfileUpdateRequest;
import com.hms.atbotizmozel.data.model.request.RegisterRequest;
import com.hms.atbotizmozel.data.model.request.SendAtbActivityListRequest;
import com.hms.atbotizmozel.data.model.request.SendAtbEntropyListRequest;
import com.hms.atbotizmozel.data.model.response.BaseResponse;
import com.hms.atbotizmozel.data.model.response.EmptyResponse;
import com.hms.atbotizmozel.data.model.response.PasswordChangeResponse;
import com.hms.atbotizmozel.data.model.response.TotalSessionResponse;
import com.hms.atbotizmozel.data.service.AtbServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Batuhan on 17.11.2017.
 */

public class AtbCalls {

    public static Call login(Context context, LoginRequest request, Callback<BaseResponse<UserWithDetail>> callback) {
        AtbServices service = ServiceGenerator.with(context).createService(AtbServices.class);
        Call<BaseResponse<UserWithDetail>> call = service.login(request);
        call.enqueue(callback);
        return call;
    }

    public static Call register(Context context, RegisterRequest request, Callback<BaseResponse<UserWithDetail>> callback) {
        AtbServices service = ServiceGenerator.with(context).createService(AtbServices.class);
        Call<BaseResponse<UserWithDetail>> call = service.register(request);
        call.enqueue(callback);
        return call;
    }

    public static Call getTotalSession(Context context, UserModel userModel, Callback<BaseResponse<TotalSessionResponse>> callback) {
        AtbServices service = ServiceGenerator.with(context).createService(AtbServices.class);
        Call<BaseResponse<TotalSessionResponse>> call = service.getTotalSession(userModel.userName, userModel.token, userModel.userId);
        call.enqueue(callback);
        return call;
    }

    public static Call sendAtbActivity(Context context, UserModel userModel, List<AtbActivityModel> models, Callback<BaseResponse<EmptyResponse>> callback) {
        AtbServices service = ServiceGenerator.with(context).createService(AtbServices.class);
        Call<BaseResponse<EmptyResponse>> call = service.sendAtbActvitiyList(userModel.token, userModel.userId, new SendAtbActivityListRequest(models));
        call.enqueue(callback);
        return call;
    }

    public static Call sendAtbEntropy(Context context, UserModel userModel, List<AtbEntropyModel> models, Callback<BaseResponse<EmptyResponse>> callback) {
        AtbServices service = ServiceGenerator.with(context).createService(AtbServices.class);
        Call<BaseResponse<EmptyResponse>> call = service.sendAtbEntropyList(userModel.token, userModel.userId, new SendAtbEntropyListRequest(models));
        call.enqueue(callback);
        return call;
    }
    public static Call sendAtbSummary(Context context, UserModel userModel, SummaryModel summaryModel, Callback<BaseResponse<EmptyResponse>> callback) {
        AtbServices service = ServiceGenerator.with(context).createService(AtbServices.class);
        Call<BaseResponse<EmptyResponse>> call = service.sendAtbSummary(userModel.token, userModel.userId, summaryModel);
        call.enqueue(callback);
        return call;
    }

    public static Call sendAtbActivityDetail(Context context, UserModel userModel, AtbActivityDetailRequest request, Callback<BaseResponse<EmptyResponse>> callback) {
        AtbServices service = ServiceGenerator.with(context).createService(AtbServices.class);
        Call<BaseResponse<EmptyResponse>> call = service.sendAtbActivityDetail(userModel.token, userModel.userId, request);
        call.enqueue(callback);
        return call;
    }

    public static Call sendAtbActivityPayment(Context context, UserModel userModel, AtbPaymentModel atbPaymentModel, Callback<BaseResponse<EmptyResponse>> callback) {
        AtbServices service = ServiceGenerator.with(context).createService(AtbServices.class);
        Call<BaseResponse<EmptyResponse>> call = service.sendAtbActivityPayment(userModel.token, userModel.userId, atbPaymentModel);
        call.enqueue(callback);
        return call;
    }

    public static Call updateProfileInfo(Context context, UserModel userModel, ProfileUpdateRequest request, Callback<BaseResponse<EmptyResponse>> callback) {
        AtbServices service = ServiceGenerator.with(context).createService(AtbServices.class);
        Call<BaseResponse<EmptyResponse>> call = service.updateProfileInfo(userModel.token, userModel.userId, userModel.userName, request);
        call.enqueue(callback);
        return call;
    }

    public static Call updatePasswordInfo(Context context, UserModel userModel, PasswordUpdateRequest request, Callback<BaseResponse<PasswordChangeResponse>> callback) {
        AtbServices service = ServiceGenerator.with(context).createService(AtbServices.class);
        Call<BaseResponse<PasswordChangeResponse>> call = service.updatePasswordInfo(userModel.token, userModel.userId, request);
        call.enqueue(callback);
        return call;
    }

    public static Call forgotPasswordInfo(Context context, ForgotPasswordRequest request, Callback<BaseResponse<UserWithDetail>> callback) {
        AtbServices service = ServiceGenerator.with(context).createService(AtbServices.class);
        Call<BaseResponse<UserWithDetail>> call = service.forgotPasswordInfo(request);
        call.enqueue(callback);
        return call;
    }

    public static Call getAtbSummaryList(Context context, UserModel userModel, Callback<BaseResponse<List<SummaryModel>>> callback) {
        AtbServices service = ServiceGenerator.with(context).createService(AtbServices.class);
        Call<BaseResponse<List<SummaryModel>>> call = service.getAtbSummaryList(userModel.token, userModel.userId, userModel.userName);
        call.enqueue(callback);
        return call;
    }

    public static Call getAtbActivityBySession(Context context, UserModel userModel, int session, Callback<BaseResponse<List<AtbActivityModel>>> callback) {
        AtbServices service = ServiceGenerator.with(context).createService(AtbServices.class);
        Call<BaseResponse<List<AtbActivityModel>>> call = service.getAtbActivityBySession(userModel.userName, session, userModel.token, userModel.userId);
        call.enqueue(callback);
        return call;
    }
}
