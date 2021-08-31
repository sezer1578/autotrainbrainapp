package com.hms.atbotizmozel.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.socializationwords.ChooseProcess;
import com.hms.atbotizmozel.socializationwords.ChooseProcessTests;
import com.hms.atbotizmozel.util.PrefUtils;

import java.util.Locale;

public class ChildPage extends AppCompatActivity {
    public Button play_button;
    private Button learn_button;
    TextToSpeech textToSpeech;




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
                PrefUtils.with(ChildPage.this).savePassword("");
                PrefUtils.with(ChildPage.this).saveUserName("");
                PrefUtils.with(ChildPage.this).removeUserModel();
                PrefUtils.with(ChildPage.this).clear();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_or_learn);
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                if(i != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.getDefault());
                }

            }
        });

       //go play
        play_button=findViewById(R.id.playb);
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText = play_button.getText().toString();
                textToSpeech.speak(getText, TextToSpeech.QUEUE_FLUSH,null);
                Intent play = new Intent(ChildPage.this, ChooseProcessTests.class);
                startActivity(play);
            }
        });
        //go learn
        learn_button=findViewById(R.id.learnb);
        learn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getText = learn_button.getText().toString();
                textToSpeech.speak(getText, TextToSpeech.QUEUE_FLUSH,null);
                Intent learn = new Intent(ChildPage.this, ChooseProcess.class);
                startActivity(learn);
            }
        });
    }
}