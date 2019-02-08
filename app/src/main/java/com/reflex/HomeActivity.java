package com.reflex;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


public class HomeActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private BottomNavigationView navigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        HomeFragment fragment = new HomeFragment();
                        fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
                        return true;
                    case R.id.navigation_dashboard:
                        ActivitiesFragment activitiesFragment = new ActivitiesFragment();
                        fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content, activitiesFragment).commit();

                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.navigation);

        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
