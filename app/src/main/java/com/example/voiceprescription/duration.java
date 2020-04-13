package com.example.voiceprescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class duration extends AppCompatActivity {

    TextView tv1,tv2,tv3,tv4,tv5;
    Button btn_Dnext;
    Spinner spinner_D1,spinner_D2,spinner_D3,spinner_D4,spinner_D5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duration);
        spinner_D1=findViewById(R.id.spinner_D1);
        spinner_D2=findViewById(R.id.spinner_D2);
        spinner_D3=findViewById(R.id.spinner_D3);
        spinner_D4=findViewById(R.id.spinner_D4);
        spinner_D5=findViewById(R.id.spinner_D5);

        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);
        tv5=findViewById(R.id.tv5);

        btn_Dnext=findViewById(R.id.btn_Dnext);

        SharedPreferences sharedPreferences= getSharedPreferences("my_key",MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
        final String p1=sharedPreferences.getString("p1","");
        final String p2=sharedPreferences.getString("p2","");
        final String p3=sharedPreferences.getString("p3","");
        final String p4=sharedPreferences.getString("p4","");
        final String p5=sharedPreferences.getString("p5","");

        String meds1=sharedPreferences.getString("meds1","");
        String meds2=sharedPreferences.getString("meds2","");
        String meds3=sharedPreferences.getString("meds3","");
        String meds4=sharedPreferences.getString("meds4","");
        String meds5=sharedPreferences.getString("meds5","");






        if(meds1.isEmpty()){
            tv1.setVisibility(View.GONE);
            spinner_D1.setVisibility(View.GONE);
        }
        if(meds2.isEmpty()){
            tv2.setVisibility(View.GONE);
            spinner_D2.setVisibility(View.GONE);
        }
        if(meds3.isEmpty()){
            tv3.setVisibility(View.GONE);
            spinner_D3.setVisibility(View.GONE);
        }
        if(meds4.isEmpty()){
            tv4.setVisibility(View.GONE);
            spinner_D4.setVisibility(View.GONE);
        }
        if(meds5.isEmpty()){
            tv5.setVisibility(View.GONE);
            spinner_D5.setVisibility(View.GONE);
        }

        tv1.setText(meds1);
        tv2.setText(meds2);
        tv3.setText(meds3);
        tv4.setText(meds4);
        tv5.setText(meds5);



        ArrayAdapter<String> myadapter2=new ArrayAdapter<String>(
                duration.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.duration));
        myadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_D2.setAdapter(myadapter2);
        spinner_D3.setAdapter(myadapter2);
        spinner_D4.setAdapter(myadapter2);
        spinner_D5.setAdapter(myadapter2);
        spinner_D1.setAdapter(myadapter2);


        spinner_D1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String d1=parent.getItemAtPosition(position).toString();
                editor.putString("d1",d1);
                editor.apply();
                //patientinfo.setD5(d5);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_D2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String d2=parent.getItemAtPosition(position).toString();
                editor.putString("d2",d2);
                editor.apply();
                //patientinfo.setD5(d5);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_D3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String d3=parent.getItemAtPosition(position).toString();
                editor.putString("d3",d3);
                editor.apply();
                //patientinfo.setD5(d5);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_D4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String d4=parent.getItemAtPosition(position).toString();
                editor.putString("d4",d4);
                editor.apply();

                //patientinfo.setD5(d5);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_D5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String d5=parent.getItemAtPosition(position).toString();
                editor.putString("d5",d5);
                editor.apply();

                //patientinfo.setD5(d5);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_Dnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tv2.getText().toString().isEmpty() && tv3.getText().toString().isEmpty()
                        && tv4.getText().toString().isEmpty() && tv5.getText().toString().isEmpty()){

                    String message="medicine 1 : "+tv1.getText().toString()+"\n pills/day of medicine 1 : "+p1
                            +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString();
                    editor.putString("message",message);
                    editor.apply();

                }else if(tv3.getText().toString().isEmpty() && tv4.getText().toString().isEmpty() && tv5.getText().toString().isEmpty()){
                    String message="medicine 1 : "+tv1.getText().toString()+"\n pills/day of medicine 1 : "+p1
                            +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+
                            "\n medicine 2 : "+tv2.getText().toString()+"\n pills/day of medicine 2 : "+p2
                            +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString();

                    editor.putString("message",message);
                    editor.apply();

                }else if(tv4.getText().toString().isEmpty() && tv5.getText().toString().isEmpty()){

                    String message="medicine 1 : "+tv1.getText().toString()+"\n pills/day of medicine 1 : "+p1
                            +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+
                            "\n medicine 2 : "+tv2.getText().toString()+"\n pills/day of medicine 2 : "+p2
                            +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+
                            "\n medicine 3 : "+tv3.getText().toString()+"\n pills/day of medicine 3 : "+p3
                            +"\n duration of medicine 3 is : " +spinner_D3.getSelectedItem().toString();
                    editor.putString("message",message);
                    editor.apply();

                }else if(tv5.getText().toString().isEmpty()){

                    String message="medicine 1 : "+tv1.getText().toString()+"\n pills/day of medicine 1 : "+p1
                            +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+
                            "\n medicine 2 : "+tv2.getText().toString()+"\n pills/day of medicine 2 : "+p2
                            +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+
                            "\n medicine 3 : "+tv3.getText().toString()+"\n pills/day of medicine 3 : "+p3
                            +"\n duration of medicine 3 is : " +spinner_D3.getSelectedItem().toString()+
                            "\n medicine 4 : "+tv4.getText().toString()+"\n pills/day of medicine 4 : "+p4
                            +"\n duration of medicine 4 is : " +spinner_D4.getSelectedItem().toString()
                            ;
                    editor.putString("message",message);
                    editor.apply();
                }else {

                    String message="medicine 1 : "+tv1.getText().toString()+"\n pills/day of medicine 1 : "+p1
                            +"\n duration of medicine 1 is : " +spinner_D1.getSelectedItem().toString()+
                            "\n medicine 2 : "+tv2.getText().toString()+"\n pills/day of medicine 2 : "+p2
                            +"\n duration of medicine 2 is : " +spinner_D2.getSelectedItem().toString()+
                            "\n medicine 3 : "+tv3.getText().toString()+"\n pills/day of medicine 3 : "+p3
                            +"\n duration of medicine 3 is : " +spinner_D3.getSelectedItem().toString()+
                            "\n medicine 4 : "+tv4.getText().toString()+"\n pills/day of medicine 4 : "+p4
                            +"\n duration of medicine 4 is : " +spinner_D4.getSelectedItem().toString()+
                            "\n medicine 5 : "+tv5.getText().toString()+"\n pills/day of medicine 5 : "+p5
                            +"\n duration of medicine 5 is : " +spinner_D5.getSelectedItem().toString()
                            ;

                    editor.putString("message",message);
                    editor.apply();
                }


                startActivity(new Intent(duration.this,medstime.class));






            }
        });



    }
}
