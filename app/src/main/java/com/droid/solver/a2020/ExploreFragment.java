package com.droid.solver.a2020;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.Toast;

import com.droid.solver.a2020.explore.ExploreActivity;
import com.droid.solver.a2020.explorefragment.ExploreFragmentAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ExploreFragment extends Fragment implements  View.OnClickListener{

    private static final int LOCATION_PERMISSION_CODE = 351;
    private RecyclerView recyclerView;
    private MaterialButton button;
    private FusedLocationProviderClient locationProviderClient;
    private String[] state;
    private TextInputEditText inputText;
    public static ExploreFragment getInstance(){
        return new ExploreFragment();
    }

    public ExploreFragment() {

        state=new String[]{
                "andhra pradesh","arunachal pradesh","assam","bihar","chhattisgarh","goa","gujarat","haryana","himachal pradesh",
                "jammu and kashmir","jharkhand","karnataka","kerela","madhya pradesh","maharashtra","manipur","meghalaya","mizoram",
                "nagaland","odisha","punjab","rajasthan","sikkim","tamil nadu","telangana","tripura","uttar pradesh","uttarakhand",
                "west bengal","andaman and nicobar islands","chandigarh","dadar and nagar haveli","daman and diu","lakshadweep",
                "delhi","puducherry"
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_explore, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        String [] temp=capitalizeFirstLetter(CONSTANT.state);
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
        recyclerView.setLayoutAnimation(animation);
        ExploreFragmentAdapter adapter=new ExploreFragmentAdapter(temp,getActivity());
        recyclerView.setAdapter(adapter);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        button=view.findViewById(R.id.materialButton);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        button.setOnClickListener(this);
        inputText=view.findViewById(R.id.searchInput);
        return view;
    }


    public  static String[] capitalizeFirstLetter(String [] city){
        String [] temp=new String[city.length];

        for(int i=0;i<city.length;i++){
            String s=city[i];
            String ss=s.substring(0,1).toUpperCase()+s.substring(1);
            temp[i]=ss;
        }
        return temp;
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        if(recyclerView.getAdapter()!=null) {
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }
    }

    private void requestLocationRequest(){

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                //request permission
                ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);

        } else {
            //permission granted
            trackLocation();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==LOCATION_PERMISSION_CODE && grantResults.length>0 &&
                grantResults[0]==PackageManager.PERMISSION_GRANTED){
              trackLocation();

        }else{
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void trackLocation(){

        locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                Log.i("TAG", "inside track location");
                if(location!=null){
                    double latitude=location.getLatitude();
                    double longitude=location.getLongitude();
                    Log.i("TAG", "longitude : "+longitude);
                    Log.i("TAG","Latitude : "+latitude );
                    Log.d("TAG", ""+location.getAccuracy());
                    String [] tt=findNearbyArtifacts(longitude, latitude);//state and city
                    Intent intent=new Intent(getActivity(), ExploreActivity.class);
                    intent.putExtra("state", tt[0]);
                    intent.putExtra("city", tt[1]);
                    startActivity(intent);
                }else{
                    Log.i("TAG", "last location is null");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("TAG", "Retrieving last location failed");
            }
        });

    }
    public void searchItem(){
        String inputString = inputText.getText().toString();
        inputString = inputString.toLowerCase();
        String[] state_city = findSearchedCity(inputString);
        //Log.i("hi bro", "searchItem:"+state_city[1]+inputString);
        int state_index;
        if (state_city[0]=="none"){
            for (int i=0;i<state.length;i++){
                if (inputString==state[i]){
                    state_index=i;
                }
            }
        }
        else{
            Intent intent=new Intent(getActivity(), ExploreActivity.class);
            intent.putExtra("state", state_city[0]);
            intent.putExtra("city", state_city[1]);
            startActivity(intent);
        }
    }
    public void onClick(View view){
        if(view.getId()==R.id.materialButton){
            //requestLocationRequest();
            searchItem();
        }
    }

    private String [] findNearbyArtifacts(double val1,double val2){



        double  minDist=Integer.MAX_VALUE;
        int minIndex=147;
        for(int i=0;i<146;i++){
            double lat=Double.parseDouble(CONSTANT.latitude[i]);
            double lon=Double.parseDouble(CONSTANT.longitude[i]);
            double val=Math.sqrt(Math.pow((lat-val2),2)+Math.pow((lon-val1),2));
            if(val<minDist){
                minDist=val;
                minIndex=i;
            }
        }
        return new String[] {CONSTANT.stateArr[minIndex],CONSTANT.cityArr[minIndex]};
    }

    private String [] findSearchedCity(String city_name){

        String []cityArr=new String[]{"amaravati", "visakhapatnam", "vijayawada", "tirupati", "guntur", "port blair", "neil island",
                "ross island", "diglipur", "mayabunder", "tawang", "itanagar", "Zero", "bomdila", "pasighat", "guwahati", "silchar",
                "dibrugarh", "jorhat", "nagaon", "patna", "gaya", "bhagalpur", "nalanda", "darbhanga", "sitamarhi", "madhubani",
                "chhapra", "buxar", "ara", "muzaffarpur", "saharsa", "begusarai", "ghaziabad", "chandigarh", "raipur", "bilaspur",
                "bastar", "silavasa", "daman", "diu", "delhi", "panaji", "ponda", "ahmedabad", "surat", "vadodra", "rajkot", "gandhinagar",
                "porbandar", "karnal", "hisar", "rohtak", "sonipat", "panipat", "shimla", "jammu", "kashmir", "ranchi", "jamshedpur", "deoghar",
                "bokaro", "bengaluru", "hampi", "mangalore", "ooty", "thiruvananthapuram", "kochi", "kavaratti", "bhopal", "indore", "gwalior",
                "ujjain", "mumbai", "auranagabad", "nagpur", "nashik", "khandala", "imphal", "shillong", "aizwal", "kohima", "dimapur", "mirzapur",
                "bhubaneswar", "cuttack", "rourkela", "pudducherry", "mahe", "karaikal", "yanam", "amritsar", "patiala", "jalandhar", "ludhiana",
                "jaipur", "jodhpur", "udaipur", "jaisalmer", "ajmer", "bikaner", "kota", "pushkar", "alwar", "chittorgarh", "gangtok", "chennai",
                "madurai", "coimbatore", "tiruchirappali", "vellore", "hyderabad", "warangal", "lucknow", "prayagraj", "kanpur", "agra", "varanasi",
                "aligarh", "bareilly", "jhansi", "mathura", "ayodhya", "jaunpur", "firozabad", "fatehpur sikri", "gorakhpur", "vrindavan", "dehradun",
                "haridwar", "kashipur", "roorkee", "kolkata", "asansol", "siliguri", "durgapur", "haldia", "darjeeling", "malda", "khargpur",
                "jalpaiguri", "birbhum", "bishnupur", "howrah", "bardhaman", "chandannagar"};

        String [] stateArr=new String []{"andhra pradesh", "andhra pradesh", "andhra pradesh", "andhra pradesh", "andhra pradesh",
                "andaman and nicobar islands", "andaman and nicobar islands", "andaman and nicobar islands", "andaman and nicobar islands",
                "andaman and nicobar islands", "arunachal pradesh", "arunachal pradesh", "arunachal pradesh", "arunachal pradesh",
                "arunachal pradesh", "assam", "assam", "assam", "assam", "assam", "bihar", "bihar", "bihar", "bihar", "bihar", "bihar",
                "bihar", "bihar", "bihar", "bihar", "bihar", "bihar", "bihar", "uttar pradesh", "chandigarh", "chhattisgarh", "chhattisgarh",
                "chhattisgarh", "dadra and nagar haveli", "daman and diu", "daman and diu", "delhi", "goa", "goa", "gujrat", "gujrat",
                "gujrat", "gujrat", "gujrat", "gujrat", "haryana", "haryana", "haryana", "haryana", "haryana", "himachal pradesh",
                "jammu and kashmir", "jammu and kashmir", "jharkhand", "jharkhand", "jharkhand", "jharkhand", "karnataka", "karnataka",
                "karnataka", "karnataka", "kerla", "kerla", "lakshadweep", "madhya pradesh", "madhya pradesh", "madhya pradesh",
                "madhya pradesh", "maharashtra", "maharashtra", "maharashtra", "maharashtra", "maharashtra", "manipur", "meghalaya",
                "mizoram", "nagaland", "nagaland", "uttar pradesh", "odisha", "odisha", "odisha", "pondicherry", "pondicherry", "pondicherry",
                "pondicherry", "punjab", "punjab", "punjab", "punjab", "rajsthan", "rajsthan", "rajsthan", "rajsthan", "rajsthan", "rajsthan",
                "rajsthan", "rajsthan", "rajasthan", "rajasthan", "sikkim", "tamil nadu", "tamil nadu", "tamil nadu", "tamil nadu", "tamil nadu",
                "telangana", "telangana", "uttar pradesh", "uttar pradesh", "uttar pradesh", "uttar pradesh", "uttar pradesh", "uttar pradesh",
                "uttar pradesh", "uttar pradesh", "uttar pradesh", "uttar pradesh", "uttar pradesh", "uttar pradesh", "uttar pradesh",
                "uttar pradesh", "uttar pradesh", "uttarakhand", "uttarakhand", "uttarakhand", "uttarakhand", "west bengal", "west bengal",
                "west bengal", "west bengal", "west bengal", "west bengal", "west bengal", "west bengal", "west bengal", "west bengal",
                "west bengal", "west bengal", "west bengal", "west bengal"};



        double  minDist=Integer.MAX_VALUE;
        int minIndex=147;
        for(int i=0;i<146;i++){
            //Log.i("compare", "findSearchedCity: "+cityArr[i]+city_name);
            if(cityArr[i].equals(city_name)){
                minIndex=i;
            }
        }
        if(minIndex<146)
            return new String[] {stateArr[minIndex],cityArr[minIndex]};
        else
            return new String[] {"none","none"};
    }



}
