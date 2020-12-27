package za.tomjuggler.processingdemo.sketch;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable; 
import android.os.Bundle; 
import android.os.Environment; 
import android.support.v4.content.ContextCompat; 
import android.support.v7.app.AppCompatActivity; 
import android.util.Log; 
import android.view.View; 
import android.widget.Button; 
import android.widget.GridLayout; 
import android.widget.Toast; 
import java.io.File; 
import java.io.FilenameFilter; 
import android.Manifest; 
import android.app.Activity; 
import android.content.pm.PackageManager; 
import android.support.v4.app.ActivityCompat; 
import android.support.v4.content.ContextCompat; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class createFolderSaveFileInside extends PApplet {

//String dirname;

boolean exists = false;
String message = "SETUP COMPLETE";
String dirName1 = android.os.Environment.getExternalStorageDirectory().getPath() + "/Pictures/SmartPoi/WirelessSmartPoi";
String dirName2 = android.os.Environment.getExternalStorageDirectory().getPath() + "/Pictures/SmartPoi/WirelessSmartPoi72px";

//String dirName = "/sdcard/Pictures/SmartPoi/WirelessSmartPoi";
//String dirName = "/sdcard/testdel";
//PImage img;
//PGraphics pg;
//int numImgs = 1;
PicInstall a;
PicInstall b;
PicInstall c;
PicInstall d;
PicInstall e;
PicInstall f;
PicInstall g;
PicInstall h;
PicInstall i;
PicInstall j;
//72px:
PicInstall k;
PicInstall ll; // l conflicts with letters, so...
PicInstall m;
PicInstall n;
PicInstall o;
PicInstall p;
PicInstall q;
PicInstall r;
PicInstall s;
PicInstall t;



String lettersStr = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ ";

LettersInstall l;
LettersInstall l2;

















public void setup(){
   
  orientation(LANDSCAPE);
  //why does below not work? did it ever? Seems to work fine without this..
  //Util.requestPermission(this, Manifest.permission.RECORD_AUDIO);
  //Util.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
           
  a = new PicInstall("a", "0/", 12, dirName1);
  b = new PicInstall("b", "1/", 1, dirName1);
  a.putPicsInFolders();
  b.putPicsInFolders();
  c = new PicInstall("c", "2/", 4, dirName1);
  d = new PicInstall("d", "3/", 6, dirName1);
  c.putPicsInFolders();
  d.putPicsInFolders();
  e = new PicInstall("e", "4/", 2, dirName1);
  f = new PicInstall("f", "5/", 1, dirName1);
  e.putPicsInFolders();
  f.putPicsInFolders();
  g = new PicInstall("g", "6/", 30, dirName1);
  h = new PicInstall("h", "7/", 36, dirName1);
  g.putPicsInFolders();
  h.putPicsInFolders();
  i = new PicInstall("i", "8/", 26, dirName1);
  j = new PicInstall("j", "9/", 4, dirName1);
  i.putPicsInFolders();
  j.putPicsInFolders();
  
  l = new LettersInstall("l", "1/", 37, dirName1);
  l.putPicsInFolders();
  
  createCheckDirectory2(dirName1 + "/Timeline1/");
  createCheckDirectory2(dirName1 + "/Music1/");
  createCheckDirectory2(dirName1 + "/Sequence1/");
  createCheckDirectory2(dirName1 + "/Messages1/"); //binary
  
  //and 72px:
  k = new PicInstall("k", "0/", 1, dirName2);
  k.putPicsInFolders();
  ll = new PicInstall("ll", "1/", 1, dirName2);
  ll.putPicsInFolders();
  m = new PicInstall("m", "2/", 1, dirName2);
  m.putPicsInFolders();
  n = new PicInstall("n", "3/", 1, dirName2);
  n.putPicsInFolders();
  o = new PicInstall("o", "4/", 1, dirName2);
  o.putPicsInFolders();
  p = new PicInstall("p", "5/", 1, dirName2);
  p.putPicsInFolders();
  q = new PicInstall("q", "6/", 1, dirName2);
  q.putPicsInFolders();
  r = new PicInstall("r", "7/", 1, dirName2);
  r.putPicsInFolders();
  s = new PicInstall("s", "8/", 1, dirName2);
  s.putPicsInFolders();
  t = new PicInstall("t", "9/", 1, dirName2);
  t.putPicsInFolders();
  
  l2 = new LettersInstall("l", "1/", 37, dirName2);
  l2.putPicsInFolders();
  
  createCheckDirectory2(dirName2 + "/Timeline1/");
  createCheckDirectory2(dirName2 + "/Music1/");
  createCheckDirectory2(dirName2 + "/Sequence1/");
  createCheckDirectory2(dirName2 + "/Messages1"); //binary, for offline
    
}

public void draw(){
  background(0);
    textSize(50);
    text(message, 100, 100);
    
  
}

// Create Check Directory //////////////////////////////////////////
//from https://forum.processing.org/one/topic/how-do-i-write-data-to-the-phone-or-sd-card-memory.html  
//will create the directory if it doesn't already exist! nice.
public void createCheckDirectory2(String path) {
  try {
    //String dirName = "/storage/sdcard0/Pictures/SmartPoi/36px/"; //using fullPath
    File newFile = new File(path);
    newFile.mkdirs();
    if (newFile.exists()) {
      println("Directory Exists...");
      if (newFile.isDirectory()) {
        println("isDirectory = true...");
      } else println("isDirectory = false...");
    } else {
      println("Directory Doesn't Exist...");
    }
  }
  catch(Exception e) {
    println("Exception creating folder... "); 
    //e.printStackTrace();
  }
}  
//end Create Check Directory////////////////////////////////////
// =======================================================================
class LettersInstall {
  PImage img; //image to hold jpg
  PGraphics pg; //
  String picPrefix; 
  String dirAppendix;
  int numImgs;
  String dirName;
  
  
  // constructor
  LettersInstall (  String picPrefix_, String dirAppendix_, 
  int numImgs_, String dirName_) {
    picPrefix=picPrefix_;
    dirAppendix=dirAppendix_;
    numImgs=numImgs_;
    dirName=dirName_;
   } // constructor
 
//methods:

public void putPicsInFolders(){
  for(int i = 0; i < numImgs; i++){
    if(i==36){ //space
      int realPicNum = i+1;
  img = loadImage(picPrefix + realPicNum + ".jpg");
  //img = loadImage(picPrefix + i + ".jpg");
  // Create Directory and add add a pic from data folder
  String newDirName = dirName + "/" + "Letters" + dirAppendix;
  createCheckDirectorySavePicName(newDirName, img, "space");
    }
    else{
    int realPicNum = i+1;
  img = loadImage(picPrefix + realPicNum + ".jpg");
  //img = loadImage(picPrefix + i + ".jpg");
  // Create Directory and add add a pic from data folder
  String newDirName = dirName + "/" + "Letters" + dirAppendix;
  createCheckDirectorySavePic(newDirName, img, i);
   }
  }
}
// Create Check Directory //////////////////////////////////////////
//from https://forum.processing.org/one/topic/how-do-i-write-data-to-the-phone-or-sd-card-memory.html  
//will create the directory if it doesn't already exist! nice.
public void createCheckDirectorySavePic(String path, PImage pic, int atChar) {
  try {
    File newFile = new File(path);
    newFile.mkdirs();
    if (newFile.exists()) {
      println("Directory Exists...");
      if (newFile.isDirectory()) { //why are we checking this every time?
        println("isDirectory = true...");
        pg = createGraphics(pic.width,pic.height);
        pg.beginDraw();
        pg.image(pic, 0, 0);
        pg.endDraw();
        pg.save(path + lettersStr.charAt(atChar) + ".jpg");
        println("adding image" + lettersStr.charAt(atChar));
      } else println("isDirectory = false...");
    } else {
      println("Directory Doesn't Exist...");
    }
  }
  catch(Exception e) {
    println("Exception creating folder... "); 
    //e.printStackTrace();
  }
}  
//end Create Check Directory////////////////////////////////////

//////////////////////for space only!!!!//////////////////////////////////////////////////////////////////////////////
public void createCheckDirectorySavePicName(String path, PImage pic, String nameOfPic) {
  try {
    File newFile = new File(path);
    newFile.mkdirs();
    if (newFile.exists()) {
      println("Directory Exists...");
      if (newFile.isDirectory()) { //why are we checking this every time?
        println("isDirectory = true...");
        pg = createGraphics(pic.width,pic.height);
        pg.beginDraw();
        pg.image(pic, 0, 0);
        pg.endDraw();
        pg.save(path + nameOfPic + ".jpg");
        println("adding image" + nameOfPic);
      } else println("isDirectory = false...");
    } else {
      println("Directory Doesn't Exist...");
    }
  }
  catch(Exception e) {
    println("Exception creating folder... "); 
    //e.printStackTrace();
  }
}  
//end Create Check Directory////////////////////////////////////
} // class
// =======================================================================
class PicInstall {
  PImage img; //image to hold jpg
  PGraphics pg; //
  String picPrefix; 
  String dirAppendix;
  int numImgs;
  String dirName;
  
  
  // constructor
  PicInstall (  String picPrefix_, String dirAppendix_, 
  int numImgs_, String dirName_) {
    picPrefix=picPrefix_;
    dirAppendix=dirAppendix_;
    numImgs=numImgs_;
    dirName=dirName_;
   } // constructor
 
//methods:

public void putPicsInFolders(){
  for(int i = 0; i < numImgs; i++){
    int realPicNum = i+1;
  img = loadImage(picPrefix + realPicNum + ".jpg");
  // Create Directory and add add a pic from data folder
  String newDirName = dirName + "/" + "Collection" + dirAppendix;
createCheckDirectorySavePic(newDirName, img, picPrefix + realPicNum);
  }
}
// Create Check Directory //////////////////////////////////////////
//from https://forum.processing.org/one/topic/how-do-i-write-data-to-the-phone-or-sd-card-memory.html  
//will create the directory if it doesn't already exist! nice.
public void createCheckDirectorySavePic(String path, PImage pic, String nameOfPic) {
  try {
    File newFile = new File(path);
    newFile.mkdirs();
    if (newFile.exists()) {
      println("Directory Exists...");
      if (newFile.isDirectory()) {
        println("isDirectory = true...");
        pg = createGraphics(pic.width,pic.height);
        pg.beginDraw();
        pg.image(pic, 0, 0);
        pg.endDraw();
        pg.save(path + nameOfPic + ".jpg"); //redo this - no "i" so need another parameter
        println("adding pic" + nameOfPic);
      } else println("isDirectory = false...");
    } else {
      println("Directory Doesn't Exist...");
    }
  }
  catch(Exception e) {
    println("Exception creating folder... "); 
    //e.printStackTrace();
  }
}  
//end Create Check Directory////////////////////////////////////
} // class





//public void requestPermission(Activity activity, String permission) {
//        if (ContextCompat.checkSelfPermission(activity, permission)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(activity, new String[]{permission}, 0);
//        }
//    }
  public void settings() {  size(displayWidth, displayHeight); }

//  @Override
//  public void settings() {
//    size(getScreenWidth(), getScreenHeight());
//  }


  //very useful functions here:
  public static int getScreenWidth() {
    return Resources.getSystem().getDisplayMetrics().widthPixels;
  }

  public static int getScreenHeight() {
    return Resources.getSystem().getDisplayMetrics().heightPixels;
  }
}
