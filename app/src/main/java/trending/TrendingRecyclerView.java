package trending;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.droid.solver.a2020.R;
import com.squareup.picasso.Picasso;
import java.util.List;


public class TrendingRecyclerView extends RecyclerView.Adapter {

    List<TrendingModel> list ;
    private Context context;
    private LayoutInflater inflater;

    public TrendingRecyclerView(List<TrendingModel> list,Context context){
        this.list=list;
        this.context=context;
        inflater=LayoutInflater.from(context);

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view=inflater.inflate(R.layout.trending_recyclerview_item,null,false);
         return new TrendingViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder  instanceof TrendingViewHolder){
            //bind here to list
            TrendingModel model = list.get(position);
            String cityImageUrl=model.getCityImage();
            String cityName=model.getCityName();
            String description=model.getDescription();
            String state=model.getState();
            int rating=model.getRating();
            int ratingCount=model.getRatingCount();

            Picasso.get().load(cityImageUrl).into(((TrendingViewHolder) holder).imageOfCity);
            ((TrendingViewHolder) holder).title.setText(cityName);
            ((TrendingViewHolder) holder).subtitle.setText(description);

        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
}
