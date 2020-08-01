package com.droid.solver.a2020.explorefragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.droid.solver.a2020.CONSTANT;
import com.droid.solver.a2020.ExploreFragment;
import com.droid.solver.a2020.R;

import java.util.ArrayList;
import java.util.List;

public class ExplorerCityActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private List<String[]> cityArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int position = 0;
        position = intent.getIntExtra("cityIndex", 0);
        cityArrayList = new ArrayList<>();
        setContentView(R.layout.activity_explorer_city);
        addCity();
        toolbar = findViewById(R.id.toolbar);
        String cc = CONSTANT.state[position];
        toolbar.setTitle(cc.substring(0, 1).toUpperCase() + cc.substring(1));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        recyclerView = findViewById(R.id.recycler_view);
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        recyclerView.setLayoutAnimation(animation);

        String[] temp = ExploreFragment.capitalizeFirstLetter(cityArrayList.get(position));
        ExploreFragmentCityAdapter adapter = new ExploreFragmentCityAdapter(temp, this, CONSTANT.state[position]);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    public void addCity() {

        String[] andhra = new String[]{"amravati", "visakhapatnam", "vijayawada", "tirupati", "guntur"};
        String[] arunachal = new String[]{"tawang", "itanagar", "zero", "bomdila", "pasighat"};
        String[] assam = new String[]{"guwahati", "silchar", "dibrugarh", "jorhat", "nagaon"};

        String[] bihar = new String[]{"patna", "gaya", "bhagalpur", "nalanda", "darbhanga", "sitamarhi", "madhubani", "chhapra", "buxar", "ara", "rajgir",
                "muzaffarpur", "saharsha", "begusarai"};

        String[] chhattisgarh = new String[]{"raipur", "bilaspur", "bastar"};
        String[] goa = new String[]{"panaji", "ponda"};
        String[] gujrat = new String[]{"ahmedabad", "surat", "vadodra", "rajkot", "gandhinagar", "porbandar", "dwarka"};
        String[] haryana = new String[]{"karnal", "rohtak", "panipat"};
        String[] himachal = new String[]{"shimla", "dharamshala", "kullu manali"};
        String[] jammu = new String[]{"jammu", "kashmir", "sonamarg", "amarnath"};
        String[] jharkhand = new String[]{"ranchi", "jamshedpur", "deoghar", "bokaro"};
        String[] karnataka = new String[]{"bengaluru", "hampi", "mangalore"};
        String[] kerela = new String[]{"thiruvananthapuram", "kochi"};
        String[] madhya = new String[]{"bhopal", "indore", "gwalior", "ujjain"};
        String[] maharastra = new String[]{"mumbai", "aurangabad", "pune", "nagpur", "nashik", "khandala", "thane"};
        String[] manipur = new String[]{"imphal", "kakching"};
        String[] meghayala = new String[]{"shillong", "cherrapunji"};
        String[] mizoram = new String[]{"aizwal", "champhai"};
        String[] nagaland = new String[]{"kohima"};
        String[] oddisha = new String[]{"bhubaneswar", "cuttack", "rourkela"};
        String[] punjab = new String[]{"amritsar", "patiala", "jalandhar", "ludhiana"};
        String[] rajasthan = new String[]{"jaipur", "jodhpur", "udaipur", "jaisalmer", "ajmer", "bikaner", "kota", "pushkar", "alwar", "chhitorgarh"};
        String[] sikkim = new String[]{"gangtok"};
        String[] tamilnadu = new String[]{"chennai", "madurai", "coimbatore", "tiruchirappali", "vellore"};
        String[] telangana = new String[]{"hyderabad", "warangal"};
        String[] tripura = new String[]{"agartala", "udaipur"};
        String[] uttarpradesh = new String[]{"lucknow", "prayagraj", "kanpur", "agra", "aligarh", "bareilly", "jhansi", "mathura",
                "jaunpur", "firozabad", "fatehpur sikri", "gorakhpur", "vrindavan", "mirzapur", "ghaziabad"};
        String[] uttarakhand = new String[]{"dehradun", "haridwar", "kashipur", "roorkee"};
        String[] westbengal = new String[]{"kolkata", "asansol", "siliguri", "durgapur", "haldia", "darjeeling", "malda", "kharagpur",
                "jalpaiguri", "birbhum", "bishnupur", "chandannagar", "howrah", "bardhaman"};
        String[] andman = new String[]{"port blair", "neil island", "ross island", "diglipur", "mayabunder"};
        String[] chandigarh = new String[]{"chandigarh"};
        String[] dadra = new String[]{"silavasa"};
        String[] daman = new String[]{"daman", "diu"};
        String[] lakshadweep = new String[]{"kavaratti"};
        String[] delhi = new String[]{"delhi"};
        String[] pondicherry = new String[]{"pudducherry", "mahe", "karaikal", "yanam"};

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

}