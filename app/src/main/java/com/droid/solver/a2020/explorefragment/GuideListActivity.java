package com.droid.solver.a2020.explorefragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.droid.solver.a2020.R;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PrivateKey;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import drawerItems.GuideRegistrationActivity;

public class GuideListActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 4;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView message;
    private ProgressBar progressBar;
    private String district ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_list);
        Intent intent=getIntent();
        district=intent.getStringExtra("city");
        checkPermissionOfCall();
        progressBar=findViewById(R.id.progress_bar);
        message=findViewById(R.id.message);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView=findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(this);
        List<GuideModel> list=new ArrayList<>();
        GuideAdapter adapter = new GuideAdapter(this,list);

        fetchGuide(list,adapter,district);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);



    }

    void checkPermissionOfCall(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }

    }

    void fetchGuide(final List<GuideModel> list, final GuideAdapter adapter, final String district){
        DatabaseReference guideRef= FirebaseDatabase.getInstance().getReference().child("guideRegistration");
        ValueEventListener listener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot daa : dataSnapshot.getChildren()){
                    GuideModel guideModel=daa.getValue(GuideModel.class);
                    if(guideModel!=null && guideModel.getDistrict()!=null && guideModel.getDistrict().toLowerCase().equals(district.toLowerCase()))
                       list.add(guideModel);
                }
                for(GuideModel mdl : list){
                    Log.i("TTT", mdl.getName()+" "+mdl.getDetails()+" "+mdl.getMobile());
                }
                adapter.updateList(list);
                progressBar.setVisibility(View.INVISIBLE);
                if(list.size()==0){
                    message.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        guideRef.addListenerForSingleValueEvent(listener) ;

    }
}
class GuideModel{
    private String name,mobile,email,qualifications,state,district,charge,details;
    GuideModel(){}
    GuideModel(String name,String mobile,String email,String qualifications,String state,String district,String charge,String details){
        this.name=name;
        this.mobile=mobile;
        this.email=email;
        this.qualifications=qualifications;
        this.state=state;
        this.district=district;
        this.charge=charge;
        this.details=details;
    }

    public String getName() {
        return name;
    }

    public String getCharge() {
        return charge;
    }

    public String getDistrict() {
        return district;
    }

    public String getDetails() {
        return details;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getQualifications() {
        return qualifications;
    }

    public String getState() {
        return state;
    }
}

class GuideAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<GuideModel> list;
    private LayoutInflater inflater;

    GuideAdapter(Context context, List<GuideModel> list){
        this.context=context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.guide_model,parent,false);
        return new GuideViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof GuideViewHolder){
            ((GuideViewHolder) holder).name.setText(list.get(position).getName());
            ((GuideViewHolder) holder).mobile.setText(list.get(position).getMobile());
            ((GuideViewHolder) holder).charge.setText("Charge : "+list.get(position).getCharge());
            ((GuideViewHolder) holder).details.setText(list.get(position).getDetails());
        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
    public void updateList(List<GuideModel> newList){
        this.list=newList;
        this.notifyDataSetChanged();
    }
}

class GuideViewHolder extends RecyclerView.ViewHolder{

    public TextView name,mobile,charge,details;
    private Context context;

    public GuideViewHolder(@NonNull View itemView, final Context context) {
        super(itemView);
        this.context=context;
        name=itemView.findViewById(R.id.textView2);
        mobile=itemView.findViewById(R.id.textView3);
        charge=itemView.findViewById(R.id.textView5);
        details=itemView.findViewById(R.id.textView4);

        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+mobile.getText().toString()));
                context.startActivity(intent);
            }
        });
    }
}
