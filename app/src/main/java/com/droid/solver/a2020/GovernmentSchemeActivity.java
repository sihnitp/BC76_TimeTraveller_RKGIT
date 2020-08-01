package com.droid.solver.a2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import drawerItems.WebViewActivity;

public class GovernmentSchemeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government_scheme);
        recyclerView=findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        List<SchemeModel> list = new ArrayList<>();
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list.add(new SchemeModel("Scheme of Financial Assistance under Seva Bhoj Yojna (NEW)",
                "https://www.indiaculture.nic.in/scheme-financial-assistance-under-seva-bhoj-yojna-new"));

        list.add(new SchemeModel("Scheme of Financial Assistance for Promotion of Art and Culture",
                "https://www.indiaculture.nic.in/scheme-financial-assistance-promotion-art-and-culture"));

        list.add(new SchemeModel("Scheme of Financial Assistance for Creation of Cultural Infrastructure",
                "https://www.indiaculture.nic.in/scheme-financial-assistance-creation-cultural-infrastructure"));

        list.add(new SchemeModel("Scheme of Scholarship and Fellowship for Promotion of Art and Culture",
                "https://www.indiaculture.nic.in/scheme-scholarship-and-fellowship-promotion-art-and-culture"));

        list.add(new SchemeModel("Certificate of Excellence Scheme for Museum Professionals",
                "https://www.indiaculture.nic.in/certificate-excellence-scheme-museum-professionals"));

        list.add(new SchemeModel("Museum Grant Scheme","https://www.indiaculture.nic.in/museum-grant-scheme"));

        list.add(new SchemeModel("Scheme for Pension and Medical Aid to Artistes",
                "https://www.indiaculture.nic.in/scheme-pension-and-medical-aid-artistes"));

        list.add(new SchemeModel("Scheme for Promotion of Culture of Science (SPOCS)",
                "https://www.indiaculture.nic.in/scheme-promotion-culture-science-spocs"));

        list.add(new SchemeModel("Scheme for Cultural Heritage Youth Leadership Programme",
                "https://www.indiaculture.nic.in/scheme-cultural-heritage-youth-leadership-programme"));

        list.add(new SchemeModel("Scheme for Safeguarding the Intangible Cultural Heritage",
                "https://www.indiaculture.nic.in/scheme-safeguarding-intangible-cultural-heritage-and-diverse-cultural-traditions-india"));

        list.add(new SchemeModel("Scheme for Promoting International Cultural Relation ",
                "https://www.indiaculture.nic.in/scheme-promoting-international-cultural-relations"));

        list.add(new SchemeModel("Schemes/Awards:Rajbhasha",
                "https://www.indiaculture.nic.in/schemesawardsrajbhasha"));

        list.add(new SchemeModel("Indian Conservation Fellowship Program (ICFP)",
                "https://www.indiaculture.nic.in/https://indiaculture.nic.in/indian-conservation-fellowship-program-icfp"));

        SchemeAdapter adapter=new SchemeAdapter(this,list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
class SchemeModel{
    String url;
    String title;
    SchemeModel(){}
    SchemeModel(String title,String url){
        this.url=url;
        this.title=title;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
class SchemeAdapter extends RecyclerView.Adapter{

    private List<SchemeModel> list ;
    private LayoutInflater inflater;
    private Context context;

    SchemeAdapter(Context context, List<SchemeModel> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.scheme_item,parent,false);
        return new SchemeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof SchemeViewHolder){
            ((SchemeViewHolder) holder).textView.setText(list.get(position).getTitle());
            ((SchemeViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//
//                    Intent intent=new Intent(context,WebViewActivity.class);
//                    intent.putExtra("url", list.get(position).getUrl());
//                    context.startActivity(intent);
//
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getUrl()));
                    Intent chooser=Intent.createChooser(browserIntent,"Choose browser to go to page");
                    context.startActivity(chooser);


                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
}
class SchemeViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    CardView cardView;
    public SchemeViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.text);
        cardView=itemView.findViewById(R.id.card_view);
    }
}
