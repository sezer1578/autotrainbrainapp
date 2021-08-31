package com.hms.atbotizmozel.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.data.call.AtbCalls;
import com.hms.atbotizmozel.data.model.UserWithDetail;
import com.hms.atbotizmozel.data.model.request.ForgotPasswordRequest;
import com.hms.atbotizmozel.data.model.response.BaseResponse;
import com.hms.atbotizmozel.util.ErrorUtils;
import com.hms.atbotizmozel.util.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {

    @BindView(R.id.userNameEdt)
    TextInputEditText userNameEdt;
    @BindView(R.id.sendBtn)
    Button sendBtn;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
    }

    @OnClick(R.id.sendBtn)
    void onSendClick() {
        String curUsername = PrefUtils.with(ForgotPassword.this).getUserName();
        final String userName = userNameEdt.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, getString(R.string.fill_missings), Toast.LENGTH_SHORT).show();
            return;
        }
        ForgotPasswordRequest request = new ForgotPasswordRequest(userName);

        AtbCalls.forgotPasswordInfo(this, request, new Callback<BaseResponse<UserWithDetail>>() {
            @Override
            public void onResponse(retrofit2.Call<BaseResponse<UserWithDetail>> call, Response<BaseResponse<UserWithDetail>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Intent intent = new Intent(ForgotPassword.this,mailSent.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<UserWithDetail>> call, Throwable t) {
                progressDialog.dismiss();
                ErrorUtils.parseThrowable(ForgotPassword.this, t);
            }
        });
    }
    @Override
    public void onBackPressed() {

    }
}