package com.parse.starter.ResolveComplaint;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.Comments.ViewHostelComments;
import com.parse.starter.MainActivity;
import com.parse.starter.R;

import java.util.ArrayList;
import java.util.List;

public class ResolveHostelComplaint extends Fragment implements View.OnClickListener{
    String parseObjectID;
    Button mark_resolved_hostelComp_Button;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.resolve_hostel_complaint,container,false);

        mark_resolved_hostelComp_Button = (Button)v.findViewById(R.id.mark_resolved_hostelComp_button);
        mark_resolved_hostelComp_Button.setOnClickListener(this);

        parseObjectID = getArguments().getString("parseObjectID");
        Log.i("parse-###", parseObjectID);




        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.mark_resolved_hostelComp_button){
            ParseQuery<ParseObject> query = new ParseQuery<>("comp_hostel");
            query.whereEqualTo("objectId", parseObjectID);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    Log.i("parse-###", String.valueOf(objects.size()));
                    if (e == null){
                        if (objects.size() != 0) {
                            ParseObject object = objects.get(0);
                            object.put("isResolved", true);
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Toast.makeText(getActivity(), "Complaint marked as Resolved", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);

                                    } else {
                                        Log.i("parse-####", e.toString());
                                    }
                                }
                            });
                        }
                        else {
                            //
                        }
                    }
                    else {
                        Log.i("parse-####", e.toString());
                    }
                }
            });
        }
        else {
            //
        }
    }
}
