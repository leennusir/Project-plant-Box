package com.example.agriculturalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ChooseImagePhoto extends AppCompatActivity {
    TextView choose_image  , camera;
    private int RESULT_LOAD_IMG = 1003;
    private final  int PERMISSION_CODE = 1000;
    private final int IMAGE_CODE_CAPTURE = 1001;
    Uri imageUri_Camera ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image_photo);
        choose_image = findViewById(R.id.add_image_plant);

        ActivityCompat.requestPermissions(ChooseImagePhoto.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        //permission access from studio to app
        //request type
        camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED ){

                        String[] per = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(per,PERMISSION_CODE);
                    }else {
                        openCamera();
                    }
                }else {
                    openCamera();
                }
            }
        });
        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGallery(); // Gallery simulation
            }
        });
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the Camera");
        imageUri_Camera = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent Camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Camera.putExtra(MediaStore.EXTRA_OUTPUT,imageUri_Camera);
        startActivityForResult(Camera,IMAGE_CODE_CAPTURE);

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
        Toast.makeText(this, String.valueOf(requestCode), Toast.LENGTH_SHORT).show();

        if (resultCode == Activity.RESULT_OK) {//exist img
            if (requestCode == RESULT_LOAD_IMG){
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
            }else if (requestCode == 1001){
                changeScreen(imageUri_Camera);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_DENIED){
                    openCamera();
                }else {
//                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void changeScreen(Uri imageUri) {
        Intent i = new Intent(this, ImageProcess.class);
        i.putExtra("ImageUri", imageUri);//photo
        startActivity(i);
    }


}