package za.tomjuggler.processingdemo.sketch;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import apwidgets.*;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.inputmethod.EditorInfo; 
import android.view.inputmethod.InputMethodManager; 
import android.content.Context; 
import android.content.BroadcastReceiver; 
import android.content.IntentFilter; 
import android.net.wifi.WifiManager; 
import android.net.wifi.WifiConfiguration; 
import java.io.*; 
import android.view.WindowManager; 
import android.view.View; 
import android.os.Bundle; 
import java.net.*; 
import java.io.*; 
import java.lang.reflect.Method; 
import java.lang.reflect.InvocationTargetException; 
import java.util.Date; 
import java.text.SimpleDateFormat; 
import processing.core.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class textOnly extends PApplet {


//**************************************************************
//apwidgets code:****************************************************
//apwidget library from: https://github.com/MeULEDs/apwidgets






APWidgetContainer widgetContainer; 
APEditText textField;

boolean isVisible = false;
//************************************************************************





//*************************************************************************
//UDP code:///////////////////////////////////////////////////
//import hypermedia.net.*;

UDP udp; // define the UDP object
//multicast - or broadcast:
//String ip = "255.255.255.255";
//String ip = "224.0.0.1"; // the remote IP address
//int UDPport = 5656; // the destination port
String ip = "192.168.1.1"; // the remote IP address (Captive)

//String ip = "192.168.8.78"; // the remote IP address (Telkom LTE)
String ip2 = "192.168.1.78"; // the remote IP address (Telkom LTE)

//String ip = "192.168.1.77"; // the remote IP address (Huawei HG532C)
//String ip2 = "192.168.1.78"; // the remote IP address (Huawei HG532C)

int UDPport = 2390; // the destination port
//////////////////////////////////////////////////////////////

boolean testing = false;

int   r, g, b;
PGraphics pg;

//import controlP5.*;
//ControlP5 cp5;
//rgb control knobs:
int myColorBackground = color(0, 0, 0);
int colourGreen = color(0, 255, 0);
int colourRed = color(255, 0, 0);
int colourBlue = color(0, 0, 255);
int colourCyan = color(0, 255, 0);
int colourMagenta = color(255, 0, 0);
int colourYellow = color(0, 0, 255);
int redValue = 0;
int greenValue = 0;
int blueValue = 0;
int colourOption = 0;
int maxColourOption = 6;
//Knob myKnobBlue;
//Knob myKnobRed;
//Knob myKnobGreen;
//button
PImage sunflower;
Button buttonSwitchScreen;
boolean screenBlank = false;

public void setup() {
  
  orientation(LANDSCAPE);
  
  //////////////////////////////UDP Code://////////////////////
  udp = new UDP( this, UDPport  ); // create a new datagram connection on port 2390 on ip
  //udp.setReuseAddress(true);
  udp.log( true ); // <\u2013 printout the connection activity
  udp.listen( true ); // and wait for incoming message
  /////////////////////////////////////////////////////////////
  
  //apwidget initilaize://////////////////////////////////////
  widgetContainer = new APWidgetContainer(this); //create new container for widgets
  textField = new APEditText( 0, 50, // top left corner at (0, 50)
  width-50, 100 );     // width = width/2, height = 100
  widgetContainer.addWidget(textField); //place textField in container
  //widgetContainer.hide();
  textField.setTextSize(30);
  textField.setText("Smart Poi");
  widgetContainer.show();
  /////////////////////////////////////////////////////////////
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
  

 

  pg = createGraphics(36, 36);

  //textPGraphics setup://////////////////////////
  pg2 = createGraphics(72, 36);
//  pfont = loadFont("18HolesBRK-20.vlw");
//  fontCol1 = color(0, 0, 255);
//  fontCol2 = color(255, 255, 0);
  ///////////////////////////////////////////////
//   cp5 = new ControlP5(this);
//   //knobs://////////////////////////////////////////////////
//  myKnobRed = cp5.addKnob("RED")
//    .setRange(0, 255)
//      .setValue(50)
//        .setPosition(0, height/6*4)
//          .setRadius(width/12+width/24)
//            .setColorBackground(colourRed)
//              .setDragDirection(Knob.HORIZONTAL)
//                .setVisible(true);
//  ;
//  myKnobGreen = cp5.addKnob("GREEN")
//    .setRange(0, 255)
//      .setValue(50)
//        .setPosition(width/6+width/12, height/6*4)
//          .setRadius(width/12+width/24)
//            .setColorBackground(colourGreen)
//              .setDragDirection(Knob.HORIZONTAL)
//                .setVisible(true);
//  ;
//  myKnobBlue = cp5.addKnob("BLUE")
//    .setRange(0, 255)
//      .setValue(50)
//        .setPosition(width/6*2+width/6, height/6*4)
//          .setRadius(width/12+width/24)
//            .setColorBackground(colourBlue)
//              .setDragDirection(Knob.HORIZONTAL)
//                .setVisible(true);
//  ; 
background(0);
 //super.start(); 
}

public void draw(){
  
//  if(!screenBlank){
  
  //showVirtualKeyboard();
  //using apwidgets example apWidgetsTextField
  textAlign(LEFT);
  textSize(30);
  
  //text(textField.getText(), 10, 200); //display stored text
//text(myKnobRed.getValue() + " " + myKnobGreen.getValue() + " " + myKnobBlue.getValue() , 10, 200); //display stored text
String[] qWord = splitTokens(textField.getText(), " "); //SPLIT AT SPACE
for(int a = 0; a<qWord.length; a++){
  int repeatNum = 10;
  for(int i = 0; i<repeatNum; i++){
  textPGraphics2(qWord[a]);
  textPGraphics2(" "); //space between words
  }
  background(4);
//  tint(0, 0, 255);
  //need a "display for x seconds option" here
  //text(qWord[a], 10, 200); //display stored text
  //delay(1000);
}
  //textPGraphics2(textField.getText());
//  tint(myKnobRed.getValue(), myKnobGreen.getValue(), myKnobBlue.getValue());
//  stroke(myKnobRed.getValue(), myKnobGreen.getValue(), myKnobBlue.getValue());
//  if(myKnobRed.isVisible()){ //tie background and my button to the Knob visibility
//  background(myKnobRed.getValue(), myKnobGreen.getValue(), myKnobBlue.getValue());
//    buttonSwitchScreen.display();
//  }
//  else{
//    background(4);
//  }
//  }
//  else{
//    myKnobRed.setVisible(false);
//     myKnobGreen.setVisible(false);
//      myKnobBlue.setVisible(false);
//      widgetContainer.hide();
//      textPGraphics2(textField.getText());
//    background(4);
//  }
  
  
  
          if(!screenBlank){
    buttonSwitchScreen.display(); //power saving button
    switch(colourOption){
            case 0: {
            fill(255, 0, 0);
            break;
            }
            case 1: {
            fill(0, 255, 0);
            break;
            }
            case 2: {
            fill(0, 0, 255);
            break;
            }
            case 3: {
            fill(255, 255, 0);
            break;
            }
            case 4: {
            fill(0, 255, 255);
            break;
            }
            case 5: {
            fill(255, 0, 255);
            break;
            }
            case 6: {
            fill(120, 120, 120);
            break;
            }
            default: {
            fill(20, 100, 100);
            break;    
            }        
          }
    rect(width/6*3, height/6*4, width/6, height/6); //colour indicator rect
  }
       
}

public void mousePressed(){
    
   if (buttonSwitchScreen.over() && screenBlank) {
    screenBlank = !screenBlank;
    widgetContainer.show();
//    myKnobRed.setVisible(true);
//     myKnobGreen.setVisible(true);
//      myKnobBlue.setVisible(true);
  }
  else if(buttonSwitchScreen.over() && !screenBlank){
     screenBlank = !screenBlank;
    widgetContainer.hide();
    background(4);
//    myKnobRed.setVisible(false);
//     myKnobGreen.setVisible(false);
//      myKnobBlue.setVisible(false);
  }
  else{
    if(!screenBlank){ //don't change colour in pocket!
    colourOption++;
      if(colourOption>maxColourOption){
       colourOption = 0; 
      }
    }
  }
  
}

public void keyPressed() {
  //below not working for some reason?
  if (key == CODED) {
    if (keyCode == MENU) {
      colourOption++;
      if(colourOption>maxColourOption){
       colourOption = 0; 
      }
//      myKnobRed.setVisible(true);
//     myKnobGreen.setVisible(true);
//      myKnobBlue.setVisible(true);
      //below not working...
/*
      myKnobRed.setVisible(!myKnobRed.isVisible());
      myKnobGreen.setVisible(!myKnobGreen.isVisible());
      myKnobBlue.setVisible(!myKnobBlue.isVisible());
//myKnobRed.setVisible(true);
//      myKnobGreen.setVisible(true);
//      myKnobBlue.setVisible(true);
      if(myKnobRed.isVisible()){
        hideVirtualKeyboard();
      }
      else{
        showVirtualKeyboard();
      }
      */
    }
  }
}

public void showVirtualKeyboard() {
  InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
  imm.showSoftInput(textField.getView(), InputMethodManager.SHOW_IMPLICIT);
}

public void hideVirtualKeyboard() {
  InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
  imm.hideSoftInputFromWindow(textField.getView().getWindowToken(), 0);
}

//*********************************************************************
// keep activity alive code import:
//*********************************************************************



 




//startup Android:////////////////////////////////////////////////////////////////////////////
public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);

  // fix so screen doesn't go to sleep when app is active
  getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

  
}
//end startup Android/////////////////////////////////////////////////////////////////////////

//The UDP
//public void onDestroy() {
//
//  super.onDestroy(); //call onDestroy on super class
//  udp.dispose();
//}
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

//void knob(int theValue) {
//  myColorBackground = color(theValue);
//  println("a knob event. setting variable to "+theValue);
//}
int sendOption = 0;
float brightnessDown = 1.00f; //tie this variable to a slider on Screen0

public void sendPGraphicsToPoi(PGraphics pgSend, int sendOpt) {
  int pixelCounter = 0;
  byte[] message = new byte[36];
  byte[] bigpx = new byte[72];
  for (int a = 0; a < pgSend.width*pgSend.height; a++) {

    if (pixelCounter == 0) {    
      //port.write(byte(startByte));
    }
//float dimmerR = red(pgSend.pixels[a])-(red(pgSend.pixels[a])*brightnessDown);
float dimmerR = (pgSend.pixels[a] >> 16 & 0xFF)-((pgSend.pixels[a] >> 16 & 0xFF)*brightnessDown);
//float dimmerG = green(pgSend.pixels[a])-(green(pgSend.pixels[a])*brightnessDown);
float dimmerG = (pgSend.pixels[a] >> 8 & 0xFF)-((pgSend.pixels[a] >> 8 & 0xFF)*brightnessDown);
//float dimmerB = blue(pgSend.pixels[a])-(blue(pgSend.pixels[a])*brightnessDown);
float dimmerB = (pgSend.pixels[a] & 0xFF)-((pgSend.pixels[a] & 0xFF)*brightnessDown);

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
    if (pixelCounter == pgSend.width) {
//      if (pixelCounter == pgSend.width*2) { //72px
      if(!testing){
      udp.send(message, ip, UDPport); //36px
      udp.send(message, ip2, UDPport); //36px
      //println("sent1");
//udp.send(bigpx, ip, UDPport); //72px
//      udp.send(bigpx, ip2, UDPport); //72px
      }
      pixelCounter = 0;
    }

    //delay(100);
  }
}


public byte pixelConverter(int red, int green, int blue) {
  byte encodedRGB;
  //int   r, g, b;
  encodedRGB = PApplet.parseByte((red & 0xE0) | ((green & 0xE0)>>3) | (blue >> 6));
  return encodedRGB;
}

PGraphics pg2;
int fontCol1;
int fontCol2;
String words = "BIG TOP ENTERTAINMENT";
PFont pfont;
int listPosition = 0;
int numWordRepeats = 400; //many times for testing only - estimate need 4-12 max but real world tests will tell...
int textSize = 15;

String spaceA = " ";

String letters1Path = "/sdcard/Pictures/SmartPoi/WirelessSmartPoi/Letters1/"; // path for Android

//using this now: 

public void textPGraphics2(String words) {
  for (int i = 0; i < words.length (); i++) {
    if (words.charAt(i) == spaceA.charAt(0)) { //space
      String spaceFileName = letters1Path + "space" + ".jpg";
      PImage letter = loadImage(spaceFileName);
      pg = createGraphics(letter.width, letter.height);
      pg.beginDraw();
      pg.image(letter, 0, 0);
//      pg.tint(0, 0, 255);
      pg.endDraw();
      if (!testing) {
        sendPGraphicsToPoi(pg, 0); // send to POI -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> ->
        //println("pg.width: " + pg.width + "PG height: " + pg.height); 
        //image(pg, 0, 100, 360, 360);
      }
    } else { //letter or number, else just put space instead of punctuation, what about line feeds?
      if (Character.isLetter(words.charAt(i)) || Character.isDigit(words.charAt(i))) { //trying isNumber??
        String letterFileName = letters1Path + words.charAt(i) + ".jpg";
        PImage letter2 = loadImage(letterFileName); //add error handling: if(loadImage(letterFileName).exists(){} or something like this...
        pg = createGraphics(letter2.width, letter2.height);
        pg.beginDraw();
//        pg.tint(myKnobRed.getValue(), myKnobGreen.getValue(), myKnobBlue.getValue());
//        pg.tint(25, 50, 200);
          
        //pg.tint(255, 0, 0);
        //pg.tint(random(80, 255),random(0, 160),random(80, 160)); //tint red for clearer letters!
        //pg.tint(255, 0, 0);
        switch(colourOption){
            case 0: {
            pg.tint(255, 0, 0);
            break;
            }
            case 1: {
            pg.tint(0, 255, 0);
            break;
            }
            case 2: {
            pg.tint(0, 0, 255);
            break;
            }
            case 3: {
            pg.tint(255, 255, 0);
            break;
            }
            case 4: {
            pg.tint(0, 255, 255);
            break;
            }
            case 5: {
            pg.tint(255, 0, 255);
            break;
            }
            case 6: {
            pg.tint(120, 120, 120);
            break;
            }
            default: {
            pg.tint(20, 100, 100);
            break;    
            }        
          }
        pg.image(letter2, 0, 0);
//        pg.tint(0, 0, 255);
        /*
        switch(colourOption){
            case 0: {
            pg.tint(255, 0, 0);
            break;
            }
            case 1: {
            pg.tint(0, 255, 0);
            break;
            }
            case 2: {
            pg.tint(0, 0, 255);
            break;
            }
            default: {
            pg.tint(20, 100, 100);
            break;    
            }        
          }
          */
        pg.endDraw();
        if (!testing) {
          sendPGraphicsToPoi(pg, 0); // send to POI -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> ->
          //println("pg.width: " + pg.width + "PG height: " + pg.height); 
          //image(pg, 0, 100, 360, 360);
        } else { //space for now instead of punctuation
          String spaceFileName = letters1Path + "space" + ".jpg";
          PImage letter3 = loadImage(spaceFileName);
          pg = createGraphics(letter3.width, letter3.height);
          pg.beginDraw();
          pg.image(letter3, 0, 0);
//          pg.tint(0, 0, 255);
          pg.endDraw();
          if (!testing) {
            sendPGraphicsToPoi(pg, 0); // send to POI -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> ->
            //println("pg.width: " + pg.width + "PG height: " + pg.height); 
            //image(pg, 0, 100, 360, 360);
          }
        }
      }
    }
  }
}

//use split(words) function to reroute words from here to textPGraphics2() !
//then can use the numWordRepeats variable as well, making it really cool...
public void textPGraphics(String words) {
  PFont pfont3 = createFont("Arial", textSize, true);
  if (testing) {
    numWordRepeats = 400;
  } else {
    numWordRepeats = 4;
  }
  String[] list = split(words, " ");
  if (list[listPosition].length() > 6) {
    pg2.beginDraw();
    pg2.background(0);
    pg2.textFont( pfont3, textSize );
    pg2.fill(fontCol1); //text colour
    pg2.textAlign( CENTER );
    println(list[listPosition]);
    pg2.text( list[listPosition], pg2.width/2, pg2.height/2 );
    cleanup(pg2);
    pg2.endDraw();

    //pg = createGraphics(pg2.width, pg2.height);
    pg.beginDraw();
    pg.translate(pg.width/2, pg.height/2);
    pg.rotate(90*TWO_PI/360);
    pg.translate(-pg.width/2, -pg.height/2);
    cleanup(pg);
    pg.image(pg2, 0, 0);
    pg.endDraw();
    for (int i = 0; i < numWordRepeats; i++) {
      image(pg, 0, 100, 360, 360);
      if (!testing) {
        sendPGraphicsToPoi(pg, 1); // send to POI -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> ->
      }
    }

    pg2.beginDraw();
    pg2.background(0);
    pg2.textFont( pfont3, textSize );
    pg2.textAlign( CENTER );
    println(list[listPosition]);
    pg2.text( list[listPosition], pg2.width/2, pg2.height/2 );
    cleanup(pg2);
    pg2.endDraw();

    //pg = createGraphics(pg2.width, pg2.height);
    pg.beginDraw();
    //finally: pg rotate  around center!
    pg.translate(pg.width/2, pg.height/2);
    pg.rotate(90*TWO_PI/360);
    pg.translate(-pg.width/2, -pg.height/2);
    //////////////////////////////////////////
    cleanup(pg);
    pg.image(pg2, -36, 0);
    pg.endDraw();
    for (int i = 0; i < numWordRepeats; i++) {
      image(pg, 0, 460, 360, 360);
      if (!testing) {
        sendPGraphicsToPoi(pg, 1); // send to POI -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> ->
      }
    }
  } else {
    background(0);
    //pg = createGraphics(pg2.width, pg2.height);   
    pg.beginDraw();
    pg.background(0);
    pg.fill(fontCol2);
    pg.textFont( pfont3, textSize );
    pg.textAlign( CENTER );
    println(list[listPosition]);
    pg.translate(pg.width/2, pg.height/2);
    pg.rotate(90*TWO_PI/360);
    pg.translate(-pg.width/2, -pg.height/2);
    pg.text( list[listPosition], pg.width/2, pg.height/2 );
    cleanup(pg);
    pg.endDraw();

    for (int i = 0; i < numWordRepeats; i++) {

      image(pg, 0, 100, 360, 360);
      if (!testing) {
        sendPGraphicsToPoi(pg, 1); // send to POI -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> -> ->
      }
    }
  } 
  listPosition++;
  if (listPosition == list.length) {
    listPosition = 0;
  } 

  //delay(500); //for testing
  //background(0);
}

public PGraphics cleanup(PGraphics pg) {
  pg.filter(THRESHOLD);
  return pg;
}


/*

 void textPGraphics(String displayText) {
 //if space go to next word:
 String[] word = split(displayText, ' ');
 for (int i = 0; i<word.length; i++) { //this method is too slow need to save words rather than 
 //background(100);
 delay(100);
 text(word[i], 10, (i*20)+200);
 //int pixelCounter = 0;
 //pg = createGraphics(36, 36); //should be in setup
 pg.beginDraw();
 pg.background(0);
 pg.stroke(255);
 float x = 10;
 float y = 18;
 pg.textAlign(CENTER, BOTTOM);
 pg.textSize(10); //need to tweak this...
 pg.pushMatrix();
 pg.translate(x, y);
 pg.rotate(HALF_PI);
 pg.text(word[i], 0, 10);
 pg.popMatrix();
 //pg.line(20, 20, mouseX, mouseY);
 
 pg.endDraw();
 
 image(pg, 9, (i*30));
 
 
 sendPGraphicsToPoi(pg, 1);
 
 }
 } 
 */


/**
 * (./) UDP.java v0.2 06/01/26
 * (by) Douglas Edric Stanley & Cousot St\u00e9phane
 * (cc) some right reserved
 *
 * Part of the Processing Libraries project, for the Atelier Hypermedia, art 
 * school of Aix-en-Provence, and for the Processing community of course.
 * - require Java version 1.4 or later -
 * -> http://hypermedia.loeil.org/processing/
 * -> http://www.processing.org/
 *
 * THIS LIBRARY IS RELEASED UNDER A CREATIVE COMMONS LICENSE BY.
 * -> http://creativecommons.org/licenses/by/2.5/
 */


//package hypermedia.net;










/** 
 * Create and manage unicast, broadcast or multicast socket to send and receive
 * datagram packets over the network.
 * <p>
 * The socket type is define at his initialyzation by the passed IP address. 
 * To reach a host (interface) within a network, you need to specified the kind 
 * of address:
 * <ul>
 * <li>An <b>unicast address</b> refer to a unique host within a subnet.</li>
 * <li>A <b>broadcast address</b> allow you to call every host within a subnet.
 * </li>
 * <li>A <b>multicast address</b> allows to call a specific group of hosts within 
 * the subnet. A multicast group is specified by a IP address in the range 
 * 224.0.0.0 (reserved, should not be used) to 
 * 239.255.255.255 inclusive, and by a standard UDP port number.
 * <br />
 * <small>notes: the complete reference of special multicast addresses should be
 * found in the latest available version of the "Assigned Numbers RFC"
 * </small></li>
 * </ul>
 * A packet sent to a unicast or broadcast address is only delivered to the 
 * host identified by that address. To the contrary, when packet is send to a 
 * multicast address, all interfaces identified by that address receive the data
 * .
 * <p>
 * To perform actions on receive and/or timeout events, you must implement 
 * specific method(s) in your code. This method will be automatically called
 * when the socket receive incoming data or a timeout event. By default, the
 * "receive handler" is typically <code>receive(byte[] data)</code> but you can
 * retrieve more informations about the datagram packet, see 
 * {@link UDP#setReceiveHandler(String name)} for more informations. In the same
 * logic, the default "timeout handler" is explicitly <code>timeout()</code>.
 * <p>
 * <small>
 * note: currently applets are not allowed to use multicast sockets
 * </small>
 *
 * @version 0.1
 * @author Cousot St\u00e9phane - stef@ubaa.net
 * @author Douglas Edric Stanley - http://www.abstractmachine.net/
 */
public class UDP implements Runnable {
  
  
  // the current unicast/multicast datagram socket
  DatagramSocket ucSocket    = null;
  MulticastSocket mcSocket  = null;
  
  boolean log      = false;  // enable/disable output log
  boolean listen    = false;  // true, if the socket waits for packets
  int timeout      = 0;    // reception timeout > 0=infinite timeout
  int size      = 65507;  // the socket buffer size in bytes
  InetAddress group  = null;    // the multicast's group address to join
  
  // the reception Thread > wait automatically for incoming datagram packets
  // without blocking the current Thread.
  Thread thread    = null;
  
  // the parent object (could be an application, a componant, etc...)
  Object owner    = null;
  
  // the default "receive handler" and "timeout handler" methods name.
  // these methods must be implemented (by the owner) to be called 
  // automatically when the socket receive incoming datas or a timeout event
  String receiveHandler    = "receive";
  String timeoutHandler    = "timeout";
  
  // the log "header" to be set for debugging. Because log is disable by 
  // default, this value is automatically replaced by the principal socket 
  // settings when a new instance of UDP is created.
  String header    = "";
  
  ///////////////////////////////// fields ///////////////////////////////
  
  /**
   * The default socket buffer length in bytes.
   */
  public static final int BUFFER_SIZE = 65507;
  
  
  ///////////////////////////// constructors ////////////////////////////
  
  /**
   * Create a new datagram socket and binds it to an available port and every 
   * address on the local host machine.
   *
   * @param owner  the target object to be call by the receive handler
   */
  public UDP( Object owner ) {
    this( owner, 0 );
  }
  
  /**
   * Create a new datagram socket and binds it to the specified port on the 
   * local host machine.
   * <p>
   * Pass <code>zero</code> as port number, will let the system choose an 
   * available port.
   *
   * @param owner  the target object to be call by the receive handler
   * @param port  local port to bind
   */
  public UDP( Object owner, int port ) {
    this( owner, port, null );
  }
  
  /**
   * Create a new datagram socket and binds it to the specified port on the  
   * specified local address or multicast group address.
   * <p>
   * Pass <code>zero</code> as port number, will let the system choose an 
   * available port. The absence of an address, explicitly <code>null</code> 
   * as IP address will assign the socket to the Unspecified Address (Also 
   * called anylocal or wildcard address). To set up the socket as multicast 
   * socket, pass the group address to be joined. If this address is not a 
   * valid multicast address, a broadcast socket will be created by default.
   *
   * @param owner  the target object to be call by the receive handler
   * @param port  local port to bind
   * @param ip  host address or group address
   */
  public UDP( Object owner, int port, String ip ) {
    
    this.owner = owner;
    
    // register this object to the PApplet, 
    // if it's used with Processing
    try {
      if ( owner instanceof PApplet ) ((PApplet)owner).registerMethod("dispose", this);
    }
    catch( NoClassDefFoundError e ) {;}
    
    // open a new socket to the specified port/address
    // and join the group if the multicast socket is required
    try {
      
      InetAddress addr = InetAddress.getByName(ip);
      InetAddress host = (ip==null) ? (InetAddress)null: addr;
      
      if ( !addr.isMulticastAddress() ) {
        ucSocket = new DatagramSocket( port, host );  // as broadcast
        ucSocket.setReuseAddress(true); //doesn't do anything...
        log( "bound socket to host:"+address()+", port: "+port() );
      }
      else {              
        mcSocket = new MulticastSocket( port );      // as multicast
        mcSocket.joinGroup( addr );
        this.group = addr;
        log( "bound multicast socket to host:"+address()+
           ", port: "+port()+", group:"+group );
      }
    }
    catch( IOException e ) { 
      // caught SocketException & UnknownHostException
      error( "Jesus Christ,  opening socket failed!"+
           "\n\t> address:"+ip+", port:"+port+" [group:"+group+"]"+
           "\n\t> "+e.getMessage()
        ); 
    }
    catch( IllegalArgumentException e ) { 
      error( "Fucken Hell, opening socket failed!"+
           "\n\t> bad arguments: "+e.getMessage()
           );
    }
    catch( SecurityException e ) {
      error( (isMulticast()?"could not joined the group":"warning")+
          "\n\t> "+e.getMessage()  );
    }
    
  }
  
  /////////////////////////////// methods ///////////////////////////////
  
  /** 
   * Close the socket. This method is automatically called by Processing when 
   * the PApplet shuts down.
   *
   * @see UDP#close()
   */
  public void dispose() {
    close();
  }
  
  /**
   * Close the actuel datagram socket and all associate resources.
   */
  public void close() {
    if ( isClosed() ) return;
    
    int port  = port();
    String ip  = address();
    
    // stop listening if needed
    //listen( false );
    
    // close the socket
    try {
      if ( isMulticast() ) {
        if ( group!=null ) {
          mcSocket.leaveGroup( group );
          log( "leave group < address:"+group+" >" );
        }
        mcSocket.close();
        mcSocket = null;
      }
      else {
        ucSocket.close();
        ucSocket = null;
      }
    }
    catch( IOException e ) {
      error( "Error while closing the socket!\n\t> " + e.getMessage() ); 
    }
    catch( SecurityException e ) {;}
    finally {
      log( "close socket < port:"+port+", address:"+ip+" >\n" );
    }
  }
  
  /**
   * Returns whether the current socket is closed or not.
   * @return boolean
   **/
  public boolean isClosed() {
    if ( isMulticast() ) return mcSocket==null ? true : mcSocket.isClosed();
    return ucSocket==null ? true : ucSocket.isClosed();
  }
  
  /**
   * Return the actual socket's local port, or -1 if the socket is closed.
   * @return int
   */
  public int port() {
    if ( isClosed() ) return -1;
    return isMulticast()? mcSocket.getLocalPort() : ucSocket.getLocalPort();
  }
  
  /**
   * Return the actual socket's local address, or <code>null</code> if the 
   * address correspond to any local address.
   *
   * @return String
   */
  public String address() {
    if ( isClosed() ) return null;
    
    InetAddress laddr = isMulticast() ? mcSocket.getLocalAddress(): 
                      ucSocket.getLocalAddress();
    return laddr.isAnyLocalAddress() ? null : laddr.getHostAddress();
  }
  
  /**
   * Send message to the current socket. Explicitly, send message to the 
   * multicast group/port or to itself.
   *
   * @param message  the message to be send
   *
   * @see  UDP#send(String message, String ip)
   * @see  UDP#send(String message, String ip, int port)
   *
   * @return boolean
   */
  public boolean send( String message ) {
    return send( message.getBytes() );
  }
  
  /**
   * Send data to the current socket. Explicitly, send data to the multicast 
   * group/port or to itself.
   *
   * @param buffer  data to be send
   *
   * @see  UDP#send(byte[] data, String ip)
   * @see  UDP#send(byte[] data, String ip, int port)
   *
   * @return boolean
   */
  public boolean send( byte[] buffer ) {
    // probably if the group could not be joined for a security reason
    if ( isMulticast() && group==null ) return false;
    
    String ip = isMulticast() ? group.getHostAddress() : address();
    return send( buffer, ip, port() );
  }
  
  /**
   * Send message to the requested IP address, to the current socket port.
   *
   * @param message  the message to be send
   * @param ip    the destination host's IP address
   *
   * @see  UDP#send(String message)
   * @see  UDP#send(String message, String ip, int port)
   *
   * @return boolean
   */
  public boolean send( String message, String ip ) {
    return send( message.getBytes(), ip );
  }
  
  /**
   * Send data to the requested IP address, to the current socket port.
   *
   * @param buffer  data to be send
   * @param ip    the destination host's IP address
   *
   * @see  UDP#send(byte[] buffer)
   * @see  UDP#send(byte[] buffer, String ip, int port)
   *
   * @return boolean
   */
  public boolean send( byte[] buffer, String ip ) {
    return send( buffer, ip, port() );
  }
  
  /**
   * Send message to the requested IP address and port.
   * <p>
   * A <code>null</code> IP address will assign the packet address to the 
   * local host. Use this method to send message to another application by
   * passing <code>null</code> as the destination address.
   *
   * @param message  the message to be send
   * @param ip    the destination host's IP address
   * @param port    the destination host's port
   *
   * @see  UDP#send(String message)
   * @see  UDP#send(String message, String ip)
   *
   * @return boolean
   */
  public boolean send( String message, String ip, int port ) {
    return send( message.getBytes(), ip, port );
  }
  
  /**
   * Send data to the requested IP address and port.
   * <p>
   * A <code>null</code> IP address will assign the packet address to the 
   * local host. Use this method to send data to another application by
   * passing <code>null</code> as the destination address.
   *
   * @param buffer  data to be send
   * @param ip    the destination host's IP address
   * @param port    the destination host's port
   *
   * @see  UDP#send(byte[] buffer, String ip)
   * @see  UDP#send(byte[] buffer, String ip, int port)
   *
   * @return boolean
   */
  public boolean send( byte[] buffer, String ip, int port ) {
    
    boolean success  = false;
    DatagramPacket pa = null;
    
    try {
      
      pa  = new DatagramPacket( buffer, buffer.length, InetAddress.getByName(ip), port );
        
      // send
      if ( isMulticast() ) mcSocket.send( pa );
      else ucSocket.send( pa );
      
      success = true;
      log( "send packet -> address:"+pa.getAddress()+
         ", port:"+ pa.getPort() +
        ", length: "+ pa.getLength()
         );
    }
    catch( IOException e ) {
      error( "could not send message!"+
           "\t\n> port:"+port+
          ", ip:"+ip+
          ", buffer size: "+size+
          ", packet length: "+pa.getLength()+
           "\t\n> "+e.getMessage()
          );
    }
    finally{ return success; }
  }
  
  /**
   * Set the maximum size of the packet that can be sent or receive on the 
   * current socket. This value must be greater than 0, otherwise the buffer 
   * size is set to the his default value.
   * <p>
   * return <code>true</code> if the new buffer value have been correctly set, 
   * <code>false</code> otherwise.
   * <p>
   * <i>note : this method has no effect if the socket is listening. To define
   * a new buffer size, call this method before implementing a new buffer in 
   * memory. Explicitly before calling a receive methods.</i>
   *
   * @param size  the buffer size value in bytes or n<=0
   * @return boolean
   * @see UDP#getBuffer()
   */
  public boolean setBuffer( int size ) {
    boolean done = false;
    
    // do nothing if listening (block the thread otherwise)
    if ( isListening() ) return done;
    
    try {
      // set the SO_SNDBUF and SO_RCVBUF value
      if ( isMulticast() ) {
        mcSocket.setSendBufferSize( size>0 ? size : BUFFER_SIZE );
        mcSocket.setReceiveBufferSize( size>0 ? size : BUFFER_SIZE );
      }
      else {
        ucSocket.setSendBufferSize( size>0 ? size : BUFFER_SIZE );
        ucSocket.setReceiveBufferSize( size>0 ? size : BUFFER_SIZE );
      }
      // set the current buffer size
      this.size = size>0 ? size : BUFFER_SIZE;
      done = true;
    }
    catch( SocketException e ) {
      error( "could not set the buffer!"+
           "\n> "+e.getMessage()
          );
    }
    finally{ return done; }
  }
  
  /**
   * Return the actual socket buffer length
   * @return int
   * @see UDP#setBuffer(int size)
   */
  public int getBuffer() {
    return size;
  }
  
  /**
   * Returns whether the socket wait for incoming data or not.
   * @return boolean
   */
  public boolean isListening() {
    return listen;
  }
  
    
  /**
   * Start/stop waiting constantly for incoming data.
   *
   * @param on  the required listening status.
   *
   * @see UDP#listen()
   * @see UDP#listen(int millis)
   * @see UDP#setReceiveHandler(String name)
   */
  public void listen( boolean on ) {
    
    listen  = on;
    timeout  = 0;
    
    // start
    if ( on && thread==null && !isClosed() ) {
      thread = new Thread( this );
      thread.start();
    }
    // stop
    if ( !on && thread!=null  ) { 
      send( new byte[0] ); // unblock the thread with a dummy message
      thread.interrupt();
      thread = null;
    }
  }
  
  /**
   * Set the socket reception timeout and wait one time for incoming data. 
   * If the timeout period occured, the owner timeout() method is 
   * automatically called.
   *
   * @param millis  the required timeout value in milliseconds.
   *
   * @see UDP#listen()
   * @see UDP#listen(boolean on)
   */
  public void listen( int millis ) {
    if ( isClosed() ) return;
    
    listen  = true;
    timeout = millis;
    
    // unblock the thread with a dummy message, if already listening
    if ( thread!=null ) send( new byte[0] );
    if ( thread==null ) {
      thread = new Thread( this );
      thread.start();
    }
  }
  
  /**
   * Wait for incoming data, and call the appropriate handlers each time a 
   * message is received. If the owner's class own the appropriate target 
   * handler, this method send it the receive message as byte[], the sender 
   * IP address and port.
   * <p>
   * This method force the current <code>Thread</code> to be ceased for a 
   * temporary period. If you prefer listening without blocking the current 
   * thread, use the {@link UDP#listen(int millis)} or 
   * {@link UDP#listen(boolean on)} method instead.
   *
   * @see UDP#listen()
   * @see UDP#listen(boolean on)
   * @see UDP#setReceiveHandler(String name)
   */
  public void listen() {
    try {
      
      byte[] buffer    = new byte[ size ];
      DatagramPacket pa  = new DatagramPacket(buffer,buffer.length);
      
      // wait
      if ( isMulticast() ) {
        mcSocket.setSoTimeout( timeout );
        mcSocket.receive( pa );  // <-- block the Thread
      }
      else {
        ucSocket.setSoTimeout( timeout );
        ucSocket.receive( pa ); // <-- block
      }

      
      log( "receive packet <- from "+pa.getAddress()+
         ", port:"+ pa.getPort() +
         ", length: "+ pa.getLength()
         );
      
    
      // get the required data only (not all the buffer)
      // and send it to the appropriate target handler, if needed
      if ( pa.getLength()!=0 ) {
        
        byte[] data = new byte[ pa.getLength() ];
        System.arraycopy( pa.getData(), 0, data, 0, data.length );
        
        try { 
          // try with one argument first > byte[]
          callReceiveHandler( data );
        }
        catch( NoSuchMethodException e ) {
          // try with many argument > byte[], String, int
          callReceiveHandler( data, 
                    pa.getAddress().getHostAddress(), 
                    pa.getPort()
                    );
        }
      }
    }
    catch( NullPointerException e ) {
      // *socket=null from the close() method;
      listen = false;
      thread = null;
    }
    catch( IOException e ) {
      
      listen = false;
      thread = null;
      
      if ( e instanceof SocketTimeoutException ) callTimeoutHandler();
      else {
         // do not print "Socket closed" error 
        // if the method close() has been called
        if ( ucSocket!=null && mcSocket!=null )
          error( "listen failed!\n\t> "+e.getMessage() );
      }
    }
  }
  
  /**
   * Wait for incoming datagram packets. This method is called automaticlly,
   * you do not need to call it.
   */
  public void run() {
    while ( listen ) listen();
  }
  
  /**
   * Register the target's receive handler.
   * <p>
   * By default, this method name is "receive" with one argument 
   * representing the received data as <code>byte[]</code>. For more 
   * flexibility, you can change this method with another useful method by 
   * passing his name. You could get more informations by implementing two 
   * additional arguments, a <code>String</code> representing the sender IP 
   * address and a <code>int</code> representing the sender port :
   * <p><blockquote><pre>
   * void myCustomReceiveHandler(byte[] message, String ip, int port) {
   *  // do something here...
   * }
   * </blockquote></pre>
   *
   * @param name  the receive handler name
   * @see UDP#setTimeoutHandler(String name)
   */
  public void setReceiveHandler( String name ) {
    this.receiveHandler = name;
  }
  
  /**
   * Call the default receive target handler method.
   *
   * @param data  the data to be passed
   * @throws NoSuchMethodException
   */
  private void callReceiveHandler( byte[] data ) 
  throws NoSuchMethodException {
    
    Class[] types;    // arguments class types
    Object[] values;  // arguments values
    Method method;
    
    try {
      types  = new Class[]{ data.getClass() };
      values  = new Object[]{ data };
      method  = owner.getClass().getMethod(receiveHandler, types);
      method.invoke( owner, values );
    }
    catch( IllegalAccessException e )    { error(e.getMessage()); }
    catch( InvocationTargetException e )  { e.printStackTrace(); }
  }

  /**
   * Call the receive target handler implemented with the optional arguments.
   *
   * @param data    the data to be passed
   * @param ip    the IP adress to be passed
   * @param port    the port number to be passed
   */
  private void callReceiveHandler( byte[] data, String ip, int port ) {
    
    Class[] types;    // arguments class types
    Object[] values;  // arguments values
    Method method;
    
    try {
      types  = new Class[]{  data.getClass(),
                  ip.getClass(), 
                  Integer.TYPE
                };
      values  = new Object[]{ data, 
                  ip, 
                  new Integer(port)
                };
      method  = owner.getClass().getMethod(receiveHandler, types);
      method.invoke( owner, values );
    }
    catch( NoSuchMethodException e )    {;}
    catch( IllegalAccessException e )    { error(e.getMessage()); }
    catch( InvocationTargetException e )  { e.printStackTrace(); }
  }
  
  /**
   * Register the target's timeout handler. By default, this method name is 
   * "timeout" with no argument.
   *
   * @param name  the timeout handler name
   * @see UDP#setReceiveHandler(String name)
   */
  public void setTimeoutHandler( String name ) {
    this.timeoutHandler = name;
  }
  
  /**
   * Call the timeout target method when the socket received a timeout event.
   * The method name to be implemented in your code is <code>timeout()</code>.
   */
  private void callTimeoutHandler() {
    try {
      Method m = owner.getClass().getDeclaredMethod(timeoutHandler, null);
      m.invoke( owner, null );
    }
    catch( NoSuchMethodException e )    {;}
    catch( IllegalAccessException e )    { error(e.getMessage()); }
    catch( InvocationTargetException e )  { e.printStackTrace(); }
  }
  
  /**
   * Returns whether the opened datagram socket is a multicast socket or not.
   * @return boolean
   */
  public boolean isMulticast() {
    return ( mcSocket!=null );
  }
  
  /**
   * Returns whether the multicast socket is joined to a group address.
   * @return boolean
   */
  public boolean isJoined() {
    return ( group!=null );
  }
  
  /**
   * Returns whether the opened socket send broadcast message socket or not.
   * @return boolean
   */
  public boolean isBroadcast() {
    boolean result = false;
    try {
      result = (ucSocket==null) ? false : ucSocket.getBroadcast();
    }
    catch( SocketException e ) { error( e.getMessage() ); }
    finally { return result; }
  }
  
  /** 
   * Enables or disables the ability of the current process to send broadcast
   * messages.
   * @return boolean
   */
  public boolean broadcast( boolean on ) {
    boolean done = false;
    try {
      if ( ucSocket!=null ) {
        ucSocket.setBroadcast( on );
        done = isBroadcast();
      }
    }
    catch( SocketException e ) { error( e.getMessage() ); }
    finally { return done; }
  }
  
  /**
   * Enable or disable the multicast socket loopback mode. By default loopback
   * is enable.
   * <br>
   * Setting loopback to false means this multicast socket does not want to 
   * receive the data that it sends to the multicast group.
   *
   * @param on  local loopback of multicast datagrams
   */
  public void loopback( boolean on ) {
    try {
      if ( isMulticast() ) mcSocket.setLoopbackMode( !on );
    }
    catch( SocketException e ) { 
      error( "could not set the loopback mode!\n\t>"+e.getMessage() ); 
    }
  }
  
  /**
   * Returns whether the multicast socket loopback mode is enable or not.
   * @return boolean
   */
  public boolean isLoopback() {
    try {
      if ( isMulticast() && !isClosed() ) 
        return !mcSocket.getLoopbackMode();
    }
    catch( SocketException e ) { 
      error( "could not get the loopback mode!\n\t> "+e.getMessage() ); 
    }
    return false;
  }
  
  /**
   * Control the life-time of a datagram in the network for multicast packets 
   * in order to indicates the scope of the multicasts (ie how far the packet 
   * will travel).
   * <p>
   * The TTL value must be in range of 0 to 255 inclusive. The larger the 
   * number, the farther the multicast packets will travel (by convention):
   * <blockquote><pre>
   * 0  -> restricted to the same host
   * 1  -> restricted to the same subnet (default)
   * &lt;32  -> restricted to the same site
   * &lt;64  -> restricted to the same region
   * &lt;128  -> restricted to the same continent
   * &lt;255  -> no restriction
   * </blockquote></pre>
   * The default value is 1, meaning that the datagram will not go beyond the 
   * local subnet.
      * <p>
   * return <code>true</code> if no error occured.
   *
   * @param ttl the "Time to Live" value
   * @return boolean
   * @see UDP#getTimeToLive()
   */
  public boolean setTimeToLive( int ttl ) {
    try {
      if ( isMulticast() && !isClosed() ) mcSocket.setTimeToLive( ttl );
      return true;
    }
    catch( IOException e ) { 
      error( "setting the default \"Time to Live\" value failed!"+
           "\n\t> "+e.getMessage() ); 
    }
    catch( IllegalArgumentException e ) {
      error( "\"Time to Live\" value must be in the range of 0-255" ); 
    }
    return false;
  }
  
  /**
   * Return the "Time to Live" value or -1 if an error occurred (or if 
   * the current socket is not a multicast socket).
   *
   * @return int
   * @see UDP#setTimeToLive(int ttl)
   */
  public int getTimeToLive() {
    try {
      if ( isMulticast() && !isClosed() ) 
        return mcSocket.getTimeToLive();
    }
    catch( IOException e ) { 
      error( "could not retrieve the current time-to-live value!"+
           "\n\t> "+ e.getMessage() ); 
    }
    return -1;
  }
  
  /**
   * Enable or disable output process log.
   */
  public void log( boolean on ) {
    log = on;
  }
  
  /**
   * Output message to the standard output stream.
   * @param out  the output message
   */
  private void log( String out ) {
    
    Date date = new Date();
    
    // define the "header" to retrieve at least the principal socket
    // informations : the host/port where the socket is bound.
    if ( !log && header.equals("") )
      header = "-- UDP session started at "+date+" --\n-- "+out+" --\n";
    
    // print out
    if ( log ) {
      
      String pattern  = "yy-MM-dd HH:mm:ss.S Z";
      String sdf    = new SimpleDateFormat(pattern).format( date );
      System.out.println( header+"["+sdf+"] "+out );
      header = ""; // forget header
    }
  }
  
  /**
   * Output error messages to the standard error stream.
   * @param err the error string
   */
  private void error( String err ) {
    System.err.println( err );
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
