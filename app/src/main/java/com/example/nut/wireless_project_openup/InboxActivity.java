package com.example.nut.wireless_project_openup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import helpers.ConsultantRecyclerAdapter;
import helpers.SessionManager;
import model.ConsultantInfo;
import sql.DatabaseHelper;

/**
 * Similar to ConsultantList activity, this activity shows a list of consultant who
 * the user has sent messages to
 */
public class InboxActivity extends AppCompatActivity{

    private AppCompatActivity activity = InboxActivity.this;
    private RecyclerView recyclerViewConsultant;
    private List<ConsultantInfo> listConsultant;
    private ConsultantRecyclerAdapter consultantRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        getSupportActionBar().setTitle("Inbox");
        initViews();
        initObjects();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewConsultant = (RecyclerView) findViewById(R.id.recyclerViewConsultant2);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listConsultant = new ArrayList<>(); //who users chatted with
        consultantRecyclerAdapter = new ConsultantRecyclerAdapter(listConsultant, this.getBaseContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewConsultant.setLayoutManager(mLayoutManager);
        recyclerViewConsultant.setItemAnimator(new DefaultItemAnimator());
        recyclerViewConsultant.setHasFixedSize(true);
        recyclerViewConsultant.setAdapter(consultantRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);
        sessionManager = new SessionManager(activity);


        getDataFromSQLite();
    }

    /**
     * This method is to fetch all consultant records from SQLite that has been chatting with the
     * user.
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listConsultant.clear();
                listConsultant.addAll(databaseHelper.getConsultantInfoInbox(sessionManager.getUserID()));
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