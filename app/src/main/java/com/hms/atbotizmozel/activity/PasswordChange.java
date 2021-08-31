package com.hms.atbotizmozel.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.data.call.AtbCalls;
import com.hms.atbotizmozel.data.model.request.PasswordUpdateRequest;
import com.hms.atbotizmozel.data.model.response.BaseResponse;
import com.hms.atbotizmozel.data.model.response.PasswordChangeResponse;
import com.hms.atbotizmozel.util.ErrorUtils;
import com.hms.atbotizmozel.util.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hms.atbotizmozel.util.ApplicationData.userModel;

public class PasswordChange extends AppCompatActivity {

    @BindView(R.id.old_passwordEdt)
    EditText oldPsw;
    @BindView(R.id.new_passwordEdt)
    EditText newPsw;
    @BindView(R.id.new_passwordEdt2)
    EditText newPswAgain;


    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        userModel = PrefUtils.with(this).getUserModel();

        TextView title = findViewById(R.id.title);
        ImageView backIcon = findViewById(R.id.back);

        title.setText(R.string.app_name);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));

        Button pswBtn = findViewById(R.id.pswBtn);
        pswBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String passwordOld = oldPsw.getText().toString();
                final String password = newPsw.getText().toString();
                final String passwordAgain = newPswAgain.getText().toString();
                String oldPassword = PrefUtils.with(PasswordChange.this).getPassword();

                if (passwordOld.equals(oldPassword) && password.equals(passwordAgain)
                        && passwordOld != null && passwordOld.length() > 0
                        && password != null && password.length() > 0
                        && passwordAgain != null && passwordAgain.length() > 0
                        && !passwordOld.equals(password) ) {

                    progressDialog.show();

                    PasswordUpdateRequest request = new PasswordUpdateRequest(userModel.userName, password, passwordOld);

                    AtbCalls.updatePasswordInfo(PasswordChange.this, userModel, request, new Callback<BaseResponse<PasswordChangeResponse>>() {
                        @Override
                        public void onResponse(retrofit2.Call<BaseResponse<PasswordChangeResponse>> call, Response<BaseResponse<PasswordChangeResponse>> response) {

                            progressDialog.dismiss();
                            //if (ErrorUtils.isSuccess(PasswordChange.this, response)) {
                            if (response.isSuccessful()) {
                                Toast.makeText(PasswordChange.this, getString(R.string.password_update_success), Toast.LENGTH_SHORT).show();
                                userModel.password = password;
                                PrefUtils.with(PasswordChange.this).savePassword(password);
                                PrefUtils.with(PasswordChange.this).saveUserModel(userModel);
                            }
                            else{
                                if(response.code() == 500)
                                {
                                    Toast.makeText(PasswordChange.this, getString(R.string.passError1), Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(PasswordChange.this, getString(R.string.error_unexpected), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse<PasswordChangeResponse>> call, Throwable t) {
                            progressDialog.dismiss();
                            ErrorUtils.parseThrowable(PasswordChange.this, t);
                        }
                    });
                } else {
                    if(passwordOld.length() == 0 || password.length() == 0 || passwordAgain.length() == 0){
                        Toast.makeText(PasswordChange.this, getString(R.string.fill_missings), Toast.LENGTH_SHORT).show();
                    }
                    else if (!passwordOld.equals(oldPassword)) {
                        Toast.makeText(PasswordChange.this, getString(R.string.passError1), Toast.LENGTH_SHORT).show();
                    }
                    else if (!password.equals(passwordAgain)) {
                        Toast.makeText(PasswordChange.this, getString(R.string.passError2), Toast.LENGTH_SHORT).show();
                    }
                    else if (passwordOld.equals(password)) {
                        Toast.makeText(PasswordChange.this, getString(R.string.passError3), Toast.LENGTH_SHORT).show();}
                }
            }
        });
    }

    public void closeKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    public void onBackPressed() {

    }
}

