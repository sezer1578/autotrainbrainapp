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
import com.hms.atbotizmozel.activity.EmotionOptions;
import com.hms.atbotizmozel.activity.FamilyGame;
import com.hms.atbotizmozel.activity.LoginActivity;
import com.hms.atbotizmozel.activity.MainActivity;
import com.hms.atbotizmozel.activity.ProfileActivity;
import com.hms.atbotizmozel.activity.RelativeEmotionGame;
import com.hms.atbotizmozel.activity.inPlay;
import com.hms.atbotizmozel.persistence.Member;
import com.hms.atbotizmozel.persistencehelpers.MemberViewModel;
import com.hms.atbotizmozel.util.PrefUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChooseProcessTests extends AppCompatActivity {

    CardView fami_rec_test,learn_emo_test,soci_words_test,deneme;
    TextView fam,emo,soci,play;
    private List<Member> memberList = new ArrayList<>();
    private MemberViewModel memberViewModel;
    private Integer x;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_page);

        fami_rec_test = findViewById(R.id.FamFace);
        learn_emo_test = findViewById(R.id.Emo);
        soci_words_test = findViewById(R.id.Soc);
        fam = findViewById(R.id.fam);
        emo = findViewById(R.id.emo);
        soci = findViewById(R.id.soci);
        play = findViewById(R.id.play_title);
        deneme = findViewById(R.id.deneme);

        memberViewModel = ViewModelProviders.of(this).get(MemberViewModel.class);
        memberViewModel.getmAllMember().observe(ChooseProcessTests.this, new Observer<List<Member>>(){

            @Override
            public void onChanged(@Nullable final List<Member> members) {

                memberList.clear();
                memberList.addAll(members);
                x=memberList.size();
            }

        });
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                if(i != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.getDefault());
                }

            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = play.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        fami_rec_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = fam.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);
                    if(x>=4) {
                        Intent intent = new Intent(ChooseProcessTests.this, inPlay.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(ChooseProcessTests.this,getResources().getString(R.string.at_least_4_relatives_needed), Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(ChooseProcessTests.this, com.hms.atbotizmozel.activity.settings.class);
                        startActivity(intent1);
                    }

            }
        });

        learn_emo_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = emo.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);

                Intent intent  = new Intent(ChooseProcessTests.this, EmotionOptions.class);
                startActivity(intent);

            }
        });

        soci_words_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = soci.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);

                Intent intent  = new Intent(ChooseProcessTests.this,TestPhasee.class);
                startActivity(intent);

            }
        });
        fam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = fam.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        emo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = emo.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        soci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = soci.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        deneme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = fam.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);
                Intent intent  = new Intent(ChooseProcessTests.this, FamilyGame.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.parent_menu,menu);
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
                PrefUtils.with(ChooseProcessTests.this).savePassword("");
                PrefUtils.with(ChooseProcessTests.this).saveUserName("");
                PrefUtils.with(ChooseProcessTests.this).removeUserModel();
                PrefUtils.with(ChooseProcessTests.this).clear();
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