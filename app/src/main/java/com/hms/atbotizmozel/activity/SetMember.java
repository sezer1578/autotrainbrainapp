package com.hms.atbotizmozel.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.hms.atbotizmozel.persistence.Member;
import com.hms.atbotizmozel.persistencehelpers.MemberViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.hms.atbotizmozel.R;

public class SetMember extends AppCompatActivity {
    public static final String EXSTRA_NAME="com.example.androidproject.EXSTRA_NAME";
    public static final String EXSTRA_PHOTO="com.example.androidproject.EXSTRA_PHOTO";
    public static final String LOG_TAG="PHOTO";


    private EditText editText;
    private ImageView imageView;
    private final int SELECT_PICTURE = 200;
    private Button updatePhoto, save,deleteMember;
    private MemberViewModel memberViewModel;
    private String path = "";
    private LiveData<Member> memberLiveData;
    private Bundle bundle;
    private String name1;
    private String photo1;
    private Integer muid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_member);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String photo = intent.getStringExtra("photo");
        Integer uid = intent.getIntExtra("uId",0);
        muid = uid;
        photo1 = photo;

            editText = findViewById(R.id.memberName);
            editText.setText(name);
            imageView = findViewById(R.id.image_profile1);
            save = findViewById(R.id.save_button);
            deleteMember = findViewById(R.id.delete_member_button);

            File f = new File(photo);
            try {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
            imageView.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
            e.printStackTrace();
        }

             memberViewModel = ViewModelProviders.of(this).get(MemberViewModel.class);
            //update photo
            updatePhoto = findViewById(R.id.updatePhoto);
            updatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(imageView);

            }
        });

            //save member
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer = saveTheMember();
                if (integer == 0) {
                    Intent save = new Intent(SetMember.this, settings.class);
                    startActivity(save);
                }

            }
        });

        //delete member
        deleteMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer = deleteMember();
                if (integer == 0) {
                    Intent delete = new Intent(SetMember.this, settings.class);
                    startActivity(delete);
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
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                path = saveToInternalStorage(bitmap, cw);
                imageView.setImageBitmap(bitmap);
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

        Member mMember = memberViewModel.getMember(muid);
        //get string
        String updateStringMember = editText.getText().toString();
        String photo = path;
        if(updateStringMember.trim().isEmpty()){
            Toast.makeText(SetMember.this,getResources().getString(R.string.fill_name), Toast.LENGTH_SHORT).show();
            return -1;
        }
        if(photo.trim().isEmpty()){
            photo = photo1;
        }
        Intent save = new Intent();
        save.putExtra(EXSTRA_NAME, updateStringMember);
        save.putExtra(EXSTRA_PHOTO,photo);
        setResult(RESULT_OK, save);
        //set
        mMember.setmName(updateStringMember);
        mMember.setmPhoto(photo);
        //update
        memberViewModel.updateMember(mMember);
        Toast.makeText(this,getResources().getString(R.string.relative_updated), Toast.LENGTH_SHORT).show();
        finish();
        return 0;
    }
    private int deleteMember(){
        Member mMember = memberViewModel.getMember(muid);
        memberViewModel.deleteMember(mMember);
        Toast.makeText(this,getResources().getString(R.string.member_deleted), Toast.LENGTH_SHORT).show();
        finish();
        return 0;
    }
    }

