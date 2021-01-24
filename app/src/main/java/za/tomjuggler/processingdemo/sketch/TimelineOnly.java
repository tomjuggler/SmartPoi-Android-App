package za.tomjuggler.processingdemo.sketch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import controlP5.ControlP5;
import controlP5.Slider;
import hypermedia.net.UDP;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import select.files.*;
import za.tomjuggler.processingdemo.R;

import static android.content.Intent.getIntent;
import static za.tomjuggler.processingdemo.MainActivity.ipa1Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa2Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa3Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa4Saved;
import static za.tomjuggler.processingdemo.MainActivity.ipa5Saved;

public class TimelineOnly extends PApplet {
    private static final String TAG = "TomtestZipFileSelect";

//this is Android Version Fork





//------------------------------------------------------------------------------------

//zip file:
//import java.io.BufferedInputStream;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.BufferedOutputStream;



    String dir="/sdcard/Pictures/SmartPoi/WirelessSmartPoi/TestZip/";







//*********************************************************************
// keep activity alive code import:
//*********************************************************************





//*********************************************************************
//**********************************************************
//partial wake lock import:
//**********************************************************
//import android.app.Application;
//import android.content.Intent;
//import android.os.PowerManager;
//import android.provider.Settings;
//
//PowerManager.WakeLock wl = null;
////**********************************************************
//
////wifi connect:
//import android.net.wifi.WifiManager;
//import android.net.wifi.WifiConfiguration;


//**************************************************************
//apwidgets code:****************************************************
//apwidget library from: https://github.com/MeULEDs/apwidgets
//import apwidgets.*;





//APWidgetContainer widgetContainer;
//APEditText textField;

    boolean isVisible = false;
//************************************************************************




//*************************************************************************
//music:
//APMediaPlayer player;

    MediaPlayer player;
//UDP code:///////////////////////////////////////////////////


    UDP udp; // define the UDP object

    String ip2 = "192.168.1.78"; // change this with android saved settings for testing
    String ip = "192.168.1.1"; // change in constructor now
    String ip3 = "192.168.1.79"; // the remote IP address (Accelerometer)

    //test add more poi:
    String ip4 = "192.168.8.80";
    String ip5 = "192.168.8.81";
    String ip6 = "192.168.8.82";
    String ip7 = "192.168.8.83";
    boolean onRouter = false;


    int UDPport = 2390; // the destination port
//////////////////////////////////////////////////////////////

//file import code:///////////////////////////////////////////////////////////////////////////////////

    //import java.io.File;
//import java.io.FilenameFilter;
    PImage[] images;
    String[] filenames;
    PImage[] timeLineImages;
    String[] timeLineFilenames;
    //String collection1Path = "/storage/sdcard0/Pictures/SmartPoi/WirelessSmartPoi/Sequence1/"; // path for Android
    String collection1Path = "/sdcard/Pictures/SmartPoi/WirelessSmartPoi/Collection6/"; // path for Android
    String sequence1Path = "/sdcard/Pictures/SmartPoi/WirelessSmartPoi/Sequence1/"; // path for Android
    //String music1Path = "/storage/sdcard0/Pictures/SmartPoi/WirelessSmartPoi/Music1/"; // path for Android
//ABOVE NOT WORKING!
    String music1Path = ""; // PLACEHOLDER UNTIL I CAN GET THE PATH TO WORK
    String timeLine1Path = "/sdcard/Pictures/SmartPoi/WirelessSmartPoi/TimeLine1/"; // path for Android

    String PATH_TO_FILE = "/sdcard/Pictures/SmartPoi/WirelessSmartPoi/TimeLine1/smart.mp3";


//String collection1Path = "/home/tom/Pictures/SmartPoi/WirelessSmartPoi/Collection1/"; // path for desktop
//String sequence1Path = "/home/tom/Pictures/SmartPoi/WirelessSmartPoi/Sequence1/"; // path for desktop

    PImage[] sequence1Images;
    String[] sequence1Filenames;
    //String fullPath = "/home/tom/Pictures/SmartPoi/Pictures_for_Ignis_Pixel_32/"; // alternative path for desktop (more pics)
//String fullPath = "/home/tom/Pictures/SmartPoi/WirelessSmartPoi/"; // path for desktop
//String fullPath = "/storage/sdcard0/Pictures/SmartPoi/36px/"; // path for android
    int whichPic = 0;
/////////////////////////////////////////////////////////////////////////////////////////////////////////

    //other experimental://///////////////////////////////////////////////////////////////////////////////////////////////
    int   r, g, b;
    boolean black = true;
    PGraphics pg;
    PGraphics pg2;
    PGraphics pgx;

    int numPixels = 36;

    PImage img1;
    PImage sunflower;
    PImage nnn;
    PImage rainbow;

    Button buttonSwitchScreen;
    Button buttonMouseOption;
    Button buttonSlowDown;
    Button buttonSpeedUp;
    Button buttonGoToScreen1;
    Button buttonGoToScreen2;
    Button buttonGoToScreen3;
    Button buttonGoToScreen4;
    Button buttonGoToScreen5;
    Button buttonPlay;
    Button buttonPause;



    //sound test:///////////////////////////////////////////////////////////////////////////////////////////////////////////
    Button buttonProgressFrame;
    Button buttonProgressData;
//end sound test////////////////////////////////////////////////////////////////////////////////////////////////////////

    //timeline:////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ArrayList<TimeLineImage> timeLineImagesArrayList;
    TimeLineEvent event1;
    ArrayList<TimeLineEvent> timeLineEventsArrayList;
    PrintWriter timeLineEventsSave1;
    BufferedReader timeLineEvents1Reader;
    String line;
    String line2;
    boolean ready = false;
    int checkLineCounter = 0;
//end timeline stuff

    ArrayList<Button> buttonsArrayList;
    ArrayList<Button> sequence1ButtonsArrayList;

    ArrayList<Button> goToScreenButtonsArrayList;
    int numScreens = 4;
    int currentScreen = 0;
    boolean showAllPicsInFolder = false;

    int timer = 0;
    int sequenceShowTime = 1000;

    ArrayList<Button> patternButtonsArrayList;
    int numPatterns = 6;
    int currentPattern = 4;
    String messageText;


    boolean testing = false;

    boolean showOnce = true;
    boolean screen5MainPicOn = true;
    int newWhichPicNum;

    //brightness idea:
    float BRT = 1.0f;
    boolean showSlider = false;

    ControlP5 cp5;
    Slider brightnessSlider;

    //holdImage:
    PGraphics pgHold;
    PImage holdImage;

    PImage play;
    PImage open;
    PImage changePX;

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
/*
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
                ipc = sharedpreferences.getString(ipa3Saved, "1");
                println("ipc is: " + ipc);
            } else {
                ipc = "1";
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

 */
////////////////////////////////////End Saved Wifi://////////////////////////////////////////

//        orientation(LANDSCAPE); //conflicting code causes force close

        //select files (zip file location)
//        files = new SelectLibrary(this);
        //  files.selectInput("Select a .zip timeline:", "fileSelected");

        //////////////////////////////UDP Code://////////////////////
        udp = new UDP( this ); // create a new datagram connection - don't need to specify port?! seems to negate already in use bug? //udp = new UDP( this, UDPport );
        //  udp.log( true ); // <\u2013 printout the connection activity
        udp.listen( true ); // and wait for incoming message
        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////

        //brightness slider:
        cp5 = new ControlP5(this);

        try {
            player = new  MediaPlayer();
            player.setDataSource(PATH_TO_FILE);
            player.prepare();
        }
        catch(IOException e) {
            println("file did not load");
        }

        timeLineImagesArrayList = new ArrayList<TimeLineImage>(); //ArrayList for timeline1 images
        timeLineEventsArrayList = new ArrayList<TimeLineEvent>(); //ArrayList for timeline


        timeLineEvents1Reader = createReader(timeLine1Path + "timeline.txt");

        //////////////////////////////////////////////////////////////


        buttonsArrayList = new ArrayList<Button>();
        sequence1ButtonsArrayList = new ArrayList<Button>();
        goToScreenButtonsArrayList = new ArrayList<Button>();
        patternButtonsArrayList = new ArrayList<Button>();
        //frameRate(1);
        createCheckDirectory(collection1Path);
        createCheckDirectory(sequence1Path);
        createCheckDirectory(music1Path);
        createCheckDirectory(timeLine1Path);

        // All Buttons Below:
        //----------------------------------------------------------------------------------------------------------------//
        sunflower = loadImage("sunflower.jpg");
        nnn = loadImage("nnn.png");
        rainbow = loadImage("rainbow.jpg"); //replace all buttons with nice graphics


        buttonSwitchScreen = new Button (  width/6*5, height/12*11,
                width/6, height/12,
                true, color (255, 0, 0), //red
                true, color (255),
                "SWITCH\nSCREEN",
                "",
                1,
                rainbow);
        //
        buttonMouseOption = new Button (  width/6*4, height/6*5,
                width/6, height/6,
                true, color (0, 255, 0), //green
                true, color (255),
                "SEND ALL",
                "",
                2,
                sunflower);
        //
        buttonSlowDown = new Button (  width/6*4, height/6*4,
                width/6, height/6,
                true, color (0, 100, 100),
                true, color (255),
                "<<\nSLOWER",
                "",
                3,
                sunflower);
        //
        buttonSpeedUp = new Button (  width/6*5, height/6*4,
                width/6, height/6,
                true, color (100, 0, 100),
                true, color (255),
                ">>\nFASTER",
                "",
                4,
                sunflower);

        //Screen4 buttons:
        buttonPlay = new Button (  0, height/10+10,
                width/4, height/10,
                //width/6, height/4,
                true, color (100, 0, 0), //red
                true, color (255),
                ">",
                "Play",
                6,
                sunflower);
        //
        buttonPause = new Button (  width/2, height/10+10,
                width/4, height/10,
                //width/6, height/4,
                true, color (0, 0, 100), //red
                true, color (255),
                "||",
                "Pause",
                6,
                sunflower);
        //
        //////////////////////////////test progress frame:////////////////////////
        buttonProgressFrame = new Button ( 0, 0,
                width, height/10,
                true, color (0),
                true, color (255),
                "",
                "Click to set play position",
                1,
                sunflower);
        //
        buttonProgressData = new Button ( 0, 0,
                0, height/10-4,
                true, color (222, 1, 1),
                false, color (255),
                "",
                "",
                -1,
                sunflower);
        //

        //End buttons
        //_________________________________________________________________________________________________________________//




        //maelstromInit();


        pg = createGraphics(numPixels, numPixels);

        //textPGraphics setup://////////////////////////
        pg2 = createGraphics(72, 36);


        brightnessSlider = cp5.addSlider("BRT")
                .setPosition(0, height/6*2)
                .setSize(width/3, height/6*3)
                .setRange(0, 1)
                .setValue(1.0f)
                .setColorForeground(color(150, 100, 100))
                .setColorBackground(color(50, 0, 0))
                .setColorActive(color(100, 50, 50))
                .setVisible(false)
        ;
        //load default zip:
        //try{
        //    unzip3("/sdcard/Pictures/SmartPoi/WirelessSmartPoi/TestZip/Test.zip", timeLine1Path);
        ////    Unzip x = new Unzip();
        ////    x.start();
        //  } catch (Exception e){
        //    println("Exception caught in Setup");
        //  }

        //file open code:///////////////////////////////////////
        //frameRate(30);
        //folders for clicking on: - need array for these too
  /*
  foldernames = loadFoldernames(fullPath);
   println("folders:");
   println(foldernames);
   */
  /*
  filenames = loadFilenames(collection1Path);
   //println("files:");
   //println(filenames);
   //println(filenames[0]);
   //exit();
   images = new PImage[filenames.length];

   //below to load all images into array - too big images = out of memory error!
   for (int i=0; i < filenames.length; i++) {
   images[i] = loadImage(collection1Path+filenames[i]);
   if(images[i].height == 36 || images[i].width == 36){
   //don't resize therefore avoiding the bug???
   }
   else{
   images[i].resize(36, 0); //there is a bug with resize: https://github.com/processing/processing-android-archive/issues/22
   //try using png images instead...
   //right now just avoid using any image with size of ( width == 36 || height == 36 ) as it won't show up for some reason...???
   //remember to use -adaptive-resize
   }
   }
   //end file open code//////////////////////////////////////
   //load image buttons to array:///////////////////////////////////////////////////
   //int imgsNum = 0;
   int buttRowNum = 0;
   int buttColNum = 0;
   // fit buttons on screen algorhythm here:
   int a = ceil(sqrt(filenames.length)); //ceil() casts float to int and rounds up

   for (int i=0; i < filenames.length; i++) {

   //still not showing on first row, last row cut off now...
   int cutH = height - height/5;
   buttonsArrayList.add(new Button(
   buttRowNum*(width/a), buttColNum*(cutH/a),
   width/a, cutH/a,
   true, color (255, 0, 0), //red
   true, color (255),
   "",
   "  new button here",
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
   */
        //end load images to array/////////////////////////////////////////////////////
        //need folder display code as well

        //Screen Buttons:///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        int buttRowNum = 0;
        int buttColNum = 0;
        play = loadImage("play.jpg");
        open = loadImage("open.png");
        changePX = loadImage("72px.png");

        //button images not displaying?? why?
        for (int i=0; i < numScreens; i++) {
            if (i<=1) { //OPEN FILE BUTTON
                goToScreenButtonsArrayList.add(new Button(
                        buttRowNum*(width/8), buttColNum*(height/6),
                        width/8, height/6,
                        true, color (random(255), random(255), random(255)), //random
                        true, color (255),
                        str(i),
                        str(i),
                        i,
                        open));
            } else if (i==2) { //PLAY BUTTON
                goToScreenButtonsArrayList.add(new Button(
                        buttRowNum*(width/8), buttColNum*(height/6),
                        width/3, height/3,
                        true, color (random(255), random(255), random(255)), //random
                        true, color (255),
                        str(i),
                        str(i),
                        i,
                        play));
            }else if(i==3){ //72PX BUTTON
                goToScreenButtonsArrayList.add(new Button(
                        5*(width/8), buttColNum*(height/6),
                        width/6, height/6,
                        true, color (random(255), random(255), random(255)), //random
                        true, color (255),
                        str(i),
                        str(i),
                        i,
                        changePX));
            }
            if (i==0) {
                buttRowNum++;
            } else {
                if (buttRowNum >= 7) { //if there are 8 pics in a row
                    buttColNum++;
                    buttRowNum = 0;
                } else {
                    buttRowNum++;
                }
            }
        }
        //end screen buttons/////////////////////////////////////////////////////////////////////////////////////


  /*
  //load timeline images:
   timeLineFilenames = loadFilenames(timeLine1Path);
   timeLineImages = new PImage[timeLineFilenames.length];
   //below to load all images into array - too big images = out of memory error!
   for (int i=0; i < timeLineFilenames.length; i++) {
   timeLineImages[i] = loadImage(timeLine1Path+timeLineFilenames[i]);
   if(timeLineImages[i].height == 36 || timeLineImages[i].width == 36){
   //don't resize therefore avoiding the bug???
   }
   else{
   timeLineImages[i].resize(36, 0); //there is a bug with resize: https://github.com/processing/processing-android-archive/issues/22
   //try using png images instead...
   //right now just avoid using any image with size of ( width == 36 || height == 36 ) as it won't show up for some reason...???
   //remember to use -adaptive-resize
   }
   }

   for(int i=0; i<timeLineFilenames.length; i++){
   //get pic number:
   String[] list = split(timeLineFilenames[i], "pic");
   int len = list[1].length();
   String ss1 = list[1].substring(0, len-4);
   int ss2 = int(ss1); //convert to int for constructor
   //
   timeLineImagesArrayList.add(new TimeLineImage(
   timeLineImages[i],
   ss2 //need to use filenames here somehow to get pic number....?????
   ));
   }
   */
        //end file open code//////////////////////////////////////

        //holding pattern load(blank image send while waiting):///////////////////////////////////////////////
        holdImage = loadImage("black.jpg");
        pgHold = createGraphics(pg.width, pg.height);
        pgHold.beginDraw();
        pgHold.image(holdImage, 0, 0, pg.width, pg.height);
        //pg.tint(BRT); //tint not working on pg
        pgHold.endDraw();
        //image(pgHold, width/3, height/3, height/2, height/2);
        /////////////////////////////////////////////////////////////////////end holding pattern load
    }//end setup()

    @Override
    public void draw() {

        //background(0);
        switch(currentScreen) {
            case 0:
                drawScreen0();
                break;
            case 4:
                drawScreen4();
                break;
            case 5:
                drawScreen5();
                break;
            default:
                drawScreen0();
                break;
        }
    }

    //*******************************************************************BRIGHTNESS******************************************************//
    public void BRT(float theBRT) { //called on slider change?
        showOnce = true;
        BRT = theBRT;
    }

    public void keyPressed() {
        if (key == CODED) {
            if (keyCode == MENU) {
                showSlider = !showSlider;
                /////////////////////////////////////brightness slider/////////////////////////////////////////////////////
                if (showSlider) {
                    //text(BRT, 100, 100);
                    brightnessSlider.setVisible(true);
                    screen5MainPicOn = true; //redraw screen
                } else {
                    brightnessSlider.setVisible(false);
                    screen5MainPicOn = true; //redraw screen
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }
        }
    }
//************************************************************************************************************************************//


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
                return name.toLowerCase().endsWith(".jpg"); // change this to any extension you want
            }
        };
        return folder.list(filenameFilter);
    }
//for folder open:///////////////////////////////////////


    //receive UDP://////////////////////////////////////////////
    public void receive( byte[] data ) { // <\u2013 default handler
        //void receive( byte[] data, String ip, int port ) { // <\u2013 extended handler

        //for (int i=0; i < data.length; i++)
        //print(char(data[i]));
        //println();
    }


    //startup Android:////////////////////////////////////////////////////////////////////////////
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);

        // fix so screen doesn't go to sleep when app is active
//  getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //processing3
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //processing2

        //http://stackoverflow.com/questions/2887368/can-you-please-explain-oncreate-and-bundles
        //try to make some more stuff persistent?? not sure if it's necessary.


        //*****************************************************
        //wake lock:
        //from http://stackoverflow.com/questions/17234184/partial-wake-lock-not-working
        //should probably remove the wakelock code as it doesn't seem to work - need to test it though, I think any PImage code isn't
        //going to work as it needs the screen on anyway... hmmm service anyone?
        //*****************************************************
  /*
  final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
  wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");
  wl.acquire();
  //*********************************************************
  // connect wifi:
  //from: http://stackoverflow.com/questions/6141185/android-connect-to-wifi-without-human-interaction
  //and from: http://stackoverflow.com/questions/12789788/connect-to-open-wifi

  WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
  // setup a wifi configuration
  WifiConfiguration wc = new WifiConfiguration();
  wc.SSID = "\"Smart_Poi_1\"";

  wc.status = WifiConfiguration.Status.ENABLED;

  wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

  // connect to and enable the connection
  int netId = wifiManager.addNetwork(wc);
  wifiManager.enableNetwork(netId, true);
  wifiManager.setWifiEnabled(true);
  */
//    }
//end startup Android/////////////////////////////////////////////////////////////////////////

    //The MediaPlayer must be released when the app closes
    public void onDestroy() {

        super.onDestroy(); //call onDestroy on super class
        if (player!=null) { //must be checked because or else crash when return from landscape mode
            player.release(); //release the player
        }
  /*
  if (wl.isHeld()) {
    wl.release();
  }
  */
    }

    public void pause() {
        super.pause();
        if (player !=null) {
            player.release();
            player = null;
        }
    };

    public void stop() {
        super.stop();
        if (player !=null) {
            player.release();
            player = null;
        }
    };
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
//    textAlign(LEFT);
//    textSize(18);
                image(img, x, y, w, h);
//      text(text, x, y+h+20);
            }
        } // method
        //
        //
        public void displayImage () {
//    if (hasColorFill)
//      fill(colorFill);
//    else
//      noFill();
//    if (hasColorStroke)
//      stroke(colorStroke);
//    else
//      noStroke();
//    rect(x, y, w, h);
//    fill(255);
//    textAlign(CENTER);
//    textSize(18);
//    text(text, x+w/2, y+h/2);
//    if (img !=null && img !=sunflower) { //sunflower img for colour only buttons... hack!
            textAlign(LEFT);
            textSize(18);
            image(img, x, y, w, h);
            text(text, x, y+h+20);
//    }
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
//void controlEvent(ControlEvent theEvent) {
//  // DropdownList is of type ControlGroup.
//  // A controlEvent will be triggered from inside the ControlGroup class.
//  // therefore you need to check the originator of the Event with
//  // if (theEvent.isGroup())
//  // to avoid an error message thrown by controlP5.
//
//  if (theEvent.isGroup()) {
//    // check if the Event was triggered from a ControlGroup
//    //println("event from group : "+theEvent.getGroup().getValue()+" from "+theEvent.getGroup());
//    msg3Data = l.item(int(theEvent.getGroup().getValue())).getName();
//
//
//    println("displaying listbox item contents: " + msg3Data);
//    l.setVisible(false); //temporary hack to see if it works, actually still have the menu button so we are good!
//    //background(0);
//    //textPGraphics(msg3Data);
//  } else if (theEvent.isController()) {
//    //println("event from controller : "+theEvent.getController().getValue()+" from "+theEvent.getController());
//  }
//}
//
//
//
//public void dontSend() {
//  //do nothing just go back to Screen1:
//  showSMS = true;
//}
//
//void knob(int theValue) {
//  myColorBackground = color(theValue);
//  println("a knob event. setting variable to "+theValue);
//}
//
//void more_Pixels(boolean theFlag) {
//  if (theFlag==true) {
//    numPixels = 36;
//
//    myToggle.setCaptionLabel("36px");
//    println(numPixels + " px selected");
//  } else {
//    numPixels = 72;
//    myToggle.setCaptionLabel("            72px");
//    println(numPixels + " px selected");
//  }
//
//}
///*
//
// public void showAllSMS(){
//
// screen2Message = "test message again"; //just for testing...
// showSMS = !showSMS; //only for testing otherwise the only reason to go there is if message received.
// background(0);
// //println("showAllSMS button pressed, screen is: " + screen);
//
//
// }



    public void drawScreen0() {
        textAlign(LEFT);
        textSize(30);
        text("1. Choose Timeline Zip File\n2. Play Timeline", 200, 350);
        for (int i = 1; i < goToScreenButtonsArrayList.size (); i++) {
            goToScreenButtonsArrayList.get(i).displayImage();
        }
        //hold Image:
        if (!testing) {
            sendPGraphicsToPoi(pgHold, 0); // send to POI -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> ->
        }
        image(holdImage, 150, 200, 100, 100); //testing only
        textAlign(LEFT);
        textSize(25);
        text("poi should be blank while this screen is on\nmake a selection", 270, 250);

        if (mousePressed) {
            for (int i = 0; i<goToScreenButtonsArrayList.size (); i++ ) {
                if (goToScreenButtonsArrayList.get(i).over()) {
                    //         myToggle.setVisible(false);
                    //         brightnessSlider.setVisible(false);
                    switch(i) {
                        case 0:
                            initScreen0();
                            break;
                        case 1:
                            initScreen4();
                            break;
                        case 2:
                            initScreen5();
                            break;
                        case 3:
                            numPixels = 72; //change numPixels, will this work here? probably not...
                            pg = createGraphics(numPixels, numPixels); //resize pg
                            holdImage = loadImage("black.jpg"); //reload holdImage and resize below:
                            pgHold = createGraphics(pg.width, pg.height);
                            pgHold.beginDraw();
                            pgHold.image(holdImage, 0, 0, pg.width, pg.height);
                            //pg.tint(BRT); //tint not working on pg
                            pgHold.endDraw();
                            background(60);
                            break;
                        default:
                            initScreen0();
                            break;
                    }
                }
                //      else{//pressed elsewhere: (need a button for this)...
                //    numPixels = 72; //change numPixels, will this work here? probably not...
                //    pg = createGraphics(numPixels, numPixels); //resize pg
                //    holdImage = loadImage("black.jpg"); //reload holdImage and resize below:
                //    pgHold = createGraphics(pg.width, pg.height);
                //    pgHold.beginDraw();
                //    pgHold.image(holdImage, 0, 0, pg.width, pg.height);
                //    //pg.tint(BRT); //tint not working on pg
                //    pgHold.endDraw();
                //      }
            }
        }
    }

    public void initScreen0() {
        //   myToggle.setVisible(true);
        //   brightnessSlider.setVisible(true);
        background(0);
        currentScreen = 0;
    }

    public void initScreen4() {
        //first delete all files://////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        filenames = loadFilenames(timeLine1Path);
        //println("files:");
        //println(filenames);
        //println(filenames[0]);
        //exit();
        //images = new PImage[filenames.length];

        //below to load all images into array - too big images = out of memory error!
        for (int i=0; i < filenames.length; i++) {
            try {
                File newFile = new File(timeLine1Path + filenames[i]);

                if (newFile.exists()) {
                    println("Exists: " + filenames[i]);
                    if (newFile.isDirectory()) {
                        println("isDirectory = true...");
                    } else {
                        newFile.delete();
                        println("isDirectory = false...");
                    }
                } else {
                    println("Doesn't Exist: " + filenames[i]);
                }
            }
            catch(Exception e) {
                //println("Exception creating folder... ");
                //e.printStackTrace();
            }
        }

        //here we go replace files.selectInput with another input method:
        performFileSearch();
//        files.selectInput("Select a .zip timeline:", "fileSelected");
        //  timeLineEventsSave1 = createWriter(timeLine1Path + "timeline.txt");
        //   try {
        //    player = new  MediaPlayer();
        //    player.setDataSource(PATH_TO_FILE);
        //    player.prepare();
        //  }
        //  catch(IOException e) {
        //    println("file did not load");
        //  }
        //  player.setLooping(true); //restart playback end reached - not working
        //  background(0);
        //  currentScreen = 4;
        //delay(100); //stop it from repeating if mouse held down too long...
    }
    public void initScreen5() {
        //load timeline images:
        timeLineFilenames = loadFilenames(timeLine1Path);
        timeLineImages = new PImage[timeLineFilenames.length];
        //below to load all images into array - too big images = out of memory error!
        for (int i=0; i < timeLineFilenames.length; i++) {
            timeLineImages[i] = loadImage(timeLine1Path+timeLineFilenames[i]);
            if (timeLineImages[i].height == numPixels || timeLineImages[i].width == numPixels) {
                //don't resize therefore avoiding the bug???
            } else {
                timeLineImages[i].resize(numPixels, 0); //there is a bug with resize: https://github.com/processing/processing-android-archive/issues/22
                //try using png images instead...
                //right now just avoid using any image with size of ( width == 36 || height == 36 ) as it won't show up for some reason...???
                //remember to use -adaptive-resize
            }
        }

        for (int i=0; i<timeLineFilenames.length; i++) {
            //get pic number:
            String[] list = split(timeLineFilenames[i], "pic");
            int len = list[1].length();
            String ss1 = list[1].substring(0, len-4);
            int ss2 = PApplet.parseInt(ss1); //convert to int for constructor
            //
            timeLineImagesArrayList.add(new TimeLineImage(
                    timeLineImages[i],
                    ss2 //need to use filenames here somehow to get pic number....?????
            ));
        }
        //end file open code//////////////////////////////////////
        showOnce=true;
        screen5MainPicOn = true;
        try {
            player = new  MediaPlayer();
            player.setDataSource(PATH_TO_FILE);
            player.prepare();
        }
        catch(IOException e) {
            println("file did not load");
        }
        player.setLooping(true); //restart playback end reached - not working
        background(0);
        whichPic = 0;
        timeLineEventsArrayList.clear(); //this line is necessary but is causing a crash somehow...
        ready = false;
        checkLineCounter = 0;
        background(0);
        currentScreen = 5;
        delay(100); //stop it from repeating if mouse held down too long...
    }

    ////////////////////////////////////////////////////Pick timeline pics from folder//////////////////////////////////////////////////
    boolean buttonStopScroll = false;

    public void drawScreen4() {
        //initialize player first? why does this not work?
        if (showOnce) {
            background(0);
            showOnce=false;
        }

        //make space for player and transport buttons, then display
        if (!buttonStopScroll) {
            for (int i = 0; i < buttonsArrayList.size (); i++) {
                //println("y is: " + buttonsArrayList.get(i).y); //moving all buttons down - this will apply to screen2 as well I am afraid...
                buttonsArrayList.get(i).y += height/5+10;
                //println("incremented y is: " + buttonsArrayList.get(i).y);
            }
            buttonStopScroll = !buttonStopScroll;
        }

        for (int i = 0; i < buttonsArrayList.size (); i++) {
            buttonsArrayList.get(i).display();
        }

  /*
   if (!(event1==null)) {
   event1.displayOnTimeline();
   println("displaying " + event1.pic());
   }
   */
        buttonPlay.display();
        buttonPause.display();
        buttonProgressFrame.display();
        //if (!(meta==null))
        buttonProgressData.w = map(player.getCurrentPosition(), 0, player.getDuration(), 0, width );
        buttonProgressData.display();
        //load timeLineEventsArrayList from pics in timeLine1Path pointed to by timeLineEventsSave1
        //loading should really be done once in initScreen4()
        //display all timeLineEvents in timeLineEventsArrayList
        if (timeLineEventsArrayList.size()>0) {
            for (int i = 0; i < timeLineEventsArrayList.size (); i++) {
                timeLineEventsArrayList.get(i).displayOnTimeline();
            }
        }
        buttonSwitchScreen.display();
        if (mousePressed) {
            if (buttonSwitchScreen.over()) {
                //all Switch screen now go to Screen0
                background(0);
                currentScreen = 0;
                delay(100); //stop it from repeating if mouse held down too long...
      /*
        background(0);
       player.setMediaFile(music1Path + "test.mp3"); //initial file for timeline
       whichPic = 0;
       timeLineEventsArrayList.clear();
       //ready = false;
       currentScreen = 5;

       delay(100); //stop it from repeating if mouse held down too long...
       */
            } else {
                for (int i = 0; i<buttonsArrayList.size (); i++ ) {
                    if (buttonsArrayList.get(i).over()) {
                        //append to timeLineEventsArrayList and save to timeLine1Path and pointer to timeLineEventsSave1
                        //println("over button " + buttonsArrayList.get(i));
                        //println(i);
                        whichPic = i;
                        //save in timeLine1 folder:
                        pg = createGraphics(buttonsArrayList.get(whichPic).pic().width, buttonsArrayList.get(whichPic).pic().height);
                        pg.beginDraw();
                        pg.image(buttonsArrayList.get(whichPic).pic(), 0, 0);
                        pg.endDraw();
                        pg.save(timeLine1Path + "pic" + i + ".jpg");
                        //test code: ///////////////////////////////////////////////////////////////////////////////////////////////////////
                        //create new TimeLineEvent and add it to timeLineEventArrayList
                        event1 = new TimeLineEvent (map(player.getCurrentPosition(), 0, player.getDuration(), 0, width ), 0,
                                height/20, height/10,
                                buttonsArrayList.get(whichPic).pic(),
                                player.getCurrentPosition());
                        timeLineEventsArrayList.add(event1);
                        //println("position is: " + player.getCurrentPosition());
                        //save all timeLineEventsArrayList to timeLineEventsSave1 file for later retrieval:
          /*
       //the below was adding exta lines to timeLineEventsSave1 every time because loop!
           for(int b = 0; b < timeLineEventsArrayList.size(); b++) { //should b be 0 or 1 to start?
           int t = timeLineEventsArrayList.get(b).atTime();
           int p = i; //need to get the proper path!
           //int p =
           //timeLineEventsSave1.println(t);
           timeLineEventsSave1.println(t);
           timeLineEventsSave1.println(p);
           }//end for(int b)
           */
                        int t = timeLineEventsArrayList.get(timeLineEventsArrayList.size()-1).atTime();
                        //int p = i;
                        int p = whichPic; //need to get the proper path?
                        //timeLineEventsSave1.println(t);
                        timeLineEventsSave1.println(t);
                        timeLineEventsSave1.println(p);
                        timeLineEventsSave1.flush();
                        //well this does do something, needs debugging making too many entries
                        //end test/////////////////////////////////////////////////////////////////////////////////////////////////////
                        delay(100); //stop it from repeating if mouse held down too long...
                    }
                }
                if (buttonPlay.over()) {
                    //test media player:
                    //player = new APMediaPlayer(this); //create new APMediaPlayer do we have to do this every time?
                    try {
                        player = new  MediaPlayer();
                        player.setDataSource(PATH_TO_FILE);
                        player.prepare();
                    }
                    catch(IOException e) {
                        println("file did not load");
                    }
                    player.setLooping(true); //restart playbwoack end reached - not working
                    player.setVolume(0.2f, 0.2f); //Set left and right volumes. Range is from 0.0 to 1.0
                    player.start(); //start play back

        /* if already playing seekTo(0);
         seekTo
         public void seekTo(int msec)
         move to a point in the file, counted from the beginning in millisecond.
         Parameters:
         msec -
         */
                }
                if (buttonPause.over()) {
                    player.pause(); //pause player
                }
                if (buttonProgressFrame.over()) {
                    int newSongPosition = PApplet.parseInt ( map(mouseX,
                            buttonProgressFrame.x, buttonProgressFrame.x+buttonProgressFrame.w,
                            0, player.getDuration() ) ) ;
                    player.seekTo(newSongPosition);
                }
            }//end else buttonSwitchScreen over
        }
    }
    /////////////////////////////////////////////////////////Run timeline////////////////////////////////////////////////////////////////
    PGraphics pg3;

    int a = 0; //for animation
    boolean screenBlank = false;

    public void drawScreen5() {
        if(!screenBlank){
            buttonProgressFrame.display();
            //  //if (!(meta==null))
            buttonProgressData.w = map(player.getCurrentPosition(), 0, player.getDuration(), 0, width );
            buttonProgressData.display();
        }
        else{ //indicator for screen off:
            background(0);
            stroke(100);
            fill(0);
            ellipse(random(width), random(height), 20, 20);
        }
//  if (showOnce) {
        //    buttonPlay.display();
        //    buttonPause.display();
//    buttonSwitchScreen.display();
        //    showOnce = false;
//  }


        //TimeLineEvent test:
        if (!ready) { //should do this in init? some bug is causing a crash
            try {
                line = timeLineEvents1Reader.readLine();
                line2 = timeLineEvents1Reader.readLine();
                checkLineCounter++;
            }
            catch (IOException e) {
                e.printStackTrace();
                line = null;
            }
            if (line == null) {
                // Stop reading because of an error or file is empty
                //noLoop();
                if (checkLineCounter > 1) {
                    ready = true;
                    screen5MainPicOn = true;
                } else {
                    //println("timeLineEvents1Reader came up empty");
                }
                //whichPic = 0;
            } else {
                int correctIndex = 0;
                int test = PApplet.parseInt(line2);
                for (int i = 0; i<timeLineImagesArrayList.size (); i++) {
                    if (timeLineImagesArrayList.get(i).getnum() == test) {
                        correctIndex =  timeLineImagesArrayList.indexOf(timeLineImagesArrayList.get(i));
                        println("correctIndex is: " + correctIndex);
                    }
                    else{
                        println("try again");
                    }
                }
                //ok ja but how to error correct above???

                event1 = new TimeLineEvent (map(PApplet.parseFloat(line), 0.0f, player.getDuration(), 0, width), height/10+5,
                        height/20, height/10,
//      buttonsArrayList.get(int(line2)).pic(), //buttonsArrayList is loaded from collections1 folder!!!! this is a grave error, need new arraylist from timeline1 folder now!
                        //timeLineImagesArrayList.get(timeLineImagesArrayList.get(int(line2)).getnum()).pic(), //if this works... eat == hat
//      timeLineImagesArrayList.get(checkLineCounter).pic(), //this is a hack, but does it work??? no no no it's totally wrong fix this urgent!!!!
                        timeLineImagesArrayList.get(correctIndex).pic(),

                        //still working on above, incorrect statement
                        PApplet.parseInt(line));
                timeLineEventsArrayList.add(event1);
            }
        } else if (ready) {
            if (showOnce) {
                background(0);
                try {
                    player = new  MediaPlayer();
                    player.setDataSource(PATH_TO_FILE);
                    player.prepare();
                }
                catch(IOException e) {
                    println("file did not load");
                }
                player.setLooping(true); //restart playback end reached
                player.setVolume(0.2f, 0.2f); //Set left and right volumes. Range is from 0.0 to 1.0
                player.start(); //start play back

                if (timeLineEventsArrayList.size()>0) {
                    for (int i = 0; i < timeLineEventsArrayList.size (); i++) {
                        if(!screenBlank){
                            timeLineEventsArrayList.get(i).displayBelowTimeline();
                        }
                    }
                }
                buttonSwitchScreen.display(); //trying this here... if it works need to remove from above position
                showOnce=false;
            }
            timeLineSendUDP();
        }





        for (int t = 0; t < timeLineEventsArrayList.size (); t++) {
            if (t==timeLineEventsArrayList.size()-1) {
                if (player.getCurrentPosition()>timeLineEventsArrayList.get(t).atTime()) {
                    //println("at pic number " + t + " this is the end");
                    whichPic = t;
                    //only draw pic once:
                    if (newWhichPicNum != whichPic) {
                        screen5MainPicOn = true;
                        newWhichPicNum = whichPic;
                        //println("changed pic");
                    }
                }
            } else {
                if (timeLineEventsArrayList.get(t).atTime()<player.getCurrentPosition() && player.getCurrentPosition()<timeLineEventsArrayList.get(t+1).atTime()) {
                    //println("at pic number " + t);
                    whichPic = t;
                    //only draw pic once:
                    if (newWhichPicNum != whichPic) {
                        screen5MainPicOn = true;
                        newWhichPicNum = whichPic;
                        //println("changed pic");
                    }
                }
            }
        }




        if (mousePressed) {

            if (buttonSwitchScreen.over()) {
                //all Switch screen now go to Screen0
                background(0);
                screenBlank = true; //screenBlank = !screenBlank;
                //currentScreen = 0;
                delay(100); //stop it from repeating if mouse held down too long...

                //        ready = !ready; //back to 0
                //       background(0);
                //       currentScreen = 0;
                //       delay(100); //stop it from repeating if mouse held down too long...
            }
    /*
    if (buttonPlay.over()) {
     //test media player:
     //player = new APMediaPlayer(this); //create new APMediaPlayer do we have to do this every time?
     player.setMediaFile(music1Path + "test.mp3"); //set the file (files are in data folder)
     player.setLooping(true); //restart playback end reached
     player.setVolume(0.2, 0.2); //Set left and right volumes. Range is from 0.0 to 1.0
     player.start(); //start play back

     // if already playing seekTo(0);
     //       seekTo
     //       public void seekTo(int msec)
     //       move to a point in the file, counted from the beginning in millisecond.
     //       Parameters:
     //       msec -
     //
     }
     if (buttonPause.over()) {
     //test code:

     //          event1 = new TimeLineEvent (map(player.getCurrentPosition(), 0, player.getDuration(), 0, width ), 30,
     //       20, 20,
     //       nnn,
     //       player.getCurrentPosition());

     println("position is: " + player.getCurrentPosition());
     ////////////////////////////////////////////////////////////////////////////////

     player.pause(); //pause player
     }
     */
        }
    }

///////////////////////////////////

    public void timeLineSendUDP() { //need error checking in case nothing in folder...

        //int pixelCounter = 0;

        //timeLineEventsArrayList.get(whichPic).pic().resize(36, 0); //moved resize here because of android resize bug... fixed now?
        timeLineEventsArrayList.get(whichPic).pic().loadPixels();
        pg = createGraphics(timeLineEventsArrayList.get(whichPic).pic().width, timeLineEventsArrayList.get(whichPic).pic().height);
        pg.beginDraw();
        pg.image(timeLineEventsArrayList.get(whichPic).pic(), 0, 0);
        //pg.tint(BRT); //tint not working on pg
        pg.endDraw();
        //image(timeLineEventsArrayList.get(whichPic).pic(), 3*(width/8), 5*(height/6), width/8, height/6); //why null pointer exception it works in setup()!
//  image(timeLineEventsArrayList.get(whichPic).pic(), width/3, height/3, width/2, width/2);

        //animations too slow on android:
//  if(timeLineEventsArrayList.get(whichPic).pic().height > 100){
//    image( timeLineEventsArrayList.get(whichPic).pic().get(0,a,width/3,1), width/3, height/3);
//    if(a>timeLineEventsArrayList.get(whichPic).pic().height){
//     a=0;
//    }
//    a++;
//  }
//  else{
        //show pic only once per load:
        if(!screenBlank){
            if (screen5MainPicOn) {
                tint(BRT*255);
                image(timeLineEventsArrayList.get(whichPic).pic(), width/3, height/3, width/3, width/3);
                //    image(pg, width/3, height/3, height/2, height/2);
                screen5MainPicOn = false; //don't draw more than once
            }
        }
//  }

        //int   r, g, b;
        //add pg code here - pg begin, pg.display timeLineEventsArrayList.get(whichPic).pic() on pg and pg.end then
        if (!testing) {
            sendPGraphicsToPoi(pg, 0); // send to POI -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> ->
        }
    }
    public byte pixelConverter(int red, int green, int blue) {
        byte encodedRGB;
        //int   r, g, b;
        encodedRGB = PApplet.parseByte((red & 0xE0) | ((green & 0xE0)>>3) | (blue >> 6));
        return encodedRGB;
    }



    int sendOption = 0;


    public void sendPGraphicsToPoi(PGraphics pgSend, int sendOpt) {
        int pixelCounter = 0;
        byte[] message = new byte[numPixels];
        byte[] bigpx = new byte[72];
        for (int a = 0; a < pgSend.width*pgSend.height; a++) {

            if (pixelCounter == 0) {
                //port.write(byte(startByte));
            }
            //float dimmerR = red(pgSend.pixels[a])-(red(pgSend.pixels[a])*brightnessDown);
            float dimmerR = (pgSend.pixels[a] >> 16 & 0xFF)-((pgSend.pixels[a] >> 16 & 0xFF)*BRT);
            //float dimmerG = green(pgSend.pixels[a])-(green(pgSend.pixels[a])*brightnessDown);
            float dimmerG = (pgSend.pixels[a] >> 8 & 0xFF)-((pgSend.pixels[a] >> 8 & 0xFF)*BRT);
            //float dimmerB = blue(pgSend.pixels[a])-(blue(pgSend.pixels[a])*brightnessDown);
            float dimmerB = (pgSend.pixels[a] & 0xFF)-((pgSend.pixels[a] & 0xFF)*BRT);

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
            switch(sendOpt) {
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
            //bigpx[pixelCounter] = Y; //72px test code

            //need to pre-load pic into array or it slows down serial!
            //delay(1);
            pixelCounter++;

            if (pixelCounter == pgSend.width) {
                if(!testing){
                    udp.send(message, ip, UDPport); //disable for testing
                    udp.send(message, ip2, UDPport); //disable for testing
                    if(onRouter){
                        udp.send(message, ip4, UDPport);
                        udp.send(message, ip5, UDPport);
                        udp.send(message, ip6, UDPport);
                        udp.send(message, ip7, UDPport);
                    }
                }
                pixelCounter = 0;
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
    class TimeLineImage {
        PImage img;
        int num;
        //
        // constructor
        TimeLineImage (  PImage img_, int num_) {
            img=img_;
            num = num_;
        } // constructor
        //

        public PImage pic() {
            return (img);
        }

        public int getnum() {
            return (num);
        }
    } // class

//int findTimeLineImgNum(ArrayList<TimeLineImage> A1, int test) {
//  for (int i = 0; i<A1.size (); i++) {
//    if (A1.get(i).getnum() == test) {
//        correct A1.indexOf(i);
//      }
//      else{
//        return(0);
//      }
//  }
//}

    // =======================================================================
//urgent: add path of PImage to this class!
    class TimeLineEvent {
        float x;  // pos
        float y;
        float w=0;  // size
        float h=0;
        PImage img;
        int timeAt;
        ///so where does a new button info get saved? need to check persistence
        //
        // constructor
        TimeLineEvent (float x_, float y_,
                       float w_, float h_, PImage img_, int timeAt_) {
            x=x_;
            y=y_;
            w=w_;
            h=h_;
            img=img_;
            timeAt = timeAt_;
        }
        // constructor
        //
        public int atTime() {
            return (timeAt);
        }

        public PImage pic() {
            return (img);
        }
        //



        public void displayOnTimeline() {
            rect(x, y, w, h);
            if (img !=null && img !=sunflower) { //sunflower img for colour only buttons... hack!
                image(img, x, y, w, h);
            }
        } // method
        //

        public void displayBelowTimeline() {
            rect(x, y+30, w, h);
            if (img !=null && img !=sunflower) { //sunflower img for colour only buttons... hack!
                image(img, x, y+30, w, h);
            }
        } // method
        //
        public boolean over() {
            return (mouseX>x && mouseX<x+w&& mouseY>y&&mouseY<y+h);
        } // func
        //


        //
    } // class
    //
    public void tryPGraphics() {
        int pixelCounter = 0;
        pg.beginDraw();
        pg.background(0);
        pg.stroke(255);
        pg.line(20, 20, mouseX, mouseY);

        pg.endDraw();

        image(pg, 9, 30);
        byte[] message = new byte[numPixels];
        for (int a = 0; a < pg.width*pg.height; a++) {

            if (pixelCounter == 0) {
                //port.write(byte(startByte));
            }

            r = (int) red(pg.pixels[a]);
            g = (int) green(pg.pixels[a]);
            b = (int) blue(pg.pixels[a]);
            //port.write(pixelConverter(r, g , b)+127);
            ////////UDP Send://///////////////////////////////////
            byte Y = PApplet.parseByte(pixelConverter(r, g, b)+127);
            message[pixelCounter] = Y;
            //need to pre-load pic into array or it slows down serial!
            //delay(1);
            pixelCounter++;
            if (pixelCounter == pg.width) {
                //udp.send(message, ip , UDPport);
                pixelCounter = 0;
            }

            //delay(100);
        }
    }

    ////////////////////////////////delete this select library code///////////////////////
    /*
    SelectLibrary files;

    boolean zipReady = false;

    String selectedFile = "nothing yet";

    public void fileSelected(File selection) {
        if (selection == null) {
            println("Nothing was selected.");
        } else {
            selectedFile = selection.getAbsolutePath();
            println("User selected " + selection.getAbsolutePath());
            try{
                unzip3(selectedFile, timeLine1Path);
//    Unzip x = new Unzip();
//    x.start();
            } catch (Exception e){
                println("Exception caught in Setup");
            }
            zipReady = true;
        }
    }
    */
 ////////////////////////////////end select library code////////////////////////////////////////////////
/*
    public static void unzip3(String zipFile, String location) throws IOException {
        int size;
        int BUFFER_SIZE = 8192;
        byte[] buffer = new byte[BUFFER_SIZE];

        try {
            if ( !location.endsWith("/") ) {
                location += "/";
            }
            File f = new File(location);
            if(!f.isDirectory()) {
                f.mkdirs();
            }
            ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile), BUFFER_SIZE));
            try {
                ZipEntry ze = null;
                while ((ze = zin.getNextEntry()) != null) {
                    String path = location + ze.getName();
                    File unzipFile = new File(path);

                    if (ze.isDirectory()) {
                        if(!unzipFile.isDirectory()) {
                            unzipFile.mkdirs();
                        }
                    } else {
                        // check for and create parent directories if they don't exist
                        File parentDir = unzipFile.getParentFile();
                        if ( null != parentDir ) {
                            if ( !parentDir.isDirectory() ) {
                                parentDir.mkdirs();
                            }
                        }

                        // unzip the file
                        FileOutputStream out = new FileOutputStream(unzipFile, false);
                        BufferedOutputStream fout = new BufferedOutputStream(out, BUFFER_SIZE);
                        try {
                            while ( (size = zin.read(buffer, 0, BUFFER_SIZE)) != -1 ) {
                                fout.write(buffer, 0, size);
                            }

                            zin.closeEntry();
                        }
                        finally {
                            fout.flush();
                            fout.close();
                        }
                    }
                }
            }
            finally {
                zin.close();
            }
        }
        catch (Exception e) {
            println( "Unzip exception" + e);
        }
    }
*/

    public static void unzip3(InputStream zipFile, String location) throws IOException {
        int size;
        int BUFFER_SIZE = 8192;
        byte[] buffer = new byte[BUFFER_SIZE];

        try {
            if ( !location.endsWith("/") ) {
                location += "/";
            }
            File f = new File(location);
            if(!f.isDirectory()) {
                f.mkdirs();
            }
            ZipInputStream zin = new ZipInputStream(new BufferedInputStream(zipFile, BUFFER_SIZE));
            try {
                ZipEntry ze = null;
                while ((ze = zin.getNextEntry()) != null) {
                    String path = location + ze.getName();
                    File unzipFile = new File(path);

                    if (ze.isDirectory()) {
                        if(!unzipFile.isDirectory()) {
                            unzipFile.mkdirs();
                        }
                    } else {
                        // check for and create parent directories if they don't exist
                        File parentDir = unzipFile.getParentFile();
                        if ( null != parentDir ) {
                            if ( !parentDir.isDirectory() ) {
                                parentDir.mkdirs();
                            }
                        }

                        // unzip the file
                        FileOutputStream out = new FileOutputStream(unzipFile, false);
                        BufferedOutputStream fout = new BufferedOutputStream(out, BUFFER_SIZE);
                        try {
                            while ( (size = zin.read(buffer, 0, BUFFER_SIZE)) != -1 ) {
                                fout.write(buffer, 0, size);
                            }

                            zin.closeEntry();
                        }
                        finally {
                            fout.flush();
                            fout.close();
                        }
                    }
                }
            }
            finally {
                zin.close();
            }
        }
        catch (Exception e) {
            println( "Unzip exception" + e);
        }
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

    /////////////////here goes nothing, file select :////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static final int READ_REQUEST_CODE = 42;
//    ...
    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     */
    boolean zipReady = false;

    String selectedFile = "nothing yet";

    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("application/zip");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
//                uri = resultData.getAbsolutePath();
                uri = resultData.getData();
               selectedFile = uri.getLastPathSegment();
//                File zip = new File(resultData.getData().getPath());
                Log.i(TAG, "Uri: " + uri.toString());
//                showImage(uri);
                println("selected file" + selectedFile);
                InputStream inStream = null;
                try {
                    inStream = getActivity().getContentResolver().openInputStream(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try{
                    unzip3(inStream, timeLine1Path);
//                    unzip3(selectedFile, timeLine1Path);
                } catch (Exception e){
                    println("Exception caught in Setup");
                }
                zipReady = true;
            }
        }
    }


}
