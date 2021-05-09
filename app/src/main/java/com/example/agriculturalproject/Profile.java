package com.example.agriculturalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriculturalproject.GlobalClasses.Global;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class Profile extends AppCompatActivity {
    EditText FirstName , LastName;
    Spinner Spinner ;
    Button update ,update_password;
    EditText new_password , old_password , confirm_password;
    DrawerLayout drawerLayout ;
    TextView x ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        FirstName = findViewById(R.id.edt_profile_Fname);
        LastName = findViewById(R.id.edt_profile_Lname);
        Spinner = findViewById(R.id.edt_spinner_profile);
        update = findViewById(R.id.btn_profile_update);
        update_password = findViewById(R.id.btn_profile_change_pass);

        FirstName.setText(Global.currentUser.getFirstName());//PUT THE USER CLASS INFO. IN PROFILE PAGE
        LastName.setText(Global.currentUser.getLastName());
        drawerLayout = findViewById(R.id.main_drawer_layout);
        x = findViewById(R.id.xxxxx);
        x.setText(Global.currentUser.getEmail());
        getSupportActionBar().hide(); // hide the title bar

        //SELECT CITY BUTTON (CODE)
        Locale[] locales = Locale.getAvailableLocales();//eVERY AVALBEL LOCATION
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {//IF COUNTRY LENGTH LESS THAN 0 AND NOT EXIST IN LIST
                countries.add(country);
            }
        }

        Collections.sort(countries);


        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, countries);

        countryAdapter.setDropDownViewResource(R.layout.spinner_layout);
        // Apply the adapter to the your spinner
        Spinner.setAdapter(countryAdapter);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String , Object> update_values = new HashMap<String, Object>();
                update_values.put("firstName",FirstName.getText().toString());
                                  //firstName(from FB) == key
                                  //FirstName.getText() == Value (from edit text)
                update_values.put("lastName",LastName.getText().toString());
                update_values.put("select",Spinner.getSelectedItem().toString());

                FirebaseDatabase.getInstance().getReference("Users").//هو المسؤول عن تعديل القيم في fb
                 child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(update_values);
                Global.currentUser.setFirstName(FirstName.getText().toString());
                Global.currentUser.setLastName(LastName.getText().toString());

                Toasty.success(Profile.this, "Success!", Toast.LENGTH_SHORT, true).show();


            }
        });
        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_password();
            }
        });

        BottomNavigationView btmNav = findViewById(R.id.bottom_nav_profile);//take FROM layout(activity profile.xml)
        btmNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.back_profile://back to last page
                                           //take from menu(bar_profile.xml)
                        finish();

                        break;


                }

            }

        });


    }

    private void change_password() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Profile.this);
        alertDialog.setTitle("Change Password");
        alertDialog.setMessage("Please fill full information");
        LayoutInflater inflater = this.getLayoutInflater();
        View add_box_layout = inflater.inflate(R.layout.change_password,null);
        old_password = add_box_layout.findViewById(R.id.edt_pass_old);
        new_password = add_box_layout.findViewById(R.id.edt_pass_new);
        confirm_password = add_box_layout.findViewById(R.id.edt_re_new_password);

        alertDialog.setView(add_box_layout);
        alertDialog.setIcon(R.drawable.icons8_plant_96);
        alertDialog.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {// add box button
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 FirebaseUser user;
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (!new_password.getText().toString().equals(confirm_password.getText().toString())){
                    Toasty.error(Profile.this, "password does not match", Toast.LENGTH_SHORT, true).show();
                    return;
                }else {
                    AuthCredential credential = EmailAuthProvider.getCredential(Global.currentUser.getEmail(),old_password.getText().toString());
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(new_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toasty.success(Profile.this, "Success!", Toast.LENGTH_SHORT, true).show();

                                                } else {
                                                    Toasty.error(Profile.this, "Not!", Toast.LENGTH_SHORT, true).show();

                                                }
                                            }
                                        });
                                    } else {
                                        Toasty.error(Profile.this, "Not!", Toast.LENGTH_SHORT, true).show();

                                    }
                                }
                            });
                }




            }
        });
        alertDialog.show();



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