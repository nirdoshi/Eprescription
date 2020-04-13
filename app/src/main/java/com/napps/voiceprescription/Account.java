package com.napps.voiceprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Account extends AppCompatActivity {

    TextView tvemail;
    Button btnemail,btnpassword,btnsignout,btndeleteuser;
    EditText etemail,etpassword;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        final String oldemail=user.getEmail();




        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(Account.this, Login.class));
                    finish();
                }
            }
        };



        tvemail=findViewById(R.id.tv_email);


        btnpassword=findViewById(R.id.btn_cpassword);
        btnsignout=findViewById(R.id.btn_signout);
        btndeleteuser=findViewById(R.id.btn_deleteuser);



       // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        tvemail.setText("Email is "+oldemail);



        btnpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                auth.sendPasswordResetEmail(oldemail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Account.this, "Reset password email is sent!", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(Account.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }

        });

        btnsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signOut();

            }
        });

        btndeleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Account.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Account.this, Registration.class));
                                        finish();

                                    } else {
                                        Toast.makeText(Account.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }


            }
        });

    }


    public void signOut() {
        auth.signOut();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }


}
