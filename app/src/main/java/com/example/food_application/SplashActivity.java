package com.example.food_application;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.food_application.firebase.SignInActivity;
import com.example.food_application.firebase.SignUpActivity;

public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        ActionBar actionBar=getSupportActionBar();
//        actionBar.hide();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(SplashActivity.this,MainActivity.class));
//                finish();
//            }
//        },3000);
    }

    public void signuppage(View view) {
        startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
        finish();
    }

    public void signinpage(View view) {
        startActivity(new Intent(SplashActivity.this, SignInActivity.class));
        finish();
    }

//    public void signinbtnclick(View view) {
//        startActivity(new Intent(SplashActivity.this,MainActivity.class));
//        finish();
//    }
}