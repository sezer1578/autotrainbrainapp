package com.hms.atbotizmozel.fragment;

import android.app.Fragment;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hms.atbotizmozel.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TutorialsPoint7 on 8/23/2016.
 */

public class AlphabetFragment extends Fragment {

    @BindView(R.id.picture)
    ImageView nameImage;
   /* @BindView(R.id.letter)
    ImageView letterImage;*/

    public boolean created = false;
    public MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alphabet, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        created = true;
    }

    public boolean setImage(int im) {
        String str1 = getString(R.string.file_url);
        String str2 = str1 + "harf/" + String.format("%d", im) + ".png";
        String str3 = str1 +  String.format("%d", im) + ".jpg";

        if (created) {
           /* Picasso.with(getView().getContext()).load(str2).into(letterImage);*/
            Picasso.with(getView().getContext()).load(str3).into(nameImage);
            return true;
        }
        return false;
    }

    public boolean setSound(int im) throws IOException {

        String url = getString(R.string.ses_url) + String.format("%d", im) + ".mp3";
        if (created) {
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.start();// might take long! (for buffering, etc)
            } catch (IOException e) {
                Log.e("", "Exception" + e.getMessage());
            }
            return true;
        }
        return false;
    }

    public void closeSound() {
        if (created) {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = new MediaPlayer();
                }
            }
        }
    }
}
