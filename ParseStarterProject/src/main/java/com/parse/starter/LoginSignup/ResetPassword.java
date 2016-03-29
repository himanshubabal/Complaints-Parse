package com.parse.starter.LoginSignup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;

//TODO -> set restrictions on new password
public class ResetPassword extends AppCompatActivity implements View.OnClickListener{
    EditText oldPass, newPass, newPassAgain;
    Button submitPass;
    ParseUser user;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
        oldPass = (EditText)findViewById(R.id.oldPass_EditText_changePass);
        newPass = (EditText)findViewById(R.id.newPass_EditText_changePass);
        newPassAgain = (EditText)findViewById(R.id.newPass_repeat_EditText_changePass);
        submitPass = (Button)findViewById(R.id.change_password_button);
        submitPass.setOnClickListener(this);
        user = ParseUser.getCurrentUser();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.change_password_button){
            if(oldPass.getText().toString().equals(user.get("passwordCopy"))){
                if(newPass.getText().toString().equals(newPassAgain.getText().toString())){
                    user.setPassword(newPass.getText().toString());
                    user.put("passwordCopy", newPass.getText().toString());
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e != null){
                                Log.i("parse-changePWD", e.toString());
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Password changed successfully \n Redirecting to Login Page", Toast.LENGTH_SHORT).show();
                                LogOut.logMeOut();
                                Intent i = new Intent(getApplicationContext(), Login.class);
                                startActivity(i);
                            }
                        }
                    });
                }
                else {
                    Log.i("parse-changePWD", "new passwords don't match");
                    Toast.makeText(getApplicationContext(),"new passwords don't match", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Log.i("parse-changePWD", "invalid Old Password");
                Toast.makeText(getApplicationContext(),"invalid Old Password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
