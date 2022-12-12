package com.example.food_application.firebase.viewholder;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_application.R;
import com.example.food_application.firebase.interfaces.ItemClickListener;
import com.example.food_application.firebase.model.Order;
import com.google.android.material.internal.TextDrawableHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView cartitemNAme,cartitemprice;
    public TextView cartcount;
     private ItemClickListener itemClickListener;


    public void setCartitemNAme(TextView cartitemNAme) {
        this.cartitemNAme = cartitemNAme;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        cartitemNAme=itemView.findViewById(R.id.cartitem_Nameid);
        cartitemprice=itemView.findViewById(R.id.cartitem_priceid);
        cartcount=itemView.findViewById(R.id.cartitem_countid);

    }

    @Override
    public void onClick(View view) {
        itemClickListener.onclick(view,getAdapterPosition(),false);

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

    private List<Order> orderList=new ArrayList<>();
    private Context context;


    public CartAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view1=layoutInflater.inflate(R.layout.cart_layout,parent,false);

        return new CartViewHolder(view1);

    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
//        TextDrawable

        holder.cartcount.setText(""+orderList.get(position).getQuantity());
        Locale locale=new Locale("en","us");
        NumberFormat numberFormat=NumberFormat.getCurrencyInstance(locale);
        int price=(Integer.parseInt(orderList.get(position).getPrice()))*(Integer.parseInt(orderList.get(position).getQuantity()));
        holder.cartitemprice.setText(numberFormat.format(price));
        holder.cartitemNAme.setText(orderList.get(position).getProductName());


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
