package com.droid.solver.a2020;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;
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
    }
    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Test App");
        tablayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(TrendingFragment.getInstance(), "Trending");
        adapter.addFragment(ExploreFragment.getInstance(), "Explore");
        tablayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.login:
                showMessage("Login");
                break;
            case R.id.feedback:
                showMessage("Feedback");
                break;
            case R.id.about:
                showMessage("About");
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
}
