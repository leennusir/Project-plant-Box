package com.example.agriculturalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.agriculturalproject.GlobalClasses.Global;
import com.example.agriculturalproject.Models.Plants;

public class MainHome extends AppCompatActivity {
    DrawerLayout drawerLayout ;
    TextView nav_email,nav_name;

    CardView card1 , card2  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        getSupportActionBar().hide();
        drawerLayout = findViewById(R.id.main_drawer_layout);

        nav_email = findViewById(R.id.txt_view_email);
        nav_email.setText(Global.currentUser.getEmail());
        nav_name = findViewById(R.id.txt_view_name);
        nav_name.setText(Global.currentUser.getFirstName() + " "+ Global.currentUser.getLastName() );
        card1 = findViewById(R.id.cardView1);
        card2 = findViewById(R.id.cardView2);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainHome.this,list_box.class));
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainHome.this, PlantsList.class));
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

    private void redirectActivity(Activity activity , Class aClass) {
        Intent obj = new Intent(activity,aClass);
        obj.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(obj);
    }

    public void ClickHome(View view){
recreate();
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