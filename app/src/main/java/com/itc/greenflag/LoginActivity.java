package com.itc.greenflag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    public final static String SHARED_PREFS ="sharedPrefs";
    public final static String TEXT = "";

    private String email;
    private String passwordOne;
    private String passwordTwo;

    //Views
    private Button mButton;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private EditText mEditTextPasswordTwo;
    private View mErrorEmail;
    private View mErrorPassword;
    private View mErrorPasswordTwo;

    //Shared Pref
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mButton = findViewById(R.id.button);
        mEditTextEmail = findViewById(R.id.editTextEmail);
        mEditTextPassword = findViewById(R.id.editTextPasswordOne);
        mEditTextPasswordTwo= findViewById(R.id.editTextPasswordTwo);
        mErrorEmail = findViewById(R.id.errorEmail);
        mErrorPassword = findViewById(R.id.errorPasswordOne);
        mErrorPasswordTwo = findViewById(R.id.errorPasswordTwo);

        mButton.setOnClickListener( view ->{
            saveContent();
            emailCheck();
            loadData();
            passwordCheck();
        });

        loadData();
        updateViews();

    }

    private void saveContent(){
        sharedPref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putString(TEXT, mEditTextEmail.getText().toString());
        editor.apply();
    }

    private void loadData(){
        sharedPref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        email = sharedPref.getString(TEXT, "");
    }

    private void updateViews(){
        mEditTextEmail.setText(email);
    }

    private void emailCheck(){
        mErrorEmail.setVisibility(View.INVISIBLE);

        if(email.equals(mEditTextEmail.getText().toString())){
            mErrorEmail.setVisibility(View.VISIBLE);
        }

    }

    private void passwordCheck(){

        boolean validPassword = isValidPassword(mEditTextPassword.getText().toString());
        boolean validPassword2 = isValidPassword(mEditTextPasswordTwo.getText().toString());

        if(validPassword && validPassword2){
            mErrorPassword.setVisibility(View.INVISIBLE);
        }

        if(mEditTextPassword.getText().toString().equals(mEditTextPasswordTwo.getText().toString())){
            mErrorPasswordTwo.setVisibility(View.INVISIBLE);
        }

        if(!validPassword || !validPassword2){
            mErrorPassword.setVisibility(View.VISIBLE);
        }

        if(!mEditTextPassword.getText().toString().equals(mEditTextPasswordTwo.getText().toString())){
            mErrorPasswordTwo.setVisibility(View.VISIBLE);
        }

    }

    public static boolean isValidPassword(String password)
    {
        boolean isValid = true;
        if (password.length() > 15 || password.length() < 8)
        {
            System.out.println("Password must be less than 20 and more than 8 characters in length.");
            isValid = false;
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars ))
        {
            System.out.println("Password must have atleast one uppercase character");
            isValid = false;
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars ))
        {
            System.out.println("Password must have atleast one lowercase character");
            isValid = false;
        }
        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers ))
        {
            System.out.println("Password must have atleast one number");
            isValid = false;
        }
        String specialChars = "(.*[@,#,$,%,*].*$)";
        if (!password.matches(specialChars ))
        {
            System.out.println("Password must have atleast one special character among @#$%");
            isValid = false;
        }
        return isValid;
    }
}