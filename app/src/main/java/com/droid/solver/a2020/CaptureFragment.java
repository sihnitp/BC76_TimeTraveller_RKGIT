package com.droid.solver.a2020;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions;
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmark;
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmarkDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.common.FirebaseVisionLatLng;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.CAMERA_SERVICE;

public class CaptureFragment extends Fragment implements View.OnClickListener {

    private static final int PICK_IMAGE = 51;
    private CardView cardView,bottomCardView;
    String currentPhotoPath;
    private TextView monumentDetails,indicator;
    private ImageView imageView;
    private static final int CAMERA_PERMISSION_CODE=13;
    private static final int REQUEST_IMAGE_CAPTURE=14;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private ImageView trackLocationImage;
    private String trackLocationUrl,destinationMonumentName;

    public static  CaptureFragment getInstance(){
        return new CaptureFragment();
    }

    public CaptureFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_capture, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        cardView=view.findViewById(R.id.cardView);
        monumentDetails=view.findViewById(R.id.textShown);
        imageView=view.findViewById(R.id.image);
        trackLocationImage=view.findViewById(R.id.track_location);
        indicator=view.findViewById(R.id.details);
        bottomCardView=view.findViewById(R.id.cardView2);
        bottomCardView.setVisibility(View.INVISIBLE);
        progressBar=view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        trackLocationImage.setVisibility(View.INVISIBLE);
        progressDialog=new ProgressDialog(getActivity());
        indicator.setText("Please Select Or Click The Monument Image");
        cardView.setOnClickListener(this);
        trackLocationImage.setOnClickListener(this);



    }

    //                         long    78.042073     lat 27.174698469698683


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.cardView||view.getId()==R.id.image){
            checkPermission();
        }
        else if(view.getId()==R.id.track_location){
            Toast.makeText(getActivity(), "Redirected to Map ,please wait ...", Toast.LENGTH_SHORT).show();
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                            Uri.parse(trackLocationUrl));
//                    startActivity(intent);
                    Uri gmmIntentUri = Uri.parse("google.navigation:q="+destinationMonumentName+",+India");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }, 1000);

        }
    }

    private void checkPermission() {

        if (getActivity()!=null && (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)||(ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)){

                //request permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);

        } else {
            //permission granted
            showDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==CAMERA_PERMISSION_CODE && grantResults.length>0 &&
                grantResults[0]==PackageManager.PERMISSION_GRANTED){
                showDialog();


        }else{
            Log.i("TAG", "permission denied");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK){
            try{
                progressBar.setVisibility(View.VISIBLE);
                    File f = new File(currentPhotoPath);
                    Uri contentUri = Uri.fromFile(f);
                    imageView.setImageURI(contentUri);
                     bottomCardView.setVisibility(View.VISIBLE);
                     processImage(contentUri);

            }catch (NullPointerException e){
                Log.i("TAG", e.getMessage());
                progressBar.setVisibility(View.GONE);
            }

        }

        else if(requestCode==PICK_IMAGE){
            bottomCardView.setVisibility(View.VISIBLE);
            try {
                progressBar.setVisibility(View.VISIBLE);
                Uri uri = data.getData();
                imageView.setImageURI(uri);
//                Picasso.get().load(uri).into(imageView);
                Log.i("TAG", "inside image uri");
                   processImage(uri);
            }catch (NullPointerException e){
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Image not selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void processImage(Uri uri){
        Log.i("TAG", "bitmap processing");
         FirebaseVisionCloudDetectorOptions options =
                 new FirebaseVisionCloudDetectorOptions.Builder()
                         .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
                         .setMaxResults(10)
                         .build();

        FirebaseVisionImage image=null;
        try {
            image = FirebaseVisionImage.fromFilePath(getActivity(), uri);
        } catch (Exception e) {
            e.printStackTrace();
            progressBar.setVisibility(View.GONE);
        }

           FirebaseVisionCloudLandmarkDetector detector = FirebaseVision.getInstance()
                 .getVisionCloudLandmarkDetector(options);

        final StringBuilder builder = new StringBuilder();
         Task<List<FirebaseVisionCloudLandmark>> result = detector.detectInImage(image)
                 .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionCloudLandmark>>() {
                     @Override
                     public void onSuccess(List<FirebaseVisionCloudLandmark> firebaseVisionCloudLandmarks) {
                         int count=0;
                         for (FirebaseVisionCloudLandmark landmark: firebaseVisionCloudLandmarks) {
                             if(count==1){
                                 break;
                             }

                             String landmarkName = landmark.getLandmark();
                             int cnt=0;
                             builder.append(landmarkName);
                             builder.append("\n");
                             count++;

                             FirebaseVisionLatLng loc=landmark.getLocations().get(0);
                             String base_url="http://maps.google.com/maps?";
                             String query="daddr="+String.valueOf(loc.getLatitude())+","+String.valueOf(loc.getLongitude())+"("+
                                     landmarkName+")";

                             Log.i("GAT", "longitude : "+loc.getLongitude()+" ,latitude : "+loc.getLatitude());

                             trackLocationUrl=base_url+query;
                             trackLocationImage.setVisibility(View.VISIBLE);
                             bottomCardView.setVisibility(View.VISIBLE);

                             indicator.setText("Here's the detailed info!");
                             if(builder.toString().length()==0){
                                 monumentDetails.setText("Not Found");
                             }
                             else{
                                 monumentDetails.setText(builder.toString());
                                 destinationMonumentName=builder.toString();
                             }
                             progressBar.setVisibility(View.GONE);

                         }
                     }
                 })
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Log.i("TAG", "inside exception ,"+e.getMessage());
                         progressBar.setVisibility(View.GONE);
                     }
                 }).addOnCanceledListener(new OnCanceledListener() {
                     @Override
                     public void onCanceled() {
                         Log.i("TAG", "cancelled ");
                         progressBar.setVisibility(View.GONE);
                     }
                 })
                 ;

     }

    private void selectImageFromGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        if(getActivity()!=null) {
            File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );
            currentPhotoPath = image.getAbsolutePath();
            return image;
        }
        return null;
    }

    private void clickPicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (getActivity()!=null && takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.sanskriti.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE );
            }
        }else{
            Toast.makeText(getActivity(), "no camera app", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showDialog(){
        final Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.fragment_image_selection);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View gallery=dialog.findViewById(R.id.galleryLayout);
        View camera=dialog.findViewById(R.id.cameraLayout);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                selectImageFromGallery();


            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                clickPicture();
            }
        });
        dialog.show();
    }

    private void showProgressDialog(){
        progressDialog.setMessage("Processing your image...");
        progressDialog.setCancelable(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Please wait");
        progressDialog.show();
    }
}