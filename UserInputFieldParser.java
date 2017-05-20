/**
 * Chad Chapman CS 342 Winter 2017 Assignment 4
 */

package listeners;

import javax.swing.JTextArea;

/**
 * A class for parsing input from a user.
 * 
 * @author Chad Chapman
 * @version 17 Feb 2017
 *
 */
public class UserInputFieldParser {

    /** String of input from the user. */
    private final String myInputString;

    /** Area to send messages to. */
    private final JTextArea myOutputDisplay;

    /** A way to tell if the input string has been parsed out. */
    private boolean myParseStatus;

    /** The last int parsed out of the input string. */
    private Integer myLastParsedInteger;

    /**
     * Constructor for this class that takes a string to parse.
     * 
     * @param theInputTextString string to parse
     * @param theOutputDisplay area to display results
     */
    public UserInputFieldParser(final String theInputTextString,
                                final JTextArea theOutputDisplay) {

        myInputString = theInputTextString;
        myOutputDisplay = theOutputDisplay;

    }

    /**
     * Constructor for this class that takes a string to parse.
     * 
     * @param theOutputDisplay area to display results
     */
    public UserInputFieldParser(final JTextArea theOutputDisplay) {

        myInputString = null;
        myOutputDisplay = theOutputDisplay;
    }

    /**
     * A way to return an Integer from the input String. Checks for input not a
     * number and attempts to handle any input not able to have an integer
     * returned from it.
     * 
     * @param theUserString string to parse integer from
     * @return Integer represents the value parsed from the input string
     */
    public Integer getUserIntegerFromString(final String theUserString) {
        Integer retInt = 0;
        Integer stringInteger = 1;
        try {
            stringInteger = Integer.parseInt(theUserString);
            if (stringInteger < retInt) {
                myOutputDisplay.append("Please enter a positive number to!!-1!! top words "
                                       + "to read from this file!");
            }
            retInt = stringInteger;
            myLastParsedInteger = stringInteger;
            myParseStatus = true;
        } catch (final NumberFormatException e) {
            myOutputDisplay.append(">>Please Enter a positive"
                           + "number value below<<<\nThis will tell the program the number "
                           + "of top words to return from the file\n\n");
            myParseStatus = false;
            myLastParsedInteger = 0;
        }
        return retInt;
    }

    /**
     * A way to find out the status of the most recent attempt at parsing an
     * Integer from the user input String.
     * 
     * @return String status message string
     */
    public String getReturnMessageInteger() {
        final StringBuilder sb = new StringBuilder(128);
        if (myParseStatus) {
            sb.append("the last parsing attempt was successful. ");
            sb.append(myLastParsedInteger);
            sb.append(" was the last number read from user input.\n");

        } else {
            sb.append("the last parsing atempt was unsuccessful, "
                      + "please enter a positive number");
        }
        return sb.toString();
    }

    /**
     * A way to tell if the parse was successful or has happened yet.
     * 
     * @return boolean whether the parse has happened successful
     */
    public boolean getMyParseStatus() {
        return myParseStatus;
    }
    
    /**
     * A way to get the integer that has been parsed out last.
     * 
     * @return integer parsed from the String
     */
    public Integer getLastParsedInteger() {
        return myLastParsedInteger;
    }

}
