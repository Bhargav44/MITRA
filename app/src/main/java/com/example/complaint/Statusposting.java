package com.example.complaint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Statusposting extends AppCompatActivity {
    RadioButton rb1,rb2,rb3;
    EditText Reason;
    String radiobutton;

    TextView tv_id;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusposting);
        rb1 = findViewById(R.id.finished);
        rb2 = findViewById(R.id.inprogress);
        rb3 = findViewById(R.id.notfinished);
        Reason=findViewById(R.id.comreason);



        Intent i=getIntent();
        String id=i.getStringExtra("id");
        TextView tv_id=findViewById(R.id.displayid);
        tv_id.setText(id);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
        }

    public void Send(View view) {

        String reason=Reason.getText().toString();
        Intent i=getIntent();
        String id=i.getStringExtra("id");
        String category=i.getStringExtra("cat");
        String description=i.getStringExtra("des");
        String phonenumber=i.getStringExtra("Number");
        if(rb1.isChecked()){
            radiobutton=rb1.getText().toString();
        }else if(rb2.isChecked()){
            radiobutton=rb2.getText().toString();
        }else if(rb3.isChecked()){
            radiobutton=rb3.getText().toString();
        }
        String status=radiobutton;
        Log.d("ale",""+status);
        Statuspojo statuspojo=new Statuspojo(category,description,status,reason);
        reference.child("Status").child(id).push().setValue(statuspojo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Statusposting.this, "Status posted", Toast.LENGTH_SHORT).show();
            }
        });
        String statusSms="Complaint: "+description+"\n Status: "+status+"\n Reason: "+reason;
        SmsManager manager=SmsManager.getDefault();
        Log.d("ale", phonenumber);
        manager.sendTextMessage(phonenumber,null,statusSms,null,null);
        Toast.makeText(this, "msg send to"+phonenumber, Toast.LENGTH_SHORT).show();


    }
}
