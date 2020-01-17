package com.droid.solver.a2020;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;



public class CaptureImageSelectionDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private CaptureFragment fragment;
    private LinearLayout galleryLayout,cameraLayout;

    public CaptureImageSelectionDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_image_selection, container, false);
        galleryLayout=view.findViewById(R.id.galleryLayout);
        cameraLayout=view.findViewById(R.id.cameraLayout);
        fragment=new CaptureFragment();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        galleryLayout.setOnClickListener(this);
        cameraLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.galleryLayout:
                getDialog().cancel();
                break;
            case R.id.cameraLayout:
                getDialog().cancel();
                break;
        }
    }

}