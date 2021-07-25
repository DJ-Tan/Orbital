package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    int userid;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Retrieve id from Login Activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userid = extras.getInt("userid");
            username = extras.getString("username");
        }

        Bundle homeBundle = new Bundle();
        homeBundle.putString("username", username);
        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            homeFragment.setArguments(homeBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    homeFragment).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Bundle homeBundle = new Bundle();
        homeBundle.putString("username", username);

        Bundle profileBundle = new Bundle();
        profileBundle.putInt("userid", userid);
        profileBundle.putString("username", username);

        Bundle pillBundle = new Bundle();
        pillBundle.putInt("userid", userid);

        Bundle scheduleBundle = new Bundle();
        scheduleBundle.putInt("userid", userid);

        Bundle settingsBundle = new Bundle();
        settingsBundle.putInt("userid", userid);

        if (item.getItemId() == R.id.nav_home) {
            HomeFragment homeFragment = new HomeFragment();
            homeFragment.setArguments(homeBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    homeFragment).commit();
        } else if (item.getItemId() == R.id.nav_profile) {
            ProfileFragment profileFragment = new ProfileFragment();
            profileFragment.setArguments(profileBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    profileFragment).commit();
        } else if (item.getItemId() == R.id.nav_pill) {
            PillFragment pillFragment = new PillFragment();
            pillFragment.setArguments(pillBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    pillFragment).commit();
        } else if (item.getItemId() == R.id.nav_schedule) {
            ScheduleFragment scheduleFragment = new ScheduleFragment();
            scheduleFragment.setArguments(scheduleBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    scheduleFragment).commit();
        } else if (item.getItemId() == R.id.nav_settings) {
            SettingsFragment settingsFragment = new SettingsFragment();
            settingsFragment.setArguments(settingsBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    settingsFragment).commit();
        } else if (item.getItemId() == R.id.nav_logout) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}