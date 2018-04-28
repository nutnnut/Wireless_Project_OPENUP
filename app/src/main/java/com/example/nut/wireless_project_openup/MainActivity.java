package com.example.nut.wireless_project_openup;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

import helpers.SessionManager;
import model.Information;
import sql.DatabaseHelper;

/**
 * This class is the main activity which show links to other features and can navigate with
 * navigation bar.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager sessionManager;
    private DatabaseHelper databaseHelper;
    private final AppCompatActivity activity = MainActivity.this;
    private NavigationView navView;
    private View navBarHeader;
    private TextView textViewName;

    /**
     * on Create is used to initialized components
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(activity);
        sessionManager.checkLogin();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databaseHelper = new DatabaseHelper(activity);

        int userID = sessionManager.getUserID();
        Information information = databaseHelper.getInfo(userID);
        String displayName = information.getDisplayName();

        navView = findViewById(R.id.nav_view);
        navBarHeader = navView.getHeaderView(0);
        textViewName = navBarHeader.findViewById(R.id.textViewNavBarName);
        textViewName.setText(displayName);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new Fragment_Home(), "Home").commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        sessionManager.checkLogin();
    }

    /**
     * This method is to close navigation bar when pressed back or if it is already closed
     * pressing back will close the app instead of going back to login.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        sessionManager.checkLogin();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //Go home if not at home, else close app;
            Fragment_Home myFragment = (Fragment_Home)getFragmentManager().findFragmentByTag("Home");
            if(myFragment == null){
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Fragment_Home(), "Home").commit();
            }
            else{
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
            }
        }
    }

    /**
     * This method defines the action of each item in navigation bar.
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_home) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Fragment_Home(), "Home").commit();
        }

        else if (id == R.id.nav_inbox) {
            startActivity(new Intent(MainActivity.this, InboxActivity.class));

        }

        else if (id == R.id.nav_consult) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new Fragment_Consult(), "Consult").commit();
        }

        else if (id == R.id.nav_nearby) {
            startActivity(new Intent(MainActivity.this, NearbyClinics.class));
        }

        else if (id == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
        }
        else if (id == R.id.nav_out) {
            sessionManager.logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
