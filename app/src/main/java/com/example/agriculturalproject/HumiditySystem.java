package com.example.agriculturalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.agriculturalproject.GlobalClasses.Global;

public class HumiditySystem extends AppCompatActivity {
    TextView plant_Humidity , plant_Soil_Moisture ,Fan ,AC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_system);

        plant_Humidity = findViewById(R.id.plant_humidity);
        plant_Humidity.setText(Global.currentBoxes.getHumidity());

        plant_Soil_Moisture = findViewById(R.id.plant_Soil_Moisture);
        plant_Soil_Moisture.setText(Global.currentBoxes.getSoil_Moisture());

        Fan = findViewById(R.id.plant_humidity_fan);
        Fan.setText(Global.currentBoxes.getFan());

        AC = findViewById(R.id.plant_humidity_AC);
        AC.setText(Global.currentBoxes.getAC());


    }
}