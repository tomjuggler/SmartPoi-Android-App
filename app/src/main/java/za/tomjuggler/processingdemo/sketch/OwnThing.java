package za.tomjuggler.processingdemo.sketch;

import android.app.Activity;

import za.tomjuggler.processingdemo.MainActivity;
import processing.core.PApplet;

/**
 * Created by root on 23/6/15.
 */
public class OwnThing extends PApplet {

    boolean followMouse = false;

    @Override
    public void setup() {
        colorMode(HSB,360,100,100);

        smooth();
    }


    @Override
    public void draw() {
        background(0,0,0);
        stroke(random(100,360),80,100);//set the color

        for(int x=0; x<=width; x=x+5) {
            //float y = (sin(radians(x+frameCount))*170)+(height/2);
            float y = (sin(radians(x+frameCount))*170)+(height/2);

            //line(x,y,mouseX,mouseY);
            //line(x,y,10,10);
            //line(x,y,mouseX,(height/2));
            if(followMouse){
                line(x,y,mouseX,mouseY);
            }else{
                line(x,y,(width/2),(height/2));
            }
        }
    }

    @Override
    public void mousePressed(){
        followMouse = !followMouse;

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(0);
    }

    @Override
    public void settings() {
        size(400,400);
    }
}
