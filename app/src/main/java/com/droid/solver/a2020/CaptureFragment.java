package com.droid.solver.a2020;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.content.Context.CAMERA_SERVICE;

public class CaptureFragment extends Fragment implements View.OnClickListener {

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

    public void takePicture(){

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {
                //show an explanation
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            }
        } else {
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getActivity().getPackageManager())!=null){
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
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
        if(requestCode==CAMERA_REQUEST_CODE && resultCode== Activity.RESULT_OK){
            Bitmap picBitmap=null;
            if(data!=null && data.getExtras()!=null && data.getExtras().get("data")!=null){

                 picBitmap= (Bitmap) data.getExtras().get("data");
                 imageView.setImageBitmap(picBitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                picBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                processImage(byteArray);
            }else{
                Toast.makeText(getActivity(), "Bitmap is null ", Toast.LENGTH_SHORT).show();
            }
        }
    }

     private void processImage(byte[] buffer){
        Log.i("TAG", "bitmap processing");
         FirebaseVisionCloudDetectorOptions options =
                 new FirebaseVisionCloudDetectorOptions.Builder()
                         .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
                         .setMaxResults(10)
                         .build();
         int rotation=0;
         try {
              rotation=getRotationCompensation("", getActivity(),getActivity() );
         } catch (CameraAccessException e) {
             e.printStackTrace();
         }

         FirebaseVisionImageMetadata metadata = new FirebaseVisionImageMetadata.Builder()
                 .setWidth(480)
                 .setHeight(360)
                 .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_NV21)
                 .setRotation(rotation)
                 .build();
           FirebaseVisionImage image = FirebaseVisionImage.fromByteArray(buffer, metadata);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getRotationCompensation(String cameraId, Activity activity, Context context)
            throws CameraAccessException {
        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation = ORIENTATIONS.get(deviceRotation);

        CameraManager cameraManager = (CameraManager) context.getSystemService(CAMERA_SERVICE);
        int sensorOrientation = cameraManager.getCameraCharacteristics(cameraId).get(CameraCharacteristics.SENSOR_ORIENTATION);
        rotationCompensation = (rotationCompensation + sensorOrientation + 270) % 360;

        // Return the corresponding FirebaseVisionImageMetadata rotation value.
        int result;
        switch (rotationCompensation) {
            case 0:
                result = FirebaseVisionImageMetadata.ROTATION_0;
                break;
            case 90:
                result = FirebaseVisionImageMetadata.ROTATION_90;
                break;
            case 180:
                result = FirebaseVisionImageMetadata.ROTATION_180;
                break;
            case 270:
                result = FirebaseVisionImageMetadata.ROTATION_270;
                break;
            default:
                result = FirebaseVisionImageMetadata.ROTATION_0;
                Log.i("TAG", "Bad rotation value: " + rotationCompensation);
        }
        return result;
    }

}