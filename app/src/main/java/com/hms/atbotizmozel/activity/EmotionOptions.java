package com.hms.atbotizmozel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.persistence.Member;
import com.hms.atbotizmozel.persistencehelpers.MemberViewModel;
import com.hms.atbotizmozel.socializationwords.ChooseProcessTests;

import java.util.ArrayList;
import java.util.List;

public class EmotionOptions extends AppCompatActivity {
//    Toolbar toolbar1;
//    TextView toolbarTitle;
    ImageView human, cartoon, emoji,family;
    TextView humantext, cartoontext, emojitext;
    private Integer x;
    private List<Member> memberList = new ArrayList<>();
    private MemberViewModel memberViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_options);


//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//        toolbar1 = findViewById(R.id.toolbar);
//        toolbarTitle = findViewById(R.id.titleText);
//        toolbar1.setTitle(" ");
//        toolbarTitle.setText(getResources().getString(R.string.choseEmotionCategory));
//        setSupportActionBar(toolbar1);

        human = findViewById(R.id.human);
        humantext = findViewById(R.id.humantext);
        cartoon = findViewById(R.id.cartoon);
        cartoontext = findViewById(R.id.cartoontext);
        emoji = findViewById(R.id.emoji);
        emojitext = findViewById(R.id.emojitext);
        family= findViewById(R.id.familyEmotion);
        memberViewModel = ViewModelProviders.of(this).get(MemberViewModel.class);
        memberViewModel.getmAllMember().observe(EmotionOptions.this, new Observer<List<Member>>(){

            @Override
            public void onChanged(@Nullable final List<Member> members) {

                memberList.clear();
                memberList.addAll(members);
                x=memberList.size();
            }

        });


        human.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionOptions.this, HumanEmotionTest.class);
                startActivity(intent);
                //finish();
            }
        });

        humantext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionOptions.this, HumanEmotionTest.class);
                startActivity(intent);
               // finish();
            }
        });

        cartoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionOptions.this, CartoonEmotionTest.class);
                startActivity(intent);
                //finish();
            }
        });

        cartoontext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionOptions.this, CartoonEmotionTest.class);
                startActivity(intent);
                //finish();
            }
        });

        emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionOptions.this, EmojiEmotionTest.class);
                startActivity(intent);
                //finish();
            }
        });

        emojitext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmotionOptions.this, EmojiEmotionTest.class);
                startActivity(intent);
                //finish();
            }
        });
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(x>=4) {
                    Intent intent = new Intent(EmotionOptions.this, inPlay.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(EmotionOptions.this,getResources().getString(R.string.at_least_4_relatives_needed), Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(EmotionOptions.this, RelativeEmotionsList.class);
                    startActivity(intent1);
                }
            }
        });

    }
}