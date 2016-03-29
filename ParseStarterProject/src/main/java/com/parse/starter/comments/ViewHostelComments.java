package com.parse.starter.Comments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.ResolveComplaint.ResolveHostelComplaint;
import com.parse.starter.ResolveComplaint.ResolveSelfComplaint;

import java.util.ArrayList;
import java.util.List;

//todo -> combine upvote and downvote
//todo -> make vite clickable only once
public class ViewHostelComments extends Fragment implements View.OnClickListener{
    TextView title, description, postedBy, postedOn, status, upVotes, downVotes;
    Button upVote_button, downVote_button, add_comment_button;
    ListView comment_listView;
    ParseUser user;
    String parseObjectID;
    ArrayList<String> comments_array_list;
    ArrayAdapter<String> comments_array_adapter;
    ParseQuery<ParseObject> query;
    ParseQuery<ParseObject> query1;
    FrameLayout hostelResolve_frameLayout;
    ArrayList<ParseObject> specialParseUsers;
    Boolean isSpecial;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_hostel_comments,container,false);

        title = (TextView)v.findViewById(R.id.title_comments_textView);
        description = (TextView)v.findViewById(R.id.description_comment_textView);
        postedBy = (TextView)v.findViewById(R.id.postedBy_comments_textView);
        postedOn = (TextView)v.findViewById(R.id.postedOn_comment_textView);
        status = (TextView)v.findViewById(R.id.status_comments_textView);
        upVotes = (TextView)v.findViewById(R.id.upVotes_comments_textView);
        downVotes = (TextView)v.findViewById(R.id.downVote_comment_textView);
        upVote_button = (Button)v.findViewById(R.id.upVote_comment_button);
        downVote_button = (Button)v.findViewById(R.id.downVote_comment_button);
        add_comment_button = (Button)v.findViewById(R.id.add_comment_button);
        upVote_button.setOnClickListener(this);
        downVote_button.setOnClickListener(this);
        add_comment_button.setOnClickListener(this);
        comment_listView = (ListView)v.findViewById(R.id.comment_listView);
        user = ParseUser.getCurrentUser();
        comments_array_list = new ArrayList<>();

        parseObjectID = getArguments().getString("parseObjectID");
        specialParseUsers = new ArrayList<>();

        hostelResolve_frameLayout = (FrameLayout)v.findViewById(R.id.hostel_resolve_frameLayout);
        hostelResolve_frameLayout.setOnClickListener(this);

        query = new ParseQuery<ParseObject>("comp_hostel");
        query.whereEqualTo("objectId", parseObjectID);

        addTextFields();

        query1 = new ParseQuery<ParseObject>("comm_hostel");


        //Special Parse Users
        Log.i("parse-isSpecial", "1");
        ParseQuery<ParseObject> specialUsers = new ParseQuery<ParseObject>("SuperUser");
        isSpecial = false;
        specialUsers.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Log.i("parse-isSpecial", "2");
                if(e == null){
                    Log.i("parse-isSpecial", "3");
                    for (ParseObject object : objects){
                        Log.i("parse-isSpecial", "4");
                        specialParseUsers.add(object);
                        if(object.get("user_id").equals(user.getObjectId().toString())){
                            isSpecial = true;
                            Log.i("parse-isSpecial", "7");
                        }
                    }
                }
                else {
                    Log.i("parse-isSpecial", "5"+e.toString());
                }
            }
        });
        Log.i("parse-isSpecial", "6");
//        isSpecial = false;
//        for (ParseObject object : specialParseUsers){
//            Log.i("parse-isSpecial", "7");
//            if(object.get("user_id").equals(user.getObjectId())){
//                isSpecial = true;
//            }
//        }


        return v;
    }

    //// TODO: 3/29/16 -> posted at, date is null, correct it. 
    public void addTextFields(){
        Log.i("parse-comment", "test-a");

        Log.i("parse-comment", "test-b");
        Log.i("parse-comment", "test-c");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Log.i("parse-comment", "test-d");
                if(e == null){//as we know, only one result will show.
                    ParseObject object = objects.get(0);
                    Log.i("parse----", object.getObjectId());

                    title.setText(object.get("title").toString());
                    Log.i("parse-comment", "test-1" + object.get("title").toString());

                    description.setText(object.get("description").toString());
                    Log.i("parse-comment", "test-2" + object.get("description").toString());

                    postedBy.setText(object.get("username").toString());
                    Log.i("parse-comment", "test-3" + object.get("username").toString());

                    postedOn.setText(String.valueOf(object.getDate("createdAt")));
                    Log.i("parse-comment", "test-4" + object.get("createdAt"));

                    if(object.getBoolean("isResolved")){
                        status.setText("Resolved");
                        Log.i("parse-comment", "test-5" + object.getBoolean("isResolved"));

                    }
                    else {
                        status.setText("Un-Resolved");
                        Log.i("parse-comment", "test-6" + object.getBoolean("isResolved"));

                    }
                    upVotes.setText(String.valueOf(object.getInt("numOfUp")));
                    Log.i("parse-comment", "test-7" + object.getInt("numOfUp"));

                    downVotes.setText(String.valueOf(object.getInt("numOfDown")));
                    Log.i("parse-comment", "test-8" + object.getInt("numOfDown"));


                    commentsList(object);

                }
                else {
                    Log.i("parse-comment", "test-e" + e.toString());
                }
            }
        });
    }

    public void commentsList(ParseObject object){
//        Log.i("parse---", comment_object.getObjectId());
        query1.whereEqualTo("comp_id", object);
        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size() != 0) {
                        Log.i("parse-self_comp_zzz", String.valueOf((objects.size())));
                        for (ParseObject object : objects) {
                            String body = object.get("body").toString();
                            Log.i("parse-self_dimagKharab", body);
                            comments_array_list.add(body);
//                            parseObjectArrayList.add(object);

                        }
                        Log.i("parse-self_comp_zzz", "out of for loop");

                        comments_array_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, comments_array_list);
                        Log.i("parse-self_comp_zzz", "test-1");

                        comment_listView.setAdapter(comments_array_adapter);
                        Log.i("parse-self_comp_zzz", "test-2");

//                        hostel_comp_listView.setOnItemClickListener(ViewHostelComp.this);

                    }
                    else {
                        Toast.makeText(getActivity(), "No comments for this complaint", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Log.i("parse-view_self_comp", e.toString());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_comment_button){
            Log.i("parse-hostel-comm", "1");
            addCommentHostel addCommentHostel = new addCommentHostel();
            Bundle parseObjID = new Bundle();
            parseObjID.putString("parseObjectID", parseObjectID);
            addCommentHostel.setArguments(parseObjID);
            Log.i("parse-hostel-comm", "2");
            FragmentTransaction fragmentT = getFragmentManager().beginTransaction();
            fragmentT.replace(R.id.frame, addCommentHostel);
            fragmentT.addToBackStack(null);
            fragmentT.commit();
            Log.i("parse-hostel-comm", "3");
        }
        else if (v.getId() == R.id.upVote_comment_button) {
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e == null){
                        ParseObject object = objects.get(0);
                        object.increment("numOfUp");
                        object.saveInBackground();
                    }
                    else {
                        Log.i("parse-view_hostel_comm", e.toString());
                    }
                }
            });
        }
        else if (v.getId() == R.id.downVote_comment_button){
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e == null){
                        ParseObject object = objects.get(0);
                        object.increment("numOfDown");
                        object.saveInBackground();
                    }
                    else {
                        Log.i("parse-view_hostel_comm", e.toString());
                    }
                }
            });
        }
        else if(v.getId() == R.id.hostel_resolve_frameLayout){
            Log.i("parse-isSpecial", "8");

            if (user.getInt("userType") <= -1){
                Log.i("parse-isSpecial", "9");
                Log.i("parse-isSpecial", String.valueOf(true));
                ResolveHostelComplaint resolveHostelComplaint = new ResolveHostelComplaint();
                Bundle parseObjID = new Bundle();
                parseObjID.putString("parseObjectID", parseObjectID);
                Log.i("parse-###", parseObjectID);
                resolveHostelComplaint.setArguments(parseObjID);

                FragmentTransaction fragmentT = getFragmentManager().beginTransaction();
                fragmentT.replace(R.id.frame, resolveHostelComplaint);
                fragmentT.addToBackStack(null);
                fragmentT.commit();
                Log.i("parse-isSpecial", "10");
            }
        }
    }
}
