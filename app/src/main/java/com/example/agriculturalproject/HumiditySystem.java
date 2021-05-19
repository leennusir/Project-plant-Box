package com.example.agriculturalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriculturalproject.GlobalClasses.Global;
import com.example.agriculturalproject.Models.Boxes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class HumiditySystem extends AppCompatActivity {
    TextView plant_Humidity , plant_Soil_Moisture ,Fan ,AC;
    DatabaseReference boxes ;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_system);
        boxes = FirebaseDatabase.getInstance().getReference().child("Boxes");
        plant_Humidity = findViewById(R.id.plant_humidity);
        plant_Soil_Moisture = findViewById(R.id.plant_Soil_Moisture);
        Fan = findViewById(R.id.plant_humidity_fan);
        AC = findViewById(R.id.plant_humidity_AC);
        boxes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Boxes box = snapshot.child(Global.currentBoxes.getId_box()).getValue(Boxes.class);
                plant_Humidity.setText(box.getHumidity());
                plant_Soil_Moisture.setText(box.getSoil_Moisture());
                Fan.setText(box.getFan());
                AC.setText(box.getAC());
                if (box.getHumidity().equals("10")){
                    addNotification();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }
    private void addNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(HumiditySystem. this,
                default_notification_channel_id )
                .setSmallIcon(R.drawable. ic_launcher_foreground )
                .setContentTitle( "Test" )
                .setContentText( "Hello! This is my first push notification" );
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new
                    NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            notificationChannel.enableLights( true ) ;
            notificationChannel.setLightColor(Color. RED ) ;
            notificationChannel.enableVibration( true ) ;
            notificationChannel.setVibrationPattern( new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 }) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis (), mBuilder.build()) ;
    }
    }
