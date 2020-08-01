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
import com.droid.solver.a2020.explore.ExploreActivity;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class ExploreFragmentCityAdapter extends RecyclerView.Adapter {

    private String [] cityArray;
    private Context context;
    private LayoutInflater inflater;
    private String stateName;
    private Random random;
    int pos=0;

    public ExploreFragmentCityAdapter(String [] cityArray, Context context,String stateName){
        this.cityArray=cityArray;
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.stateName=stateName;
        random=new Random();
        pos=random.nextInt(30);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.explorer_recycler_city_item,parent,false);
        return new ExploreFragmentCityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ExploreFragmentCityViewHolder){
            ((ExploreFragmentCityViewHolder) holder).cityName.setText(cityArray[position]);

            Picasso.get().load(CONSTANT.image[(pos+2)%30]).into(((ExploreFragmentCityViewHolder) holder).imageView);
            pos+=2;

            ((ExploreFragmentCityViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(context, ExploreActivity.class);
                    intent.putExtra("state", stateName);
                    intent.putExtra("city", cityArray[position].toLowerCase());
                    context.startActivity(intent);

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return cityArray==null?0:cityArray.length;
    }
}
