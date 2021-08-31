package com.hms.atbotizmozel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.data.model.UserPaymentModel;
import com.hms.atbotizmozel.util.AssetUtils;
import com.hms.atbotizmozel.util.PrefUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ContractActivity extends AppCompatActivity {

    @BindView(R.id.contractTxt)
    TextView contractTxt;

    private String contractContent;
    private UserPaymentModel userPaymentModel;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userPaymentModel = (UserPaymentModel) getIntent().getSerializableExtra("userPaymentModel");
        if (getIntent().getBooleanExtra("isSales", false)) {
            contractContent = AssetUtils.getSalesContractContent(this);
            getSupportActionBar().setTitle(getString(R.string.sales_contract));
        } else {
            contractContent = AssetUtils.getPreInfoContractContent(this);
            getSupportActionBar().setTitle(getString(R.string.pre_info_contract));
        }

        contractTxt.setText(Html.fromHtml(contractContent.replace("<USERNAME>", userPaymentModel.username).replace("<NAME-SURNAME>", userPaymentModel.name)
                .replace("<ADDRESS>", userPaymentModel.address).replace("<EMAIL>", userPaymentModel.mail)
                .replace("<DATE>", simpleDateFormat.format(new Date()))));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.profile:
                Intent profileIntent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(profileIntent);
                return true;
            case R.id.action_logout:
                PrefUtils.with(ContractActivity.this).savePassword("");
                PrefUtils.with(ContractActivity.this).saveUserName("");
                PrefUtils.with(ContractActivity.this).removeUserModel();
                PrefUtils.with(ContractActivity.this).clear();
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
