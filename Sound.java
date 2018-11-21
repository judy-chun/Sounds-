/* Filename: Sound.java 
* Created by: Luis Matias-Escobar, cs8afdw and Judy Chun, cs8afug 
* Date: 11/26/2017
*/ 

/*
 * Class that represents a sound.  This class is used by the students
 * to extend the capabilities of SimpleSound.
 *
 * Copyright Georgia Institute of Technology 2004
 * @author Barbara Ericson ericson@cc.gatech.edu
 */

/*--------PROGRAM DESCRIPTION----------
 * PSA7PictureTester.java program allows you to horizontally flip only the square region of the
 * picture, and shows this new flipped image. First, the program lets you choose and create a picture.
 * The program then asks you to enter the x-value and y-value of the bottom left corner of the
 * box to flip horizontally, along with the size of the box to flip. After entering the information,
 * the program will display a new Picture, which is the modified copy of the original picture with the
 * horizontally flipped square region. 
 * 
 * Sound.java program allows you to create plucking sounds of a guitar string by using a sound
 * containing the white noise, which is a random noise, with the correct length to generate the 
 * final resulting sound. The program modifies both the white noise sound and the sound that stores
 * the final resulting sound by setting each element in the sound with different values. The program
 * also tests whether the generated white noise sound is the same as the already given file with random
 * noise sound, and also tests whether the generated plucking sound is the same as the given file with
 * actual plucking sound of a guitar string by returning true if the two sounds are the same, or else, false
 * if the two sounds are not the same. 
 */

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.Character;
import javax.swing.Timer;
import java.awt.event.ActionListener;

public class Sound extends SimpleSound
{

  /////////////// constructors ////////////////////////////////////

  /**
   * Constructor that takes a file name
   * @param fileName the name of the file to read the sound from
   */
  public Sound(String fileName)
  {
    // let the parent class handle setting the file name
    super(fileName);
  }

  /**
   * Constructor that takes the number of samples in
   * the sound
   * @param numSamples the number of samples desired
   */
  public Sound (int numSamples)
  {
    // let the parent class handle this
    super(numSamples);
  }

  /**
   * Constructor that takes the number of samples that this
   * sound will have and the sample rate
   * @param numSamples the number of samples desired
   * @param sampleRate the number of samples per second
   */
  public Sound (int numSamples, int sampleRate)
  {
    // let the parent class handle this
    super(numSamples,sampleRate);
  }

  /**
   * Constructor that takes a sound to copy
   */
  public Sound (Sound copySound)
  {
    // let the parent class handle this
    super(copySound);
  }

  ////////////////// methods ////////////////////////////////////
  
  /**
   * Method to return the string representation of this sound
   * @return a string with information about this sound
   */
  public String toString()
  {
    String output = "Sound";
    String fileName = getFileName();
    
    // if there is a file name then add that to the output
    if (fileName != null)
      output = output + " file: " + fileName;
    
    // add the length in frames
    output = output + " number of samples: " + getLengthInFrames();
    
    return output;
  }
  
  /*
   * Method: The method creates a sound with the correct length, and modifies its 
   * SoundSample array and returns the sound 
   * Input: frequency of the sound you want to generate 
   * Return: returns the Sound containing the white noise with the correct length 
   */
  public static Sound whitenoise(int frequency) {
    int soundLength = 0;
    
    //length of the SoundSample array should be calculated as 22050/frequency 
    if(22050%frequency == 0) {   
    soundLength = (int)(22050/frequency);  //length should not round up if 22050/frequency
    //is less than 0.5 where 22050 is the sampling frequency used in our Sound class
    }
    if(22050%frequency > 0) {
    soundLength = (int)(22050/frequency)+1; //length should round up if 22050/frequency
    //is greater than 0.5 
    } 
    Sound s = new Sound(soundLength); //creates a sound with the correct length 
    SoundSample[] soundArray = s.getSamples();  //get samples from the SoundSample array 
    Random generator = new Random();  //use Random class to generate random numbers 
    
    //loop through SoundSample array 
    for(int sourceIndex=0; sourceIndex < soundLength; sourceIndex++) {  
      
      int value = generator.nextInt(65536)-32768; //generating random number in the range of -32768 and 32767 inclusive 
      soundArray[sourceIndex].setValue(value); //setting the value of each cell in the SoundSample array 
      
    }
    return s; //returns the Sound containing the white noise with the correct length 
  }
  
  
  /*
   * Method: The method will generate the sound with soundLength SoundSamples by using two Sounds, 
   * the one from the calling object for rotating using Karplus-Strong algorithm, and the one we
   * just created (called result) for storing the final resulting sound 
   * Input: the number of SoundSamples in the actual pluck sound (not related to length of whitenoise) 
   * Return: return sound 
   *
   */
  public Sound pluck (int soundLength) {
    Sound result = new Sound(soundLength); //create a Sound, result, with the correct length
    SoundSample [] soundArray = result.getSamples(); //obtain its SoundSample array
    //create sound from the calling object, which is the whitenoise generated by whitenoise method 
    Sound whiteNoise = new Sound(this); 
    SoundSample[] soundArray1 = whiteNoise.getSamples(); 
    
    //loop through the result sound 
    for(int sourceIndex = 0; sourceIndex < soundArray.length; sourceIndex++) { 
      
      //getting the 0th and 1st element from the noise calling object's SoundSample array 
      int whiteNoiseValue1 = soundArray1[0].getValue();
      int whiteNoiseValue2 = soundArray1[1].getValue();
      //using Karplus-Strong algorithm 
      int newWhiteValue = (int)(0.996*(whiteNoiseValue1+whiteNoiseValue2)/2);
      //setting element from noise calling object's SoundSample array into each element in result's SoundSample array 
      soundArray[sourceIndex].setValue(soundArray1[0].getValue());  
      
      //loop through the noise calling object sound 
      for(int index = 0; index < soundArray1.length-1; index++) {
      soundArray1[index].setValue(soundArray1[index+1].getValue());
      //rotate the noise calling object's SoundSample array one position to the left 

      }
      soundArray1[soundArray1.length-1].setValue(newWhiteValue); //fill in the correct value on the last index 
    }
    

    return result; //return sound 
  }
  
  
  /*
   * Method: The method that is a tester for the pluck method, and compares if two Sounds from the
   * calling object and the parameter sound are the same. 
   * Input: parameter sound 
   * Return: return true if all SoundSamples from the calling object and the parameter sound are the same,
   * or else, it should return false. 
   */
  public boolean sameSound(Sound s) {
   
    SoundSample[] soundArray1 = this.getSamples();  //obtain calling object's SoundSample array 
    SoundSample[] soundArray2 = s.getSamples();     //obtain parameter sound's SoundSample array 
    
    //loop through the calling object 
    for(int sourceIndex = 0; sourceIndex < this.getLength(); sourceIndex++) { 
      
      
      //statement checks for unequal value 
      if( soundArray1[sourceIndex].getValue() != soundArray2[sourceIndex].getValue()) {

        return false;  //return false if two Sounds are not the same 
      }      
    }
    return true;  //return true if two Sounds are the same 
  }
  
  
   /*
   * Method: The method that is a tester for the whitenoise method, and compares if two Sounds from the
   * calling object and the parameter sound produces the same sound length. 
   * Input: parameter sound 
   * Return: return true if all SoundSamples from the calling object and the parameter sound have the
   * same length, or else, it should return false. 
   */
  public boolean sameLength(Sound s) {
    
    SoundSample[] soundArray1 = this.getSamples(); //obtain calling object's SoundSample array 
    SoundSample[] soundArray2 = s.getSamples();  //obtain parameter sound's SoundSample array 
    
    //loop through the calling object 
    for(int sourceIndex = 0; sourceIndex < this.getLength(); sourceIndex++) {
      
      //get value on the index of calling object's SoundSample array 
      int value = soundArray1[sourceIndex].getValue(); 
      
      //statement checks for unequal value of length  
      if(soundArray1.length != soundArray2.length) {
        System.out.println(soundArray1.length);
        System.out.println(soundArray2.length);
        return false;  //return false if two Sounds are not equal in length 
      }
      
      //statement checks whether value is within range of -32768 and 32767 inclusive 
      if( value > 32767) { 
       return false; 
      }
 
      if( value < -32768) {
       return false;  //return false if value is out of bounds  
      }
    }
    return true; //return true if two Sounds are equal in length and within range 
  }
  
  
   /*
    * Main Method: Calling appropriate methods to test if our pluck method and whitenoise
    * method generates the correct sound with the correct length by using provided files with 
    * random noise sound for base frequency 440Hz (noise440.wav), and pluck sound it generated (sound440.wav). 
    */
   public static void main( String[] args )
   {
     
    Sound randomNoise = new Sound(FileChooser.pickAFile()); //select noise440.wav file and create new Sound 
    Sound pluckNoise = new Sound(FileChooser.pickAFile()); //select sound440.wav file and create new Sound 
    
    Sound whiteNoise = Sound.whitenoise(440); //returns the Sound containing the white noise with the correct length 
    
    Sound randomPluck = randomNoise.pluck(10000); //returns the Sound that stores final resulting sound
    
    //tests if pluck method produces the correct sound given a white noise calling object 
    System.out.println(randomPluck.sameSound(pluckNoise)); 
    
    //tests if whitenoise method produces the correct sound length 
    System.out.println(whiteNoise.sameLength(randomNoise)); 
   }

} // this } is the end of class Sound, put all new methods before this
