package com.parse.starter.Utilities;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.LoginSignup.LogOut;
import com.parse.starter.MainActivity;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class UploadImageToParse {
    static ParseUser user;

    public static int uploadToParse(final Bitmap bitmap){
        final int[] value = new int[1];
        user = ParseUser.getCurrentUser();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
        final byte[] byteArray = byteArrayOutputStream.toByteArray();


        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("profilePic");
        query.whereEqualTo("username", user.getUsername().toString());
        Log.i("parse-test", "6");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null) {
                    if (objects.size() == 0) {
                        ParseFile file = new ParseFile("userDP.png", byteArray);

                        ParseObject object = new ParseObject("profilePic");

                        object.put("username", user.getUsername().toString());
                        object.put("profile_pic", file);
                        object.put("user_ID", ParseUser.getCurrentUser());
                        Log.i("parse-test", "7");

                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.i("parse-test", "8");
                                    value[0] = 1;
                                } else {
                                    Log.i("parse-uploadImgToParse", e.toString());
                                    value[0] = -1;
                                }
                            }
                        });
                    } else {
                        ParseFile file = new ParseFile("userDP.png", byteArray);

                        ParseObject object = objects.get(0);
                        object.put("profile_pic", file);
                        Log.i("parse-test", "9");
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.i("parse-test", "10");
                                    value[0] = 1;
                                } else {
                                    Log.i("parse-uploadImgToParse", e.toString());
                                    value[0] = -1;
                                }
                            }
                        });
                    }
                }
                else {
                    Log.i("parse-uploadImgToParse", e.toString());
                }
            }
        });

        return value[0];

//        ParseFile file = new ParseFile("userDP.png", byteArray);
//
//        ParseObject object = new ParseObject("profilePic");
//
//        object.put("username", user.getUsername().toString());
//        object.put("profile_pic", file);
//
//        object.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e == null){
//                    value[0] = 1;
//                }
//                else {
//                    Log.i("parse-uploadImgToParse", e.toString());
//                    value[0] = -1;
//                }
//            }
//        });

    }

}
