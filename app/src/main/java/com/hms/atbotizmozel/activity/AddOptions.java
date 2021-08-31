package com.hms.atbotizmozel.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.hms.atbotizmozel.R;

public class AddOptions extends AppCompatActivity {
    Button family,emotion,record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_options);
        family = findViewById(R.id.family_face);
        emotion = findViewById(R.id.emotion);

        
         //family rec
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddOptions.this, settings.class);
                startActivity(intent);
            }
        });
       // Relative Emotions
       emotion.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(AddOptions.this, RelativeEmotionsList.class);
               startActivity(intent);
           }
       });

    }
}