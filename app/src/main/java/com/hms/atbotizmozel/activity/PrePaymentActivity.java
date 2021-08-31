package com.hms.atbotizmozel.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.hms.atbotizmozel.R;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrePaymentActivity extends AppCompatActivity {

    @BindView(R.id.logoImg)
    ImageView logoImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_payment);
        ButterKnife.bind(this);

        init();
    }

    @Override
    public void onBackPressed() {

    }

    private void init() {
        Picasso.with(this).load(R.drawable.logo_transparent).into(logoImg);
    }

    @OnClick(R.id.directBtn)
    void onDirect() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.autotrainbrain.com/#price-table"));
        startActivity(intent);
    }
}
