package za.tomjuggler.processingdemo.sketch;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FilenameFilter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import android.view.MotionEvent;
import ketai.ui.*;
import hypermedia.net.*;
import java.io.*;
import controlP5.*;
import za.tomjuggler.processingdemo.R;
//import http.requests.*;
import android.view.WindowManager;
import android.view.View;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import static za.tomjuggler.processingdemo.MainActivity.ipa1Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa2Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa3Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa4Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa5Saved;

/**
 * Created by tom on 2017/08/30.
 */

public class manyScreensAndroidFlick extends PApplet {


//note: this won't run with Android 6.0 selected. Not sure why.




    KetaiGesture gesture;

    int currentScreen = 0;
    ArrayList<Screen> screensArrayList;
    ArrayList<Button> buttonsArrayList;

//UDP code:///////////////////////////////////////////////////


    UDP udp; // define the UDP object
    String ip = "192.168.1.1"; // change in constructor now
    String ip2 = "192.168.1.78"; // change this with android saved settings for testing
    String ip3 = "192.168.1.79"; // the remote IP address (Accelerometer)

    //test add more poi:
    String ip4 = "192.168.8.80";
    String ip5 = "192.168.8.81";
    String ip6 = "192.168.8.82";
    String ip7 = "192.168.8.83";
    boolean onRouter = false;
    //try https://stackoverflow.com/questions/40155591/set-static-ip-and-gateway-programmatically-in-android-6-x-marshmallow
    //Android needs static ip here.
//String ip = "192.168.1.77"; // the remote IP address (Huawei HG532C)
//String ip2 = "192.168.1.78"; // the remote IP address (Huawei HG532C)

    int UDPport = 2390; // the destination port
//////////////////////////////////////////////////////////////

//file import code:///////////////////////////////////////////////////////////////////////////////////
    String sequence1Path = "/sdcard/Pictures/SmartPoi/WirelessSmartPoi/Sequence1/"; // path for Android
    String music1Path = ""; // PLACEHOLDER UNTIL I CAN GET THE PATH TO WORK
    String timeLine1Path = "/sdcard/Pictures/SmartPoi/WirelessSmartPoi/TimeLine1/"; // path for Android
    PImage[] sequence1Images;
    String[] sequence1Filenames;

    int whichPic = 0;
    int sequenceShowTime = 3000; //change every 3 seconds
    int timer = 0;
    PGraphics pg;
    boolean showOnce = true;
    Button buttonSwitchScreen;
    Button buttonSaveBattery;
    Button buttonBackwards;
    boolean backwards = false;

    PImage sunflower;
    boolean screenBlank = false;
    //brightness idea:
    float BRT = 1.0f;
    boolean showSlider = false;
    ControlP5 cp5;
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
        background(100);
        orientation(PORTRAIT);

        //////////////////////////////UDP Code://////////////////////
        udp = new UDP( this, UDPport ); // create a new datagram connection on port 2390
//  udp.log( true ); // <\u2013 printout the connection activity
        udp.listen( true ); // and wait for incoming message
        //////////////////////////////////////////////////////////////////////
        //brightness slider:
        cp5 = new ControlP5(this);

        //some buttons://////////////////////////////////////////////////////////////////////////
        sunflower = loadImage("sunflower.jpg");
        buttonSwitchScreen = new Button (  width/6*5, height/6*5,
                width/6, height/6,
                true, color (0, 0, 0), //Black button
                true, color (100),
                "CHANGE\nSPEED",
                "",
                1,
                sunflower);
        buttonSaveBattery = new Button (  width/6*4, height/6*5,
                width/6, height/6,
                true, color (0, 0, 0), //Black button
                true, color (100),
                "BATTERY\nSAVE",
                "",
                1,
                sunflower);
        buttonBackwards = new Button (  width/6*3, height/6*5,
                width/6, height/6,
                true, color (0, 0, 0), //Black button
                true, color (100),
                "IMAGE\nBACKWARDS",
                "",
                1,
                sunflower);
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        gesture = new KetaiGesture(this);
        //noStroke();
        smooth();
        ellipseMode(CENTER);
        screensArrayList = new ArrayList<Screen>();
        for (int i = 0; i<10; i++) {
            screensArrayList.add(new Screen(i)); //need path to images here? Or make new folder every time...
        }
        pg = createGraphics(36, 36);

        brightnessSlider = cp5.addSlider("BRT")
                .setPosition(0, height/6*2)
                .setSize(width/3, height/6*3)
                .setRange(0, 1) //multiplies by sequenceShowTime
                .setValue(1.0f)
//                .setValue(0.0f) //0 for select one pic at a time 1.0 is for change every sequenceShowTime seconds
                .setColorForeground(color(150, 100, 100, 100)) //now opaque?
                .setColorBackground(color(50, 0, 0, 100))
                .setColorActive(color(100, 50, 50, 100))
                .setVisible(false) //no more menu button need button to activate this
        ;
        ellipseMode(CORNER);

        pg.beginDraw();
        pg.background(0);
        pg.endDraw();
    } //end setup()






    public void draw() {
         if (!screenBlank) {
            if (screensArrayList.get(currentScreen).buttonsArrayList.size()<2) {
                whichPic = 0;
            }
//             screensArrayList.get(currentScreen).showBlank();
            screensArrayList.get(currentScreen).showOnceOptimized();
        } else {
            if (screensArrayList.get(currentScreen).buttonsArrayList.size()<2) {
                whichPic = 0;
            }
            screensArrayList.get(currentScreen).showBlank();
        }
        //println(currentScreen);
    } //end draw()

    public void BRT(float theBRT) { //called on slider change?
        showOnce = true;
        BRT = theBRT;
        sequenceShowTime = PApplet.parseInt(6000*BRT);
    }

    public void mousePressed() {
        if (buttonSwitchScreen.over()) {
            showSlider = !showSlider;
            /////////////////////////////////////brightness slider/////////////////////////////////////////////////////
            if (showSlider) {
                //text(BRT, 100, 100);
                brightnessSlider.setVisible(true);
                showOnce = true; //redraw screen
            } else {
                brightnessSlider.setVisible(false);
                showOnce = true; //redraw screen
            }
            //////////////
//            screenBlank = !screenBlank;
        }
        if (buttonSaveBattery.over()) {
            screenBlank = !screenBlank;
        }
        if (buttonBackwards.over()) {

            backwards = !backwards;
        }
        if(sequenceShowTime<10){ //slider is on 0, so we choose the pic manually...
//            brightnessSlider.setVisible(false);
            for (int i = 0; i < screensArrayList.get(currentScreen).buttonsArrayList.size(); i++) {
                if (screensArrayList.get(currentScreen).buttonsArrayList.get(i).over()) {
                    showOnce = true; //redraw screen
                    whichPic = i;

                }
            }
        }
    }

    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == MENU) { //damn they removed this in Nougat!!!!!! Bastards!
                showSlider = !showSlider;
                /////////////////////////////////////brightness slider/////////////////////////////////////////////////////
                if (showSlider) {
                    //text(BRT, 100, 100);
                    brightnessSlider.setVisible(true);
                    showOnce = true; //redraw screen
                } else {
                    brightnessSlider.setVisible(false);
                    showOnce = true; //redraw screen
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }
        }
    }


    //ketai gesture code:
//the coordinates of the start of the gesture,
//end of gesture and velocity in pixels/sec
    public void onFlick( float x, float y, float px, float py, float v)
    {
        //background(0); //refresh
        screenBlank = false; //show pics again on swipe only
        //println("flicked");
        if (px<displayWidth/10) {
            //    if (x-px > 100) {
            background(100);
            whichPic = 0;
            showOnce = true;
            currentScreen = 6;
            //    }
        } else {
            if (x-px > 100) { //300 for S2
                background(100);
                whichPic = 0;
                showOnce = true;
                currentScreen--;
                if (currentScreen<0) {
                    currentScreen = 0;
                }
            }
            if (px-x > 100) { //300 for S2
                background(100);
                whichPic = 0;
                showOnce = true;
                currentScreen++;
                if (currentScreen>=screensArrayList.size()) {
                    currentScreen = 0;
                }
            }
        }
    }

    public boolean surfaceTouchEvent(MotionEvent event) {

        //call to keep mouseX, mouseY, etc updated
        super.surfaceTouchEvent(event);

        //forward event to class for processing
        return gesture.surfaceTouchEvent(event);
    }

    // Create Check Directory //////////////////////////////////////////
//from https://forum.processing.org/one/topic/how-do-i-write-data-to-the-phone-or-sd-card-memory.html
//will create the directory if it doesn't already exist! nice.
    public void createCheckDirectory(String path) {
        try {
            //String dirName = "/storage/sdcard0/Pictures/SmartPoi/36px/"; //using fullPath
            File newFile = new File(path);
            newFile.mkdirs();
            if (newFile.exists()) {
                //println("Directory Exists: " + path);
                if (newFile.isDirectory()) {
                    //println("isDirectory = true...");
                } else {
                    //println("isDirectory = false...");
                }
            } else {
                //println("Directory Doesn't Exist: " + path);
            }
        }
        catch(Exception e) {
            //println("Exception creating folder... ");
            //e.printStackTrace();
        }
    }
//end Create Check Directory////////////////////////////////////

    //for file open:///////////////////////////////////////
//should have exception handling - error if no files in directory!
    public String[] loadFilenames(String path) {
        File folder = new File(path);
        FilenameFilter filenameFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if(name.endsWith(".jpg")){
                    return name.toLowerCase().endsWith(".jpg"); // change this to any extension you want
                }
                else if(name.endsWith(".jpeg")){
                    return name.toLowerCase().endsWith(".jpeg");
                }
                else if(name.endsWith(".png")){
                    return name.toLowerCase().endsWith(".png");
                }
                else{
                    return false;
                }

            }
        };
        return folder.list(filenameFilter);
    }

    //receive UDP://////////////////////////////////////////////
//receive from D1AccelerometerSender
    public void receive( byte[] data ) { //  default handler

//        public void receive( byte[] data, String ip, int port ) { // <\u2013 extended handler
//  testing = true;
//println("port is: " + port + " ip is: " + ip);

        for (int i=0; i < data.length; i++) {
            if(data[i] == 97){ //ascii 'a'
                print("processing data....a");
                whichPic++;
                println("changing pic");
                if(whichPic>screensArrayList.get(currentScreen).buttonsArrayList.size()-1){
                    whichPic = 0;
                }
            }
            else if(data[i] == 99){ //ascii 'c'
                print("processing data....c");
                //background(0); //refresh
                screenBlank = false; //show pics again on swipe only
                println("change screen!");
                background(100);
                whichPic = 0;
                showOnce = true;
                currentScreen++;
                if (currentScreen>=screensArrayList.size()) {
                    currentScreen = 0;
                }
            }
            else{
                print(PApplet.parseChar(data[i]));
            }
        }
        println();
    }


// =======================================================================
    class Button {
        // represents a rect / button
        float x;  // pos
        float y;
        float w=0;  // size
        float h=0;
        // color
        boolean hasColorFill=true;   // if it has filling
        int colorFill;             // what is it
        //
        boolean hasColorStroke=true; // if it has an outline
        int  colorStroke;          // what is it
        // other
        String text="";           // text to display
        String textMouseOver="";  // text for mouse over
        int commandNumber;        // each button has a command number that gets executed when clicked
        PImage img;
        ///so where does a new button info get saved? need to check persistence
        //
        // constructor
        Button (  float x_, float y_,
                  float w_, float h_,
                  boolean hasColorFill_, int cFill_,
                  boolean hasColorStroke_, int cStroke_,
                  String text_,
                  String textMouseOver_,
                  int commandNumber_,
                  PImage img_) {
            x=x_;
            y=y_;
            w=w_;
            h=h_;
            // color fill
            hasColorFill=hasColorFill_;
            colorFill=cFill_;
            // color stroke
            hasColorStroke = hasColorStroke_;
            colorStroke = cStroke_;
            //
            text=text_;
            textMouseOver=textMouseOver_;
            commandNumber=commandNumber_;
            img=img_;
        } // constructor
        //
        public String name() {
            return (text);
        }

        public PImage pic() {
            return (img);
        }
        //
        public void display () {
            if (hasColorFill)
                fill(colorFill);
            else
                noFill();
            if (hasColorStroke)
                stroke(colorStroke);
            else
                noStroke();
            rect(x, y, w, h);
            fill(255);
            textAlign(CENTER);
            textSize(18);
            text(text, x+w/2, y+h/2);
            if (img !=null && img !=sunflower) { //sunflower img for colour only buttons... hack!
//////////////////////////////////////brightness thing///////////////////////////////
                //tint(BRT*255);
////////////////////////////////////////////////////////////////////////////////////
                image(img, x, y, w, h);
            }
        } // method
        //
        public boolean over() {
            return (mouseX>x && mouseX<x+w&& mouseY>y&&mouseY<y+h);
        } // func
        //
        public int filler() {
            return (colorFill);
        }
        public void showMouseOver() {
            // yellow mouse over help text
            if (!textMouseOver.equals("")) {
                float pos=mouseX; //pos=x; // or  mouseX;
                // right screen border?
                if (pos+textWidth(textMouseOver)+10>width) {
                    pos=width-textWidth(textMouseOver)-12;
                }
                //fill(255, 255, 44);
                //rect(pos, y+h+14, textWidth(textMouseOver)+2, 20);
                //fill(0);
                text ( textMouseOver, pos+2, y+35);
            }
            //
        }
        //
    } // class
//

    //for Arrays.sort()
    boolean doPG = true;
    boolean uploadNow = false;
    String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    class Screen {
        int num;
        ArrayList<Button> buttonsArrayList;
        PImage[] images;
        String[] filenames;
        //generic base for all dynamic collection paths:
        String collectionPath = Environment.getExternalStorageDirectory().getPath() + "/Pictures/SmartPoi/WirelessSmartPoi/Collection";
        String collectionPath2 = Environment.getExternalStorageDirectory().getPath() + "/Pictures/SmartPoi/WirelessSmartPoi72px/Collection";
        // constructor
        Screen (  int num_ ) {
            num=num_;
            if(numPixels == 36) {
                createCheckDirectory(collectionPath + str(num) + "/");
            //file open code:///////////////////////////////////////
            println("collection path is: " + collectionPath + str(num));
            filenames = loadFilenames(collectionPath + str(num) + "/");
            Arrays.sort(filenames); //sorts filenames in alphabetical order??? I hope so!
            println("files:");
            println(filenames);
            images = new PImage[filenames.length];
            } else if(numPixels == 72){
                createCheckDirectory(collectionPath2 + str(num) + "/");
                //file open code:///////////////////////////////////////
                println("collection path is: " + collectionPath2 + str(num));
                filenames = loadFilenames(collectionPath2 + str(num) + "/");
                Arrays.sort(filenames); //sorts filenames in alphabetical order??? I hope so!
                println("files:");
                println(filenames);
                images = new PImage[filenames.length];
            }
            //below to load all images into array - too big images = out of memory error!
            for (int i=0; i < filenames.length; i++) {
                if(numPixels == 36) {
                    images[i] = loadImage(collectionPath + str(num) + "/" + filenames[i]);
                } else if(numPixels == 72){
                    images[i] = loadImage(collectionPath2 + str(num) + "/" + filenames[i]);
                }
                if(images[i].width == numPixels){
                    //don't resize therefore avoiding the bug???
                }
                else{
                    images[i].resize(numPixels, 0); //there is a bug with resize: https://github.com/processing/processing-android-archive/issues/22
                    //try using png images instead...
                    //right now just avoid using any image with size of ( width == 36 || height == 36 ) as it won't show up for some reason...???
                    //remember to use -adaptive-resize
                }

            }
            //end file open code//////////////////////////////////////
            buttonsArrayList = new ArrayList<Button>();
            int buttRowNum = 0;
            int buttColNum = 0;
            int numberOfButtons = filenames.length;   //for testing only... filenames location will need to be part of screen object.
            int a = ceil(sqrt(numberOfButtons));
            for (int i=0; i < filenames.length; i++) {
                buttonsArrayList.add(new Button(
                        buttRowNum*(width/a), buttColNum*(height/a),
                        width/a, height/a,
                        true, color (random(255), random(255), random(255)), //random
                        true, color (255),
                        str(i),
                        str(i),
                        i,
                        images[i]));

                if (i==0) {
                    buttRowNum++;
                } else {
                    if (buttRowNum >= (a-1)) { //if there are 8 pics in a row
                        buttColNum++;
                        buttRowNum = 0;
                    } else {
                        buttRowNum++;
                    }
                }
            }
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //add ControlP5 items and see if they work...
        } // constructor
        //Method

        public void showOnceOptimized() {
            textSize(32);
            stroke(255);
            fill(255);
            text("Screen " + num, width / 5 * 2, height / 5 * 4 + height / 10);
            if (showOnce) {
                println("showOnce Screen: " + num);
                for (int i = 0; i < buttonsArrayList.size(); i++) {
                    buttonsArrayList.get(i).display();
                    buttonSwitchScreen.display();
                    buttonSaveBattery.display();
                    buttonBackwards.display();
                }
                showOnce = false;
            }

            if (millis() > 0) {
                if (millis() - timer >= sequenceShowTime) { //every 2 seconds
                    println(millis());
                    /////////////////////////////////////////////////////////////////////////PG once//////////////////////////////////////////////////////////////////////////
                    if (whichPic >= buttonsArrayList.size() - 1) {
                        if (sequenceShowTime > 0) { // if slider is 0 don't change pics
                            whichPic = 0;
                        }
                        pg = createGraphics(buttonsArrayList.get(whichPic).pic().width, buttonsArrayList.get(whichPic).pic().height);
                        fill(0, 0, 0);
                        ellipse(buttonsArrayList.get(buttonsArrayList.size() - 1).x, buttonsArrayList.get(buttonsArrayList.size() - 1).y, buttonsArrayList.get(whichPic).w / 2, buttonsArrayList.get(whichPic).w / 2);
                    } else {
                        if (sequenceShowTime > 0) { // if slider is 0 don't change pics
                            whichPic = whichPic + 1;
                        }
                        pg = createGraphics(buttonsArrayList.get(whichPic).pic().width, buttonsArrayList.get(whichPic).pic().height);
                        fill(0, 0, 0);
                        //set previous ellipse to black - only in sequence mode
                        if (sequenceShowTime > 0) { //avoid null pointer here!
                            ellipse(buttonsArrayList.get(whichPic - 1).x, buttonsArrayList.get(whichPic - 1).y, buttonsArrayList.get(whichPic).w / 2, buttonsArrayList.get(whichPic).w / 2);
                        }
                    }
                    pg.beginDraw();
                    pg.image(buttonsArrayList.get(whichPic).pic(), 0, 0);
                    println("whichPic is now: " + whichPic);
                    pg.endDraw();
                    /////////////////////////////////////////////////////////////////
                    //convert to bin and upload via post request here:
                    //using backwards button for save .bin test: it works!
                    if(backwards) {
                        String nameAlphabet = str(alphabet.charAt(whichPic));
                        message1(pg, nameAlphabet);
                    }
                    //////////////////////////////////////////////////////////////////
                    fill(255, 0, 0);
                    ellipse(buttonsArrayList.get(whichPic).x, buttonsArrayList.get(whichPic).y, buttonsArrayList.get(whichPic).w / 2, buttonsArrayList.get(whichPic).w / 2);
//                    println("ellipse here");
                    timer = millis(); //test as well
                }//this is a test
                if (!testing) {
                    if(backwards) {
                        sendPGraphicsToPoiBackwards(pg, whichPic);
                    } else {
                    sendPGraphicsToPoi(pg, whichPic);
                    }
//                    println("sending");
                }
//            background(255, 0, 0);
//                println("end showOnceOptimized");
            }
        }



        /*

        public void showOnceOptimized() {
            //println(num);
            //make box for text here?
            textSize(32);
            stroke(255);
            fill(255);
            text("Screen " + num, width/5*2, height/5*4+height/10);
            //Testing show buttons once:
            if (showOnce) {
                println("showOnce Screen: " + num);
                for (int i = 0; i < buttonsArrayList.size (); i++) {
                    buttonsArrayList.get(i).display();
                    buttonSwitchScreen.display();
                    buttonSaveBattery.display();
                }
                showOnce = false;
            }
            //do PG once at beginning, don't waste time on it after!
//if(millis() - timer == sequenceShowTime){// && millis() - timer <= sequenceShowTime + 10 ){
//  print("doPG is getting done ");
//  println(timer);
//  doPG = true;
//  timer = millis();
//}

//if(doPG){
            if (millis() - timer >= sequenceShowTime) { //every 2 seconds
//    println(timer);
    println("doPGTimer " + timer);
                //background(random(255));
//     if (doPG) { //don't waste time calculating this if unneccesary
/////////////////////////////////////////////////////////////////////////PG once//////////////////////////////////////////////////////////////////////////
                if (whichPic >= buttonsArrayList.size()-1) {
                    if(sequenceShowTime>0){ // if slider is 0 don't change pics
                        whichPic = 0;
                    }
                    pg = createGraphics(buttonsArrayList.get(whichPic).pic().width, buttonsArrayList.get(whichPic).pic().height);
                    fill(0, 0, 0);
                    ellipse(buttonsArrayList.get(buttonsArrayList.size()-1).x, buttonsArrayList.get(buttonsArrayList.size()-1).y, buttonsArrayList.get(whichPic).w/2, buttonsArrayList.get(whichPic).w/2);
                } else {
                    if(sequenceShowTime>0){ // if slider is 0 don't change pics
                        whichPic = whichPic+1;
                    }
                    pg = createGraphics(buttonsArrayList.get(whichPic).pic().width, buttonsArrayList.get(whichPic).pic().height);
                    fill(0, 0, 0);
                    //set previous ellipse to black - only in sequence mode
                    if(sequenceShowTime>0){ //avoid null pointer here!
                        ellipse(buttonsArrayList.get(whichPic-1).x, buttonsArrayList.get(whichPic-1).y, buttonsArrayList.get(whichPic).w/2, buttonsArrayList.get(whichPic).w/2);
                    }
                }
//      timer = millis();
//    }//this was important, disabled for test only
//    pg = createGraphics(buttonsArrayList.get(whichPic).pic().width, buttonsArrayList.get(whichPic).pic().height);
                pg.beginDraw();
                pg.image(buttonsArrayList.get(whichPic).pic(), 0, 0);
                println("whichPic is now: " + whichPic);
// //////////////////////////brightness?/////////////////////////////////////////////////////////////////////////
//updateMask();
//pg.mask(mymask);
                //pg.tint(BRT); tint doesn't work on PGraphics
// /////////////////////////////////////////////////////////////////////////////////////////////////////////////
//pg.tint(255, 255);
                pg.endDraw();
                fill(255, 0, 0);
                ellipse(buttonsArrayList.get(whichPic).x, buttonsArrayList.get(whichPic).y, buttonsArrayList.get(whichPic).w/2, buttonsArrayList.get(whichPic).w/2);
                println("ellipse here");
                timer = millis(); //test as well
            }//this is a test

//    doPG = false;
//  }//end if(doPG)
//else{
            //sort out whichPic here
//}
//    fill(255, 0, 0);
            //red ellipse indicates current pic
//    ellipse(buttonsArrayList.get(whichPic).x, buttonsArrayList.get(whichPic).y, buttonsArrayList.get(whichPic).w/2, buttonsArrayList.get(whichPic).w/2);

            if (!testing) {
                sendPGraphicsToPoi(pg, 0);
            }
            background(255, 0, 0);
            println("end showOnceOptimized");
        }
*/
        //hmmmm mask where was I going with this, fades or what? look at PGraphics_Pattern for implementation
//  void updateMask()
//{
//  mymask.beginDraw();
//  mymask.background(0);
//  mymask.endDraw();
////  offScreen.mask(mymask);
//}

        public void showBlank() {
            //println(num);
            background(0);
            textSize(32);
            stroke(100);
            fill(100);
            //text("Screen " + num, width/5*2, height/5*4+height/10); //DISABLE THIS TO PREVENT BURN-IN ON SCREEN?!
            stroke(100);
            fill(0);
            ellipse(random(0, 600), random(0, 600), 50, 50);
            //buttonSwitchScreen.display();
            //Testing show buttons once:
//    if (showOnce) {
//      for (int i = 0; i < buttonsArrayList.size (); i++) {
//        buttonsArrayList.get(i).display();
//        buttonSwitchScreen.display();
//      }
//      showOnce = false;
//    }

            if (millis() - timer >= sequenceShowTime) { //every 2 seconds
                //background(random(255));

                if (whichPic >= buttonsArrayList.size()-1) {
                    if(sequenceShowTime>0){ // if slider is 0 don't change pics
                        whichPic = 0;
                    }
                    pg = createGraphics(buttonsArrayList.get(whichPic).pic().width, buttonsArrayList.get(whichPic).pic().height);
                    //fill(0, 0, 0);
                    //ellipse(buttonsArrayList.get(buttonsArrayList.size()-1).x, buttonsArrayList.get(buttonsArrayList.size()-1).y, buttonsArrayList.get(whichPic).w/2, buttonsArrayList.get(whichPic).w/2);
                } else {
                    if(sequenceShowTime>0){ // if slider is 0 don't change pics
                        whichPic = whichPic+1;
                    }
                    pg = createGraphics(buttonsArrayList.get(whichPic).pic().width, buttonsArrayList.get(whichPic).pic().height);
                    fill(0, 0, 0);
                    //set previous ellipse to black - only in sequence mode
                    if(sequenceShowTime>0){ //avoid null pointer here!
                        //ellipse(buttonsArrayList.get(whichPic-1).x, buttonsArrayList.get(whichPic-1).y, buttonsArrayList.get(whichPic).w/2, buttonsArrayList.get(whichPic).w/2);
                    }
                    //timer = millis();
                }
//    pg = createGraphics(buttonsArrayList.get(whichPic).pic().width, buttonsArrayList.get(whichPic).pic().height);
                pg.beginDraw();
                pg.image(buttonsArrayList.get(whichPic).pic(), 0, 0);
                //println("whichPic is now: " + whichPic);
                // //////////////////////////brightness?/////////////////////////////////////////////////////////////////////////
                //pg.tint(BRT); //tint not working on pg
// /////////////////////////////////////////////////////////////////////////////////////////////////////////////
                pg.endDraw();
                fill(255, 0, 0);
                ellipse(buttonsArrayList.get(whichPic).x, buttonsArrayList.get(whichPic).y, buttonsArrayList.get(whichPic).w/2, buttonsArrayList.get(whichPic).w/2);

                timer = millis(); //test as well
            }//this is a test

//    doPG = false;
//  }//end if(doPG)
//else{
            //sort out whichPic here
//}
//    fill(255, 0, 0);
            //red ellipse indicates current pic
//    ellipse(buttonsArrayList.get(whichPic).x, buttonsArrayList.get(whichPic).y, buttonsArrayList.get(whichPic).w/2, buttonsArrayList.get(whichPic).w/2);

            if (!testing) {
                if(backwards) {
                    sendPGraphicsToPoiBackwards(pg, whichPic);
                } else {
                    sendPGraphicsToPoi(pg, whichPic);
                }
            }
        }


        //
    } // class
//

    public byte pixelConverter(int red, int green, int blue) {
        byte encodedRGB;
        //int   r, g, b;
        //only testing nothing else:
        //println(red + " " + green + " " + blue);
        encodedRGB = PApplet.parseByte((red & 0xE0) | ((green & 0xE0)>>3) | (blue >> 6));
        return encodedRGB;
    }

    boolean testing = false;
    int r, g, b;
    int numPixels = 36; //this can change

    DatagramSocket udpSocket;
    DatagramSocket udpSocket2;

    public void sendPGraphicsToPoi(PGraphics pgSend, int sendOpt) {

        if(onRouter){
            numPixels = 72; //to accommodate big poi! not a real solution?! how does it look...
        }
        int pixelCounter = 0;
        int pixelCounter2 = 0;
        byte[] message = new byte[numPixels];
        byte[] bigpx = new byte[72];
        for (int a = 0; a < pgSend.width*pgSend.height; a++) {
//          for (int a = pgSend.width*pgSend.height-1; a > -1; a--) {
            if (pixelCounter == 0) {
                //port.write(byte(startByte));
            }
            //float dimmerR = red(pgSend.pixels[a])-(red(pgSend.pixels[a])*brightnessDown);
            float dimmerR = (pgSend.pixels[a] >> 16 & 0xFF)-((pgSend.pixels[a] >> 16 & 0xFF));
            //float dimmerG = green(pgSend.pixels[a])-(green(pgSend.pixels[a])*brightnessDown);
            float dimmerG = (pgSend.pixels[a] >> 8 & 0xFF)-((pgSend.pixels[a] >> 8 & 0xFF));
            //float dimmerB = blue(pgSend.pixels[a])-(blue(pgSend.pixels[a])*brightnessDown);
            float dimmerB = (pgSend.pixels[a] & 0xFF)-((pgSend.pixels[a] & 0xFF));

            //    r = (int) red(pgSend.pixels[a]);
            //    g = (int) green(pgSend.pixels[a]);
            //    b = (int) blue(pgSend.pixels[a]);
            //r = (int) red(pgSend.pixels[a]) - (int) dimmerR;
            r = (int) pgSend.pixels[a] >> 16 & 0xFF - (int) dimmerR;
            //g = (int) green(pgSend.pixels[a]) - (int) dimmerG;
            g = (int) pgSend.pixels[a] >> 8 & 0xFF - (int) dimmerG;
            //b = (int) blue(pgSend.pixels[a]) - (int) dimmerB;
            b = (int) pgSend.pixels[a] & 0xFF - (int) dimmerB;
            //port.write(pixelConverter(r, g , b)+127);
            ////////UDP Send://///////////////////////////////////

            byte Y = PApplet.parseByte(pixelConverter(r, g, b)+127);
            message[pixelCounter] = Y;
            bigpx[pixelCounter2] = Y; //72px test code

            //need to pre-load pic into array or it slows down serial!
            //delay(1);
            pixelCounter++;
            pixelCounter2++;

            if (pixelCounter == numPixels){ // this may have an error if any image is < 36px wide. try below:
//                if (pixelCounter == pgSend.width){
                if(!testing){
                    /*
                    //below seems to work the same as processing method, not much difference...
                    //method from: http://msdalp.github.io/2014/03/09/Udp-on-Android/
                    //with socket close from: http://android-er.blogspot.co.za/2016/05/android-datagramudp-client-example.html
                    try {
                        udpSocket = new DatagramSocket(UDPport);
                        InetAddress serverAddr = InetAddress.getByName(ip);
                        DatagramPacket packet = new DatagramPacket(message, message.length, serverAddr, UDPport);
                        udpSocket.send(packet);
                    } catch (SocketException e) {
                        Log.e("Udp:", "Socket Error:", e);
                    } catch (IOException e) {
                        Log.e("Udp Send:", "IO Error:", e);
                    }finally {
                        if (udpSocket != null) {
                            udpSocket.close(); //this seems wrong, close every time...?
                        }
                    }
                    //and try again for another ip

                    try {
                        udpSocket2 = new DatagramSocket(UDPport);
                        InetAddress serverAddr2 = InetAddress.getByName(ip2);
                        DatagramPacket packet2 = new DatagramPacket(message, message.length, serverAddr2, UDPport);
                        udpSocket2.send(packet2);
                    } catch (SocketException e) {
                        Log.e("Udp:", "Socket Error:", e);
                    } catch (IOException e) {
                        Log.e("Udp Send:", "IO Error:", e);
                    }finally {
                        if (udpSocket2 != null) {
                            udpSocket2.close(); //this seems wrong, close every time...??
                        }
                    }
*/
                    //old:
                    udp.send(message, ip, UDPport);
                    udp.send(message, ip2, UDPport); 
                    if(onRouter){
                        udp.send(message, ip4, UDPport);
                        udp.send(message, ip5, UDPport);
                        //big poi:
                        udp.send(message, ip6, UDPport);
                        udp.send(message, ip7, UDPport);

                    }
                }
                pixelCounter = 0;
            }

            if (pixelCounter2 == pgSend.width*2){
                if(!testing){
//                    udp.send(message, ip, UDPport);
//                    udp.send(message, ip2, UDPport);
                    if(onRouter){
//                        udp.send(message, ip4, UDPport);
//                        udp.send(message, ip5, UDPport);
                        //big poi:
                        udp.send(bigpx, ip6, UDPport);
                        udp.send(bigpx, ip7, UDPport);

                    }
                }
                pixelCounter2 = 0;
            }

    /*
    if (pixelCounter == pgSend.width*2) { //72px test code
      if (!testing) {
        //      udp.send(message, ip, UDPport); //disable for testing
        //      udp.send(message, ip2, UDPport); //disable for testing
        udp.send(bigpx, ip, UDPport); //disable for testing
        udp.send(bigpx, ip2, UDPport); //disable for testing
      }
      pixelCounter = 0;
    }
*/
            //delay(100);
        }
    }


    public void sendPGraphicsToPoiBackwards(PGraphics pgSend, int sendOpt) {





        int pixelCounter = pgSend.width-1;
        byte[] message = new byte[numPixels];
        byte[] bigpx = new byte[72];
//        for (int a = 0; a < pgSend.width*pgSend.height; a++) {
        for (int a = pgSend.width*pgSend.height-1; a > -1; a--) { //rotates 180 degrees this does!
//            if (pixelCounter == 0) {
//                //port.write(byte(startByte));
//            }
            //float dimmerR = red(pgSend.pixels[a])-(red(pgSend.pixels[a])*brightnessDown);
            float dimmerR = (pgSend.pixels[a] >> 16 & 0xFF)-((pgSend.pixels[a] >> 16 & 0xFF));
            //float dimmerG = green(pgSend.pixels[a])-(green(pgSend.pixels[a])*brightnessDown);
            float dimmerG = (pgSend.pixels[a] >> 8 & 0xFF)-((pgSend.pixels[a] >> 8 & 0xFF));
            //float dimmerB = blue(pgSend.pixels[a])-(blue(pgSend.pixels[a])*brightnessDown);
            float dimmerB = (pgSend.pixels[a] & 0xFF)-((pgSend.pixels[a] & 0xFF));

            //    r = (int) red(pgSend.pixels[a]);
            //    g = (int) green(pgSend.pixels[a]);
            //    b = (int) blue(pgSend.pixels[a]);
            //r = (int) red(pgSend.pixels[a]) - (int) dimmerR;
            r = (int) pgSend.pixels[a] >> 16 & 0xFF - (int) dimmerR;
            //g = (int) green(pgSend.pixels[a]) - (int) dimmerG;
            g = (int) pgSend.pixels[a] >> 8 & 0xFF - (int) dimmerG;
            //b = (int) blue(pgSend.pixels[a]) - (int) dimmerB;
            b = (int) pgSend.pixels[a] & 0xFF - (int) dimmerB;
            //port.write(pixelConverter(r, g , b)+127);
            ////////UDP Send://///////////////////////////////////

            byte Y = PApplet.parseByte(pixelConverter(r, g, b)+127);
            message[pixelCounter] = Y;
            //bigpx[pixelCounter] = Y; //72px test code

            //need to pre-load pic into array or it slows down serial!
            //delay(1);
            pixelCounter--;

            if (pixelCounter == -1) {
                if(!testing){

                    //old:
                    udp.send(message, ip, UDPport); //disable for testing
                    udp.send(message, ip2, UDPport); //disable for testing
                }
                pixelCounter = pgSend.width-1;
            }


            //delay(100);
        }


    }

    void message1(PImage cImg, String name) {
        String messagePath = Environment.getExternalStorageDirectory().getPath() + "/Pictures/SmartPoi/WirelessSmartPoi/Messages1/";

        int   r, g, b;
        byte encodedRGB;
        cImg.loadPixels();
        int len1 = cImg.height;
        int width1 = cImg.width;
        int cImgSize = len1*width1;
        byte[] testBin = new byte[cImgSize];

        for (int y = 0; y < cImg.height; y++) {    //height
            if (y == len1-1) {
                    for (int x = 0; x < cImg.width; x++) {   //width
                    int loc = x + y*cImg.width;

                    if(x == 0) {
                        r = (int) red(cImg.pixels[loc]);
                        g = (int) green(cImg.pixels[loc]);
                        b = (int) blue(cImg.pixels[loc]);
                        encodedRGB = PApplet.parseByte((r & 0xE0) | ((g & 0xE0)>>3) | (b >> 6));
                        testBin[loc] = encodedRGB;
                    } else if(x == (width1-1)) {
                        r = (int) red(cImg.pixels[loc]);
                        g = (int) green(cImg.pixels[loc]);
                        b = (int) blue(cImg.pixels[loc]);
                        encodedRGB = PApplet.parseByte((r & 0xE0) | ((g & 0xE0)>>3) | (b >> 6));
                        testBin[loc] = encodedRGB;
//                    output.println("};");
//                    output.println("struct pattern " + name + " = {" + width1 + ", " + len1 + "," + name + "Data};"); //not needed?
                    } else {
                        r = (int) red(cImg.pixels[loc]);
                        g = (int) green(cImg.pixels[loc]);
                        b = (int) blue(cImg.pixels[loc]);
                        encodedRGB = PApplet.parseByte((r & 0xE0) | ((g & 0xE0)>>3) | (b >> 6));
                        testBin[loc] = encodedRGB;
                    }

                }//end for x
                break;
            }//end if y
            else { //if y
                for (int x = 0; x < cImg.width; x++) {   //width
                    int loc = x + y*cImg.width;
                    if(x == 0) {
                        r = (int) red(cImg.pixels[loc]);
                        // println("r is " + r);
                        g = (int) green(cImg.pixels[loc]);
                        b = (int) blue(cImg.pixels[loc]);
                        encodedRGB = PApplet.parseByte((r & 0xE0) | ((g & 0xE0)>>3) | (b >> 6));
                        testBin[loc] = encodedRGB;
                    } else if(x == (width1-1)) {
                        r = (int) red(cImg.pixels[loc]);
                        g = (int) green(cImg.pixels[loc]);
                        b = (int) blue(cImg.pixels[loc]);
                        encodedRGB = PApplet.parseByte((r & 0xE0) | ((g & 0xE0)>>3) | (b >> 6));
                        testBin[loc] = encodedRGB;
                    } else {
                        r = (int) red(cImg.pixels[loc]);
                        g = (int) green(cImg.pixels[loc]);
                        b = (int) blue(cImg.pixels[loc]);
                        encodedRGB = PApplet.parseByte((r & 0xE0) | ((g & 0xE0)>>3) | (b >> 6));
                        testBin[loc] = encodedRGB;
                    }
                }//end for x
            }//end if y
        }//end for y
        saveBytes(messagePath + name + ".bin", testBin);

        //send:
        String url ="http://192.168.1.1/edit";
        try {
            upload(url, new File(messagePath + name + ".bin"));
        } catch (java.io.IOException e){

        }
    }

    //OkHttp send:
    public void upload(String url, File file) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Response response = client.newCall(request).execute();
    }
/*
    public void sendPGraphicsToPoi(PGraphics pgSend, int sendOpt) {
        int pixelCounter = 0;
        byte[] message = new byte[36]; // change to new byte[numPixels] - from TimelineOnly.java
        //byte[] bigpx = new byte[72];
        for (int a = 0; a < pgSend.width*pgSend.height; a++) {

            if (pixelCounter == 0) {
                //port.write(byte(startByte));
            }
//need something like: if(red < minimum red) red = minimum red
            r = pgSend.pixels[a] >> 16 & 0xFF;
            g = pgSend.pixels[a] >> 8 & 0xFF;
            b = pgSend.pixels[a] & 0xFF;

            ////////UDP Send://///////////////////////////////////
            switch(sendOpt) { //might still use this...??? how to tell if pic is text though?
                case 0:
                {
                }
                break;
                case 1: //text
                {
                    if (r>0) {
                        r=100;
                    }
                    if (g>0) {
                        g=100;
                    }
                    if (b>0) {
                        b=100;
                    }
                }
                break;
                default:
                {
                }
                break;
            }

            byte Y = PApplet.parseByte(pixelConverter(r, g, b)+127);
            message[pixelCounter] = Y;
            //    bigpx[pixelCounter] = Y;

            //need to pre-load pic into array or it slows down serial!
            //delay(1);
            pixelCounter++;

            if (pixelCounter == pgSend.width) {
                if (!testing) {
                    udp.send(message, ip, UDPport); //disable for testing
                    udp.send(message, ip2, UDPport); //disable for testing
                    udp.send(message, ip3, UDPport); // only for testing change this now! On second thoughts, does nothing so no harm here, we need accelerometer!
                    if(udp.isBroadcast()){
                        //println("sent to: " + ip + UDPport);
                    }


                }
                pixelCounter = 0;
            }
        }
    }
*/

//    public int sketchWidth() { return displayWidth; }
//    public int sketchHeight() { return displayHeight; }
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