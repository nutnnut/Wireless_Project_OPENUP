package com.example.nut.wireless_project_openup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import helpers.MessageRecycle;
import model.Chatmessage;
import model.Consultant;
import model.User;
import sql.DatabaseHelper;

public class Chatroom extends AppCompatActivity {

    private AppCompatActivity activity = Chatroom.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewMessage;
    private List<Chatmessage> listText;
    private EditText message;
    private MessageRecycle messageRecycle;
    private DatabaseHelper databaseHelper;
    private User user;
    private Consultant consultant;
    private Intent intentExtras;
    private Bundle extrasBundle;
    private Integer consultantID;
    private String TAG = "Chatroom";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        getSupportActionBar().setTitle("");
        initViews();
        initObjects();

    }

    public MessageRecycle getMessageRecycle() {
        return messageRecycle;
    }



    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = (AppCompatTextView) findViewById(R.id.textViewName);
        recyclerViewMessage = (RecyclerView) findViewById(R.id.messagelist);
        message = (EditText) findViewById(R.id.input);
        intentExtras = getIntent();
        extrasBundle = intentExtras.getExtras();
        consultantID = extrasBundle.getInt("consultantID");
        message.setText(consultantID.toString());
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

        //String emailFromIntent = getIntent().getStringExtra("EMAIL");
        //textViewName.setText(emailFromIntent);

        getDataFromSQLite(user,consultant);
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite(final User u, final Consultant c) {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listText.clear();

                try {
                    listText.addAll(databaseHelper.getMessage(u, c));
                }catch(Exception e){
                    Log.e(TAG, e+"");
                    Log.e(TAG, "doInBackground: Bam wants me to fetch some null in DB");
                }

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
