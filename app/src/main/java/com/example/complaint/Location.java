package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class Location extends AppCompatActivity implements View.OnClickListener {
    TextView tv_disp, tv_latlong;
    ImageView profileimage;
    Button b_location, b_camera, b_gallery,b_save_button;
    Spinner sp_district, sp_category;
    EditText et;
    LocationManager manager;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase database;
    DatabaseReference reference;

    DatePicker complaintDate;

    public static final int REQUEST_CODE_CAMERA = 22;
    public static final int REQUEST_CODE_GALLERY = 33;
    private static final int PERMISSION_REQUEST_CODE = 200;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        b_location = findViewById(R.id.location_button);
        sp_district=findViewById(R.id.district);
        sp_category=findViewById(R.id.category);
        complaintDate=findViewById(R.id.complaint_date);
        tv_disp=findViewById(R.id.description);
        b_camera = findViewById(R.id.camera_button);
        b_gallery = findViewById(R.id.gallery_button);
        b_save_button=findViewById(R.id.save_button);

        profileimage = findViewById(R.id.images);
        tv_latlong = findViewById(R.id.latlong);

        b_location.setOnClickListener(this);
        b_camera.setOnClickListener(this);
        b_gallery.setOnClickListener(this);
        b_save_button.setOnClickListener(this);


        manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();


        database=FirebaseDatabase.getInstance();
        reference=database.getReference();

    }

    public void imageupload(Uri filepath){
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("uploading file");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMax(100);
        pd.setCancelable(false);
        pd.show();
        storageReference= storageReference.child("images/"+ UUID.randomUUID().toString());
        storageReference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Toast.makeText(Location.this, "IMAGE UPLOADED SUCCESSFULLY", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Location.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                pd.setProgress((int) progress);
            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_button:
                opencamera();
                break;
            case R.id.gallery_button:
                opengallery();
                break;
            case R.id.location_button:
                getcurrentlocation();
                break;
            case R.id.save_button:
                saveData();
                break;



        }

    }


    private void saveData() {

        final  String id= UUID.randomUUID().toString();
        final String latlong=tv_latlong.getText().toString();
        final String district=sp_district.getSelectedItem().toString();
        final String category=sp_category.getSelectedItem().toString();
        final String description=tv_disp.getText().toString();
        Intent n=getIntent();
        final String mobilenumber=n.getStringExtra("number");

        //String userId=n.getStringExtra("userid");
        String complaint_day=""+complaintDate.getDayOfMonth();
        String complaint_month=""+complaintDate.getMonth();
        String complaint_year=""+complaintDate.getYear();
        final String complt_Date=""+complaint_day+"-"+complaint_month+"-"+complaint_year;
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imagepath=uri.toString();
                Student s=new Student(id,district,category,description,latlong,imagepath,complt_Date,mobilenumber);
                reference.child("Complaints").child(district).child(category).push().setValue(s).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Location.this, "your details saved successfully", Toast.LENGTH_SHORT).show();
                        tv_latlong.setText("");
                        tv_disp.setText("");

                    }
                });
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getcurrentlocation() {
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                tv_latlong.setText("" + latitude + "," + longitude);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, listener);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,1,listener);
    }

    private void opengallery() {
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,REQUEST_CODE_GALLERY);
    }

    private void opencamera() {
        Intent g=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(g,REQUEST_CODE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA) {

            if (resultCode == RESULT_OK) {
                Bitmap b = (Bitmap) data.getExtras().get("data");
                Uri u=getImageUri(this,b);
                profileimage.setImageURI(u);
                imageupload(u);

            }

        }
        else if (requestCode==REQUEST_CODE_GALLERY){
            if (resultCode==RESULT_OK){
                Uri u=data.getData();
                profileimage.setImageURI(u);
                imageupload(u);
            }

        }
    }
    public Uri getImageUri(Location inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
