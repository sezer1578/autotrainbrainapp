package com.hms.atbotizmozel.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.persistence.Member;
import com.hms.atbotizmozel.persistencehelpers.MemberViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class FamilyGame extends AppCompatActivity {
    TextView title,reldeg;
    ImageView top,bottom;
    TextToSpeech textToSpeech;
    private MemberViewModel memberViewModel;
    private List<Member> memberList = new ArrayList<>();
    private List<String> memberPhotos = new ArrayList<>();
    private String currentName;
    private String currentPhoto;
    private Integer stepNo = 0;
    private Integer totalScore = 0;
    private Boolean answerSelect = false;
    long elapsedTime;
    Chronometer cmTimer;
    Boolean resume = false;
    String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_game);
        title = findViewById(R.id.Question);
        reldeg = findViewById(R.id.Tv_rel_deg);
        top = findViewById(R.id.imageViewTop);
        bottom = findViewById(R.id.imageViewBottom);
        cmTimer = (Chronometer)findViewById(R.id.tvTimer);
        memberViewModel = ViewModelProviders.of(this).get(MemberViewModel.class);
        memberViewModel.getmAllMember().observe(FamilyGame.this, new Observer<List<Member>>(){

            @Override
            public void onChanged(@Nullable final List<Member> members) {

                memberList.clear();
                memberList.addAll(members);
                Collections.shuffle(memberList);

                memberPhotos.clear();
                for(Member member: memberList){
                    memberPhotos.add(member.getmPhoto());
                }
                startGame();
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
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = title.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        resume = true;
        timer();

        //next
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepNo == memberList.size() - 1) {
                    ScoreCount(v);
                    answerSelect = false;
                    endGame();
                } else {
                    ScoreCount(v);
                    answerSelect = false;
                    stepNo++;
                    startGame();
                }
            }

        });
        //next
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepNo == memberList.size() - 1) {
                    ScoreCount(v);
                    answerSelect = false;
                    endGame();
                } else {
                    ScoreCount(v);
                    answerSelect = false;
                    stepNo++;
                    startGame();
                }
            }

        });
        reldeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = reldeg.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        cmTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (!resume) {
                    long minutes = ((SystemClock.elapsedRealtime() - cmTimer.getBase())/1000) / 60;
                    long seconds = ((SystemClock.elapsedRealtime() - cmTimer.getBase())/1000) % 60;
                    elapsedTime = SystemClock.elapsedRealtime();
                    Log.d(TAG, "onChronometerTick: " + minutes + " : " + seconds);

                } else {
                    long minutes = ((elapsedTime - cmTimer.getBase())/1000) / 60;
                    long seconds = ((elapsedTime - cmTimer.getBase())/1000) % 60;
                    elapsedTime = elapsedTime + 1000;
                    Log.d(TAG, "onChronometerTick: " + minutes + " : " + seconds);

                }
            }
        });
    }
    public void timer() {
        if(!resume) {
            cmTimer.setBase(SystemClock.elapsedRealtime());
            cmTimer.start();
        }
        else {
            cmTimer.start();
        }
    }

    public void startGame(){
        String photo = memberList.get(stepNo).getmPhoto();
        String name = memberList.get(stepNo).getmName();
        currentName = name;
        currentPhoto= photo;
        Collections.shuffle(memberPhotos);
        memberPhotos.remove(photo);
        List<String> shuffleNames = memberPhotos.subList(0,1);
        shuffleNames.add(photo);
        Collections.shuffle(shuffleNames);
        File f = new File(shuffleNames.get(0));
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
            bottom.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File d = new File(shuffleNames.get(1));
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(d));
            top.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        reldeg.setText(name);
    }

    public void checkAnswer(View view) {

        String userAnswer = ((Button) view).getText().toString().trim();
        if(currentPhoto.equals(userAnswer)){
            answerSelect = true;
        }else{
            answerSelect = false;
        }
    }

    public void ScoreCount(View view) {
        if(answerSelect==true){
            totalScore++;
        }
    }
    public void endGame(){
        cmTimer.stop();
        Intent result = new Intent(FamilyGame.this, result.class);
        result.putExtra("totalScore",totalScore);
        result.putExtra("totalNumber",memberList.size());
        result.putExtra("elapsedTime", elapsedTime);
        startActivity(result);
        finish();

    }
    @Override
    protected void onPause() {

        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        super.onPause();
    }
}