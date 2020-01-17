package trending;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.droid.solver.a2020.R;


public class TrendingViewHolder extends RecyclerView.ViewHolder {

    public CardView rootCard;
    public ImageView imageOfCity;
    public TextView title,subtitle;


    public TrendingViewHolder(@NonNull View itemView) {
        super(itemView);
        rootCard=itemView.findViewById(R.id.card_view);
        imageOfCity=itemView.findViewById(R.id.image);
        title=itemView.findViewById(R.id.title);
        subtitle=itemView.findViewById(R.id.subtitle);

    }


}
