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
import com.example.food_app2.common.Commons;
import com.example.food_app2.eventbus.CategoryClick;
import com.example.food_app2.interfaces.RecyclerClickListener;
import com.example.food_app2.model.CategoryModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.UserHolder> {

    Context context;
    List<CategoryModel> categoryModelList;


    public CategoriesAdapter(Context context, List<CategoryModel> categoryModelList) {
        this.context = context;
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserHolder(LayoutInflater.from(context).inflate(R.layout.layout_category_item,parent,false));


    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {

        Glide.with(context).load(categoryModelList.get(position).getImage()).into(holder.imgcat);
        holder.textCat.setText(categoryModelList.get(position).getName());

        //Event
        holder.setRecyclerClickListener(new RecyclerClickListener() {
            @Override
            public void onItemClickListener(View v, int pos) {
                Commons.categorySelected=categoryModelList.get(pos);
                EventBus.getDefault().postSticky(new CategoryClick(true,categoryModelList.get(pos)));

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Unbinder unbinder;
        @BindView(R.id.img_categoryid)
        ImageView imgcat;
        @BindView(R.id.text_categoryid)
        TextView textCat;
        RecyclerClickListener recyclerClickListener;

        public void setRecyclerClickListener(RecyclerClickListener recyclerClickListener) {
            this.recyclerClickListener = recyclerClickListener;
        }

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            unbinder= ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recyclerClickListener.onItemClickListener(view,getAdapterPosition());
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (categoryModelList.size()==1)

            return Commons.DEFAULT_COLUMN_COUNT;
        else
        {
            if (categoryModelList.size()% 2== 0)
                return Commons.DEFAULT_COLUMN_COUNT;
            else
                return (position>1 && position == categoryModelList.size()-1) ? Commons.FULL_WIDTH_COLUMN:Commons.DEFAULT_COLUMN_COUNT;
        }



    }
}
