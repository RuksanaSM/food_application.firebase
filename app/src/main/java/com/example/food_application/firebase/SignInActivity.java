package com.example.food_application.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.example.food_application.R;

import com.example.food_application.firebase.common.Common;
import com.example.food_application.firebase.model.UserClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    EditText phoneedt, pwedt;
    Button signinbtn;
    String phonestr, pwstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        phoneedt = findViewById(R.id.phoneedtsignin);
        pwedt = findViewById(R.id.pwedtsignin);
        signinbtn = findViewById(R.id.signinbtniddd);
//
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference table_user = database.getReference("User");
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonestr=phoneedt.getText().toString();
              pwstr=pwedt.getText().toString();
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference table_user = database.getReference("User");

                final ProgressDialog mdialog = new ProgressDialog(SignInActivity.this);
                mdialog.setMessage("Please wait...");
                mdialog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user not exist
                        if (dataSnapshot.child(phoneedt.getText().toString()).exists()) {
                            //Get UserClass Information
                            mdialog.dismiss();
                            UserClass userClass = dataSnapshot.child(phoneedt.getText().toString()).getValue(UserClass.class);
                           userClass.setPhone(phoneedt.getText().toString());
                            if (userClass.getPassword().equals(pwedt.getText().toString())) {
                                Intent homeIntent = new Intent(SignInActivity.this, Home.class);
                                Common.currentUser=userClass;
                                startActivity(homeIntent);

                                finish();
                            } else {
                                Toast.makeText(SignInActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mdialog.dismiss();
                            Toast.makeText(SignInActivity.this, "User Does Not exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
          }
        });
    }
//    private void checkuser() {
//        phonestr=phoneedt.getText().toString();
//        pwstr=pwedt.getText().toString();
//        final FirebaseDatabase database=FirebaseDatabase.getInstance();
//        final DatabaseReference table_user=database.getReference("UserClass");
//        ProgressDialog progressDialog=new ProgressDialog(SignInActivity.this);
//        progressDialog.setMessage("Please Wait...");
//        progressDialog.show();
//        table_user.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                //check user exist in databse or not
//                if (snapshot.child(phoneedt.getText().toString()).exists()) {
//                    //get user information
//                    progressDialog.dismiss();
//                    UserClass users = snapshot.child(phoneedt.getText().toString()).getValue(UserClass.class);
////                    if (users.getPasswd().equals(pwedt.getText().toString())) {
////                        Toast.makeText(SignInActivity.this, "signin successfully", Toast.LENGTH_SHORT).show();
////                    }
//                    if (users.getPasswd()!=null && pwstr!=null && users.getPasswd().equals(pwstr)) {
////                        Intent homeIntent = new Intent(SignInActivity.this, MainActivity.class);
////
////                        startActivity(homeIntent);
////                        finish();
//                        Toast.makeText(SignInActivity.this, "signin successfully", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        Toast.makeText(SignInActivity.this, "signin failed...", Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//                else
//                {
//                    progressDialog.dismiss();
//                    Toast.makeText(SignInActivity.this, "UserClass not exist", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
            //}


        }