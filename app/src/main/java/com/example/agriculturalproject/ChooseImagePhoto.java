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

        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGallery();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            try{
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                File cachePath = new File(getApplicationContext().getCacheDir(), "images");
                cachePath.mkdirs(); // don't forget to make the directory
                FileOutputStream stream = new FileOutputStream(cachePath + "/image.jpg"); // overwrites this image every time
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.close();
                File imagePath = new File(this.getCacheDir(), "images");
                File newFile = new File(imagePath, "image.jpg");
//                Uri contentUri = FileProvider.getUriForFile(this, "com.jayvaghasiya.test.FileProvider", newFile);
                changeScreen(imageUri);
//                plantImage.setImageBitmap(selectedImage);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void changeScreen(Uri imageUri) {
        Toast.makeText(this, "21212", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, ImageProcess.class);
        i.putExtra("ImageUri", imageUri);
        startActivity(i);
    }

    private void goToGallery() {
        Intent selectPhoto = new Intent(Intent.ACTION_PICK);
        selectPhoto.setType("image/*");
        startActivityForResult(selectPhoto, RESULT_LOAD_IMG);

    }
}