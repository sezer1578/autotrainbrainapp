package com.hms.atbotizmozel.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.hms.atbotizmozel.R;
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

public class RelativeEmotionLearn extends AppCompatActivity {

    private ImageView imageView;
    private EmotionViewModel emotionViewModel;
    private Button button;
    private List<String> emotionNames = new ArrayList<>();
    private String currentName;
    private List<Emotion> emotionList = new ArrayList<>();
    private Integer stepNo = 0;
    private TextView text_name;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_emo_learn);

        imageView = findViewById(R.id.imageView3);
        button = findViewById(R.id.nextButton);
        text_name = findViewById(R.id.name);

        emotionViewModel = ViewModelProviders.of(this).get(EmotionViewModel.class);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                if(i != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.getDefault());
                }

            }
        });
        text_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = text_name.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        emotionViewModel.getmAllEmotion().observe(RelativeEmotionLearn.this, new Observer<List<Emotion>>(){

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

        //next
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stepNo == emotionList.size() - 1) {
                    endGame();
                } else {
                    stepNo++;
                    startGame();
                }
            }

        });


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
        text_name.setText(currentName);

    }
    @Override
    protected void onPause() {

        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        super.onPause();
    }

    public void endGame(){

        Intent result = new Intent(RelativeEmotionLearn.this, ChildPage.class);
        startActivity(result);
    }
}
