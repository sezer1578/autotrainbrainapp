package com.hms.atbotizmozel.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hms.atbotizmozel.R;

import com.hms.atbotizmozel.adapter.SettingsAdapter;
import com.hms.atbotizmozel.persistence.Member;
import com.hms.atbotizmozel.persistencehelpers.MemberViewModel;

import java.util.List;

public class settings extends AppCompatActivity {

    public ImageButton back_settings;
    private MemberViewModel memberViewModel;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // init recyclerview
        RecyclerView recyclerView = findViewById(R.id.memberList);
        final SettingsAdapter adapter = new SettingsAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        //floating button add member
        floatingActionButton = findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(settings.this, addMember.class);
                startActivity(add);
            }
        });


        // init view model
        memberViewModel = ViewModelProviders.of(this).get(MemberViewModel.class);
        memberViewModel.getmAllMember().observe(this, new Observer<List<Member>>() {
            @Override
            public void onChanged(@Nullable final List<Member> members) {
                // Update the cached copy of the words in the adapter.
                for (int x =0; x < members.size();  x++){
                    Log.d("Member Name", members.get(x).getmName());
                    adapter.setMembers(members);
                }
            }
        });
    }

}