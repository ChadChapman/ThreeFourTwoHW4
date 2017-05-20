/**
 * Chad Chapman CS 342 Winter 2017 Assignment 4
 */

package applications;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for parsing words out of input streams.
 * 
 * @author Chad Chapman
 * @version 16 Feb 2017
 *
 */
public class WordParser {

    /** The list of words currently being worked with. */
    private final List<String> myWordList;
    
    /** A seemingly amateur way to check if a char is alphabetic. */
    private final String myCharDictString;

    /**
     * Only constructor so far. Constructor parameters seem unneeded for this
     * class.
     */
    public WordParser() {

        myWordList = new ArrayList<>();
        myCharDictString = populateCharDict();

    }

    /**
     * A way to get word strings from a scanner nextLine String. Only parses on
     * the String split on spaces. Not refined Strings.
     * 
     * @param theNextLineString string of entire read line from a scanner or
     *            other input
     * @return list of split on space Strings from the parameter string
     */
    public List<String> parseWordsEntireLine(final String theNextLineString) {
        // final char[] nextLineCharArray = theNextLineString.toCharArray();
        // Character bigC;
        final String[] nextLineStringArray = theNextLineString.split(" ");
        for (final String s : nextLineStringArray) {
            myWordList.add(s);
        }
        return myWordList;

        //
    }
    
    /**
     * A way to further parse words from a list of strings.
     * 
     * @param theRawString string to parse further
     * @return List<String> list of strings, should contain less non-word
     *         Strings
     */
    public String refineString(final String theRawString) {
        final String[] strAry = theRawString.split("");

        final StringBuilder sb = new StringBuilder(64);
        //I like having a return string that makes this block more readable
        final String retString;
        for (final String s : strAry) {
            if (myCharDictString.contains(s)) {
                sb.append(s);
            } // that should eliminate any non-letters or spaces by this point
        }
        if (sb.length() > 1) {
            retString = sb.toString();
        } else if (sb.length() == 1 && checkSingleCharsWords(sb.toString())) {
            retString = sb.toString();
        } else {
            retString = "null";
        }
        return retString;

    }

    /**
     * A way to check if a single char should be counted as its own word.
     * 
     * @param theSingleString the char to whose status is to be determined
     * @return boolean whether or not the char will count as a word
     */
    private boolean checkSingleCharsWords(final String theSingleString) {
        boolean retBool = false;
        //PMD you vex me so, it is either this should be final or it should be a field
        //I only need this string here so it does not need to be a field
        final String singleLetterWords = "AaI";
        if (singleLetterWords.contains(theSingleString)) {
            retBool = true;
        }
        return retBool;
    }

    /**
     * A way to print out all the string elements in a list, each on its own
     * line.
     * 
     * @param thePrintList the list of strings to print out
     * @return String contiguous String of all the elements in the parameter
     *         list
     */
    public String printWordList(final List<String> thePrintList) {
        final StringBuilder sb = new StringBuilder(thePrintList.size());
        sb.append("Begin of printed List\n\n");
        for (final String s : thePrintList) {
            sb.append(s);
            sb.append('\n');
        }
        sb.append("End of printed List");

        return sb.toString();
    }

    /**
     * A way to clear out all words currently in the myWordList field.
     */
    public void clearCurrentWordList() {
        myWordList.clear();
    }

    /**
     * A way to decide what is used in the char dictionary for comparison.
     * 
     * @return string of the current char dictionary
     */
    private String populateCharDict() {
        final String retString = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        
        return retString;
    }

}
