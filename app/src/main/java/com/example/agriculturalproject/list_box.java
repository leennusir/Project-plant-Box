package com.example.agriculturalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.agriculturalproject.Adapters.Adapter_Boxes;
import com.example.agriculturalproject.InterFace.ItemClickListener;
import com.example.agriculturalproject.Models.Boxes;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class list_box extends AppCompatActivity {
    RecyclerView recyclerBoxes;
    DatabaseReference Box;//to get boxes from firebase
    FirebaseRecyclerAdapter<Boxes, Adapter_Boxes> related_Boxes ; //Boxes = class
    //Adapter_Boxes = class
    // connect between Boxes and adapter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_box);

                recyclerBoxes = findViewById(R.id.recycle_box);
        recyclerBoxes.setHasFixedSize(true);//to make recyclerview Fixed
        recyclerBoxes.setLayoutManager(new GridLayoutManager(this , 2));//2 card in the same line
        Box = FirebaseDatabase.getInstance().getReference("Boxes"); //Boxes table from firebase
        getBoxes(); //function(call)

        BottomNavigationView btmNav = findViewById(R.id.bottom_nav_list_box);
        btmNav.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.back:
                        finish();

                        break;
                    case R.id.Home:
                        Toast.makeText(list_box.this, "11", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.my_table:
                        Toast.makeText(list_box.this, "1dd1", Toast.LENGTH_SHORT).show();

                        break;
                }

            }

        });

    }
    private void getBoxes()
    {
        related_Boxes = new FirebaseRecyclerAdapter<Boxes, Adapter_Boxes>(Boxes.class , R.layout.card_box , Adapter_Boxes.class ,Box) {
                                                                         // "Boxes" = class here(img ,name)

            @Override
            protected void populateViewHolder(Adapter_Boxes adapter_boxes, Boxes boxes, int i) {
                Picasso.get().load(boxes.getImg()).into(adapter_boxes.img_box);//to put defualt image
                adapter_boxes.name_box.setText(boxes.getName());
                adapter_boxes.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int pos, boolean isLongClick) {
                        Toast.makeText(list_box.this, "Test", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recyclerBoxes.setAdapter(related_Boxes);
    }
}