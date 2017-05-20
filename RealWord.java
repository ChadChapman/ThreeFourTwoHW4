/**
 * Chad Chapman
 * CSS 342 Winter 2017
 * Assignment 4
 */

package items;

/** 
 * A class to encapsulate char strings that have been parsed out into words.
 * 
 * @author Chad Chapma
 * @version 22 Feb 2017
 *
 */
public class RealWord implements Comparable<RealWord> {

    /** String of the word this class represents. */
    private final String myWord;
    
    /** Frequency count for this word. */
    private Integer myFreq;
    
    /**
     * Sole constructor for this class.  Assumes initial frequency of word is 1;
     * @param theWord string this class object represents
     */
    public RealWord(final String theWord) {
        myWord = theWord;
        myFreq = 1;
    }

    /**
     * A way to find the frequency for this word appearing.
     * 
     * @return integer of number of times this word occurs
     */
    public Integer getMyFreq() {
        return myFreq;
    }
    
    /**
     * A way to find out the string this class object represents.
     * 
     * @return word string this class represents
     */
    public String getMyWord() {
        return myWord;
    }
    
    /**
     * A way to increase the word count frequency value by one.
     */
    public void incrementCount() {
        myFreq++;
    }
    
    /**
     * A way to return a string containing all info this class holds about a word string.
     * 
     * @return string of this objects field info
     */
    public String toMsgString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("Word: -> ");
        sb.append(getMyWord());
        sb.append(" <- appears ");
        sb.append(getMyFreq());
        sb.append(" times in this file\n");
        
        return sb.toString();
    }

    @Override
    public int compareTo(final RealWord arg0) {
        final int compareFreq = arg0.getMyFreq();
        return this.myFreq - compareFreq;
    }
    
    //end of RealWord class   
}