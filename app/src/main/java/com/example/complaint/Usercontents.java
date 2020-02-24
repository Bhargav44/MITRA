package com.example.complaint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Usercontents extends AppCompatActivity {
    RecyclerView rv;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercontents);
        rv=findViewById(R.id.mycomplaints);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
    }

    public void newcomplaint(View view) {
       Intent i=getIntent();
       String number=i.getStringExtra("number");

        Intent m=new Intent(this,Location.class);
        Log.d("alei",""+number);
       // m.putExtra("username",)
        m.putExtra("mobile",number);
        startActivity(m);
    }

    public void click(View view) {

    }
}
