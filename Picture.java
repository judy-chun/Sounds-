/* PSA4 Code
 * Filename: Picture.java
 * Created by: Luis Matias-Escobar, cs8afdw and Judy Chun, cs8afug 
 * Date: 11/26/2017
 */

/*
 * Comment on Program: The flipHorizontalSqaure method will return a new Picture with a horizontal flip
 * on the bounded region of the image. If the region to be flipped is out of bounds, the region that is within
 * bounds of the picture will only be flipped, while ignoring the regions that go out of bounds. If the initial
 * point implies a square that is completely out of bounds of the picture, the method returns the picture with
 * nothing flipped. 
*/

import java.awt.*;
import java.awt.Color;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * Copyright Georgia Institute of Technology 2004-2005
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param width the width of the desired picture
   * @param height the height of the desired picture
   */
  public Picture(int width, int height)
  {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /*
   * Method:     After each x,y coordinate and size is entered, this method only flips the region that is within bounds
   *            of the picture, ignoring the regions that go out of bounds, and returns a new Picture 
   * Input:     enter x and y integers as the (x,y) coordinate of the bottom left corner of the region to be 
   *            flipped, and an integer for uniform size 
   * Return:    returning a new Picture that is a modified copy of the original Picture 
   */
  
  public Picture flipHorizontalSquare(int x, int y, int size){
    
    Picture result = new Picture(this);  //making a copy of the calling object called result 

    int width = size;      //setting up integers for uniform size instead of width and height 
    int height = size;
    int leftTopX = x;            //x-coordinate of top left corner of region 
    int leftBottomY = y;         //y-coordinate of bottom left corner of region 
    int rightTopX = x + width;   //x-coordinate of top right corner of region 
    int leftTopY = y-height;     //y-coordinate of top left corner of region
    int finalWidth = width;      
    int finalHeight = height;
    Color tempColor;             //storing temporary color value when setting color 
    
    //if region is out of bounds to the left  
    if(leftTopX < 0) {             
      finalWidth = width+leftTopX;   //final width is only within bounds of the Picture 
      leftTopX=0;    //setting x-coordinate of top left corner of region as 0 
      
      if(leftTopY < 0) {
        finalHeight = height+leftTopY;   //final height is only within bounds of the Picture 
        leftTopY = 0;  //setting y-coordinate of top left corner of region as 0 
      }
      
      if(leftBottomY > result.getHeight()) {   
        finalHeight = result.getHeight()- leftTopY; //final height is only within bounds of the Picture
        leftBottomY = result.getHeight(); //setting y-coordinate of bottom left corner as Picture's height 
      }
    }
    
    
    //if region is out of bounds to the right 
    if(rightTopX > result.getWidth() ) {  
      finalWidth = result.getWidth()-leftTopX;  //final width is only within bounds of the Picture 
      rightTopX = result.getWidth();  //setting x-coordinate of top right corner as Picture's width 
      
      if(leftTopY < 0) {
        finalHeight = height+leftTopY;  //final height is only within bounds of the Picture 
        leftTopY = 0;  //setting y-coordinate of top left corner of region as 0 
      }
      
      if(leftBottomY > result.getHeight()) {
        finalHeight = result.getHeight()- leftTopY; //final height is only within bounds of the Picture
        leftBottomY = result.getHeight();  //setting y-coordinate of bottom left corner as Picture's height 
      }
    }
    
    
    //if region is out of bounds to the top 
    if(leftTopY < 0) {   
      finalHeight = height+leftTopY;  //final height is only within bounds of the Picture
      leftTopY = 0;  //setting y-coordinate of top left corner of region as 0 
      
      if(leftTopX < 0) {
        finalWidth = width+leftTopX;  //final width is only within bounds of the Picture
        leftTopX=0;  //setting x-coordinate of top left corner of region as 0 
      }
      
      if(rightTopX > result.getWidth() ) {    
        finalWidth = result.getWidth()-leftTopX;  //final width is only within bounds of the Picture 
        rightTopX = result.getWidth();  //setting x-coordinate of top right corner as Picture's width 
      }
    }
    
    
    //if region is out of bounds to the bottom 
    if(leftBottomY > result.getHeight()) { 
      finalHeight = result.getHeight()- leftTopY;  //final height is only within bounds of the Picture
      leftBottomY = result.getHeight();  //setting y-coordinate of bottom left corner as Picture's height 
      
      if(leftTopX < 0) {
        finalWidth = width+leftTopX;  //final width is only within bounds of the Picture
        leftTopX=0;  //setting x-coordinate of top left corner of region as 0 
      }
      
      if(rightTopX > result.getWidth() ) {  
        finalWidth = result.getWidth()-leftTopX;  //final width is only within bounds of the Picture 
        rightTopX = result.getWidth();  //setting x-coordinate of top right corner as Picture's width 
      }      
    }
    
    width = finalWidth;  
    height = finalHeight;
    int leftHalf = (int)(width/2);  //setting limit (mid-point for flip) 
    int topHalf = (int)(height/2);  
    
    
    
    //The loop that will coordinate the horizontal flip 
    for(int resultX = leftTopX; resultX < leftTopX + (width/2); resultX++)
    {
      
      for(int resultY = leftTopY; resultY < leftBottomY; resultY++)
      { 
        
        
        int realPixel = resultX - leftTopX; 
        
        //Gets the pixels
        Pixel leftPixel = result.getPixel(resultX, resultY);  
        Pixel rightPixel = result.getPixel(rightTopX-1- realPixel, resultY);  
        
        //Get the RGB color values from the left pixel
        int r = leftPixel.getRed();
        int g = leftPixel.getGreen();
        int b = leftPixel.getBlue();
        
        //store temporary color value of the left pixel 
        tempColor = new Color(r,g,b);
        
        //Sets the colors in order to do the flip
        leftPixel.setColor(rightPixel.getColor());
        rightPixel.setColor(tempColor);  
        
      }
      
    }
    
    return result;  //returning a new Picture 
  }
  
  
  
} // this } is the end of class Picture, put all new methods before this

