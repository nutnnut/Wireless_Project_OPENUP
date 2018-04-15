package com.example.nut.wireless_project_openup;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.text.ParseException;

import helpers.SessionManager;
import model.Information;
import sql.DatabaseHelper;

public class InfoRegisActivity extends AppCompatActivity{

    private final AppCompatActivity activity = InfoRegisActivity.this;

    private NestedScrollView nestedScrollView;

    private AppCompatButton appCompatButtonSaveInfo;
    private AppCompatButton appCompatButtonSkipInfo;


    private static TextInputEditText DateEdit;
    private Spinner spinnerOccupation;
    private Spinner spinnerMedicalCondition;
    private Spinner spinnerGender;

    private Information information;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_regis);
        initViews();
        initListeners();
        initObjects();
    }

    private void initViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        appCompatButtonSaveInfo = (AppCompatButton) findViewById(R.id.appCompatButtonSaveInfo);
        appCompatButtonSkipInfo = (AppCompatButton) findViewById(R.id.appCompatButtonSkipInfo);
        DateEdit = findViewById(R.id.textInputEditTextDate);
        spinnerOccupation = findViewById(R.id.SpinnerOccupation);
        spinnerMedicalCondition = findViewById(R.id.SpinnerCondition);
        spinnerGender = findViewById(R.id.SpinnerGender);
    }

    private void initListeners() {
        appCompatButtonSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                try {
                    postDataToSQLite();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Intent myIntent = new Intent(InfoRegisActivity.this,MainActivity.class);
                InfoRegisActivity.this.startActivity(myIntent);
            }
        });
        appCompatButtonSkipInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(InfoRegisActivity.this,MainActivity.class);
                InfoRegisActivity.this.startActivity(myIntent);
            }
        });
        DateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
    }

    private void initObjects(){
        information = new Information();
        databaseHelper = new DatabaseHelper(activity);
        sessionManager = new SessionManager(activity);
    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            DateEdit.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

    public void postDataToSQLite() throws ParseException {
        information.setOccupation(spinnerOccupation.getSelectedItem().toString());
        information.setMedicalCondition(spinnerMedicalCondition.getSelectedItem().toString());
        information.setBirthdate(DateEdit.getText().toString());
        information.setGender(spinnerGender.getSelectedItem().toString());
        information.setUserID(sessionManager.getUserID());
        databaseHelper.addInfo(information);
    }
}
