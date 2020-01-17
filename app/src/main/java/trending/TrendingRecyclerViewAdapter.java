package trending;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.droid.solver.a2020.R;
import com.droid.solver.a2020.explore.ExploreActivity;
import com.squareup.picasso.Picasso;
import java.util.List;


public class TrendingRecyclerViewAdapter extends RecyclerView.Adapter {

    private Context context;
    private LayoutInflater inflater;
    private List<TrendingModel> list;
    public TrendingRecyclerViewAdapter(Context context,List<TrendingModel> list){
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.list= list;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view=inflater.inflate(R.layout.trending_recyclerview_item,parent,false);
         return new TrendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder  instanceof TrendingViewHolder){
            TrendingModel model=list.get(position);

            final String cityName=model.getCityName();
            final String stateName=model.getStateName();
            String ss=null;
            if(cityName!=null) {
                  ss = cityName.substring(0, 1).toUpperCase() + cityName.substring(1);
            }
            String description=model.getDescription();
            final String cityImage=model.getCityImage();

            ((TrendingViewHolder) holder).title.setText(ss);
            ((TrendingViewHolder) holder).subtitle.setText(description);
            Picasso.get().load(cityImage).placeholder(R.drawable.trending_item_gradient)
                    .into(((TrendingViewHolder) holder).imageOfCity);

            ((TrendingViewHolder) holder).rootCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ExploreActivity.class);
                    intent.putExtra("city", cityName);
                    intent.putExtra("state", stateName);
                    context.startActivity(intent);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
}
