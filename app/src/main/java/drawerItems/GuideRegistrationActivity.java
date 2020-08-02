package drawerItems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.droid.solver.a2020.R;
import com.droid.solver.a2020.explorefragment.GuideListActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URISyntaxException;

public class GuideRegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int FILE_SELECT_CODE = 0;
    private Button submit,aadharCardButton;
    private TextInputEditText name,mobile,email,qualification,state,district,charge,details;
    private TextInputLayout nameLayout,mobileLayout,emailLayout,qualificationLayout,stateLayout,districtLayout,chargeLayout,detailsLayout;
    private ProgressBar progressBar;
    private Uri aadharUri=null;
    private UploadTask uploadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_registration);
        init();

    }
    private void init(){
        submit=findViewById(R.id.submit);
        name=findViewById(R.id.guide_name);
        mobile=findViewById(R.id.guide_mobile);
        email=findViewById(R.id.guide_email);
        qualification=findViewById(R.id.guide_qualification);
        state=findViewById(R.id.guide_state);
        district=findViewById(R.id.guide_district);
        charge=findViewById(R.id.guide_charge);
        details=findViewById(R.id.guide_details);
        progressBar=findViewById(R.id.progress_bar);
        aadharCardButton=findViewById(R.id.aadhar_card);

        nameLayout=findViewById(R.id.textInputLayout2);
        mobileLayout=findViewById(R.id.textInputLayout4);
        emailLayout=findViewById(R.id.textInputLayout5);
        qualificationLayout=findViewById(R.id.textInputLayout6);
        stateLayout=findViewById(R.id.textInputLayout7);
        districtLayout=findViewById(R.id.textInputLayout8);
        chargeLayout=findViewById(R.id.textInputLayout9);
        detailsLayout=findViewById(R.id.textInputLayout10);

        submit.setOnClickListener(this);
        aadharCardButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.aadhar_card){
            showFileChooser();
        }
        else if(view.getId()==R.id.submit){
            if(name.getText()==null || name.getText().toString().length()==0){
                nameLayout.setErrorEnabled(true);
                name.setError("Please Enter name");
                return;
            }
            if(mobile.getText()==null || mobile.getText().toString().length()==0 &&
                    !(mobile.getText().toString().length()==10 || mobile.getText().toString().length()==12)) {
                mobileLayout.setErrorEnabled(true);
                mobile.setError("Please Enter valid mobile no");
                return;
            }

            if(state.getText()==null || state.getText().toString().length()==0){
                stateLayout.setErrorEnabled(true);
                state.setError("Please mention your state");
                return;
            }
            if(district.getText()==null || district.getText().toString().length()==0){
                districtLayout.setErrorEnabled(true);
                district.setError("Please mention your district");
                return;
            }
            if(charge.getText()==null || charge.getText().toString().length()==0){
                chargeLayout.setErrorEnabled(true);
                charge.setError("Mention your cost as guide");
                return;
            }
            if(aadharUri==null){
                Toast.makeText(GuideRegistrationActivity.this, "Please Select Your Aadhar card", Toast.LENGTH_LONG).show();
                return;
            }

            String mname=name.getText().toString();
            String mmobile=mobile.getText().toString();
            String memail=email.getText()!=null?email.getText().toString():null;
            String mqualification=qualification.getText()!=null?qualification.getText().toString():null;
            String mstate=state.getText().toString();
            String mdistrict=district.getText().toString();
            String mcharge=charge.getText().toString();
            String mdetails=details.getText()!=null?details.getText().toString():null;

            uploadAadharToStorage(mname,mmobile,memail,mqualification,mstate,mdistrict,mcharge,mdetails);

        }
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    aadharUri = data.getData();
                    if(aadharUri!=null){
                        aadharCardButton.setText("Aadhar Selected");
                    }else{
                        aadharCardButton.setText("Upload Your aadhar card");
                        Toast.makeText(GuideRegistrationActivity.this, "Aadhar not selected", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void uploadAadharToStorage(final String mname, final String mmobile, final String memail, final String mqualification, final String mstate,
                               final String mdistrict, final String mcharge, final String mdetails){
        progressBar.setVisibility(View.VISIBLE);
        final StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("Aadhar/"+System.currentTimeMillis());
        uploadTask = imageRef.putFile(aadharUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i("TAG", "Unsuccessfull attempt");
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(GuideRegistrationActivity.this,"Aadhar not uploaded,try again",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return imageRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){

                            putGuideInDatabaseForVerification(mname, mmobile, memail,
                                    mqualification, mstate, mdistrict, mcharge, mdetails
                                    , task.getResult().toString());

                        }
                        else{
                            Toast.makeText(GuideRegistrationActivity.this,"Aadhar not uploaded,try again",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GuideRegistrationActivity.this,"Aadhar not uploaded,try again",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }


    private void putGuideInDatabaseForVerification(String mname, String mmobile, String memail, String mqualification, String mstate,
                                                   String mdistrict, String mcharge, String mdetails, final String aadharUrl) {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("guideRegistration");
        String key=reference.push().getKey();
        if(key!=null) {
            reference.child(key).child("name").setValue(mname);
            reference.child(key).child("mobile").setValue(mmobile);
            reference.child(key).child("email").setValue(memail);
            reference.child(key).child("qualification").setValue(mqualification);
            reference.child(key).child("state").setValue(mstate);
            reference.child(key).child("district").setValue(mdistrict);
            reference.child(key).child("charge").setValue(mcharge);
            reference.child(key).child("details").setValue(mdetails);
            reference.child(key).child("aadharUrl").setValue(aadharUrl);
        }

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                name.setText("");
                mobile.setText("");
                email.setText("");
                qualification.setText("");
                state.setText("");
                district.setText("");
                charge.setText("");
                details.setText("");
                aadharCardButton.setText("Upload Your aadhar card");
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(GuideRegistrationActivity.this,
                        "Your details is uploaded.We will contact you shortly",Toast.LENGTH_LONG).show();
            }
        }, 2000);





    }
}