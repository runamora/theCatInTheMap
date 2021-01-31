package processing.test.alleycat_v3;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ketai.sensors.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class alleycat_v3 extends PApplet {

//WIP

/**
 * Developed by RunaMORA
 * Based on - Updated: 2017-07-27 Daniel Sauter/j.duran
 * With - Ketai Sensor Library for Android: http://Ketai.org
 */

 

double longitude, latitude;
KetaiLocation location;

IntList coordX, coordY;

//ggj

Item i1;
int si = 6;
int counter = 0;
int limit = 35000;
//int limit = 500;
int border = 100;
boolean win = false;

float x,y;

int[] bck = {255,242,0};
int[] goal = {237,28,36};
int[] heroe = {46,49,146};



public void setup() {
  
  
  x = random(border,width-border);
  y = random(border,height-border);  
  i1 = new Item(x,y);

  
  location = new KetaiLocation(this);

  stroke(255);
  strokeWeight(3);
  noFill();
  
  coordX = new IntList();
  coordY = new IntList();
}

public void draw() {
  background(bck[0],bck[1],bck[2]);
  //frameRate(0.1);
  
  textAlign(LEFT);
  textSize(displayDensity * 14);
  if (location == null  || location.getProvider() == "none"){
    text("Location data is unavailable. \n" +
      "Please check your location settings.", 0, 0, width, height);
  }
  else{
    text("Latitude: " + latitude + "\n" + 
      "Longitude: " + longitude + "\n" + 
      "Provider: " + location.getProvider(),
      70, 70, width, height);

    float lx = (float) (longitude*1000);
    float ly = (float) (latitude*1000);

    float mapX = map(lx,-78567,-78423,0,width);
    float mapY = map(ly,-93,-351,0,height);
    
    coordX.append(PApplet.parseInt(mapX));
    coordY.append(PApplet.parseInt(mapY));
    
    stroke(heroe[0],heroe[1],heroe[2]);
    for(int i = 3; i < coordX.size(); i++){
    line(coordX.get(i-1), coordY.get(i-1),coordX.get(i), coordY.get(i));
    }
    
    //ggj
    if (counter>limit && win==false) {
      bck[0] = 255;
      bck[1] = 255;
      bck[2] = 255;
      fill(0);
      textAlign(CENTER);
      textSize(displayDensity * 32);
      text("LOST", width/2, height/2);
    } else {
      i1.display(mapX, mapY);   
    } 
  }
  counter++;  
}

public void onLocationEvent(double _latitude, double _longitude)
{
  longitude = _longitude;
  latitude = _latitude;  
}


class Item{
  float ypos, xpos;
  
  Item (float x, float y){
    xpos = x;
    ypos = y;
  }
                                  
  public void display(float bikex, float bikey){
    noFill();
    strokeWeight(si);
    if (bikex - xpos > -si*5 
        && bikex - xpos < si*5
        && bikey - ypos > -si*5 
        && bikey - ypos < si*5
        ){
         bck[0] = 0;
         bck[1] = 0;
         bck[2] = 0;
         
         fill(255);
         textAlign(CENTER);
         textSize(displayDensity * 32);
         text("FOUND", width/2, height/2);
         win = true;
         
    } else if(bikex - xpos > -si*10 
        && bikex - xpos < si*10
        && bikey - ypos > -si*10 
        && bikey - ypos < si*10
        ){
       stroke(goal[0],goal[1],goal[2]);
    } else if (bikex - xpos > -si*15 
        && bikex - xpos < si*15
        && bikey - ypos > -si*15 
        && bikey - ypos < si*15
        ){
      stroke(goal[0],goal[1],goal[2],100);
    }  else {
       stroke(goal[0],goal[1],goal[2],50);
    }
    ellipse(xpos,ypos,si*20+si*5*sin(frameCount*0.03f),si*20+si*5*sin(frameCount*0.03f));
    ellipse(xpos,ypos,si*15+si*5*sin(frameCount*0.03f),si*15+si*5*sin(frameCount*0.03f));
    ellipse(xpos,ypos,si*10+si*5*sin(frameCount*0.03f),si*10+si*5*sin(frameCount*0.03f));
}
}
  public void settings() {  fullScreen(); }
}
