package com.example.food_app2;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.food_app2.common.Commons;
import com.example.food_app2.model.UserModel;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


public class MainActivity extends AppCompatActivity {


    private static int APP_REQUEST_CODE = 7171;  //any number
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;
    private AlertDialog dialog;
    private CompositeDisposable disposable = new CompositeDisposable();

    private DatabaseReference userRef;
    private List<AuthUI.IdpConfig> providers;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        if (listener != null)
            firebaseAuth.removeAuthStateListener(listener);
        disposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
    }

    private void Init() {
        providers= Arrays.asList(new AuthUI.IdpConfig.PhoneBuilder().build());


        userRef= FirebaseDatabase.getInstance().getReference(Commons.USER_REFERENCES);
        firebaseAuth = FirebaseAuth.getInstance();
        AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Loading...");
//            dialog.setCancelable(false);
//            dialog.setMessage("Loading...");
//            setContext(this).build();

        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    //Account is already logged in
                    CheckUserFromFirebase(user);

                    // Toast.makeText(MainActivity.this, "Already Logged In", Toast.LENGTH_SHORT).show();
                } else {
                    phoneLogIn();

                }
            }
        };
    }

    private void CheckUserFromFirebase(FirebaseUser user) {
//        dialog.show();
        userRef.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    UserModel userModel= dataSnapshot.getValue(UserModel.class);
                    goToHomeActivity(userModel);
                }
                else{
                    showRegisterDialog(user);
                }
//                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, ""+ databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showRegisterDialog(FirebaseUser user)
    {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Register");
        builder.setMessage("Please Fill Information");
//        builder.setCancelable(false);

        View itemView= LayoutInflater.from(this).inflate(R.layout.layout_register, null);

        builder.setCancelable(false);

        builder.setView(itemView);
        EditText edt_name= (itemView).findViewById(R.id.edt_name);
        EditText edt_adress= itemView.findViewById(R.id.edt_address);
        EditText edt_phone=itemView.findViewById(R.id.edt_phone);

        //set data
        edt_phone.setText(user.getPhoneNumber());


        builder.setNegativeButton("CANCEL", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        builder.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(TextUtils.isEmpty(edt_name.getText().toString()))
                {

                    Toast.makeText(MainActivity.this, "Please enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else  if(TextUtils.isEmpty(edt_adress.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Please enter Address", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserModel userModel= new UserModel();
                userModel.setUid(user.getUid());
                userModel.setName(edt_name.getText().toString());
                userModel.setAddress(edt_adress.getText().toString());
                userModel.setPhone(edt_phone.getText().toString());

                userRef.child(user.getUid()).setValue(userModel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
                                    dialogInterface.dismiss();

                                    Toast.makeText(MainActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                    goToHomeActivity(userModel);
                                }

                            }

                        });
            }
        });

//            builder.setView(itemView);
      androidx.appcompat.app.AlertDialog alert = builder.create();

        alert.show();
    }

    private void phoneLogIn() {

        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),APP_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) {
            IdpResponse response= IdpResponse.fromResultIntent(data);
            if(resultCode== RESULT_OK)
            {
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            }
            else
            {
                Toast.makeText(this, "SignIn Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }





    private void goToHomeActivity(UserModel userModel) {
        Commons.currentUser = userModel;
        startActivity(new Intent(MainActivity.this,HomeActivity.class));
        finish();
//                FirebaseMessaging.getInstance()
//                    .getToken()
//                    .addOnFailureListener(e -> {
//                        Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        Commons.currentUser = userModel;
//
//                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
//                        finish();

//                    }).addOnCompleteListener(task -> {
//
//                        Commons.currentUser = userModel;
//                        Commons.updateToken(MainActivity.this,task.getResult());
//                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
//                        finish();
//                    });
    }


}