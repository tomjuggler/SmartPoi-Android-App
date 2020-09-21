package za.tomjuggler.processingdemo.sketch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import controlP5.ControlP5;
import controlP5.Knob;
import controlP5.Slider;
import hypermedia.net.UDP;
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
import static za.tomjuggler.processingdemo.MainActivity.mypreference;
import static za.tomjuggler.processingdemo.MainActivity.routerSaved;

public class PGraphics_Pattern_Template extends PApplet {


    ControlP5 cp5;

    int myColorBackground = color(0, 0, 0);
    int colourGreen = color(0, 255, 0);
    int colourRed = color(255, 0, 0);
    int colourBlue = color(0, 0, 255);

    Knob myKnobBlue;
    Knob myKnobRed;
    Knob myKnobGreen;

//UDP code:///////////////////////////////////////////////////


    UDP udp; // define the UDP object


//    String ip2 = "192.168.8.78"; // change this with android saved settings for testing

    String ip2 = "192.168.1.78"; // change this with android saved settings for testing
    String ip = "192.168.1.1"; // change in constructor now
    String ip3 = "192.168.1.79"; // the remote IP address (Accelerometer)
    //more for testing: //not using these because of esp8266 AP limit!
//    String ip4 = "192.168.1.71";
//    String ip5 = "192.168.1.72";

    //test add more poi:
    String ip4 = "192.168.8.80";
    String ip5 = "192.168.8.81";
    String ip6 = "192.168.8.82";
    String ip7 = "192.168.8.83";
    boolean onRouter = false;

    int UDPport = 2390; // the destination port
//////////////////////////////////////////////////////////////

    PGraphics pg;
    PGraphics pgMask1;
    PGraphics pgMask2;

    int xInt = 0;
    boolean goingUp = true;
    int r, g, b;
    boolean testing = false;
    float speed;

    float y = 3;

    int variation = 0;
    int variationMax = 8;

    int t = 0;
    int f = 100;
    float scale = 0.002f;
    int[] plasma;
    int palette[];

    boolean showOnce = true;

    Button buttonSwitchScreen;
    Button buttonSaveBattery;
    Button buttonPlayAll;
    boolean playAll = false;
    PImage sunflower;
    boolean screenBlank = false;

    //brightness idea:
    float BRT = 1.0f;
    boolean showSlider = false;

    Slider brightnessSlider;
    PImage img;
    PImage img1;

    int timer = 0;
    int sequenceShowTime = 3000; //change every ... milliseconds

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
        //println("ssid is: " + ssid);
        //double quotation marks for ssid always apparently!
//        String expectedSSID = "\"Smart_Poi_2\""; //
        Resources res = getResources();
        String expectedSSID = res.getString(R.string.ap_name); //todo: put in saved settings
        String expectedSSID2 = res.getString(R.string.ap_name2); //todo: put in saved settings
        String expectedSSID3 = res.getString(R.string.ap_name3); //todo: put in saved settings
        if(ssid.equals(expectedSSID) || ssid.equals(expectedSSID2) || ssid.equals(expectedSSID3)){ //compare strings - why the hell doesn't this work????
            //println("AP Mode Hard Coded Now!");
        } else {
            onRouter = true;
            //todo: add more .equals calls for different poi? maybe not now...
            //println("Router Network - using saved settings");
//        }
            //load all preferences:
            sharedpreferences = this.getActivity().getSharedPreferences("mypref", 0);
            if (sharedpreferences.contains(ipa1Saved)) { //is it saved? use this...
                ipa = sharedpreferences.getString(ipa1Saved, "192");
                //println("ipa is: " + ipa);
            } else {
                ipa = "192";
            }
            if (sharedpreferences.contains(ipa2Saved)) { //is it saved? use this...
                ipb = sharedpreferences.getString(ipa2Saved, "168");
                //println("ipb is: " + ipb);
            } else {
                ipb = "168";
            }
            if (sharedpreferences.contains(ipa3Saved)) { //is it saved? use this...
                ipc = sharedpreferences.getString(ipa3Saved, "8");
                //println("ipc is: " + ipc);
            } else {
                ipc = "8";
            }
            if (sharedpreferences.contains(ipa4Saved)) { //is it saved? use this...
                ipd = sharedpreferences.getString(ipa4Saved, "78");
                //println("ipd is: " + ipd);

            } else {
                ipd = "78";
                //println("no ipd");
            }
            if (sharedpreferences.contains(ipa5Saved)) { //is it saved? use this...
                ipe = sharedpreferences.getString(ipa5Saved, "79");
                //println("ipe is: " + ipe);

            } else {
                ipe = "79";
                //println("no ipe");
            }

            //println("ip before: " + ip);
            ip = ipa + "." + ipb + "." + ipc + "." + ipd;
            //println("ip after: " + ip);

            //println("ip2 before: " + ip2);
            ip2 = ipa + "." + ipb + "." + ipc + "." + ipe;
            //println("ip2 after: " + ip2);
        } //now ip only changed from default if AP not connected direct!
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

        buttonPlayAll = new Button(width / 6, height / 6 * 5,
                width / 6, height / 6,
                true, color(0, 0, 0), //Black button
                true, color(100),
                "PLAY\nALL",
                "",
                2,
                sunflower);
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        cp5 = new ControlP5(this);
        myKnobRed = cp5.addKnob("RED")
                .setRange(0, 255)
                .setValue(50)
                .setPosition(0, height / 6 * 5)
                .setRadius(width / 12)
                .setColorBackground(colourRed)
                .setDragDirection(Knob.VERTICAL)
                .setVisible(false);
        ;
        myKnobGreen = cp5.addKnob("GREEN")
                .setRange(0, 255)
                .setValue(50)
                .setPosition(width / 6, height / 6 * 5)
                .setRadius(width / 12)
                .setColorBackground(colourGreen)
                .setDragDirection(Knob.VERTICAL)
                .setVisible(false);
        ;
        myKnobBlue = cp5.addKnob("BLUE")
                .setRange(0, 255)
                .setValue(50)
                .setPosition(width / 6 * 2, height / 6 * 5)
                .setRadius(width / 12)
                .setColorBackground(colourBlue)
                .setDragDirection(Knob.VERTICAL)
                .setVisible(false);
        ;
        brightnessSlider = cp5.addSlider("BRT")
                .setPosition(0, height / 6 * 2)
                .setSize(width / 3, height / 6 * 3)
                .setRange(0, 1)
                .setValue(1.0f)
                .setColorForeground(color(150, 100, 100))
                .setColorBackground(color(50, 0, 0))
                .setColorActive(color(100, 50, 50))
                .setVisible(false)
        ;

        //////////////////////////////UDP Code://////////////////////
        udp = new UDP(this, UDPport); // create a new datagram connection on port 8888
        //udp.log( true ); // <\u2013 printout the connection activity
        udp.listen(true); // and wait for incoming message
        /////////////////////////////////////////////////////////////
        //frameRate(5);
        pg = createGraphics(36, 36);
        pgMask1 = createGraphics(36, 36);
        pgMask2 = createGraphics(36, 36);
        smooth();

        colorMode(HSB);
        palette = new int[256];
        for (int i = 0; i < 256; i++) {
            palette[i] = color(i, 255, 255);
        }

        plasma = new int[pg.width * pg.height];
        for (int x = 0; x < pg.width; x++) {
            for (int y = 0; y < pg.height; y++) {
                plasma[y * pg.width + x] = PApplet.parseInt(255 * abs(sin(noise(x * scale, y * scale) * f)));
            }
        }

        rMain = -PI;
        img = loadImage("image.jpg");
        img1 = loadImage("image1.jpg");

        maskPic();
    }



    @Override
    public void draw() {
        speed = mouseX;
        //println(speed);
        int varX = xInt;

        float f = PApplet.parseFloat(frameCount);
        int noiseCol = color(
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
    }

    public void BRT(float theBRT) { //called on slider change?
        showOnce = true; //update screen
        BRT = theBRT;
        sequenceShowTime = PApplet.parseInt(6000*BRT);
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
        } else if (variation == 5) { //new method:
//    maskPic();
            mainPic();
//    background(0); //test only
//    image(pg, 0, 0, width, width); //testing only
        }
        else if (variation == 6) { //Picture Mask method:
            maskPic2();
            mainPic2();
//  background(0); //testing only
//  tint(255);
//  image(pg, 0, 0, width, width);
        }
        else if (variation == 7) { //Picture Mask generic pass image to function method:
            maskPic3(img1, true); //(image, bigsmall boolean)
            mainPic3();
        }
        if (!screenBlank) {
            if (showOnce) {
                maskPic(); //just a test!
                maskPic();
                background(0);
//      tint(BRT*255);
                image(pg, 0, 0, displayWidth, displayWidth);
                buttonSwitchScreen.display();
                buttonSaveBattery.display();
                buttonPlayAll.display();
                showOnce = false;
                text(variation, width/2+60, height-100);
            }
        } else {
            if (showOnce) {
                background(0);
                //tint(BRT);
//      image(pg, 0, 0, 400, 400);
//      buttonSwitchScreen.display();
                showOnce = false;
//      text(variation, width/2+60, height-100);
            }

            //testing only:
//    background(0);
//    image(pg, 0, 0, 400, 400);
//    buttonSwitchScreen.display();
//    buttonPlayAll.display();
//    showOnce = false;
//    text(variation, width/2+60, height-100);
        }

        //image(pg, 0, 0, 400, 400);

        if(playAll){
            if (millis() - timer >= sequenceShowTime) {
                showOnce = true;
                variation++;
                timer = millis();
            }
            if (variation>=variationMax) {
                variation = 0;
            }
            text(">>>>>>>>>>>>", width/2+60, height-50);
        }

        //send to poi
        sendPGraphicsToPoi(pg, 0);
        sendPGraphicsToPoi(pg, 0); //was twice for big poi compatibility???
        //delay(100); //for testing
        //frameRate(constrain(int(speed), 2, 60)); // not the way to do it, neither is delay()
    }

    public void knob(int theValue) {
        myColorBackground = color(theValue);
        //println("a knob event. setting background to "+theValue);
    }

    public void mousePressed() {

            if(buttonPlayAll.over()){
                playAll = !playAll;
                screenBlank = true;
                showOnce=true;
            }
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

    public void keyPressed() {
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

    public void mainPic(){
        pg.beginDraw();
//  pg.background(100, 0, 0);
        pg.background(random(200), random(200), random(200));
        pg.loadPixels();
        pg.updatePixels();
        pg.blend(pgMask2, 0, 0, pg.width, pg.height, 0, 0, pg.width, pg.height, SUBTRACT);
        pg.endDraw();
    }
    float s = 1;
    float rMain = 0;
    float m = 18;

    public void mainPic2(){
        pg.beginDraw();
        for (int i = 0; i < plasma.length; i++) {
            pg.pixels[i] = palette[(plasma[i] + t) % 256];
        }
        pg.updatePixels();
        t++;
//  pg.background(255, 0, 255);
//  pg.background(random(200), random(200), random(200));
//pg.noStroke();
//pg.colorMode(RGB, 100);
//for (int i = 0; i < pg.width; i++) {
//  for (int j = 0; j < pg.height; j++) {
//    pg.stroke(i, j, 0);
//    pg.point(i, j);
//  }
//}
//pg.noStroke();
//pg.colorMode(HSB, 36);
//for (int i = 0; i < pg.width; i++) {
//  for (int j = 0; j < pg.height; j++) {
//    pg.stroke(i, j, 255);
//    pg.point(i, j);
//  }
//}
//pg.colorMode(HSB,255,0,255,255);
//  rMain += 0.01;
//  pg.background(0);
//  pg.rectMode(CENTER);
//  pg.translate(pg.width/2,pg.height/2);
//  for(int i = 0; i < m; i++){
//    idkName(map(i,0,m,0,255));
//  }

//  pg.loadPixels();
//  pg.updatePixels();
        pg.blend(pgMask1, 0, 0, pg.width, pg.height, 0, 0, pg.width, pg.height, SUBTRACT);
        pg.endDraw();
    }

    public void idkName(float cr){
        pg.rotate(rMain);
        pg.noStroke();
        for(int i = 0; i < pg.width;i+=s){
            pg.fill(cr,255,255,map(i,0,pg.width,10,50));
            pg.rect(0,i,s,s);
            pg.rect(0,-i,s,s);
            pg.rect(i,0,s,s);
            pg.rect(-i,0,s,s);
            //-----------------
            pg.rect(i,i,s,s);
            pg.rect(i,-i,s,s);
            pg.rect(-i,i,s,s);
            pg.rect(-i,-i,s,s);
            s+=map(i,0,pg.width,1,4);
        }
        s = 2;
    }
    public void mainPic3(){
        pg.beginDraw();
        for (int i = 0; i < plasma.length; i++) {
            pg.pixels[i] = palette[(plasma[i] + t) % 256];
        }
        pg.updatePixels();
        t++;
        pg.blend(pgMask1, 0, 0, pg.width, pg.height, 0, 0, pg.width, pg.height, SUBTRACT);
        pg.endDraw();
    }


    float rMask = 15;
    float theta = 0;

    //this one is still too slow to do every time... done in setup() for now
    public void maskPic(){
        pgMask2.beginDraw();
        //setup mask:
        pgMask2.background(0);
        pgMask2.filter(INVERT);
        pgMask2.strokeWeight(1);
        pgMask2.stroke(0);
        pgMask2.fill(0);
        //whatever shape here shows through:

        for(int i = 0; i<1300; i++){
            float x = rMask * cos(theta);
            float y = rMask * sin(theta);
//    pgMask2.point(x+pgMask2.width/2, y+pgMask2.height/2);
            pgMask2.ellipse(x+pgMask2.width/2, y+pgMask2.height/2, 1, 1);
            theta += 0.01f;
            rMask-=.01f;
            if(rMask < 1 || rMask > 16){
                rMask=16;
            }
        }

//  pgMask2.triangle(0, 0, 35, 0, 18, 35);
        //pgMask2.triangle(18, 0, 0, 18, 35, 18);
        pgMask2.endDraw();

    }



    //float rMask = 15;
//float theta = 0;
    int change = 0;
    boolean up = true;

    public void maskPic2(){
        pgMask1.beginDraw();
        //setup mask:
        pgMask1.background(0);
        pgMask1.filter(INVERT);
        pgMask1.strokeWeight(1);
        pgMask1.stroke(0);
        pgMask1.fill(0);
        //whatever shape here shows through:
        pgMask1.image(img, change, change, pg.width-change*2, pg.height-change*2);
//  pgMask1.image(img, 0, 0, pg.width, pg.height);
//  for(int i = 0; i<150; i++){ //this takes too long, need a better way to draw a spiral than this... too much lag to do in draw()
//    float x = rMask * cos(theta);
//    float y = rMask * sin(theta);
////    pgMask1.point(x+pgMask1.width/2, y+pgMask1.height/2);
//    pgMask1.ellipse(x+pgMask1.width/2, y+pgMask1.height/2, 1, 1);
//    theta += 0.1;
//    rMask-=.09;
//    if(rMask < 1 || rMask > 16){
//     rMask=16;
//    }
//  }

//  pgMask1.triangle(0, 0, 35, 0, 18, 35);
        //pgMask1.triangle(18, 0, 0, 18, 35, 18);
        pgMask1.endDraw();
//  if(up){
//    change+=2;
//    if(change > pgMask1.width/4){
//      //change = 0;
//      up=!up;
//    }
//  } else{
//    change-=2;
//    if(change == 0){
//      //change = pgMask1.width/2;
//      up=!up;
//    }
//  }
    }








    public void maskPic3(PImage maskImg, boolean bigsmall){
        pgMask1.beginDraw();
        //setup mask:
        pgMask1.background(0);
        pgMask1.filter(INVERT);
        pgMask1.strokeWeight(1);
        pgMask1.stroke(0);
        pgMask1.fill(0);
        //whatever shape in black maskImg shows through:
        pgMask1.image(maskImg, change, change, pg.width-change*2, pg.height-change*2);
        pgMask1.endDraw();
        //make bigger and smaller:

        if(bigsmall){
            if(up){
                change+=2;
                if(change > pgMask1.width/4){
                    //change = 0;
                    up=!up;
                }
            } else{
                change-=2;
                if(change == 0){
                    //change = pgMask1.width/2;
                    up=!up;
                }
            }
        }
    }








    public byte pixelConverter(int red, int green, int blue) {
        byte encodedRGB;
        //int   r, g, b;
        encodedRGB = PApplet.parseByte((red & 0xE0) | ((green & 0xE0)>>3) | (blue >> 6));
        return encodedRGB;
    }


    int sendOption = 0;

int numPixels = 72;
//int numPixels = 432; //testing max packet size send for reliability? this shouldn't work...

    public void sendPGraphicsToPoi(PGraphics pgSend, int sendOpt) {
        int pixelCounter = 0;
        byte[] message = new byte[numPixels]; //add numPixels like in TimelineOnly.java
        byte[] bigpx = new byte[72];
        for (int a = 0; a < pgSend.width*pgSend.height; a++) {

            if (pixelCounter == 0) {
                //port.write(byte(startByte));
            }

            r = pgSend.pixels[a] >> 16 & 0xFF;
            g = pgSend.pixels[a] >> 8 & 0xFF;
            b = pgSend.pixels[a] & 0xFF;

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
            message[pixelCounter] = Y;
//    bigpx[pixelCounter] = Y;

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
            if (pixelCounter == numPixels) {
                if(!testing){
                    udp.send(message, ip, UDPport); //disable for testing
                    udp.send(message, ip2, UDPport); //disable for testing
                    if(onRouter){
                        udp.send(message, ip4, UDPport);
                        udp.send(message, ip5, UDPport);
                        udp.send(message, ip6, UDPport);
                        udp.send(message, ip7, UDPport);
                    }
                    //what about big poi at same time??? Send twice for now, this may work as-is!
//                    udp.send(message, ip4, UDPport); //disable for testing
//                    udp.send(message, ip5, UDPport); //disable for testing
//            println("sent to: " + ip);
//      udp.send(bigpx, ip, UDPport); //disable for testing
//      udp.send(bigpx, ip2, UDPport); //disable for testing
                }
                pixelCounter = 0;
            }

            //delay(100);
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

/////////////////////////////////////////////////settings class////////////////////////////////////////

    public class KeyValueDB {
        private SharedPreferences sharedPreferences;
        private String PREF_NAME = "prefs";

        public KeyValueDB() {
            // Blank
        }

        private SharedPreferences getPrefs(Context context) {
            return getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }

        public String getIP4(Context context) {
                  return getPrefs(context).getString("ipa4Saved", "2");
        }

        public String getUsername(Context context) {
            return getPrefs(context).getString("username_key", "default_username");
        }

        public void setUsername(Context context, String input) {
            SharedPreferences.Editor editor = getPrefs(context).edit();
            editor.putString("username_key", input);
            editor.commit();
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////

}

