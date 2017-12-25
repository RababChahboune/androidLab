package com.example.learning.androidlabwork;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.learning.androidlabwork.Model.Post;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class CameraActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView newImage;
    EditText newDescription;
    EditText newTitle;
    Button share;
    Bitmap imageBitmap;
    private StorageReference mStorageRef;
    LocationManager locationManager;
    String imageUUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        //get random id for image
        imageUUID = UUID.randomUUID().toString();
        //get firebase instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //create a storage ref to our image
        mStorageRef = FirebaseStorage.getInstance().getReference().child(imageUUID);
        //get referance to our posts
        final DatabaseReference myRef=  database.getReference("posts");
        //init our UI
        newImage = (ImageView) findViewById(R.id.newImage);
        newDescription = (EditText) findViewById(R.id.newDescritpion);
        newTitle = (EditText) findViewById(R.id.newTitle);
        share = (Button) findViewById(R.id.share_button);
        //mStorageRef = FirebaseStorage.getInstance().getReference().child("9466ffcc-e1b4-4abd-b92d-189f0ed8914a");

        //init location to get coordinates of user
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        dispatchTakePictureIntent();

        share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                uploadImage(imageBitmap,myRef);
            }
        });
    }


    private void uploadImage(Bitmap bm,DatabaseReference dbRef){

        if(newDescription.getText().toString().equals("") || newTitle.getText().toString().equals("")){
            Toast.makeText(CameraActivity.this,"You must fill all fields",Toast.LENGTH_SHORT);
        }
        else{
            //so that if uplaod is successful save description...
            final DatabaseReference ref = dbRef;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = mStorageRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(CameraActivity.this,"Fail upload",Toast.LENGTH_SHORT);
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    //Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    //the coordinate are not working I will pass them now and try it later
                    ref.push().setValue(
                            new Post(
                                    imageUUID,newTitle.getText().toString(),newDescription.getText().toString(),
                                    0.0,0.0));
                    startActivity(new Intent(CameraActivity.this,MainActivity.class));
                }
            });

        }


    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            newImage.setImageBitmap(imageBitmap);
        }else{
            startActivity(new Intent(CameraActivity.this,MainActivity.class));
        }
    }

    @Override
    public void onBackPressed(){
        // do something here and don't write super.onBackPressed()
        startActivity(new Intent(CameraActivity.this,MainActivity.class));
    }
}
