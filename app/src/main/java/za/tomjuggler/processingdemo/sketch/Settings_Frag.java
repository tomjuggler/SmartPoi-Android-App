package za.tomjuggler.processingdemo.sketch;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.View.OnClickListener;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

//import za.tomjuggler.processingdemo.MainActivity;
import za.tomjuggler.processingdemo.MainActivity;
import za.tomjuggler.processingdemo.R;

//just for naming saved prefs
import static za.tomjuggler.processingdemo.MainActivity.mypreference;
import static za.tomjuggler.processingdemo.MainActivity.channelSaved;
import static za.tomjuggler.processingdemo.MainActivity.routerSaved;
import static za.tomjuggler.processingdemo.MainActivity.passwordSaved;
import static za.tomjuggler.processingdemo.MainActivity.patternSaved;
import static za.tomjuggler.processingdemo.MainActivity.ipa1Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa2Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa3Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa4Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa5Saved;
/**
 * Created by tom on 2018/02/13.
 */

//public class Settings_Frag extends android.app.Fragment implements android.view.View.OnClickListener {
public class Settings_Frag extends AppCompatActivity implements android.view.View.OnClickListener {
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
        setContentView(R.layout.settings_layout);
        //do drawer again?

        //sharedPreferences for saving:
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if(sharedpreferences.contains(ipa1Saved)){ //is it saved? use this...
            ipa1c = sharedpreferences.getString(ipa1Saved, "");
            Log.d("SHARED PREFERENCES HAS", "we're saved again: " + ipa1c);

        }else {
            Log.d("SHARED PREFERENCES HAS", "we're not saved again: ");
        }


        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.settings_layout,
//                container, false);

        android.support.v7.widget.AppCompatButton _sendRequest;
        ProgressBar _proProgressBar;

        //Hooking the UI views for usage
        _router = (TextView) findViewById(R.id.router);
        _pwd = (TextView) findViewById(R.id.pwd);
        _channel = (TextView) findViewById(R.id.channel);
        _address = (TextView) findViewById(R.id.address);
        _address2 = (TextView) findViewById(R.id.address2);
        _response = (TextView) findViewById(R.id.response);
        _IP1 = (TextView) findViewById(R.id.IP1);
        _IP2 = (TextView) findViewById(R.id.IP2);
        _IP3 = (TextView) findViewById(R.id.IP3);
        _patternChange = (TextView) findViewById(R.id.patternChange);

//        _proProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        _sendRequest = (AppCompatButton) findViewById(R.id.send_request);
        _sendRequest.setOnClickListener(this);
        //hooking onclick listener of button
//        return view;

        /////////////////////////////////////////get settings://///////////////////////
        //should do this on program start, to test connectivity, some saved settings here would be good too.

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.1/returnsettings";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        _response.setText("Response is: " + response);//.substring(0,500));
                        Log.d("REPLY", response); //seems to work now, how?
                        reply1 = response;
                        //parse and set fields:
//                        String parsedChannel = reply1.split(",")[2]; //waste of a good string here, delete now

                        SharedPreferences.Editor editor = sharedpreferences.edit(); //need to do this in onclick() as well!
                        //set textview to received channel:
                        ((TextView) findViewById(R.id.router)).setText(reply1.split(",")[0]);
                        editor.putString(routerSaved, reply1.split(",")[0]);
                        ((TextView) findViewById(R.id.pwd)).setText(reply1.split(",")[1]);
                        editor.putString(passwordSaved, reply1.split(",")[1]);
                        ((TextView) findViewById(R.id.channel)).setText(reply1.split(",")[2]);
                        editor.putString(channelSaved, reply1.split(",")[2]);
                        ((TextView) findViewById(R.id.IP1)).setText(reply1.split(",")[3]);
                        editor.putString(ipa1Saved, reply1.split(",")[3]);
                        ((TextView) findViewById(R.id.IP2)).setText(reply1.split(",")[4]);
                        editor.putString(ipa2Saved, reply1.split(",")[4]);
                        ((TextView) findViewById(R.id.IP3)).setText(reply1.split(",")[5]);
                        editor.putString(ipa3Saved, reply1.split(",")[5]);
                        ((TextView) findViewById(R.id.address)).setText(reply1.split(",")[6]);
                        editor.putString(ipa4Saved, reply1.split(",")[6]);
                        ((TextView) findViewById(R.id.patternChange)).setText(reply1.split(",")[7]);
                        editor.putString(patternSaved, reply1.split(",")[7]);
                        editor.commit();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _response.setText("get settings didn't work!");
                Log.d("REPLY", "no reply"); //seems to work now, how?
                reply1 = "no reply";
            }
        });
// Add the request to the RequestQueue.
//        queue.add(stringRequest); //disable to stop restart bug here. unstable returnsettings on poi

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
                        _response.setText("Response is: " + response);//.substring(0,500));
                        Log.d("REPLY", response); //seems to work now, how?
                        reply1 = response;
                        //parse and set fields:
//                        String parsedChannel = reply1.split(",")[2]; //waste of a good string here, delete now

                        SharedPreferences.Editor editor = sharedpreferences.edit(); //need to do this in onclick() as well!
                        //set textview to received channel:

                        ((TextView) findViewById(R.id.address2)).setText(reply1.split(",")[6]);
                        editor.putString(ipa5Saved, reply1.split(",")[6]);
                        Log.d("SettingB", reply1.split(",")[6]); //seems to work now, how?
                        reply1 = "no reply";
                        editor.commit();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _response.setText("get settings didn't work!");
                Log.d("REPLY", "no reply"); //seems to work now, how?
                reply1 = "no reply";
            }
        });
// Add the request to the RequestQueue.
//        queue2.add(stringRequest2); //disable to stop restart bug here. unstable returnsettings on poi

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

                /////////////////////////////////////////////////////////////////////////////
                // Save new form settings before transmit: (ok what if not transmit? need to move this down)
                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(routerSaved, _router.getText().toString());
                editor.putString(passwordSaved, _pwd.getText().toString());
                editor.putString(channelSaved, _channel.getText().toString());
                editor.putString(ipa1Saved, _IP1.getText().toString());
                editor.putString(ipa2Saved, _IP2.getText().toString());
                editor.putString(ipa3Saved, _IP3.getText().toString());
                editor.putString(ipa4Saved, _address.getText().toString());
                editor.putString(patternSaved, _patternChange.getText().toString());
                editor.putString(ipa5Saved, _address2.getText().toString());

                editor.commit();

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(this);
                //this is the url where you want to send the request
                //if you change the address then this won't work:
                //so first get the address (how?) - the answer is only send when you know the address, as in AP mode (address doesn't change!)

//                String url = "http://192.168.1.1/setting?ssid=ROUTER_NAME&pwd=PASSWORD&channel=" + _channel.getText().toString() +
//                        "&addressA=" + _IP1.getText().toString() + "&addressB=" + _IP2.getText().toString() +
//                        "&addressC=" + _IP3.getText().toString() + "&address=" + _address.getText().toString() +
//                        "&patternChooserChange=" + _patternChange.getText().toString();

                //need url2 here as well for slave:

                String url = "http://192.168.1.1/setting?ssid=" + _router.getText().toString() + "&pwd=" + _pwd.getText().toString() + "&channel=" + _channel.getText().toString() +
                        "&addressA=" + _IP1.getText().toString() + "&addressB=" + _IP2.getText().toString() +
                        "&addressC=" + _IP3.getText().toString() + "&address=" + _address.getText().toString() +
                        "&patternChooserChange=" + _patternChange.getText().toString();
//String url = "http://192.168.8.78/setting?ssid=ROUTER_NAME&pwd=PASSWORD&channel=1&addressA=192&addressB=168&addressC=8&address=78&patternChooserChange=1";
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //save form input settings should be here - at least parts pertaining to "Master" poi
                                // Display the response string.
                                _response.setText(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        _response.setText("That didn't work!");
                    }
                }) {
                    //adding parameters to the request
                    //so far this below code is useless..? for what I want anyway.
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("channel", _channel.getText().toString());
                        params.put("address", _address.getText().toString());
                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
                Log.d("myTag", stringRequest.toString()); //seems to work now, how?
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//again for slave poi:
                // Instantiate the RequestQueue.
                RequestQueue queueSlave = Volley.newRequestQueue(this);
                String addressSlave = "79"; //test value remove this
                String urlSlave = "http://192.168.1.78/setting?ssid=" + _router.getText().toString() + "&pwd=" + _pwd.getText().toString() + "&channel=" + _channel.getText().toString() +
                        "&addressA=" + _IP1.getText().toString() + "&addressB=" + _IP2.getText().toString() +
                        "&addressC=" + _IP3.getText().toString() + "&address=" + _address2.getText().toString() +
                        "&patternChooserChange=" + _patternChange.getText().toString();
//String url = "http://192.168.8.78/setting?ssid=ROUTER_NAME&pwd=PASSWORD&channel=1&addressA=192&addressB=168&addressC=8&address=78&patternChooserChange=1";
                // Request a string response from the provided URL.
                StringRequest stringRequestSlave = new StringRequest(Request.Method.POST, urlSlave,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //save form input settings should be here - just the "Slave" specific parts!
                                // Display the response string.
                                _response.setText(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        _response.setText("That didn't work!");
                    }
                }) {
                    //adding parameters to the request
                    //so far this below code is useless..? for what I want anyway.
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("channel", _channel.getText().toString());
                        params.put("address", _address.getText().toString());
                        return params;
                    }
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
//                Intent intent = new Intent(this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
//                startActivity(intent);

                this.finish();
                break;
        }


    }

//    isFrag = false;//hack

}

