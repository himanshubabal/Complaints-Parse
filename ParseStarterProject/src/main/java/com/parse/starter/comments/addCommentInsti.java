package com.parse.starter.Comments;

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
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;
import com.parse.starter.ViewComplaints.ViewInstiComp;

import java.util.List;

public class addCommentInsti extends Fragment implements View.OnClickListener{
    EditText addInstiCommentEditText;
    Button addInstiCommentButton;
    String parseObjectID;
    ParseUser user;
    ParseObject comment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_comment_insti,container,false);
        Log.i("parse-hostel-comm", "4");
        addInstiCommentEditText = (EditText)v.findViewById(R.id.add_insti_comment_editText);
        addInstiCommentButton = (Button)v.findViewById(R.id.add_insti_comment_button);
        addInstiCommentButton.setOnClickListener(this);

        parseObjectID = getArguments().getString("parseObjectID");
        user = ParseUser.getCurrentUser();
        Log.i("parse-hostel-comm", "5");
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("comp_insti");
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
        if (v.getId() == R.id.add_insti_comment_button){
            Log.i("parse-hostel-comm", "7");
            final ParseObject newComment = new ParseObject("comm_insti");
            newComment.put("body", addInstiCommentEditText.getText().toString());
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
                        ViewInstiComp viewInstiComp = new ViewInstiComp();
                        FragmentTransaction fragmentT = getFragmentManager().beginTransaction();
                        fragmentT.replace(R.id.frame, viewInstiComp);
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
