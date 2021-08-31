package com.hms.atbotizmozel.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hms.atbotizmozel.R;

import java.util.ArrayList;
import java.util.Locale;


import androidx.appcompat.app.AppCompatActivity;

public class SpeakActivity extends AppCompatActivity {
    private final int REQ_CODE = 100;
    private TextToSpeech textToSpeech;
    TextView textView;
    private int i=0;
    private  int MINNBR = 0;
    private  int MAXNBR = 442;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speak);
        textView = findViewById(R.id.text);

        ImageView speak = findViewById(R.id.speak);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    final Locale locale = new Locale("tr", "TR");
                    //Locale locale = new Locale("tr_TR");//set Locale
                    int ttsLang = textToSpeech.setLanguage(locale);
                    String[] wordsArrays = getResources().getStringArray(R.array.words_arrays);
                    String[] positiveArrays = getResources().getStringArray(R.array.positives);
                    String[] negativeArrays = getResources().getStringArray(R.array.negatives);

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                } else {
                    Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE);
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                final Locale locale = new Locale("tr", "TR");
                //Locale locale = new Locale("tr_TR");//set Locale
                int ttsLang = textToSpeech.setLanguage(locale);
                String[] wordsArrays = getResources().getStringArray(R.array.words_arrays);
                String[] positiveArrays = getResources().getStringArray(R.array.positives);
                String[] negativeArrays = getResources().getStringArray(R.array.negatives);
                MAXNBR= wordsArrays.length-1;
                i= (int) ((Math.random() * ((MAXNBR - MINNBR) + 1)) + MINNBR);
                textView.setText(wordsArrays[i]);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        wordsArrays[i]);
                try {
                    startActivityForResult(intent, REQ_CODE);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textView.setText(result.get(0));
                    String data2 = result.get(0);
                    String data3;

                    final Locale locale = new Locale("tr", "TR");
                    //Locale locale = new Locale("tr_TR");//set Locale
                    int ttsLang = textToSpeech.setLanguage(locale);
                    String[] wordsArrays = getResources().getStringArray(R.array.words_arrays);
                    String[] positiveArrays = getResources().getStringArray(R.array.positives);
                    String[] negativeArrays = getResources().getStringArray(R.array.negatives);
                    Log.e("TTS", data2);
                    Log.e("TTS", wordsArrays[i]);
                    textView.setText(wordsArrays[i]);
                    int j=(int) (Math.random() * 20);
                    if (data2.compareToIgnoreCase(wordsArrays[i])==0)
                        data3=positiveArrays[j];
                    else data3=negativeArrays[j];
                    int speechStatus = textToSpeech.speak(data2, TextToSpeech.QUEUE_FLUSH, null);

                    if (speechStatus == TextToSpeech.ERROR) {
                        Log.e("TTS", "Error in converting Text to Speech!");
                    }
                    speechStatus = textToSpeech.speak(data3, TextToSpeech.QUEUE_FLUSH, null);

                    if (speechStatus == TextToSpeech.ERROR) {
                        Log.e("TTS", "Error in converting Text to Speech!");
                    }
                }
                break;
            }
        }
    }
}
