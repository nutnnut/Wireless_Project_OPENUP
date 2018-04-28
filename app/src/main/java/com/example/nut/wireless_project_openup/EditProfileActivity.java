package com.example.nut.wireless_project_openup;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import helpers.InputValidation;
import helpers.SessionManager;
import model.Information;
import sql.DatabaseHelper;

/**
 * This activity allows user to view and edit their profile information
 */
public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = EditProfileActivity.this;

    private NestedScrollView nestedScrollView;

    private Button buttonUpdate;
    private Button buttonCancel;


    private TextInputLayout textInputLayoutName;
    private TextInputEditText textInputEditTextName;
    private static TextInputEditText DateEdit;
    private Spinner spinnerOccupation;
    private Spinner spinnerMedicalCondition;
    private Spinner spinnerGender;

    private Information information;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;
    private InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initViews();
        initListeners();
        initObjects();
        initInfo();

    }

    /**
     * This method is to initialize views
     */
    private void initViews(){
        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutNameEdit);
        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextNameEdit);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        DateEdit = findViewById(R.id.textInputEditTextDateEdit);
        spinnerOccupation = findViewById(R.id.SpinnerOccupationEdit);
        spinnerMedicalCondition = findViewById(R.id.SpinnerConditionEdit);
        spinnerGender = findViewById(R.id.SpinnerGenderEdit);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners(){
        buttonUpdate.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        DateEdit.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        sessionManager = new SessionManager(activity);
        inputValidation = new InputValidation(activity);
        Integer userID = sessionManager.getUserID();
        //get user information from database through databaseHelper using user ID
        information = databaseHelper.getInfo(userID);
    }

    /**
     * This method is to set views to contain information that is selected from SQLite
     */
    private void initInfo(){

        textInputEditTextName.setText(information.getDisplayName());

        ArrayAdapter occupationAdapter = (ArrayAdapter) spinnerOccupation.getAdapter();
        int occupationPosition = occupationAdapter.getPosition(information.getOccupation());
        spinnerOccupation.setSelection(occupationPosition);

        ArrayAdapter medicalConditionAdapter = (ArrayAdapter) spinnerMedicalCondition.getAdapter();
        int medicalPosition = medicalConditionAdapter.getPosition(information.getMedicalCondition());
        spinnerMedicalCondition.setSelection(medicalPosition);

        DateEdit.setText(information.getBirthdate());

        ArrayAdapter genderAdapter = (ArrayAdapter) spinnerGender.getAdapter();
        int genderPosition = genderAdapter.getPosition(information.getGender());
        spinnerGender.setSelection(genderPosition);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonUpdate:
                updateInfoSQL();
                break;

            case R.id.textInputEditTextDateEdit:
                showDatePickerDialog(v);
                break;

            case R.id.buttonCancel:
                finish();
                break;
        }
    }

    /**
     * This method is to show dialog for picking user birthdate
     * @param v
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new EditProfileActivity.DatePickerFragment();
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
     * Update the user information with current input values
     */
    private void updateInfoSQL(){
        information.setDisplayName(textInputEditTextName.getText().toString().trim());
        information.setOccupation(spinnerOccupation.getSelectedItem().toString());
        information.setMedicalCondition(spinnerMedicalCondition.getSelectedItem().toString());
        information.setBirthdate(DateEdit.getText().toString());
        information.setGender(spinnerGender.getSelectedItem().toString());
        information.setUserID(sessionManager.getUserID());
        databaseHelper.updateInfo(information);
    }


}
