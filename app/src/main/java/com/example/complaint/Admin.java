package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {
    EditText et1,et2;
    FirebaseDatabase database;
    DatabaseReference reference;
    List<User> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        et1=findViewById(R.id.username);
        et2=findViewById(R.id.password);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        list = new ArrayList<>();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User u = snapshot.getValue(User.class);
                    list.add(u);
                }
                Toast.makeText(Admin.this, "" + list.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void adminlogin(View view) {
        final String username = et1.getText().toString();
        final String password = et2.getText().toString();

    //    for (int i = 0; i < list.size(); i++) {
            if ((username.equals("Admin")) && (password.equals("Admin"))){
                startActivity(new Intent(this, Location.class));
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                Intent v = new Intent(this,Viewstatus.class);
                startActivity(v);
            } else {

                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }


   // }
}

