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

public class Fragment_Consult extends Fragment {

    View myView;
    CardView consult1;
    CardView consult2;
    CardView consult3;
    CardView consult4;
    CardView consult5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_consult, container, false);

        consult1 = (CardView) myView.findViewById(R.id.Consult1);
        consult2 = (CardView) myView.findViewById(R.id.Consult2);
        consult3 = (CardView) myView.findViewById(R.id.Consult3);
        consult4 = (CardView) myView.findViewById(R.id.Consult4);
        consult5 = (CardView) myView.findViewById(R.id.Consult5);

        final Intent listIntent = new Intent(getActivity(), ConsultantListActivity.class);

        final Bundle bundle = new Bundle();

        consult1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                bundle.putString("expertise", "Love");
                listIntent.putExtras(bundle);
                startActivity(listIntent);
            }
        });
        consult2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                bundle.putString("expertise", "Family");
                listIntent.putExtras(bundle);
                startActivity(listIntent);
            }
        });
        consult3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                bundle.putString("expertise", "Work");
                listIntent.putExtras(bundle);
                startActivity(listIntent);
            }
        });
        consult4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                bundle.putString("expertise", "School");
                listIntent.putExtras(bundle);
                startActivity(listIntent);
            }
        });
        consult5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                bundle.putString("expertise", "All");
                listIntent.putExtras(bundle);
                startActivity(listIntent);
            }
        });

//        consult1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /*FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.content_frame, new Fragment_ConsultList(), "ConsultList").commit();*/
//                switch(v.getId()){
//                    case R.id.Consult1:
//                        bundle.putString("expertise", "Love");
//                        listIntent.putExtras(bundle);
//                        getActivity().startActivity(listIntent);
//                        break;
//                    case R.id.Consult2:
//                        bundle.putString("expertise", "Family");
//                        listIntent.putExtras(bundle);
//                        getActivity().startActivity(listIntent);
//                        break;
//                    case R.id.Consult3:
//                        bundle.putString("expertise", "Work");
//                        listIntent.putExtras(bundle);
//                        getActivity().startActivity(listIntent);
//                        break;
//                    case R.id.Consult4:
//                        bundle.putString("expertise", "School");
//                        listIntent.putExtras(bundle);
//                        getActivity().startActivity(listIntent);
//                        break;
//                    case R.id.Consult5:
//                        bundle.putString("expertise", "All");
//                        listIntent.putExtras(bundle);
//                        getActivity().startActivity(listIntent);
//                        break;
//                }
//            }
//        });

        return myView;
    }
}
