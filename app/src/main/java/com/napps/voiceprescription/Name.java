package com.napps.voiceprescription;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class Name extends AppCompatActivity {

    EditText et_Nname,et_Nage,et_Nphone,et_Nemail;
    RadioButton rb_sex;
    Button btn_Nnext;
    ImageButton ib_Nvoice;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);


        et_Nphone=findViewById(R.id.et_Nphone);
        et_Nname=findViewById(R.id.et_Nname);
        et_Nage=findViewById(R.id.et_Nage);
        et_Nemail=findViewById(R.id.et_Nemail);
        radioGroup=findViewById(R.id.radio_group);

        ib_Nvoice=findViewById(R.id.ib_Nvoice);



        btn_Nnext=findViewById(R.id.btn_Nnext);

        //PersonInfo personInfo=new PersonInfo();
        //et_Nname.setText(personInfo.getName());


        ib_Nvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                promptSpeechInput();

            }
        });

        btn_Nnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = et_Nname.getText().toString().trim();
                String email=et_Nemail.getText().toString().trim();
                String age = et_Nage.getText().toString().trim();
                String phone = et_Nphone.getText().toString().trim();

                if(et_Nname.getText().toString().isEmpty() || et_Nage.getText().toString().isEmpty() ||
                et_Nphone.getText().toString().isEmpty()){

                    Toast.makeText(Name.this, "please enter all fields", Toast.LENGTH_SHORT).show();

                }else if (Integer.parseInt(age)>=130) {
                    Toast.makeText(Name.this, "please enter a valid age", Toast.LENGTH_SHORT).show();
                }
                else if(phone.length()!=10) {
                    Toast.makeText(Name.this, "please enter a valid phone number", Toast.LENGTH_SHORT).show();
                }else {
//                    String gender=rb_sex.getText().toString().trim();
                    int radiogroupid=radioGroup.getCheckedRadioButtonId();
                    radioButton= (RadioButton) findViewById(radiogroupid);
                    SharedPreferences sharedPreferences = getSharedPreferences("my_key", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name);
                    editor.putString("age",age);
                    editor.putString("phone",phone);
                    editor.putString("email",email);
                    editor.putString("sex",radioButton.getText().toString());
  //                  editor.putString("gender",gender);
                    editor.apply();
                    Intent intent = new Intent(Name.this, Symptoms.class);
                    startActivity(intent);
                }


            }
        });


    }


    private void promptSpeechInput() {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_IN");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"speak something..");
            //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en_IN");
            //intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
            //    getString(R.string.speech_prompt));
            try {
                startActivityForResult(intent, 10);
            } catch (ActivityNotFoundException a) {
                Toast.makeText(this, "error"+a.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode){

                case 10:
                    if(resultCode==RESULT_OK && data!=null){

                        ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                        et_Nname.setText(result.get(0));
                    }
                    break;
            }

    }
}
