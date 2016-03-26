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
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.starter.LoginSignup.LogOut;
import com.parse.starter.LoginSignup.Login;
import com.parse.starter.Navigation.ContentFragment;
import com.parse.starter.Navigation.ContentFragment2;
import com.parse.starter.Navigation.Profile.UserProfile;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    CircleImageView userDP;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());

    //Navigation Starts

      userDP = (CircleImageView) findViewById(R.id.profile_image);
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
                  case R.id.inbox:
                      Toast.makeText(getApplicationContext(),"Inbox Selected",Toast.LENGTH_SHORT).show();
                      ContentFragment fragment = new ContentFragment();
                      android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                      fragmentTransaction.replace(R.id.frame,fragment);
                      fragmentTransaction.commit();
                      return true;

                  // For rest of the options we just show a toast on click

                  case R.id.starred:
                      Toast.makeText(getApplicationContext(),"Stared Selected",Toast.LENGTH_SHORT).show();
                      ContentFragment2 fragments = new ContentFragment2();
                      android.support.v4.app.FragmentTransaction fragmentTransactions = getSupportFragmentManager().beginTransaction();
                      fragmentTransactions.replace(R.id.frame,fragments);
                      fragmentTransactions.commit();
                      return true;


                  case R.id.sent_mail:
                      Toast.makeText(getApplicationContext(),"Send Selected",Toast.LENGTH_SHORT).show();
                      return true;


                  case R.id.drafts:
                      Toast.makeText(getApplicationContext(),"Drafts Selected",Toast.LENGTH_SHORT).show();
                      return true;


                  case R.id.allmail:
                      Toast.makeText(getApplicationContext(),"All Mail Selected",Toast.LENGTH_SHORT).show();
                      return true;


                  case R.id.trash:
                      Toast.makeText(getApplicationContext(),"Trash Selected",Toast.LENGTH_SHORT).show();
                      return true;


                  case R.id.spam:
                      Toast.makeText(getApplicationContext(),"Spam Selected",Toast.LENGTH_SHORT).show();
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
