package com.example.food_application.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.food_application.R;
import com.example.food_application.firebase.interfaces.ItemClickListener;
import com.example.food_application.firebase.model.Food_Model;
import com.example.food_application.firebase.viewholder.Food_ViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerViewfoodlist;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference foodlist;
    String categoryId = "";
    FirebaseRecyclerAdapter<Food_Model, Food_ViewHolder> adapter;

    //search Functanality
    FirebaseRecyclerAdapter<Food_Model,Food_ViewHolder> searchAdapter;
    List<String> suggestList=new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //Firebase
        database = FirebaseDatabase.getInstance();
        foodlist = database.getReference("Food");


        recyclerViewfoodlist = findViewById(R.id.recycler_foodlistid);
        recyclerViewfoodlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewfoodlist.setLayoutManager(layoutManager);

        //Get intent here
        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("CategoryId");

            if (!categoryId.isEmpty() && categoryId != null) {
                loadListOfFood(categoryId);
            }
        }

        materialSearchBar=findViewById(R.id.searchbarid);
        materialSearchBar.setHint("Enter your Food");
        loadSuggestSearchlist();

        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                List<String> suugest=new ArrayList<>();
                for (String search:suggestList)
                {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                    {
                        suugest.add(search);
                    }
                }

                materialSearchBar.setLastSuggestions(suugest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when search bar is closed restore the original adapter

                if (!enabled)
                {
                    recyclerViewfoodlist.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                //when search finish show  result to search adapter
                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

    private void startSearch(CharSequence text) {
        searchAdapter=new FirebaseRecyclerAdapter<Food_Model, Food_ViewHolder>(
                Food_Model.class,
                R.layout.food_item,
                Food_ViewHolder.class,
                foodlist.orderByChild("Name").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(Food_ViewHolder food_viewHolder, Food_Model food_model, int i) {

                food_viewHolder.Food_Name.setText(food_model.getName());
                Picasso.with(getBaseContext()).load(food_model.getImage())
                        .into(food_viewHolder.Food_imageView);
                final Food_Model moodelClass = food_model;
                food_viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onclick(View view, int position, boolean islongClick) {
                        // Toast.makeText(FoodList.this, "" + moodelClass.getName(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(FoodList.this, FoodDetail.class);
                        intent.putExtra("FoodId",searchAdapter.getRef(position).getKey());
                        startActivity(intent);

                    }
                });
            }
        };
        recyclerViewfoodlist.setAdapter(searchAdapter);
    }

    private void loadSuggestSearchlist() {

        foodlist.orderByChild("MenuId").equalTo(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot:snapshot.getChildren())
                {
                    Food_Model food_list=postSnapshot.getValue(Food_Model.class);
                    suggestList.add(food_list.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadListOfFood(String categoryId) {


        try {
            adapter = new FirebaseRecyclerAdapter<Food_Model, Food_ViewHolder>
                    (Food_Model.class,
                            R.layout.food_item,
                            Food_ViewHolder.class,
                            foodlist.orderByChild("MenuId").equalTo(categoryId)
                    ) {
                @Override
                protected void populateViewHolder(Food_ViewHolder food_viewHolder, Food_Model food_model, int i) {

                    food_viewHolder.Food_Name.setText(food_model.getName());
                    Picasso.with(getBaseContext()).load(food_model.getImage())
                            .into(food_viewHolder.Food_imageView);
                    final Food_Model moodelClass = food_model;
                    food_viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onclick(View view, int position, boolean islongClick) {
                           // Toast.makeText(FoodList.this, "" + moodelClass.getName(), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(FoodList.this, FoodDetail.class);
                            intent.putExtra("FoodId",adapter.getRef(position).getKey());
                            startActivity(intent);

                        }
                    });
                }
            };

            Log.d("Tag", "" + adapter.getItemCount());
            recyclerViewfoodlist.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}