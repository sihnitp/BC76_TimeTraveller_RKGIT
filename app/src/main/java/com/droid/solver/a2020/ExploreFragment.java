package com.droid.solver.a2020;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;

public class ExploreFragment extends Fragment implements View.OnClickListener {

    private String [] state=new String[]{"Bihar","Uttar Pradesh","Delhi","Madhya pradesh","Andhra pradesh"};
    private String [] district=new String[]{"Bihar","Uttar Pradesh","Delhi","Madhya pradesh","Andhra pradesh"};
    private String [] city=new String[]{"Bihar","Uttar Pradesh","Delhi","Madhya pradesh","Andhra pradesh"};

    private ArrayAdapter<String> adapter;
    private AutoCompleteTextView stateAutoComplete,districtAutoComplete,cityAutoComplete;
    private MaterialButton button;

    public static ExploreFragment getInstance(){
        return new ExploreFragment();
    }

    public ExploreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_explore, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        adapter=new ArrayAdapter<>(getActivity(),R.layout.dropdown_menuitem,state);
        stateAutoComplete= view.findViewById(R.id.state);
        districtAutoComplete=view.findViewById(R.id.district);
        cityAutoComplete=view.findViewById(R.id.city);
        button=view.findViewById(R.id.search_button);
        stateAutoComplete.setAdapter(adapter);
        districtAutoComplete.setAdapter(adapter);
        cityAutoComplete.setAdapter(adapter);
        button.setOnClickListener(this);

        stateAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showMessage(state[i]+" ");
            }
        });

        districtAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showMessage(district[i]+" ");
            }
        });

        cityAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showMessage(city[i]+" ");
            }
        });
    }

    @Override
    public void onClick(View view) {
        showMessage("button ");
    }
    private void showMessage(String s){
        Toast.makeText(getActivity(), s+" is clicked", Toast.LENGTH_SHORT).show();
    }


}
