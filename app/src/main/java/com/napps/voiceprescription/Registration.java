package com.napps.voiceprescription;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {

    EditText etname,etnumber,etemail,etpass,etrepass;
    Button btnsignup;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

    etname=findViewById(R.id.et_name);
    etnumber=findViewById(R.id.et_num);
    etemail=findViewById(R.id.et_email);
    etpass=findViewById(R.id.et_pass);
    etrepass=findViewById(R.id.et_repass);

    btnsignup=findViewById(R.id.btn_signup);

        firebaseAuth = FirebaseAuth.getInstance();

    btnsignup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(etname.getText().toString().isEmpty() || etnumber.getText().toString().isEmpty() || etemail.getText().toString().isEmpty()
            || etpass.getText().toString().isEmpty() || etrepass.getText().toString().isEmpty()){

                Toast.makeText(Registration.this, "please enter all fields", Toast.LENGTH_SHORT).show();
            }
            else if(etpass.getText().toString().equals(etrepass.getText().toString()))
            {

                String email= etemail.getText().toString();
                String password=etpass.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(Registration.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                if (!task.isSuccessful()) {
                                    Toast.makeText(Registration.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Registration.this, "Registeration successfull", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Registration.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });


            }
            else {

                Toast.makeText(Registration.this, "please enter correct password", Toast.LENGTH_SHORT).show();
            }


        }
    });

    }


}
