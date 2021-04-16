package com.example.agriculturalproject.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agriculturalproject.InterFace.ItemClickListener;
import com.example.agriculturalproject.R;

public class Adapter_Boxes extends RecyclerView.ViewHolder implements View.OnClickListener {
   public ImageView img_box ;
    public TextView name_box , nameplant ;
    private ItemClickListener itemClickListener;
    public void setItemClickListener(ItemClickListener itemClickListener) {// when i put on the box; get action
        this.itemClickListener = itemClickListener;
    }

    public Adapter_Boxes(@NonNull View itemView) {
        super(itemView);
        img_box = itemView.findViewById(R.id.box_image);
        name_box = itemView.findViewById(R.id.box_name);
        nameplant = itemView.findViewById(R.id.name_plant);
        itemView.setOnClickListener(this);//


    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick( v ,getAdapterPosition(),false);//اسناد قيم للفنكشن في interface


    }
}
