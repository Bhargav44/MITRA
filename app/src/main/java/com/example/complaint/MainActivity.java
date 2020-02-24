package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText et_username, et_password,et_loginid;
    FirebaseDatabase database;
    DatabaseReference reference;
    List<User> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_username = findViewById(R.id.loginusername);
        et_password = findViewById(R.id.loginpassword);
        //  et_loginid=findViewById(R.id.loginid);
        list = new ArrayList<>();



        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User u = snapshot.getValue(User.class);
                    list.add(u);
                }
                Toast.makeText(MainActivity.this, "" + list.size(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void login(View view) {
        final String username = et_username.getText().toString();
        final String password = et_password.getText().toString();
        for (int i = 0; i < list.size(); i++) {
            Log.d("ale...Garu",list.get(i).getUsername()+" "+list.get(i).getPassword());
            if ((username.equals(list.get(i).getUsername()) && (password.equals(list.get(i).getPassword())))) {
                 Intent l=new Intent(this, Location.class);
                String reg_mobile=list.get(i).getMobileno();
                String user_name=list.get(i).getUsername();
                l.putExtra("number",reg_mobile);
                Log.d("alll",""+reg_mobile);
                l.putExtra("username",user_name);
                 startActivity(l);
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                break;
            } else {

               // Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
      }


    }

    public void signup(View view) {
        Intent i=new Intent(this,Registration.class);
        startActivity(i);
    }
}
