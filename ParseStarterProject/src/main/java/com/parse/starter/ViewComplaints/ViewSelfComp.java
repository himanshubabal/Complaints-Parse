package com.parse.starter.ViewComplaints;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;

import java.util.ArrayList;
import java.util.List;

public class ViewSelfComp extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{
    ListView self_comp_listView;
    ParseUser user;
    ArrayList<String> self_comp_array;
    ArrayAdapter<String> self_comp_arrayAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_self_comp,container,false);
        user = ParseUser.getCurrentUser();
        self_comp_listView = (ListView)v.findViewById(R.id.self_comp_listView);
        self_comp_array = new ArrayList<String>();

        generateCompList();

        return v;
    }

    public void generateCompList(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("comp_indiv");
        query.whereEqualTo("user_id", user);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size() != 0) {
                        Log.i("parse-self_comp_zzz", String.valueOf((objects.size())));
                        int i = 0;
                        for (ParseObject object : objects) {
                            String title = object.get("title").toString();
                            String description = object.get("description").toString();
                            Boolean isResolved = object.getBoolean("isResolved");
                            int numOfUp = object.getInt("numOfUp");
                            int numOfDown = object.getInt("numOfDown");
                            String status;
                            if(isResolved){
                                status = "Resolved";
                            }
                            else {
                                status = "Un-Resolved";
                            }

                            self_comp_array.add("Title : " + title + "\n" + "Description : " + description + "\n" +
                                                "Status : " + status + "\n" + "Up-Votes : " + numOfUp + "\n" + "Down-Votes : " + numOfDown);
                        }
                        Log.i("parse-self_comp_zzz", "out of for loop");

                        self_comp_arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, self_comp_array);
                        Log.i("parse-self_comp_zzz", "test-1");

                        self_comp_listView.setAdapter(self_comp_arrayAdapter);
                        Log.i("parse-self_comp_zzz", "test-2");

                    }
                    else {
                        Toast.makeText(getActivity(), "No Complaints Posted by you", Toast.LENGTH_LONG).show();
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

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
