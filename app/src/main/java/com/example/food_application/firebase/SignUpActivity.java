package com.example.food_application.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.food_application.R;
import com.example.food_application.firebase.model.UserClass;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignUpActivity extends AppCompatActivity {

    TextInputEditText phoneedt,name,pw;
    Button btnsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        phoneedt=findViewById(R.id.phoneedtsignup);
        name=findViewById(R.id.nameedtsignup);
        pw=findViewById(R.id.pwedtsignup);
        btnsignup=findViewById(R.id.signupupbtnid);
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mdailog=new ProgressDialog(SignUpActivity.this);
                mdailog.setMessage("Please wait...");
                mdailog.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //check phone number already exist or not
                        if (snapshot.child(phoneedt.getText().toString()).exists())

                        {
                            mdailog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Phone number already Registered...", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            mdailog.dismiss();
                            UserClass userClass =new UserClass(name.getText().toString(),pw.getText().toString());
                            table_user.child(phoneedt.getText().toString()).setValue(userClass);
                            Toast.makeText(SignUpActivity.this, "Signup Successfully...", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }


}