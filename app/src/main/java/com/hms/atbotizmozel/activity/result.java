package com.hms.atbotizmozel.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.socializationwords.ChooseProcessTests;

public class result extends AppCompatActivity {

    private ImageButton back_settings;
    private Button finish_button;
    private TextView textView;
    private TextView message;
    private Integer integerScore, integerNumber;
    Chronometer timetaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView = findViewById(R.id.resultNumber);
        message = findViewById(R.id.resultFeedBack);
        timetaken = findViewById(R.id.timetaken);
        Intent intent = getIntent();
        integerScore = intent.getIntExtra("totalScore",0);
        integerNumber = intent.getIntExtra("totalNumber",0);
        String totalScore = String.valueOf(intent.getIntExtra("totalScore",0));
        String totalNumber = String.valueOf(intent.getIntExtra("totalNumber",0));
        textView.setText(String.format(getResources().getString(R.string.result) + ": %s / %s", totalScore, totalNumber));
        if((integerNumber/4)>=integerScore){
            message.setText(getResources().getString(R.string.result_message1));
        }else if((integerNumber/2)>=integerScore){
            message.setText(getResources().getString(R.string.result_message2));
        }else if((integerNumber*0.75)>=integerScore){
            message.setText(getResources().getString(R.string.result_message3));
        }else{
            message.setText(getResources().getString(R.string.result_message4));
        }



        //go back to main
        finish_button=findViewById(R.id.finish_button);
        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent finish = new Intent(result.this, ChooseProcessTests.class);
                startActivity(finish);

            }

        });
        long elapsedTime = intent.getExtras().getLong("elapsedTime");
        long minutes = (elapsedTime/1000) / 60;
        long seconds = (elapsedTime/1000) % 60;
        timetaken.setText(" "+minutes + ":" + seconds);
    }
}