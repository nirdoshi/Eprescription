package com.napps.voiceprescription;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Symptoms extends AppCompatActivity {

    //TextView tv1;
    EditText et_Ssymptoms,et_Sdiagnosis;
    ImageButton ib_Svoice1,ib_Svoice2;

    Button btn_Snext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);


      //  tv1=findViewById(R.id.tv1);

        et_Ssymptoms=findViewById(R.id.et_Ssymptoms);
        et_Sdiagnosis=findViewById(R.id.et_Sdiagnosis);
        ib_Svoice1=findViewById(R.id.ib_Svoice1);
        ib_Svoice2=findViewById(R.id.ib_Svoice2);
        btn_Snext=findViewById(R.id.btn_Snext);

       //SharedPreferences sharedPreferences=getSharedPreferences("my_key",MODE_PRIVATE);
        //String name=sharedPreferences.getString("phone","");
        //tv1.setText(name);
        //personInfo.setName("nir");
       // tv1.setText(personInfo.getName());

        ib_Svoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput(10);
            }
        });
        ib_Svoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput(9);
            }
        });


        btn_Snext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(et_Sdiagnosis.getText().toString().isEmpty() || et_Ssymptoms.getText().toString().isEmpty()){

                Toast.makeText(Symptoms.this, "please enter all details", Toast.LENGTH_SHORT).show();

            }
            else {

                String symptoms=et_Ssymptoms.getText().toString().trim();
                String diagnosis=et_Sdiagnosis.getText().toString().trim();

                SharedPreferences sharedPreferences=getSharedPreferences("my_key",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();

                editor.putString("symptoms",symptoms);
                editor.putString("diagnosis",diagnosis);
                editor.apply();

                startActivity(new Intent(Symptoms.this,prescription.class));


            }

                //  startActivity(new Intent(Symptoms.this,test.class));



            }
        });

    }


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
            if(i==10) {
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

            case 9:
                if(resultCode==RESULT_OK && data!=null){

                    ArrayList<String> result2=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_Sdiagnosis.setText(result2.get(0));

                }
                break;
            case 10:
                if(resultCode==RESULT_OK && data!=null){

                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_Ssymptoms.setText(result.get(0));

                }
                break;
        }

    }



}
