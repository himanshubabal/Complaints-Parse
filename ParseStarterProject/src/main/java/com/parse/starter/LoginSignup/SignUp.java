package com.parse.starter.LoginSignup;


import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.starter.R;

import java.util.List;

public class SignUp extends AppCompatActivity{
    EditText email_Id, entry_No, full_Name, password, hostel, user_name;
    RadioButton student, faculty, staff;
    Button signUpButton;
    int userType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        ParseUser.logOut();

        //todo -> Email format => kerebrosID@iitd.ac.in
        email_Id = (EditText) findViewById(R.id.email_id_editText);
        entry_No = (EditText) findViewById(R.id.entry_number_editText);
        full_Name = (EditText) findViewById(R.id.full_name_editText);
        user_name = (EditText) findViewById(R.id.userName_editText_signup);
        password = (EditText) findViewById(R.id.password_editText_signup);
        hostel = (EditText) findViewById(R.id.hostel_editText);

        //radio button not working
        student = (RadioButton) findViewById(R.id.student_radioButton);
        if(student.isChecked()){
            userType = 0;
        }
        faculty = (RadioButton) findViewById(R.id.faculty_radioButton);
        if(faculty.isChecked()){
            userType = 1;
        }
        staff = (RadioButton) findViewById(R.id.staff_radioButton);
        if(staff.isChecked()){
            userType = 2;
        }

        signUpButton = (Button) findViewById(R.id.signUp_button);

    }

    //by default - Users in parse have unique Email-ID and UserName.
    //so, no need to check it in the code
    public void signUpMethod(View view){
        //TODO -> after successful signUp, prompt user to verify email ID
        //TODO -> condition on radio button

        ParseUser user = new ParseUser();

        user.setUsername(user_name.getText().toString());
        user.setPassword(password.getText().toString());
        user.put("passwordCopy", password.getText().toString());

        if(isValidEmail(email_Id.getText().toString())) {
            user.setEmail(email_Id.getText().toString());
        }
        else {
            Log.i("parse-signUp", "email auth failed");
            return;
        }

        user.put("hostel", hostel.getText().toString());
        user.put("name", full_Name.getText().toString());
        user.put("userType", userType);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){//User successfully Signed Up
                    Log.i("parse-signUp", "SignUp Successful");
                }
                else {//error in signing up
                    Log.i("parse-signUp", e.toString());
                }
            }
        });
    }

    public Boolean isValidEmail(String s){
        //TODO -> add additional checks on mail id, such as its length, etc.
        if(s.contains("iitd.ac.in") && s.contains("@")){
            return true;
        }
        else {
            return false;
        }
    }

    //TODO -> if possible, check availability while user types the user name, ie. while still typing other feilds.
    //hint - use textWatcher and call API every time a character is added/removed
    //this method isn't working
    public int userNameAvailable(String string){
        final int[] available = new int[1];
        ParseQuery<ParseUser> userNameQuery = ParseQuery.getQuery("User");
        userNameQuery.whereEqualTo("username", string);
        userNameQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(objects.size() == 0){
                    available[0] = objects.size();
                }
                else {
                    available[0] = 0;
                }
            }
        });
        return available[0];
    }
}
