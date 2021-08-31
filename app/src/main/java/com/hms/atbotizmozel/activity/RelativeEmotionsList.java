package com.hms.atbotizmozel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.adapter.EmotionAdapter;
import com.hms.atbotizmozel.adapter.SettingsAdapter;
import com.hms.atbotizmozel.persistence.Emotion;
import com.hms.atbotizmozel.persistence.Member;
import com.hms.atbotizmozel.persistencehelpers.EmotionViewModel;
import com.hms.atbotizmozel.persistencehelpers.MemberViewModel;

import java.util.List;

public class RelativeEmotionsList extends AppCompatActivity {
    private EmotionViewModel emotionViewModel;
    private FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_emotions_list);

        // init recyclerview
        RecyclerView recyclerView = findViewById(R.id.RelEmoList);
        final EmotionAdapter adapter = new EmotionAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        //floating button add member
        floatingActionButton = findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(RelativeEmotionsList.this, AddRelativeEmotions.class);
                startActivity(add);
            }
        });


        // init view model
        emotionViewModel = ViewModelProviders.of(this).get(EmotionViewModel.class);
        emotionViewModel.getmAllEmotion().observe(this, new Observer<List<Emotion>>() {
            @Override
            public void onChanged(@Nullable final List<Emotion> emotions) {
                // Update the cached copy of the words in the adapter.
                for (int x =0; x < emotions.size();  x++){
                    Log.d("Member Name", emotions.get(x).getmEmotionName());
                    adapter.setEmotion(emotions);
                }
            }
        });
    }

}
