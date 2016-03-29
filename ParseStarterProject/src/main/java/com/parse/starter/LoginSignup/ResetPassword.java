package com.parse.starter.LoginSignup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
public class ResetPassword extends Fragment implements View.OnClickListener{
    EditText oldPass, newPass, newPassAgain;
    Button submitPass;
    ParseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.reset_password,container,false);
        oldPass = (EditText)v.findViewById(R.id.oldPass_EditText_changePass);
        newPass = (EditText)v.findViewById(R.id.newPass_EditText_changePass);
        newPassAgain = (EditText)v.findViewById(R.id.newPass_repeat_EditText_changePass);
        submitPass = (Button)v.findViewById(R.id.change_password_button);
        submitPass.setOnClickListener(this);
        user = ParseUser.getCurrentUser();

        return v;
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
                                Toast.makeText(getActivity(), "Password changed successfully \n Redirecting to Login Page", Toast.LENGTH_SHORT).show();
                                LogOut.logMeOut();
                                Intent i = new Intent(getActivity(), Login.class);
                                startActivity(i);
                            }
                        }
                    });
                }
                else {
                    Log.i("parse-changePWD", "new passwords don't match");
                }
            }
            else {
                Log.i("parse-changePWD", "invalid Old Password");
            }
        }
    }
}
