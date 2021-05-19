package com.example.agriculturalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.agriculturalproject.GlobalClasses.Global;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TemperatureSystem extends AppCompatActivity {
    TextView  plant_temp ;
    Switch plant_temp_ac , plant_temp_fan;
    DatabaseReference Box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempruture_system);
        Box = FirebaseDatabase.getInstance().getReference("Boxes"); //Boxes table from firebase

        plant_temp = findViewById(R.id.plant_temp);
        plant_temp.setText(Global.currentBoxes.getTemperature() );

        plant_temp_ac = findViewById(R.id.plant_temp_AC);
        plant_temp_ac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HashMap<String , Object> update_values = new HashMap<String, Object>();
                update_values.put("AC",String.valueOf(isChecked));


                Box.child("-MZT-_W4Ib79xESLf_D4").updateChildren(update_values);
            }
        });

        plant_temp_fan = findViewById(R.id.plant_temp_fan);
        plant_temp_ac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HashMap<String , Object> update_values = new HashMap<String, Object>();
                update_values.put("Fan",String.valueOf(isChecked));


                Box.child("-MZT-_W4Ib79xESLf_D4").updateChildren(update_values);
            }
        });


    }
}