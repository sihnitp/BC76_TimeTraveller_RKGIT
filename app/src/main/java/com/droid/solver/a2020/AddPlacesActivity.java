package com.droid.solver.a2020;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AddPlacesActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE = 51;
    private CardView cardView;
    private ImageView imageView;
    private TextInputEditText cityNameEditText,aboutEditText;
    private MaterialButton button;
    private Toolbar toolbar;
    private StorageReference rootRef;
    private byte[] selectedImageData;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);
        rootRef= FirebaseStorage.getInstance().getReference();
        init();

    }

    private void init(){
        cardView=findViewById(R.id.cardView);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        imageView=findViewById(R.id.image);
        cityNameEditText=findViewById(R.id.city_name_edit_text);
        aboutEditText=findViewById(R.id.about_edit_text);
        button=findViewById(R.id.button);
        progressBar=findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        cardView.setOnClickListener(this);
        button.setOnClickListener(this);
        toolbar.setTitle("Add Places");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardView:
                selectImageFromGallery();
                break;
            case R.id.button:
                if(cityNameEditText.getText()==null||cityNameEditText.getText().length()==0){
                    showMessage("City name not selected");
                    return ;
                }
                else if(aboutEditText.getText()==null||aboutEditText.getText().length()==0){
                    showMessage("Write something about your selction");
                    return ;
                }
                uploadSelectedImage();
                break;
        }
    }

    private void selectImageFromGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image to upload"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            Uri path=data.getData();
            try{
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                selectedImageData = baos.toByteArray();

            }catch (IOException e){
                Log.i("TAG", "Exception occured in selecting image ,"+e.getMessage());
            }

        }else if(requestCode==RESULT_CANCELED){
            Toast.makeText(this, "Picture not selected", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadSelectedImage(){
        progressBar.setVisibility(View.VISIBLE);
        final StorageReference reference=rootRef.child("addplace").child(UUID.randomUUID().toString());
        UploadTask uploadTask = reference.putBytes(selectedImageData);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddPlacesActivity.this, "Places not uploaded", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url=uri.toString();
                        String description=String.valueOf(aboutEditText.getText());
                        String cityName=String.valueOf(cityNameEditText.getText());
                        uploadToDatabase(url, cityName, description);

                    }
                });
            }
        });

    }

    public void uploadToDatabase(String url,String cityName,String description){

        DatabaseReference root=FirebaseDatabase.getInstance().getReference();
        String key=root.child("addplaces").push().getKey();
        if(key!=null) {
            root.child("addplaces").child(key).child("cityname").setValue(cityName);
            root.child("addplaces").child(key).child("description").setValue(description);
            root.child("addplaces").child(key).child("image").setValue(url).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                 showMessage("Uploaded successfully");
                 progressBar.setVisibility(View.INVISIBLE);
                 imageView.setImageDrawable(getDrawable(R.drawable.add_image));
                 aboutEditText.setText("");
                 cityNameEditText.setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showMessage("Error occured,uploading failed");
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }else{
            showMessage("Error occured ,try again");
            progressBar.setVisibility(View.INVISIBLE);

        }
    }

    private void showMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
