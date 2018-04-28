package com.example.nut.wireless_project_openup;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.text.ParseException;

import helpers.InputValidation;
import helpers.SessionManager;
import model.ConsultantInfo;
import model.Information;
import sql.DatabaseHelper;

/**
 * This class is for collecting consultant profile information, this is not optional
 */
public class InfoRegisActivity_Consultant extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = InfoRegisActivity_Consultant.this;

    private NestedScrollView nestedScrollView;

    private AppCompatButton appCompatButtonSaveInfo;


    private TextInputEditText textInputEditTextName;
    private TextInputLayout textInputLayoutName;
    private static TextInputEditText DateEdit;
    private Spinner spinnerExpertise;
    private Spinner spinnerGender;

    private ConsultantInfo consultantInfo;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;
    private InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_regis_consultant);

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutNameConsultant);
        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextNameConsultant);
        appCompatButtonSaveInfo = (AppCompatButton) findViewById(R.id.appCompatButtonSaveInfoConsultant);
        DateEdit = findViewById(R.id.textInputEditTextDateConsultant);
        spinnerExpertise = findViewById(R.id.SpinnerExpertise);
        spinnerGender = findViewById(R.id.SpinnerGenderConsultant);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners(){
        appCompatButtonSaveInfo.setOnClickListener(this);
        DateEdit.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects(){
        consultantInfo = new ConsultantInfo();
        databaseHelper = new DatabaseHelper(activity);
        sessionManager = new SessionManager(activity);
        inputValidation = new InputValidation(activity);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonSaveInfoConsultant:
                postDataToSQLite();
                Intent intent = new Intent(InfoRegisActivity_Consultant.this, InboxActivity_Consultant.class);
                startActivity(intent);
                break;

            case R.id.textInputEditTextDateConsultant:
                showDatePickerDialog(v);
                break;
        }
    }

    /**
     * This method is to show dialog for picking consultant birthdate
     * @param v
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new InfoRegisActivity_Consultant.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * This inner class is to define date picker dialog
     */
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

        /**
         * Set text in DateEdit to selected date
         * @param view
         * @param year
         * @param month
         * @param day
         */
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            DateEdit.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

    /**
     * This method is to insert the information to SQLite through databaseHelper
     *
     */
    public void postDataToSQLite(){
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_invalid_name))) {
            return;
        }
        consultantInfo.setName(textInputEditTextName.getText().toString().trim());
        consultantInfo.setExpertise(spinnerExpertise.getSelectedItem().toString());
        consultantInfo.setBirthdate(DateEdit.getText().toString());
        consultantInfo.setGender(spinnerGender.getSelectedItem().toString());
        consultantInfo.setConsultantID(sessionManager.getUserID());
        databaseHelper.addConsultantInfo(consultantInfo);
        Intent myIntent = new Intent(InfoRegisActivity_Consultant.this,LoginActivity_Consultant.class);
        InfoRegisActivity_Consultant.this.startActivity(myIntent);
    }

}
