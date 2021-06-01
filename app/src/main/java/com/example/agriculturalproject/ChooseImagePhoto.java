package com.example.agriculturalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriculturalproject.GlobalClasses.Global;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ChooseImagePhoto extends AppCompatActivity {
    CardView choose_image  , camera;

    Uri imageUri_Camera ; //Path (Image)
    TextView name_users ; //get name for user

    private int RESULT_LOAD_IMG = 1003; //code with open gallery
    private final  int PERMISSION_CODE = 1000; // code with PERMISSION
    private final int IMAGE_CODE_CAPTURE = 1001; //code with camera

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image_photo);
        getSupportActionBar().hide();//to hide tool bar


        ActivityCompat.requestPermissions(ChooseImagePhoto.this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA},
                1);
                                             //PERMISSION to open gallery
                                            //type of PERMISSION==READ_EXTERNAL_STORAGE


        choose_image = findViewById(R.id.cardGallery);
        camera = findViewById(R.id.cardCamera);
        name_users = findViewById(R.id.name_users);


        name_users.setText("Welcome, " + Global.currentUser.getFirstName()+ " " + Global.currentUser.getLastName() );

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openCamera();
            }
        });
        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGallery(); // Gallery simulation
            }
        });
        BottomNavigationView btmNav = findViewById(R.id.bottom_nav_ml);//take FROM layout()
        btmNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.back_ml://back to last page
                        //take from menu(bar_profile.xml)
                        finish();

                        break;


                }

            }

        });
    }

    private void openCamera() {
        ContentValues values = new ContentValues(); //Map(key,value)
        values.put(MediaStore.Images.Media.TITLE,"New Picture");//title
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the Camera");//description
        Intent Camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//open camera
        imageUri_Camera = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);//select camera path after CAPTURE
        Camera.putExtra(MediaStore.EXTRA_OUTPUT,imageUri_Camera);//store path
        startActivityForResult(Camera,IMAGE_CODE_CAPTURE);//onActivityResult

    }

    private void goToGallery() {
        Intent selectPhoto = new Intent(Intent.ACTION_PICK);
        selectPhoto.setType("image/*"); //type photo
        startActivityForResult(selectPhoto,RESULT_LOAD_IMG);//selectPhoto==path

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//resultCode == img exist or not
                                                                                             //data == img
                                                                                                //requestCode type (gallery or camera)

        if (resultCode == Activity.RESULT_OK) {//exist img
            if (requestCode == RESULT_LOAD_IMG){//gallery
                    final Uri imageUri = data.getData();//path for img
                    changeScreen(imageUri);

            }else if (requestCode == IMAGE_CODE_CAPTURE){//camera
                changeScreen(imageUri_Camera);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }


    //go to imageProcess with photo
    private void changeScreen(Uri imageUri) {
        Intent i = new Intent(this, ImageProcess.class);
        i.putExtra("ImageUri", imageUri);//path photo
        startActivity(i);//intent

    }





}