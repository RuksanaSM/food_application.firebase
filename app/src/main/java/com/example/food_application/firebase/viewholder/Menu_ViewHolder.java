package com.example.food_application.firebase.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import android.support.v7.widget.RecyclerView;
import com.example.food_application.R;
import com.example.food_application.firebase.interfaces.ItemClickListener;

public class Menu_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

  public  TextView textmenunName;
  public  ImageView imageView;
    private ItemClickListener itemClickListener;

    public Menu_ViewHolder(@NonNull View itemView) {
        super(itemView);
        textmenunName=itemView.findViewById(R.id.menu_nameid);
        imageView=itemView.findViewById(R.id.menu_imageid);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onclick(view,getAdapterPosition(),false);
    }
}
