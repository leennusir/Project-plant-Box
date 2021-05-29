package com.example.agriculturalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agriculturalproject.Adapters.Adapter_Boxes;
import com.example.agriculturalproject.GlobalClasses.Global;
import com.example.agriculturalproject.InterFace.ItemClickListener;
import com.example.agriculturalproject.Models.Boxes;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class  list_box extends AppCompatActivity implements PaymentResultListener {//payment system (عملية الدفع)
    RecyclerView recyclerBoxes;
    DatabaseReference Box;//to get boxes from firebase()
    FirebaseRecyclerAdapter<Boxes, Adapter_Boxes> related_Boxes ; //Boxes = class

    DrawerLayout drawerLayout ;
    TextView nav_email,nav_name ;//for main_nav_drawar
    EditText name;
    Spinner   plant_name;//select the plant

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_box);
        drawerLayout = findViewById(R.id.main_drawer_layout);
        getSupportActionBar().hide();//hide tool bar

        recyclerBoxes = findViewById(R.id.recycle_box);//recycle_box==هو اللي بنعرض جواته البوكسات
        recyclerBoxes.setHasFixedSize(true);//to make recyclerview Fixed
        recyclerBoxes.setLayoutManager(new GridLayoutManager(this , 1));//1 or any num card in the same line(ع السطر الواحد كم بوكس)
        Box = FirebaseDatabase.getInstance().getReference("Boxes"); //Boxes table from firebase
        getBoxes(); //function(call)
        nav_email = findViewById(R.id.txt_view_email);
        nav_email.setText(Global.currentUser.getEmail());

        nav_name = findViewById(R.id.txt_view_name);
        nav_name.setText(Global.currentUser.getFirstName() + " "+ Global.currentUser.getLastName() );

        BottomNavigationView btmNav = findViewById(R.id.bottom_nav_list_box);
        btmNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.back://back to last page
                        finish();

                        break;

                    case R.id.my_table://add box button

                        add_boxes();
                        break;
                }

            }

        });

    }
    public void add_boxes(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(list_box.this);
        alertDialog.setTitle("Add new Boxes");
        alertDialog.setMessage("Please fill full information");
        LayoutInflater inflater = this.getLayoutInflater();
        View add_box_layout = inflater.inflate(R.layout.add_boxes,null);//from add_box.xml :get edt_box and selection ,put in this dialog.
        name = add_box_layout.findViewById(R.id.edt_name_box);
        plant_name = add_box_layout.findViewById(R.id.edt_plant_name);
        ArrayList<String> plants = new ArrayList<String>();
        plants.add("Apple");
        plants.add("Mushroom");
        plants.add("Banana");
        plants.add("Strawberries");
        plants.add("Orange");
        plants.add("Watermelon");
        plants.add("Cherries");
        ArrayAdapter<String> plantAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, plants);// concatenation between plants name and my special spinner
        plantAdapter.setDropDownViewResource(R.layout.spinner_layout);
        plant_name.setAdapter(plantAdapter);
//
        alertDialog.setView(add_box_layout);
        alertDialog.setIcon(R.drawable.icons8_plant_96);
        alertDialog.setPositiveButton("Add Box", new DialogInterface.OnClickListener() {// add box button
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // payment library
                Checkout checkout = new Checkout();// checkout class
                checkout.setKeyID("rzp_test_C2EodRkMQfOxSf");//id from website(api key)

                JSONObject object = new JSONObject();// like map
                String Samount = "50";
                int a = Math.round(Float.parseFloat(Samount)*50);
                try {
                    //PAYMENT SETTING
                    object.put("name","Smart Farm Payment");
                    object.put("description","This is pay box");
                    object.put("theme.color","#024D21");
                    object.put("currency","USD");
                    object.put("amount",a);
                    object.put("prefill.contact","0797652607");
                    object.put("prefill.email","leen.nsser99@gmail.com");
                    checkout.open(list_box.this,object);

                } catch (JSONException e) {
                    // LIBRARY
                    //SHOW ERROR
                    e.printStackTrace();
                }

            }
        });
        alertDialog.show();
    }



    private void getBoxes(){
        related_Boxes = new FirebaseRecyclerAdapter<Boxes, Adapter_Boxes>(Boxes.class , R.layout.card_box ,
                Adapter_Boxes.class ,Box.orderByChild("userId").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                                                         // "Boxes" = class here(img ,name)

            @Override
            protected void populateViewHolder(Adapter_Boxes adapter_boxes, Boxes boxes, int i) {
                Picasso.get().load(boxes.getImg()).into(adapter_boxes.img_box);//to put defualt image
                adapter_boxes.name_box.setText(boxes.getName());
                adapter_boxes.nameplant.setText(boxes.getPlant());

                adapter_boxes.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int pos, boolean isLongClick) {
                        boxes.setId_box(related_Boxes.getRef(pos).getKey());
                        Global.currentBoxes = boxes;

                        startActivity(new Intent(list_box.this , Monitor.class));
                    }
                });
            }
        };
        recyclerBoxes.setAdapter(related_Boxes);
    }

    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pay id");
        builder.setMessage("Successful purchase");
        Boxes boxes = new Boxes("@drawable/iconbox",
                name.getText().toString(),
                plant_name.getSelectedItem().toString(),
                FirebaseAuth.getInstance().getCurrentUser().getUid()
                ,"OFF", "nan" ,"nan" , "0.00","360","ON","ON","ON","ON");
        Box.push().setValue(boxes);
        builder.show();
    }

    @Override
    public void onPaymentError(int i, String s) {

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
    public void Clickmap(View view){redirectActivity(this,Map.class);}

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