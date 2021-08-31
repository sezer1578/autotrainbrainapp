package com.hms.atbotizmozel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hms.atbotizmozel.R;
public class TestOrLearning extends AppCompatActivity {
//    Toolbar toolbar1;
    TextView toolbarTitle;
    ImageView test, learning;
    TextView testtext, learningtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_or_learning);

//        toolbar1 = findViewById(R.id.toolbar);
//        toolbarTitle = findViewById(R.id.titleText);
//
//        toolbar1.setTitle(" ");
//        toolbarTitle.setText(getResources().getString(R.string.choseEmotionCategory));
//        setSupportActionBar(toolbar1);

        test = findViewById(R.id.test);
        learning = findViewById(R.id.learning);
        testtext = findViewById(R.id.testtext);
        learningtext = findViewById(R.id.learningtext);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestOrLearning.this, EmotionOptions.class);
                startActivity(intent);
                //finish();
            }
        });
        testtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestOrLearning.this, EmotionOptions.class);
                startActivity(intent);
                //finish();
            }
        });

        learning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestOrLearning.this, EmotionLearning.class);
                startActivity(intent);
                //finish();
            }
        });

        learningtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestOrLearning.this, EmotionLearning.class);
                startActivity(intent);
                //finish();
            }
        });

    }
}