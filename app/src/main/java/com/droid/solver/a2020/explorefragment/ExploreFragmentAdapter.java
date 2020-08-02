package com.droid.solver.a2020.explorefragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.droid.solver.a2020.CONSTANT;
import com.droid.solver.a2020.R;
import com.squareup.picasso.Picasso;


public class ExploreFragmentAdapter extends RecyclerView .Adapter{
    private String [] stateArray;
    private Context context;
    private LayoutInflater inflater;


    public ExploreFragmentAdapter(String [] stateArray,Context context){
        this.context=context;
        this.stateArray=stateArray;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.explore_recycler_item,parent,false);
        return new ExploreFragmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ExploreFragmentViewHolder){

            ((ExploreFragmentViewHolder) holder).stateTitle.setText(stateArray[position]);
            Picasso.get().load(CONSTANT.image[position%30]).into(((ExploreFragmentViewHolder) holder).imageView);

            ((ExploreFragmentViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,ExplorerCityActivity.class);
                    intent.putExtra("cityIndex", position);
                    context.startActivity(intent);

                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return stateArray==null?0:stateArray.length;
    }
}
