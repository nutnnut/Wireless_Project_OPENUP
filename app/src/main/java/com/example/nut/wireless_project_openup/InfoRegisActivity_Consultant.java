package com.example.nut.wireless_project_openup;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
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
import model.ConsultantInfo;
import model.Information;
import sql.DatabaseHelper;

public class InfoRegisActivity_Consultant extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = InfoRegisActivity_Consultant.this;

    private NestedScrollView nestedScrollView;

    private AppCompatButton appCompatButtonSaveInfo;


    private static TextInputEditText DateEdit;
    private Spinner spinnerExpertise;
    private Spinner spinnerGender;

    private ConsultantInfo consultantInfo;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_regis_consultant);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        appCompatButtonSaveInfo = (AppCompatButton) findViewById(R.id.appCompatButtonSaveInfoConsultant);
        DateEdit = findViewById(R.id.textInputEditTextDateConsultant);
        spinnerExpertise = findViewById(R.id.SpinnerExpertise);
        spinnerGender = findViewById(R.id.SpinnerGenderConsultant);
    }

    private void initListeners(){
        appCompatButtonSaveInfo.setOnClickListener(this);
        DateEdit.setOnClickListener(this);
    }

    private void initObjects(){
        consultantInfo = new ConsultantInfo();
        databaseHelper = new DatabaseHelper(activity);
        sessionManager = new SessionManager(activity);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonSaveInfoConsultant:
                postDataToSQLite();
                break;

            case R.id.textInputEditTextDateConsultant:
                showDatePickerDialog(v);
                break;
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new InfoRegisActivity_Consultant.DatePickerFragment();
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

    public void postDataToSQLite(){
        consultantInfo.setExpertise(spinnerExpertise.getSelectedItem().toString());
        consultantInfo.setBirthdate(DateEdit.getText().toString());
        consultantInfo.setGender(spinnerGender.getSelectedItem().toString());
        consultantInfo.setConsultantID(sessionManager.getUserID());
        databaseHelper.addConsultantInfo(consultantInfo);
        Intent myIntent = new Intent(InfoRegisActivity_Consultant.this,LoginActivity_Consultant.class);
        InfoRegisActivity_Consultant.this.startActivity(myIntent);
    }

}
