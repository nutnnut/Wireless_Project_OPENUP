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
 * This class is the activity class for consultant login.
 */
public class LoginActivity_Consultant extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity_Consultant.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;
    private AppCompatTextView textViewLinkLogin;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_consultant);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);
        textViewLinkLogin = (AppCompatTextView) findViewById(R.id.textViewLinkLogin);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
        textViewLinkLogin.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
        sessionManager = new SessionManager(activity);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity for consultant
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity_Consultant.class);
                startActivity(intentRegister);
                break;
            case R.id.textViewLinkLogin:
                // Navigate to user login
                Intent intentLogin = new Intent(getApplicationContext(), LoginActivity_New.class);
                startActivity(intentLogin);
                break;
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {

        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_invalid_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_invalid_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_incorrect_password))) {
            return;
        }

        if (databaseHelper.checkConsultant(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {
            //Check email and password with record in SQLite. If match, allow user to go to main activity
            Consultant loggedInUser =  databaseHelper.getConsultant(textInputEditTextEmail.getText().toString().trim());
            Integer userID = loggedInUser.getId();
            sessionManager.createLoginSession(userID, false);
            Intent myIntent = new Intent(LoginActivity_Consultant.this,InboxActivity_Consultant.class);
            LoginActivity_Consultant.this.startActivity(myIntent);


        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_incorrect_password), Snackbar.LENGTH_LONG).show();
        }

    }
}
