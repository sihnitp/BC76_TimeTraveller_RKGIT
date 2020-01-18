package com.droid.solver.a2020.explore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.droid.solver.a2020.DetailActivity;
import com.droid.solver.a2020.MainActivity;
import com.droid.solver.a2020.PhysicalArtifactsModel;
import com.droid.solver.a2020.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

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
        String ss=title.substring(0,1).toUpperCase()+title.substring(1);
        SharedPreferences preferences=context.getSharedPreferences(MainActivity.preferenceName, MODE_PRIVATE);
        int isHindi=preferences.getInt(MainActivity.lan, 1);
        switch (isHindi){
            case 0:
                downloadModelToBengali(title,viewHolder.title);
                downloadModelToBengali(description, viewHolder.description);
                break;
            case 1:
                viewHolder.title.setText(title);
                viewHolder.description.setText(description);
                break;
            case 2:
                downloadModelToHindi(title,viewHolder.title);
                downloadModelToHindi(description, viewHolder.description);
                break;
            case 3:
                downloadModelToMarathi(title,viewHolder.title);
                downloadModelToMarathi(description, viewHolder.description);
                break;
            case 4:
                downloadModelToTamil(title,viewHolder.title);
                downloadModelToTamil(description, viewHolder.description);
                break;
            case 5:
                downloadModelToTelgu(title,viewHolder.title);
                downloadModelToTelgu(description, viewHolder.description);
                break;
            case 6:
                downloadModelToUrdu(title,viewHolder.title);
                downloadModelToUrdu(description, viewHolder.description);
                break;
        }

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

    public void translate(final String text, FirebaseTranslator  englishHindiTranslator, final View view){

        Log.i("TAG", "inside translate");
        englishHindiTranslator.translate(text)
                .addOnSuccessListener(
                        new OnSuccessListener<String>() {
                            @Override
                            public void onSuccess(@NonNull String translatedText) {
                                TextView textView= (TextView) view;
                                textView.setText(translatedText);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("TAG", "error occured in translation");
                            }
                        });


    }

    private void downloadModelToBengali(final String text,final View view){

        FirebaseTranslatorOptions options =
                new FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(FirebaseTranslateLanguage.EN)
                        .setTargetLanguage(FirebaseTranslateLanguage.BN)
                        .build();

        final FirebaseTranslator englishHindiTranslator= FirebaseNaturalLanguage.getInstance().getTranslator(options);

        Log.i("TAG", "inside download model");

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();

        englishHindiTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                translate(text, englishHindiTranslator,view);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("TAG","model could't downloaded ,"+e.getMessage());
                            }
                        });

    }

    private void downloadModelToHindi(final String text,final View view){

        FirebaseTranslatorOptions options =
                new FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(FirebaseTranslateLanguage.EN)
                        .setTargetLanguage(FirebaseTranslateLanguage.HI)
                        .build();

        final FirebaseTranslator englishHindiTranslator= FirebaseNaturalLanguage.getInstance().getTranslator(options);

        Log.i("TAG", "inside download model");

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();

        englishHindiTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                translate(text, englishHindiTranslator,view);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("TAG","model could't downloaded ,"+e.getMessage());
                            }
                        });

    }

    private void downloadModelToMarathi(final String text,final View view){

        FirebaseTranslatorOptions options =
                new FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(FirebaseTranslateLanguage.EN)
                        .setTargetLanguage(FirebaseTranslateLanguage.MR)
                        .build();

        final FirebaseTranslator englishHindiTranslator= FirebaseNaturalLanguage.getInstance().getTranslator(options);

        Log.i("TAG", "inside download model");

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();

        englishHindiTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                translate(text, englishHindiTranslator,view);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("TAG","model could't downloaded ,"+e.getMessage());
                            }
                        });

    }

    private void downloadModelToTamil(final String text,final View view){

        FirebaseTranslatorOptions options =
                new FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(FirebaseTranslateLanguage.EN)
                        .setTargetLanguage(FirebaseTranslateLanguage.TA)
                        .build();

        final FirebaseTranslator englishHindiTranslator= FirebaseNaturalLanguage.getInstance().getTranslator(options);

        Log.i("TAG", "inside download model");

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();

        englishHindiTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                translate(text, englishHindiTranslator,view);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("TAG","model could't downloaded ,"+e.getMessage());
                            }
                        });

    }

    private void downloadModelToTelgu(final String text,final View view){

        FirebaseTranslatorOptions options =
                new FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(FirebaseTranslateLanguage.EN)
                        .setTargetLanguage(FirebaseTranslateLanguage.TE)
                        .build();

        final FirebaseTranslator englishHindiTranslator= FirebaseNaturalLanguage.getInstance().getTranslator(options);

        Log.i("TAG", "inside download model");

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();

        englishHindiTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                translate(text, englishHindiTranslator,view);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("TAG","model could't downloaded ,"+e.getMessage());
                            }
                        });

    }

    private void downloadModelToUrdu(final String text,final View view){

        FirebaseTranslatorOptions options =
                new FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(FirebaseTranslateLanguage.EN)
                        .setTargetLanguage(FirebaseTranslateLanguage.UR)
                        .build();

        final FirebaseTranslator englishHindiTranslator= FirebaseNaturalLanguage.getInstance().getTranslator(options);

        Log.i("TAG", "inside download model");

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();

        englishHindiTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                translate(text, englishHindiTranslator,view);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("TAG","model could't downloaded ,"+e.getMessage());
                            }
                        });

    }

}