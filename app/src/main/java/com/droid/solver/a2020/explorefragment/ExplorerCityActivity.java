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
import android.widget.Toast;

import com.droid.solver.a2020.ExploreFragment;
import com.droid.solver.a2020.R;

import java.util.ArrayList;
import java.util.List;

public class ExplorerCityActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private String[] state = new String[]{
            "andhra pradesh", "arunachal pradesh", "assam", "bihar", "chhattisgarh", "goa", "gujarat", "haryana", "himachal pradesh",
            "jammu and kashmir", "jharkhand", "karnataka", "kerela", "madhya pradesh", "maharashtra", "manipur", "meghalaya", "mizoram",
            "nagaland", "odisha", "punjab", "rajasthan", "sikkim", "tamil nadu", "telangana", "tripura", "uttar pradesh", "uttarakhand",
            "west bengal", "andaman and nicobar islands", "chandigarh", "dadar and nagar haveli", "daman and diu", "lakshadweep",
            "delhi", "puducherry"
    };

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
        String cc = state[position];
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
        ExploreFragmentCityAdapter adapter = new ExploreFragmentCityAdapter(temp, this, state[position]);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }

    public void addCity() {

        String[] andhra = new String[]{"amravati", "visakhapatnam", "vijayawada", "tirupati", "guntur"};
        String[] arunachal = new String[]{"tawang", "itanagar", "zero", "bomdila", "pasighat"};
        String[] assam = new String[]{"guwahati", "silchar", "dibrugarh", "jorhat", "nagaon"};
        String[] bihar = new String[]{"patna", "gaya", "bhagalpur", "nalanda", "darbhanga", "sitamarhi", "madhubani", "chhapra", "buxar", "ara", "rajgir",
                "muzaffarpur", "saharsha", "begusarai", "katihar", "sasaram", "siwan", "nawada", "motihari", "jamui", "bettiah", "aurangabad",
                "jehanabad", "purnea"};
        String[] chhattisgarh = new String[]{"raipur", "bilaspur", "bastar", "durg", "dantewada"};
        String[] goa = new String[]{"vasco da gama", "margao", "panaji", "mapusa", "ponda"};
        String[] gujrat = new String[]{"ahmedabad", "surat", "vadodra", "rajkot", "gandhinagar", "junagadh", "bhavnagar",
                "jamnagar", "porbandar", "dwarka", "palanpur", "godhra", "idar"};
        String[] haryana = new String[]{"karnal", "hisar", "rohtak", "sonipat", "panipat"};
        String[] himachal = new String[]{"shimla", "kufri", "dharamshala", "kullu manali", "khajjiar"};
        String[] jammu = new String[]{"jammu", "kashmir", "gulmarg", "sonamarg", "amarnath"};
        String[] jharkhand = new String[]{"ranchi", "jamshedpur", "deoghar", "bokaro", "giridih"};
        String[] karnataka = new String[]{"bengaluru", "hampi", "mysore", "coorg", "mangalore", "ooty"};
        String[] kerela = new String[]{"thiruvananthapuram", "kochi", "kovalam", "alleppey", "munar"};
        String[] madhya = new String[]{"bhopal", "indore", "jabalpur", "gwalior", "ujjain"};
        String[] maharastra = new String[]{"mumbai", "aurangabad", "pune", "nagpur", "nashik", "chandrapur", "jalgaon", "khandala", "kolhapur",
                "solapur", "thane"};
        String[] manipur = new String[]{"imphal", "kakching", "moirang", "churachandpur", "ukhrul"};
        String[] meghayala = new String[]{"shillong", "cherrapunji", "tura", "umroi", "mawlai"};
        String[] mizoram = new String[]{"aizwal", "champhai", "kolasib", "lawngtlai", "lunglei", "mamit"};
        String[] nagaland = new String[]{"kohima", "dimapur", "mokokchung", "longleng", "mon"};
        String[] oddisha = new String[]{"bhubaneswar", "cuttack", "rourkela", "puri", "sambalpur"};
        String[] punjab = new String[]{"amritsar", "patiala", "jalandhar", "ludhiana", "mohali"};
        String[] rajasthan = new String[]{"jaipur", "jodhpur", "udaipur", "jaisalmer", "ajmer", "bikaner", "kota", "pushkar", "alwar",
                "chhitorgarh", "bundi"};
        String[] sikkim = new String[]{"gangtok", "namchi", "nayabajar", "rangpo", "rhenak"};
        String[] tamilnadu = new String[]{"chennai", "madurai", "coimbatore", "tiruchirappali", "salem", "vellore", "thanjavur"};
        String[] telangana = new String[]{"hyderabad", "warangal", "adilabad", "karimnagar", "khammam", "nalgonda", "nizamabad"};
        String[] tripura = new String[]{"agartala", "udaipur", "dharmanagar"};
        String[] uttarpradesh = new String[]{"lucknow", "prayagraj", "kanpur", "agra", "aligarh", "bareilly", "jhansi", "mathura",
                "jaunpur", "firozabad", "fatehpur sikri", "gorakhpur", "vrindavan", "mirzapur", "ghaziabad", "meerut", "saharanpur", "muzzafarnagar",
                "noida", "unnao", "kashganj", "ghazipur", "ballia", "pilibhit", "raebareli"};
        String[] uttarakhand = new String[]{"dehradun", "haridwar", "kashipur", "roorkee", "haldwani"};
        String[] westbengal = new String[]{"kolkata", "asansol", "siliguri", "durgapur", "haldia", "darjeeling", "malda", "kharagpur",
                "jalpaiguri", "birbhum", "bishnupur", "chandannagar", "howrah", "kalimpong", "santhia", "bolpur", "bardhaman",
                "murshidabad", "purulia", "serampore", "kalyani"};
        String[] andman = new String[]{"port blair", "neil island", "ross island", "diglipur", "mayabunder"};
        String[] chandigarh = new String[]{"chandigarh"};
        String[] dadra = new String[]{"silavasa", "dadra", "naroli", "vapi", "amli"};
        String[] daman = new String[]{"daman", "diu"};
        String[] lakshadweep = new String[]{"kavaratti", "minicoy island", "amini"};
        String[] delhi = new String[]{"delhi"};
        String[] pondicherry = new String[]{"pudducherry", "mahe", "karaikal", "yanam", "villianpur"};

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