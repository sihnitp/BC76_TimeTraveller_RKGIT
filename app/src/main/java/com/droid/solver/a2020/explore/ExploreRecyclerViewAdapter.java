package com.droid.solver.a2020.explore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.droid.solver.a2020.R;

import java.util.List;

public class ExploreRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<ExploreModel> list ;
    private Context context;
    private LayoutInflater inflater;
    public ExploreRecyclerViewAdapter(List<ExploreModel> list , Context context){
        this.list=list;
        this.context=context;
        inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.explore_horizontal_item,null,false);
        return new ExploreViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
}
