package com.example.nut.wireless_project_openup;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import helpers.ConsultantRecyclerAdapter;
import model.ConsultantInfo;
import sql.DatabaseHelper;

public class Fragment_ConsultList extends Fragment {

    private View myView;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewConsultant;
    private List<ConsultantInfo> listConsultant;
    private ConsultantRecyclerAdapter consultantRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.layout_consultlist, container, false);

        textViewName = (AppCompatTextView) myView.findViewById(R.id.textViewName);
        recyclerViewConsultant = (RecyclerView) myView.findViewById(R.id.recyclerViewConsultant);

        initObjects();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setSubtitle("");

        return myView;
    }


    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listConsultant = new ArrayList<>();
        consultantRecyclerAdapter = new ConsultantRecyclerAdapter(listConsultant);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewConsultant.setLayoutManager(mLayoutManager);
        recyclerViewConsultant.setItemAnimator(new DefaultItemAnimator());
        recyclerViewConsultant.setHasFixedSize(true);
        recyclerViewConsultant.setAdapter(consultantRecyclerAdapter);
        databaseHelper = new DatabaseHelper(getActivity());

        String emailFromIntent = getActivity().getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);

        getDataFromSQLite();
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listConsultant.clear();
                listConsultant.addAll(databaseHelper.getAllConsultantInfo());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                consultantRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
