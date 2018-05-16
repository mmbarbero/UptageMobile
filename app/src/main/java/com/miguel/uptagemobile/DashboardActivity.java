package com.miguel.uptagemobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    JSONObject user;
    String username;
    String email;
    String firstName;
    String lastName;
    String userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final LinearLayout linearLayout = findViewById(R.id.videosLayout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        ActionBar actionBar = getSupportActionBar();


        TextView fullNameText = header.findViewById(R.id.userFullName);
        TextView emailText = header.findViewById(R.id.userEmail);

        Intent intent = getIntent();

        if(getIntent().hasExtra("json")) {
            try {
                user = new JSONObject(intent.getStringExtra("json"));

                Log.v("text", user.toString());

            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        try {
            username = user.getString("username");
            email = user.getString("email");
            firstName = user.getString("firstName").substring(0,1).toUpperCase() + user.getString("firstName").substring(1);
            lastName = user.getString("lastName").substring(0,1).toUpperCase() + user.getString("lastName").substring(1);
            userType = user.getString("type");



        } catch (JSONException e) {
            e.printStackTrace();
        }
        actionBar.setTitle("Welcome " +username);
        fullNameText.setText(firstName + " "+ lastName);
        emailText.setText(email);


        HttpUtils.get("/api/videos/user/"+ username, null, new JsonHttpResponseHandler(){


            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                try{
                    for (int i = 0; i < response.length(); i++){

                        JSONObject vid = response.getJSONObject(i);

                        VideoView videoView = new VideoView(getApplicationContext());
                        TextView videoTitle = new TextView(getApplicationContext());
                        LinearLayout videoLayout = new LinearLayout(getApplicationContext());



                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(800, 600);
                        layoutParams.setMargins(80, 20, 0, 10);

                        LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(800, 80);
                        titleLayoutParams.setMargins(80, 0, 0, 20);


                        final Uri uri = Uri.parse(vid.getString("path"));
                        videoView.setVideoURI(uri);
                        videoView.seekTo(100);
                        videoTitle.setText(vid.getString("title"));
                        videoLayout.setClickable(true);
                        videoLayout.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View v) {
                                Log.v("videoplayer", "ive been clicked");
                                Intent intentVideo = new Intent(Intent.ACTION_VIEW,uri);
                                intentVideo.setDataAndType(uri, "video/mp4");
                                startActivity(intentVideo);

                            }
                        });

                        videoLayout.addView(videoView,layoutParams);
                        linearLayout.addView(videoLayout);
                        linearLayout.addView(videoTitle,titleLayoutParams);


                    }



                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });

        /**  VideoView videoView = new VideoView(getApplicationContext());
         videoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
         ViewGroup.LayoutParams.WRAP_CONTENT));

         Uri uri = Uri.parse("http://mbarhomelab.ddns.net:9000/uptage/1ff6228a3d055f0f451cf7da2a4498b0.mp4");


         videoView.setVideoURI(uri);
         videoView.seekTo(100);

         linerLayout.addView(videoView); **/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
