package com.droid.solver.a2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.droid.solver.a2020.explorefragment.StreetViewActivity;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private ViewPager viewPager;
    private TabLayout tablayout;
    private Toolbar toolbar;
    private List<String> listOfCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        invalidateOptionsMenu();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        tablayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager(),
                BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(ExploreFragment.getInstance(), "Explore");
        adapter.addFragment(TrendingFragment.getInstance(), "Trending");
        adapter.addFragment(CaptureFragment.getInstance(), "Capture");
        tablayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        setSupportActionBar(toolbar);
        listOfCity=new ArrayList<>();
        toolbar.setOnMenuItemClickListener(this);
        Thread thread=new Thread(){
            @Override
            public void run() {
                addCityToList();
            }
        };
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_place:
                startActivity(new Intent(this,AddPlacesActivity.class));
                break;
            case R.id.about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
            case R.id.share:
                showMessage("Share");
                break;
            case R.id.search:
                showMessage("search clicked");
                startActivity(new Intent(this, StreetViewActivity.class));
                break;
        }
        return false;
    }

    private void showMessage(String s){
        Toast.makeText(this,s+" is cilcked",Toast.LENGTH_SHORT ).show();
    }

    public void addCityToList() {
        List<String[]> cityArrayList=new ArrayList<>();
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

}
