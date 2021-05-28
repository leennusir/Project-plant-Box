package com.example.agriculturalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.agriculturalproject.GlobalClasses.Global;
import com.hololo.tutorial.library.PermissionStep;
import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class Plants_information extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addFragment(new Step.Builder().setTitle("Welcome to the special instructions at "+Global.currentPlants.getName())
                .setContent("Here are all the tips and tricks to help growers grow a plant " + Global.currentPlants.getName())
                .setBackgroundColor(Color.parseColor("#6D63EF")) // int background color
                .setDrawable(R.drawable.icons8plant00) // int top drawable
                .setSummary("1")
                .build());
        addFragment(new Step.Builder().setTitle("Temperature")
                .setContent("The plant can "+ Global.currentPlants.getName()+" live at a temperature "+Global.currentPlants.getTemperature()+ "° C so we recommend that farmers plant it at this temperature")
                .setBackgroundColor(Color.parseColor("#26A6B5")) // int background color
                .setDrawable(R.drawable.icons8_temperature_sensitive_100) // int top drawable
                .setSummary("2")
                .build());
        addFragment(new Step.Builder().setTitle("Humidity")
                .setContent("Remember very much that the " +Global.currentPlants.getName()+ " plant should have about "+Global.currentPlants.getHumidity()+" humidity in it, and if it gets too high or below the limit, it may die.")
                .setBackgroundColor(Color.parseColor("#F89688")) // int background color
                .setDrawable(R.drawable.icons8_humidity_100) // int top drawabledd
                .setSummary("3")
                .build());
        addFragment(new Step.Builder().setTitle("Water")
                .setContent("Remember well that feeding the plant with water is one of the most important things in the cultivation process, and therefore the water in the "+ Global.currentPlants.getName()+" plant should be about "+Global.currentPlants.getWater_level())
                .setBackgroundColor(Color.parseColor("#6CF8AC")) // int background color
                .setDrawable(R.drawable.icons8_water_100) // int top drawabledd
                .setSummary("4")
                .build());
    }
    @Override
    public void finishTutorial() {
finish();
    }
    @Override
    public void currentFragmentPosition(int position) {

    }
}