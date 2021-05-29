package com.example.agriculturalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;

public class ResultActivity extends AppCompatActivity {
    TextView name_plant , disease_plant ;
    FloatingActionButton fab ;
    ImageView disease_detection_image;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().hide();//to hide tool bar
        Bundle ex = getIntent().getExtras();
        fab = findViewById(R.id.di_button_rest);
        disease_plant = findViewById(R.id.disease_detection);
        name_plant = findViewById(R.id.name_plant_di);
        String disease = ex.getString("result");
        imageUri = ex.getParcelable("image");
        String[] arrOfStr = disease.split("___");
        name_plant.setText(arrOfStr[0]);
        disease_plant.setText(arrOfStr[1]);
        disease_detection_image = findViewById(R.id.disease_detection_image);
        disease_detection_image.setImageURI(imageUri);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultActivity.this, ChooseImagePhoto.class);
                Arrays.fill(arrOfStr, null);
                startActivity(i);
            }
        });

    }
}