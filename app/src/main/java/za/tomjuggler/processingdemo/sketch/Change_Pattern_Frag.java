package za.tomjuggler.processingdemo.sketch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import za.tomjuggler.processingdemo.MainActivity;
import za.tomjuggler.processingdemo.R;

import static za.tomjuggler.processingdemo.MainActivity.channelSaved;
import static za.tomjuggler.processingdemo.MainActivity.ipa1Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa2Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa3Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa4Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa5Saved;
import static za.tomjuggler.processingdemo.MainActivity.passwordSaved;
import static za.tomjuggler.processingdemo.MainActivity.patternSaved;
import static za.tomjuggler.processingdemo.MainActivity.routerSaved;

//import za.tomjuggler.processingdemo.MainActivity;
//just for naming saved prefs

/**
 * Created by tom on 2018/02/13.
 */

//public class Settings_Frag extends android.app.Fragment implements android.view.View.OnClickListener {
public class Change_Pattern_Frag extends AppCompatActivity implements OnClickListener {
    /////////test settings/////////////
    TextView _router, _pwd, _channel, _address, _address2,  _response, _IP1, _IP2, _IP3, _patternChange ;
    //    android.support.v7.widget.AppCompatButton _sendRequest;
//    ProgressBar _proProgressBar;
    String reply1 = "";

    String ipa1c;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pattern_layout);

        //sharedPreferences for saving:
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        _patternChange = (TextView) findViewById(R.id.patternChange);
        //buttons:
        AppCompatButton _sendRequest;
        _sendRequest = (AppCompatButton) findViewById(R.id.send_request);
        _sendRequest.setOnClickListener(this);

        AppCompatButton _sendRequest2;
        _sendRequest2 = (AppCompatButton) findViewById(R.id.send_request2);
        _sendRequest2.setOnClickListener(this);

        AppCompatButton _sendRequest3;
        _sendRequest3 = (AppCompatButton) findViewById(R.id.send_request3);
        _sendRequest3.setOnClickListener(this);

        AppCompatButton _sendRequest4;
        _sendRequest4 = (AppCompatButton) findViewById(R.id.send_request4);
        _sendRequest4.setOnClickListener(this);

        AppCompatButton _sendRequest5;
        _sendRequest5 = (AppCompatButton) findViewById(R.id.send_request5);
        _sendRequest5.setOnClickListener(this);

        AppCompatButton _sendRequest6;
        _sendRequest6 = (AppCompatButton) findViewById(R.id.send_request6);
        _sendRequest6.setOnClickListener(this);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.1/returnsettings";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
//                        _response.setText("Response is: " + response);//.substring(0,500));
                        Log.d("REPLY", response); //seems to work now, how?
                        reply1 = response;
                        //parse and set fields:
//                        String parsedChannel = reply1.split(",")[2]; //waste of a good string here, delete now

                        SharedPreferences.Editor editor = sharedpreferences.edit(); //need to do this in onclick() as well!
                        //set textview to received channel:
//                        ((TextView) findViewById(R.id.router)).setText(reply1.split(",")[0]);
//                        editor.putString(routerSaved, reply1.split(",")[0]);
//                        ((TextView) findViewById(R.id.pwd)).setText(reply1.split(",")[1]);
//                        editor.putString(passwordSaved, reply1.split(",")[1]);
//                        ((TextView) findViewById(R.id.channel)).setText(reply1.split(",")[2]);
//                        editor.putString(channelSaved, reply1.split(",")[2]);
//                        ((TextView) findViewById(R.id.IP1)).setText(reply1.split(",")[3]);
//                        editor.putString(ipa1Saved, reply1.split(",")[3]);
//                        ((TextView) findViewById(R.id.IP2)).setText(reply1.split(",")[4]);
//                        editor.putString(ipa2Saved, reply1.split(",")[4]);
//                        ((TextView) findViewById(R.id.IP3)).setText(reply1.split(",")[5]);
//                        editor.putString(ipa3Saved, reply1.split(",")[5]);
//                        ((TextView) findViewById(R.id.address)).setText(reply1.split(",")[6]);
//                        editor.putString(ipa4Saved, reply1.split(",")[6]);
                        ((TextView) findViewById(R.id.patternChange)).setText(reply1.split(",")[7]);
                        editor.putString(patternSaved, reply1.split(",")[7]);
                        editor.commit();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                _response.setText("get settings didn't work!");
                Log.d("REPLY", "no reply"); //seems to work now, how?
                reply1 = "no reply";
            }
        });
// Add the request to the RequestQueue.
//        queue.add(stringRequest); //disable to see if restart bug persists

        ///////////////////////////Slave: ////////////////////////////////////////////////////////
        // Instantiate the RequestQueue.
        RequestQueue queue2 = Volley.newRequestQueue(this);
        String url2 = "http://192.168.1.78/returnsettings";

// Request a string response from the provided URL.
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
//                        _response.setText("Response is: " + response);//.substring(0,500));
                        Log.d("REPLY", response); //seems to work now, how?
                        reply1 = response;
                        //parse and set fields:
//                        String parsedChannel = reply1.split(",")[2]; //waste of a good string here, delete now

                        SharedPreferences.Editor editor = sharedpreferences.edit(); //need to do this in onclick() as well!
                        //set textview to received channel:

//                        ((TextView) findViewById(R.id.address2)).setText(reply1.split(",")[6]);
                        editor.putString(ipa5Saved, reply1.split(",")[6]);
                        Log.d("SettingB", reply1.split(",")[6]); //seems to work now, how?
                        reply1 = "no reply";
                        editor.commit();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                _response.setText("get settings didn't work!");
                Log.d("REPLY", "no reply"); //seems to work now, how?
                reply1 = "no reply";
            }
        });
// Add the request to the RequestQueue.
//        queue2.add(stringRequest2); disabling to see if restart bug persists

        Context context = getApplicationContext();
        CharSequence text = reply1;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        Button b = (AppCompatButton) v;

        switch (b.getId()) {
            case R.id.send_request:
                sendPatternChange("1");
                break;

            case R.id.send_request2:
                sendPatternChange("2");
                break;
            case R.id.send_request3:
                sendPatternChange("3");
                break;

            case R.id.send_request4:
                sendPatternChange("4");
                break;
            case R.id.send_request5:
                sendPatternChange("5");
                break;

            case R.id.send_request6:
                sendPatternChange("6");
                break;


        }


    }

    public void sendPatternChange(String pat){
        String patternToSend = pat;
        /////////////////////////////////////////////////////////////////////////////
        // Save new form settings before transmit: (ok what if not transmit? need to move this down)
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(patternSaved, patternToSend);
        editor.commit();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.1/pattern?patternChooserChange=" + patternToSend;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //save form input settings should be here - at least parts pertaining to "Master" poi
                        // Display the response string.
//                                _response.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                        _response.setText("That didn't work!");
            }
        }) {
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        Log.d("myTag", stringRequest.toString()); //seems to work now, how?
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//again for slave poi:
        // Instantiate the RequestQueue.
        RequestQueue queueSlave = Volley.newRequestQueue(this);
        String urlSlave = "http://192.168.1.78/pattern?patternChooserChange=" + patternToSend;
//String url = "http://192.168.8.78/setting?ssid=ROUTER&pwd=PASSWORD&channel=1&addressA=192&addressB=168&addressC=8&address=78&patternChooserChange=1";
        // Request a string response from the provided URL.
        StringRequest stringRequestSlave = new StringRequest(Request.Method.POST, urlSlave,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //save form input settings should be here - just the "Slave" specific parts!
                        // Display the response string.
//                                _response.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                        _response.setText("That didn't work!");
            }
        }) {
        };
        // Add the request to the RequestQueue.
        queueSlave.add(stringRequestSlave);
        Log.d("myTag", stringRequestSlave.toString()); //seems to work now, how?

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Context context = getApplicationContext();
        CharSequence text = stringRequestSlave.toString();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        //back to other activity again? This works great!
        Intent intent = new Intent(this, Change_Pattern_Frag.class); //reload?
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(intent);

        this.finish();
    }

//    isFrag = false;//hack

}

