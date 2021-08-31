package com.hms.atbotizmozel.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

import com.hms.atbotizmozel.R;
public class CartoonEmotionTest extends AppCompatActivity {

//    Toolbar toolbar1;
//    TextView toolbarTitle;
    Chronometer cmTimer;
    TextView tvResult;
    ImageView ivShowImage;
    HashMap<String, Integer> map = new HashMap<>();
    ArrayList<String> techList = new ArrayList<>();
    int index;
    AppCompatButton btn1, btn2, btn3, btn4;
    TextView tvPoints;
    int points;
    long elapsedTime;
    String TAG = "TAG";
    Boolean resume = false;
    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoon_emotion_test);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//        toolbar1 = findViewById(R.id.toolbar);
//        toolbarTitle = findViewById(R.id.titleText);
//
//        toolbar1.setTitle("");
//        toolbarTitle.setText(getResources().getString(R.string.cartoonEmotionTest));
        //setSupportActionBar(toolbar1);

        cmTimer = (Chronometer)findViewById(R.id.tvTimer);
        tvResult = findViewById(R.id.tvResult);
        ivShowImage = findViewById(R.id.ivShowImage);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        tvPoints = findViewById(R.id.tvPoints);
        index = 0;

        techList.add(getResources().getString(R.string.happy));
        techList.add(getResources().getString(R.string.sad));
        techList.add(getResources().getString(R.string.angry));
        techList.add(getResources().getString(R.string.surprised));
        techList.add(getResources().getString(R.string.smiley));
        techList.add(getResources().getString(R.string.scared));
        techList.add(getResources().getString(R.string.upset));
        techList.add(getResources().getString(R.string.normal));
        techList.add(getResources().getString(R.string.furious));
        map.put(techList.get(0), R.drawable.cartoon1_1);
        map.put(techList.get(1), R.drawable.cartoon1_2);
        map.put(techList.get(2), R.drawable.cartoon1_3);
        map.put(techList.get(3), R.drawable.cartoon1_4);
        map.put(techList.get(4), R.drawable.cartoon1_5);
        map.put(techList.get(5), R.drawable.cartoon3_3);
        map.put(techList.get(6), R.drawable.cartoon3_4);
        map.put(techList.get(7), R.drawable.cartoon3_5);
        map.put(techList.get(8), R.drawable.cartoon3_2);

        Collections.shuffle(techList);
        points = 0;
        startGame();


        cmTimer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer arg0) {

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
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                if(i != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });
        resume = true;
    }
    public void onClick() {

        if(!resume) {
            cmTimer.setBase(SystemClock.elapsedRealtime());
            cmTimer.start();
        }
        else {
            cmTimer.start();
        }
    }

    private void startGame(){
        onClick();

        resume=true;
//        tvPoints.setText(points+"/"+techList.size());
        generateQuestions(index);
    }

    private void generateQuestions(int index) {
        ArrayList<String> techListTemp = (ArrayList<String>)techList.clone();
        String correctAnswer = techList.get(index);
        techListTemp.remove(correctAnswer);
        Collections.shuffle(techListTemp);
        ArrayList<String> newList = new ArrayList<>();
        newList.add(techListTemp.get(0));
        newList.add(techListTemp.get(1));
        newList.add(techListTemp.get(2));
        newList.add(correctAnswer);
        Collections.shuffle(newList);
        btn1.setText(newList.get(0));
        btn2.setText(newList.get(1));
        btn3.setText(newList.get(2));
        btn4.setText(newList.get(3));
        ivShowImage.setImageResource(map.get(techList.get(index)));
    }

    public void nextQuestion(View view) {
        btn1.setBackground(getResources().getDrawable(R.drawable.button_background));
        btn2.setBackground(getResources().getDrawable(R.drawable.button_background));
        btn3.setBackground(getResources().getDrawable(R.drawable.button_background));
        btn4.setBackground(getResources().getDrawable(R.drawable.button_background));
        index++;
        if (index > techList.size() - 1){
            ivShowImage.setVisibility(View.GONE);
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);

            Intent intent = new Intent(CartoonEmotionTest.this, GameOver.class);
            intent.putExtra("points", points);
            intent.putExtra("elapsedTime", elapsedTime);
            startActivity(intent);
            finish();
        } else {
            startGame();
        }
    }
    public void answerSelected(View view) {
        String answer = ((Button) view).getText().toString().trim();
        String correctAnswer = techList.get(index);

        if(btn1.isPressed()){
            btn1.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
            String getText = btn1.getText().toString();
            textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);

        }else {
            btn1.setBackground(getResources().getDrawable(R.drawable.button_background));

        }
        if(btn2.isPressed()){
            btn2.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
            String getText = btn2.getText().toString();
            textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);

        }else {
            btn2.setBackground(getResources().getDrawable(R.drawable.button_background));

        }
        if(btn3.isPressed()){
            btn3.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
            String getText = btn3.getText().toString();
            textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);

        }else {
            btn3.setBackground(getResources().getDrawable(R.drawable.button_background));

        }
        if(btn4.isPressed()){
            btn4.setBackground(getResources().getDrawable(R.drawable.button_background_selected));
            String getText = btn4.getText().toString();
            textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);

        }else {
            btn4.setBackground(getResources().getDrawable(R.drawable.button_background));

        }

        if(answer.equals(correctAnswer)){
          //  tvPoints.setText(points + "/"+ techList.size());
           // tvPoints.setText(points + "/"+ techList.size());
            tvResult.setText(answer+ " " + getResources().getString(R.string.selected));
            points++;

        }else{
         //   tvPoints.setText(points + "/"+ techList.size());
            tvResult.setText(answer+" " + getResources().getString(R.string.selected));
        }

    }

  

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        cmTimer.stop();
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(CartoonEmotionTest.this);
            builder.setTitle(getResources().getString(R.string.exitTest));
            builder.setMessage(getResources().getString(R.string.exitTestLong));
            builder.setNegativeButton(getResources().getString(R.string.no), null);
            builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(CartoonEmotionTest.this, EmotionOptions.class);
                    startActivity(intent);
                    finish();

                }
            });
            builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    cmTimer.start();
                }
            });
            builder.show();

            return true;
        }
        return false;
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