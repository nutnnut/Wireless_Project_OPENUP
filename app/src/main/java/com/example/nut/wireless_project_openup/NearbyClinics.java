package com.example.nut.wireless_project_openup;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

<<<<<<< HEAD

import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
=======
import com.google.android.gms.maps.CameraUpdateFactory;
>>>>>>> parent of 4074e05... nearby unsuccessful
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

<<<<<<< HEAD
public class NearbyClinics extends FragmentActivity{

    private GoogleMap mMap;


    //protected GeoDataClient mGeoDataClient;

=======
public class NearbyClinics extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

>>>>>>> parent of 4074e05... nearby unsuccessful
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(R.layout.activity_main);

        // Construct a GeoDataClient.
        //mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        //mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // TODO: Start using the Places API.
=======
        setContentView(R.layout.activity_nearby_clinics);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
>>>>>>> parent of 4074e05... nearby unsuccessful
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ArrayList<LatLng> Pos = new ArrayList<LatLng>();
        ArrayList<String> Title = new ArrayList<String>();
        ArrayList<String> Details = new ArrayList<String>();

        // Add static markers details
        Pos.add(new LatLng(13.7949983, 100.3217247));
        Title.add("Golden Jubilee Medical Center");
        Details.add("The best medical center for Mahidol students and others patients. High quality of medical treatment at a very low price.");

        //TODO: Retrieve details from Database


        // Put markers on map
        for(int i = 0; i<Pos.size(); i++){
            mMap.addMarker(new MarkerOptions().position(Pos.get(i)).title(Title.get(i)).snippet(Details.get(i)));
        }
    }
}
