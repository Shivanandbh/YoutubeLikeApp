package com.demo.youtube.mvvm.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.demo.youtube.mvvm.R;
import com.demo.youtube.mvvm.fragments.HomeFragment;

/**
 * Created by ${Shivanand} on 6/20/2019.
 */
public class BottomMainActivity extends AppCompatActivity {
    private MenuItem menuItemSelected;
    private HomeFragment fragment;
    private FragmentManager fragmentManager;
    private android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setTitleMarginStart(0);
        toolbar.setLogo(getResources().getDrawable(R.drawable.new_app_icon));
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        fragment = new HomeFragment();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_container, fragment).commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(menuItemSelected != item) {
                    menuItemSelected = item;
                    switch (item.getItemId()) {
                        case R.id.title_home:
                            fragment = new HomeFragment();
                            break;
                    }
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frame_container, fragment).commit();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
