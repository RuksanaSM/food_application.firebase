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
import com.example.food_app2.model.Popular_Category_model;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class PopularCategoriesAdapter extends RecyclerView.Adapter<PopularCategoriesAdapter.UserHolder> {


    Context context;
    List<Popular_Category_model> popularCategoryModelList;


    public PopularCategoriesAdapter(Context context, List<Popular_Category_model> popularCategoryModelList) {
        this.context = context;
        this.popularCategoryModelList = popularCategoryModelList;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_popular_categories,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        Glide.with(context).load(popularCategoryModelList.get(position).getImage()).into(holder.circlepopimg);
        holder.textcategory_name.setText(popularCategoryModelList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return popularCategoryModelList.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {

        Unbinder unbinder;

        @BindView(R.id.textcategory_name)
        TextView textcategory_name;

        @BindView(R.id.circlepopimg)
        CircleImageView circlepopimg;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this, itemView);


        }
    }
}
