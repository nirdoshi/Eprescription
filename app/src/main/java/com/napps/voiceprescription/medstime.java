package com.napps.voiceprescription;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class medstime extends AppCompatActivity {
    TextView tv1,tv2,tv3,tv4,tv5;
    Button btn_submit,btn_Email,btn_whatsapp,btn_message;
    DatabaseReference reff;
    FirebaseAuth firebaseAuth;
    String id="";
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =1 ;
    String SENT="SMS_SENT";
    String DELIVERED="SMS_DELIVERED";
    PendingIntent sentPI,deliveredPI;
    BroadcastReceiver smsSentReceiver,smsDeliveredReciever;
    Spinner spinner_T1,spinner_T2,spinner_T3,spinner_T4,spinner_T5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medstime);

        sentPI=PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
        deliveredPI=PendingIntent.getBroadcast(this,0,new Intent(DELIVERED),0);

        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);
        tv5=findViewById(R.id.tv5);
                        spinner_T1=findViewById(R.id.spinner_T1);
                        spinner_T2=findViewById(R.id.spinner_T2);
                        spinner_T3=findViewById(R.id.spinner_T3);
                        spinner_T4=findViewById(R.id.spinner_T4);
                        spinner_T5=findViewById(R.id.spinner_T5);
        btn_submit=findViewById(R.id.btn_submit);
       btn_Email=findViewById(R.id.btn_email);
       btn_message=findViewById(R.id.btn_message);
       btn_whatsapp=findViewById(R.id.btn_whatsapp);
        // btn_message=findViewById(R.id.btn_message);
        //all previous data is collected here;



        final SharedPreferences sharedPreferences= getSharedPreferences("my_key",MODE_PRIVATE);
        final String name=sharedPreferences.getString("name","");
        final String age=sharedPreferences.getString("age","");
        String phone=sharedPreferences.getString("phone","");
        String sex=sharedPreferences.getString("sex","");
        final String symptoms=sharedPreferences.getString("symptoms","");
        final String diagnosis=sharedPreferences.getString("diagnosis","");
        String meds1=sharedPreferences.getString("meds1","");
        String meds2=sharedPreferences.getString("meds2","");
        String meds3=sharedPreferences.getString("meds3","");
        String meds4=sharedPreferences.getString("meds4","");
        String meds5=sharedPreferences.getString("meds5","");
        //String meds1=sharedPreferences.getString("meds1","");
        String p1=sharedPreferences.getString("p1","");
        String p2=sharedPreferences.getString("p2","");
        String p3=sharedPreferences.getString("p3","");
        String p4=sharedPreferences.getString("p4","");
        String p5=sharedPreferences.getString("p5","");

        String d1=sharedPreferences.getString("d1","");
        String d2=sharedPreferences.getString("d2","");
        String d3=sharedPreferences.getString("d3","");
        String d4=sharedPreferences.getString("d4","");
        String d5=sharedPreferences.getString("d5","");
        final String email=sharedPreferences.getString("email","");


        if(meds1.isEmpty()){
            tv1.setVisibility(View.GONE);
            spinner_T1.setVisibility(View.GONE);
        }
        if(meds2.isEmpty()){
            tv2.setVisibility(View.GONE);
            spinner_T2.setVisibility(View.GONE);
        }
        if(meds3.isEmpty()){
            tv3.setVisibility(View.GONE);
            spinner_T3.setVisibility(View.GONE);
        }
        if(meds4.isEmpty()){
            tv4.setVisibility(View.GONE);
            spinner_T4.setVisibility(View.GONE);
        }
        if(meds5.isEmpty()){
            tv5.setVisibility(View.GONE);
            spinner_T5.setVisibility(View.GONE);
        }


        tv1.setText(meds1);
        tv2.setText(meds2);
        tv3.setText(meds3);
        tv4.setText(meds4);
        tv5.setText(meds5);
        //all previous data is set here;
        final Patientinfo patientinfo=new Patientinfo();

        patientinfo.setName(name);
        patientinfo.setAge(age);
        patientinfo.setPhone(phone);
        patientinfo.setSex(sex);
        patientinfo.setSymptoms(symptoms);
        patientinfo.setDiagnosis(diagnosis);

        patientinfo.setMeds1(meds1);
        patientinfo.setMeds2(meds2);
        patientinfo.setMeds3(meds3);
        patientinfo.setMeds4(meds4);
        patientinfo.setMeds5(meds5);

        patientinfo.setP1(p1);
        patientinfo.setP2(p2);
        patientinfo.setP3(p3);
        patientinfo.setP4(p4);
        patientinfo.setP5(p5);

        patientinfo.setD1(d1);
        patientinfo.setD2(d2);
        patientinfo.setD3(d3);
        patientinfo.setD4(d4);
        patientinfo.setD5(d5);
        patientinfo.setEmail(email);

        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();
        patientinfo.setUid(uid);

        reff= FirebaseDatabase.getInstance().getReference().child(uid).child("Patients");


        //autoincrement of patient id in database is here

      /*  reff.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if (mutableData.getValue() == null) {
                    mutableData.setValue(1);
                } else {
                    int count = mutableData.getValue(Integer.class);
                    mutableData.setValue(count + 1);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean success, DataSnapshot dataSnapshot) {
                // Analyse databaseError for any error during increment
            }
        });

       */







       /* reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    //id=(dataSnapshot.getChildrenCount());
                     id=dataSnapshot.getKey();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */


        //time spinner adapter here;

        ArrayAdapter<String> myadapter3=new ArrayAdapter<String>(medstime.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.time));
        spinner_T1.setAdapter(myadapter3);
        spinner_T2.setAdapter(myadapter3);
        spinner_T3.setAdapter(myadapter3);
        spinner_T4.setAdapter(myadapter3);
        spinner_T5.setAdapter(myadapter3);

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

        btn_Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences= getSharedPreferences("my_key",MODE_PRIVATE);
                String message=sharedPreferences.getString("message","");


                String complete="your name is : "+name+"\n your age is : "+age+"\n your symptoms are : "+symptoms+
                        "\n diagnosis : "+diagnosis;

                final String[] time1 = new String[10];
                final String[] ftime3=new String[100];
                if(spinner_T2.getSelectedItem().toString().equals("0") && spinner_T3.getSelectedItem().toString().equals("0")
                        &&spinner_T4.getSelectedItem().toString().equals("0") && spinner_T5.getSelectedItem().toString().equals("0")
                ){

                    time1[0] =spinner_T1.getSelectedItem().toString();
                    String ftime="Time to take medicine 1 is :-"+time1[0];
                    ftime3[0]=complete+"\n"+message+"\n"+ftime;
                    //time="Time to take medcine 1 : "+spinner_T1.getSelectedItem().toString();

                }else if(spinner_T3.getSelectedItem().toString().equals("0") && spinner_T4.getSelectedItem().toString().equals("0")
                        && spinner_T5.getSelectedItem().toString().equals("0")){

                    time1[0]="Time to take medicine 1 : "+spinner_T1.getSelectedItem().toString()+"\n Time to take medicine 2 : "
                            +spinner_T2.getSelectedItem().toString();

                    ftime3[0]=complete+"\n"+message+"\n"+time1[0];


                }else if(spinner_T4.getSelectedItem().toString().equals("0") && spinner_T5.getSelectedItem().toString().equals("0")){

                    time1[0]="Time to take medicine 1 : "+spinner_T1.getSelectedItem().toString()+"\n Time to take medicine 2 : "+spinner_T2.getSelectedItem().toString()
                            +" \nTime to make medicine 3 : "+spinner_T3.getSelectedItem().toString()
                    ;
                    ftime3[0]=complete+"\n"+message+"\n"+time1[0];


                }else if(spinner_T5.getSelectedItem().toString().equals("0")){

                    time1[0]="Time to take medicine 1 : "+spinner_T1.getSelectedItem().toString()+"\n Time to take medicine 2 : "+spinner_T2.getSelectedItem().toString()
                            +" \n Time to make medicine 3 : "+spinner_T3.getSelectedItem().toString()+"\n Time to take medicine 4 : "+spinner_T4.getSelectedItem().toString()
                    ;
                    ftime3[0]=complete+"\n"+message+"\n"+time1[0];


                }else {

                    time1[0]="Time to take medicine 1 : "+spinner_T1.getSelectedItem().toString()+"\n Time to take medicine 2 : "+spinner_T2.getSelectedItem().toString()
                            +" \n Time to make medicine 3 : "+spinner_T3.getSelectedItem().toString()+"\n Time to take medicine 4 : "+spinner_T4.getSelectedItem().toString()
                            +"\n Time to make medcine 5 : "+spinner_T5.getSelectedItem().toString()
                    ;
                    ftime3[0]=complete+"\n"+message+"\n"+time1[0];

                }



                String[] To=email.split(",");


                Intent intent =new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,To);
                intent.putExtra(Intent.EXTRA_SUBJECT,"prescription");
                intent.putExtra(Intent.EXTRA_TEXT,ftime3[0]);
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent,"choose an email client"));
            }

        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id=reff.push().getKey();
                patientinfo.setId(id);
                //reff.child(String.valueOf(id+1)).setValue(patientinfo);
                reff.child(id).setValue(patientinfo);
                Toast.makeText(medstime.this, "added to database", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(medstime.this,MainActivity.class));
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        smsSentReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(medstime.this, "Sms sent", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(medstime.this, "Generic failure", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(medstime.this, "no service", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(medstime.this, "Null pdu", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(medstime.this, "Radio off", Toast.LENGTH_SHORT).show();
                }


            }
        };
        smsDeliveredReciever=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()){

                    case Activity.RESULT_OK:
                        Toast.makeText(medstime.this, "Sms delivered", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(medstime.this, "sms not deliverd", Toast.LENGTH_SHORT).show();
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

    public void btn_sendsms_OnClick(View v){
      //  Patientinfo patientinfo=new Patientinfo();
       // String message;

       /* if(ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);

        }else {
         */

        /*if(ContextCompat.checkSelfPermission(medstime.this,Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
        }else {
            permission();
        }*/


            SharedPreferences sharedPreferences = getSharedPreferences("my_key", MODE_PRIVATE);
            String name = sharedPreferences.getString("name", "");
            String phone = sharedPreferences.getString("phone", "");
            String age = sharedPreferences.getString("age", "");
            String symptoms = sharedPreferences.getString("symptoms", "");
            String diagnosis = sharedPreferences.getString("diagnosis", "");
            String message = sharedPreferences.getString("message", "");

            String complete = "your name is : " + name + "\n your age is : " + age + "\n your symptoms are : " + symptoms +
                    "\n diagnosis : " + diagnosis;

            final String[] time = new String[10];

            if (spinner_T2.getSelectedItem().toString().equals("0") && spinner_T3.getSelectedItem().toString().equals("0")
                    && spinner_T4.getSelectedItem().toString().equals("0") && spinner_T5.getSelectedItem().toString().equals("0")
            ) {

                SmsManager sms = SmsManager.getDefault();
                time[0] = spinner_T1.getSelectedItem().toString();
                String ftime = "Time to take medicine 1 is :-" + time[0];
                String ftime2 = complete + "\n" + message + "\n" + ftime;
               /* ArrayList<String> messages = sms.divideMessage(ftime2);
                sms.sendMultipartTextMessage(phone, null, messages, null, null);
                */
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phone));
                intent.putExtra("sms_body", ftime2);
                startActivity(intent);


                /* for (String msg : messages) {

                sms.sendTextMessage(phone,null,msg,sentPI,deliveredPI);
            }*/

                //time="Time to take medcine 1 : "+spinner_T1.getSelectedItem().toString();

            } else if (spinner_T3.getSelectedItem().toString().equals("0") && spinner_T4.getSelectedItem().toString().equals("0")
                    && spinner_T5.getSelectedItem().toString().equals("0")) {
                SmsManager sms = SmsManager.getDefault();
                time[0] = "Time to take medicine 1 : " + spinner_T1.getSelectedItem().toString() + "\n Time to take medicine 2 : "
                        + spinner_T2.getSelectedItem().toString();

                String ftime2 = complete + "\n" + message + "\n" + time[0];
                // List<String> messages = sms.divideMessage(ftime2);
               /* ArrayList<String> messages = sms.divideMessage(ftime2);
                sms.sendMultipartTextMessage(phone, null, messages, null, null);
                */
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phone));
                intent.putExtra("sms_body", ftime2);
                startActivity(intent);


                /* for (String msg : messages) {

                sms.sendTextMessage(phone,null,msg,sentPI,deliveredPI);
            }*/

            } else if (spinner_T4.getSelectedItem().toString().equals("0") && spinner_T5.getSelectedItem().toString().equals("0")) {
                SmsManager sms = SmsManager.getDefault();
                time[0] = "Time to take medicine 1 : " + spinner_T1.getSelectedItem().toString() + "\n Time to take medicine 2 : " + spinner_T2.getSelectedItem().toString()
                        + " \nTime to make medicine 3 : " + spinner_T3.getSelectedItem().toString()
                ;
                String ftime2 = complete + "\n" + message + "\n" + time[0];
                //ArrayList<String> messages = sms.divideMessage(ftime2);
                //sms.sendMultipartTextMessage(phone, null, messages, null, null);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phone));
                intent.putExtra("sms_body", ftime2);
                startActivity(intent);

                /*for (String msg : messages) {

                sms.sendTextMessage(phone,null,msg,sentPI,deliveredPI);
            }*/

            } else if (spinner_T5.getSelectedItem().toString().equals("0")) {
                SmsManager sms = SmsManager.getDefault();
                time[0] = "Time to take medicine 1 : " + spinner_T1.getSelectedItem().toString() + "\n Time to take medicine 2 : " + spinner_T2.getSelectedItem().toString()
                        + " \nTime to make medicine 3 : " + spinner_T3.getSelectedItem().toString() + "\n Time to take medicine 4 : " + spinner_T4.getSelectedItem().toString()
                ;
                String ftime2 = complete + "\n" + message + "\n" + time[0];
                //ArrayList<String> messages = sms.divideMessage(ftime2);
                //sms.sendMultipartTextMessage(phone, null, messages, null, null);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phone));
                intent.putExtra("sms_body", ftime2);
                startActivity(intent);

                /*for (String msg : messages) {

                sms.sendTextMessage(phone,null,msg,sentPI,deliveredPI);
            }*/

            } else {
                SmsManager sms = SmsManager.getDefault();
                time[0] = "Time to take medicine 1 : " + spinner_T1.getSelectedItem().toString() + "\n Time to take medicine 2 : " + spinner_T2.getSelectedItem().toString()
                        + " \n Time to make medicine 3 : " + spinner_T3.getSelectedItem().toString() + "\n Time to take medicine 4 : " + spinner_T4.getSelectedItem().toString()
                        + "\n Time to make medcine 5 : " + spinner_T5.getSelectedItem().toString()
                ;
                String ftime2 = complete + "\n" + message + "\n" + time[0];
               // ArrayList<String> messages = sms.divideMessage(ftime2);
                //sms.sendMultipartTextMessage(phone, null, messages, null, null);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phone));
                intent.putExtra("sms_body", ftime2);
                startActivity(intent);

                /*for (String msg : messages) {

                sms.sendTextMessage(phone,null,msg,sentPI,deliveredPI);
            }*/

            }



       /* String complete="your name is : "+name+"\n your age is : "+age+"\n your symptoms are : "+symptoms+
                "\n diagnosis : "+diagnosis; */


        }

        /*else {
            String Fcomplete=complete+"\n"+message+"\n"+ time[0];
            SmsManager sms=SmsManager.getDefault();
            sms.sendTextMessage(phone,null,Fcomplete,sentPI,deliveredPI);


        }*/



    public void onClickWhatsApp(View view) {
        SharedPreferences sharedPreferences= getSharedPreferences("my_key",MODE_PRIVATE);
        String name=sharedPreferences.getString("name","");
        String phone=sharedPreferences.getString("phone","");
        String age=sharedPreferences.getString("age","");
        String symptoms=sharedPreferences.getString("symptoms","");
        String diagnosis=sharedPreferences.getString("diagnosis","");
        String message=sharedPreferences.getString("message","");

        String complete="your name is : "+name+"\n your age is : "+age+"\n your symptoms are : "+symptoms+
                "\n diagnosis : "+diagnosis;

        final String[] time = new String[10];
        final String[] ftime2=new String[100];
        if(spinner_T2.getSelectedItem().toString().equals("0") && spinner_T3.getSelectedItem().toString().equals("0")
                &&spinner_T4.getSelectedItem().toString().equals("0") && spinner_T5.getSelectedItem().toString().equals("0")
        ){

            time[0] =spinner_T1.getSelectedItem().toString();
            String ftime="Time to take medicine 1 is :-"+time[0];
            ftime2[0]=complete+"\n"+message+"\n"+ftime;
            //time="Time to take medcine 1 : "+spinner_T1.getSelectedItem().toString();

        }else if(spinner_T3.getSelectedItem().toString().equals("0") && spinner_T4.getSelectedItem().toString().equals("0")
                && spinner_T5.getSelectedItem().toString().equals("0")){

            time[0]="Time to take medicine 1 : "+spinner_T1.getSelectedItem().toString()+"\n Time to take medicine 2 : "
                    +spinner_T2.getSelectedItem().toString();

            ftime2[0]=complete+"\n"+message+"\n"+time[0];


        }else if(spinner_T4.getSelectedItem().toString().equals("0") && spinner_T5.getSelectedItem().toString().equals("0")){

            time[0]="Time to take medicine 1 : "+spinner_T1.getSelectedItem().toString()+"\n Time to take medicine 2 : "+spinner_T2.getSelectedItem().toString()
                    +" \nTime to make medicine 3 : "+spinner_T3.getSelectedItem().toString()
            ;
            ftime2[0]=complete+"\n"+message+"\n"+time[0];


        }else if(spinner_T5.getSelectedItem().toString().equals("0")){

            time[0]="Time to take medicine 1 : "+spinner_T1.getSelectedItem().toString()+"\n Time to take medicine 2 : "+spinner_T2.getSelectedItem().toString()
                    +" \n Time to make medicine 3 : "+spinner_T3.getSelectedItem().toString()+"\n Time to take medicine 4 : "+spinner_T4.getSelectedItem().toString()
            ;
            ftime2[0]=complete+"\n"+message+"\n"+time[0];


        }else {

            time[0]="Time to take medicine 1 : "+spinner_T1.getSelectedItem().toString()+"\n Time to take medicine 2 : "+spinner_T2.getSelectedItem().toString()
                    +" \n Time to make medicine 3 : "+spinner_T3.getSelectedItem().toString()+"\n Time to take medicine 4 : "+spinner_T4.getSelectedItem().toString()
                    +"\n Time to make medcine 5 : "+spinner_T5.getSelectedItem().toString()
            ;
             ftime2[0]=complete+"\n"+message+"\n"+time[0];

        }


        try {
          //  String text = "This is a test";// Replace with your message.

          //  String toNumber = "xxxxxxxxxx"; // Replace with mobile phone number without +Sign or leading zeros, but with country code
            //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.


            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"91"+phone +"&text="+ftime2[0]));
            startActivity(intent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }




}
