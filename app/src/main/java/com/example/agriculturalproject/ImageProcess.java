package com.example.agriculturalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageProcess extends AppCompatActivity {
    ImageView plantImageView;
    Button cancelButton;
    Button confirmButton;
    Uri imageUri;
    String selectedImagePath;//path for img

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_process);
        plantImageView = findViewById(R.id.plantImageView);
        setImage();
        cancelButton = findViewById(R.id.cancelButton);
        confirmButton = findViewById(R.id.confirmButton);
        ActivityCompat.requestPermissions(ImageProcess.this, new String[]{Manifest.permission.INTERNET}, 2);
                                                                                      //INTERNET(request type 2) == allow to get img from page to another
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ImageProcess.this, MainActivity.class);
                startActivity(i);
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectServer(v);//connectServer == connect to api(python)
            }
        });

    }
    private void connectServer(View v) {

        String postUrl= "http://167.71.63.72:80/";//api link
        ByteArrayOutputStream stream = new ByteArrayOutputStream();//initialise ByteArray
        BitmapFactory.Options options = new BitmapFactory.Options();//initialise BitmapFactory
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        try {

            selectedImagePath = getPath(getApplicationContext(), imageUri);
            Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath, options);//convert img to Bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG , 100, stream);//best performance and store in stream
            byte[] byteArray = stream.toByteArray();//convert img to zero ir one after store in byte array

            RequestBody postBodyImage = new MultipartBody.Builder()//class initialise ,send photo to api python
                    .setType(MultipartBody.FORM)//form type
                    .addFormDataPart("image", "image.jpg", RequestBody.create(MediaType.parse("image/*jpg"), byteArray))
                    .build();

            Toast.makeText(this, "Please wait ...", Toast.LENGTH_SHORT).show();

            postRequest(postUrl, postBodyImage);
        }  catch (Exception e) {
            e.printStackTrace();
        }

    }

    void postRequest(String postUrl  , RequestBody postBody) { //api,body for img
        OkHttpClient client = new OkHttpClient();//send req.() to api

        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .build();
        /*
        * post => create , process
        * get => return data
        * delete => delete
        * put , patch => update
        * */

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Cancel the post on failure.
                call.cancel();
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Failed to Connect to Server", Toast.LENGTH_SHORT).show();
                    }
                });



            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                Intent j = new Intent(getBaseContext(), ResultActivity.class);
                j.putExtra("result", response.body().string());//result
                startActivity(j);

            }
        });
    }


    private void setImage() {
        Bundle ex = getIntent().getExtras();
        imageUri = ex.getParcelable("ImageUri");
        plantImageView.setImageURI(imageUri);

    }





    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getApplicationContext(), "Access to Storage Permission Granted. Thanks.", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(getApplicationContext(), "Access to Storage Permission Denied.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getApplicationContext(), "Access to Internet Permission Granted. Thanks.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Access to Internet Permission Denied.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

}