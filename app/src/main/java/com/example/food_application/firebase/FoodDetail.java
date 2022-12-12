package com.example.food_application.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_application.R;
import com.example.food_application.firebase.databases.Database;
import com.example.food_application.firebase.model.Food_Model;
import com.example.food_application.firebase.model.Order;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {

    TextView food_Name,food_price,food_Description,textnumimg;
    ImageView food_img;
    ImageView plusimg,minusimg;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Button numberbtn;
    FloatingActionButton btncart;
    String foodId="";
    FirebaseDatabase database;
    DatabaseReference foods;
    Food_Model food_model;
 int totalprice=0;
  public  int totalQuantity=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

         database=FirebaseDatabase.getInstance();
         foods=database.getReference("Food");

         food_Name=findViewById(R.id.foodnametextvwid);
         food_price=findViewById(R.id.food_pricetextvwid);
         food_Description=findViewById(R.id.food_descrptionid);
         food_img=findViewById(R.id.img_Foodfdetailid);
         btncart=findViewById(R.id.shopcartid);
         plusimg=findViewById(R.id.imageViewplusid);
         minusimg=findViewById(R.id.imageViewminusid);
         textnumimg=findViewById(R.id.numbertextid);

         plusimg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 if (totalQuantity<10)
                 {
                     totalQuantity++;
                     textnumimg.setText(String.valueOf(totalQuantity));

                 }

             }
         });
         minusimg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 if (totalQuantity>0)
                 {
                     totalQuantity--;
                     textnumimg.setText(String.valueOf(totalQuantity));
                 }
             }
         });

         btncart.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
//                  new Database(getBaseContext()).addToCart(new Order( foodId,
//                          food_model.getName(),
//                          food_model.getPrice(),
//                          food_model.getDiscount()
//                          ));
                 Toast.makeText(FoodDetail.this, "Added to Cart", Toast.LENGTH_SHORT).show();
             }
         });
         collapsingToolbarLayout=findViewById(R.id.toolbarcolap_foodDetailid);
         collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExtendedAppbar);
         collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppbar);

         if (getIntent()!=null)
           foodId=getIntent().getStringExtra("FoodId");
         if (!foodId.isEmpty())
         {
             getFoodDetails(foodId);
         }


    }

    private void getFoodDetails(String foodId) {

        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 food_model=snapshot.getValue(Food_Model.class);

                Picasso.with(getBaseContext()).load(food_model.getImage()).into(food_img);
//                Picasso.get().load(food_model.getImage()).into(food_img);
                collapsingToolbarLayout.setTitle(food_model.getName());
                food_price.setText(food_model.getPrice());
                food_Name.setText(food_model.getName());
                food_Description.setText(food_model.getDescription());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}