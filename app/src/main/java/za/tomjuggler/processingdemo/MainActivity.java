package za.tomjuggler.processingdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import za.tomjuggler.processingdemo.sketch.Brightness;
import za.tomjuggler.processingdemo.sketch.Change_Pattern_Frag;
import za.tomjuggler.processingdemo.sketch.OwnThing;
import za.tomjuggler.processingdemo.sketch.PGraphics_Pattern_Template;
import za.tomjuggler.processingdemo.sketch.Particles;
import za.tomjuggler.processingdemo.sketch.Settings_Frag;
import za.tomjuggler.processingdemo.sketch.Speed;
import za.tomjuggler.processingdemo.sketch.ZapGame;
import za.tomjuggler.processingdemo.sketch.android_fft_minim.android_fft_minim;
import za.tomjuggler.processingdemo.sketch.manyScreensAndroidFlick;
import za.tomjuggler.processingdemo.sketch.TimelineOnly;
import processing.core.PApplet;
import za.tomjuggler.processingdemo.sketch.manyScreensAndroidFlick72px;

import static android.R.attr.key;
import static android.R.attr.searchSuggestIntentData;
import static processing.core.PApplet.println;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    String reply1 = "nothing, here, my, 192, 168, 1, 2, 1"; //load default, ip 2 is to test loading
    String ipa1;
    String ipa1b;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    public static final String channelSaved = "channel";
    public static final String routerSaved = "router";
    public static final String passwordSaved = "password";
    public static final String patternSaved = "pattern";
    public static final String ipa1Saved = "ip1Key";
    public static final String ipa2Saved = "ip2Key";
    public static final String ipa3Saved = "ip3Key";
    public static final String ipa4Saved = "ip4Key";
    public static final String ipa5Saved = "ip5Key";


    Context mContext;

//    public static final String ipa1Saved = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);

        //check first run:
        checkFirstRun();
        //fullscreen:
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        updateSharedPreferences(); //get settings from poi here

        ////connect to poi here and get settings? ///////////////////////////////////////////////////////////////
//        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "http://192.168.1.1/returnsettings";
//
//// Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        Log.d("REPLY", response); //seems to work now, how?
//                        reply1 = response;
//                        }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("REPLY", "no reply"); //seems to work now, how?
//                reply1 = "no reply";
//            }
//        });
//// Add the request to the RequestQueue.
//        queue.add(stringRequest);
////      ipa1 = Integer.parseInt(reply1.split(",")[3]);
//        ipa1 = reply1.split(",")[3];
////////////////////////////////////////////end get poi settings/////////////////////////////////////

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);




        //connect to wifi here test?????//////////////////////////////////////////////////////////////////////
        //no joy so far...
/*
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"",getString(R.string.ssid));
        wifiConfig.preSharedKey = String.format("\"%s\"",getString(R.string.key));
        //try this:
        wifiConfig.status = WifiConfiguration.Status.ENABLED;

        wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);

        wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        //
        WifiManager wifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
        wifiManager.startScan();
//remember id
        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
            println("netId is: " + netId);
            wifiManager.reconnect();
            println("connected to : " + wifiManager.getConnectionInfo());
*/
        /////////////////////////////////////////////////////////////end connect to wifi test /////////////////
    }
public void updateSharedPreferences(){
    //connect to poi here and get settings? ///////////////////////////////////////////////////////////////
    // Instantiate the RequestQueue.
    RequestQueue queue = Volley.newRequestQueue(this);
    String url = "http://192.168.1.1/returnsettings"; //using AP for now but actually this is really for Router. AP is going to be set.
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //get shared prefs from android: - do this in onStart()? Yes...
//    sharedpreferences = getSharedPreferences(mypreference,
//            Context.MODE_PRIVATE);
//    if(sharedpreferences.contains(ipa1Saved)){ //is it saved? use this... hmmm maybe don't get from poi at all.
//        ipa1b = sharedpreferences.getString(ipa1Saved, "");
//        Log.d("SHARED PREFERENCES HAS", "we're saved: " + ipa1b);
//    }
//    if(sharedpreferences.contains(routerSaved)){ //is it saved? use this...
//        ipa1b = sharedpreferences.getString(routerSaved, "");
//        Log.d("SHARED PREFERENCES HAS", "router saved: " + ipa1b);
//    } else{
//        Log.d("SHARED PREFERENCES ERR", "router not saved");
//    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    else { // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        Log.d("REPLY", response); //seems to work now, how?
//                        reply1 = response;
//                        ipa1 = reply1.split(",")[6];
//                        //save to settings for next time:
//                        SharedPreferences.Editor editor = sharedpreferences.edit();
//                        editor.putString(ipa1Saved, ipa1);
//                        editor.commit();
//                        Log.d("SAVED", "we're committed "+ ipa1);
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("REPLY", "no reply"); //seems to work now, how?
//                reply1 = "no reply";
//            }
//        });
// Add the request to the RequestQueue.
//        queue.add(stringRequest); //executes in background?
        //this doesn't achieve results the first time...
//      ipa1 = Integer.parseInt(reply1.split(",")[3]);
    // can't do work with reply1 here as StringRequest above is asynchronous!!!!
//        ipa1 = reply1.split(",")[6];
//        //save to settings for next time:
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putString(ipa1Saved, ipa1);
//        editor.commit();
//        Log.d("SAVED", "we're committed"+ ipa1);
//////////////////////////////////////////end get poi settings/////////////////////////////////////
//    }
}
    @Override
    public void onNavigationDrawerItemSelected(int position) {
    //todo: add some saved preferences here!
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment;
        Activity settingsAct;
        boolean isFrag = true;

//        ipa1 = sharedpreferences.getString(ipa1Saved, "1"); //this crashes app!

        //change all Fragments to Activities???
        switch (position) {
            case 0:
                fragment = new manyScreensAndroidFlick();
                break;
            case 1:
                fragment = new manyScreensAndroidFlick72px();
                break;
            case 2:
                fragment = new Brightness();
                break;
            case 3:
                fragment = new PGraphics_Pattern_Template();
                break;
            case 4:
                fragment = new TimelineOnly();
                break;
            case 5:
                fragment = new android_fft_minim();
                break;
            case 6:
                fragment = new ZapGame();
                break;
            case 7: //settings screen, not Processing code:

//                fragment = new R.id.bodyFrag;
//                settingsAct = new Settings_Frag();
//                fragment = new Fragment();
//                setContentView(R.layout.settings_layout);
                Intent intent = new Intent(this,Settings_Frag.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                startActivity(intent);
//                this.finish();

//                fragment = new Settings_Frag();//hack
//                fragment = new PApplet(); //what is this doing here?????????
                fragment = null; //now why would anyone do this?
                isFrag = false;
                break;
            case 8: //Change Pattern screen, not Processing code:

//                fragment = new R.id.bodyFrag;
//                settingsAct = new Settings_Frag();
//                fragment = new Fragment();
//                setContentView(R.layout.settings_layout);
                Intent intent2 = new Intent(getApplicationContext(),Change_Pattern_Frag.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                startActivity(intent2);
                //below stops activity from going back to main screen.
//                this.finish(); //todo: test do I need this in order for pattern transmit codes to work?

//                fragment = new Settings_Frag();//hack
//                fragment = new PApplet(); //what is this doing here?????????
                fragment = null; //now why would anyone do this?
                isFrag = false;
                break;
            case 9:
                fragment = new Speed();
                break;
            case 10:
                fragment = new PApplet();
                break;
            default:
                throw new UnsupportedOperationException("Invalid position");
        }
        if(isFrag) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } else{
//            fragmentManager.beginTransaction()
//                    .replace(R.id.settings1, fragment)
//                    .commit();
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_directional);
                break;
            case 2:
                mTitle = getString(R.string.title_empty);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    //checkFirstRun from https://stackoverflow.com/questions/7217578/check-if-application-is-on-its-first-run#7217834

    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {
            Log.d("FIRSTRUN", "this is just a normal run");

            // This is just a normal run
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {
            Log.d("FIRSTRUN", "this is a new install");
            // TODO This is a new install (or the user cleared the shared preferences)

        } else if (currentVersionCode > savedVersionCode) {
            Log.d("FIRSTRUN", "this is an upgrade");
            // TODO This is an upgrade
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }
}
