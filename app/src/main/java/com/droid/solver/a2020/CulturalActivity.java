package com.droid.solver.a2020;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CulturalActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultural);
        recyclerView=findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        List<CulturalModel> list=new ArrayList<>();
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Cultural video");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list.add(new CulturalModel(R.drawable.video,"Kathakali","Tl3UKV1z9lM"));
        list.add(new CulturalModel(R.drawable.video,"Kathak","4wXJWaqFbQc"));
        list.add(new CulturalModel(R.drawable.video,"Bharatnatyam","YuxLEc2JqnE"));
        list.add(new CulturalModel(R.drawable.video,"Kuchi pudi","ko5sKNuSLbs"));
        list.add(new CulturalModel(R.drawable.video,"Oddisi","1wX5yHh6DHc"));
        list.add(new CulturalModel(R.drawable.video,"Famous painting in India","lmbuaqRQqjg"));
        list.add(new CulturalModel(R.drawable.video,"Bangle making industry of firozabad","F6dYgaz-YUk"));
        list.add(new CulturalModel(R.drawable.video,"Punjabi folk dance","xT-Hwodb_ho"));
        list.add(new CulturalModel(R.drawable.video,"Garba","dPHJWCJMcLo"));
        list.add(new CulturalModel(R.drawable.video,"Traditional song","-wra4p4zARw"));
        list.add(new CulturalModel(R.drawable.video,"Traditional flute song","O2K0ptoYpuc"));
        list.add(new CulturalModel(R.drawable.video,"Sattriya","RhjWoZsM8pE"));
        CulturalAdapter adapter=new CulturalAdapter(this,list);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
class CulturalModel{
    int imageId;
    String name;
    String videoId;

    CulturalModel(){}

    CulturalModel(int imageId,String name,String videoId){
        this.imageId=imageId;
        this.name=name;
        this.videoId=videoId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getVideoId() {
        return videoId;
    }
}

class CulturalAdapter extends RecyclerView.Adapter{

    private LayoutInflater inflater;
    private Context context;
    private List<CulturalModel> list;

    public  CulturalAdapter(Context context, List<CulturalModel> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.cultural_item,parent,false);
        return new CulturalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CulturalViewHolder){

            final CulturalModel model=list.get(position);
            ((CulturalViewHolder) holder).imageView.setImageResource(model.getImageId());
            ((CulturalViewHolder) holder).textView.setText(model.getName());
            ((CulturalViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent=new Intent(context, VideoPlayerActivity.class);
                    intent.putExtra("videoid", model.getVideoId());
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

class CulturalViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView textView;
    CardView cardView;
    public CulturalViewHolder(View itemView){
        super(itemView);
        imageView=itemView.findViewById(R.id.image);
        textView=itemView.findViewById(R.id.text);
        cardView=itemView.findViewById(R.id.root);
    }
}

