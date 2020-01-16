package com.droid.solver.a2020.explorefragment;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.droid.solver.a2020.R;

public class ExploreFragmentViewHolder extends RecyclerView.ViewHolder {
    TextView stateTitle;
    CardView cardView;

    ConstraintLayout constraintLayout;
    public ExploreFragmentViewHolder(@NonNull View itemView) {
        super(itemView);
        stateTitle=itemView.findViewById(R.id.title);
        cardView=itemView.findViewById(R.id.card_view);
        constraintLayout=itemView.findViewById(R.id.constraint);
    }

}
