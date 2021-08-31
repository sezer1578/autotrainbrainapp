package com.hms.atbotizmozel.socializationwords;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

import com.hms.atbotizmozel.R;

import java.util.Locale;

public class LearningPhase extends AppCompatActivity {


    CardView hello,howAreYou,whatsYourName,myName,fineThankYou,goodBye;

    Button hellobtn,howAreYoubtn,whatsYourNamebtn,myNamebtn,fineThankYoubtn,goodByebtn;

    TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_phase);

        hello = findViewById(R.id.hello);
        howAreYou = findViewById(R.id.howAreYou);
        whatsYourName = findViewById(R.id.whatIsYourName);
        myName = findViewById(R.id.myName);
        fineThankYou = findViewById(R.id.fineThankYou);
        goodBye = findViewById(R.id.goodBye);

        hellobtn = findViewById(R.id.hellobtn);
        howAreYoubtn = findViewById(R.id.howAreYoubtn);
        whatsYourNamebtn = findViewById(R.id.whatIsYourNamebtn);
        myNamebtn = findViewById(R.id.myNamebtn);
        fineThankYoubtn = findViewById(R.id.fineThankYoubtn);
        goodByebtn = findViewById(R.id.goodByebtn);


        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                if(i != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });

        hellobtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String getText = hellobtn.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);
                return false;
            }
        });
        howAreYoubtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String getText = howAreYoubtn.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);
                return false;
            }
        });

        whatsYourNamebtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String getText = whatsYourNamebtn.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);
                return false;
            }
        });

        myNamebtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String getText = myNamebtn.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);
                return false;
            }
        });

        fineThankYoubtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String getText = fineThankYoubtn.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);
                return false;
            }
        });

        goodByebtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String getText = goodByebtn.getText().toString();
                textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);
                return false;
            }
        });

        hellobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = hellobtn.getText().toString();
                /*textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);*/

                if(getText.equals("Hello")){
                    Intent intent = new Intent(getApplicationContext(), VideoPage.class);
                    intent.putExtra("value", "https://atbprod.s3.us-east-2.amazonaws.com/Socialization_words_videos/EN/Hello.mp4");
                    startActivity(intent);
                }
                if(getText.equals("Merhaba")){
                    Intent intent = new Intent(getApplicationContext(), VideoPage.class);
                    intent.putExtra("value", "https://atbprod.s3.us-east-2.amazonaws.com/Socialization_words_videos/TR/Merhaba2x!.mp4");
                    startActivity(intent);
                }
            }
        });

        howAreYoubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = howAreYoubtn.getText().toString();
                /*textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);*/

                if(getText.equals("How are you?")){
                    Intent intent = new Intent(getApplicationContext(), VideoPage.class);
                    intent.putExtra("value", "https://atbprod.s3.us-east-2.amazonaws.com/Socialization_words_videos/EN/HowAreYou.mp4");
                    startActivity(intent);
                }
                if(getText.equals("Nasılsın?")){
                    Intent intent = new Intent(getApplicationContext(), VideoPage.class);
                    intent.putExtra("value", "https://atbprod.s3.us-east-2.amazonaws.com/Socialization_words_videos/TR/Bug%C3%BCnNas%C4%B1ls%C4%B1n.mp4");
                    startActivity(intent);
                }
            }
        });

        whatsYourNamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = whatsYourNamebtn.getText().toString();
               /*textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);*/

                if(getText.equals("What is your name?")){
                    Intent intent = new Intent(getApplicationContext(), VideoPage.class);
                    intent.putExtra("value", "https://atbprod.s3.us-east-2.amazonaws.com/Socialization_words_videos/EN/WhatIsYourName.mp4");
                    startActivity(intent);
                }
                if(getText.equals("Adın ne?")){
                    Intent intent = new Intent(getApplicationContext(), VideoPage.class);
                    intent.putExtra("value", "https://atbprod.s3.us-east-2.amazonaws.com/Socialization_words_videos/TR/Ad%C4%B1nNe.mp4");
                    startActivity(intent);
                }
            }
        });

        myNamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = myNamebtn.getText().toString();
               /* textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);*/

                if(getText.equals("My name is..")){
                    Intent intent = new Intent(getApplicationContext(), VideoPage.class);
                    intent.putExtra("value", "https://atbprod.s3.us-east-2.amazonaws.com/Socialization_words_videos/EN/MyNameIs...mp4");
                    startActivity(intent);
                }
                if(getText.equals("Benim adım")){
                    Intent intent = new Intent(getApplicationContext(), VideoPage.class);
                    intent.putExtra("value", "https://atbprod.s3.us-east-2.amazonaws.com/Socialization_words_videos/TR/Ben%C3%96mer.mp4");
                    startActivity(intent);
                }

            }
        });
        fineThankYoubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = fineThankYoubtn.getText().toString();
                /*textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);*/

                if(getText.equals("Fine, thank you")){
                    Intent intent = new Intent(getApplicationContext(), VideoPage.class);
                    intent.putExtra("value", "https://atbprod.s3.us-east-2.amazonaws.com/Socialization_words_videos/EN/FineThankYou.mp4");
                    startActivity(intent);
                }
                if(getText.equals("İyiyim, teşekkür ederim")){
                    Intent intent = new Intent(getApplicationContext(), VideoPage.class);
                    intent.putExtra("value", "https://atbprod.s3.us-east-2.amazonaws.com/Socialization_words_videos/TR/T%C5%9Fk%C4%B0yiyim!.mp4");
                    startActivity(intent);
                }
            }
        });
        goodByebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getText = goodByebtn.getText().toString();
               /* textToSpeech.speak(getText,TextToSpeech.QUEUE_FLUSH,null);*/
                if(getText.equals("Good bye")){
                    Intent intent = new Intent(getApplicationContext(), VideoPage.class);
                    intent.putExtra("value", "https://atbprod.s3.us-east-2.amazonaws.com/Socialization_words_videos/EN/GoodBye.mp4");
                    startActivity(intent);
                }
                if(getText.equals("Güle güle")){
                    Intent intent = new Intent(getApplicationContext(), VideoPage.class);
                    intent.putExtra("value", "https://atbprod.s3.us-east-2.amazonaws.com/Socialization_words_videos/TR/Ho%C5%9F%C3%A7akal-G%C3%BCleG%C3%BCle.mp4");
                    startActivity(intent);
                }
            }
        });


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