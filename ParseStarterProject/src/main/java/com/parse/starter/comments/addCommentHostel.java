package com.parse.starter.comments;

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

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;
import com.parse.starter.ViewComplaints.ViewHostelComp;

import java.util.List;

public class addCommentHostel extends Fragment implements View.OnClickListener{
    EditText addHostelCommentEditText;
    Button addHostelCommentButton;
    String parseObjectID;
    ParseUser user;
    ParseObject comment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_comment_hostel,container,false);
        Log.i("parse-hostel-comm", "4");
        addHostelCommentEditText = (EditText)v.findViewById(R.id.add_hostel_comment_editText);
        addHostelCommentButton = (Button)v.findViewById(R.id.add_hostel_comment_button);
        addHostelCommentButton.setOnClickListener(this);

        parseObjectID = getArguments().getString("parseObjectID");
        user = ParseUser.getCurrentUser();
        Log.i("parse-hostel-comm", "5");
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("comp_hostel");
        query.whereEqualTo("objectId", parseObjectID);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    comment = objects.get(0);
                    Log.i("parse-hostel-comm", "6");
                }
            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_hostel_comment_button){
            Log.i("parse-hostel-comm", "7");
            final ParseObject newComment = new ParseObject("comm_hostel");
            newComment.put("body", addHostelCommentEditText.getText().toString());
            Log.i("parse-hostel-comm", "8");
            ParseACL acl = new ParseACL();
            acl.setPublicReadAccess(true);
            newComment.setACL(acl);
            newComment.put("user_id", user);
            newComment.put("comp_id", comment);
            newComment.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        Log.i("parse-hostel-comm", "9");
                        ViewHostelComp viewHostelComp = new ViewHostelComp();
                        FragmentTransaction fragmentT = getFragmentManager().beginTransaction();
                        fragmentT.replace(R.id.frame, viewHostelComp);
                        fragmentT.addToBackStack(null);
                        fragmentT.commit();
                        Log.i("parse-hostel-comm", "10");
                    }
                }
            });

        }
        else {

        }
    }
}
