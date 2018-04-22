package com.example.nut.wireless_project_openup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Chatroom extends AppCompatActivity {

    private static int SING_IN_REQUEST_CODE=1;
   // private FirebaseListAdapter<Chatmessage> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
    }
}
