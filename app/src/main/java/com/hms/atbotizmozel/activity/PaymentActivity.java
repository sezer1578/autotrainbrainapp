package com.hms.atbotizmozel.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.cooltechworks.creditcarddesign.CreditCardView;
import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.adapter.BaseSpinnerAdapter;
import com.hms.atbotizmozel.data.call.AtbCalls;
import com.hms.atbotizmozel.data.model.AtbPaymentModel;
import com.hms.atbotizmozel.data.model.PaymentModel;
import com.hms.atbotizmozel.data.model.PaymentType;
import com.hms.atbotizmozel.data.model.UserModel;
import com.hms.atbotizmozel.data.model.UserPaymentModel;
import com.hms.atbotizmozel.data.model.VirtualPos;
import com.hms.atbotizmozel.data.model.response.BaseResponse;
import com.hms.atbotizmozel.data.model.response.EmptyResponse;
import com.hms.atbotizmozel.data.soap.MakePaymentAsync;
import com.hms.atbotizmozel.data.soap.PaymentHashAsync;
import com.hms.atbotizmozel.data.soap.PaymentTypesAsync;
import com.hms.atbotizmozel.data.soap.VirtualPosAsync;
import com.hms.atbotizmozel.util.DeviceUtils;
import com.hms.atbotizmozel.util.PrefUtils;
import com.hms.atbotizmozel.util.PriceUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hms.atbotizmozel.activity.PaymentWebViewActivity.EXTRA_URL;

public class PaymentActivity extends AppCompatActivity {

    private static final int ENTER_NEW_CARD = 2;
    private static final int PAYMENT_3D = 3;

    @BindView(R.id.contractTxt)
    TextView contractTxt;
    @BindView(R.id.contractCheckBox)
    CheckBox contractCheckBox;
    @BindView(R.id.bankDetailTxt)
    TextView bankDetailTxt;
    @BindView(R.id.enterCreditCardTxt)
    TextView enterCreditCardTxt;
    @BindView(R.id.creditCardView)
    CreditCardView creditCardView;
    @BindView(R.id.installmentSpinner)
    AppCompatSpinner installmentSpinner;
    @BindView(R.id.installmentLayout)
    LinearLayout installmentLayout;
    @BindView(R.id.makePaymentTxt)
    TextView makePaymentTxt;

    private UserPaymentModel userPaymentModel;
    private PaymentType selectedPaymentType;
    private BaseSpinnerAdapter installmentsAdapter;
    private List<PaymentType> paymentTypes;
    private VirtualPos virtualPos;
    private PaymentModel paymentModel;
    private ProgressDialog progressDialog;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userPaymentModel = (UserPaymentModel) getIntent().getSerializableExtra("userPaymentModel");
        userModel = PrefUtils.with(this).getUserModel();

        setTextClicks();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        makePaymentTxt.setEnabled(false);
        contractCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                makePaymentTxt.setEnabled(isChecked && !TextUtils.isEmpty(creditCardView.getCardNumber()));
            }
        });
    }

    @OnClick(R.id.creditCardLayout)
    void enterCreditCard() {
        Intent intent = new Intent(this, CardEditActivity.class);
        startActivityForResult(intent, ENTER_NEW_CARD);
    }

    private void setTextClicks() {
        int salesStart = 0;
        int salesEnd = 25;
        int preInfoStart = 29;
        int preInfoEnd = 51;
        if (!DeviceUtils.isTurkish()) {
            salesStart = 28;
            salesEnd = 51;
            preInfoStart = 56;
            preInfoEnd = 84;
        }

        SpannableString ss = new SpannableString(getString(R.string.confirm_contracts));
        ClickableSpan salesClick = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(PaymentActivity.this, ContractActivity.class);
                intent.putExtra("isSales", true);
                intent.putExtra("userPaymentModel", userPaymentModel);
                startActivity(intent);
            }
        };
        ss.setSpan(salesClick, salesStart, salesEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan preInformationClick = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(PaymentActivity.this, ContractActivity.class);
                intent.putExtra("isSales", false);
                intent.putExtra("userPaymentModel", userPaymentModel);
                startActivity(intent);
            }
        };
        ss.setSpan(preInformationClick, preInfoStart, preInfoEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        contractTxt.setText(ss);
        contractTxt.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENTER_NEW_CARD && resultCode == RESULT_OK) {
            String cardHolderName = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            String cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
            String expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
            String cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

            enterCreditCardTxt.setVisibility(View.GONE);
            creditCardView.setVisibility(View.VISIBLE);
            creditCardView.setCardHolderName(cardHolderName);
            creditCardView.setCardNumber(cardNumber);
            creditCardView.setCardExpiry(expiry);
            creditCardView.setCVV(cvv);
            loadBank(cardNumber);
        } else if (requestCode == PAYMENT_3D && resultCode == RESULT_OK) {
            boolean success = data.getBooleanExtra(PaymentWebViewActivity.EXTRA_SUCCESS, false);
            if (success) {
                Toast.makeText(this, getString(R.string.payment_success), Toast.LENGTH_SHORT).show();
                progressDialog.show();
                AtbCalls.sendAtbActivityPayment(this, userModel, new AtbPaymentModel(userPaymentModel.username, userPaymentModel.name, userPaymentModel.address,
                        userPaymentModel.mail, userPaymentModel.phone, paymentModel.installment), new Callback<BaseResponse<EmptyResponse>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<EmptyResponse>> call, Response<BaseResponse<EmptyResponse>> response) {
                        progressDialog.dismiss();
                        setResult(RESULT_OK);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<EmptyResponse>> call, Throwable t) {
                        progressDialog.dismiss();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            } else {
                Toast.makeText(this, getString(R.string.payment_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void extractCardFromList(int posId) {
        control:
        for (int i = 0; i < paymentTypes.size(); i++) {
            if (paymentTypes.get(i).posId == posId) {
                selectedPaymentType = paymentTypes.get(i);
                break control;
            }
        }
        if (selectedPaymentType != null) {
            loadInstallments();
        }
    }

    private void loadBank(String cardNumber) {
        String bin = cardNumber.substring(0, 6);
        VirtualPosAsync virtualPosAsync = new VirtualPosAsync(this, bin);
        virtualPosAsync.registerListener(Integer.parseInt(bin), new Loader.OnLoadCompleteListener<VirtualPos>() {
            @Override
            public void onLoadComplete(Loader<VirtualPos> loader, VirtualPos data) {
                if (data != null) {
                    virtualPos = data;
                    loadCards(data.virtualPosId);
                }
            }
        });
        virtualPosAsync.startLoading();
    }

    private void loadCards(final int posId) {
        if (paymentTypes == null || paymentTypes.size() == 0) {
            PaymentTypesAsync paymentTypesAsync = new PaymentTypesAsync(this);
            paymentTypesAsync.registerListener(0, new Loader.OnLoadCompleteListener<List<PaymentType>>() {
                @Override
                public void onLoadComplete(Loader<List<PaymentType>> loader, List<PaymentType> data) {
                    if (data.size() > 0) {
                        paymentTypes = data;
                        extractCardFromList(posId);
                    }
                }
            });
            paymentTypesAsync.startLoading();
        } else {
            extractCardFromList(posId);
        }
    }

    private void loadInstallments() {
        List<String> installmentNames = new ArrayList<>();

        selectedPaymentType.installmentRates.clear();
        selectedPaymentType.installmentCounts.clear();
        String[] installmentTitles = getResources().getStringArray(R.array.installments_array);
        float[] installmentRates = new float[]{selectedPaymentType.month1Rate, selectedPaymentType.month2Rate, selectedPaymentType.month3Rate, selectedPaymentType.month4Rate,
                selectedPaymentType.month5Rate, selectedPaymentType.month6Rate, selectedPaymentType.month7Rate, selectedPaymentType.month8Rate,
                selectedPaymentType.month9Rate, selectedPaymentType.month10Rate, selectedPaymentType.month11Rate, selectedPaymentType.month12Rate};
        for (int i = 0; i < installmentRates.length; i++) {
            if (installmentRates[i] >= 0) {
                installmentNames.add(installmentTitles[i]);
                selectedPaymentType.installmentRates.add(installmentRates[i]);
                selectedPaymentType.installmentCounts.add(i + 1);
            }
        }
        installmentsAdapter = new BaseSpinnerAdapter(PaymentActivity.this,
                R.layout.item_spinner, installmentNames, null);
        installmentSpinner.setAdapter(installmentsAdapter);

        if (installmentNames.size() > 0) {
            bankDetailTxt.setText(getString(R.string.installment_options, virtualPos.cardBank));
            installmentLayout.setVisibility(View.VISIBLE);
            makePaymentTxt.setEnabled(contractCheckBox.isChecked());
        } else {
            Toast.makeText(this, getString(R.string.credit_card_error), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.makePaymentTxt)
    void makePaymentClick() {
        String cardHolderName = creditCardView.getCardHolderName();
        String cardNumber = creditCardView.getCardNumber();
        String expiry = creditCardView.getExpiry();
        String cvv = creditCardView.getCVV();
        if (TextUtils.isEmpty(cardHolderName) || TextUtils.isEmpty(cardNumber) ||
                TextUtils.isEmpty(expiry) || TextUtils.isEmpty(cvv)) {
            Toast.makeText(this, getString(R.string.enter_credit_card), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!contractCheckBox.isChecked()) {
            Toast.makeText(this, getString(R.string.check_contracts), Toast.LENGTH_SHORT).show();
            return;
        }
        String[] splitted = expiry.split("/");
        paymentModel = new PaymentModel(virtualPos.virtualPosId, selectedPaymentType.installmentCounts.get(installmentSpinner.getSelectedItemPosition()),
                getCalculatedOperationAmount(4200), "4200,00", System.currentTimeMillis() / 1000, getString(R.string.payment_error_url), getString(R.string.payment_success_url),
                cardHolderName, cardNumber, splitted[0], "20" + splitted[1], cvv, userPaymentModel.phone, "");
        getHash();
    }

    private void getHash() {
        progressDialog.show();
        PaymentHashAsync paymentHashAsync = new PaymentHashAsync(this, paymentModel);
        paymentHashAsync.registerListener(0, new Loader.OnLoadCompleteListener<String>() {
            @Override
            public void onLoadComplete(Loader<String> loader, String data) {
                if (data != null) {
                    paymentModel.operationHash = data;
                    makePayment();
                } else {
                    progressDialog.dismiss();
                }
            }
        });
        paymentHashAsync.startLoading();
    }

    private void makePayment() {
        MakePaymentAsync makePaymentAsync = new MakePaymentAsync(this, paymentModel);
        makePaymentAsync.registerListener(0, new Loader.OnLoadCompleteListener<String>() {
            @Override
            public void onLoadComplete(Loader<String> loader, String data) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (data != null && !data.equals("anyType{}")) {
                    Intent intent = new Intent(PaymentActivity.this, PaymentWebViewActivity.class);
                    intent.putExtra(EXTRA_URL, data);
                    startActivityForResult(intent, PAYMENT_3D);
                } else {
                    Toast.makeText(PaymentActivity.this, getString(R.string.credit_card_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
        makePaymentAsync.startLoading();
    }

    private String getCalculatedOperationAmount(float totalAmount) {
        float installmentRate = selectedPaymentType.installmentRates.get(installmentSpinner.getSelectedItemPosition());
        float operationAmount = 100 * totalAmount / (installmentRate + 100f);
        return PriceUtils.formatted(operationAmount);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
