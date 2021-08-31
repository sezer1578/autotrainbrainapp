package com.hms.atbotizmozel.socializationwords;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.activity.AddOptions;
import com.hms.atbotizmozel.activity.EmotionLearning;
import com.hms.atbotizmozel.activity.Learn;
import com.hms.atbotizmozel.activity.LoginActivity;
import com.hms.atbotizmozel.activity.MainActivity;
import com.hms.atbotizmozel.activity.ProfileActivity;
import com.hms.atbotizmozel.activity.RelativeEmotionLearn;
import com.hms.atbotizmozel.activity.inPlay;
import com.hms.atbotizmozel.persistence.Emotion;
import com.hms.atbotizmozel.persistence.Member;
import com.hms.atbotizmozel.persistencehelpers.EmotionViewModel;
import com.hms.atbotizmozel.persistencehelpers.MemberViewModel;
import com.hms.atbotizmozel.util.PrefUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ChooseProcess extends AppCompatActivity {


    CardView fami_rec, learn_emo, soci_words, special_edu, relative_emo;
    TextView famf, famE, emot, Soci, read1, learn;
    TextToSpeech textToSpeech;
    private List<Member> memberList = new ArrayList<>();
    private List<Emotion> relEmotionList = new ArrayList<>();
    private MemberViewModel memberViewModel;
    private EmotionViewModel emotionViewModel;
    private Integer x, y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_page);

        fami_rec = findViewById(R.id.FamFaces);
        learn_emo = findViewById(R.id.Emos);
        soci_words = findViewById(R.id.Socilaze);
        special_edu = findViewById(R.id.read);
        relative_emo = findViewById(R.id.FamEmos);
        famf = findViewById(R.id.famf);
        famE = findViewById(R.id.famE);
        emot = findViewById(R.id.emot);
        Soci = findViewById(R.id.Soci);
        read1 = findViewById(R.id.read1);
        learn = findViewById(R.id.learn_title);

        emotionViewModel = ViewModelProviders.of(this).get(EmotionViewModel.class);
        emotionViewModel.getmAllEmotion().observe(ChooseProcess.this, new Observer<List<Emotion>>() {
            @Override
            public void onChanged(List<Emotion> emotions) {
                relEmotionList.clear();
                relEmotionList.addAll(emotions);
                y = relEmotionList.size();
            }
        });

        memberViewModel = ViewModelProviders.of(this).get(MemberViewModel.class);
        memberViewModel.getmAllMember().observe(ChooseProcess.this, new Observer<List<Member>>() {

            @Override
            public void onChanged(@Nullable final List<Member> members) {

                memberList.clear();
                memberList.addAll(members);
                x = memberList.size();
            }

        });
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                if (i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                }

            }
        });
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = learn.getText().toString();
                textToSpeech.speak(getText, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        fami_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = famf.getText().toString();
                textToSpeech.speak(getText, TextToSpeech.QUEUE_FLUSH, null);
                if (x >= 1) {
                    Intent intent = new Intent(ChooseProcess.this, Learn.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ChooseProcess.this, getResources().getString(R.string.at_least_1_relatives_needed), Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(ChooseProcess.this, com.hms.atbotizmozel.activity.settings.class);
                    startActivity(intent1);
                }

            }
        });
        learn_emo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = emot.getText().toString();
                textToSpeech.speak(getText, TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(ChooseProcess.this, EmotionLearning.class);
                startActivity(intent);
            }
        });
        soci_words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = Soci.getText().toString();
                textToSpeech.speak(getText, TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(ChooseProcess.this, LearningPhase.class);
                startActivity(intent);
            }
        });

        special_edu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = read1.getText().toString();
                textToSpeech.speak(getText, TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(ChooseProcess.this, MainActivity.class);
                startActivity(intent);
            }
        });
        relative_emo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = famE.getText().toString();
                textToSpeech.speak(getText, TextToSpeech.QUEUE_FLUSH, null);
                if (y >= 1) {
                    Intent intent = new Intent(ChooseProcess.this, RelativeEmotionLearn.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ChooseProcess.this, getResources().getString(R.string.at_least_1_relatives_needed), Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(ChooseProcess.this, com.hms.atbotizmozel.activity.RelativeEmotionsList.class);
                    startActivity(intent1);
                }

            }

        });
        famE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = famE.getText().toString();
                textToSpeech.speak(getText, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        read1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = read1.getText().toString();
                textToSpeech.speak(getText, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        Soci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = Soci.getText().toString();
                textToSpeech.speak(getText, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        emot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = emot.getText().toString();
                textToSpeech.speak(getText, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        famf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = famf.getText().toString();
                textToSpeech.speak(getText, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parent_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_logout:
                PrefUtils.with(ChooseProcess.this).savePassword("");
                PrefUtils.with(ChooseProcess.this).saveUserName("");
                PrefUtils.with(ChooseProcess.this).removeUserModel();
                PrefUtils.with(ChooseProcess.this).clear();
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