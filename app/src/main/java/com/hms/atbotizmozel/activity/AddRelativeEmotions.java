package com.hms.atbotizmozel.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.hms.atbotizmozel.persistence.Emotion;
import com.hms.atbotizmozel.persistence.Member;
import com.hms.atbotizmozel.persistencehelpers.EmotionViewModel;
import com.hms.atbotizmozel.persistencehelpers.MemberViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.hms.atbotizmozel.R;
public class AddRelativeEmotions extends AppCompatActivity {
    public static final String EXSTRA_NAME = "com.example.androidproject.EXSTRA_NAME";
    public static final String EXSTRA_PHOTO = "com.example.androidproject.EXSTRA_PHOTO";
    private static final String LOG_TAG = "Error";


    public Button save_member_button;
    public Button choosePhoto;

    int SELECT_PICTURE = 200;

    //references to button and other controls on the layout

    private ImageView image1;
    private EditText name_text;
    private EmotionViewModel emotionViewModel;
    private String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_relative_emotion);


        name_text = findViewById(R.id.memberName);
        choosePhoto = findViewById(R.id.updatePhoto);
        image1 = findViewById(R.id.image_profile1);
        save_member_button = findViewById(R.id.save_member_button);

        emotionViewModel = ViewModelProviders.of(this).get(EmotionViewModel.class);
        //choose Photo
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(image1);
            }
        });


        //save member
        save_member_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer integer = saveTheMember();
                if (integer == 0) {
                    Intent save = new Intent(AddRelativeEmotions.this, RelativeEmotionsList.class);
                    startActivity(save);
                }
            }
        });
    }
    public void chooseImage(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
        }else{
            Log.d("INTENT STATUS", "INTENT DOES NOT FOUND!");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fullPhotoUri);
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                path = saveToInternalStorage(imageBitmap, cw);
                image1.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(LOG_TAG, "Selected image could not be uploaded!");
        }
    }



    public static String saveToInternalStorage(Bitmap bitmapImage, ContextWrapper cw){
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new Date());
        String imageFileName = "PNG" + timeStamp + ".png";
        File directory = cw.getDir("assets", Context.MODE_PRIVATE);

        File mypath = new File(directory, imageFileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }

    private int saveTheMember(){
        String name = name_text.getText().toString();
        String photo = path;
        if(name.trim().isEmpty()){
            Toast.makeText(AddRelativeEmotions.this,getResources().getString(R.string.fill_name), Toast.LENGTH_SHORT).show();
            return -1;
        }
        if(photo.trim().isEmpty()){
            Toast.makeText(AddRelativeEmotions.this,getResources().getString(R.string.fill_photo), Toast.LENGTH_SHORT).show();
            return -1;
        }
        Intent data = new Intent();
        data.putExtra(EXSTRA_NAME, name);
        data.putExtra(EXSTRA_PHOTO, photo);
        setResult(RESULT_OK, data);

        Emotion emotion = new Emotion(name,photo);
        emotionViewModel.insert(emotion);
        Toast.makeText(this,getResources().getString(R.string.relative_saved), Toast.LENGTH_SHORT).show();
        finish();
        return 0;
    }

}