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

public class MainHome extends AppCompatActivity {
    DrawerLayout drawerLayout ; // for navigation
    TextView nav_email,nav_name;

    CardView card1 , card2  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        getSupportActionBar().hide();//to hide tool bar
        drawerLayout = findViewById(R.id.main_drawer_layout);

        nav_email = findViewById(R.id.txt_view_email);
        nav_email.setText(Global.currentUser.getEmail());

        nav_name = findViewById(R.id.txt_view_name);
        nav_name.setText(Global.currentUser.getFirstName() + " "+ Global.currentUser.getLastName() );
        card1 = findViewById(R.id.cardViewWater);
        card2 = findViewById(R.id.cardViewHumidity);

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
    drawerLayout.openDrawer(GravityCompat.START);//show navigation
    }
    public void ClickLogo(View view){//close when i click on drawer pic
        closeDrawer(drawerLayout);
    }
    private static void closeDrawer(DrawerLayout drawerLayout) {//this fun. responsible on close nav.
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void ClickProfile(View view)
    {
        redirectActivity(this,Profile.class);
    }

    public void Clickprivacypolicy(View view){redirectActivity(this,privacypolicy.class);}
    public void Clickcontactus(View view){redirectActivity(this,contactus.class);}
    private void redirectActivity(Activity activity , Class aClass) {
        Intent obj = new Intent(activity,aClass);
        obj.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(obj);
    }

    public void ClickHome(View view){
           recreate();  }//open now page

    public void ClickLogout(View view){
        redirectActivity(this,MainActivity.class);//go to home

    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);// click on any space to close navigation
    }
}