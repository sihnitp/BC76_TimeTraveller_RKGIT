package com.droid.solver.a2020;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TrendingFragment extends Fragment {

    public static TrendingFragment getInstance(){
        return  new TrendingFragment();
    }
    public TrendingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trending, container, false);
    }

}
