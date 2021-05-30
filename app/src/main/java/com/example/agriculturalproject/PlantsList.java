package com.example.agriculturalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriculturalproject.Adapters.Adapter_Boxes;
import com.example.agriculturalproject.Adapters.Adapter_Plants;
import com.example.agriculturalproject.GlobalClasses.Global;
import com.example.agriculturalproject.InterFace.ItemClickListener;
import com.example.agriculturalproject.Models.Boxes;
import com.example.agriculturalproject.Models.Plants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
//for guide page
public class PlantsList extends AppCompatActivity {
    RecyclerView recyclerPlants;
    DatabaseReference Plant;//to get boxes from firebase()
    FirebaseRecyclerAdapter<Plants, Adapter_Plants> related_Plants ; //Boxes = class
    DrawerLayout drawerLayout ;
    TextView nav_email,nav_name ;//for main_nav_drawar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants_list);
        drawerLayout = findViewById(R.id.main_drawer_layout);
        getSupportActionBar().hide();//hide tool bar

        recyclerPlants = findViewById(R.id.recycle_plant);//recycle_plant==هو اللي بنعرض جواته البوكسات
        recyclerPlants.setHasFixedSize(true);//to make recyclerview Fixed
        recyclerPlants.setLayoutManager(new GridLayoutManager(this , 1));//1 or any num card in the same line(ع السطر الواحد كم بوكس)
        Plant = FirebaseDatabase.getInstance().getReference("Plants"); //Boxes table from firebase
        getPlants(); //function(call)
        nav_email = findViewById(R.id.txt_view_email);
        nav_email.setText(Global.currentUser.getEmail());

        nav_name = findViewById(R.id.txt_view_name);
        nav_name.setText(Global.currentUser.getFirstName() + " "+ Global.currentUser.getLastName() );
    }

    private void getPlants() {
        related_Plants = new FirebaseRecyclerAdapter<Plants, Adapter_Plants>(Plants.class,R.layout.card_plants,Adapter_Plants.class,Plant) {
            @Override
            protected void populateViewHolder(Adapter_Plants adapter_plants, Plants plants, int i) {
                Picasso.get().load(plants.getImg()).into(adapter_plants.plant_card_image);//to put defualt image
                adapter_plants.plant_card_name.setText(plants.getName());
                adapter_plants.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int pos, boolean isLongClick) {
                        Global.currentPlants = plants;
                        startActivity(new Intent(PlantsList.this,Plants_information.class));
                    }
                });
            }
        };
        recyclerPlants.setAdapter(related_Plants);
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