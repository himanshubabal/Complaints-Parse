package com.parse.starter.LoginSignup;


import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.starter.R;

import java.util.List;

public class SignUp extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, Spinner.OnItemSelectedListener{
    EditText email_Id, entry_No, full_Name, password, user_name;
    RadioButton student, faculty, staff;
    String hostel;
    Button signUpButton;
    int userType;
    RadioGroup radioGroup;
    Spinner hostel_dropDown;
    ArrayAdapter<CharSequence> adapter;

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
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_signup);
        student = (RadioButton) findViewById(R.id.student_radioButton);
        faculty = (RadioButton) findViewById(R.id.faculty_radioButton);
        staff = (RadioButton) findViewById(R.id.staff_radioButton);
        signUpButton = (Button) findViewById(R.id.signUp_button);
        radioGroup.setOnCheckedChangeListener(this);

        hostel_dropDown = (Spinner) findViewById(R.id.hostel_selection_spinner);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.hostel_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hostel_dropDown.setAdapter(adapter);
        hostel_dropDown.setOnItemSelectedListener(this);
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

        user.put("hostel", hostel.toLowerCase());
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


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.student_radioButton){
            userType = 0;
            Log.i("parse-signup", String.valueOf(userType));
        }
        else if (checkedId == R.id.faculty_radioButton){
            userType = 1;
            Log.i("parse-signup", String.valueOf(userType));
        }
        else if (checkedId == R.id.staff_radioButton){
            userType = 2;
            Log.i("parse-signup", String.valueOf(userType));
        }
        else {
            //
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                hostel = "karakoram";
                break;
            case 1:
                hostel = "aravali";
                break;
            case 2:
                hostel = "girnar";
                break;
            case 3:
                hostel = "jwalamukhi";
                break;
            case 4:
                hostel = "kumaon";
                break;
            case 5:
                hostel = "nilgiri";
                break;
            case 6:
                hostel = "shivalik";
                break;
            case 7:
                hostel = "satpura";
                break;
            case 8:
                hostel = "udaigiri";
                break;
            case 9:
                hostel = "vindhyachal";
                break;
            case 10:
                hostel = "zanskar";
                break;
            case 11:
                hostel = "kailash";
                break;
            case 12:
                hostel = "Himadri";
                break;
            case 13:
                hostel = "not_applicable";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
