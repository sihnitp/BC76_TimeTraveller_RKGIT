package drawerItems;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.droid.solver.a2020.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GuideRegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private Button submit;
    private TextInputEditText name,mobile,email,qualification,state,district,charge,details;
    private TextInputLayout nameLayout,mobileLayout,emailLayout,qualificationLayout,stateLayout,districtLayout,chargeLayout,detailsLayout;
    private ProgressBar progressBar;
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

        nameLayout=findViewById(R.id.textInputLayout2);
        mobileLayout=findViewById(R.id.textInputLayout4);
        emailLayout=findViewById(R.id.textInputLayout5);
        qualificationLayout=findViewById(R.id.textInputLayout6);
        stateLayout=findViewById(R.id.textInputLayout7);
        districtLayout=findViewById(R.id.textInputLayout8);
        chargeLayout=findViewById(R.id.textInputLayout9);
        detailsLayout=findViewById(R.id.textInputLayout10);

        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.submit){
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

            String mname=name.getText().toString();
            String mmobile=mobile.getText().toString();
            String memail=email.getText()!=null?email.getText().toString():null;
            String mqualification=qualification.getText()!=null?qualification.getText().toString():null;
            String mstate=state.getText().toString();
            String mdistrict=district.getText().toString();
            String mcharge=charge.getText().toString();
            String mdetails=details.getText()!=null?details.getText().toString():null;

            putGuideInDatabaseForVerification(mname,mmobile,memail,mqualification,mstate,mdistrict,mcharge,mdetails);

        }
    }

    private void putGuideInDatabaseForVerification(String mname, String mmobile, String memail, String mqualification, String mstate,
                                                   String mdistrict, String mcharge, String mdetails) {
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
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(GuideRegistrationActivity.this,
                        "Your details is uploaded.We will contact you shortly",Toast.LENGTH_LONG).show();
            }
        }, 2000);





    }
}