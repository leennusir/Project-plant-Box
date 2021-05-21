package com.example.agriculturalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriculturalproject.GlobalClasses.Global;
import com.example.agriculturalproject.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText email_phone , password ;
    TextView forget ,crate;
    Button login , api_python;
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        email_phone = findViewById(R.id.edt_email_phone);
        password = findViewById(R.id.edt_pass);
        forget = findViewById(R.id.edt_for);
        crate = findViewById(R.id.edt_cna);
        login = findViewById(R.id.btn_log);
        fAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");//access to all data about this user

        api_python = findViewById(R.id.btn_log2);
        api_python.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChooseImagePhoto.class));

            }
        });
        crate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,RegisterUser.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_phone_str = email_phone.getText().toString().trim() ;
                String password_str = password.getText().toString().trim() ;

                if (email_phone_str.isEmpty()){
                    Toast.makeText(MainActivity.this, "The Email or Phone is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password_str.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "The Password is empty", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if(password_str.length() < 6){
                        Toast.makeText(MainActivity.this, "The Password length < 6 ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                mAuth.signInWithEmailAndPassword(email_phone_str , password_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Users user = snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(Users.class);//control in data(update,delete,insert )
                                    Global.currentUser = user;
                                    startActivity(new Intent(MainActivity.this , MainHome.class));//access to home page(drawable)

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                        else
                            Toast.makeText(MainActivity.this, "The password or email is error", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder PasswordRestDialog = new AlertDialog.Builder(v.getContext());
                PasswordRestDialog.setTitle("Reset Password ?");
                PasswordRestDialog.setMessage("Enter Your Email To Received Reset Link");
                PasswordRestDialog.setView(resetMail);

                PasswordRestDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // EXTRACT THE EMAIL & SEND RESET LINK
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Reset Link To Your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error ! Reset Link Is Not Sent"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                PasswordRestDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // CLOSE THE DIALOG

                    }
                });
                PasswordRestDialog.create().show();

            }
        });
        
    }
}