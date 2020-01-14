package com.droid.solver.a2020.explore;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.droid.solver.a2020.DetailActivity;
import com.droid.solver.a2020.PhysicalArtifactsModel;
import com.droid.solver.a2020.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder> {

    private Context context;
    private List<PhysicalArtifactsModel> list;
    private LayoutInflater inflater;
    public SliderAdapter(Context context, List<PhysicalArtifactsModel> list) {
        this.context = context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = inflater.inflate(R.layout.explore_horizontal_item, null,false);
        return new SliderViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {

        final String url=list.get(position).getImage();
        final String title=list.get(position).getName();
        final String description=list.get(position).getDescription();

        Picasso.get().load(url).into(viewHolder.imageView);
        viewHolder.title.setText(title);
        viewHolder.description.setText(description);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("image", url);
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    class SliderViewHolder extends SliderViewAdapter.ViewHolder {

        ImageView imageView;
        TextView title;
        TextView description;
        View view;
        CardView cardView;

        public SliderViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            this.view=itemView;
            cardView=itemView.findViewById(R.id.card_view);

        }
    }
}