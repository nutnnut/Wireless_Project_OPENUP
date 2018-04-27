package com.example.nut.wireless_project_openup;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import helpers.ConsultantRecyclerAdapter;
import model.ConsultantInfo;
import sql.DatabaseHelper;

public class InboxActivity_Consultant extends AppCompatActivity {

    private AppCompatActivity activity = InboxActivity_Consultant.this;
    private AppCompatTextView textViewName;
    private AppCompatTextView textViewFilter;
    private RecyclerView recyclerViewConsultant;
    private List<ConsultantInfo> listConsultant;
    private ConsultantRecyclerAdapter consultantRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private Intent intentExtras;
    private Bundle extrasBundle;
    private String filter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox__consultant);
    }
    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        textViewFilter = (AppCompatTextView) findViewById(R.id.AppCompatTextViewFilter);
        recyclerViewConsultant = (RecyclerView) findViewById(R.id.recyclerViewConsultant2);
        intentExtras = getIntent();
        textViewFilter.setText(filter);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listConsultant = new ArrayList<>();
        consultantRecyclerAdapter = new ConsultantRecyclerAdapter(listConsultant, this.getBaseContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewConsultant.setLayoutManager(mLayoutManager);
        recyclerViewConsultant.setItemAnimator(new DefaultItemAnimator());
        recyclerViewConsultant.setHasFixedSize(true);
        recyclerViewConsultant.setAdapter(consultantRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);


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
                if(filter==null){
                    listConsultant.addAll(databaseHelper.getAllConsultantInfo());
                }
                else if(filter.equals("All")){
                    listConsultant.addAll(databaseHelper.getAllConsultantInfo());
                }
                else{
                    listConsultant.addAll(databaseHelper.getAllConsultantInfo(filter));
                }


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