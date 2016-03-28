/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRole;
import com.parse.ParseUser;
import com.parse.starter.Complaints.NewComplaint;
import com.parse.starter.LoginSignup.LogOut;
import com.parse.starter.LoginSignup.Login;
import com.parse.starter.Navigation.ContentFragment;
import com.parse.starter.Navigation.Profile.UserProfile;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    public  CircleImageView userDP;
    ParseUser user;
    public TextView name_header, entryNo_header;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
      user = ParseUser.getCurrentUser();
      userDP = (CircleImageView) findViewById(R.id.profile_image);
      ParseAnalytics.trackAppOpenedInBackground(getIntent());

    //Navigation Starts
      name_header = (TextView) findViewById(R.id.username_header);
      entryNo_header = (TextView) findViewById(R.id.email_header);
      name_header.setText(user.get("name").toString());
      entryNo_header.setText(user.getEmail());
      //setting current DP
      updateNavBarDp(null);


      userDP.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              UserProfile profile = new UserProfile();
              android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
              fragmentTransaction.replace(R.id.frame,profile);
              fragmentTransaction.commit();
              drawerLayout.closeDrawers();
          }
      });

      // Initializing Toolbar and setting it as the actionbar
      toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
//      getSupportActionBar().setLogo(R.drawable.logout);

      //Initializing NavigationView
      navigationView = (NavigationView) findViewById(R.id.navigation_view);

      //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
      navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

          // This method will trigger on item Click of navigation menu
          @Override
          public boolean onNavigationItemSelected(MenuItem menuItem) {


              //Checking if the item is in checked state or not, if not make it in checked state
              if(menuItem.isChecked()) menuItem.setChecked(false);
              else menuItem.setChecked(true);

              //Closing drawer on item click
              drawerLayout.closeDrawers();

              //Check to see which item was being clicked and perform appropriate action
              switch (menuItem.getItemId()){


                  //Replacing the main content with ContentFragment Which is our Inbox View;
                  case R.id.new_comp_menu:
                      Toast.makeText(getApplicationContext(),"Inbox Selected",Toast.LENGTH_SHORT).show();
                      NewComplaint complaint = new NewComplaint();
                      android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                      fragmentTransaction.replace(R.id.frame,complaint);
                      fragmentTransaction.commit();
                      return true;

                  // For rest of the options we just show a toast on click

                  //Menu View Profile Done
                  case R.id.view_profile_menu:
                      Toast.makeText(getApplicationContext(),"View your Profile",Toast.LENGTH_SHORT).show();
                      UserProfile profile = new UserProfile();
                      android.support.v4.app.FragmentTransaction fragmentTransactions = getSupportFragmentManager().beginTransaction();
                      fragmentTransactions.replace(R.id.frame,profile);
                      fragmentTransactions.commit();
                      return true;


                  case R.id.your_comp_menu:
                      Toast.makeText(getApplicationContext(),"Send Selected",Toast.LENGTH_SHORT).show();
                      return true;


                  case R.id.hostel_comp_menu:
                      Toast.makeText(getApplicationContext(),"Drafts Selected",Toast.LENGTH_SHORT).show();
                      return true;


                  case R.id.insti_comp_menu:
                      Toast.makeText(getApplicationContext(),"All Mail Selected",Toast.LENGTH_SHORT).show();
                      return true;


                  case R.id.settings_menu:
                      Toast.makeText(getApplicationContext(),"Trash Selected",Toast.LENGTH_SHORT).show();
                      return true;


                  //Menu Logout Done
                  case R.id.logout_menu:
                      Toast.makeText(getApplicationContext(),"Logging you Out..!!",Toast.LENGTH_SHORT).show();
                      LogOut.logMeOut();
                      Intent i = new Intent(getApplicationContext(), Login.class);
                      startActivity(i);
                      return true;


                  default:
                      Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                      return true;

              }
          }
      });

      // Initializing Drawer Layout and ActionBarToggle
      drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
      ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

          @Override
          public void onDrawerClosed(View drawerView) {
              // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
              super.onDrawerClosed(drawerView);
          }

          @Override
          public void onDrawerOpened(View drawerView) {
              // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

              super.onDrawerOpened(drawerView);
          }
      };

      //Setting the actionbarToggle to drawer layout
      drawerLayout.setDrawerListener(actionBarDrawerToggle);

      //calling sync state is necessay or else your hamburger icon wont show up
      actionBarDrawerToggle.syncState();

      //NAVIGATION CLOSED




  }

  public void updateNavBarDp(Bitmap bitmap){

        if(bitmap == null) {
            final ParseFile[] DPfile = new ParseFile[1];
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("profilePic");
            query.whereEqualTo("username", user.getUsername());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
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
                    } else {
                        Log.i("parse-userDP", e.toString());
                    }
                }
            });
            Log.i("parse-dp_file", String.valueOf(DPfile[0]));
        }
        else {
            userDP.setImageBitmap(bitmap);
        }
    }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

    //Logout Enabled
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.logout_settings) {
      LogOut.logMeOut();
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
    }

    return super.onOptionsItemSelected(item);
  }
}
