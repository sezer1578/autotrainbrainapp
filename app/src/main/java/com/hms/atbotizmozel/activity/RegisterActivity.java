package com.hms.atbotizmozel.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.data.call.AtbCalls;
import com.hms.atbotizmozel.data.model.UserModel;
import com.hms.atbotizmozel.data.model.UserWithDetail;
import com.hms.atbotizmozel.data.model.request.RegisterRequest;
import com.hms.atbotizmozel.data.model.response.BaseResponse;
import com.hms.atbotizmozel.util.ErrorUtils;
import com.hms.atbotizmozel.util.PrefUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class
RegisterActivity extends AppCompatActivity {

    @BindView(R.id.genderSpinner)
    AppCompatSpinner genderSpinner;
    @BindView(R.id.userNameEdt)
    EditText userNameEdt;
    @BindView(R.id.emailEdt)
    EditText emailEdt;
    @BindView(R.id.passwordEdt)
    EditText passwordEdt;
    @BindView(R.id.skillEdt)
    TextView skillEdt;
    @BindView(R.id.helperEdt)
    TextView helperEdt;
    @BindView(R.id.birthdateEdt)
    TextView birthdateEdt;
    @BindView(R.id.locationEdt)
    EditText locationEdt;

    private String[] skills;
    private String[] helper;
    private boolean[] selectedHelper;
    private boolean[] selectedSkills;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));

        List<String> genders = new ArrayList<>();
        genders.add(getString(R.string.gender));
        genders.add(getString(R.string.man));
        genders.add(getString(R.string.woman));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, genders);
        adapter.setDropDownViewResource(R.layout.item_spinner);
        genderSpinner.setAdapter(adapter);

        skills = getResources().getStringArray(R.array.skills_arrays);
        selectedSkills = new boolean[skills.length];
        for (int i = 0; i < selectedSkills.length; i++) {
            selectedSkills[i] = false;
        }
        helper = getResources().getStringArray(R.array.helper_arrays);
        selectedHelper = new boolean[helper.length];
        for (int i = 0; i < selectedHelper.length; i++) {
            selectedHelper[i] = false;
        }
    }

    @OnClick(R.id.birthdateEdt)
    void onPickDateClick() {
        closeKeyboard();
        DatePickerDialog dialog = new DatePickerDialog(this, dateSetListener, 1990, 10, 10);
        dialog.show();
    }

    private final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            birthdateEdt.setText(sdf.format(c.getTime()));
        }
    };

    @OnClick(R.id.skillEdt)
    void onSkillClick() {
        closeKeyboard();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.hint_skill));
        builder.setMultiChoiceItems(skills, selectedSkills, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                selectedSkills[which] = isChecked;
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                String skillsStr = "";
                for (int i = 0; i < selectedSkills.length; i++) {
                    if (selectedSkills[i]) {
                        if (TextUtils.isEmpty(skillsStr))
                            skillsStr += skills[i];
                        else
                            skillsStr += " - " + skills[i];
                    }
                }
                skillEdt.setText(skillsStr);
            }
        });
        builder.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String skillsStr = "";
                for (int i = 0; i < selectedSkills.length; i++) {
                    if (selectedSkills[i]) {
                        if (TextUtils.isEmpty(skillsStr))
                            skillsStr += skills[i];
                        else
                            skillsStr += " - " + skills[i];
                    }
                }
                skillEdt.setText(skillsStr);
            }
        });
        builder.create().show();
    }

    @OnClick(R.id.helperEdt)
    void onHelperClick() {
        closeKeyboard();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.hint_help));
        builder.setMultiChoiceItems(helper, selectedHelper, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                selectedHelper[which] = isChecked;
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                String helperStr = "";
                for (int i = 0; i < selectedHelper.length; i++) {
                    if (selectedHelper[i]) {
                        if (TextUtils.isEmpty(helperStr))
                            helperStr += helper[i];
                        else
                            helperStr += " - " + helper[i];
                    }
                }
                helperEdt.setText(helperStr);
            }
        });
        builder.setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String helperStr = "";
                for (int i = 0; i < selectedHelper.length; i++) {
                    if (selectedHelper[i]) {
                        if (TextUtils.isEmpty(helperStr))
                            helperStr += helper[i];
                        else
                            helperStr += " - " + helper[i];
                    }
                }
                helperEdt.setText(helperStr);
            }
        });
        builder.create().show();
    }

    @OnClick(R.id.registerBtn)
    void onRegister() {
        int genderSelected = genderSpinner.getSelectedItemPosition();
        String gender = genderSelected == 0 ? "" : genderSelected == 1 ? "E" : "K";
        String skill = skillEdt.getText().toString();
        String helper = helperEdt.getText().toString();
        String birthdate = birthdateEdt.getText().toString();
        int maxSession=0;
        String subscriptionStartDate="";
        String subscriptionEndDate="";

        final String username = userNameEdt.getText().toString();
        final String email = emailEdt.getText().toString();
        final String password = passwordEdt.getText().toString();
        final String location = locationEdt.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email) ||TextUtils.isEmpty(birthdate) ||TextUtils.isEmpty(location)) {
            Toast.makeText(this, getString(R.string.fill_missings), Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();
        for (int i = 0; i < selectedHelper.length; i++) {
            if (selectedHelper[i]) {
                if (i == 0)
                    maxSession = 30;
                else if (i == 1)
                    maxSession = 60;
                else if (i == 2)
                    maxSession = 90;
                else if (i == 3)
                    maxSession = 180;
                else if (i == 4)
                    maxSession = 365;
                else maxSession = 10;
            }
        }
        maxSession=0;
        DateFormat simple = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        long  milli =  Calendar.getInstance().getTimeInMillis();
        Date result = new Date(milli);
        subscriptionStartDate =  simple.format(result);
        double x = 8.64e+7;
        double millis =  Calendar.getInstance().getTimeInMillis() + x* maxSession ;
        long miliSec = (long) millis;

        Date result2 = new Date(miliSec);
        subscriptionEndDate = simple.format(result2);
        RegisterRequest request = new RegisterRequest(username, email, password, skill, gender, birthdate, maxSession, subscriptionStartDate,subscriptionEndDate,location);
        int finalMaxSession = maxSession;
        String subscriptionEndDate2 = subscriptionEndDate;
        AtbCalls.register(this, request, new Callback<BaseResponse<UserWithDetail>>() {
            @Override
            public void onResponse(Call<BaseResponse<UserWithDetail>> call, retrofit2.Response<BaseResponse<UserWithDetail>> response) {
                progressDialog.dismiss();
                if (ErrorUtils.isSuccess(RegisterActivity.this, response)) {
                    UserWithDetail userWithDetail = response.body().data;

                    UserModel userModel = new UserModel(userWithDetail);
                    userModel.session = 0;
                    userModel.maxSession = finalMaxSession;
                    userModel.subscriptionEndDate= subscriptionEndDate2;
                    PrefUtils.with(RegisterActivity.this).saveUserModel(userModel);


                    //if (userModel.session > userModel.maxSession) {
                    //    Intent intent = new Intent(getBaseContext(), PaymentActivity.class);
                    //    intent.putExtra(PaymentActivity.EXTRA_USERNAME, username);
                    //    startActivity(intent);
                    //   return;
                    //}

                    Intent intent = new Intent(getBaseContext(), ChildPage.class);
                    startActivity(intent);
                    finish();
                }
            }


            @Override
            public void onFailure(Call<BaseResponse<UserWithDetail>> call, Throwable t) {
                progressDialog.dismiss();
                ErrorUtils.parseThrowable(RegisterActivity.this, t);
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
}
