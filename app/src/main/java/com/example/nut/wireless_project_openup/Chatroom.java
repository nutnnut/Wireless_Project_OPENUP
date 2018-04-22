package com.example.nut.wireless_project_openup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import helpers.MessageRecycle;
import model.Chatmessage;
import model.User;
import sql.DatabaseHelper;

public class Chatroom extends AppCompatActivity {

    private AppCompatActivity activity = Chatroom.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewMessage;
    private List<Chatmessage> listText;
    private MessageRecycle messageRecycle;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        getSupportActionBar().setTitle("");
        initViews();
        initObjects();

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        recyclerViewMessage = (RecyclerView) findViewById(R.id.messagelist);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listText = new ArrayList<>();
        messageRecycle = new MessageRecycle(listText);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewMessage.setLayoutManager(mLayoutManager);
        recyclerViewMessage.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMessage.setHasFixedSize(true);
        recyclerViewMessage.setAdapter(messageRecycle);
        databaseHelper = new DatabaseHelper(activity);

        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);

        getDataFromSQLite(user);
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite(final User u) {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listText.clear();
                listText.addAll(databaseHelper.getMessage(u));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                messageRecycle.notifyDataSetChanged();
            }
        }.execute();
    }
}
