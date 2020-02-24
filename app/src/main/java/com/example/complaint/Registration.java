package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    EditText et_name,et_mobile,et_email,et_username,et_password,et_newid;
    Button b_save;


    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            //EventLogTags.Description.setVisibility(View.INVISIBLE);
            new AlertDialog.Builder(Registration.this)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage("Please Check your Internetconnection")
                    .setPositiveButton("OK", null).show();
        }else {
            Toast.makeText(this,
                    "Iternet is Available", Toast.LENGTH_SHORT).show();
        }

        et_name=findViewById(R.id.name);
        et_mobile=findViewById(R.id.mobile);
        et_email=findViewById(R.id.email);
        et_username=findViewById(R.id.username);
        et_password=findViewById(R.id.password);
        //et_newid=findViewById(R.id.newid);

        b_save=findViewById(R.id.savebutton);



        database=FirebaseDatabase.getInstance();
        reference=database.getReference("users");
        b_save.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.savebutton:
                saveData();
                break;
        }
    }

    private void saveData() {

        String name=et_name.getText().toString();
        final String mobile=et_mobile.getText().toString();
        String email=et_email.getText().toString();
        final String usernam=et_username.getText().toString();
        String password=et_password.getText().toString();
        User U = new User(name,mobile,email,usernam,password);
        reference.push().setValue(U).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Registration.this, "saved", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Registration.this,MainActivity.class);
                i.putExtra("Mobilenum",mobile);

                startActivity(i);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("tom",e.getMessage());
            }
        });


    }
}

