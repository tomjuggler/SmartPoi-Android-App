package za.tomjuggler.processingdemo.sketch.android_fft_minim;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioRecord;
import android.media.AudioFormat; 
import android.media.MediaRecorder;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.WindowManager;
import android.view.View; 
import android.os.Bundle; 
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

public class android_fft_minim extends PApplet {

/*
Audio spectrum analyzer for android devices
 12 may 2013
 David Sanz Kirbis
 
 This is a remix of a lot of code and info.
 
 Basically adapted some of the minim analysis code to use the FFT with
 android audio recorder.
 https://github.com/ddf/Minim/tree/master/src/ddf/minim/analysis
 
 More info:
 
 setting up android & processing:
 http://wiki.processing.org/w/Android
 http://developer.android.com/sdk/index.html#ExistingIDE
 http://developer.android.com/sdk/installing/index.html
 
 http://forum.processing.org/topic/microphone-on-android
 
 android specific
 http://stackoverflow.com/questions/5774104/android-audio-fft-to-retrieve-specific-frequency-magnitude-using-audiorecord
 http://www.androidcookbook.info/android-media/visualizing-frequencies.html
 
 fft generic:
 http://stackoverflow.com/questions/4633203/extracting-precise-frequencies-from-fft-bins-using-phase-change-between-frames
 http://www.dsprelated.com/showmessage/18389/1.php
 dsp.stackexchange.com/questions/2121/i-need-advice-about-how-to-make-an-audio-frequency-analyzer
 https://en.wikipedia.org/wiki/Log-log_plot
 
 */






//*********************************************************************
// keep activity alive code import:
//*********************************************************************


 


int       RECORDER_SAMPLERATE = 44100;
int       MAX_FREQ = RECORDER_SAMPLERATE/2;
final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
final int PEAK_THRESH = 5;

short[]     buffer           = null;
int         bufferReadResult = 0;
AudioRecord audioRecord      = null;

boolean     aRecStarted      = false;
int         bufferSize       = 2048;
int         minBufferSize    = 0;
float       volume           = 0;
FFT         fft              = null;
float[]     fftRealArray     = null;
int         mainFreq         = 0;

float       drawScaleH       = 4.5f; // TODO: calculate the drawing scales
float       drawScaleW       = 1.0f; // TODO: calculate the drawing scales
int         drawStepW        = 2;   // display only every Nth freq value
float       maxFreqToDraw    = 2500; // max frequency to represent graphically
int         drawBaseLine     = 0;



//beat detect: /////////////////////////////////////////////////////////////////////////////////////////////////////////////
BeatDetect beat;
float eRadius;
int[] avgTimes;
int previousMillis;
int timeVar;
int average;
int bpm;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//UDP code:///////////////////////////////////////////////////


UDP udp; // define the UDP object
//multicast - or broadcast:
//String ip = "255.255.255.255";
//String ip = "224.0.0.1"; // the remote IP address
//int UDPport = 5656; // the destination port
String ip2 = "192.168.1.78"; // change this with android saved settings for testing
    String ip = "192.168.1.1"; // change in constructor now
    String ip3 = "192.168.1.79"; // the remote IP address (Accelerometer)

//String ip = "192.168.1.77"; // the remote IP address (Huawei HG532C)
//String ip2 = "192.168.1.78"; // the remote IP address (Huawei HG532C)

int UDPport = 2390; // the destination port
//////////////////////////////////////////////////////////////


PGraphics pg;

int testCol;
int count;
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
 
  orientation(PORTRAIT);
  testCol = color(0, 0, 100);
  //////////////////////////////UDP Code://////////////////////
  udp = new UDP( this, UDPport ); // create a new datagram connection on port 8888
  udp.log( true ); // <\u2013 printout the connection activity
  udp.listen( true ); // and wait for incoming message
  //////////////////////////////////////////////////////////////////////
  pg = createGraphics(36, 36);
  pg.beginDraw();
  pg.background(0);
  pg.endDraw();

  drawBaseLine = displayHeight-150;
  minBufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING);
  // if we are working with the android emulator, getMinBufferSize() does not work
  // and the only samplig rate we can use is 8000Hz
  if (minBufferSize == AudioRecord.ERROR_BAD_VALUE) {
    RECORDER_SAMPLERATE = 8000; // forced by the android emulator
    MAX_FREQ = RECORDER_SAMPLERATE/2;
    bufferSize =  getHigherP2(RECORDER_SAMPLERATE);// buffer size must be power of 2!!!
    // the buffer size determines the analysis frequency at: RECORDER_SAMPLERATE/bufferSize
    // this might make trouble if there is not enough computation power to record and analyze
    // a frequency. In the other hand, if the buffer size is too small AudioRecord will not initialize
  } else bufferSize = minBufferSize;

  buffer = new short[bufferSize];
  // use the mic with Auto Gain Control turned off!
  audioRecord = new AudioRecord( MediaRecorder.AudioSource.VOICE_RECOGNITION, RECORDER_SAMPLERATE, 
  RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING, bufferSize);

  //audioRecord = new AudioRecord( MediaRecorder.AudioSource.MIC, RECORDER_SAMPLERATE,
  //                              RECORDER_CHANNELS,RECORDER_AUDIO_ENCODING, bufferSize);
  if ((audioRecord != null) && (audioRecord.getState() == AudioRecord.STATE_INITIALIZED)) {
    try {
      // this throws an exception with some combinations
      // of RECORDER_SAMPLERATE and bufferSize 
      audioRecord.startRecording(); 
      aRecStarted = true;
    }
    catch (Exception e) {
      aRecStarted = false;
    }

    if (aRecStarted) {
      bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
      // compute nearest higher power of two
      bufferReadResult = getHigherP2(bufferReadResult);
      fft = new FFT(bufferReadResult, RECORDER_SAMPLERATE);
      fftRealArray = new float[bufferReadResult]; 
      drawScaleW = drawScaleW*(float)displayWidth/(float)fft.freqToIndex(maxFreqToDraw);
    }
  }
  fill(0);
  noStroke();
  //beat detect: 
  beat = new BeatDetect();  

  ellipseMode(CENTER_RADIUS);
  eRadius = 20;

  //BeatDetect code:////////////////////////////////////////////////////////////////////////////////////////
  avgTimes = new int[16];
  //////////////////////////////////////////////////////////////////////////////////////////////////////////
}

    @Override
public void draw() {
  background(0); 
//  fill(0); 
//  noStroke();
//  if (aRecStarted) {
//    bufferReadResult = audioRecord.read(buffer, 0, bufferSize); 
//    for (int i = 0; i < bufferReadResult; i++) {
//      fftRealArray[i] = (float) buffer[i] / Short.MAX_VALUE;// 32768.0;
//    }
//     // zero out first point (not touched by odd-length window)
//    fftRealArray[0] = 0;
//    pg.beginDraw();
//    pg.background(0);
//////////////////////////////////Fourier Transform:              ///////////////////////////////////////////////////////////////////
//fft.forward(fftRealArray);
////pg.stroke(255);
////pg.fill(255);
//for (int i = 0; i < fft.specSize (); i++) {
//  pg.stroke(random(255), random(255), random(255)); 
//    // draw the line for frequency band i, scaling it by 4 so we can see it a bit better
//    //pg.line(i, pg.height, i, pg.height - fft.getBand(i) * 4);
//    pg.line( pg.width - fft.getBand(i) * 4, i, pg.width, i); //add colours
//    //pg.line( 18-((lastVal)%18), (prev_i)%36, 18+((lastVal)%18), (prev_i)%36);
//  }
//
//
//    ////////////////////////////////////////////BeatDetect:///////////////////////////////////////////////////////////////////////
// /*   
//    beat.detect(fftRealArray);
//    if ( beat.isOnset() ) { //really need sensitivity slider here...
//      eRadius = 30;
////change colour every 4 beats? ->
//      count++;
//      if(count > 4 && count < 9){ //change every 4 beats more natural...
//      testCol = color(100, 0, 0);
//      }
//      if(count == 9){
//        count = 0; 
//        testCol = color(0, 100, 0);
//      }
////end colour change every 4 beats      
//      pg.fill(testCol);
//      pg.ellipse(pg.width/2, pg.height/2, eRadius, eRadius); //could change picture here instead
//    } else {
//      pg.fill(testCol);
//      pg.ellipse(pg.width/2, pg.height/2, eRadius, eRadius);
//    }
//    */
//    pg.endDraw();
//    stroke(255);
//    sendPGraphicsToPoi(pg, 0);
//    image(pg, width/2, height/2, 200, 200);
//    //text(bpm, width/2, height/2);
//    ////////////////////////////////////////////////////////end beat detect//////////////////////////////////////
//  }

/////////////////////////////////////////////////////////////////////////////////From WirlessSmartPoi_Serial_Send
if (aRecStarted) {
    bufferReadResult = audioRecord.read(buffer, 0, bufferSize); 
    // After we read the data from the AudioRecord object, we loop through
    // and translate it from short values to double values. We can't do this
    // directly by casting, as the values expected should be between -1.0 and 1.0
    // rather than the full range. Dividing the short by 32768.0 will do that,
    // as that value is the maximum value of short.
    volume = 0;
    for (int i = 0; i < bufferReadResult; i++) {
      fftRealArray[i] = (float) buffer[i] / Short.MAX_VALUE;// 32768.0;
      volume += Math.abs(fftRealArray[i]);
    }
    volume = (float)Math.log10(volume/bufferReadResult);

    // apply windowing
    for (int i = 0; i < bufferReadResult/2; ++i) {
      // Calculate & apply window symmetrically around center point
      // Hanning (raised cosine) window
      float winval = (float)(0.5f+0.5f*Math.cos(Math.PI*(float)i/(float)(bufferReadResult/2)));
      if (i > bufferReadResult/2)  winval = 0;
      fftRealArray[bufferReadResult/2 + i] *= winval;
      fftRealArray[bufferReadResult/2 - i] *= winval;
    }
    // zero out first point (not touched by odd-length window)
    fftRealArray[0] = 0;
    fft.forward(fftRealArray);

    //BeatDetect:///////////////////////////////////////////////////////////////////////

    pg.beginDraw();
    //pg.loadPixels();
    pg.background(0);
    /*
    beat.detect(fftRealArray);
    float a = map(eRadius, 20, 80, 60, 255);
    //fill(60, 255, 0, a);
    if ( beat.isOnset() ) {

      timeVar++;
      avgTimes[timeVar] = millis()-previousMillis;
      previousMillis = millis(); 
      if (timeVar==avgTimes.length-1) {
        timeVar = 0;
      } 
      average = 0;
      for ( int i = 0; i < avgTimes.length; ++i )
      {
        average += avgTimes[i];
      }
      average /= avgTimes.length;
      bpm = int(60000/average);

      eRadius = 80;
      //ellipse(width/2, height/2, eRadius, eRadius);
      eRadius *= 0.95;
      if ( eRadius < 20 ) eRadius = 20;
      //println(beat.average());
    }
    stroke(255);
*/
//    text(bpm + " bpm", width/2, (height/4)*3);
    //pg.fill(bpm, 255-bpm, 0);
    //pg.ellipse(pg.width/2, pg.height/2, bpm%36, bpm%36);

    //////////////////////////////////////////////end beat detect//////////////////////////////////////

/*

    //
    fill(255);
    stroke(100);
    pushMatrix();
    rotate(radians(90));
    translate(drawBaseLine-3, 0);
    textAlign(LEFT, CENTER);
    for (float freq = RECORDER_SAMPLERATE/2-1; freq > 0.0; freq -= 150.0) {
      int y = -(int)(fft.freqToIndex(freq)*drawScaleW); // which bin holds this frequency?
      //line(-displayHeight,y,0,y); // add tick mark
      //text(Math.round(freq)+" Hz", 10, y); // add text label
    }
    popMatrix();
    noStroke();
*/
    //pg.beginDraw();
    //pg.background(0);

    float lastVal = 0;
    float val = 0;
    float maxVal = 0; // index of the bin with highest value
    int maxValIndex = 0; // index of the bin with highest value
    for (int i = 0; i < fft.specSize (); i++) {
      val += fft.getBand(i);
      if (i % drawStepW == 0) {
        val /= drawStepW; // average volume value
        int prev_i = i-drawStepW;
        stroke(255);
        //pg.stroke(0, bpm, 255-bpm);
        // draw the line for frequency band i, scaling it up a bit so we can see it
        //line( prev_i*drawScaleW, drawBaseLine, prev_i*drawScaleW, drawBaseLine - lastVal*drawScaleH );
        //pg.line( prev_i*drawScaleW, drawBaseLine, prev_i*drawScaleW, drawBaseLine - lastVal*drawScaleH );
        int colour7 = color(100, 100, 0);
        //change colour here with colour wheels
        
        pg.strokeWeight(1);
        float vvv = (lastVal)%36;
//        if (!randomColour7) {
          if (vvv<5) {
            pg.stroke(0, random(255), 0);
          }
          if (vvv>=5 & vvv<10) {
            pg.stroke(0, random(255), random(255));
          }
          if (vvv>=10 & vvv<15) {
            pg.stroke(random(255), 0, 0);
          }
          if (vvv>=15 & vvv<20) {
            pg.stroke(random(255), random(255), 0);
          }
          if (vvv>=20 & vvv<30) {
            pg.stroke(random(255), 0, random(255));
          }
          if (vvv>=30) {
            pg.stroke(0, 0, random(255));
          }
//        } else {
//          if (vvv<5) {
//            pg.stroke(0, myKnobGreen.getValue(), 0);
//          }
//          if (vvv>=5 & vvv<10) {
//            pg.stroke(0, myKnobGreen.getValue(), myKnobBlue.getValue());
//          }
//          if (vvv>=10 & vvv<15) {
//            pg.stroke(myKnobRed.getValue(), 0, 0);
//          }
//          if (vvv>=15 & vvv<20) {
//            pg.stroke(myKnobRed.getValue(), 255-myKnobGreen.getValue(), 0);
//          }
//          if (vvv>=20 & vvv<30) {
//            pg.stroke(myKnobRed.getValue(), 0, 255-myKnobBlue.getValue());
//          }
//          if (vvv>=30) {
//            pg.stroke(0, 0, 255-myKnobBlue.getValue());
//          }
//        }

        //pg.rect(((prev_i*drawScaleW)-(prev_i*drawScaleW))%36, (lastVal*drawScaleH)%36, (lastVal*drawScaleH)%36, 2);
        //pg.line( (prev_i)%36, (0)%36, (prev_i)%36, (lastVal)%36 );
        pg.line( 18-((lastVal)%18), (prev_i)%36, 18+((lastVal)%18), (prev_i)%36);
        if (val-lastVal > PEAK_THRESH) {
          stroke(255, 0, 0);
          fill(255, 128, 128);
          //ellipse(i*drawScaleW, drawBaseLine - val*drawScaleH, 20,20);
          //pg.ellipse(i*drawScaleW, drawBaseLine - val*drawScaleH, 20,20);
          stroke(255);
          fill(255);
          if (val > maxVal) {
            maxVal = val;
            maxValIndex = i;
          }
        } 
        //pg.stroke(0, 255-bpm, bpm);
        pg.stroke(0, random(200), random(100));
        //line( prev_i*drawScaleW, drawBaseLine - lastVal*drawScaleH, i*drawScaleW, drawBaseLine - val*drawScaleH );
        //pg.line( (prev_i)%36, (lastVal)%36, (i)%36, (val)%36 );
//        pg.fill(0, bpm, 255-bpm);
        pg.fill(0, random(200), random(100));
        int j = fft.specSize()-i;
        int prev_j = j+1;
        //pg.rect(((j*drawScaleW)-(prev_j*drawScaleW))%36, ((val*drawScaleH)-(lastVal*drawScaleH))%36, ((val*drawScaleH)-(lastVal*drawScaleH))%36, 2); 
        lastVal = val;
        val = 0;
      }
    }
    if (maxValIndex-drawStepW > 0) {
      fill(255, 0, 0);
      //ellipse(maxValIndex*drawScaleW, drawBaseLine - maxVal*drawScaleH, 20,20);
      //pg.ellipse(maxValIndex*drawScaleW, drawBaseLine - maxVal*drawScaleH, 20,20);
      //           fill(0,0,255);
      //           text( " " + fft.indexToFreq(maxValIndex-drawStepW/2)+"Hz",
      //                25+maxValIndex*drawScaleW, drawBaseLine - maxVal*drawScaleH);
    }
    //pg.updatePixels();
    //add black line at end://////////////////////////////////////////////////////////////////
    pg.strokeWeight(1);
    pg.stroke(0);
    pg.line(0, 35, 35, 35);
    //////////////////////////////////////////////////////////////////////////////////////////
    pg.endDraw();
    text("Frame Rate: " + frameRate, 10, 400);  //test
//    image(pg, 0, 0, 360, 360); //for testing only 
    //fill(255); 
    //        pushMatrix();
    //        translate(displayWidth/2,drawBaseLine);
    //        text("buffer readed: " + bufferReadResult, 20, 80);
    //        text("fft spec size: " + fft.specSize(), 20, 100);
    //        text("volume: " + volume, 20, 120);  
    //        popMatrix();
  } else {
    fill(255, 0, 0);
    text("AUDIO RECORD NOT INITIALIZED!!!", 100, height/2);
  }  
  //fill(255); 
  //pushMatrix();
  //translate(0,drawBaseLine);
  //text("sample rate: " + RECORDER_SAMPLERATE + " Hz", 20, 80);   
  //text("displaying freq: 0 Hz  to  "+maxFreqToDraw+" Hz", 20, 100);   
  //text("buffer size: " + bufferSize, 20, 120);   
  //popMatrix();
  ///////////////////////////////////////////Send Pixels///////////////////////////////////////////////////////
  if (!testing) {
    sendPGraphicsToPoi(pg, 0); // send to POI -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> ->
//      sendPGraphicsToPoi(pg, 0);
}
//  buttonRandomColour.display();
//  buttonSwitchScreen.display();
}


public void stop() {
  audioRecord.stop();
  audioRecord.release();
}

// compute nearest higher power of two
// see: graphics.stanford.edu/~seander/bithacks.html
public int getHigherP2(int val)
{
  val--;
  val |= val >> 1;
  val |= val >> 2;
  val |= val >> 8;
  val |= val >> 16;
  val++;
  return(val);
}

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
float BRT = 1.0f;
int numPixels = 72;
public void sendPGraphicsToPoi(PGraphics pgSend, int sendOpt) {
  int pixelCounter = 0;
//  byte[] message = new byte[36];
  byte[] bigpx = new byte[72];
  for (int a = 0; a < pgSend.width*pgSend.height; a++) {

    if (pixelCounter == 0) {
      //port.write(byte(startByte));
    }
//need something like: if(red < minimum red) red = minimum red    
////////////////////////////////////////////////////////dimmer code:///////////////////////////////////////////////////////////////////    
    //float dimmerR = red(pgSend.pixels[a])-(red(pgSend.pixels[a])*brightnessDown);
float dimmerR = (pgSend.pixels[a] >> 16 & 0xFF)-((pgSend.pixels[a] >> 16 & 0xFF)*BRT);
//float dimmerG = green(pgSend.pixels[a])-(green(pgSend.pixels[a])*brightnessDown);
float dimmerG = (pgSend.pixels[a] >> 8 & 0xFF)-((pgSend.pixels[a] >> 8 & 0xFF)*BRT);
//float dimmerB = blue(pgSend.pixels[a])-(blue(pgSend.pixels[a])*brightnessDown);
float dimmerB = (pgSend.pixels[a] & 0xFF)-((pgSend.pixels[a] & 0xFF)*BRT);
//r = (int) red(pgSend.pixels[a]) - (int) dimmerR;
r = (int) pgSend.pixels[a] >> 16 & 0xFF - (int) dimmerR;
//g = (int) green(pgSend.pixels[a]) - (int) dimmerG;
g = (int) pgSend.pixels[a] >> 8 & 0xFF - (int) dimmerG;
//b = (int) blue(pgSend.pixels[a]) - (int) dimmerB;
b = (int) pgSend.pixels[a] & 0xFF - (int) dimmerB; 
    //port.write(pixelConverter(r, g , b)+127);
//////////////////////////////////////////////////////////////////////////////////////////////////    
//    r = (int) red(pgSend.pixels[a]);
//    g = (int) green(pgSend.pixels[a]);
//    b = (int) blue(pgSend.pixels[a]); 
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
//    message[pixelCounter] = Y;
        bigpx[pixelCounter] = Y;

    //need to pre-load pic into array or it slows down serial!
    //delay(1);
    pixelCounter++;
/*
    if (pixelCounter == pgSend.width) {
      if (!testing) {
        udp.send(message, ip, UDPport); //disable for testing
        udp.send(message, ip2, UDPport); //disable for testing
        if(udp.isBroadcast()){
          //println("sent to: " + ip + UDPport);
        }
          
       
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
}
