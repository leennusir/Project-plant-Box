package com.example.agriculturalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriculturalproject.Adapters.Adapter_Boxes;
import com.example.agriculturalproject.GlobalClasses.Global;
import com.example.agriculturalproject.InterFace.ItemClickListener;
import com.example.agriculturalproject.Models.Boxes;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    TextView name , hedarEmail; //
    ImageView imageView;


    CardView card1 , card2  ;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle( this , drawer ,toolbar ,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        name = headerView.findViewById(R.id.hedarName);
        name.setText(Global.currentUser.getFirstName()+" "+ Global.currentUser.getLastName());

        hedarEmail = headerView.findViewById(R.id.HedarEmail);
        hedarEmail.setText(Global.currentUser.getEmail());
        imageView = headerView.findViewById(R.id.imageViewUser);
        Picasso.get().load(Global.currentUser.getImg()).into(imageView);//to put defualt image
        card1 = findViewById(R.id.cardView1);
        card2 = findViewById(R.id.cardView2);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,list_box.class));
            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home)
        {
            Toast.makeText(this, "This is home", Toast.LENGTH_SHORT).show();

        }
        else if (id == R.id.nav_Profile)
        {
            Toast.makeText(this, "This is my profile ", Toast.LENGTH_SHORT).show();

        }
        else if (id == R.id.nav_addBox)
        {
            Toast.makeText(this, "This is add new box ", Toast.LENGTH_SHORT).show();

        }
        else if( id == R.id.nav_Logout)
        {
            Global.currentUser = null;
            startActivity(new Intent(Home.this , MainActivity.class));

        }

        return true;
    }
}