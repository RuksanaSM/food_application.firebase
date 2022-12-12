package com.example.food_application.firebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.food_application.R;
import com.example.food_application.firebase.databases.Database;
import com.example.food_application.firebase.model.Order;
import com.example.food_application.firebase.viewholder.CartAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference request;

    TextView totalprice;
   Button fbtn;

    List<Order> cart=new ArrayList<>();

     CartAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database=FirebaseDatabase.getInstance();
        request=database.getReference("Requests");

        recyclerView=findViewById(R.id.cart_recyclervw);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        totalprice=findViewById(R.id.totalpricetid);
        fbtn=findViewById(R.id.extendedfbtnid);



    }




    }
