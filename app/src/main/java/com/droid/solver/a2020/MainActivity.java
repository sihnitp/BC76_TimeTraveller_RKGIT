package com.droid.solver.a2020;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import drawerItems.GuideRegistrationActivity;
import drawerItems.WebViewActivity;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener,
                                                NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
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
        navigationView.setNavigationItemSelectedListener(this);
        preferences=this.getSharedPreferences(preferenceName,MODE_PRIVATE);
        invalidateOptionsMenu();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation_view);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        View view=navigationView.getHeaderView(0);
        TextView userName=view.findViewById(R.id.user_name);
        ImageView userImage=view.findViewById(R.id.user_image);
        SharedPreferences preferences=getSharedPreferences(LoginActivity.MY_PREF, MODE_PRIVATE);
        userName.setText(preferences.getString(LoginActivity.NAME, " "));
        Picasso.get().load(preferences.getString(LoginActivity.PHOTO_URL, "no_url")).into(userImage);


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
        if(actionBarDrawerToggle.onOptionsItemSelected(item))return true;
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
                                showMessage("Language Swithced to Bengali");
                                editor.putInt(lan, 0);
                                editor.apply();
                                break;
                            case R.id.english:
                                showMessage("Language Swithced to English");
                                editor.putInt(lan, 1);
                                editor.apply();
                                break;
                            case R.id.hindi:
                                showMessage("Language Swithced to Hindi");
                                editor.putInt(lan, 2);
                                editor.apply();
                                break;
                            case R.id.marathi:
                                showMessage("Language Swithced to Marathi");
                                editor.putInt(lan, 3);
                                editor.apply();
                                break;
                            case R.id.tamil:
                                showMessage("Language Swithced to Tamil");
                                editor.putInt(lan, 4);
                                editor.apply();
                                break;
                            case R.id.telugu:
                                showMessage("Language Swithced to Telugu");
                                editor.putInt(lan, 5);
                                editor.apply();
                                break;
                            case R.id.urdu:
                                showMessage("Language Swithced to Urdu");
                                editor.putInt(lan, 6);
                                editor.apply();
                                break;
                        }
                        return true;
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMessage(String s){
//        Snackbar.make(coordinatorLayout, s+" is clicked" ,Snackbar.LENGTH_SHORT).show();
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.first:
                startActivity(new Intent(MainActivity.this,CulturalActivity.class));
                break;
            case R.id.second:
                startActivity(new Intent(MainActivity.this,GovernmentSchemeActivity.class));
                break;
            case R.id.third:
                startActivity(new Intent(MainActivity.this,KidsActivity.class));
                break;
            case R.id.fourth:
                Intent intent=new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("url", "https://www.olacabs.com/");
                startActivity(intent);
                break;
            case R.id.fifth:
                intent=new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", "https://www.oyorooms.com");
                startActivity(intent);
                break;
            case R.id.sixth:
                startActivity(new Intent(MainActivity.this, GuideRegistrationActivity.class));
                break;
            default :
                return true;
        }
        drawerLayout.closeDrawers();
        return true;
    }


}
