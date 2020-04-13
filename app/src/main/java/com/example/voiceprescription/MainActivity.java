package com.example.voiceprescription;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ImageView ivaccount;
    Button btn_new_patient,btn_exsisting_patient,btn_delete_patient;
    TextView tv2;
    list_adapter adapter;
    DatabaseReference reff;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // tv2=findViewById(R.id.tv2);
        ivaccount=findViewById(R.id.iv_account);
        btn_new_patient=findViewById(R.id.btn_new_patient);
        btn_exsisting_patient=findViewById(R.id.btn_exsisting_patient);
        //btn_delete_patient=findViewById(R.id.btn_delete_patient);

        // tv2.setText(personInfo.getName());

        ivaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            startActivity(new Intent(MainActivity.this,Account.class));


            }
        });

        btn_new_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,Name.class));
            }
        });

        btn_exsisting_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(MainActivity.this,Exsistinpatient.class);

                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();

    }
}
