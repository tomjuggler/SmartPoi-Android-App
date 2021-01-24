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
import android.view.WindowManager;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;



import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import controlP5.*;
import hypermedia.net.*;
import za.tomjuggler.processingdemo.R;

import android.view.WindowManager;
import android.view.View;
import android.os.Bundle;

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

public class Brightness extends PApplet {




    ControlP5 cp5;

    int myColorBackground = color(0, 0, 0);
    int colourGreen = color(0, 255, 0);
    int colourRed = color(255, 0, 0);
    int colourBlue = color(0, 0, 255);
    int redValue = 0;
    int greenValue = 0;
    int blueValue = 0;

    Knob myKnobBlue;
    Knob myKnobRed;
    Knob myKnobGreen;

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
//////////////////////////////////////////////////////////////

    PGraphics pg;
    PGraphics pgMask1;

    int xInt = 0;
    boolean goingUp = true;
    int r, g, b;
    boolean testing = false;
    float speed;

    float y = 3;

    int variation = 0;
    int variationMax = 5;

    int t = 0;
    int f = 100;
    float scale=0.002f;
    int[] plasma;
    int palette[];

    boolean showOnce = true;

    Button buttonSwitchScreen;
    PImage sunflower;
    boolean screenBlank = false;

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

        orientation(PORTRAIT);
        textSize(50);
        //frameRate(4);
        background(0);
        //test off screen button://////////////////////////////////////////////////////////////////////////
        sunflower = loadImage("sunflower.jpg");
        buttonSwitchScreen = new Button (  width/6*5, height/6*5,
                width/6, height/6,
                true, color (0, 0, 0), //Black button
                true, color (100),
                "BATTERY\nSAVER",
                "",
                1,
                sunflower);
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        cp5 = new ControlP5(this);
        myKnobRed = cp5.addKnob("RED")
                .setRange(0, 255)
                .setValue(50)
                .setPosition(0, height/6*5)
                .setRadius(width/12)
                .setColorBackground(colourRed)
                .setDragDirection(Knob.VERTICAL)
                .setVisible(false);
        ;
        myKnobGreen = cp5.addKnob("GREEN")
                .setRange(0, 255)
                .setValue(50)
                .setPosition(width/6, height/6*5)
                .setRadius(width/12)
                .setColorBackground(colourGreen)
                .setDragDirection(Knob.VERTICAL)
                .setVisible(false);
        ;
        myKnobBlue = cp5.addKnob("BLUE")
                .setRange(0, 255)
                .setValue(50)
                .setPosition(width/6*2, height/6*5)
                .setRadius(width/12)
                .setColorBackground(colourBlue)
                .setDragDirection(Knob.VERTICAL)
                .setVisible(false);
        ;
        brightnessSlider = cp5.addSlider("BRT")
                .setPosition(50, 50)
                .setSize(width/3*2, height/3*2)
                .setRange(0.01f, 0.7f)
                .setValue(0.01f)
                .setColorForeground(color(150, 100, 100))
                .setColorBackground(color(50, 0, 0))
                .setColorActive(color(100, 50, 50))
                .setNumberOfTickMarks(12)
                .setSliderMode(Slider.FLEXIBLE)
                .setVisible(true)
        ;

        //////////////////////////////UDP Code://////////////////////
        udp = new UDP( this ); // no need port, can cause errors...?

//  udp = new UDP( this, UDPport ); // create a new datagram connection on port 8888
        //udp.log( true ); // <\u2013 printout the connection activity
        udp.listen( true ); // and wait for incoming message
        /////////////////////////////////////////////////////////////
        //frameRate(5);
        pg = createGraphics(36, 36);
        pgMask1 = createGraphics(36, 36);
        smooth();

        colorMode(HSB);
        palette = new int[256];
        for (int i = 0; i < 256; i++) {
            palette[i] = color(i, 255, 255);
        }

        plasma = new int[pg.width*pg.height];
        for (int x = 0; x < pg.width; x++) {
            for (int y = 0; y < pg.height; y++) {
                plasma[y*pg.width+x] = PApplet.parseInt(255*abs(sin(noise(x*scale, y*scale)*f)));
            }
        }
    }


    @Override
    public void draw() {
        sendConfigMessageToPoi();
  /*
  speed = mouseX;
  //println(speed);
  int varX = xInt;

  float f = float(frameCount);
  color noiseCol = color(
  128 - 128*sin(f / k(100, f) + y/k(10, f)),
  128 - 128*sin(f / k(150, f) + y/k(20, f)),
  128 - 128*sin(f / k(200, f) + y/k(10, f))
    );
  if (goingUp) {

    //  drawColouredLine(color(myKnobRed.getValue(), myKnobGreen.getValue(), myKnobBlue.getValue()), varX);
    drawColouredThing(noiseCol, varX);
  }
  //println(i);
  else {
    //  drawColouredLine(color(myKnobRed.getValue(), myKnobGreen.getValue(), myKnobBlue.getValue()), 35-varX);
    drawColouredThing(noiseCol, 35-varX);
  }
  xInt++;
  if (xInt>36) {
    goingUp = !goingUp;
    xInt = 0;
  }
  */
    }

    public void BRT(float theBRT) { //called on slider change?
        showOnce = true; //update screen
        BRT = theBRT;
    }

    public float k(float n, float f) {

        return 10 + 100 * noise(n + f / 3000);
    }

    public void drawColouredThing(int c, int up) {

        if (variation == 0) {
            //delay(100); //enable for testing
            int to = color(255-red(c), 255-green(c), 255-blue(c));

            pg.beginDraw();
            pg.strokeWeight(2);
            pg.background(to);
            //    pg.stroke(c);
            pg.stroke(0);
            pg.fill(c);

            //    pg.line(35-up, 0, 35-up, 35);
            //    pg.line(up, 0, up, 35);
            pg.arc(pg.width/2, pg.height/2, pg.width-1, pg.height-1, 0, TWO_PI);
            //pg.arc(pg.width/2, pg.height/2, 18+up, 35-up+10, 0, TWO_PI);
            //pg.tint(BRT);
            pg.endDraw();
        } else if (variation == 1) {

            int to = color(constrain(255-red(c), 80, 255), constrain(255-green(c), 80, 255), constrain(255-blue(c), 80, 255));
            pg.beginDraw();
            pg.strokeWeight(1);
            pg.background(to);
            //    pg.stroke(c);
            pg.stroke(0);
            pg.fill(0);
            pg.triangle(0, 0, 35, 0, 18, 35);
            //pg.triangle(18, constrain(up/2, 0, 18),  0, constrain(up,18, 35),  35, constrain(up,18, 35));
            //pg.triangle(constrain(up/2, 0, 18), 18, constrain(up,18, 35), 0, constrain(up,18, 35), 35);
            pg.endDraw();
        } else if (variation == 2) {
            int to = color(255-red(c), 255-green(c), 255-blue(c));
            pg.beginDraw();
            pg.loadPixels();
            for (int i = 0; i < plasma.length; i++) {
                pg.pixels[i] = palette[(plasma[i] + t) % 256];
            }
            pg.updatePixels();
            t++;
            pg.strokeWeight(1);
            //pg.background(to);
            //    pg.stroke(c);
            pg.stroke(0);
            pg.fill(0);
            pg.triangle(18, 18, 35, 0, 35, 35);
            pg.triangle(0, 18, 18, 0, 18, 35);
            pg.tint(BRT*255);
            pg.endDraw();
        } else if (variation == 3) {

            pg.beginDraw();
            pg.loadPixels();
            for (int i = 0; i < plasma.length; i++) {
                pg.pixels[i] = palette[(plasma[i] + t) % 256];
            }
            pg.updatePixels();
            t++;
            pgMask1.beginDraw();
            pgMask1.background(0);
            pgMask1.filter(INVERT);
            pgMask1.strokeWeight(1);
            pgMask1.stroke(0);
            pgMask1.fill(0);
            pgMask1.triangle(0, 0, 35, 0, 18, 35);
            //pgMask1.triangle(18, 0, 0, 18, 35, 18);
            pgMask1.endDraw();
            //pg.image(pgMask1, 0, 0);
            //pg.mask(pgMask1);
            pg.blend(pgMask1, 0, 0, pg.width, pg.height, 0, 0, pg.width, pg.height, SUBTRACT);
            //pg.tint(BRT);
            pg.endDraw();
        } else if (variation == 4) {
            pg.beginDraw();
            pg.loadPixels();
            for (int i = 0; i < plasma.length; i++) {
                pg.pixels[i] = palette[(plasma[i] + t) % 256];
            }
            pg.updatePixels();
            t++;
            pgMask1.beginDraw();
            pgMask1.background(0);
            pgMask1.filter(INVERT);
            pgMask1.strokeWeight(1);
            //    pg.stroke(c);
            pgMask1.stroke(0);
            pgMask1.fill(0);

            pgMask1.line(35, 0, 35, 35);
            pgMask1.line(0, 0, 0, 35);
            pgMask1.arc(pg.width/2, pg.height/2, 18, 18, 0, TWO_PI);
            pgMask1.endDraw();
            pg.blend(pgMask1, 0, 0, pg.width, pg.height, 0, 0, pg.width, pg.height, SUBTRACT);
            //pg.tint(BRT);
            pg.endDraw();
        }
        if (!screenBlank) {
            if (showOnce) {
                background(0);
                tint(BRT*255);
                image(pg, 0, 0, displayWidth, displayWidth);
                buttonSwitchScreen.display();
                showOnce = false;
                text(variation, width/2+60, height-100);
            }
        } else {
            if (showOnce) {
                background(0);
                //tint(BRT);
                //image(pg, 0, 0, 400, 400);
                //buttonSwitchScreen.display();
                showOnce = false;
                //text(variation, width/2+60, height-100);
            }
        }

        //image(pg, 0, 0, 400, 400);



        //send to poi
        sendConfigMessageToPoi();
//  sendPGraphicsToPoi(pg, 0);
//  sendPGraphicsToPoi(pg, 0);
        //delay(100); //for testing
        //frameRate(constrain(int(speed), 2, 60)); // not the way to do it, neither is delay()
    }

    public void knob(int theValue) {
        myColorBackground = color(theValue);
        //println("a knob event. setting background to "+theValue);
    }

    public void mousePressed() {
  /*
  if (brightnessSlider.isVisible()) {
  } else {
    if (buttonSwitchScreen.over()) {
      screenBlank = true;
      showOnce=true;
    } else {
      if (!screenBlank) {
        variation++;
        if (variation>=variationMax) {
          variation = 0;
        }
      } else {
        screenBlank = false;
      }
      showOnce=true;
    }
  }
  */
    }

    public void keyPressed() {
  /*
  if (key == CODED) {
    if (keyCode == MENU) {
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
  */
    }

    /**
     * ControlP5 Slider
     *
     *
     * find a list of public methods available for the Slider Controller
     * at the bottom of this sketch.
     *
     * by Andreas Schlegel, 2012
     * www.sojamo.de/libraries/controlp5
     *
     */

/*
a list of all methods available for the Slider Controller
 use ControlP5.printPublicMethodsFor(Slider.class);
 to print the following list into the console.

 You can find further details about class Slider in the javadoc.

 Format:
 ClassName : returnType methodName(parameter type)

 controlP5.Slider : ArrayList getTickMarks()
 controlP5.Slider : Slider setColorTickMark(int)
 controlP5.Slider : Slider setHandleSize(int)
 controlP5.Slider : Slider setHeight(int)
 controlP5.Slider : Slider setMax(float)
 controlP5.Slider : Slider setMin(float)
 controlP5.Slider : Slider setNumberOfTickMarks(int)
 controlP5.Slider : Slider setRange(float, float)
 controlP5.Slider : Slider setScrollSensitivity(float)
 controlP5.Slider : Slider setSize(int, int)
 controlP5.Slider : Slider setSliderMode(int)
 controlP5.Slider : Slider setTriggerEvent(int)
 controlP5.Slider : Slider setValue(float)
 controlP5.Slider : Slider setWidth(int)
 controlP5.Slider : Slider showTickMarks(boolean)
 controlP5.Slider : Slider shuffle()
 controlP5.Slider : Slider snapToTickMarks(boolean)
 controlP5.Slider : Slider update()
 controlP5.Slider : TickMark getTickMark(int)
 controlP5.Slider : float getValue()
 controlP5.Slider : float getValuePosition()
 controlP5.Slider : int getDirection()
 controlP5.Slider : int getHandleSize()
 controlP5.Slider : int getNumberOfTickMarks()
 controlP5.Slider : int getSliderMode()
 controlP5.Slider : int getTriggerEvent()
 controlP5.Controller : CColor getColor()
 controlP5.Controller : ControlBehavior getBehavior()
 controlP5.Controller : ControlWindow getControlWindow()
 controlP5.Controller : ControlWindow getWindow()
 controlP5.Controller : ControllerProperty getProperty(String)
 controlP5.Controller : ControllerProperty getProperty(String, String)
 controlP5.Controller : Label getCaptionLabel()
 controlP5.Controller : Label getValueLabel()
 controlP5.Controller : List getControllerPlugList()
 controlP5.Controller : PImage setImage(PImage)
 controlP5.Controller : PImage setImage(PImage, int)
 controlP5.Controller : PVector getAbsolutePosition()
 controlP5.Controller : PVector getPosition()
 controlP5.Controller : Slider addCallback(CallbackListener)
 controlP5.Controller : Slider addListener(ControlListener)
 controlP5.Controller : Slider bringToFront()
 controlP5.Controller : Slider bringToFront(ControllerInterface)
 controlP5.Controller : Slider hide()
 controlP5.Controller : Slider linebreak()
 controlP5.Controller : Slider listen(boolean)
 controlP5.Controller : Slider lock()
 controlP5.Controller : Slider plugTo(Object)
 controlP5.Controller : Slider plugTo(Object, String)
 controlP5.Controller : Slider plugTo(Object[])
 controlP5.Controller : Slider plugTo(Object[], String)
 controlP5.Controller : Slider registerProperty(String)
 controlP5.Controller : Slider registerProperty(String, String)
 controlP5.Controller : Slider registerTooltip(String)
 controlP5.Controller : Slider removeBehavior()
 controlP5.Controller : Slider removeCallback()
 controlP5.Controller : Slider removeCallback(CallbackListener)
 controlP5.Controller : Slider removeListener(ControlListener)
 controlP5.Controller : Slider removeProperty(String)
 controlP5.Controller : Slider removeProperty(String, String)
 controlP5.Controller : Slider setArrayValue(float[])
 controlP5.Controller : Slider setArrayValue(int, float)
 controlP5.Controller : Slider setBehavior(ControlBehavior)
 controlP5.Controller : Slider setBroadcast(boolean)
 controlP5.Controller : Slider setCaptionLabel(String)
 controlP5.Controller : Slider setColor(CColor)
 controlP5.Controller : Slider setColorActive(int)
 controlP5.Controller : Slider setColorBackground(int)
 controlP5.Controller : Slider setColorCaptionLabel(int)
 controlP5.Controller : Slider setColorForeground(int)
 controlP5.Controller : Slider setColorValueLabel(int)
 controlP5.Controller : Slider setDecimalPrecision(int)
 controlP5.Controller : Slider setDefaultValue(float)
 controlP5.Controller : Slider setHeight(int)
 controlP5.Controller : Slider setId(int)
 controlP5.Controller : Slider setImages(PImage, PImage, PImage)
 controlP5.Controller : Slider setImages(PImage, PImage, PImage, PImage)
 controlP5.Controller : Slider setLabelVisible(boolean)
 controlP5.Controller : Slider setLock(boolean)
 controlP5.Controller : Slider setMax(float)
 controlP5.Controller : Slider setMin(float)
 controlP5.Controller : Slider setMouseOver(boolean)
 controlP5.Controller : Slider setMoveable(boolean)
 controlP5.Controller : Slider setPosition(PVector)
 controlP5.Controller : Slider setPosition(float, float)
 controlP5.Controller : Slider setSize(PImage)
 controlP5.Controller : Slider setSize(int, int)
 controlP5.Controller : Slider setStringValue(String)
 controlP5.Controller : Slider setUpdate(boolean)
 controlP5.Controller : Slider setValueLabel(String)
 controlP5.Controller : Slider setView(ControllerView)
 controlP5.Controller : Slider setVisible(boolean)
 controlP5.Controller : Slider setWidth(int)
 controlP5.Controller : Slider show()
 controlP5.Controller : Slider unlock()
 controlP5.Controller : Slider unplugFrom(Object)
 controlP5.Controller : Slider unplugFrom(Object[])
 controlP5.Controller : Slider unregisterTooltip()
 controlP5.Controller : Slider update()
 controlP5.Controller : Slider updateSize()
 controlP5.Controller : String getAddress()
 controlP5.Controller : String getInfo()
 controlP5.Controller : String getName()
 controlP5.Controller : String getStringValue()
 controlP5.Controller : String toString()
 controlP5.Controller : Tab getTab()
 controlP5.Controller : boolean isActive()
 controlP5.Controller : boolean isBroadcast()
 controlP5.Controller : boolean isInside()
 controlP5.Controller : boolean isLabelVisible()
 controlP5.Controller : boolean isListening()
 controlP5.Controller : boolean isLock()
 controlP5.Controller : boolean isMouseOver()
 controlP5.Controller : boolean isMousePressed()
 controlP5.Controller : boolean isMoveable()
 controlP5.Controller : boolean isUpdate()
 controlP5.Controller : boolean isVisible()
 controlP5.Controller : float getArrayValue(int)
 controlP5.Controller : float getDefaultValue()
 controlP5.Controller : float getMax()
 controlP5.Controller : float getMin()
 controlP5.Controller : float getValue()
 controlP5.Controller : float[] getArrayValue()
 controlP5.Controller : int getDecimalPrecision()
 controlP5.Controller : int getHeight()
 controlP5.Controller : int getId()
 controlP5.Controller : int getWidth()
 controlP5.Controller : int listenerSize()
 controlP5.Controller : void remove()
 controlP5.Controller : void setView(ControllerView, int)
 java.lang.Object : String toString()
 java.lang.Object : boolean equals(Object)


 */



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
                tint(BRT*255);
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

    public byte pixelConverter(int red, int green, int blue) {
        byte encodedRGB;
        //int   r, g, b;
        encodedRGB = PApplet.parseByte((red & 0xE0) | ((green & 0xE0)>>3) | (blue >> 6));
        return encodedRGB;
    }


    boolean send0 = false;
    public void sendConfigMessageToPoi() {
        byte[] message = new byte[36];
        for (byte a = 0; a < 36; a++) {
            if (a==3) {
                message[a] = PApplet.parseByte(BRT*254); //brightness value, testing here
//      message[a] = 1; //brightness value, testing here
                send0 = true;
            } else {
                if (send0) {
                    message[a] = 127; //translates to 0?
                } else {
                    message[a] = a;
                }
            }
        }
        send0 = false;
        udp.send(message, ip, UDPport); //disable for testing
        udp.send(message, ip2, UDPport); //disable for testing
        if(onRouter){
            udp.send(message, ip4, UDPport);
            udp.send(message, ip5, UDPport);
            udp.send(message, ip6, UDPport);
            udp.send(message, ip7, UDPport);
        }
    }

    int sendOption = 0;


    public void sendPGraphicsToPoi(PGraphics pgSend, int sendOpt) {
        int pixelCounter = 0;
        byte[] message = new byte[36];
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
                    if(r>0){
                        r=100;
                    }
                    if(g>0){
                        g=100;
                    }
                    if(b>0){
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
            //message[pixelCounter] = Y;
            bigpx[pixelCounter] = Y;

            //need to pre-load pic into array or it slows down serial!
            //delay(1);
            pixelCounter++;
    /*
    if (pixelCounter == pgSend.width) {
      if(!testing){
      udp.send(message, ip, UDPport); //disable for testing
      udp.send(message, ip2, UDPport); //disable for testing
      }
      pixelCounter = 0;
    }
    */
            if (pixelCounter == pgSend.width*2) {
                if(!testing){
//      udp.send(message, ip, UDPport); //disable for testing
//      udp.send(message, ip2, UDPport); //disable for testing
                    udp.send(bigpx, ip, UDPport); //disable for testing
                    udp.send(bigpx, ip2, UDPport); //disable for testing
                    if(onRouter){
                        udp.send(bigpx, ip4, UDPport);
                        udp.send(bigpx, ip5, UDPport);
                        udp.send(bigpx, ip6, UDPport);
                        udp.send(bigpx, ip7, UDPport);
                    }
                }
                pixelCounter = 0;
            }

            //delay(100);
        }
    }

    public void triangleMask(){

    }

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
