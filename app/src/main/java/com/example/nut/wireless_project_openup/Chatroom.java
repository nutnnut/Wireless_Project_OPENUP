package com.example.nut.wireless_project_openup;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import helpers.MessageRecycle;
import helpers.SessionManager;
import model.Chatmessage;
import model.ConsultantInfo;
import model.Information;
import sql.DatabaseHelper;

public class Chatroom extends AppCompatActivity implements View.OnClickListener{

    private AppCompatActivity activity = Chatroom.this;
    private RecyclerView recyclerViewMessage;
    private List<Chatmessage> listText;
    private EditText message;
    private MessageRecycle messageRecycle;
    private FloatingActionButton buttonSend;
    private Button startcall;

    private DatabaseHelper databaseHelper;
    private Integer userID;
    private Intent intentExtras;
    private Bundle extrasBundle;
    private Integer consultantID;
    private ConsultantInfo consultantInfo;
    private Information information;
    private String TAG = "Chatroom";
    private SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        initViews();
        initObjects();
        initListeners();


    }

    public MessageRecycle getMessageRecycle() {
        return messageRecycle;
    }



    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewMessage = (RecyclerView) findViewById(R.id.messagelist);
        message = (EditText) findViewById(R.id.input);
        buttonSend = (FloatingActionButton) findViewById(R.id.chatroomsend);
    }

    private void initListeners(){
        buttonSend.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listText = new ArrayList<>();

        databaseHelper = new DatabaseHelper(activity);
        sessionManager = new SessionManager(this);
        messageRecycle = new MessageRecycle(listText, sessionManager.isUser());

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setStackFromEnd(true);
        recyclerViewMessage.setLayoutManager(mLayoutManager);
        recyclerViewMessage.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMessage.setHasFixedSize(true);
        recyclerViewMessage.setAdapter(messageRecycle);
        intentExtras = getIntent();
        extrasBundle = intentExtras.getExtras();
        if(sessionManager.isUser()){
            consultantID = extrasBundle.getInt("consultantID");
            userID = sessionManager.getUserID();
            consultantInfo = databaseHelper.getConsultantInfo(consultantID);
            getSupportActionBar().setTitle(consultantInfo.getName());
        }
        else{
            consultantID = sessionManager.getUserID();
            userID = extrasBundle.getInt("userID");
            information = databaseHelper.getInfo(userID);
            getSupportActionBar().setTitle(information.getDisplayName());
        }





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
                listText.clear();

                try {
                    listText.addAll(databaseHelper.getAllChatmessage(userID, consultantID));
                    recyclerViewMessage.scrollToPosition(listText.size()-1);
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

    private void postDataToSQLite(){
        Chatmessage chatmessage = new Chatmessage(message.getText().toString()
                , userID, consultantID, sessionManager.isUser());
        databaseHelper.addChatMessage(chatmessage);
        listText.add(chatmessage);
        recyclerViewMessage.scrollToPosition(listText.size()-1);
        message.setText(null);
        messageRecycle.notifyDataSetChanged();
    }

    //this method allow users choose whether they want to make a video call by connecting to camera or not
    public void videocalldialog(){
        AlertDialog.Builder calldialog = new AlertDialog.Builder(Chatroom.this);
        
        calldialog.setMessage("Start video call?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent( Chatroom.this, VideoCall.class));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog dialog = calldialog.create();
        dialog.setTitle("Video Call");
        dialog.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chatroomsend:
                postDataToSQLite();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //In case we get more items(?)
        switch (item.getItemId()){
            case R.id.StartCall:
                videocalldialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
