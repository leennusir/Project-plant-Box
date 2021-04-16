package com.example.agriculturalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.agriculturalproject.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class RegisterUser extends AppCompatActivity {
    EditText FirstName , LastName , EmailPhone,Password_reg, Confirm_reg ;
    Button SignUp;
    private FirebaseAuth mAuth;
    Spinner select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        FirstName = findViewById(R.id.edt_reg_first_name);
        LastName = findViewById(R.id.edt_reg_last_name);
        EmailPhone = findViewById(R.id.edt_reg_email_phone);
        Password_reg = findViewById(R.id.edt_reg_pass);
        Confirm_reg = findViewById(R.id.edt_reg_pass_confirm);

        SignUp = findViewById(R.id.btn_reg_signup);
        mAuth = FirebaseAuth.getInstance();
        select = findViewById(R.id.spinner_id);

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

        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the your spinner
        select.setAdapter(countryAdapter);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edt_reg_f_name_str = FirstName.getText().toString().trim() ;
                String edt_reg_l_name_str = LastName.getText().toString().trim() ;
                String edt_reg_email_str = EmailPhone.getText().toString().trim();
                String edt_reg_pass_str = Password_reg.getText().toString().trim();
                String edt_reg_confirm_str = Confirm_reg.getText().toString().trim();
                String edt_reg_select_city = select.getSelectedItem().toString();

                if (edt_reg_f_name_str.isEmpty()){
                    Toast.makeText(RegisterUser.this, "The First Name Is Empty", Toast.LENGTH_SHORT).show();
                    return;}

                if (edt_reg_l_name_str.isEmpty()){
                    Toast.makeText(RegisterUser.this, "The Last Name Is Empty", Toast.LENGTH_SHORT).show();
                    return;}


                if (edt_reg_email_str.isEmpty()){
                    Toast.makeText(RegisterUser.this, "The Email  Is Empty", Toast.LENGTH_SHORT).show();
                    return;}


                if(edt_reg_pass_str.isEmpty())
                {
                    Toast.makeText(RegisterUser.this, "The password is empty", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if(edt_reg_pass_str.length() < 6){
                        Toast.makeText(RegisterUser.this, "The password length < 6 ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (edt_reg_confirm_str.isEmpty()){
                    Toast.makeText(RegisterUser.this, "The Confirm Password  Is Empty", Toast.LENGTH_SHORT).show();
                    return;}
                if (!edt_reg_pass_str.equals(edt_reg_confirm_str))
                {
                    Toast.makeText(RegisterUser.this, "The Confirm Password  Is Error", Toast.LENGTH_SHORT).show();
                    return;
                }


                //TO CHICK THE EMAIL FORMAT IS CORRECT
                if(!Patterns.EMAIL_ADDRESS.matcher(edt_reg_email_str).matches()){
                    Toast.makeText(RegisterUser.this, "This msh email ", Toast.LENGTH_SHORT).show();
                    return;
                }
                //TO MAKE RECORD IN FIREBASE
                mAuth.createUserWithEmailAndPassword(edt_reg_email_str,edt_reg_pass_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterUser.this, "The record success", Toast.LENGTH_SHORT).show();
                            Users stu = new Users(edt_reg_f_name_str , edt_reg_l_name_str , edt_reg_email_str ,edt_reg_pass_str,edt_reg_select_city,"https://i.ibb.co/PNJXysd/blankprofilepicture973460640.png");//Object from record (insert realtime database)
                            FirebaseDatabase.getInstance().getReference("Users").//هو المسؤول عن اضافة كلاسuser   في fb
                                    child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(stu).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(RegisterUser.this, "Complete", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegisterUser.this , MainActivity.class));


                                            }
                                            else
                                                Toast.makeText(RegisterUser.this, "not Complete ", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }else {
                            Toast.makeText(RegisterUser.this, "The registration not complete", Toast.LENGTH_SHORT).show();//to insert recored in Auth()

                        }

                    }
                });






            }
        });
        BottomNavigationView btmNav = findViewById(R.id.bottom_register);
        btmNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.back:
                        finish();

                        break;
                    case R.id.Home:
                        Toast.makeText(RegisterUser.this, "11", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.my_table:
                        Toast.makeText(RegisterUser.this, "1dd1", Toast.LENGTH_SHORT).show();

                        break;
                }

            }

        });


    }
}