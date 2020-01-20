package za.tomjuggler.processingdemo.sketch;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import controlP5.ControlP5;
import controlP5.Knob;
import hypermedia.net.UDP;
import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * Created by tom on 2017/09/04.
 */






        import processing.core.*;
        import processing.data.*;
        import processing.event.*;
        import processing.opengl.*;

        import controlP5.*;
        import hypermedia.net.*;
import za.tomjuggler.processingdemo.R;

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

public class ZapGame extends PApplet {




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
    String ip2 = "192.168.1.78"; // change this with android saved settings for testing
    String ip = "192.168.1.1"; // change in constructor now
    String ip3 = "192.168.1.79"; // the remote IP address (Accelerometer)

    int UDPport = 2390; // the destination port
//////////////////////////////////////////////////////////////

    PGraphics pg;
    PGraphics pg2;

    int xInt = 0;
    int incrDn = 0;
    boolean goingUp = true;
    int r, g, b;
    boolean testing = false;
    float speed;

    int maxPx = 36;
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

        //frameRate(4);
        background(0);
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

        //////////////////////////////UDP Code://////////////////////
        udp = new UDP( this, UDPport ); // create a new datagram connection on port 8888
        //udp.log( true ); // <\u2013 printout the connection activity
        udp.listen( true ); // and wait for incoming message
        /////////////////////////////////////////////////////////////
        //frameRate(5);
        pg = createGraphics(36, 36);
        pg2 = createGraphics(36, 1);
    }


    @Override
    public void draw() {

        drawColouredThing(color(0, 0, 0), 0, 0);
        speed = mouseX;
        println(speed);
        fill(0, 255, 255);
        rect(0, 0, width, height/2);
        fill(255, 0, 255);
        rect(0, height/2, width, height/2);

    }



    public void drawColouredThing(int c, int up, int poi) {

        pg2.beginDraw();
        pg2.strokeWeight(1);
        pg2.background(0);
        pg2.stroke(c);
        //pg2.line(35-up, 0, up, 0);
        //pg2.point(35-up, 0);
        pg2.point(up, 0);
        pg2.endDraw();

        //background(0);
        //    image(pg, 0, height/2-5, width, 10);
        image(pg2, 100, xInt+incrDn*(maxPx-1)+10, pg2.width*10, 1);
        println("image: " + xInt);
        println("frameRate: " + frameRate);
        //send to poi
        switch(poi) {
            case 0:
                sendPGraphicsToPoi(pg2, 0);
            case 1:
                sendPGraphicsToPoi1(pg2, 0);
                break;
            case 2:
                sendPGraphicsToPoi2(pg2, 0);
                break;
        }

        //delay(100); //for testing
        //frameRate(constrain(int(speed), 2, 60)); // not the way to do it, neither is delay()
    }




    public void knob(int theValue) {
        myColorBackground = color(theValue);
        println("a knob event. setting background to "+theValue);
    }

    public void mousePressed() {
        if (mouseY > height/2) {
            for (int i = 0; i < 37; i++) {
                int varX = xInt;
//      drawColouredThing(color(myKnobRed.getValue(), myKnobGreen.getValue(), myKnobBlue.getValue()), varX, 1);
                drawColouredThing(color(255, 0, 255), varX, 1);
                xInt++;
                delay(3);
                if (xInt>36) {
                    goingUp = !goingUp;
                    incrDn++;
                    if (incrDn > 8) {
                        background(0);
                        incrDn = 0;
                    }
                    xInt = 0;
                }
            }
        } else {
            for (int i = 0; i < 37; i++) {
                int varX = xInt;
//      drawColouredThing(color(myKnobRed.getValue(), myKnobGreen.getValue(), myKnobBlue.getValue()), varX, 2);
                drawColouredThing(color(0, 255, 255), varX, 2);
                xInt++;
                delay(3);
                if (xInt>36) {

                    goingUp = !goingUp;
                    incrDn++;
                    if (incrDn > 8) {
                        background(0);
                        incrDn = 0;
                    }
                    xInt = 0;
                }
            }
        }
    }

    public void keyPressed(){
        if(key == ' '){
            for (int i = 0; i < 37; i++) {
                int varX = xInt;
                drawColouredThing(color(255, 255, 0), varX, 0);
                xInt++;
                delay(3);
                if (xInt>36) {

                    goingUp = !goingUp;
                    incrDn++;
                    if (incrDn > 8) {
                        background(0);
                        incrDn = 0;
                    }
                    xInt = 0;
                }
            }
        }
//  // method for generating a data file
//  // this prevents ociliations in the startup of the program
//  if(key==' '){
//    String[] data = new String[num];
//    for(int i=0;i<num;i++){
//      // each circle records its location and radius
//      data[i] = nf(loc[i].x,1,3)+"\t"+nf(loc[i].y,1,3)+"\t"+nf(rad[i],1,3);
//    }
//    saveStrings("data.txt",data);
//  }
    }

    public byte pixelConverter(int red, int green, int blue) {
        byte encodedRGB;
        //int   r, g, b;
        encodedRGB = PApplet.parseByte((red & 0xE0) | ((green & 0xE0)>>3) | (blue >> 6));
        return encodedRGB;
    }

    public void sendPGraphicsToPoi(PGraphics pgSend, int sendOpt) {
        int pixelCounter = 0;
        byte[] message = new byte[maxPx];
        //byte[] bigpx = new byte[72];
        for (int a = 0; a < pgSend.width*pgSend.height; a++) {

            if (pixelCounter == 0) {
                //port.write(byte(startByte));
            }

            r = (int) red(pgSend.pixels[a]);
            g = (int) green(pgSend.pixels[a]);
            b = (int) blue(pgSend.pixels[a]);
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
            message[pixelCounter] = Y;
//    bigpx[pixelCounter] = Y;

            //need to pre-load pic into array or it slows down serial!
            //delay(1);
            pixelCounter++;

            if (pixelCounter == pgSend.width) {
                if(!testing){
                    udp.send(message, ip, UDPport);
                    //sending twice for 72px support:
                    udp.send(message, ip, UDPport);
                    udp.send(message, ip2, UDPport);
                    //sending twice for 72px support:
                    udp.send(message, ip2, UDPport);
                }
                pixelCounter = 0;
            }
/*
    if (pixelCounter == pgSend.width*2) {
      if(!testing){
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

    public void sendPGraphicsToPoi1(PGraphics pgSend, int sendOpt) {
        int pixelCounter = 0;
        byte[] message = new byte[maxPx];
        //byte[] bigpx = new byte[72];
        for (int a = 0; a < pgSend.width*pgSend.height; a++) {

            if (pixelCounter == 0) {
                //port.write(byte(startByte));
            }

            r = (int) red(pgSend.pixels[a]);
            g = (int) green(pgSend.pixels[a]);
            b = (int) blue(pgSend.pixels[a]);
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
            message[pixelCounter] = Y;
//    bigpx[pixelCounter] = Y;

            //need to pre-load pic into array or it slows down serial!
            //delay(1);
            pixelCounter++;

            if (pixelCounter == pgSend.width) {
                if(!testing){
                    udp.send(message, ip, UDPport);
                    //sending twice for 72px support:
//      udp.send(message, ip, UDPport);
//      udp.send(message, ip2, UDPport);
                    //sending twice for 72px support:
//      udp.send(message, ip2, UDPport);
                }
                pixelCounter = 0;
            }
/*
    if (pixelCounter == pgSend.width*2) {
      if(!testing){
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

    public void sendPGraphicsToPoi2(PGraphics pgSend, int sendOpt) {
        int pixelCounter = 0;
        byte[] message = new byte[maxPx];
        //byte[] bigpx = new byte[72];
        for (int a = 0; a < pgSend.width*pgSend.height; a++) {

            if (pixelCounter == 0) {
                //port.write(byte(startByte));
            }

            r = (int) red(pgSend.pixels[a]);
            g = (int) green(pgSend.pixels[a]);
            b = (int) blue(pgSend.pixels[a]);
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
            message[pixelCounter] = Y;
//    bigpx[pixelCounter] = Y;

            //need to pre-load pic into array or it slows down serial!
            //delay(1);
            pixelCounter++;

            if (pixelCounter == pgSend.width) {
                if(!testing){
//      udp.send(message, ip, UDPport);
                    //sending twice for 72px support:
//      udp.send(message, ip, UDPport);
                    udp.send(message, ip2, UDPport);
                    //sending twice for 72px support:
//      udp.send(message, ip2, UDPport);
                }
                pixelCounter = 0;
            }
/*
    if (pixelCounter == pgSend.width*2) {
      if(!testing){
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
