package com.hms.atbotizmozel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hms.atbotizmozel.R;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class EmotionLearning extends AppCompatActivity {
    ImageView happyemoji, excitedemoji, angryemoji, sademoji, hungryemoji,nervousemoji, shyemoji, sickemoji, boredemoji;
    TextView happyemojitext, excitedemojitext, angryemojitext, sademojitext, hungryemojitext, nervousemojitext, shyemojitext, sickemojitext, boredemojitext;
    TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_learning);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                if(i != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.getDefault());
                }

            }
        });
        happyemoji = findViewById(R.id.happyemoji);
        happyemojitext = findViewById(R.id.happyemojitext);

        excitedemoji = findViewById(R.id.excitedEmoji);
        excitedemojitext = findViewById(R.id.excitedEmojitext);

        angryemoji = findViewById(R.id.angryemoji);
        angryemojitext = findViewById(R.id.angryemojitext);

        sademoji = findViewById(R.id.sademoji);
        sademojitext = findViewById(R.id.sademojitext);

        hungryemoji = findViewById(R.id.hungryemoji);
        hungryemojitext = findViewById(R.id.hungryemojitext);

        nervousemoji = findViewById(R.id.nervousemoji);
        nervousemojitext = findViewById(R.id.nervousemojitext);

        shyemoji = findViewById(R.id.shyemoji);
        shyemojitext = findViewById(R.id.shyemojitext);

        sickemoji = findViewById(R.id.sickemoji);
        sickemojitext = findViewById(R.id.sickemojitext);

        boredemoji = findViewById(R.id.boredemoji);
        boredemojitext = findViewById(R.id.boredemojitext);

        happyemoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionLearning.this, LearningHappy.class);
                startActivity(intent);

            }
        });

        happyemojitext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText = happyemojitext.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);

            }
        });
        excitedemoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionLearning.this, LearningExcited.class);
                startActivity(intent);

            }
        });

        excitedemojitext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText = excitedemojitext.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);

            }
        });

        angryemoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionLearning.this, LearningAngry.class);
                startActivity(intent);

            }
        });

        angryemojitext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText = angryemojitext.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);

            }
        });

        sademoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionLearning.this, LearningSad.class);
                startActivity(intent);

            }
        });

        sademojitext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText = sademojitext.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);

            }
        });

        hungryemoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionLearning.this, LearningHungry.class);
                startActivity(intent);

            }
        });

        hungryemojitext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText = hungryemojitext.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);

            }
        });

        nervousemoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionLearning.this, LearningNervous.class);
                startActivity(intent);

            }
        });

        nervousemojitext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText = nervousemojitext.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);

            }
        });

        shyemoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionLearning.this, LearningShy.class);
                startActivity(intent);

            }
        });

        shyemojitext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText = shyemojitext.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);

            }
        });

        sickemoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionLearning.this, LearningSick.class);
                startActivity(intent);

            }
        });

        sickemojitext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText = sickemojitext.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);

            }
        });

        boredemoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionLearning.this, LearningBored.class);
                startActivity(intent);

            }
        });

        boredemojitext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText = boredemojitext.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);

            }
        });

    }
}