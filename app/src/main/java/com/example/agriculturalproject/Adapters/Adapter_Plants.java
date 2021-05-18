package com.example.agriculturalproject.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agriculturalproject.InterFace.ItemClickListener;
import com.example.agriculturalproject.R;

public class Adapter_Plants extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView plant_card_image ;
    public TextView plant_card_name ;
    private ItemClickListener itemClickListener;//()
    public void setItemClickListener(ItemClickListener itemClickListener) {// when i put on the box; get action
        this.itemClickListener = itemClickListener;
    }

    public Adapter_Plants(@NonNull View itemView) {
        super(itemView);
        plant_card_image = itemView.findViewById(R.id.plant_card_image);
        plant_card_name = itemView.findViewById(R.id.plant_card_name);
        itemView.setOnClickListener(this);//فعل onClick في adapter
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick( v , getAdapterPosition(),false);//اسناد قيم للفنكشن في interface
        //position ==فعل محل ما كبست في البوكس
        //isLongClick == short or long Click
        // V == full card box

    }
}
