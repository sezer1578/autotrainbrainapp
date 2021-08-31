package com.hms.atbotizmozel.activity;


import com.google.android.material.textfield.TextInputEditText;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.data.call.AtbCalls;
import com.hms.atbotizmozel.data.model.UserModel;
import com.hms.atbotizmozel.data.model.UserWithDetail;
import com.hms.atbotizmozel.data.model.request.LoginRequest;
import com.hms.atbotizmozel.data.model.response.BaseResponse;
import com.hms.atbotizmozel.util.ErrorUtils;
import com.hms.atbotizmozel.util.PrefUtils;
//import com.microsoft.appcenter.AppCenter;
//import com.microsoft.appcenter.analytics.Analytics;
//import com.microsoft.appcenter.crashes.Crashes;
//import com.microsoft.appcenter.distribute.Distribute;
//import com.microsoft.appcenter.distribute.UpdateAction;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.userNameEdt)
    TextInputEditText userNameEdt;
    @BindView(R.id.passwordEdt)
    TextInputEditText passwordEdt;
    @BindView(R.id.registerTxt)
    TextView registerTxt;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        passwordEdt.setOnTouchListener(View::onTouchEvent);

        TextView pswForget = findViewById(R.id.pswForgetTxt);
        pswForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(intent);
            }
        });

        init();
        PrefUtils.with(LoginActivity.this).removeUserModel();
/*
        Distribute.setListener((activity, releaseDetails) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle(getString(R.string.app_name));
            builder.setMessage(getString(R.string.app_update_message));
            builder.setPositiveButton(getString(R.string.update), (dialog, which) -> {
                dialog.dismiss();
                Distribute.notifyUpdateAction(UpdateAction.UPDATE);
            });
            builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> {
                dialog.dismiss();
            });
            builder.create().show();
            return true;
        });
        Distribute.setEnabledForDebuggableBuild(true);
        Distribute.setEnabled(true);
        AppCenter.start(getApplication(), "16e5ce9a-aaad-477c-a552-ce50924fe34a", Analytics.class, Crashes.class, Distribute.class);
    */
    }
    private void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
    }

    @OnClick(R.id.registerTxt)
    void onRegisterClick() {
        Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.loginBtn)
    void onLoginClick() {
        final String userName = userNameEdt.getText().toString();
        final String password = passwordEdt.getText().toString();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, getString(R.string.fill_missings), Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();
        LoginRequest request = new LoginRequest(userName, password);
        AtbCalls.login(this, request, new Callback<BaseResponse<UserWithDetail>>() {
            @Override
            public void onResponse(Call<BaseResponse<UserWithDetail>> call, retrofit2.Response<BaseResponse<UserWithDetail>> response) {
                progressDialog.dismiss();
                if (ErrorUtils.isSuccess(LoginActivity.this, response)) {
                    UserWithDetail userWithDetail = response.body().data;

                    UserModel userModel = new UserModel(userWithDetail);
                    PrefUtils.with(LoginActivity.this).saveUserModel(userModel);
                    PrefUtils.with(LoginActivity.this).saveUserModel(userModel);
                    PrefUtils.with(LoginActivity.this).savePassword(password);
                    PrefUtils.with(LoginActivity.this).saveUserName(userName);
                    /*if (userModel.session >= userModel.maxSession) {
                        Intent intent = new Intent(getBaseContext(), ChildPage.class);
                        startActivity(intent);
                        return;
                    }*/

                    Intent intent = new Intent(getBaseContext(), ParentChild.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<UserWithDetail>> call, Throwable t) {
                progressDialog.dismiss();
                ErrorUtils.parseThrowable(LoginActivity.this, t);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
