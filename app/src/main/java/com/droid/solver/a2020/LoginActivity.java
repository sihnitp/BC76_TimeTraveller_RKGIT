package com.droid.solver.a2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.PendingIntentRequiredException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private int RC_SIGN_IN=101;

    public static final String MY_PREF="MY_PREF";
    public static final String NAME="NAME";
    public static final String EMAIL="EMAIL";
    public static final String PHOTO_URL="PHOTO_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());
        startActivity(new Intent(this,MainActivity.class));
        finish();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        if(user!=null){
            //go to mainActivity
        }
        else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setIsSmartLockEnabled(false)
                            .setLogo(R.drawable.logo)
                            .setTheme(R.style.FullscreenTheme)
                            .build(),
                    RC_SIGN_IN);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user");
                    String uid = user.getUid();
                    reference.child(uid).child("name").setValue(user.getDisplayName());
                    reference.child(uid).child("email").setValue(user.getEmail());


                    SharedPreferences preferences =getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=  preferences.edit();
                    editor.putString(NAME, user.getDisplayName());
                    editor.putString(EMAIL, user.getEmail());
                    editor.putString(PHOTO_URL, user.getPhotoUrl().toString());
                    editor.apply();

                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
            } else {
                if(response!=null&&response.getError()!=null) {
                    Toast.makeText(this, "sign in  failed ,", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
