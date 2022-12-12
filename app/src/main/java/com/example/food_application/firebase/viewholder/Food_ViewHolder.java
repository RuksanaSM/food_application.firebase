package com.example.food_application.firebase.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_application.R;
import com.example.food_application.firebase.interfaces.ItemClickListener;

public class Food_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView Food_Name;
    public ImageView Food_imageView;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }

    public Food_ViewHolder(@NonNull View itemView) {
        super(itemView);
        Food_Name=itemView.findViewById(R.id.foodlist_nameid);
        Food_imageView=itemView.findViewById(R.id.foodlist_imageid);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onclick(view,getAdapterPosition(),false);

    }

}
