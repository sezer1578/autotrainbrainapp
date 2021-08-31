package com.hms.atbotizmozel.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.util.PrefUtils;

public class ParentChild extends AppCompatActivity {
    CardView Parent,Child;

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
                PrefUtils.with(ParentChild.this).savePassword("");
                PrefUtils.with(ParentChild.this).saveUserName("");
                PrefUtils.with(ParentChild.this).removeUserModel();
                PrefUtils.with(ParentChild.this).clear();
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
        setContentView(R.layout.activity_parent_child);
        Parent = findViewById(R.id.CvParent);
        Child = findViewById(R.id.CvChild);
        Parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ParentIntent = new Intent(getBaseContext(), AddOptions.class);
                startActivity(ParentIntent);
            }
        });
        Child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ParentIntent = new Intent(getBaseContext(), ChildPage.class);
                startActivity(ParentIntent);
            }
        });
    }
}