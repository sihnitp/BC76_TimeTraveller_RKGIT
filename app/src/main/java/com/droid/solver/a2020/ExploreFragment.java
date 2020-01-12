package com.droid.solver.a2020;


import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExploreFragment extends Fragment implements View.OnClickListener {

    private String [] state=new String[]{"Bihar","Uttar Pradesh","Delhi","Madhya pradesh","Andhra pradesh"};
    private String [] city=new String[]{"Bihar","Uttar Pradesh","Delhi","Madhya pradesh","Andhra pradesh"};


    private ArrayAdapter<String> adapter;
    private AutoCompleteTextView stateAutoComplete,cityAutoComplete;
    private MaterialButton button;

    public static ExploreFragment getInstance(){
        return new ExploreFragment();
    }

    public ExploreFragment() {}

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
        cityAutoComplete=view.findViewById(R.id.city);
        button=view.findViewById(R.id.search_button);
        stateAutoComplete.setAdapter(adapter);
        cityAutoComplete.setAdapter(adapter);
        button.setOnClickListener(this);

        stateAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showMessage(state[i]+" ");
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
     //   makeDatabaseSchema();

       //reference.setValue("patna", "ganga");

//        Map<String,SkillModel> skillModelMap=new HashMap<>();
//        skillModelMap.put("skills", new SkillModel("image", "imageurl"));
//        Map<String,PracticesModel> practicesModelMap=new HashMap<>();
//        practicesModelMap.put("practices", new PracticesModel("image", "imageurl"));
//        Map<String, List<PhysicalArtifactsModel>> artifactsModelMap=new HashMap<>();
//        List<PhysicalArtifactsModel> list = new ArrayList<>();
//        for(int i=0;i<5;i++){
//            list.add(new PhysicalArtifactsModel("image", "imageurl"));
//        };
//        artifactsModelMap.put("physicalartifacts", list);
//        Map<String,Feedback> feedbackMap=new HashMap<>();
//        Map<String,SubFeedback> subFeedbackMap=new HashMap<>();
//        subFeedbackMap.put("uid",new SubFeedback(5,"message from user"));
//        feedbackMap.put("feedback", new Feedback("uid",subFeedbackMap));
//
//
//
//        RootModel model=new RootModel("patna", "city url", "patna city near ganga river",
//                skillModelMap,practicesModelMap,artifactsModelMap,feedbackMap);
//        reference.setValue("patna", model);


    }

    private void showMessage(String s){
        Toast.makeText(getActivity(), s+" is clicked", Toast.LENGTH_SHORT).show();

    }

    public void makeDatabaseSchema(){

        FirebaseDatabase database=FirebaseDatabase.getInstance();
         String [] st=new String[]{
                "andhra pradesh","arunachal pradesh","assam","bihar","chhattisgarh","goa","gujrat","haryana","himachal pradesh",
                 "jammu and kashmir","jharkhand","karnataka","kerla","madhya pradesh","maharashtra","manipur","meghalaya","mizoram",
                 "nagaland","odisha","punjab","rajsthan","sikkim","tamil nadu","telangana","tripura","uttar pradesh","uttarakhand",
                 "west bengal","andman and nicobar islands","chandigarh","dadra and nagar haveli","daman and diu","lakshadweep",
                 "delhi","pondicherry"
        };
        for(int j=0;j<st.length;j++) {
            for(int k=0;k<5;k++) {
                DatabaseReference ref=database.getReference("state/"+st[j]);
                String kk=ref.push().getKey();
                DatabaseReference reference=ref.child(kk);
                reference.child("cityname").setValue("name");
                reference.child("cityimage").setValue("imageurl");
                reference.child("description").setValue("description");

                reference.child("skills").child("image").setValue("imageurl");
                reference.child("skills").child("description").setValue("description");

                reference.child("practices").child("image").setValue("imageurl");
                reference.child("practices").child("description").setValue("description");

                for (int i = 0; i < 5; i++) {
                    String key = reference.child("physicalartifacts").push().getKey();
                    reference.child("physicalartifacts").child(key).child("image").setValue("imageurl");
                    reference.child("physicalartifacts").child(key).child("description").setValue("description");
                }
                reference.child("feedback").setValue(null);
            }

        }
        //reference.child("feedback").child()

    }


}
