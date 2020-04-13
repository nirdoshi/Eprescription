package com.example.voiceprescription;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Editinfo extends AppCompatActivity {

    EditText et_Ename,et_Esymptoms,et_Ediagnosis,et_Emeds1,et_Emeds2,et_Emeds3,et_Emeds4,et_Emeds5;
    ImageButton ib_Evoice1,ib_Evoice2,ib_Evoice3,ib_Emvoice1,ib_Emvoice2,ib_Emvoice3,ib_Emvoice4,ib_Emvoice5;
    Spinner spinner_P1,spinner_P2,spinner_P3,spinner_P4,spinner_P5,spinner_D1,spinner_D2,spinner_D3,spinner_D4,spinner_D5,spinner_T1,spinner_T2,spinner_T3,spinner_T4,spinner_T5;;
    Button btn_Eupdate,btn_Edelete,btn_Eemail;
    TextView tv1,tv2,tv3,tv4,tv5;
    DatabaseReference reff;
    FirebaseAuth firebaseAuth;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =1 ;
    String SENT="SMS_SENT";
    String DELIVERED="SMS_DELIVERED";
    PendingIntent sentPI,deliveredPI;
    BroadcastReceiver smsSentReceiver,smsDeliveredReciever;
    int index1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editinfo);

        final Intent intent=new Intent();

        final int index=getIntent().getIntExtra("index",0);
        index1=index;
        //all layoutitems are declared here;
        et_Ename=findViewById(R.id.et_Ename);
        et_Esymptoms=findViewById(R.id.et_Esymptoms);
        et_Ediagnosis=findViewById(R.id.et_Ediagnosis);
        et_Emeds1=findViewById(R.id.et_Emeds1);
        et_Emeds2=findViewById(R.id.et_Emeds2);
        et_Emeds3=findViewById(R.id.et_Emeds3);
        et_Emeds4=findViewById(R.id.et_Emeds4);
        et_Emeds5=findViewById(R.id.et_Emeds5);

        ib_Evoice1=findViewById(R.id.ib_Evoice1);
        ib_Evoice2=findViewById(R.id.ib_Evoice2);
        ib_Evoice3=findViewById(R.id.ib_Evoice3);
        ib_Emvoice1=findViewById(R.id.ib_Emvoice1);
        ib_Emvoice2=findViewById(R.id.ib_Emvoice2);
        ib_Emvoice3=findViewById(R.id.ib_Emvoice3);
        ib_Emvoice4=findViewById(R.id.ib_Emvoice4);
        ib_Emvoice5=findViewById(R.id.ib_Emvoice5);

        spinner_P1=findViewById(R.id.spinner_P1);
        spinner_P2=findViewById(R.id.spinner_P2);
        spinner_P3=findViewById(R.id.spinner_P3);
        spinner_P4=findViewById(R.id.spinner_P4);
        spinner_P5=findViewById(R.id.spinner_P5);

        spinner_D1=findViewById(R.id.spinner_D1);
        spinner_D2=findViewById(R.id.spinner_D2);
        spinner_D3=findViewById(R.id.spinner_D3);
        spinner_D4=findViewById(R.id.spinner_D4);
        spinner_D5=findViewById(R.id.spinner_D5);

        btn_Edelete=findViewById(R.id.btn_Edelete);
        btn_Eupdate=findViewById(R.id.btn_Eupdate);
        btn_Eemail=findViewById(R.id.btn_Eemail);

        spinner_T1=findViewById(R.id.spinner_T1);
        spinner_T2=findViewById(R.id.spinner_T2);
        spinner_T3=findViewById(R.id.spinner_T3);
        spinner_T4=findViewById(R.id.spinner_T4);
        spinner_T5=findViewById(R.id.spinner_T5);





        String key=publicarray.info.get(index).getId();

        ArrayAdapter<String> myadapter=new ArrayAdapter<String>(Editinfo.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.pills));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_P1.setAdapter(myadapter);
        spinner_P2.setAdapter(myadapter);
        spinner_P3.setAdapter(myadapter);
        spinner_P4.setAdapter(myadapter);
        spinner_P5.setAdapter(myadapter);

        ArrayAdapter<String> myadapter2=new ArrayAdapter<String>(Editinfo.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.duration));
        myadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_D2.setAdapter(myadapter2);
        spinner_D3.setAdapter(myadapter2);
        spinner_D4.setAdapter(myadapter2);
        spinner_D5.setAdapter(myadapter2);
        spinner_D1.setAdapter(myadapter2);

        ArrayAdapter<String> myadapter3=new ArrayAdapter<String>(Editinfo.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.time));
        spinner_T1.setAdapter(myadapter3);
        spinner_T2.setAdapter(myadapter3);
        spinner_T3.setAdapter(myadapter3);
        spinner_T4.setAdapter(myadapter3);
        spinner_T5.setAdapter(myadapter3);


        //setting spinner as per data feeded earlier as per also the index of item clicked by user.

        String spinnerP1=publicarray.info.get(index).getP1();
        int posP1=myadapter.getPosition(spinnerP1);
        spinner_P1.setSelection(posP1);

        String spinnerP2=publicarray.info.get(index).getP2();
        int posP2=myadapter.getPosition(spinnerP2);
        spinner_P2.setSelection(posP2);

        String spinnerP3=publicarray.info.get(index).getP3();
        int posP3=myadapter.getPosition(spinnerP3);
        spinner_P3.setSelection(posP3);

        String spinnerP4=publicarray.info.get(index).getP4();
        int posP4=myadapter.getPosition(spinnerP4);
        spinner_P4.setSelection(posP4);

        String spinnerP5=publicarray.info.get(index).getP5();
        int posP5=myadapter.getPosition(spinnerP5);
        spinner_P5.setSelection(posP5);

        String spinnerD1=publicarray.info.get(index).getD1();
        int posD1=myadapter2.getPosition(spinnerD1);
        spinner_D1.setSelection(posD1);

        String spinnerD2=publicarray.info.get(index).getD2();
        int posD2=myadapter2.getPosition(spinnerD2);
        spinner_D2.setSelection(posD2);

        String spinnerD3=publicarray.info.get(index).getD3();
        int posD3=myadapter2.getPosition(spinnerD3);
        spinner_D3.setSelection(posD3);

        String spinnerD4=publicarray.info.get(index).getD4();
        int posD4=myadapter2.getPosition(spinnerD4);
        spinner_D4.setSelection(posD4);

        String spinnerD5=publicarray.info.get(index).getD5();
        int posD5=myadapter2.getPosition(spinnerD5);
        spinner_D5.setSelection(posD5);

        ///////////
        String spinnert1=publicarray.info.get(index).getT1();
        int post1=myadapter3.getPosition(spinnert1);
        spinner_T1.setSelection(post1);

        String spinnert2=publicarray.info.get(index).getT2();
        int post2=myadapter3.getPosition(spinnert2);
        spinner_T2.setSelection(post2);

        String spinnert3=publicarray.info.get(index).getT3();
        int post3=myadapter3.getPosition(spinnert3);
        spinner_T3.setSelection(post3);

        String spinnert4=publicarray.info.get(index).getT4();
        int post4=myadapter3.getPosition(spinnert4);
        spinner_T4.setSelection(post4);

        String spinnert5=publicarray.info.get(index).getT5();
        int post5=myadapter3.getPosition(spinnert5);
        spinner_T5.setSelection(post5);
////////////////////////////////////////////////

        //all data as per clicked in listview which is stored in our public array have been get down here;
        et_Ename.setText(publicarray.info.get(index).getName());
        et_Esymptoms.setText(publicarray.info.get(index).getSymptoms());
        et_Ediagnosis.setText(publicarray.info.get(index).getDiagnosis());
        et_Emeds1.setText(publicarray.info.get(index).getMeds1());
        et_Emeds2.setText(publicarray.info.get(index).getMeds2());
        et_Emeds3.setText(publicarray.info.get(index).getMeds3());
        et_Emeds4.setText(publicarray.info.get(index).getMeds4());
        et_Emeds5.setText(publicarray.info.get(index).getMeds5());
       /* tv1.setText(publicarray.info.get(index).getMeds1());
        tv2.setText(publicarray.info.get(index).getMeds2());
        tv3.setText(publicarray.info.get(index).getMeds3());
        tv4.setText(publicarray.info.get(index).getMeds4());
        tv5.setText(publicarray.info.get(index).getMeds5());
*/
        /*//hiding textview dynamically
        if(et_Emeds1.getText().toString().isEmpty()){
            tv1.setVisibility(View.GONE);
            spinner_T1.setVisibility(View.GONE);
        }
        if(et_Emeds2.getText().toString().isEmpty()){
            tv2.setVisibility(View.GONE);
            spinner_T2.setVisibility(View.GONE);
        }
        if(et_Emeds3.getText().toString().isEmpty()){
            tv3.setVisibility(View.GONE);
            spinner_T3.setVisibility(View.GONE);
        }
        if(et_Emeds4.getText().toString().isEmpty()){
            tv4.setVisibility(View.GONE);
            spinner_T4.setVisibility(View.GONE);
        }
        if(et_Emeds5.getText().toString().isEmpty()){
            tv5.setVisibility(View.GONE);
            spinner_T5.setVisibility(View.GONE);
        }*/


        //voice is being setted up here;
        ib_Evoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput(1);
            }
        });
        ib_Evoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput(2);
            }
        });
        ib_Evoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput(3);
            }
        });
        ib_Emvoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput(4);
            }
        });
        ib_Emvoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput(5);
            }
        });
        ib_Emvoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput(6);
            }
        });
        ib_Emvoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput(7);
            }
        });
        ib_Emvoice5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput(8);
            }
        });

        final Patientinfo patientinfo=new Patientinfo();

        //spinner onclick listerner here
        spinner_P5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String p5=parent.getItemAtPosition(position).toString();
                patientinfo.setP5(p5);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner_D5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String d5=parent.getItemAtPosition(position).toString();
                patientinfo.setD5(d5);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_P4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String p4=parent.getItemAtPosition(position).toString();
                patientinfo.setP4(p4);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner d4 here

        spinner_D4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String d4=parent.getItemAtPosition(position).toString();
                patientinfo.setD4(d4);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_P3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String p3=parent.getItemAtPosition(position).toString();
                patientinfo.setP3(p3);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spineer d3 here

        spinner_D3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String d3=parent.getItemAtPosition(position).toString();
                patientinfo.setD3(d3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_P2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String p2=parent.getItemAtPosition(position).toString();
                patientinfo.setP2(p2);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner d2 here

        spinner_D2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String d2=parent.getItemAtPosition(position).toString();
                patientinfo.setD2(d2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_P1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String p1=parent.getItemAtPosition(position).toString();
                patientinfo.setP1(p1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner d1 here

        spinner_D1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String d1=parent.getItemAtPosition(position).toString();
                patientinfo.setD1(d1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_T1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String t1=parent.getItemAtPosition(position).toString();
                patientinfo.setT1(t1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_T2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String t2=parent.getItemAtPosition(position).toString();
                patientinfo.setT2(t2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_T3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String t3=parent.getItemAtPosition(position).toString();
                patientinfo.setT3(t3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_T4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String t4=parent.getItemAtPosition(position).toString();
                patientinfo.setT4(t4);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_T5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String t5=parent.getItemAtPosition(position).toString();
                patientinfo.setT5(t5);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        FirebaseUser user= firebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();
        reff= FirebaseDatabase.getInstance().getReference().child(uid).child("Patients").child(key);

      btn_Eemail.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


              final String[] message = new String[100];
              if(et_Emeds2.getText().toString().isEmpty() && et_Emeds3.getText().toString().isEmpty()
                      && et_Emeds4.getText().toString().isEmpty() && et_Emeds5.getText().toString().isEmpty()){

                  message[0]="medicine 1 : "+et_Emeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                          +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+"\n Time to take medicine 1 is : "+spinner_T1.getSelectedItem()
                          .toString();

              }else if(et_Emeds3.getText().toString().isEmpty() && et_Emeds4.getText().toString().isEmpty() && et_Emeds5.getText().toString().isEmpty()){
                  message[0]="medicine 1 : "+et_Emeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                          +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+"\n Time to take medicine 1 is : "+spinner_T1.getSelectedItem()
                          .toString()+
                          "\n medicine 2 : "+et_Emeds2.getText().toString()+"\n pills/day of medicine 2 : "+spinner_P2.getSelectedItem().toString()
                          +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+"\n Time to take medicine 2 is : "+spinner_T2.getSelectedItem()
                          .toString();;



              }else if(et_Emeds4.getText().toString().isEmpty() && et_Emeds5.getText().toString().isEmpty()){

                  message[0]="medicine 1 : "+et_Emeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                          +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+"\n Time to take medicine 1 is : "+spinner_T1.getSelectedItem()
                          .toString()+
                          "\n medicine 2 : "+et_Emeds2.getText().toString()+"\n pills/day of medicine 2 : "+spinner_P2.getSelectedItem().toString()
                          +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+"\n Time to take medicine 2 is : "+spinner_T2.getSelectedItem()
                          .toString()+
                          "\n medicine 3 : "+et_Emeds3.getText().toString()+"\n pills/day of medicine 3 : "+spinner_P3.getSelectedItem().toString()
                          +"\n duration of medicine 3 is : " +spinner_D3.getSelectedItem().toString()+"\n Time to take medicine 3 is : "+spinner_T3.getSelectedItem()
                          .toString();


              }else if(et_Emeds5.getText().toString().isEmpty()){

                  message[0]="medicine 1 : "+et_Emeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                          +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+"\n Time to take medicine 1 is : "+spinner_T1.getSelectedItem()
                          .toString()+
                          "\n medicine 2 : "+et_Emeds2.getText().toString()+"\n pills/day of medicine 2 : "+spinner_P2.getSelectedItem().toString()
                          +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+"\n Time to take medicine 2 is : "+spinner_T2.getSelectedItem()
                          .toString()+
                          "\n medicine 3 : "+et_Emeds3.getText().toString()+"\n pills/day of medicine 3 : "+spinner_P3.getSelectedItem().toString()
                          +"\n duration of medicine 3 is : " +spinner_D3.getSelectedItem().toString()+"\n Time to take medicine 3 is : "+spinner_T3.getSelectedItem()
                          .toString()+
                          "\n medicine 4 : "+et_Emeds4.getText().toString()+"\n pills/day of medicine 4 : "+spinner_P4.getSelectedItem().toString()
                          +"\n duration of medicine 4 is : " +spinner_D4.getSelectedItem().toString()+"\n Time to take medicine 4 is : "+spinner_T4.getSelectedItem()
                          .toString();

              }else {

                  message[0]="medicine 1 : "+et_Emeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                          +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+"\n Time to take medicine 1 is : "+spinner_T1.getSelectedItem()
                          .toString()+
                          "\n medicine 2 : "+et_Emeds2.getText().toString()+"\n pills/day of medicine 2 : "+spinner_P2.getSelectedItem().toString()
                          +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+"\n Time to take medicine 2 is : "+spinner_T2.getSelectedItem()
                          .toString()+
                          "\n medicine 3 : "+et_Emeds3.getText().toString()+"\n pills/day of medicine 3 : "+spinner_P3.getSelectedItem().toString()
                          +"\n duration of medicine 3 is : " +spinner_D3.getSelectedItem().toString()+"\n Time to take medicine 3 is : "+spinner_T3.getSelectedItem()
                          .toString()+
                          "\n medicine 4 : "+et_Emeds4.getText().toString()+"\n pills/day of medicine 4 : "+spinner_P4.getSelectedItem().toString()
                          +"\n duration of medicine 4 is : " +spinner_D4.getSelectedItem().toString()+"\n Time to take medicine 4 is : "+spinner_T4.getSelectedItem()
                          .toString()+
                          "\n medicine 5 : "+et_Emeds5.getText().toString()+"\n pills/day of medicine 5 : "+spinner_P5.getSelectedItem().toString()
                          +"\n duration of medicine 5 is : " +spinner_D5.getSelectedItem().toString()+"\n Time to take medicine 5 is : "+spinner_T5.getSelectedItem()
                          .toString();
              }


              String email=publicarray.info.get(index).getEmail();

              String[] To=email.split(",");

              Intent intent =new Intent(Intent.ACTION_SEND);
              intent.putExtra(Intent.EXTRA_EMAIL,To);
              intent.putExtra(Intent.EXTRA_SUBJECT,"prescription");
              intent.putExtra(Intent.EXTRA_TEXT,message[0]);
              intent.setType("message/rfc822");
              startActivity(Intent.createChooser(intent,"choose an email client"));
          }
      });

      btn_Eupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        dataSnapshot.getRef().child("name").setValue(et_Ename.getText().toString().trim());
                        dataSnapshot.getRef().child("symptoms").setValue(et_Esymptoms.getText().toString().trim());
                        dataSnapshot.getRef().child("diagnosis").setValue(et_Ediagnosis.getText().toString().trim());
                        dataSnapshot.getRef().child("meds1").setValue(et_Emeds1.getText().toString().trim());
                        dataSnapshot.getRef().child("meds2").setValue(et_Emeds2.getText().toString().trim());
                        dataSnapshot.getRef().child("meds3").setValue(et_Emeds3.getText().toString().trim());
                        dataSnapshot.getRef().child("meds4").setValue(et_Emeds4.getText().toString().trim());
                        dataSnapshot.getRef().child("meds5").setValue(et_Emeds5.getText().toString().trim());

                        dataSnapshot.getRef().child("p1").setValue(patientinfo.getP1());
                        dataSnapshot.getRef().child("p2").setValue(patientinfo.getP2());
                        dataSnapshot.getRef().child("p3").setValue(patientinfo.getP3());
                        dataSnapshot.getRef().child("p4").setValue(patientinfo.getP4());
                        dataSnapshot.getRef().child("p5").setValue(patientinfo.getP5());

                        dataSnapshot.getRef().child("d1").setValue(patientinfo.getD1());
                        dataSnapshot.getRef().child("d2").setValue(patientinfo.getD2());
                        dataSnapshot.getRef().child("d3").setValue(patientinfo.getD3());
                        dataSnapshot.getRef().child("d4").setValue(patientinfo.getD4());
                        dataSnapshot.getRef().child("d5").setValue(patientinfo.getD5());

                        dataSnapshot.getRef().child("t1").setValue(patientinfo.getT1());
                        dataSnapshot.getRef().child("t2").setValue(patientinfo.getT2());
                        dataSnapshot.getRef().child("t3").setValue(patientinfo.getT3());
                        dataSnapshot.getRef().child("t4").setValue(patientinfo.getT4());
                        dataSnapshot.getRef().child("t5").setValue(patientinfo.getT5());


                        Toast.makeText(Editinfo.this, "data updated", Toast.LENGTH_SHORT).show();



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                setResult(RESULT_OK,intent);
                Intent intent2=new Intent(Editinfo.this,MainActivity.class);
                startActivity(intent2);
                Editinfo.this.finish();
            }

      });

      btn_Edelete.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              reff.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {

                      if(task.isSuccessful()){
                          Toast.makeText(Editinfo.this, "record deleted", Toast.LENGTH_SHORT).show();
                      }else {
                          Toast.makeText(Editinfo.this, "record not deleted", Toast.LENGTH_SHORT).show();
                      }

                  }
              });
              setResult(RESULT_OK,intent);

              startActivity(new Intent(Editinfo.this,Exsistinpatient.class));
          }
      });



    }//oncreate last bracket


    //voice code here
    private void promptSpeechInput(int i) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_IN");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"speak something..");
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en_IN");
        //intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
        //    getString(R.string.speech_prompt));
        try {
            if(i==1) {
                startActivityForResult(intent, i);
            }else if(i==2) {
                startActivityForResult(intent, i);
            }else if(i==3){
                startActivityForResult(intent, i);
            }else if (i==4){
                startActivityForResult(intent, i);
            }else if (i==5){
                startActivityForResult(intent, i);
            }else if(i==6){
                startActivityForResult(intent, i);
            }else if(i==7){
                startActivityForResult(intent, i);
            }else if(i==8){
                startActivityForResult(intent, i);
            }

        } catch (ActivityNotFoundException a) {
            Toast.makeText(this, "error"+a.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case 1:
                if(resultCode==RESULT_OK && data!=null){

                    ArrayList<String> result1=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_Ename.setText(result1.get(0));

                }
                break;
            case 2:
                if(resultCode==RESULT_OK && data!=null){

                    ArrayList<String> result2=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_Esymptoms.setText(result2.get(0));

                }
                break;

            case 3:
                if(resultCode==RESULT_OK && data!=null){

                    ArrayList<String> result3=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_Ediagnosis.setText(result3.get(0));

                }
                break;
            case 4:
                if(resultCode==RESULT_OK && data!=null){

                    ArrayList<String> result4=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_Emeds1.setText(result4.get(0));

                }
                break;
            case 5:
                if(resultCode==RESULT_OK && data!=null){

                    ArrayList<String> result5=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_Emeds2.setText(result5.get(0));

                }
                break;
            case 6:
                if(resultCode==RESULT_OK && data!=null){

                    ArrayList<String> result6=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_Emeds3.setText(result6.get(0));

                }
                break;
            case 7:
                if(resultCode==RESULT_OK && data!=null){

                    ArrayList<String> result7=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_Emeds4.setText(result7.get(0));

                }
                break;
            case 8:
                if(resultCode==RESULT_OK && data!=null){

                    ArrayList<String> result8=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_Emeds5.setText(result8.get(0));

                }
                break;


        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        smsSentReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(Editinfo.this, "Sms sent", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(Editinfo.this, "Generic failure", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(Editinfo.this, "no service", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(Editinfo.this, "Null pdu", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(Editinfo.this, "Radio off", Toast.LENGTH_SHORT).show();
                }


            }
        };
        smsDeliveredReciever=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()){

                    case Activity.RESULT_OK:
                        Toast.makeText(Editinfo.this, "Sms delivered", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(Editinfo.this, "sms not deliverd", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };

        registerReceiver(smsSentReceiver,new IntentFilter(SENT));
        registerReceiver(smsDeliveredReciever,new IntentFilter(DELIVERED));

    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(smsDeliveredReciever);
        unregisterReceiver(smsSentReceiver);

    }


    public void btn_sendsms_OnClick(View v) {

        final String[] message = new String[100];
        if(et_Emeds2.getText().toString().isEmpty() && et_Emeds3.getText().toString().isEmpty()
                && et_Emeds4.getText().toString().isEmpty() && et_Emeds5.getText().toString().isEmpty()){

            message[0]="medicine 1 : "+et_Emeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                    +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+"\n Time to take medicine 1 is : "+spinner_T1.getSelectedItem()
                    .toString();

        }else if(et_Emeds3.getText().toString().isEmpty() && et_Emeds4.getText().toString().isEmpty() && et_Emeds5.getText().toString().isEmpty()){
            message[0]="medicine 1 : "+et_Emeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                    +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+"\n Time to take medicine 1 is : "+spinner_T1.getSelectedItem()
                    .toString()+
                    "\n medicine 2 : "+et_Emeds2.getText().toString()+"\n pills/day of medicine 2 : "+spinner_P2.getSelectedItem().toString()
                    +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+"\n Time to take medicine 2 is : "+spinner_T2.getSelectedItem()
                    .toString();;



        }else if(et_Emeds4.getText().toString().isEmpty() && et_Emeds5.getText().toString().isEmpty()){

            message[0]="medicine 1 : "+et_Emeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                    +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+"\n Time to take medicine 1 is : "+spinner_T1.getSelectedItem()
                    .toString()+
                    "\n medicine 2 : "+et_Emeds2.getText().toString()+"\n pills/day of medicine 2 : "+spinner_P2.getSelectedItem().toString()
                    +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+"\n Time to take medicine 2 is : "+spinner_T2.getSelectedItem()
                    .toString()+
                    "\n medicine 3 : "+et_Emeds3.getText().toString()+"\n pills/day of medicine 3 : "+spinner_P3.getSelectedItem().toString()
                    +"\n duration of medicine 3 is : " +spinner_D3.getSelectedItem().toString()+"\n Time to take medicine 3 is : "+spinner_T3.getSelectedItem()
                    .toString();


        }else if(et_Emeds5.getText().toString().isEmpty()){

            message[0]="medicine 1 : "+et_Emeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                    +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+"\n Time to take medicine 1 is : "+spinner_T1.getSelectedItem()
                    .toString()+
                    "\n medicine 2 : "+et_Emeds2.getText().toString()+"\n pills/day of medicine 2 : "+spinner_P2.getSelectedItem().toString()
                    +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+"\n Time to take medicine 2 is : "+spinner_T2.getSelectedItem()
                    .toString()+
                    "\n medicine 3 : "+et_Emeds3.getText().toString()+"\n pills/day of medicine 3 : "+spinner_P3.getSelectedItem().toString()
                    +"\n duration of medicine 3 is : " +spinner_D3.getSelectedItem().toString()+"\n Time to take medicine 3 is : "+spinner_T3.getSelectedItem()
                    .toString()+
                    "\n medicine 4 : "+et_Emeds4.getText().toString()+"\n pills/day of medicine 4 : "+spinner_P4.getSelectedItem().toString()
                    +"\n duration of medicine 4 is : " +spinner_D4.getSelectedItem().toString()+"\n Time to take medicine 4 is : "+spinner_T4.getSelectedItem()
                    .toString();

        }else {

            message[0]="medicine 1 : "+et_Emeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                    +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+"\n Time to take medicine 1 is : "+spinner_T1.getSelectedItem()
                    .toString()+
                    "\n medicine 2 : "+et_Emeds2.getText().toString()+"\n pills/day of medicine 2 : "+spinner_P2.getSelectedItem().toString()
                    +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+"\n Time to take medicine 2 is : "+spinner_T2.getSelectedItem()
                    .toString()+
                    "\n medicine 3 : "+et_Emeds3.getText().toString()+"\n pills/day of medicine 3 : "+spinner_P3.getSelectedItem().toString()
                    +"\n duration of medicine 3 is : " +spinner_D3.getSelectedItem().toString()+"\n Time to take medicine 3 is : "+spinner_T3.getSelectedItem()
                    .toString()+
                    "\n medicine 4 : "+et_Emeds4.getText().toString()+"\n pills/day of medicine 4 : "+spinner_P4.getSelectedItem().toString()
                    +"\n duration of medicine 4 is : " +spinner_D4.getSelectedItem().toString()+"\n Time to take medicine 4 is : "+spinner_T4.getSelectedItem()
                    .toString()+
                    "\n medicine 5 : "+et_Emeds5.getText().toString()+"\n pills/day of medicine 5 : "+spinner_P5.getSelectedItem().toString()
                    +"\n duration of medicine 5 is : " +spinner_D5.getSelectedItem().toString()+"\n Time to take medicine 5 is : "+spinner_T5.getSelectedItem()
                    .toString();
        }

        String complete="your name is :"+et_Ename.getText().toString()+"\n your symtom are : "+et_Esymptoms.getText().toString()
                +"\n your diagnosis is : "+et_Ediagnosis.getText().toString();

        String fcomplete=complete+"\n"+message[0];
        SmsManager sms=SmsManager.getDefault();
        String phone=publicarray.info.get(index1).getPhone();
        //time[0] =spinner_T1.getSelectedItem().toString();
        //String ftime="Time to take medicine 1 is :-"+time[0];
        //String ftime2=complete+"\n"+message+"\n"+ftime;
        ArrayList<String> messages = sms.divideMessage(fcomplete);
        sms.sendMultipartTextMessage(phone,null,messages,null,null);


        if(ContextCompat.checkSelfPermission(Editinfo.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(Editinfo.this,new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);

        }

    }


    public void onClickWhatsApp(View view) {

        final String[] message = new String[100];
        if(et_Emeds2.getText().toString().isEmpty() && et_Emeds3.getText().toString().isEmpty()
                && et_Emeds4.getText().toString().isEmpty() && et_Emeds5.getText().toString().isEmpty()){

            message[0]="medicine 1 : "+et_Emeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                    +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+"\n Time to take medicine 1 is : "+spinner_T1.getSelectedItem()
                    .toString();

        }else if(et_Emeds3.getText().toString().isEmpty() && et_Emeds4.getText().toString().isEmpty() && et_Emeds5.getText().toString().isEmpty()){
            message[0]="medicine 1 : "+et_Emeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                    +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+"\n Time to take medicine 1 is : "+spinner_T1.getSelectedItem()
                    .toString()+
                    "\n medicine 2 : "+et_Emeds2.getText().toString()+"\n pills/day of medicine 2 : "+spinner_P2.getSelectedItem().toString()
                    +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+"\n Time to take medicine 2 is : "+spinner_T2.getSelectedItem()
                    .toString();;



        }else if(et_Emeds4.getText().toString().isEmpty() && et_Emeds5.getText().toString().isEmpty()){

            message[0]="medicine 1 : "+et_Emeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                    +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+"\n Time to take medicine 1 is : "+spinner_T1.getSelectedItem()
                    .toString()+
                    "\n medicine 2 : "+et_Emeds2.getText().toString()+"\n pills/day of medicine 2 : "+spinner_P2.getSelectedItem().toString()
                    +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+"\n Time to take medicine 2 is : "+spinner_T2.getSelectedItem()
                    .toString()+
                    "\n medicine 3 : "+et_Emeds3.getText().toString()+"\n pills/day of medicine 3 : "+spinner_P3.getSelectedItem().toString()
                    +"\n duration of medicine 3 is : " +spinner_D3.getSelectedItem().toString()+"\n Time to take medicine 3 is : "+spinner_T3.getSelectedItem()
                    .toString();


        }else if(et_Emeds5.getText().toString().isEmpty()){

            message[0]="medicine 1 : "+et_Emeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                    +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+"\n Time to take medicine 1 is : "+spinner_T1.getSelectedItem()
                    .toString()+
                    "\n medicine 2 : "+et_Emeds2.getText().toString()+"\n pills/day of medicine 2 : "+spinner_P2.getSelectedItem().toString()
                    +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+"\n Time to take medicine 2 is : "+spinner_T2.getSelectedItem()
                    .toString()+
                    "\n medicine 3 : "+et_Emeds3.getText().toString()+"\n pills/day of medicine 3 : "+spinner_P3.getSelectedItem().toString()
                    +"\n duration of medicine 3 is : " +spinner_D3.getSelectedItem().toString()+"\n Time to take medicine 3 is : "+spinner_T3.getSelectedItem()
                    .toString()+
                    "\n medicine 4 : "+et_Emeds4.getText().toString()+"\n pills/day of medicine 4 : "+spinner_P4.getSelectedItem().toString()
                    +"\n duration of medicine 4 is : " +spinner_D4.getSelectedItem().toString()+"\n Time to take medicine 4 is : "+spinner_T4.getSelectedItem()
                    .toString();

        }else {

            message[0]="medicine 1 : "+et_Emeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                    +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+"\n Time to take medicine 1 is : "+spinner_T1.getSelectedItem()
                    .toString()+
                    "\n medicine 2 : "+et_Emeds2.getText().toString()+"\n pills/day of medicine 2 : "+spinner_P2.getSelectedItem().toString()
                    +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+"\n Time to take medicine 2 is : "+spinner_T2.getSelectedItem()
                    .toString()+
                    "\n medicine 3 : "+et_Emeds3.getText().toString()+"\n pills/day of medicine 3 : "+spinner_P3.getSelectedItem().toString()
                    +"\n duration of medicine 3 is : " +spinner_D3.getSelectedItem().toString()+"\n Time to take medicine 3 is : "+spinner_T3.getSelectedItem()
                    .toString()+
                    "\n medicine 4 : "+et_Emeds4.getText().toString()+"\n pills/day of medicine 4 : "+spinner_P4.getSelectedItem().toString()
                    +"\n duration of medicine 4 is : " +spinner_D4.getSelectedItem().toString()+"\n Time to take medicine 4 is : "+spinner_T4.getSelectedItem()
                    .toString()+
                    "\n medicine 5 : "+et_Emeds5.getText().toString()+"\n pills/day of medicine 5 : "+spinner_P5.getSelectedItem().toString()
                    +"\n duration of medicine 5 is : " +spinner_D5.getSelectedItem().toString()+"\n Time to take medicine 5 is : "+spinner_T5.getSelectedItem()
                    .toString();
        }



        try {
            //  String text = "This is a test";// Replace with your message.

            //  String toNumber = "xxxxxxxxxx"; // Replace with mobile phone number without +Sign or leading zeros, but with country code
            //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.

            String phone=publicarray.info.get(index1).getPhone();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"91"+phone +"&text="+message[0]));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

} //class bracket
