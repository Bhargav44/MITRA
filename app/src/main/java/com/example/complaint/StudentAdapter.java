package com.example.complaint;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {
    Context ct;
    List<Student> studentList;
    String id;


    public StudentAdapter(Context applicationContext, List<Student> list) {
        ct = applicationContext;
        studentList = list;
    }

    @NonNull
    @Override
    public StudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ct).inflate(R.layout.viewstatusrow,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentAdapter.MyViewHolder holder, final int position) {
        holder.id.setText("ID:"+studentList.get(position).getId());
        holder.description.setText("COMPLAINT:"+studentList.get(position).getDescription());
        holder.latlong.setText("LATLONG:"+studentList.get(position).getLatlong());

        holder.adpdate.setText("DATE:"+studentList.get(position).getComplaintdate());
        Glide.with(ct).load(studentList.get(position).getImagepath()).into(holder.imagepath);
       /* holder.locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latlong = studentList.get(position).getLatlong();
                Uri u = Uri.parse("geo:"+latlong);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ct.startActivity(i);
                holder.moboleno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri u = Uri.parse("tel:"+studentList.get(position).getDescription());
                        Intent i = new Intent(Intent.ACTION_DIAL);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ct.startActivity(i);

                    }
                });


            }
        });*/
       holder.status.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i=new Intent(ct,Statusposting.class);
               id=studentList.get(position).getId();
               i.putExtra("id",id);
               i.putExtra("cat",studentList.get(position).getCategory());
               i.putExtra("des",studentList.get(position).getDescription());
               i.putExtra("Number",studentList.get(position).getMobilenumber());
                             ct.startActivity(i);

           }
       });
       holder.viewbox.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String phonenum=studentList.get(position).getMobilenumber();
               SmsManager manager=SmsManager.getDefault();
               Log.d("ale", phonenum);
               manager.sendTextMessage(phonenum,null,"your complaint is verified",null,null);
               Toast.makeText(ct, "msg send to"+phonenum, Toast.LENGTH_SHORT).show();

           }
       });

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imagepath;
        TextView id, description,latlong,adpdate;
        Button status;
        CheckBox viewbox;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            imagepath = itemView.findViewById(R.id.dispimage);
            id=itemView.findViewById(R.id.dispid);
            adpdate=itemView.findViewById(R.id.complaintdisplay_date);
          description=itemView.findViewById(R.id.dispdescription);
          latlong=itemView.findViewById(R.id.displatlong);
          viewbox=itemView.findViewById(R.id.viewed);
          status=itemView.findViewById(R.id.complaint_status);



        }
    }
}

