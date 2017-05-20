/**
 * Chad Chapman
 * CS 342 Winter 2017
 * Assignment 4
 */
package programgui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import listeners.FileChooseListener;
import listeners.ReadCountListener;
import listeners.UserInputFieldParser;

/**
 * A class for creating a GUI for applications reading from a file and returning
 * some set of specified values. Heavily borrows from and in places directly
 * copied from StackDemoGUI.
 * 
 * @author Alan Fowler
 * @author Chad Chapman
 * @version 14 Feb 2017
 *
 */
public class ReadFileOutputValuesGUI extends JPanel {

    /** A generated version ID for Serialization. */
    private static final long serialVersionUID = 3902076596574498432L;

    /** The default width for text fields and text areas. */
    private static final int TEXT_WIDTH = 20;

    /** The default height for text areas. */
    private static final int TEXT_HEIGHT = 6;

    /** The width used in various margins and insets. */
    private static final int PADDING = 30;
    
    /** The value multiplier for the display area. */
    private static final int SIZE_MULT = 3;

    /** A message to display when the stack is empty. */
    private static final String EMPTY_MESSAGE = "1) SELECT A TEXT FILE\n" 
                    + "2) ENTER HOW MANY OF THE TOP WORDS YOU WANT TO RETURN\n"
                    + "3) CLICK THE BUTTON LABELED 'Get Counts' TO GET THE "
                    + "MOST USED WORDS IN THE FILE\n\n\n";

    /** An area to display the program output. */
    private final JTextArea myTextDisplayArea;

    /** An area for the user enter values to the program. */
    private final JTextField myUserInputField;

    /** A button to allow the user to manually submit their input. */
    private final JButton myHashMapCountButton;

    /** A button to allow the user to manually submit their input. */
    private final JButton myTreeMapCountButton;

    /** A button to allow the user to chose which file to read from. */
    private final JButton myChooseFileButton;

    /** Amount of most used words to be returned. */
    private Integer myOutputNCount;
    
    /** A way to choose the input file for the program to read from. */
    private final FileChooseListener myFileListener;

    /**
     * Sole constructor for this class so far. I don't think it should need
     * parameters yet. Prevents accidental instances.
     */
    public ReadFileOutputValuesGUI() {
        super();

        myTextDisplayArea = new JTextArea("MY TEXT DISPLAY AREA TEXT !! CHANGE!",
                                          TEXT_HEIGHT * SIZE_MULT, TEXT_WIDTH * SIZE_MULT);
        myUserInputField = new JTextField(TEXT_WIDTH / 2);
        myChooseFileButton = new JButton("Select Input File");
        myHashMapCountButton = new JButton("HashMap Count");
        myTreeMapCountButton = new JButton("TreeMap Count");
        myOutputNCount = -1;
        myFileListener = new FileChooseListener(this, myTextDisplayArea, myUserInputField);
        setUpPanel();
    }

    /**
     * A way to prepare the panel to display and function properly.
     */
    private void setUpPanel() {
        

        
        myHashMapCountButton.setMnemonic(KeyEvent.VK_H);
        
        //make the count buttons disabled until the needed info is able to be passed in
        myHashMapCountButton.setEnabled(false);
        myTreeMapCountButton.setEnabled(false);
        myTreeMapCountButton.setMnemonic(KeyEvent.VK_T);
                        
        myUserInputField.addActionListener(new InputFieldListener());
        // the chose n top words may need its own setup
        final JLabel inputLabel = new JLabel("How many words to count?");
        myUserInputField.setEditable(true);
        myUserInputField.setEnabled(false);
        
        myTextDisplayArea.setMargin(new Insets(PADDING, PADDING, PADDING, PADDING));
        myTextDisplayArea.setEditable(false);
        myTextDisplayArea.setFocusable(false);
        myTextDisplayArea.setText(EMPTY_MESSAGE);

        myChooseFileButton.addActionListener(myFileListener);
        myChooseFileButton.setMnemonic(KeyEvent.VK_F);
        
        final JPanel inputPanel = new JPanel();
        inputPanel.add(inputLabel);
        inputPanel.add(myUserInputField);
        inputPanel.add(myChooseFileButton);
        inputPanel.add(myHashMapCountButton);
        inputPanel.add(myTreeMapCountButton);

        setLayout(new BorderLayout());
        add(new JScrollPane(myTextDisplayArea), BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);

    }

    /**
     * Creates and displays the application frame.
     */
    public void display() {
        final JFrame frame = new JFrame("!THIS IS THE FRAME TEXT!!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this);

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        myUserInputField.grabFocus();
        //getRootPane().setDefaultButton(myCountValuesButton);
    }

    public String getPromptMsg() {
        return EMPTY_MESSAGE;
    }

    /**
     * An action listener for the push button. For some reason the compiler
     * wanted it to be public, but in another class I had it inside of, it
     * seemed fine as private.
     */
    private class InputFieldListener implements ActionListener {
        /**
         * Takes user input String and returns a parsed-out Integer.
         * 
         * @param theEvent incoming event
         * 
         */
        private final UserInputFieldParser myParser;

        /**
         * A class to act on the user inputting into a field.
         * 
         * @author Chad Chapman
         * @version 15 Feb 2017
         */
        InputFieldListener() {
            myParser = new UserInputFieldParser(myTextDisplayArea);
        }

        /**
         * What is to take place when this listener is notified.
         * 
         * @param theEvent trigger event for this listener
         */
        public void actionPerformed(final ActionEvent theEvent) {
            // this class now needs to be the way to take in input from text
            // field
            // then parse out the integer and handle exceptions
            // then pass the parsed value to the ReadCountListener
            final String userInputString = myUserInputField.getText();
            if (userInputString.isEmpty()) {
                myTextDisplayArea.append("Please enter the number of most "
                                         + "used words to return from this file\n");

            } else {
                myParser.getUserIntegerFromString(userInputString);
                //bring the user value out so it can go to map button params
                myOutputNCount = myParser.getLastParsedInteger();
                myTextDisplayArea.append(myParser.getReturnMessageInteger() + "\n");
                //now the file should be chosen, allowing the int value to be entered, so now 
                myHashMapCountButton.addActionListener(new ReadCountListener(
                                         myFileListener.getMyCurrentChosenFile(), 
                                         "HashMap", myTextDisplayArea, myOutputNCount));
                myHashMapCountButton.setEnabled(true);
                
                myTreeMapCountButton.addActionListener(new ReadCountListener(
                                         myFileListener.getMyCurrentChosenFile(), 
                                         "TreeMap", myTextDisplayArea, myOutputNCount));
                myTreeMapCountButton.setEnabled(true);
                
                //now reset the input field for the next time
                myTextDisplayArea.setCaretPosition(0); // forces scroll up
                myUserInputField.setText(null); // clears input field
                myUserInputField.grabFocus();
            }
        }
//        private String displayNextStep() {
//            final StringBuilder sb = new StringBuilder(128);
//            //if the user n int value was good but no file selected yet
//            if (myParser.getMyParseStatus() && myFileListener.getMyCurrentChosenFile() != null) {
//            sb.append()
//        }
//            //next is if n int value was good and file has been choses, enable count buttons
//            return sb.toString();
//    }

    // end of ReadFileOutputValuesGUI class
    }
}