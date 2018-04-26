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
    CardView videoshortcut;
    CardView consult;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_home, container, false);

        videoshortcut = (CardView) myView.findViewById(R.id.videoshortcut);

        videoshortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent myIntent = new Intent(getActivity(),VideoCall.class);
                startActivity(myIntent);
            }
        });
        return myView;
    }


}
