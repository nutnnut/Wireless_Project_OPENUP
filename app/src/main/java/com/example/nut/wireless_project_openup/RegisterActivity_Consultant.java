package com.example.nut.wireless_project_openup;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import helpers.InputValidation;
import helpers.SessionManager;
import model.Consultant;
import model.User;
import sql.DatabaseHelper;

/**
 * This activity is to register consultant account to SQLite
 */
public class RegisterActivity_Consultant extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity_Consultant.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;
    private Consultant consultant;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_consultant);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollViewConsultant);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmailConsultant);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPasswordConsultant);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPasswordConsultant);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmailConsultant);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPasswordConsultant);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPasswordConsultant);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegisterConsultant);

        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        sessionManager = new SessionManager(activity);
        consultant = new Consultant();
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonRegisterConsultant:
                postDataToSQLite();
                break;

            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {

        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_invalid_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_invalid_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_invalid_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_unmatched_password))) {
            return;
        }

        if (!databaseHelper.checkConsultant(textInputEditTextEmail.getText().toString().trim())) {

            //get texts from inputs and add to SQLite
            consultant.setEmail(textInputEditTextEmail.getText().toString().trim());
            consultant.setPassword(textInputEditTextPassword.getText().toString().trim());
            databaseHelper.addConsultant(consultant);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_sign_up), Snackbar.LENGTH_LONG).show();

            //Create session (Preference) and record consultant ID to the session
            Consultant loggedInUser =  databaseHelper.getConsultant(textInputEditTextEmail.getText().toString().trim());
            Integer userID = loggedInUser.getId();
            sessionManager.createLoginSession(userID, false);
            emptyInputEditText();

            //Move on to information register for consultant
            Intent myIntent = new Intent(RegisterActivity_Consultant.this,InfoRegisActivity_Consultant.class);
            RegisterActivity_Consultant.this.startActivity(myIntent);

        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_exist_email), Snackbar.LENGTH_LONG).show();
        }


    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }

}
