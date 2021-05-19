package com.example.agriculturalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.agriculturalproject.GlobalClasses.Global;
import com.example.agriculturalproject.Models.Boxes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class TemperatureSystem extends AppCompatActivity {
    TextView  plant_temp ;
    TextView plant_temp_ac , plant_temp_fan;
    DatabaseReference Box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempruture_system);
        Box = FirebaseDatabase.getInstance().getReference("Boxes"); //Boxes table from firebase

        plant_temp = findViewById(R.id.plant_temp);
        plant_temp_ac = findViewById(R.id.plant_temp_AC);
        plant_temp_fan = findViewById(R.id.plant_temp_fan);

        Box.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Boxes box = snapshot.child(Global.currentBoxes.getId_box()).getValue(Boxes.class);
                plant_temp.setText(box.getTemperature() );
                plant_temp_ac.setText(box.getAC() );
                plant_temp_fan.setText(box.getFan() );

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }
}