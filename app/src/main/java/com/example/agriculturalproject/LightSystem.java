package com.example.agriculturalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LightSystem extends AppCompatActivity {
    Switch led , led_auto;
    DatabaseReference Box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_system);
        led = findViewById(R.id.switch_led);
        led_auto = findViewById(R.id.switch_led_auto);
        Box = FirebaseDatabase.getInstance().getReference("Boxes"); //Boxes table from firebase



        led.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HashMap<String , Object> update_values = new HashMap<String, Object>();
                update_values.put("Led",String.valueOf(isChecked));


                Box.child("-MZT-_W4Ib79xESLf_D4").updateChildren(update_values);
            }
        });
        led_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HashMap<String , Object> update_values = new HashMap<String, Object>();
                update_values.put("Led_auto",String.valueOf(isChecked));


                Box.child("-MZT-_W4Ib79xESLf_D4").updateChildren(update_values);
            }
        });
    }
}