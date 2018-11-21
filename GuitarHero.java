/* Filename: GuitarHero.java 
* Created by: Luis Matias-Escobar, cs8afdw and Judy Chun, cs8afug 
* Date: 11/26/2017
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

/**
 * the main GUI class that imitates Guitar
 */

public class GuitarHero {

private JFrame frame;
/** The total number of notes.*/
public static final int NUM_KEYS = 7;
/** How many octaves should be created.*/
public static final int NUM_OCTAVES = 3;
private String[] notes = {"C","D","E","F","G", "A","B"};
/** Holds the possible sharps.*/
private String[] sharps = {"C#","D#","F#","G#", "A#"};
/** Holds the octave numbers.*/
private String[] octave = {"3","4","5"};
/** Define the key name font */
public static final Font keyNameFont = new Font("Segoe UI", Font.PLAIN, 12);
// define the keyboards
public static final String keyboards = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
/** Holds the possible instruments*/
private JButton spaceB;
private int [][] keyVals  = { 
  {
   0,0,0,0,0,
    java.awt.event.KeyEvent.VK_Q,
    java.awt.event.KeyEvent.VK_W }, {
    java.awt.event.KeyEvent.VK_E,
    java.awt.event.KeyEvent.VK_R,
    java.awt.event.KeyEvent.VK_T,
    java.awt.event.KeyEvent.VK_Y,
    java.awt.event.KeyEvent.VK_U,
    java.awt.event.KeyEvent.VK_I,
    java.awt.event.KeyEvent.VK_O}, {
    java.awt.event.KeyEvent.VK_P,
    java.awt.event.KeyEvent.VK_OPEN_BRACKET,
    java.awt.event.KeyEvent.VK_Z,
    java.awt.event.KeyEvent.VK_X,
    java.awt.event.KeyEvent.VK_C,
    java.awt.event.KeyEvent.VK_V,
    java.awt.event.KeyEvent.VK_B }
};
private int [] keyval2 = {

    java.awt.event.KeyEvent.VK_N,
    java.awt.event.KeyEvent.VK_M,
    java.awt.event.KeyEvent.VK_COMMA,
    java.awt.event.KeyEvent.VK_PERIOD,
    java.awt.event.KeyEvent.VK_SLASH
    
};
private int [][] blackKeyVal = { 
  {
    java.awt.event.KeyEvent.VK_4,
    java.awt.event.KeyEvent.VK_5,
    java.awt.event.KeyEvent.VK_7,
    java.awt.event.KeyEvent.VK_8,
    java.awt.event.KeyEvent.VK_9,
 },{java.awt.event.KeyEvent.VK_MINUS,
    java.awt.event.KeyEvent.VK_EQUALS,
    java.awt.event.KeyEvent.VK_D,
    java.awt.event.KeyEvent.VK_F,
    java.awt.event.KeyEvent.VK_G,
 },{
    java.awt.event.KeyEvent.VK_J,
    java.awt.event.KeyEvent.VK_K,
    java.awt.event.KeyEvent.VK_SEMICOLON,
    java.awt.event.KeyEvent.VK_QUOTE,}
};

 /**
  * Launch the application.
  */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          GuitarHero window = new GuitarHero();
          window.frame.setVisible(true);
        } 
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public GuitarHero() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    // create an array of frequencies
    int[] freqs = new int[37];
    // create an array of sound objects
    Sound[] sounds = new Sound[37];
    
    System.out.println("Initializing ... (It might take up to 20 seconds)");
    
    // iterate through 37 frequencies, and call whitenoise and pluck 
    // methods to generate Sounds
    for(int i = 0; i < 37; i++){
      freqs[i] = (int) (440 * Math.pow( 2.0, (i-24.0)/12.0));
      try {
        sounds[i] = Sound.whitenoise(freqs[i]).pluck(30000);
      } catch (Exception e) {
        System.out.println(e);
      }
    }

    AbstractAction buttonPressed = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JButton jb;
        if( e.getActionCommand().equals(" ")) { jb = spaceB; }
        else jb = (JButton)e.getSource();
        String name = jb.getName();
        System.out.println(name);
        jb.getModel().setArmed(true);
        jb.getModel().setPressed(true);
        Timer t = new Timer(100, new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            jb.getModel().setArmed(false);
            jb.getModel().setPressed(false);
          }
        });
        t.setRepeats(false);
        t.start();
        int index = keyboards.indexOf(e.getActionCommand());
        if(index >= 0 && index < sounds.length) {
          Sound result = sounds[index];
          System.out.println(e.getActionCommand());
          System.out.println(result);
          result.play();
        }
      }
    };
    
    
    // create the main frame named Guitar Hero
    frame = new JFrame("Guitar Hero");
    frame.setBounds(100, 100, 1250, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    Container mainPanel = frame.getContentPane();
    // set the layout and background of the GUI
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setForeground(Color.WHITE);
    mainPanel.setBackground(Color.BLACK);
    mainPanel.add(Box.createRigidArea(new Dimension(0,10)));

    JLayeredPane panel = makeKeys(buttonPressed);
    mainPanel.add(panel);
  }
  
  /**
   * making a JButton crossponding to a key on the keyboard
   */
  public JButton makeKey(String name, AbstractAction buttonPressed, 
                 int keyVal, int x, int y, Color background) {
    
    Action blankAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
      }
    };

    JButton jb = new JButton(name);
    jb.setName(name);
    jb.setActionCommand(name);
    jb.addActionListener(buttonPressed);
    jb.setFont(keyNameFont);
    jb.setVerticalAlignment(SwingConstants.BOTTOM);
    ActionMap am = jb.getActionMap();
    am.put("pressed", blankAction);
    am.put("released", blankAction);
    jb.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
       put(javax.swing.KeyStroke.getKeyStroke(keyVal,0), "pressed");
    jb.getActionMap().put("pressed", buttonPressed);
    jb.setBounds(x,y,50,180);
    jb.setBackground(background);
    return jb;
  }

  /**
   * Make the keys on keyboards by creating JButtons and 
   * pass in Action object to associate with the keyboard action
   * on keyboard.
   */
  public JLayeredPane makeKeys(AbstractAction buttonPressed){
    // Initialize
    String name = "";
    int x = 55;
    int y = 0;
  
    // Create layerPane
    JLayeredPane keyBoard = new JLayeredPane();
    keyBoard.setPreferredSize(new Dimension(900,162));
    keyBoard.add(Box.createRigidArea(new Dimension(x, 0))); 

    // Add the white key buttons
    for(int i = 0; i< NUM_OCTAVES; i ++) {  
      for(int j = 0; j < NUM_KEYS; j ++) {
        // skip the first five keys (We are not using them)
        if( i == 0 && j < 5) { continue; }
          // name of the key
          name = notes[j] + octave[i];
          // Jbutton setup
          keyBoard.add(makeKey(name, buttonPressed, keyVals[i][j], x, y, 
                               Color.WHITE), new Integer(1));
          keyBoard.add(Box.createRigidArea(new Dimension(2, 0)));
          x += 51; // increment the x axis distance
      }
    }
    // iterate through the left 6 keys and add them
    for(int j = 0; j < 5; j ++ ) {
      name = notes[j] + "5";
      keyBoard.add(makeKey(name, buttonPressed, keyval2[j], x, y, Color.WHITE), new Integer(1));
      keyBoard.add(Box.createRigidArea(new Dimension(2, 0)));
      x += 51;
    }
    // make a special case for space key
    name = "A5";
    spaceB = new JButton(name);
    spaceB.setName(name);
    spaceB.setActionCommand(name);
    spaceB.addActionListener(buttonPressed);
    spaceB.setFont(keyNameFont);
    spaceB.setVerticalAlignment(SwingConstants.BOTTOM);
    spaceB.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
       put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SPACE,0), "pressed");
    spaceB.getActionMap().put("pressed", buttonPressed);
    spaceB.setBounds(x,y,50,180);
    spaceB.setBackground(Color.WHITE);
    keyBoard.add(spaceB, new Integer(1));
    keyBoard.add(Box.createRigidArea(new Dimension(2, 0)));
    
    // Reinitialize
    x = 0;
     
    // Add the black keys 
    for(int i = 0; i < 3; i ++) {
      // Make 5 "keys"
   
      JButton jb0 = new JButton();
      name = sharps[0]+octave[i];
      jb0.setName(name);
      jb0.setActionCommand(name);
      jb0.addActionListener(buttonPressed);
      jb0.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
       put(javax.swing.KeyStroke.getKeyStroke(blackKeyVal[i][0],0), "A_pressed");
      jb0.getActionMap().put("A_pressed", buttonPressed);

      JButton jb1 = new JButton();
      name = sharps[1]+octave[i];
      jb1.setName(name);
      jb1.setActionCommand(name);
      jb1.addActionListener(buttonPressed);
      jb1.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
      put(javax.swing.KeyStroke.getKeyStroke(blackKeyVal[i][1],0), "A_pressed");
      jb1.getActionMap().put("A_pressed", buttonPressed);

      JButton jb2 = new JButton();
      name = sharps[2]+octave[i];
      jb2.setName(name);
      jb2.setActionCommand(name);
      jb2.addActionListener(buttonPressed);
      jb2.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
      put(javax.swing.KeyStroke.getKeyStroke(blackKeyVal[i][2],0), "A_pressed");
      jb2.getActionMap().put("A_pressed", buttonPressed);

      JButton jb3 = new JButton();
      name = sharps[3]+octave[i];
      jb3.setName(name);
      jb3.setActionCommand(name);
      jb3.addActionListener(buttonPressed);
      jb3.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
      put(javax.swing.KeyStroke.getKeyStroke(blackKeyVal[i][3],0), "A_pressed");
      jb3.getActionMap().put("A_pressed", buttonPressed);



      // Place the 5 keys 
      jb0.setBounds(190+(357*i),y,32,105);
      keyBoard.add(jb0,new Integer(2));

      jb1.setBounds(241+(357*i),y,32,105);
      keyBoard.add(jb1,new Integer(2));

      jb2.setBounds(343+(357*i),y,32,105);
      keyBoard.add(jb2,new Integer(2));

      jb3.setBounds(395+(357*i),y,32,105);
      keyBoard.add(jb3,new Integer(2));

      if(i < 2) {
        JButton jb4 = new JButton();
        name = sharps[4]+octave[i];
        jb4.setName(name);
        jb4.setActionCommand(name);
        jb4.addActionListener(buttonPressed);
        jb4.setBounds(447+(357*i),y,32,105);
        jb4.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
        put(javax.swing.KeyStroke.getKeyStroke(blackKeyVal[i][4],0), "A_pressed");
        jb4.getActionMap().put("A_pressed", buttonPressed);
        keyBoard.add(jb4,new Integer(2));
        jb4.setBackground(Color.BLACK);
        }

        jb0.setBackground(Color.BLACK);
        jb1.setBackground(Color.BLACK);
        jb2.setBackground(Color.BLACK);
        jb3.setBackground(Color.BLACK);
      }
  JButton jb2 = new JButton();
  name = "A#2";
  jb2.setName(name);
  jb2.setActionCommand(name);
  jb2.addActionListener(buttonPressed);
  jb2.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
  put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2,0), "A_pressed");
  jb2.getActionMap().put("A_pressed", buttonPressed);
  jb2.setBounds(90, y, 32, 105);
  keyBoard.add(jb2,new Integer(2));
  jb2.setBackground(Color.BLACK);
  return keyBoard;
  }
}
