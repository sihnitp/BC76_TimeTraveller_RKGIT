package com.droid.solver.a2020;


import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.droid.solver.a2020.explore.ExploreActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExploreFragment extends Fragment implements View.OnClickListener {

    private String [] state;
    private List<String[]> cityArrayList;
    private boolean isStateSelected=false;//default
    private int selectedStateIndex=0;//default
    private boolean isCitySelected=false;//default
    private int selectedCityIndex=0;//defaulttrue
    private ArrayAdapter<String> stateAdapter,cityAdapter;
    private AutoCompleteTextView stateAutoComplete,cityAutoComplete;
    private MaterialButton button;

    public static ExploreFragment getInstance(){
        return new ExploreFragment();
    }

    public ExploreFragment() {
        cityArrayList=new ArrayList<>();
        state=new String[]{
                "andhra pradesh","arunachal pradesh","assam","bihar","chhattisgarh","goa","gujrat","haryana","himachal pradesh",
                "jammu and kashmir","jharkhand","karnataka","kerela","madhya pradesh","maharashtra","manipur","meghalaya","mizoram",
                "nagaland","odisha","punjab","rajasthan","sikkim","tamil nadu","telangana","tripura","uttar pradesh","uttarakhand",
                "west bengal","andaman and nicobar islands","chandigarh","dadar and nagar haveli","daman and diu","lakshadweep",
                "delhi","pondicherry"
        };
        addCityArray();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_explore, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        String [] temp=capitalizeFirstLetter(state);
        stateAdapter=new ArrayAdapter<>(getActivity(),R.layout.dropdown_menuitem,temp);
        stateAutoComplete= view.findViewById(R.id.state);
        cityAutoComplete=view.findViewById(R.id.city);
        button=view.findViewById(R.id.search_button);
        stateAutoComplete.setAdapter(stateAdapter);
        button.setOnClickListener(this);

        stateAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isStateSelected=true;
                selectedStateIndex=i;
                String [] temp=capitalizeFirstLetter(cityArrayList.get(i));
              cityAdapter=new ArrayAdapter<>(getActivity(),R.layout.dropdown_menuitem,temp);
              cityAutoComplete.setAdapter(cityAdapter);
            }
        });

        cityAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!isStateSelected){
                    Toast.makeText(getActivity(), "City not selected", Toast.LENGTH_SHORT).show();
                }
                else{
                    isCitySelected=true;
                    selectedCityIndex=i;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(isCitySelected&&isStateSelected){
            startActivity(new Intent(getActivity(), ExploreActivity.class));
        }else{

            if(!isStateSelected) {
                showMessage("State not selected");
            }else{
                showMessage("City not selected");
            }
        }

    }

    private void showMessage(String s){
        Toast.makeText(getActivity(), s+" is clicked", Toast.LENGTH_SHORT).show();
    }

    private void addCityArray(){

        String [] andhra =new String[]{"amravati","visakhapatnam","vijayawada","tirupati","guntur"};
        String [] arunachal=new String[]{"tawang","itanagar","zero","bomdila","pasighat"};
        String [] assam=new String[]{"guwahati","silchar","dibrugarh","jorhat","nagaon"};

        String [] bihar=new String[]{"patna","gaya","bhagalpur","nalanda","darbhanga","sitamarhi","madhubani","chhapra","buxar","ara","rajgir",
                                      "muzaffarpur","saharsha","begusarai","katihar","sasaram","siwan","nawada","motihari","jamui","bettiah","aurangabad",
                                       "jehanabad","purnea"};

        String [] chhattisgarh=new String[]{"raipur","bilaspur","bastar","durg","dantewada"};
        String [] goa=new String[]{"vasco da gama","margao","panaji","mapusa","ponda"};
        String [] gujrat=new String[]{"ahmedabad","surat","vadodra","rajkot","gandhinagar","junagadh","bhavnagar",
                "jamnagar","porbandar","dwarka","palanpur","godhra","idar"};

        String [] haryana=new String[]{"karnal","hisar","rohtak","sonipat","panipat"};
        String [] himachal=new String[]{"shimla","kufri","dharamshala","kullu manali","khajjiar"};
        String [] jammu=new String[]{"jammu","kashmir","gulmarg","sonamarg","amarnath"};

        String [] jharkhand=new String[]{"ranchi","jamshedpur","deoghar","bokaro","giridih"};
        String [] karnataka=new String[]{"bengaluru","hampi","mysore","coorg","mangalore","ooty"};
        String [] kerela=new String[]{"thiruvananthapuram","kochi","kovalam","alleppey","munar"};

        String [] madhya=new String[]{"bhopal","indore","jabalpur","gwalior","ujjain"};
        String [] maharastra=new String[]{"mumbai","aurangabad","pune","nagpur","nashik","chandrapur","jalgaon","khandala","kolhapur",
                "solapur","thane"};
        String [] manipur=new String[]{"imphal","kakching","moirang","churachandpur","ukhrul"};

        String [] meghayala=new String[]{"shillong","cherrapunji","tura","umroi","mawlai"};
        String [] mizoram=new String[]{"aizwal","champhai","kolasib","lawngtlai","lunglei","mamit"};
        String [] nagaland=new String[]{"kohima","dimapur","mokokchung","longleng","mon"};
        String [] oddisha=new String[]{"bhubaneswar","cuttack","rourkela","puri","sambalpur"};

        String [] punjab=new String[]{"amritsar","patiala","jalandhar","ludhiana","mohali"};
        String [] rajasthan=new String[]{"jaipur","jodhpur","udaipur","jaisalmer","ajmer","bikaner","kota","pushkar","alwar",
                "chhitorgarh","bundi"};

        String [] sikkim=new String[]{"gangtok","namchi","nayabajar","rangpo","rhenak"};

        String [] tamilnadu=new String[]{"chennai","madurai","coimbatore","tiruchirappali","salem","vellore","thanjavur"};
        String [] telangana=new String[]{"hyderabad","warangal","adilabad","karimnagar","khammam","nalgonda","nizamabad"};
        String [] tripura=new String[]{"agartala","udaipur","dharmanagar"};

        String [] uttarpradesh=new String[]{"lucknow","prayagraj","kanpur","agra","aligarh","bareilly","jhansi","mathura",
                "jaunpur","firozabad","fatehpur sikri","gorakhpur","vrindavan","mirzapur","ghaziabad","meerut","saharanpur","muzzafarnagar",
                 "noida","unnao","kashganj","ghazipur","ballia","pilibhit","raebareli"};

        String [] uttarakhand=new String[]{"dehradun","haridwar","kashipur","roorkee","haldwani"};

        String [] westbengal=new String[]{"kolkata","asansol","siliguri","durgapur","haldia","darjeeling","malda","kharagpur",
                "jalpaiguri","birbhum","bishnupur","chandannagar","howrah","kalimpong","santhia","bolpur","bardhaman",
                "murshidabad","purulia","serampore","kalyani"};

        String [] andman=new String[]{"port blair","neil island","ross island","diglipur","mayabunder"};
        String [] chandigarh=new String[]{"chandigarh"};
        String [] dadra=new String[]{"silavasa","dadra","naroli","vapi","amli"};
        String [] daman=new String[]{"daman","diu"};

        String [] lakshadweep=new String[]{"kavaratti","minicoy island","amini"};
        String [] delhi=new String[]{"delhi"};
        String [] pondicherry=new String[]{"pudducherry","mahe","karaikal","yanam","villianpur"};

        cityArrayList.add(andhra);
        cityArrayList.add(arunachal);
        cityArrayList.add(assam);
        cityArrayList.add(bihar);
        cityArrayList.add(chhattisgarh);
        cityArrayList.add(goa);
        cityArrayList.add(gujrat);
        cityArrayList.add(haryana);
        cityArrayList.add(himachal);
        cityArrayList.add(jammu);
        cityArrayList.add(jharkhand);
        cityArrayList.add(karnataka);
        cityArrayList.add(kerela);
        cityArrayList.add(madhya);
        cityArrayList.add(maharastra);
        cityArrayList.add(manipur);
        cityArrayList.add(meghayala);
        cityArrayList.add(mizoram);
        cityArrayList.add(nagaland);
        cityArrayList.add(oddisha);
        cityArrayList.add(punjab);
        cityArrayList.add(rajasthan);
        cityArrayList.add(sikkim);
        cityArrayList.add(tamilnadu);
        cityArrayList.add(telangana);
        cityArrayList.add(tripura);
        cityArrayList.add(uttarpradesh);
        cityArrayList.add(uttarakhand);
        cityArrayList.add(westbengal);
        cityArrayList.add(andman);
        cityArrayList.add(chandigarh);
        cityArrayList.add(dadra);
        cityArrayList.add(daman);
        cityArrayList.add(lakshadweep);
        cityArrayList.add(delhi);
        cityArrayList.add(pondicherry);
    }

    private String[] capitalizeFirstLetter(String [] city){
        String [] temp=new String[city.length];

        for(int i=0;i<city.length;i++){
            String s=city[i];
            String ss=s.substring(0,1).toUpperCase()+s.substring(1);
            temp[i]=ss;
        }
        return temp;
    }



}
