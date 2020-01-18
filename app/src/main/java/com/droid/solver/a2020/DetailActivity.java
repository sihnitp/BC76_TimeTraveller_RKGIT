package com.droid.solver.a2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;
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
        SharedPreferences preferences=getSharedPreferences(MainActivity.preferenceName, MODE_PRIVATE);
        int isHindi=preferences.getInt(MainActivity.lan, 0);

        switch (isHindi){
            case 0:
                downloadModelToBengali(titleText,title);
                downloadModelToBengali(descriptionText, description);
                break;
            case 1:
                title.setText(titleText);
                description.setText(descriptionText);
                break;
            case 2:
                downloadModelToHindi(titleText,title);
                downloadModelToHindi(descriptionText,description);
                break;
            case 3:
                downloadModelToMarathi(titleText,title);
                downloadModelToMarathi(descriptionText, description);
                break;
            case 4:
                downloadModelToTamil(titleText,title);
                downloadModelToTamil(descriptionText, description);
                break;
            case 5:
                downloadModelToTelgu(titleText,title);
                downloadModelToTelgu(descriptionText,description);
                break;
            case 6:
                downloadModelToUrdu(titleText,title);
                downloadModelToUrdu(descriptionText,description);
                break;
        }

        Picasso.get().load(imageurl).placeholder(R.drawable.trending_item_gradient).into(imageView);

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
}
