package com.droid.solver.a2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView title,description;
    private ImageView imageView;
    private String imageurl;
    private String titleText,descriptionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        imageurl=intent.getStringExtra("image");
        titleText=intent.getStringExtra("title");
        descriptionText=intent.getStringExtra("description");

        setContentView(R.layout.activity_detail);
        title=findViewById(R.id.title);
        description=findViewById(R.id.description);
        imageView=findViewById(R.id.city_image);

        if(titleText!=null&& titleText.length()==0){
            title.setVisibility(View.INVISIBLE);
        }else{
            title.setText(titleText);
        }
        if(descriptionText!=null&& descriptionText.length()==0){
            description.setVisibility(View.INVISIBLE);
        }else{
            description.setText(descriptionText);
        }
        Picasso.get().load(imageurl).placeholder(R.drawable.trending_item_gradient).into(imageView);

    }
}
