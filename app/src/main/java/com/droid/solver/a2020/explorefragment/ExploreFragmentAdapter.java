package com.droid.solver.a2020.explorefragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.droid.solver.a2020.R;

import java.util.Random;

public class ExploreFragmentAdapter extends RecyclerView .Adapter{
    private String [] stateArray;
    private Context context;
    private LayoutInflater inflater;
    int [] images=new int[]{
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image8,
            R.drawable.image9,
            R.drawable.image10,
            R.drawable.image11,
            R.drawable.image12,
            R.drawable.image13,
            R.drawable.image14,
            R.drawable.image15,
            R.drawable.image16,
            R.drawable.image17,
            R.drawable.image18,
            R.drawable.image19,
            R.drawable.image20,
            R.drawable.image21,
            R.drawable.image22,
            R.drawable.image23,
            R.drawable.image24,
            R.drawable.image25,
            R.drawable.image26,
            R.drawable.image27,
            R.drawable.image28,
            R.drawable.image29,
            R.drawable.image30,
    };

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
            int pos=position%29;
            if(position>=30){
                Random r=new Random();
                pos=r.nextInt(29);
            }
            ((ExploreFragmentViewHolder) holder).imageView.setImageResource(images[pos]);
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
