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
import com.hms.atbotizmozel.data.model.request.ProfileUpdateRequest;
import com.hms.atbotizmozel.data.model.response.BaseResponse;
import com.hms.atbotizmozel.data.model.response.EmptyResponse;
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
import retrofit2.Response;

import static com.hms.atbotizmozel.util.ApplicationData.userModel;

public class ProfileActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "EXTRA_USER";

    @BindView(R.id.userNameEdt)
    EditText userNameEdt;
    @BindView(R.id.emailEdt)
    EditText emailEdt;
    @BindView(R.id.skillEdt)
    EditText skillEdt;
    @BindView(R.id.birthdateEdt)
    EditText birthdateEdt;
    @BindView(R.id.genderEdt)
    AppCompatSpinner genderEdt;
    @BindView(R.id.sessionEdt)
    EditText sessionEdt;
    @BindView(R.id.maxsessionEdt)
    EditText maxsessionEdt;
    @BindView(R.id.helperEdt)
    EditText helperEdt;
    @BindView(R.id.locationEdt)
    EditText locationEdt;

    private UserModel userModel;

    private String[] skills;
    private String[] helper;
    private boolean[] selectedHelper;
    private boolean[] selectedSkills;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));

        TextView pswChange = findViewById(R.id.pswChange);
        pswChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this,PasswordChange.class);
                startActivity(i);
                finish();
            }
        });


        List<String> genders = new ArrayList<>();
        genders.add(getString(R.string.gender));
        genders.add(getString(R.string.man));
        genders.add(getString(R.string.woman));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, genders);
        adapter.setDropDownViewResource(R.layout.item_spinner);
        genderEdt.setAdapter(adapter);

        int ses = getIntent().getIntExtra("session", 0);
        userModel = PrefUtils.with(this).getUserModel();
        if (userModel == null) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
            return;
        }
        userNameEdt.setText(userModel.userName);
        emailEdt.setText(userModel.email);
        skillEdt.setText(userModel.skill);
        birthdateEdt.setText(userModel.birthdate);
        if (userModel.gender != null){
            if (userModel.gender.equals("K")){
                genderEdt.setSelection(2);
            }
            else if (userModel.gender.equals("E")){
                genderEdt.setSelection(1);
            }
            else{
                genderEdt.setSelection(0);
            }
        }
        //genderEdt.setText(userModel.gender);
        sessionEdt.setText(ses + "");
        maxsessionEdt.setText(userModel.maxSession + "");

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
        helperEdt.setText(userModel.subscriptionEndDate);

        locationEdt.setText(userModel.location);
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

    @OnClick(R.id.saveBtn)
    void onSave() {
        int genderSelected = genderEdt.getSelectedItemPosition();
        String gender = genderSelected == 0 ? "" : genderSelected == 1 ? "E" : "K";

        final String email = emailEdt.getText().toString();
        final String skill = skillEdt.getText().toString();
        final String birthdate = birthdateEdt.getText().toString();
        final String session = sessionEdt.getText().toString();
        final String location = locationEdt.getText().toString();
        int maxSession=0;
        String subscriptionStartDate="";
        String subscriptionEndDate="";
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

        DateFormat simple = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        subscriptionStartDate =  userModel.subscriptionStartDate;
        subscriptionEndDate= userModel.subscriptionEndDate;
        Date date1 = new Date();
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(subscriptionEndDate);
        }
        catch(Exception e){}
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.DATE,maxSession);
        Date date2 = cal.getTime();
        subscriptionEndDate= simple.format(date2);
        final String subscriptionEndDate2 = subscriptionEndDate;
        int finalMaxSession = maxSession + userModel.maxSession;
        ProfileUpdateRequest request = new ProfileUpdateRequest(skill, email, gender, birthdate,userModel.maxSession, userModel.subscriptionStartDate, userModel.subscriptionEndDate,location);

        AtbCalls.updateProfileInfo(this, userModel, request, new Callback<BaseResponse<EmptyResponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<EmptyResponse>> call, Response<BaseResponse<EmptyResponse>> response) {
                progressDialog.dismiss();
                if (ErrorUtils.isSuccess(ProfileActivity.this, response)) {
                    Toast.makeText(ProfileActivity.this, getString(R.string.profile_update_success), Toast.LENGTH_SHORT).show();
                    userModel.gender = gender;
                    userModel.email = email;
                    userModel.skill = skill;
                    userModel.birthdate = birthdate;
                    userModel.session = Integer.valueOf(session);
                    userModel.location = location;
                    PrefUtils.with(ProfileActivity.this).saveUserModel(userModel);
                    userModel = PrefUtils.with(ProfileActivity.this).getUserModel();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<EmptyResponse>> call, Throwable t) {
                progressDialog.dismiss();
                ErrorUtils.parseThrowable(ProfileActivity.this, t);
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getBaseContext(), ChildPage.class);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                PrefUtils.with(ProfileActivity.this).savePassword("");
                PrefUtils.with(ProfileActivity.this).saveUserName("");
                PrefUtils.with(ProfileActivity.this).removeUserModel();
                PrefUtils.with(ProfileActivity.this).clear();
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
