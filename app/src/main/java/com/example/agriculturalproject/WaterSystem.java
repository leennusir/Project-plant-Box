package com.example.agriculturalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.agriculturalproject.GlobalClasses.Global;

public class WaterSystem extends AppCompatActivity {
    TextView  plant_Water_Level , plant_pump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_system);
        plant_Water_Level =  findViewById(R.id.plant_Water_Level);
        plant_pump = findViewById(R.id.plant_pump);

        plant_Water_Level.setText(Global.currentBoxes.getWater_Level());
        plant_pump.setText(Global.currentBoxes.getPump());
    }
}