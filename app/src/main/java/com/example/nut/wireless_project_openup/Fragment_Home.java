package com.example.nut.wireless_project_openup;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Home extends Fragment {

    View myView;
    CardView homeinbox;
    CardView consult;
    CardView nearby;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_home, container, false);

        homeinbox = (CardView) myView.findViewById(R.id.homeinbox);
        consult = (CardView) myView.findViewById(R.id.homeconsult);
        nearby = (CardView) myView.findViewById(R.id.homenearby);

        //there are 3 cardview button on home page
        //to consult
        consult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new Fragment_Consult(), "Consult").commit();
            }
        });

        //to inbox
        homeinbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent myIntent = new Intent(getActivity(),InboxActivity.class);
                startActivity(myIntent);
            }
        });

        //to nearby clinic
        nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent myIntent = new Intent(getActivity(), NearbyClinics.class);
                startActivity(myIntent);
            }
        });
        return myView;
    }


}
