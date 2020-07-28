package com.droid.solver.a2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class guideList extends AppCompatActivity {
    public void newActivity(View view) {
        Intent myIntent = new Intent(view.getContext(), guideProfile.class);
        startActivityForResult(myIntent, 0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_list);
    }
}
