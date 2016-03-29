package com.parse.starter.Navigation.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.LoginSignup.ResetPassword;
import com.parse.starter.R;

import java.util.List;

public class UserProfile extends Fragment implements View.OnClickListener{
    ImageView userDP;
    Button changePwd, changeDP;
    ParseUser user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_profile,container,false);

        userDP = (ImageView)v.findViewById(R.id.user_profile_pic);
        changePwd = (Button)v.findViewById(R.id.change_password_button);
        changeDP = (Button)v.findViewById(R.id.change_dp_button);
        changeDP.setOnClickListener(this);
        changePwd.setOnClickListener(this);

        user = ParseUser.getCurrentUser();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("profilePic");
        query.whereEqualTo("username", user.getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null) {
                    if(objects.size() != 0) {
                        ParseObject object = objects.get(0);
                        ParseFile file = (ParseFile) object.get("profile_pic");
                        if (file != null) {
                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    Bitmap img = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    userDP.setImageBitmap(img);
                                }
                            });
                        } else {
                            userDP.setImageDrawable(getResources().getDrawable(R.drawable.default_user_dp));
                        }
                    }
                    else {
                        userDP.setImageDrawable(getResources().getDrawable(R.drawable.default_user_dp));
                    }
                }
                else {
                    Log.i("parse-user_Profile", e.toString());
                }
            }
        });

        return v;
    }

    public void updateDP(){

    }


    //todo -> update the DP from gallary / camera
    //todo -> while updatitng the dp, also add username in the feild
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_dp_button:
                Fragment changeDP = new ChangeDP();
                FragmentTransaction fragmentT = getFragmentManager().beginTransaction();
                fragmentT.replace(R.id.frame, changeDP);
                fragmentT.addToBackStack(null);
                fragmentT.commit();
                updateDP();
                break;
            case R.id.change_password_button:
                Intent intent = new Intent(getActivity(), ResetPassword.class);
                startActivity(intent);
        }
    }
}
