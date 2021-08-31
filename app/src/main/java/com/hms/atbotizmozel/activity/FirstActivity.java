package com.hms.atbotizmozel.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.data.call.AtbCalls;
import com.hms.atbotizmozel.data.model.UserModel;
import com.hms.atbotizmozel.data.model.UserWithDetail;
import com.hms.atbotizmozel.data.model.request.LoginRequest;
import com.hms.atbotizmozel.data.model.response.BaseResponse;
import com.hms.atbotizmozel.util.ApplicationData;
import com.hms.atbotizmozel.util.ErrorUtils;
import com.hms.atbotizmozel.util.PrefUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;

import static com.hms.atbotizmozel.util.ApplicationData.userModel;


public class FirstActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        String userName = PrefUtils.with(FirstActivity.this).getUserName();
        String password = PrefUtils.with(FirstActivity.this).getPassword();

        if (userName != null && userName.length() > 0
                && password != null && password.length() > 0){

            LoginRequest request = new LoginRequest(userName, password);
            AtbCalls.login(this, request, new Callback<BaseResponse<UserWithDetail>>() {
                @Override
                public void onResponse(Call<BaseResponse<UserWithDetail>> call, retrofit2.Response<BaseResponse<UserWithDetail>> response) {
                    if (ErrorUtils.isSuccess(FirstActivity.this, response)) {
                        UserWithDetail userWithDetail = response.body().data;

                        userModel = new UserModel(userWithDetail);
                        PrefUtils.with(FirstActivity.this).saveUserModel(userModel);

                        Date date1 = java.util.Calendar.getInstance().getTime();
                        try {
                            date1=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(userModel.subscriptionEndDate);
                        }
                        catch(Exception e) {}
                        Date date2=java.util.Calendar.getInstance().getTime();

                       /* if (userModel.session >= userModel.maxSession || date1.compareTo(date2) ==-1)  {

                            Intent intent = new Intent(getBaseContext(), PrePaymentActivity.class);
                            startActivity(intent);
                            return;
                        }*/

                        Intent intent = new Intent(getBaseContext(), ParentChild.class);
                        ApplicationData.intentHeadset = intent;
                        startActivity(intent);
                        finish();
                    }
                    else{
                        gotoLogin();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<UserWithDetail>> call, Throwable t) {
                    gotoLogin();
                }
            });

        }
        else{
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){
                    gotoLogin();
                }

            },SPLASH_TIME_OUT);
        }
    }

    void gotoLogin()
    {
        Intent loginIntent = new Intent(FirstActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}