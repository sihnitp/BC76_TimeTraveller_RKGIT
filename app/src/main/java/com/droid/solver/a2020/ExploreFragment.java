package com.droid.solver.a2020;

import android.Manifest;
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

public class ExploreFragment extends Fragment implements  View.OnClickListener{

    private static final int LOCATION_PERMISSION_CODE = 351;
    private String [] state;
    private RecyclerView recyclerView;
    private MaterialButton button;
    private FusedLocationProviderClient locationProviderClient;
    private EditText inputText;

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
        String [] temp=capitalizeFirstLetter(state);
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
        for (int i=0;i<)
    }

    public void onClick(View view){
        if(view.getId()==R.id.materialButton){
            requestLocationRequest();
        }
    }

    private String [] findNearbyArtifacts(double val1,double val2){

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

        String [] latitude=new String[]{"16.485800", "17.686815", "16.505329", "13.628756", "16.306652", "11.621610", "11.621610",
                "11.621610", "13.241830", "12.890930", "27.578068", "27.100260", "27.570299", "27.263600", "28.066700", "26.144518",
                "24.833271", "27.472834", "26.751921", "26.346371", "25.594095", "24.791395", "25.347799", "25.128290", "26.111868",
                "26.594900", "26.348900", "25.786800", "25.561001", "25.5575", "26.121473", "25.883495", "25.416676", "28.667856",
                "30.74721123", "21.250000", "22.078642", "19.066667", "20.27", "20.397373", "20.71", "28.644800", "15.496777", "15.4",
                "22.999880", "21.170240", "22.310696", "22.259392", "23.237560", "21.640575", "29.685629", "29.151861", "28.895515",
                "28.655149", "29.398928", "31.104605", "32.732998", "34.083656", "23.344101", "22.805618", "24.482775", "23.669296",
                "12.972442", "15.334444", "12.83982", "11.410000", "8.524139", "9.939093", "10.57", "23.829321", "22.719568",
                "26.218287", "23.179300", "19.076090", "19.901054", "21.146633", "19.997454", "18.758", "24.813967", "25.5667",
                "23.727106", "25.6701", "25.906267", "24.146", "20.296059", "20.450474", "22.2494", "11.931", "11.7", "10.925440",
                "16.7333", "31.633980", "30.340000", "31.326015", "30.901", "26.922070", "26.263863", "24.571270", "26.911661",
                "26.449896", "28.027138", "25.155169", "26.4905", "27.560932", "24.879999", "27.338936", "13.090994", "9.939093",
                "11.017363", "10.7905", "12.934968", "17.437462", "18.000055", "26.850000", "25.4358", "26.449923", "27.176670", "25.321684",
                "27.900383", "28.375694", "25.436298", "27.492413", "26.7964", "25.748695", "27.159101", "27.0937", "26.765844",
                "27.5806", "30.316496", "29.945690", "29.210421", "29.854263", "22.542061", "23.673944", "26.732311", "23.533440",
                "22.066673", "27.036007", "25.010841", "22.3304", "26.540457", "23.8402", "24.0261", "22.59577", "23.23333", "22.87"};

        String [] longitude=new String[]{"80.393700", "83.218483", "80.661209", "79.419182", "80.436539", "92.722237", "92.722237",
                "92.722237", "92.963348", "92.895508", "91.875740", "93.628212", "93.829903", "92.422900", "95.327499", "91.736237",
                "92.778908", "94.911964", "94.220108", "92.684044", "85.137566", "85.000237", "86.982430", "85.450233", "85.896004",
                "85.504799", "86.077103", "84.725502", "83.980698", "84.67", "85.368752", "86.600624", "86.129379", "77.449791",
                "76.768066", "81.629997", "82.152328", "82.033056", "73.02", "72.832801", "70.98", "77.216721", "73.827827",
                "74.02", "72.660614", "72.831062", "73.192635", "70.777184", "72.647781", "69.605965", "76.990547", "75.721123",
                "76.606613", "77.091492", "76.977081", "77.173424", "74.864271", "74.797371", "85.309563", "86.203110", "86.695175",
                "86.151115", "77.580643", "76.462222", "74.78994", "76.700000", "76.936638", "76.270523", "72.64", "77.412613",
                "75.857727", "78.182831", "75.784912", "72.877426", "75.352478", "79.088860", "73.789803", "73.372", "93.950279",
                "91.8833", "92.717636", "94.1077", "93.727592", "82.569", "85.824539", "85.905533", "84.883", "79.7852", "75.54",
                "79.838005", "82.2167", "74.872261", "76.379997", "75.576180", "75.8573", "75.778885", "73.008957", "73.691544",
                "70.922928", "74.639915", "73.302155", "75.827248", "74.5551", "76.625015", "74.629997", "88.606506", "80.224998",
                "78.121719", "76.958885", "78.7047", "79.146881", "78.448288", "79.588165", "80.949997", "81.8463", "80.331871",
                "78.008072", "82.987289", "78.072281", "79.435959", "78.567352", "77.673676", "82.1986", "82.698441", "78.395760",
                "77.66", "83.364944", "77.7006", "78.032188", "78.164246", "78.961830", "77.888000", "88.318954", "86.952393",
                "88.410286", "87.321930", "88.069809", "88.262672", "88.141098", "87.3181", "88.719391", "87.6186", "87.5322",
                "88.263641", "87.86667", "88.38"};

        double  minDist=Integer.MAX_VALUE;
        int minIndex=147;
        for(int i=0;i<146;i++){
            double lat=Double.parseDouble(latitude[i]);
            double lon=Double.parseDouble(longitude[i]);
            double val=Math.sqrt(Math.pow((lat-val2),2)+Math.pow((lon-val1),2));
            if(val<minDist){
                minDist=val;
                minIndex=i;
            }

        }

        return new String[] {stateArr[minIndex],cityArr[minIndex]};
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

        String [] latitude=new String[]{"16.485800", "17.686815", "16.505329", "13.628756", "16.306652", "11.621610", "11.621610",
                "11.621610", "13.241830", "12.890930", "27.578068", "27.100260", "27.570299", "27.263600", "28.066700", "26.144518",
                "24.833271", "27.472834", "26.751921", "26.346371", "25.594095", "24.791395", "25.347799", "25.128290", "26.111868",
                "26.594900", "26.348900", "25.786800", "25.561001", "25.5575", "26.121473", "25.883495", "25.416676", "28.667856",
                "30.74721123", "21.250000", "22.078642", "19.066667", "20.27", "20.397373", "20.71", "28.644800", "15.496777", "15.4",
                "22.999880", "21.170240", "22.310696", "22.259392", "23.237560", "21.640575", "29.685629", "29.151861", "28.895515",
                "28.655149", "29.398928", "31.104605", "32.732998", "34.083656", "23.344101", "22.805618", "24.482775", "23.669296",
                "12.972442", "15.334444", "12.83982", "11.410000", "8.524139", "9.939093", "10.57", "23.829321", "22.719568",
                "26.218287", "23.179300", "19.076090", "19.901054", "21.146633", "19.997454", "18.758", "24.813967", "25.5667",
                "23.727106", "25.6701", "25.906267", "24.146", "20.296059", "20.450474", "22.2494", "11.931", "11.7", "10.925440",
                "16.7333", "31.633980", "30.340000", "31.326015", "30.901", "26.922070", "26.263863", "24.571270", "26.911661",
                "26.449896", "28.027138", "25.155169", "26.4905", "27.560932", "24.879999", "27.338936", "13.090994", "9.939093",
                "11.017363", "10.7905", "12.934968", "17.437462", "18.000055", "26.850000", "25.4358", "26.449923", "27.176670", "25.321684",
                "27.900383", "28.375694", "25.436298", "27.492413", "26.7964", "25.748695", "27.159101", "27.0937", "26.765844",
                "27.5806", "30.316496", "29.945690", "29.210421", "29.854263", "22.542061", "23.673944", "26.732311", "23.533440",
                "22.066673", "27.036007", "25.010841", "22.3304", "26.540457", "23.8402", "24.0261", "22.59577", "23.23333", "22.87"};

        String [] longitude=new String[]{"80.393700", "83.218483", "80.661209", "79.419182", "80.436539", "92.722237", "92.722237",
                "92.722237", "92.963348", "92.895508", "91.875740", "93.628212", "93.829903", "92.422900", "95.327499", "91.736237",
                "92.778908", "94.911964", "94.220108", "92.684044", "85.137566", "85.000237", "86.982430", "85.450233", "85.896004",
                "85.504799", "86.077103", "84.725502", "83.980698", "84.67", "85.368752", "86.600624", "86.129379", "77.449791",
                "76.768066", "81.629997", "82.152328", "82.033056", "73.02", "72.832801", "70.98", "77.216721", "73.827827",
                "74.02", "72.660614", "72.831062", "73.192635", "70.777184", "72.647781", "69.605965", "76.990547", "75.721123",
                "76.606613", "77.091492", "76.977081", "77.173424", "74.864271", "74.797371", "85.309563", "86.203110", "86.695175",
                "86.151115", "77.580643", "76.462222", "74.78994", "76.700000", "76.936638", "76.270523", "72.64", "77.412613",
                "75.857727", "78.182831", "75.784912", "72.877426", "75.352478", "79.088860", "73.789803", "73.372", "93.950279",
                "91.8833", "92.717636", "94.1077", "93.727592", "82.569", "85.824539", "85.905533", "84.883", "79.7852", "75.54",
                "79.838005", "82.2167", "74.872261", "76.379997", "75.576180", "75.8573", "75.778885", "73.008957", "73.691544",
                "70.922928", "74.639915", "73.302155", "75.827248", "74.5551", "76.625015", "74.629997", "88.606506", "80.224998",
                "78.121719", "76.958885", "78.7047", "79.146881", "78.448288", "79.588165", "80.949997", "81.8463", "80.331871",
                "78.008072", "82.987289", "78.072281", "79.435959", "78.567352", "77.673676", "82.1986", "82.698441", "78.395760",
                "77.66", "83.364944", "77.7006", "78.032188", "78.164246", "78.961830", "77.888000", "88.318954", "86.952393",
                "88.410286", "87.321930", "88.069809", "88.262672", "88.141098", "87.3181", "88.719391", "87.6186", "87.5322",
                "88.263641", "87.86667", "88.38"};

        double  minDist=Integer.MAX_VALUE;
        int minIndex=147;
        for(int i=0;i<146;i++){
            if(cityArr[i]==city_name){
                minIndex=i;
            }
        }

        return new String[] {stateArr[minIndex],cityArr[minIndex]};
    }

}
