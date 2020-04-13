package com.example.voiceprescription;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.Collections;

public class Exsistinpatient extends AppCompatActivity {

    ListView list;
    DatabaseReference reff;
    FirebaseDatabase database;
    list_adapter adapter;
    Patientinfo patientinfo;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    SearchView mySearchView = null ;
    Button btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exsistinpatient);
        publicarray.info.clear();

        btn_back=findViewById(R.id.btn_back);
        progressBar=findViewById(R.id.progressBar);
        mySearchView = (SearchView) findViewById( R.id.mySearchView ) ;
        list=findViewById(R.id.list);


        Intent intent=new Intent();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Exsistinpatient.this,Editinfo.class);
                intent.putExtra("index",position);
                //intent.putExtra("name",publicarray.info.get(position).getName());
                startActivityForResult(intent,0);


            }
        });



        FirebaseUser user= firebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();

        database=FirebaseDatabase.getInstance();

        reff=database.getReference(uid+"/Patients");
            mySearchView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        patientinfo = data.getValue(Patientinfo.class);
                        publicarray.info.add(patientinfo);
                    }


                    adapter = new list_adapter(Exsistinpatient.this, publicarray.info);
                    list.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                    mySearchView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressBar.setVisibility(View.GONE);
                    mySearchView.setVisibility(View.VISIBLE);
                }
            });

           // setResult(RESULT_OK,intent);
            mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    adapter.notifyDataSetChanged();
                    return false;
                }
            });

            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Exsistinpatient.this,MainActivity.class));
                    Exsistinpatient.this.finish();
                }
            });


    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            adapter.clear();
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot data:dataSnapshot.getChildren()){

                        patientinfo=data.getValue(Patientinfo.class);
                        publicarray.info.add(patientinfo);
                    }

                    adapter=new list_adapter(Exsistinpatient.this,publicarray.info);
                    list.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }


}
