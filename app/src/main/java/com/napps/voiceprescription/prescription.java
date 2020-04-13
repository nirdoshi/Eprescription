package com.napps.voiceprescription;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class prescription extends AppCompatActivity{


    TextView tv1;
    EditText et_Pmeds1,et_Pmeds2,et_Pmeds3,et_Pmeds4,et_Pmeds5;
    ImageButton ib_Pvoice1,ib_Pvoice2,ib_Pvoice3,ib_Pvoice4,ib_Pvoice5;
    Patientinfo patientinfo;
    Button btn_Psubmit;
    DatabaseReference reff;
    FirebaseAuth firebaseAuth;
    long id=0;
    long Userid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        //tv1=findViewById(R.id.tv1);

        final Spinner spinner_P1=findViewById(R.id.spinner_P1);
        //final Spinner spinner_D1=findViewById(R.id.spinner_D1);
        final Spinner spinner_P2=findViewById(R.id.spinner_P2);
        //final Spinner spinner_D2=findViewById(R.id.spinner_D2);
        final Spinner spinner_P3=findViewById(R.id.spinner_P3);
        //final Spinner spinner_D3=findViewById(R.id.spinner_D3);
        final Spinner spinner_P4=findViewById(R.id.spinner_P4);
        //final Spinner spinner_D4=findViewById(R.id.spinner_D4);
        final Spinner spinner_P5=findViewById(R.id.spinner_P5);
        //final Spinner spinner_D5=findViewById(R.id.spinner_D5);


        et_Pmeds1=findViewById(R.id.et_Pmeds1);
        et_Pmeds2=findViewById(R.id.et_Pmeds2);
        et_Pmeds3=findViewById(R.id.et_Pmeds3);
        et_Pmeds4=findViewById(R.id.et_Pmeds4);
        et_Pmeds5=findViewById(R.id.et_Pmeds5);


        ib_Pvoice1=findViewById(R.id.ib_Pvoice1);
        ib_Pvoice2=findViewById(R.id.ib_Pvoice2);
        ib_Pvoice3=findViewById(R.id.ib_Pvoice3);
        ib_Pvoice4=findViewById(R.id.ib_Pvoice4);
        ib_Pvoice5=findViewById(R.id.ib_Pvoice5);


        btn_Psubmit=findViewById(R.id.btn_Psubmit);




        //add all previous activity data here to upload in database
        SharedPreferences sharedPreferences= getSharedPreferences("my_key",MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
       /* final String name=sharedPreferences.getString("name","");
        String age=sharedPreferences.getString("age","");
        String phone=sharedPreferences.getString("phone","");
        String symptoms=sharedPreferences.getString("symptoms","");
        String diagnosis=sharedPreferences.getString("diagnosis","");



        patientinfo=new Patientinfo();
        patientinfo.setName(name);
        patientinfo.setAge(age);
        patientinfo.setPhone(phone);
        patientinfo.setSymptoms(symptoms);
        patientinfo.setDiagnosis(diagnosis);
        */


        //database reference
       /* FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();
        patientinfo.setUid(uid);

        reff= FirebaseDatabase.getInstance().getReference().child(uid).child("Patients");


        //autoincrement of patient id in database is here

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    id=(dataSnapshot.getChildrenCount());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */

        //adapters down here for spinners

        ArrayAdapter<String> myadapter=new ArrayAdapter<String>(prescription.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.pills));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_P1.setAdapter(myadapter);
        spinner_P2.setAdapter(myadapter);
        spinner_P3.setAdapter(myadapter);
        spinner_P4.setAdapter(myadapter);
        spinner_P5.setAdapter(myadapter);

       /* ArrayAdapter<String> myadapter2=new ArrayAdapter<String>(prescription.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.duration));
        myadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_D2.setAdapter(myadapter2);
        spinner_D3.setAdapter(myadapter2);
        spinner_D4.setAdapter(myadapter2);
        spinner_D5.setAdapter(myadapter2);
        spinner_D1.setAdapter(myadapter2);
        */



        spinner_P5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String p5=parent.getItemAtPosition(position).toString();
                editor.putString("p5",p5);
                editor.apply();
                //patientinfo.setP5(p5);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*spinner_D5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String d5=parent.getItemAtPosition(position).toString();
                editor.putString("d5",d5);

                //patientinfo.setD5(d5);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */
        spinner_P4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String p4=parent.getItemAtPosition(position).toString();
                editor.putString("p4",p4);
                editor.apply();
                // patientinfo.setP4(p4);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner d4 here

        /*spinner_D4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String d4=parent.getItemAtPosition(position).toString();
                editor.putString("d4",d4);
                editor.apply();
                //patientinfo.setD4(d4);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

         */
        spinner_P3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String p3=parent.getItemAtPosition(position).toString();
                editor.putString("p3",p3);
                editor.apply();

                //patientinfo.setP3(p3);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spineer d3 here

       /* spinner_D3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String d3=parent.getItemAtPosition(position).toString();
                editor.putString("d3",d3);
                editor.apply();
                //patientinfo.setD3(d3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */

        spinner_P2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String p2=parent.getItemAtPosition(position).toString();
                editor.putString("p2",p2);
                editor.apply();

                //patientinfo.setP2(p2);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner d2 here

       /* spinner_D2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String d2=parent.getItemAtPosition(position).toString();
                editor.putString("d2",d2);
                editor.apply();
                //patientinfo.setD2(d2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */
        spinner_P1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String p1=parent.getItemAtPosition(position).toString();
                if(p1.equals("0")){
                    editor.putString("p1","");
                    editor.apply();
                }else {

                    editor.putString("p1", p1);
                    editor.apply();
                }
                //patientinfo.setP1(p1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //spinner d1 here

       /* spinner_D1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String d1 = parent.getItemAtPosition(position).toString();
                if (d1.equals("0")) {
                    editor.putString("d1","");
                    editor.apply();
                } else {
                    editor.putString("d1", d1);
                    editor.apply();
                    //patientinfo.setD1(d1);

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */
        //voice button here
        ib_Pvoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput(1);
            }
        });
        ib_Pvoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput(2);
            }
        });
        ib_Pvoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput(3);
            }
        });
        ib_Pvoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput(4);
            }
        });
        ib_Pvoice5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput(5);
            }
        });



        //button create here
        btn_Psubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_Pmeds1.getText().toString().isEmpty()){

                    editor.putString("meds1","");
                    editor.apply();

                }else {

                    String meds1= et_Pmeds1.getText().toString().trim();
                    editor.putString("meds1",meds1);
                    editor.apply();
                    //patientinfo.setMeds1(meds1);
                }

                if (et_Pmeds2.getText().toString().isEmpty()){

                    editor.putString("meds2","");
                    editor.apply();

                }else {

                    String meds2= et_Pmeds2.getText().toString().trim();
                    editor.putString("meds2",meds2);
                    editor.apply();
                    //patientinfo.setMeds2(meds2);

                }
                if (et_Pmeds3.getText().toString().isEmpty()){

                    editor.putString("meds3","");
                    editor.apply();

                }else {

                    String meds3= et_Pmeds3.getText().toString().trim();
                    editor.putString("meds3",meds3);
                    editor.apply();
                    //patientinfo.setMeds3(meds3);

                }
                if (et_Pmeds4.getText().toString().isEmpty()){

                    editor.putString("meds4","");
                    editor.apply();


                }else {

                    String meds4= et_Pmeds4.getText().toString().trim();
                    editor.putString("meds4",meds4);
                    editor.apply();
                    //patientinfo.setMeds4(meds4);

                }
                if (et_Pmeds5.getText().toString().isEmpty()){

                    editor.putString("meds5","");
                    editor.apply();

                }else {

                    String meds5= et_Pmeds5.getText().toString().trim();
                    editor.putString("meds5",meds5);
                    editor.apply();
                    //patientinfo.setMeds5(meds5);

                }


                    //sending selective message here
              /*  if(et_Pmeds2.getText().toString().isEmpty() && et_Pmeds3.getText().toString().isEmpty()
                && et_Pmeds4.getText().toString().isEmpty() && et_Pmeds5.getText().toString().isEmpty()){

                    String message="medicine 1 : "+et_Pmeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                            +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString();
                    editor.putString("message",message);
                    editor.apply();

                }else if(et_Pmeds3.getText().toString().isEmpty() && et_Pmeds4.getText().toString().isEmpty() && et_Pmeds5.getText().toString().isEmpty()){
                    String message="medicine 1 : "+et_Pmeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                            +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+
                            "\n medicine 2 : "+et_Pmeds2.getText().toString()+"\n pills/day of medicine 2 : "+spinner_P2.getSelectedItem().toString()
                            +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString();

                    editor.putString("message",message);
                    editor.apply();

                }else if(et_Pmeds4.getText().toString().isEmpty() && et_Pmeds5.getText().toString().isEmpty()){

                    String message="medicine 1 : "+et_Pmeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                            +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+
                            "\n medicine 2 : "+et_Pmeds2.getText().toString()+"\n pills/day of medicine 2 : "+spinner_P2.getSelectedItem().toString()
                            +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+
                            "\n medicine 3 : "+et_Pmeds3.getText().toString()+"\n pills/day of medicine 3 : "+spinner_P3.getSelectedItem().toString()
                            +"\n duration of medicine 3 is : " +spinner_D3.getSelectedItem().toString();
                    editor.putString("message",message);
                    editor.apply();

                }else if(et_Pmeds5.getText().toString().isEmpty()){

                    String message="medicine 1 : "+et_Pmeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                            +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+
                            "\n medicine 2 : "+et_Pmeds2.getText().toString()+"\n pills/day of medicine 2 : "+spinner_P2.getSelectedItem().toString()
                            +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+
                            "\n medicine 3 : "+et_Pmeds3.getText().toString()+"\n pills/day of medicine 3 : "+spinner_P3.getSelectedItem().toString()
                            +"\n duration of medicine 3 is : " +spinner_D3.getSelectedItem().toString()+
                            "\n medicine 4 : "+et_Pmeds4.getText().toString()+"\n pills/day of medicine 4 : "+spinner_P4.getSelectedItem().toString()
                            +"\n duration of medicine 4 is : " +spinner_D4.getSelectedItem().toString()
                            ;
                    editor.putString("message",message);
                    editor.apply();
                }else {

                    String message="medicine 1 : "+et_Pmeds1.getText().toString()+"\n pills/day of medicine 1 : "+spinner_P1.getSelectedItem().toString()
                            +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+
                            "\n medicine 2 : "+et_Pmeds2.getText().toString()+"\n pills/day of medicine 2 : "+spinner_P2.getSelectedItem().toString()
                            +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+
                            "\n medicine 3 : "+et_Pmeds3.getText().toString()+"\n pills/day of medicine 3 : "+spinner_P3.getSelectedItem().toString()
                            +"\n duration of medicine 3 is : " +spinner_D3.getSelectedItem().toString()+
                            "\n medicine 4 : "+et_Pmeds4.getText().toString()+"\n pills/day of medicine 4 : "+spinner_P4.getSelectedItem().toString()
                            +"\n duration of medicine 4 is : " +spinner_D4.getSelectedItem().toString()+
                            "\n medicine 5 : "+et_Pmeds5.getText().toString()+"\n pills/day of medicine 5 : "+spinner_P5.getSelectedItem().toString()
                            +"\n duration of medicine 5 is : " +spinner_D5.getSelectedItem().toString()
                            ;

                    editor.putString("message",message);
                    editor.apply();
                }

               */




               /* patientinfo.setId(id+1);
                reff.child(String.valueOf(id+1)).setValue(patientinfo);
                Toast.makeText(prescription.this, "added to database", Toast.LENGTH_SHORT).show();
                */
                startActivity(new Intent(prescription.this,duration.class));
            }
        });





    } //oncreate bracket


    //voice method here

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
            }else {
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
                    et_Pmeds1.setText(result1.get(0));

                }
                break;
            case 2:
                if(resultCode==RESULT_OK && data!=null){

                    ArrayList<String> result2=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_Pmeds2.setText(result2.get(0));

                }
                break;

            case 3:
                if(resultCode==RESULT_OK && data!=null){

                    ArrayList<String> result3=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_Pmeds3.setText(result3.get(0));

                }
                break;
            case 4:
                if(resultCode==RESULT_OK && data!=null){

                    ArrayList<String> result4=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_Pmeds4.setText(result4.get(0));

                }
                break;
            case 5:
                if(resultCode==RESULT_OK && data!=null){

                    ArrayList<String> result5=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_Pmeds5.setText(result5.get(0));

                }
                break;


        }

    }


}   //class bracket
