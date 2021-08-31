package com.hms.atbotizmozel.socializationwords;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.hms.atbotizmozel.R;

public class VideoPage extends AppCompatActivity {

    TextView title;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_page);

        title = findViewById(R.id.videoTitle);
        videoView = findViewById(R.id.vid);

        MediaController mediaController = new MediaController(this);

        videoView.findViewById(R.id.vid);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        Bundle extras = getIntent().getExtras();

        String videoUrl = (String) extras.get("value");
        Uri uri = Uri.parse(videoUrl);
        videoView.setVideoURI(uri);
        videoView.start();
    }
}