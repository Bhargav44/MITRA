
package com.example.complaint;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Viewstatus extends AppCompatActivity {
     Spinner sp_district,sp_category;
     RecyclerView rv;

     FirebaseDatabase database;
     DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewstatus);
        sp_district=findViewById(R.id.complaint_district);
        sp_category=findViewById(R.id.spcomplaint);
        rv=findViewById(R.id.recycle);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Complaints");



    }


    public void getData(View view) {
       // Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        String selectdistrict=sp_district.getSelectedItem().toString();
        String selectcategory=sp_category.getSelectedItem().toString();
       // Toast.makeText(this, ""+selectdistrict+selectcategory, Toast.LENGTH_SHORT).show();

       reference.child(selectdistrict).child(selectcategory).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               List<Student> list=new ArrayList<>();
               for(DataSnapshot snapshot:dataSnapshot.getChildren())
               {
                   Student s = snapshot.getValue(Student.class);
                   list.add(s);
               }
               Toast.makeText(Viewstatus.this, ""+list.size(), Toast.LENGTH_SHORT).show();

               rv.setLayoutManager(new LinearLayoutManager(Viewstatus.this));
               rv.setAdapter(new StudentAdapter(Viewstatus.this,list));
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }
}

