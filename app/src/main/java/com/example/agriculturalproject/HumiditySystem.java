package com.example.agriculturalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriculturalproject.GlobalClasses.Global;
import com.example.agriculturalproject.Models.Boxes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class HumiditySystem extends AppCompatActivity {
    TextView Fan ,AC;
    DatabaseReference boxes ;
    //here starts circle progress code
    private ProgressBar progressBar,progressBarsoil;
    private TextView progressText,progressTextsoil;
    //here to set the value from firebase
//here it ends
    DrawerLayout drawerLayout ;
    TextView nav_email,nav_name ;//for main_nav_drawar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_system);

        drawerLayout = findViewById(R.id.main_drawer_layout);
        getSupportActionBar().hide();//hide tool bar


        boxes = FirebaseDatabase.getInstance().getReference("Boxes");
        Fan = findViewById(R.id.plant_humidity_fan);
        AC = findViewById(R.id.plant_humidity_AC);
        boxes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Boxes box = snapshot.child(Global.currentBoxes.getId_box()).getValue(Boxes.class);
                Fan.setText(box.getFan());
                AC.setText(box.getAC());
                //here start code for circle progress
                int m = 0;
                int n=0;

                if (!box.getHumidity().equals("nan")){
                    float f = Float.parseFloat(box.getHumidity());
                    m = Math.round(f);
                }
                AC.setText(box.getAC() );
                Fan.setText(box.getFan() );
                //here start code for circle progress
                progressBar = findViewById(R.id.progress_bar_humidity);
                progressText = findViewById(R.id.progress_text_humidity);
                int finalM = m;
                progressText.setText(String.valueOf(finalM));
                progressBar.setProgress(finalM);

                //---------------------------------------------------


                if(!box.getSoil_Moisture().equals("nan"))
                {
                    float  f =  Float.parseFloat(box.getSoil_Moisture());
                    n= Math.round(f);
                }
                //here start code for circle progress
                progressBarsoil = findViewById(R.id.progress_bar_soil);
                progressTextsoil = findViewById(R.id.progress_text_soil);
                int finalN = n;
                progressTextsoil.setText(String.valueOf(finalN));
                progressBarsoil.setProgress(finalN);

            }



            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        nav_email = findViewById(R.id.txt_view_email);
        nav_email.setText(Global.currentUser.getEmail());

        nav_name = findViewById(R.id.txt_view_name);
        nav_name.setText(Global.currentUser.getFirstName() + " "+ Global.currentUser.getLastName() );

        BottomNavigationView btmNav = findViewById(R.id.bottom_nav_humidity);//take FROM layout()
        btmNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.back_humidity://back to last page
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