package com.example.agriculturalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.agriculturalproject.GlobalClasses.Global;
import com.example.agriculturalproject.Models.Boxes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class    TemperatureSystem extends AppCompatActivity {
    TextView plant_temp_ac , plant_temp_fan;
    DatabaseReference Box;

    DrawerLayout drawerLayout ;
    TextView nav_email,nav_name ;//for main_nav_ drawer

    //here starts circle progress code
    private ProgressBar progressBar;
    private TextView progressText;
    int i = 0;
    //here to set the value from firebase
    int m = 100;
    //here it ends
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempruture_system);
        Box = FirebaseDatabase.getInstance().getReference("Boxes"); //Boxes table from firebase
        drawerLayout = findViewById(R.id.main_drawer_layout);
        getSupportActionBar().hide();//hide tool bar


        plant_temp_ac = findViewById(R.id.plant_temp_AC);
        plant_temp_fan = findViewById(R.id.plant_temp_fan);

        Box.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Boxes box = snapshot.child(Global.currentBoxes.getId_box()).getValue(Boxes.class);
                int m = 0;
                if (!box.getTemperature().equals("nan")){
                    float f = Float.parseFloat(box.getTemperature());
                    m = Math.round(f);
                }
                plant_temp_ac.setText(box.getAC() );
                plant_temp_fan.setText(box.getFan() );
                //here start code for circle progress
                progressBar = findViewById(R.id.progress_bar_temperature);
                progressText = findViewById(R.id.progress_text_temperature);
                int finalM = m;
                progressText.setText(String.valueOf(finalM));
                progressBar.setProgress(finalM);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        nav_email = findViewById(R.id.txt_view_email);
        nav_email.setText(Global.currentUser.getEmail());

        nav_name = findViewById(R.id.txt_view_name);
        nav_name.setText(Global.currentUser.getFirstName() + " "+ Global.currentUser.getLastName() );

        BottomNavigationView btmNav = findViewById(R.id.bottom_nav_temperature);//take FROM layout()
        btmNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.back_temperature://back to last page
                        //take from menu(bar_profile.xml)
                        finish();

                        break;


                }

            }

        });




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
    public void Clickprivacypolicy(View view){redirectActivity(this,privacypolicy.class);}
    public void Clickcontactus(View view){redirectActivity(this,contactus.class);}
    public void Clickmap(View view){redirectActivity(this,Map.class);}

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