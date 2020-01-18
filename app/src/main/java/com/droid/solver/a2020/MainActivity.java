package com.droid.solver.a2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private ViewPager viewPager;
    private TabLayout tablayout;
    private Toolbar toolbar;
    private List<String> listOfCity;
    private CoordinatorLayout coordinatorLayout;
    private SharedPreferences preferences;
    public static String preferenceName="preferenceName";
    public static String lan="language";
    public PopupMenu popupMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        preferences=this.getSharedPreferences(preferenceName,MODE_PRIVATE);
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
        coordinatorLayout=findViewById(R.id.coordinator);
        toolbar.setOnMenuItemClickListener(this);
        Thread thread=new Thread(){
            @Override
            public void run() {
                addCityToList();
            }
        };
        thread.start();
         Log.i("TAG", "initialized");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        View menuItemView = findViewById(R.id.translate);
        popupMenu = new PopupMenu(this, menuItemView);
        popupMenu.inflate(R.menu.translator_menu);
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
            case R.id.translate:
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        SharedPreferences.Editor editor=preferences.edit();//false for english
                        switch (menuItem.getItemId()){
                            case R.id.bengali:
                                showMessage("Bengali");
                                editor.putInt(lan, 0);
                                editor.apply();
                                break;
                            case R.id.english:
                                showMessage("English");
                                editor.putInt(lan, 1);
                                editor.apply();
                                break;
                            case R.id.hindi:
                                showMessage("Hindi");
                                editor.putInt(lan, 2);
                                editor.apply();
                                break;
                            case R.id.marathi:
                                showMessage("Marathi");
                                editor.putInt(lan, 3);
                                editor.apply();
                                break;
                            case R.id.tamil:
                                showMessage("Tamil");
                                editor.putInt(lan, 4);
                                editor.apply();
                                break;
                            case R.id.telugu:
                                showMessage("Telugu");
                                editor.putInt(lan, 5);
                                editor.apply();
                                break;
                            case R.id.urdu:
                                showMessage("Urdu");
                                editor.putInt(lan, 6);
                                break;
                        }
                        return true;
                    }
                });
                break;
        }
        return false;
    }

    private void showMessage(String s){
        Snackbar.make(coordinatorLayout, "Language switched to "+s ,Snackbar.LENGTH_SHORT).show();
    }

    public void addCityToList() {

        List<String[]> cityArrayList=new ArrayList<>();

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



}
