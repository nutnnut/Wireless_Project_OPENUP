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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager sessionManager;
    private DatabaseHelper databaseHelper;
    private final AppCompatActivity activity = MainActivity.this;

    private TextView textViewNavBarName;

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
        textViewNavBarName = (TextView) findViewById(R.id.textViewNavBarName);


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

    //Unused option menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_home) {
            //startActivity(new Intent(MainActivity.this,MainActivity.class));
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
//            Uri gmmIntentUri = Uri.parse("geo:13.79457750,100.3212284?q=Golden Jubilee Medical Center");
//            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//            mapIntent.setPackage("com.google.android.apps.maps");
//            startActivity(mapIntent);
        }

        else if (id == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
        }
        else if (id == R.id.nav_out) {
            sessionManager.logoutUser();
            //startActivity(new Intent(MainActivity.this,splash.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
