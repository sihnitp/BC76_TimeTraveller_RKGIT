package com.droid.solver.a2020.explorefragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.droid.solver.a2020.R;

public class ExploreFragmentCityViewHolder extends RecyclerView.ViewHolder {
    TextView cityName;
    CardView cardView;
    ImageView imageView;
    ConstraintLayout constraintLayout;

    public ExploreFragmentCityViewHolder(@NonNull View itemView) {
        super(itemView);
        cityName=itemView.findViewById(R.id.title);
        cardView=itemView.findViewById(R.id.card_view);
        constraintLayout=itemView.findViewById(R.id.constraint);
        imageView=itemView.findViewById(R.id.image);
    }
}
