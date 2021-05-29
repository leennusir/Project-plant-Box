package com.example.agriculturalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriculturalproject.GlobalClasses.Global;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class contactus extends AppCompatActivity {
    DrawerLayout drawerLayout ;
    TextView nav_email,nav_name;
    private EditText eMsg;
    private Button btn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        getSupportActionBar().hide();
        eMsg = (EditText)findViewById(R.id.your_message);
        btn = (Button)findViewById(R.id.post_message);
        drawerLayout = findViewById(R.id.main_drawer_layout);
        nav_email = findViewById(R.id.txt_view_email);
        nav_email.setText(Global.currentUser.getEmail());
        nav_name = findViewById(R.id.txt_view_name);
        nav_name.setText(Global.currentUser.getFirstName() + " "+ Global.currentUser.getLastName() );
        BottomNavigationView btmNav = findViewById(R.id.bottom_nav_contactus);//take FROM layout(activity profile.xml)
        btmNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.back_contactus://back to last page
                        //take from menu(bar_profile.xml)
                        finish();

                        break;


                }

            }

        });

        
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_SEND);
                it.setType("message/html");
                it.putExtra(Intent.EXTRA_EMAIL , new String[] {"haithamodehodeh@gmail.com"} );
                it.putExtra(Intent.EXTRA_SUBJECT ,"Feedback from App");
                it.putExtra(Intent.EXTRA_TEXT ,"Message: "+eMsg.getText());
                try {
                    startActivity(Intent.createChooser(it,"Choose Mail App"));
                }
                catch (android.content.ActivityNotFoundException ex)
                {
                    Toast.makeText(contactus.this,"There are no email Clients",Toast.LENGTH_SHORT).show();
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
        redirectActivityto(this,Profile.class);
    }
    public void Clickprivacypolicy(View view){redirectActivityto(this,privacypolicy.class);}
    public void Clickcontactus(View view){redirectActivityto(this,contactus.class);}
    public void Clickmap(View view){redirectActivityto(this,Map.class);}

    private void redirectActivityto(Activity activity , Class aClass) {
        Intent obj = new Intent(activity,aClass);
        obj.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(obj);
    }
    public void ClickHome(View view){
        redirectActivityto(this, MainHome.class);
    }
    public void ClickLogout(View view){
        redirectActivityto(this,MainActivity.class);

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

    public void onResume() {
        super.onResume();
        //Get a Tracker (should auto-report)
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
