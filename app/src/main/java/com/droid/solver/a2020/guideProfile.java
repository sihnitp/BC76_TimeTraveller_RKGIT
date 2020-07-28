package com.droid.solver.a2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class guideProfile extends AppCompatActivity {
    public  void emailActivity(View view)
    {
        Intent intent=new Intent(Intent.ACTION_SEND);
        String[] recipients={"guidemail@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Subject here..");
        intent.putExtra(Intent.EXTRA_TEXT,"Body here..");
        intent.putExtra(Intent.EXTRA_CC,"xyz@gmail.com");
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Send mail"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_profile);
    }
}
