package com.example.agriculturalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriculturalproject.GlobalClasses.Global;
import com.example.agriculturalproject.Models.Boxes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class HumiditySystem extends AppCompatActivity {
    TextView plant_Humidity , plant_Soil_Moisture ,Fan ,AC;
    DatabaseReference boxes ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_system);
        boxes = FirebaseDatabase.getInstance().getReference().child("Boxes");
        plant_Humidity = findViewById(R.id.plant_humidity);
        plant_Soil_Moisture = findViewById(R.id.plant_Soil_Moisture);
        Fan = findViewById(R.id.plant_humidity_fan);
        AC = findViewById(R.id.plant_humidity_AC);
        boxes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Boxes box = snapshot.child(Global.currentBoxes.getId_box()).getValue(Boxes.class);
                plant_Humidity.setText(box.getHumidity());
                plant_Soil_Moisture.setText(box.getSoil_Moisture());
                Fan.setText(box.getFan());
                AC.setText(box.getAC());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }
}