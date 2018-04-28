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
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import helpers.ConsultantRecyclerAdapter;
import helpers.SessionManager;
import helpers.UserRecyclerAdapter;
import model.ConsultantInfo;
import model.Information;
import sql.DatabaseHelper;

public class InboxActivity_Consultant extends AppCompatActivity {

    private AppCompatActivity activity = InboxActivity_Consultant.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUser;
    private List<Information> listUser;
    private UserRecyclerAdapter userRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox__consultant);
        initViews();
        initObjects();
    }
    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewNameUser);
        recyclerViewUser = (RecyclerView) findViewById(R.id.recyclerViewUser);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listUser = new ArrayList<>();
        userRecyclerAdapter = new UserRecyclerAdapter(listUser, this.getBaseContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUser.setLayoutManager(mLayoutManager);
        recyclerViewUser.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUser.setHasFixedSize(true);
        recyclerViewUser.setAdapter(userRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);
        sessionManager = new SessionManager(activity);


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
                listUser.clear();
                listUser.addAll(databaseHelper.getUserInfoInbox(sessionManager.getUserID()));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                userRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
