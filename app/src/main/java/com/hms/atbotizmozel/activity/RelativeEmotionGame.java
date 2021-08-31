package com.hms.atbotizmozel.activity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hms.atbotizmozel.persistence.Emotion;
import com.hms.atbotizmozel.persistence.Member;
import com.hms.atbotizmozel.persistencehelpers.EmotionViewModel;
import com.hms.atbotizmozel.persistencehelpers.MemberViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.hms.atbotizmozel.R;
import androidx.lifecycle.Observer;
public class RelativeEmotionGame extends AppCompatActivity  {

    private ImageView imageView;
    private Button mAnswer1, mAnswer2,mAnswer3,mAnswer4;
    private EmotionViewModel emotionViewModel;
    private Button button;
    private List<Emotion> emotionList = new ArrayList<>();
    private List<String> emotionNames = new ArrayList<>();
    private String currentName;
    private Integer stepNo = 0;
    private Integer totalScore = 0;
    private Boolean answerSelect = false;
    private Button button_color;
    long elapsedTime;
    Chronometer cmTimer;
    Boolean resume = false;
    String TAG = "TAG";
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_emo_game);


        imageView = findViewById(R.id.imageView3);
        mAnswer1 = findViewById(R.id.answer1);
        mAnswer2 = findViewById(R.id.answer2);
        mAnswer3 = findViewById(R.id.answer3);
        mAnswer4 = findViewById(R.id.answer4);
        button = findViewById(R.id.nextButton);
        cmTimer = (Chronometer)findViewById(R.id.tvTimer);
        emotionViewModel = ViewModelProviders.of(this).get(EmotionViewModel.class);
        emotionViewModel.getmAllEmotion().observe(RelativeEmotionGame.this, new Observer<List<Emotion>>(){

            @Override
            public void onChanged(@Nullable final List<Emotion> emotions) {

                emotionList.clear();
                emotionList.addAll(emotions);
                Collections.shuffle(emotionList);

                emotionNames.clear();
                for(Emotion emotion: emotionList){
                    emotionNames.add(emotion.getmEmotionName());
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
        resume = true;
        timer();

        //next
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswer1.setBackground(getResources().getDrawable(R.drawable.button_background));
                mAnswer2.setBackground(getResources().getDrawable(R.drawable.button_background));
                mAnswer3.setBackground(getResources().getDrawable(R.drawable.button_background));
                mAnswer4.setBackground(getResources().getDrawable(R.drawable.button_background));
                if (stepNo == emotionList.size() - 1) {
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
        String photo = emotionList.get(stepNo).getmEmotionPhoto();
        String name = emotionList.get(stepNo).getmEmotionName();
        currentName = name;
        File f = new File(photo);
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Collections.shuffle(emotionNames);
        emotionNames.remove(name);
        List<String> shuffleNames = emotionNames.subList(0,3);
        shuffleNames.add(name);
        Collections.shuffle(shuffleNames);
        mAnswer1.setText(shuffleNames.get(0));
        mAnswer2.setText(shuffleNames.get(1));
        mAnswer3.setText(shuffleNames.get(2));
        mAnswer4.setText(shuffleNames.get(3));
    }

    public void checkAnswer(View view) {

        String userAnswer = ((Button) view).getText().toString().trim();
        if(mAnswer1.isPressed()){

            mAnswer1.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
            String getText = mAnswer1.getText().toString();
            textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);

        }else {
            mAnswer1.setBackground(getResources().getDrawable(R.drawable.button_background));

        }
        if(mAnswer2.isPressed()){

            mAnswer2.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
            String getText = mAnswer2.getText().toString();
            textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);


        }else {
            mAnswer2.setBackground(getResources().getDrawable(R.drawable.button_background));

        }
        if(mAnswer3.isPressed()){

            mAnswer3.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
            String getText = mAnswer3.getText().toString();
            textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);


        }else {
            mAnswer3.setBackground(getResources().getDrawable(R.drawable.button_background));

        }
        if(mAnswer4.isPressed()){

            mAnswer4.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
            String getText = mAnswer4.getText().toString();
            textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);


        }else {
            mAnswer4.setBackground(getResources().getDrawable(R.drawable.button_background));

        }
        if(currentName.equals(userAnswer)){
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
        Intent result = new Intent(RelativeEmotionGame.this, result.class);
        result.putExtra("totalScore",totalScore);
        result.putExtra("totalNumber",emotionList.size());
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
