package com.droid.solver.a2020;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import trending.TrendingModel;
import trending.TrendingRecyclerViewAdapter;


public class TrendingFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public static TrendingFragment getInstance(){
        return  new TrendingFragment();
    }

    public TrendingFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_trending, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        progressBar=view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        query();
    }

    public void query(){
        DatabaseReference root= FirebaseDatabase.getInstance().getReference().child("recommended");
        root.addValueEventListener(listener);


    }

    ValueEventListener listener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            List<TrendingModel> list=new ArrayList<>();
            for(DataSnapshot  snapshot : dataSnapshot.getChildren()){
                String cityImage=snapshot.child("cityimage").getValue(String.class);
                String cityName=snapshot.child("cityname").getValue(String.class);
                String description=snapshot.child("description").getValue(String.class);
                String stateName=snapshot.child("statename").getValue(String.class);
                int userCount= (int) snapshot.child("user").getChildrenCount();
                TrendingModel model=new TrendingModel(cityName, cityImage, stateName
                , description, userCount);
                list.add(model);
            }
            sortList(list);
            TrendingRecyclerViewAdapter adapter=new TrendingRecyclerViewAdapter(getActivity(),list);
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.i("TAG", "query cancelled");
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Error occured,",Toast.LENGTH_SHORT).show();
        }
    };

    public void sortList(List<TrendingModel> list){

        Collections.sort(list,new Comparator<TrendingModel>(){

            @Override
            public int compare(TrendingModel t1, TrendingModel t2) {
                return t1.getUserCount()-t2.getUserCount();
            }
        });


    }

}
