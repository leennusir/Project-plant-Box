package com.example.agriculturalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriculturalproject.GlobalClasses.Global;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Monitor extends AppCompatActivity {
    DrawerLayout drawerLayout ;
    TextView nav_email ,nav_name , plant_temp ,plant_Humidity;
    Switch led;
    DatabaseReference Box;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        Box = FirebaseDatabase.getInstance().getReference("Boxes"); //Boxes table from firebase

        drawerLayout = findViewById(R.id.main_drawer_layout);
        nav_email = findViewById(R.id.txt_view_email);
        nav_email.setText(Global.currentUser.getEmail());
        nav_name = findViewById(R.id.txt_view_name);
        led = findViewById(R.id.switch_led);

        plant_temp = findViewById(R.id.plant_temp);
        plant_Humidity = findViewById(R.id.plant_humidity);
        plant_temp.setText(Global.currentBoxes.getTemperature() );
        plant_Humidity.setText(Global.currentBoxes.getHumidity()  );

        led.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HashMap<String , Object> update_values = new HashMap<String, Object>();
                        update_values.put("Led",String.valueOf(isChecked));


                        Box.child("-MZT-_W4Ib79xESLf_D4").updateChildren(update_values);
            }
        });
        nav_name.setText(Global.currentUser.getFirstName() + " "+ Global.currentUser.getLastName() );
        getSupportActionBar().hide(); // hide the title bar
        Toast.makeText(this, Global.currentBoxes.getName(), Toast.LENGTH_SHORT).show();





    }
    public void OpenMenu(View view){
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }
    public void ClickProfile(View view){
        redirectActivity(this,Profile.class);
    }

    private void redirectActivity(Activity activity , Class aClass) {
        Intent obj = new Intent(activity,aClass);
        obj.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(obj);
    }

    public void ClickHome(View view){
        redirectActivity(this,MainHome.class);
    }
    public void ClickLogout(View view){
        redirectActivity(this,MainActivity.class);

    }

    private static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}