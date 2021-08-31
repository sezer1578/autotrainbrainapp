package com.hms.atbotizmozel.activity;

import com.google.android.material.textfield.TextInputEditText;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.util.PrefUtils;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ReportBuyActivity extends AppCompatActivity {

    @BindView(R.id.logoImg)
    ImageView logoImg;
    @BindView(R.id.sessionEdt)
    TextInputEditText sessionEdt;

    int session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        init();
        session = getIntent().getIntExtra("session", 0);
        sessionEdt.setText(session + "");
    }

    private void init() {
        Picasso.with(this).load(R.drawable.logo_transparent).into(logoImg);
    }

    @OnClick(R.id.confirmReportBtn)
    void onConfirm() {
        String sessionInput = sessionEdt.getText().toString().trim();
        if (TextUtils.isEmpty(sessionInput)) {
            Toast.makeText(this, getString(R.string.fill_missings), Toast.LENGTH_SHORT).show();
            return;
        }
        if (Integer.valueOf(sessionInput) > session) {
            Toast.makeText(this, getString(R.string.bigger_session), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, ShowReportActivity.class);
        intent.putExtra("session", Integer.valueOf(sessionInput));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.profile:
                Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_logout:
                PrefUtils.with(ReportBuyActivity.this).savePassword("");
                PrefUtils.with(ReportBuyActivity.this).saveUserName("");
                PrefUtils.with(ReportBuyActivity.this).removeUserModel();
                PrefUtils.with(ReportBuyActivity.this).clear();
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
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
