package com.parse.starter.ResolveComplaint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.parse.starter.R;

import java.util.ArrayList;
import java.util.List;

public class ResolveSelfComplaint extends Fragment implements View.OnClickListener{
    String parseObjectID;
    Button mark_resolved_selfComp_Button;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.resolve_self_complaint,container,false);

        mark_resolved_selfComp_Button = (Button)v.findViewById(R.id.mark_resolved_selfComp_button);
        mark_resolved_selfComp_Button.setOnClickListener(this);

        parseObjectID = getArguments().getString("parseObjectID");




        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.mark_resolved_selfComp_button){
            ParseQuery<ParseObject> query = new ParseQuery<>("comp_indiv");
            query.whereEqualTo("objectId", parseObjectID);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null){
                        ParseObject object = objects.get(0);
                        object.put("isResolved", true);
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null){
                                    Toast.makeText(getActivity(), "Complaint marked as Resolved", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    //
                                }
                            }
                        });
                    }
                    else {
                        //
                    }
                }
            });
        }
        else {
            //
        }
    }
}
