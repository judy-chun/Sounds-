/* Filename: PSA7PictureTester.java 
* Created by: Luis Matias-Escobar, cs8afdw and Judy Chun, cs8afug 
* Date: 11/26/2017
*/ 

import java.util.Scanner;  // Do not remove this line

public class PSA7PictureTester
{
  /*
   * Comment on Program: The program prompts the user to select a picture and create a Picture object, while
   * also entering the (x,y) coordinate of bottom left corner of a region, and a uniform size of the square region
   * to be horizontally flipped. After entering the following information, the flipHorizontalSqaure method will
   * return a new Picture with a horizontal flip on the bounded region of the image. 
   */
  
  public static void main( String[] args )
  {

    Picture original = new Picture( FileChooser.pickAFile() ); //select a picture and create a Picture object
    original.explore();  //display the original picture 
    
    int x, y, size;  
    int width = original.getWidth(); 
    int height = original.getHeight();

    System.out.println( "Picture loaded with width=" + width +
                       " and height=" + height );

    Scanner reader = new Scanner( System.in );

    //Here's an example of reading integer input from the user.
    System.out.print("Please enter the x, y coordinates of lower left corner ");
    System.out.println( "of the box to flip horizontally, x first:" );
    x = reader.nextInt();  //x, y coordinate of bottom left corner is entered 
    y = reader.nextInt();
    System.out.println( "Enter the size of the box to flip:" );   
    size = reader.nextInt();  //entering an integer for uniform size

    System.out.println( "You entered x=" + x + " y=" + y + " size=" + size ); 

    //returning a new Picture that is a modified copy of the original Picture 
    Picture result = original.flipHorizontalSquare( x, y, size);  
    result.show();  //showing the new flipped Picture 
  }
}
