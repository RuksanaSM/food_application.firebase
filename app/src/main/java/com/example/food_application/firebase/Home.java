package com.example.food_application.firebase;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_application.R;
import com.example.food_application.databinding.ActivityHomeBinding;
import com.example.food_application.firebase.common.Common;
import com.example.food_application.firebase.interfaces.ItemClickListener;
import com.example.food_application.firebase.model.Category_ModelClass;

import com.example.food_application.firebase.viewholder.Menu_ViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import androidx.recyclerview.widget.RecyclerView;
//import android.support.v7.widget.RecyclerView;

public class Home extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    FirebaseDatabase database;
    DatabaseReference category;
    TextView textfullname;
    RecyclerView recyclerView;
RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category_ModelClass, Menu_ViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//       binding.appBarHome.toolbar.setTitle("Menu");


       //Init firebase
        database=FirebaseDatabase.getInstance();
        category=database.getReference("Category");
        setSupportActionBar(binding.appBarHome.toolbar);

//        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Select your food to order", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////                startActivity(new Intent(Home.this,Cart.class));
//                Intent i=new Intent(Home.this,Cart.class);
//                startActivity(i);
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_cart, R.id.nav_order,R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //set name for user
        View headerview=navigationView.getHeaderView(0);
        textfullname=headerview.findViewById(R.id.Fullnameid);
        textfullname.setText(Common.currentUser.getName());

        //load menu
        recyclerView=findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        loadMenu();
    }



    private void loadMenu() {

        adapter=new FirebaseRecyclerAdapter<Category_ModelClass, Menu_ViewHolder>(Category_ModelClass.class,
                R.layout.menu_item_food,Menu_ViewHolder.class,category) {
            @Override
            protected void populateViewHolder(Menu_ViewHolder viewHolder, Category_ModelClass model, int position) {
                viewHolder.textmenunName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);
                Category_ModelClass clickItem=model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onclick(View view, int position, boolean islongClick) {
                       // Toast.makeText(Home.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();

                        //Get CategoryId and send to new activity
                        Intent intent=new Intent(Home.this, FoodList.class);
                        intent.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void fabclick(View view) {
        Intent i=new Intent(Home.this,Cart.class);
        startActivity(i);
    }
}