package com.hms.atbotizmozel.data.service;

import com.hms.atbotizmozel.data.ServiceGenerator;
import com.hms.atbotizmozel.data.model.AtbActivityModel;
import com.hms.atbotizmozel.data.model.AtbPaymentModel;
import com.hms.atbotizmozel.data.model.SummaryModel;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Batuhan on 17.11.2017.
 */

public interface AtbServices {

    @POST(ServiceGenerator.API_ADRESS + "/login")
    Call<BaseResponse<UserWithDetail>> login(@Body LoginRequest request);

    @POST(ServiceGenerator.API_ADRESS + "/signup")
    Call<BaseResponse<UserWithDetail>> register(@Body RegisterRequest request);

    @POST(ServiceGenerator.API_ADRESS + "/atbActivity/totalSession/byUsername/{username}/{uid}/{token}")
    Call<BaseResponse<TotalSessionResponse>> getTotalSession(@Path("username") String username,
                                                             @Path("token") String token,
                                                             @Path("uid") String userId);



    @POST(ServiceGenerator.API_ADRESS + "/atbActivity/sendList/{uid}/{token}")
    Call<BaseResponse<EmptyResponse>> sendAtbActvitiyList(@Path("token") String token,
                                                                 @Path("uid") String userId,
                                                                 @Body SendAtbActivityListRequest request);

    @POST(ServiceGenerator.API_ADRESS + "/atbEntropy/sendList/{uid}/{token}")
    Call<BaseResponse<EmptyResponse>> sendAtbEntropyList(@Path("token") String token,
                                                          @Path("uid") String userId,
                                                          @Body SendAtbEntropyListRequest request);

    @POST(ServiceGenerator.API_ADRESS + "/atbActivitySummary/{uid}/{token}")
    Call<BaseResponse<EmptyResponse>> sendAtbSummary(@Path("token") String token,
                                                     @Path("uid") String userId,
                                                     @Body SummaryModel summaryModel);

    @POST(ServiceGenerator.API_ADRESS + "/atbActivityDetail/{uid}/{token}")
    Call<BaseResponse<EmptyResponse>> sendAtbActivityDetail(@Path("token") String token,
                                                            @Path("uid") String userId,
                                                            @Body AtbActivityDetailRequest request);

    @POST(ServiceGenerator.API_ADRESS + "/atbActivityPayment/{uid}/{token}")
    Call<BaseResponse<EmptyResponse>> sendAtbActivityPayment(@Path("token") String token,
                                                             @Path("uid") String userId,
                                                             @Body AtbPaymentModel atbPaymentModel);

    @PUT(ServiceGenerator.API_ADRESS + "/user/patient/updateUserInfo/{username}/{uid}/{token}")
    Call<BaseResponse<EmptyResponse>> updateProfileInfo(@Path("token") String token,
                                                        @Path("uid") String userId,
                                                        @Path("username") String username,
                                                        @Body ProfileUpdateRequest request);

    @POST(ServiceGenerator.API_ADRESS + "/changePass/{uid}/{token}")
    Call<BaseResponse<PasswordChangeResponse>> updatePasswordInfo(@Path("token") String token,
                                                                  @Path("uid") String userId,
                                                                  @Body PasswordUpdateRequest request);

    @POST(ServiceGenerator.API_ADRESS + "/forgotPassword")
    Call<BaseResponse<UserWithDetail>> forgotPasswordInfo(@Body ForgotPasswordRequest request);

    @GET(ServiceGenerator.API_ADRESS + "/atbActivitySummary/getList/byUsername/{username}/{uid}/{token}")
    Call<BaseResponse<List<SummaryModel>>> getAtbSummaryList(@Path("token") String token,
                                                             @Path("uid") String userId,
                                                             @Path("username") String username);

    @GET(ServiceGenerator.API_ADRESS + "/atbActivity/getList/byUsername/{username}/bySession/{session}/{uid}/{token}")
    Call<BaseResponse<List<AtbActivityModel>>> getAtbActivityBySession(@Path("username") String userName,
                                                                       @Path("session") int session,
                                                                       @Path("token") String token,
                                                                       @Path("uid") String userId);
}
