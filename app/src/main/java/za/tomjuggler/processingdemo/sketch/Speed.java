package za.tomjuggler.processingdemo.sketch;

/**
 * Created by tom on 2017/08/30.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import java.io.IOException;

import controlP5.ControlP5;
import controlP5.Knob;
import controlP5.Slider;
import hypermedia.net.UDP;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import za.tomjuggler.processingdemo.R;

import static za.tomjuggler.processingdemo.MainActivity.ipa1Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa2Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa3Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa4Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa5Saved;

public class Speed extends PApplet {




    ControlP5 cp5;

    int myColorBackground = color(0, 0, 0);


//UDP code:///////////////////////////////////////////////////


    UDP udp; // define the UDP object
    //multicast - or broadcast:
//String ip = "255.255.255.255";
//String ip = "224.0.0.1"; // the remote IP address
//int UDPport = 5656; // the destination port
    String ip2 = "192.168.1.78"; // change this with android saved settings for testing
    String ip = "192.168.1.1"; // change in constructor now
    String ip3 = "192.168.1.79"; // the remote IP address (Accelerometer)

    //test add more poi:
    String ip4 = "192.168.8.80";
    String ip5 = "192.168.8.81";
    String ip6 = "192.168.8.82";
    String ip7 = "192.168.8.83";
    boolean onRouter = false;

//String ip = "192.168.1.77"; // the remote IP address (Huawei HG532C)
//String ip2 = "192.168.1.78"; // the remote IP address (Huawei HG532C)

    int UDPport = 2390; // the destination port
////////////////////////////////////////////////////////////

    boolean showOnce = true;

    //brightness idea:
    float BRT = 0.5f;
    boolean showSlider = false;

    Slider brightnessSlider;
    ////////////////////////////////////Saved Wifi://////////////////////////////////////////
    String ipa;
    String ipb;
    String ipc;
    String ipd;
    String ipe;


    SharedPreferences sharedpreferences;
//    public static final String mypreference = "mypref";

    String ssid;
    /////////////////////////////////////end Saved Wifi///////////////////////////////////////
    @Override
    public void setup() {
////////////////////////////////////Saved Wifi://////////////////////////////////////////
        WifiManager wifiManager = (WifiManager) this.getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo;
        wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
            ssid = wifiInfo.getSSID();
        }
        println("ssid is: " + ssid);
        //double quotation marks for ssid always apparently!
//        String expectedSSID = "\"Smart_Poi_2\""; //todo: put this in saved settings

        Resources res = getResources();
        String expectedSSID = res.getString(R.string.ap_name); //todo: put in saved settings
        String expectedSSID2 = res.getString(R.string.ap_name2); //todo: put in saved settings
        String expectedSSID3 = res.getString(R.string.ap_name3); //todo: put in saved settings
        if(ssid.equals(expectedSSID) || ssid.equals(expectedSSID2) || ssid.equals(expectedSSID3)){ //compare strings - why the hell doesn't this work????
            println("AP Mode Hard Coded Now!");
        } else {
            onRouter = true;
            //todo: add more .equals calls for different poi? maybe not now...
            println("Router Network - using saved settings");
//        }
            //load all preferences:
            sharedpreferences = this.getActivity().getSharedPreferences("mypref", 0);
            if (sharedpreferences.contains(ipa1Saved)) { //is it saved? use this...
                ipa = sharedpreferences.getString(ipa1Saved, "192");
                println("ipa is: " + ipa);
            } else {
                ipa = "192";
            }
            if (sharedpreferences.contains(ipa2Saved)) { //is it saved? use this...
                ipb = sharedpreferences.getString(ipa2Saved, "168");
                println("ipb is: " + ipb);
            } else {
                ipb = "168";
            }
            if (sharedpreferences.contains(ipa3Saved)) { //is it saved? use this...
                ipc = sharedpreferences.getString(ipa3Saved, "8");
                println("ipc is: " + ipc);
            } else {
                ipc = "8";
            }
            if (sharedpreferences.contains(ipa4Saved)) { //is it saved? use this...
                ipd = sharedpreferences.getString(ipa4Saved, "78");
                println("ipd is: " + ipd);

            } else {
                ipd = "78";
                println("no ipd");
            }
            if (sharedpreferences.contains(ipa5Saved)) { //is it saved? use this...
                ipe = sharedpreferences.getString(ipa5Saved, "79");
                println("ipe is: " + ipe);

            } else {
                ipe = "79";
                println("no ipe");
            }

            println("ip before: " + ip);
            ip = ipa + "." + ipb + "." + ipc + "." + ipd;
            println("ip after: " + ip);

            println("ip2 before: " + ip2);
            ip2 = ipa + "." + ipb + "." + ipc + "." + ipe;
            println("ip2 after: " + ip2);
        } //now ip only changed from default if AP not connected direct!
////////////////////////////////////End Saved Wifi://////////////////////////////////////////

        orientation(PORTRAIT);
        textSize(50);
        //frameRate(4);
        background(0);
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        cp5 = new ControlP5(this);

        brightnessSlider = cp5.addSlider("BRT")
                .setPosition(50, 50)
                .setSize(width/3*2, height/3*2)
                .setRange(0, 1) //multiplies by sequenceShowTime
                .setValue(1.0f)
                .setNumberOfTickMarks(10)
                .setVisible(true)
        ;

        //////////////////////////////UDP Code://////////////////////
        udp = new UDP( this ); // no need port, can cause errors...?

//  udp = new UDP( this, UDPport ); // create a new datagram connection on port 8888
        //udp.log( true ); // <\u2013 printout the connection activity
        udp.listen( true ); // and wait for incoming message
        /////////////////////////////////////////////////////////////

    }


    @Override
    public void draw() {

    }

    public void BRT(float theBRT) { //called on slider change?
        BRT = theBRT;
        int sequenceShowTime = PApplet.parseInt(10000*BRT); //up to 10 seconds..
        int roundedShowTime = PApplet.parseInt(sequenceShowTime/1000);
        String sendShowTime = str(roundedShowTime);
        //send post request changing time:
        String url ="http://" + ip + "/intervalChange";
        String url2 ="http://" + ip2 + "/intervalChange";
        try {
            post(url, sendShowTime);
            post(url2, sendShowTime);
        } catch (java.io.IOException e){

        }


        showOnce = true;
        //now disable, so we can't change more and send too many post requests:
//        brightnessSlider.setVisible(false);
    }

    public float k(float n, float f) {

        return 10 + 100 * noise(n + f / 3000);
    }



    public void knob(int theValue) {
        myColorBackground = color(theValue);
        //println("a knob event. setting background to "+theValue);
    }

    public void mousePressed() {

    }

    public void keyPressed() {

    }

    //OkHttp post:
    public void post(String url, String interval) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("interval", interval)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {
                        // Error
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String res = response.body().string();

                        // Do something with the response
                    }
                });
//        try {
//            Response response = client.newCall(request).execute();
//
//            // Do something with the response.
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


//*********************************************************************
// keep activity alive code import:
//*********************************************************************





//*********************************************************************

////startup Android:////////////////////////////////////////////////////////////////////////////
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // fix so screen doesn't go to sleep when app is active
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //http://stackoverflow.com/questions/2887368/can-you-please-explain-oncreate-and-bundles
        //try to make some more stuff persistent?? not sure if it's necessary.
        //*****************************************************
    }







@Override
public void settings() {
    size(getScreenWidth(), getScreenHeight());
}


    //very useful functions here:
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
