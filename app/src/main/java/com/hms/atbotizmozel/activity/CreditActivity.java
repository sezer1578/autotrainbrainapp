package com.hms.atbotizmozel.activity;

import com.google.android.material.textfield.TextInputEditText;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.data.model.UserPaymentModel;
import com.hms.atbotizmozel.util.PrefUtils;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CreditActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "EXTRA_USERNAME";

    @BindView(R.id.logoImg)
    ImageView logoImg;
    @BindView(R.id.nameEdt)
    TextInputEditText nameEdt;
    @BindView(R.id.phoneEdt)
    TextInputEditText phoneEdt;
    @BindView(R.id.mailEdt)
    TextInputEditText mailEdt;
    @BindView(R.id.addressEdt)
    TextInputEditText addressEdt;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        Picasso.with(this).load(R.drawable.logo_transparent).into(logoImg);
        username = getIntent().getStringExtra(EXTRA_USERNAME);
    }

    @OnClick(R.id.confirmBtn)
    void onConfirm() {
        String name = nameEdt.getText().toString().trim();
        String phone = phoneEdt.getText().toString().trim();
        String mail = mailEdt.getText().toString().trim();
        String address = addressEdt.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(address)) {
            Toast.makeText(this, getString(R.string.fill_missings), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            Toast.makeText(this, getString(R.string.incorrect_mail_address), Toast.LENGTH_SHORT).show();
            return;
        }
        UserPaymentModel userPaymentModel = new UserPaymentModel(username, name, phone, mail, address);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra("userPaymentModel", userPaymentModel);
        startActivityForResult(intent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
            case R.id.profile:
                Intent profileIntent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(profileIntent);
                return true;
            case R.id.action_logout:
                PrefUtils.with(CreditActivity.this).savePassword("");
                PrefUtils.with(CreditActivity.this).saveUserName("");
                PrefUtils.with(CreditActivity.this).removeUserModel();
                PrefUtils.with(CreditActivity.this).clear();
                Intent i = new Intent(getBaseContext(),LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
