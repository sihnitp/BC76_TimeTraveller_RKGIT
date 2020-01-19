package com.droid.solver.a2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class KidsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids);
        recyclerView=findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        List<Model> list=new ArrayList<>();
        toolbar=findViewById(R.id.toolbar2);
        toolbar.setTitle("Kids section");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list.add(new Model(R.drawable.index,"Tajmahal","v1GShoW1Ez4"));
        list.add(new Model(R.drawable.golden_temple,"Golden Temple","Qiz2TVPN9TU"));
        list.add(new Model(R.drawable.india_gate,"India Gate","gTQ8djPZNcQ"));
        list.add(new Model(R.drawable.victoria,"Victorial Memorial","9JAS6St15eg"));
        list.add(new Model(R.drawable.sanchi,"Sanchi Stupa","g38r0OfRt8Y"));
        list.add(new Model(R.drawable.everest,"Mount Everest","QqkgKHu1gt0"));
        KidsAdapter adapter=new KidsAdapter(this,list);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
class Model{
    int imageId;
    String name;
    String videoId;
    Model(){}
    Model(int imageId,String name,String videoId){
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

class KidsAdapter extends RecyclerView.Adapter{
    private LayoutInflater inflater;
    private Context context;
    private List<Model> list;
    public  KidsAdapter(Context context, List<Model> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.kids_item,parent,false);
        return new KidsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
     if(holder instanceof KidsViewHolder){
         final Model model=list.get(position);
         ((KidsViewHolder) holder).imageView.setImageResource(model.getImageId());
         ((KidsViewHolder) holder).textView.setText(model.getName());
         ((KidsViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
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

class KidsViewHolder extends RecyclerView.ViewHolder{
    ImageView imageView;
    TextView textView;
    CardView cardView;
    public KidsViewHolder(View itemView){
        super(itemView);
        imageView=itemView.findViewById(R.id.image);
        textView=itemView.findViewById(R.id.text);
        cardView=itemView.findViewById(R.id.root);
    }
}
