package com.example.agriculturalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ChooseImagePhoto extends AppCompatActivity {
    TextView choose_image ;
    private int RESULT_LOAD_IMG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image_photo);
        choose_image = findViewById(R.id.add_image_plant);

        ActivityCompat.requestPermissions(ChooseImagePhoto.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        //permission access from studio to app
        //request type

        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGallery(); // Gallery simulation
            }
        });
    }
    private void goToGallery() {
        Intent selectPhoto = new Intent(Intent.ACTION_PICK);
        selectPhoto.setType("image/*"); //image path  in phone
        startActivityForResult(selectPhoto,RESULT_LOAD_IMG);//selectPhoto==path
                                                            ////resultCode==empty
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//resultCode==empty
                                                                                             //data==img
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {//exist img
            try{
                final Uri imageUri = data.getData();//path for img
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);//convert path to img
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);//convert img to bit
                File cachePath = new File(getApplicationContext().getCacheDir(), "images");//go to img directory
                cachePath.mkdirs(); //(create folder)
                FileOutputStream stream = new FileOutputStream(cachePath + "/image.jpg"); // overwrites this image every time
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.close();
                File imagePath = new File(this.getCacheDir(), "images");
                changeScreen(imageUri);
            }
            catch (FileNotFoundException e) {//print error
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void changeScreen(Uri imageUri) {
        Intent i = new Intent(this, ImageProcess.class);
        i.putExtra("ImageUri", imageUri);//photo
        startActivity(i);
    }


}