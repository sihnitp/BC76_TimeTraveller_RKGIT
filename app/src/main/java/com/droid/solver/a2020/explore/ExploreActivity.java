package com.droid.solver.a2020.explore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.a2020.DetailActivity;
import com.droid.solver.a2020.MainActivity;
import com.droid.solver.a2020.PhysicalArtifactsModel;
import com.droid.solver.a2020.R;
import com.droid.solver.a2020.explorefragment.GuideListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ExploreActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, View.OnClickListener {

    private CoordinatorLayout rootLayout;
    private SliderView sliderView;
    private Toolbar toolbar;
    private String state,cityName="City name";
    private ImageView cityImage,practicesImages,skillImages;
    private TextView cityTitle,cityDescription,practicesDescription,skillDescription,needGuide;
    ProgressDialog progressDialog;
    private CardView root;
    private CardView cityCard,practicesCard,skillCard;
    private ImageView imageView;
    private TextToSpeech tts;
    private String globalCityDescription="",globalCityCulture="",globalCitySkills="";
    private String globalCityArtificats="";
    private boolean isPlaying=false;
    private TextInputEditText inputEditText;
    private MaterialButton submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        state=intent.getStringExtra("state");
        cityName=intent.getStringExtra("city");
        setContentView(R.layout.activity_explore);
        tts=new TextToSpeech(this, this);
        init();

    }

    public void init() {
        sliderView = findViewById(R.id.imageSlider);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.translator_menu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        inputEditText = findViewById(R.id.text_input_edit_text);
        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);
        imageView = findViewById(R.id.text_to_speech_image);
        cityImage = findViewById(R.id.city_image);
        cityTitle = findViewById(R.id.city_title);
        cityDescription = findViewById(R.id.city_description);
        practicesImages = findViewById(R.id.practices_images);
        practicesDescription = findViewById(R.id.practices_description);
        skillImages = findViewById(R.id.skill_image);
        skillDescription = findViewById(R.id.skill_description);
        progressDialog = new ProgressDialog(this, R.style.progress_dialog);
        root = findViewById(R.id.root);
        root.setVisibility(View.GONE);
        cityCard = findViewById(R.id.card_view);
        practicesCard = findViewById(R.id.practices_card);
        skillCard = findViewById(R.id.skills_card);
        rootLayout = findViewById(R.id.root_layout);
        needGuide=findViewById(R.id.need_guide);
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.appBarLayout);
        needGuide.setOnClickListener(this);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(cityName);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        collapsingToolbarLayout.setExpandedTitleTextColor(getResources().getColorStateList(R.color.white, null));
                    }
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });


        fetchDetails();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    tts.stop();
                    tts.shutdown();
                    isPlaying = false;
                } else {
                    makeSpeak();
                    isPlaying = true;
                }
            }
        });
    }

    @Override
    protected void onPause() {

        if(tts!=null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void fetchDetails(){
        if(state!=null && cityName!=null){
            DatabaseReference cityRef= FirebaseDatabase.getInstance().getReference().child("state")
                    .child(state).child(cityName);
            showProgressDialog();
            cityRef.addListenerForSingleValueEvent(listener);

        }else{
            Toast.makeText(this,"State or city is not selected",Toast.LENGTH_SHORT ).show();
            root.setVisibility(View.VISIBLE);
            progressDialog.dismiss();
        }
    }

    private ValueEventListener listener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            globalCityArtificats=cityName+" Have some popular pyhsical artifacts. Like ";
                List<PhysicalArtifactsModel> artifactsList=new ArrayList<>();

                for(DataSnapshot model : snapshot.child("physicalartifacts").getChildren()){
                   String key=model.getKey();
                   if(key!=null) {

                       String descripton = model.child("description").getValue(String.class);
                       String image = model.child("image").getValue(String.class);

                       String latitude="0'0";
                       String longitude= "0.0";

                       String name=model.child("name").getValue(String.class);
                       PhysicalArtifactsModel mm=new PhysicalArtifactsModel(image, descripton,latitude,longitude, name);
                       artifactsList.add(mm);
                       globalCityArtificats=globalCityArtificats+name+" ,";
                   }
                }

                 final String cityimage=snapshot.child("cityimage").getValue(String.class);
                 final String cityid=snapshot.child("cityid").getValue(String.class);
                 final String citydescription=snapshot.child("description").getValue(String.class);
                 final String practicesimage=snapshot.child("practices").child("image").getValue(String.class);
                 final String practicesdescription=snapshot.child("practices").child("description").getValue(String.class);
                 final String skillimage=snapshot.child("skills").child("image").getValue(String.class);
                 final String skilldescription=snapshot.child("skills").child("description").getValue(String.class);

                 SharedPreferences preferences=getSharedPreferences(MainActivity.preferenceName, MODE_PRIVATE);
                 int lan=preferences.getInt(MainActivity.lan, 1);

                 sliderView.setSliderAdapter(new SliderAdapter(ExploreActivity.this,artifactsList));
                 Picasso.get().load(cityimage).placeholder(R.drawable.trending_item_gradient).into(cityImage);
                 Picasso.get().load(practicesimage).placeholder(R.drawable.trending_item_gradient).into(practicesImages);
                 Picasso.get().load(skillimage).placeholder(R.drawable.trending_item_gradient).into(skillImages);
                 String ss=cityName.substring(0,1).toUpperCase()+cityName.substring(1);

                 switch (lan){
                     case 0:
                         downloadModelToBengali(ss,cityTitle);
                         downloadModelToBengali(citydescription, cityDescription);
                         downloadModelToBengali(practicesdescription,practicesDescription);
                         downloadModelToBengali(skilldescription,skillDescription);
                         break;
                     case 1:
                         cityTitle.setText(ss);
                         cityDescription.setText(citydescription);
                         practicesDescription.setText(practicesdescription);
                         skillDescription.setText(skilldescription);
                         break;
                     case 2:
                         downloadModelToHindi(ss,cityTitle);
                         downloadModelToHindi(citydescription, cityDescription);
                         downloadModelToHindi(practicesdescription,practicesDescription);
                         downloadModelToHindi(skilldescription,skillDescription);
                         break;
                     case 3:
                         downloadModelToMarathi(ss,cityTitle);
                         downloadModelToMarathi(citydescription, cityDescription);
                         downloadModelToMarathi(practicesdescription,practicesDescription);
                         downloadModelToMarathi(skilldescription,skillDescription);
                         break;
                     case 4:
                         downloadModelToTamil(ss,cityTitle);
                         downloadModelToTamil(citydescription, cityDescription);
                         downloadModelToTamil(practicesdescription,practicesDescription);
                         downloadModelToTamil(skilldescription,skillDescription);
                         break;
                     case 5:
                         downloadModelToTelgu(ss,cityTitle);
                         downloadModelToTelgu(citydescription, cityDescription);
                         downloadModelToTelgu(practicesdescription,practicesDescription);
                         downloadModelToTelgu(skilldescription,skillDescription);
                         break;
                     case 6:
                         downloadModelToUrdu(ss,cityTitle);
                         downloadModelToUrdu(citydescription, cityDescription);
                         downloadModelToUrdu(practicesdescription,practicesDescription);
                         downloadModelToUrdu(skilldescription,skillDescription);
                         break;
                 }

                 root.setVisibility(View.VISIBLE);
                 progressDialog.dismiss();
                 globalCityCulture=practicesdescription;
                 globalCitySkills=skilldescription;
                 globalCityDescription=citydescription;

                 String uid= FirebaseAuth.getInstance().getUid();
                 if(uid!=null){
                     updateDatabase(cityid,uid,state,cityName,cityimage,citydescription);
                 }
                 cityDescription.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         showDetailActivity(cityimage, cityName, citydescription);
                     }
                 });
                 skillCard.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         showDetailActivity(skillimage, "Skills/Knowledge", skilldescription);
                     }
                 });
                 practicesCard.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         showDetailActivity(practicesimage, "Practices/Culture", practicesdescription);
                     }
                 });

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            progressDialog.dismiss();
            root.setVisibility(View.VISIBLE);
            Toast.makeText(ExploreActivity.this, "Error occured in fetching data from database", Toast.LENGTH_SHORT).show();
        }
    };

    private void showProgressDialog(){
        progressDialog.setTitle("Please wait ");
        progressDialog.setMessage("Loading ...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();


    }

    private void showDetailActivity(String url,String title,String description){
        Intent intent=new Intent(this, DetailActivity.class);
        intent.putExtra("image", url);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        startActivity(intent);
    }

    private void updateDatabase(String cityId,String uid,String stateName,String cityName,String cityImage,String cityDescription){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("recommended");
        reference.child(cityId).child("user").child(uid).setValue("visited");
        reference.child(cityId).child("statename").setValue(stateName);
        reference.child(cityId).child("cityname").setValue(cityName);
        reference.child(cityId).child("cityimage").setValue(cityImage);
        reference.child(cityId).child("description").setValue(cityDescription);

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
                                Log.i("TAG", "error occured ,"+e.getMessage());
                                Log.i("TAG", "error occured in translation");
                            }
                        });


    }

    public void speakOut(String text){
        String string = text;
        tts.setPitch(1f);
        int status=tts.speak(string,TextToSpeech.QUEUE_FLUSH,null);
        if(status==TextToSpeech.ERROR){
            Log.i("TAG", "Error in converting text to speech");
            Log.i("TAG", "error in converting ");
            showSnackBar("Error occured in generating sound");
        }
    }
    @Override
    public void onInit(int i) {

        if(i==TextToSpeech.SUCCESS){
            int result=tts.setLanguage(Locale.US);
            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.i("TAG", "Languge not supported");
                showSnackBar("Language not supported");
            }else{
                Log.i("TAG","Language supported");
            }
        }else{
            Log.i("TAG", "Initialization failed");
            showSnackBar("Failed to understand language");
        }
    }

    private void showSnackBar(String message){
        Snackbar.make(rootLayout,message,Snackbar.LENGTH_SHORT).show();
    }

    private void makeSpeak(){

        String string="Welcome to the "+cityName+""+"Here is the brief info about the city as "+globalCityDescription+". " +
                "Now ,have a look on the physical artefacts around "+cityName+" as "+globalCityArtificats+"."
                +"Let me aware you to the culture of "+cityName+"  as "+
                ","+globalCityCulture+" . "+cityName+" talking about the skills "+globalCitySkills;
        speakOut(string);

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.submit_button) {
            showProgressDialog();

            if (inputEditText.getText()==null||inputEditText.getText().length() == 0) {
                Snackbar.make(rootLayout, "Please enter your suggestion", Snackbar.LENGTH_SHORT).show();
                return;
            }

            String suggestion = inputEditText.getText().toString();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("suggestion");
            String key=reference.push().getKey();

                reference.child(key).setValue(suggestion).addOnCompleteListener(new OnCompleteListener<Void>(){
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Snackbar.make(rootLayout, "Thanks for your suggestion", Snackbar.LENGTH_SHORT).show();
                        inputEditText.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Snackbar.make(rootLayout, "Error occured,try again after some time ", Snackbar.LENGTH_SHORT).show();
                    }
                });

        }
        else if(view.getId()==R.id.need_guide){
            Intent intent=new Intent(ExploreActivity.this,GuideListActivity.class);
            intent.putExtra("city", cityName);
            startActivity(intent);
        }


    }
}
