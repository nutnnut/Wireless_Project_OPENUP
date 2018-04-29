package com.example.nut.wireless_project_openup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import helpers.SessionManager;
import helpers.UserRecyclerAdapter;
import model.Information;
import sql.DatabaseHelper;

/**
 * This activity shows a list of users who sent messages to the consultant
 */
public class InboxActivity_Consultant extends AppCompatActivity {

    private AppCompatActivity activity = InboxActivity_Consultant.this;
    private RecyclerView recyclerViewUser;
    private List<Information> listUser;
    private UserRecyclerAdapter userRecyclerAdapter;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;
    private Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox__consultant);
        initViews();
        initObjects();

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logoutUser();
            }
        });
    }
    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewUser = (RecyclerView) findViewById(R.id.recyclerViewUser);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listUser = new ArrayList<>(); //who the consultant chatted with
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
