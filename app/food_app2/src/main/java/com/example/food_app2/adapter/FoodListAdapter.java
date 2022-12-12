package com.example.food_app2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_app2.R;
import com.example.food_app2.model.FoodModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.UserHolder> {

    private Context context;
    private List<FoodModel> foodModelList;


    public FoodListAdapter(Context context, List<FoodModel> foodModelList) {
        this.context = context;
        this.foodModelList = foodModelList;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserHolder(LayoutInflater.from(context).inflate(R.layout.layout_food_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {

        Glide.with(context).load(foodModelList.get(position).getImage()).into(holder.foodimage);
        holder.foodprice.setText(new StringBuilder("$")
                .append(foodModelList.get(position).getPrice()));
        holder.foodname.setText(new StringBuilder("").append(foodModelList.get(position).getName()));

    }

    @Override
    public int getItemCount() {
        return foodModelList.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {

        private Unbinder unbinder;

        @BindView(R.id.foodlist_text_foodname)
        TextView foodname;
        @BindView(R.id.text_food_price)
        TextView foodprice;
        @BindView(R.id.foodlist_img_fav)
        ImageView favimg;
        @BindView(R.id.foodlist_img_cart)
        ImageView foodcart;
        @BindView(R.id.foodlist_imgids)
        ImageView foodimage;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            unbinder= ButterKnife.bind(this,itemView);
        }
    }
}
