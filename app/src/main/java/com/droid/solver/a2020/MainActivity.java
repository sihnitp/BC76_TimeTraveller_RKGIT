package com.droid.solver.a2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private ViewPager viewPager;
    private TabLayout tablayout;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        invalidateOptionsMenu();
    }
    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Test App");
        tablayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(ExploreFragment.getInstance(), "Explore");
        adapter.addFragment(TrendingFragment.getInstance(), "Trending");
//        adapter.addFragment(CaptureFragment.getInstance(), "Capture");
        tablayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

        @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        Log.i("TAG", "called");
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        MenuItem loginMenu=menu.findItem(R.id.login);

        if(loginMenu!=null) {
            if (user == null) {
                loginMenu.setTitle("Login");
            } else {
                loginMenu.setTitle("Logout");
            }
        }
        return super.onPrepareOptionsMenu(menu);

    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){
            case R.id.login:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user==null) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    signOut();
                }
                break;
            case R.id.add_place:
                startActivity(new Intent(this,AddPlacesActivity.class));
                break;
            case R.id.about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
            case R.id.share:
                showMessage("Share");
                break;
        }
        return false;
    }

    private void showMessage(String s){
        Toast.makeText(this,s+" is cilcked",Toast.LENGTH_SHORT ).show();
    }

    private void signOut(){
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu();
            }
        });
    }
}
