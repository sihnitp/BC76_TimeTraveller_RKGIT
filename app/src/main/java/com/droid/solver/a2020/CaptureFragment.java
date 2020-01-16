package com.droid.solver.a2020;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.CAMERA_SERVICE;

public class CaptureFragment extends Fragment implements View.OnClickListener {

    private static final int PICK_IMAGE = 51;
    private Uri imageUri;
    private CardView cardView;
    private TextView textView;
    private ImageView imageView;
    private static final int CAMERA_PERMISSION_CODE=13;
    private static final int CAMERA_REQUEST_CODE=14;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

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
        textView=view.findViewById(R.id.textShown);
        imageView=view.findViewById(R.id.image);
        cardView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.cardView||view.getId()==R.id.image){
            takePicture();
        }
    }

    private void takePicture() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {

                //show an explanation for requesting request

            } else {
                //request permission
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
            }
        } else {
            //permission granted

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==CAMERA_PERMISSION_CODE && grantResults.length>0 &&
                grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(getActivity()!=null && intent.resolveActivity(getActivity().getPackageManager())!=null){
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }

        }else{
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==CAMERA_REQUEST_CODE && resultCode==RESULT_OK){
            try{

                Bitmap thumbnail=MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),imageUri );
                imageView.setImageBitmap(thumbnail);

            }catch (Exception e){
                Log.i("TAG", e.getMessage());
            }
        }
    }

    private void processImage(Bitmap bitmap){
        Log.i("TAG", "bitmap processing");
         FirebaseVisionCloudDetectorOptions options =
                 new FirebaseVisionCloudDetectorOptions.Builder()
                         .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
                         .setMaxResults(10)
                         .build();


           FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
           FirebaseVisionCloudLandmarkDetector detector = FirebaseVision.getInstance()
                 .getVisionCloudLandmarkDetector(options);

         Task<List<FirebaseVisionCloudLandmark>> result = detector.detectInImage(image)
                 .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionCloudLandmark>>() {
                     @Override
                     public void onSuccess(List<FirebaseVisionCloudLandmark> firebaseVisionCloudLandmarks) {
                         for (FirebaseVisionCloudLandmark landmark: firebaseVisionCloudLandmarks) {

                             Rect bounds = landmark.getBoundingBox();
                             String landmarkName = landmark.getLandmark();
                             String entityId = landmark.getEntityId();
                             float confidence = landmark.getConfidence();
                             Log.i("TAG", "inside success");
                             for (FirebaseVisionLatLng loc: landmark.getLocations()) {

                                 double latitude = loc.getLatitude();
                                 double longitude = loc.getLongitude();

                                 Log.i("TAG", "landmark name : "+landmarkName);
                                 Log.i("TAG", "entityid : "+entityId);
                                 Log.i("TAG", "confidence : "+confidence);
                                 Log.i("TAG", "latitude : "+latitude);
                                 Log.i("TAG", "longitude  : "+longitude);
                             }
                         }
                     }
                 })
                 .addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Log.i("TAG", "inside exception ,"+e.getMessage());
                     }
                 }).addOnCanceledListener(new OnCanceledListener() {
                     @Override
                     public void onCanceled() {
                         Log.i("TAG", "cancelled ");
                     }
                 })
                 ;
     }

    private void selectImageFromGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image to upload"), PICK_IMAGE);
    }


}