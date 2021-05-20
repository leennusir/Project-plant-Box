package com.example.agriculturalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.CompoundButton;
import android.widget.Switch;
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

import java.util.HashMap;

public class LightSystem extends AppCompatActivity {
    Switch led ;
    TextView led_auto ;
    DatabaseReference Box;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_system);
        led = findViewById(R.id.switch_led);
        led_auto = findViewById(R.id.switch_led_auto);
        Box = FirebaseDatabase.getInstance().getReference("Boxes"); //Boxes table from firebase


        led.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HashMap<String , Object> update_values = new HashMap<String, Object>();
                update_values.put("Led",String.valueOf(isChecked));

                if(isChecked){
                    addNotification("The light is on!");
                }

                Box.child(Global.currentBoxes.getId_box()).updateChildren(update_values);
                final Handler handler = new Handler(Looper.getMainLooper());//timer
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isChecked){
                            addNotification("The light is still going too far");

                        }
                    }
                }, 30000);
            }
        });
        Box.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Boxes box = snapshot.child(Global.currentBoxes.getId_box()).getValue(Boxes.class);
                led_auto.setText(box.getLed_auto());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
    private void addNotification(String msg) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(LightSystem.this,
                default_notification_channel_id )
                .setSmallIcon(R.drawable.ic_launcher_foreground )//ic_launcher_foreground== pic on notification
                .setContentTitle( "Smart Box" )
                .setContentText( msg );
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE ) ;//class NotificationManager  get NotificationService
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ) {//android version must be more or equal VERSION_CODES(my app)
            int importance = NotificationManager.IMPORTANCE_HIGH ;//
            NotificationChannel notificationChannel = new
                    NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;//create a object from channel
            notificationChannel.enableLights( true ) ;
            notificationChannel.setLightColor(Color. RED ) ;
            notificationChannel.enableVibration( true ) ;
            notificationChannel.setVibrationPattern( new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 }) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ; //create channel
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis (), mBuilder.build()) ; //run notification
    }
}