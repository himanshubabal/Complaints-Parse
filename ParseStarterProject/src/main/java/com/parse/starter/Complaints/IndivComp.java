package com.parse.starter.Complaints;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.Navigation.Profile.ChangeDP;
import com.parse.starter.R;

public class IndivComp extends Fragment implements View.OnClickListener{
    EditText title_indiv_comp, description_indiv_comp;
    Button indiv_comp_button;
    ParseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.indiv_comp,container,false);

        title_indiv_comp = (EditText)v.findViewById(R.id.title_indiv_comp_editText);
        description_indiv_comp = (EditText)v.findViewById(R.id.description_indiv_comp_editText);
        indiv_comp_button = (Button)v.findViewById(R.id.indiv_comp_submit_button);
        indiv_comp_button.setOnClickListener(this);
        user = ParseUser.getCurrentUser();

        return v;
    }

    @Override
    public void onClick(View v) {
        ParseObject indiv_comp = new ParseObject("comp_indiv");
        indiv_comp.put("user_id", user);
        indiv_comp.put("title", title_indiv_comp.getText().toString().trim());
        indiv_comp.put("description", description_indiv_comp.getText().toString().trim());
        indiv_comp.put("isResolved", false);
        indiv_comp.put("numOfUp", 0);
        indiv_comp.put("numOfDown", 0);
        indiv_comp.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Toast.makeText(getActivity(), "Complaint Posted Successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.i("parse-indivComp_submit", e.toString());
                }
            }
        });
    }
}
