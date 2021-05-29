package com.example.agriculturalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {
    TextView x ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle ex = getIntent().getExtras();
        String disease = ex.getString("result");
        Toast.makeText(this, disease + " " + "ssss", Toast.LENGTH_SHORT).show();
        x = findViewById(R.id.causeTextView);
        x.setText(disease);


    }
}