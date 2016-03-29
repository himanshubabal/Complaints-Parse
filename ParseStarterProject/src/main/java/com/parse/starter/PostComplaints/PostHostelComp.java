package com.parse.starter.PostComplaints;

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

import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;

public class PostHostelComp extends Fragment implements View.OnClickListener{
    EditText title_hostel_comp, description_hostel_comp;
    Button hostel_comp_button;
    ParseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.post_hostel_comp,container,false);

        title_hostel_comp = (EditText)v.findViewById(R.id.title_hostel_comp_editText);
        description_hostel_comp = (EditText)v.findViewById(R.id.description_hostel_comp_editText);
        hostel_comp_button = (Button)v.findViewById(R.id.hostel_comp_submit_button);
        hostel_comp_button.setOnClickListener(this);
        user = ParseUser.getCurrentUser();

        return v;
    }

    @Override
    public void onClick(View v) {
        ParseObject indiv_comp = new ParseObject("comp_hostel");
        String hostel = user.get("hostel").toString();
        indiv_comp.put("user_id", user);
        indiv_comp.put("title", title_hostel_comp.getText().toString().trim());
        indiv_comp.put("description", description_hostel_comp.getText().toString().trim());
        indiv_comp.put("isResolved", false);
        indiv_comp.put("numOfUp", 0);
        indiv_comp.put("numOfDown", 0);
        indiv_comp.put("hostel", hostel);
        indiv_comp.put("username", user.getUsername());

        ParseACL parseACL = new ParseACL();

        //todo -> set hostel warden write access here
        parseACL.setPublicReadAccess(true);
        parseACL.setWriteAccess(user, true);
        indiv_comp.setACL(parseACL);
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
