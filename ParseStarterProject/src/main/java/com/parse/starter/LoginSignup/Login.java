package com.parse.starter.LoginSignup;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.starter.R;

public class Login extends AppCompatActivity{
    EditText userName;
    EditText password;
    Button logIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        userName = (EditText) findViewById(R.id.userName_EditText);
        password = (EditText) findViewById(R.id.password_editText);
        logIn = (Button) findViewById(R.id.logIn_button);
    }


    //Log user In using ID and Password
    public void logInMethod(View view){
        ParseUser.logInInBackground(userName.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null){
                    Log.i("parse-LogIn", "Log In successful");
                }
                else {
                    Log.i("parse-LogIn", e.toString());
                }
            }
        });
    }
}
