/**
 * Chad Chapman CS 342 Winter 2017 Assignment 4
 */

package applications;

import comparators.RealWordComparator;
import items.RealWord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

//import comparators.RealWordDescendingComparator;

/**
 * A class for taking in a File and outputting the counts of frequency specified
 * items are found.
 * 
 * @author Chad Chapman
 * @version 15 Feb 2017
 */
public class ReadFileOutputCounts {

    /** Character set used to ensure consistency. */
    public static final Charset ENCODING = StandardCharsets.UTF_8;

    /** A place to store strings and their frequency counts. */
    private final Map<String, RealWord> myStringCountMap;

    /** File to read form and output counts of its contents. */
    private final File myReadFile;

    /** List of the chosen return elements. */
    private List<RealWord> myReturnList;

    /** User selected number of elements to return. */
    private final Integer myUserInputValue;

    /** A list of the words read from the file. */
    private final List<String> myReadList;
    
    /** A refined list of Strings to be counted. */
    private final List<String> myRefinedList;

    /** An object to parse out words from input. */
    private final WordParser myWordParser;

    /**
     * A value for the total time used to get top n values from the count map.
     */
    private long myTotalReturnTime;
    
    /**
     * Constructor to take an input file and a string which denotes the choice
     * of count storage data structure.
     * 
     * @param theInputFile file to read and get counts from
     * @param theCountStore name of type of storage structure type
     * @param theCountAmount number of elements to return from this counting map
     */
    public ReadFileOutputCounts(final File theInputFile, final String theCountStore,
                                final Integer theCountAmount) {
        
        myReadFile = theInputFile;
        myReadList = new ArrayList<String>();
        myReturnList = new ArrayList<RealWord>();
        myRefinedList = new ArrayList<String>();
        myWordParser = new WordParser();
        // init the chosen map
        myStringCountMap = initChosenMapStore(theCountStore);

        myUserInputValue = theCountAmount;
        myTotalReturnTime = 0;
        
        setup();
    }

    /**
     * A way to dial in settings for this class.
     */
    private void setup() {
        try {
            readFileIntoList();
        } catch (final IOException e) {
            System.err.println("error in reading the file in the setup method");
        }
        //insertWordListIntoCountMap(myRefinedList);
        insertWordListIntoCountMap(myRefinedList);
        System.err.println("The map size ->" + myStringCountMap.size());
        makeReturnNList();
    }

    /**
     * A way to initialize the chosen structure for storing counts.
     * 
     * @param theCountStoreChoice string with choice of backing store info
     * @return Map type of data structure used for storage
     */
    private Map<String, RealWord> initChosenMapStore(final String theCountStoreChoice) {
        final Map<String, RealWord> countMap;
        if ("HashMap".equals(theCountStoreChoice)) {
            countMap = new HashMap<String, RealWord>();
        } else {
            countMap = new TreeMap<String, RealWord>();
            //countMap = new TreeMap<String, RealWord>(new RealWordDescendingComparator());
            System.out.println("tree map was selected");
        }
        return countMap;
    }

    /**
     * A way to populate a class variable List with the contents of the file
     * being read as input.
     * 
     * @throws IOException any number of reasons the file is not read
     */
    private void readFileIntoList() throws IOException {

        final String readFileName = myReadFile.getName();
        final Path filePath = Paths.get(readFileName);
        try (final Scanner inputScanner = new Scanner(filePath, ENCODING.name())) {

            while (inputScanner.hasNextLine()) {
                final String nextLineScanned = inputScanner.nextLine();
                myReadList.addAll(myWordParser.parseWordsEntireLine(nextLineScanned));
                
                myWordParser.clearCurrentWordList(); // or reset?
            }
        }
        System.out.println("ReadList size -> " + getReadList().size() + '\n');
        refineReadList();
        //System.out.println("list was refined -> " + getRefinedList().size() + '\n');
    }

    /**
     * A way to get cleaner elements before inserting into the map.
     */
    private void refineReadList() {
        for (final String s : myReadList) {
            final String testString = myWordParser.refineString(s);
            if (!"null".equals(testString)) {
                myRefinedList.add(testString);
            }
        }
    }

    /**
     * A way to return a map of the strings to count as the keys and the count
     * of each string as the value.
     * 
     * @param theWordList the list of words to use as map keys
     * 
     */
    private void insertWordListIntoCountMap(final List<String> theWordList) {
        for (final String s : theWordList) {
            if (myStringCountMap.containsKey(s)) {
                myStringCountMap.get(s).incrementCount();
            } else {
                myStringCountMap.put(s, new RealWord(s));
            }
        }
    }

    /**
     * A way to make a list of the top n counts from the string count map. The
     * client will choose the value of n for how many of the top words to
     * return.
     * 
     */
    private void makeReturnNList() {
        
        final long startReturnTime = System.nanoTime();
//Sorry PMD, the spec sheet says I need to make a TreeMap
        if (myStringCountMap.getClass() == TreeMap.class) {
            
            myReturnList = sortTreeMapValuesDescending();
            System.out.println("the tree map was sorted and a return list made\n");
            //System.err.println("rfoc myReturnList size is->" + myReturnList.size() + "\n");
        } else {
            
            myReturnList = sortHashMapValuesDescending();
            //System.out.println("the hash map was selected and sort happened");
        }
        
        final long endReturnTime = System.nanoTime();
        myTotalReturnTime = endReturnTime - startReturnTime;
        
    }

    /**
     * A way to sort the values from a HashMap in descending order.
     * 
     * @return list of top n values from HashMap
     */
    private List<RealWord> sortHashMapValuesDescending() {
        final List<RealWord> valList = new ArrayList<>();
        final List<RealWord> retList = new ArrayList<>();
        final Collection<RealWord> valCollection = myStringCountMap.values();
        final Iterator<RealWord> valIter = valCollection.iterator();
        
        while (valIter.hasNext()) {
            final RealWord nextWord = valIter.next();
            valList.add(nextWord);
        }
        
        valList.sort(new RealWordComparator());
        Collections.reverse(valList);

        //now return the top n values 
        int stringsAdded = 0;
        System.out.println("my user input value is -> " + myUserInputValue);
        while (stringsAdded < myUserInputValue) {
            final RealWord indexRW = valList.get(stringsAdded);
            retList.add(indexRW);
            
            stringsAdded++;
        }
        return retList;
    }

    /**
     * A way to sort the values in a TreeMap and return the top n values.
     * 
     * @return a list of the top n values for this map
     */
    private List<RealWord> sortTreeMapValuesDescending() {
        final List<RealWord> valList = new ArrayList<>();
        final List<RealWord> retList = new ArrayList<>();
        final Collection<RealWord> valCollection = myStringCountMap.values();
        final Iterator<RealWord> valIter = valCollection.iterator();
        
        while (valIter.hasNext()) {
            final RealWord nextWord = valIter.next();
            valList.add(nextWord);
        }
        
        valList.sort(new RealWordComparator());
        Collections.reverse(valList);

        //now return the top n values 
        int stringsAdded = 0;
        while (stringsAdded < myUserInputValue) {
            final RealWord indexRW = valList.get(stringsAdded);
            retList.add(indexRW);
            
            stringsAdded++;
        }
        return retList;
    }

    /**
     * A way to return the map containing the strings and frequency counts.
     * 
     * @return map of string keys and RealWord values
     */
    public Map<String, RealWord> getMyStringCountMap() {
        return myStringCountMap;
    }

    /**
     * A way to the the total time it takes to get the top n values from a
     * string count map.
     * 
     * @return long of the total elapsed time from system.nanotime()
     */
    public long getMyTotalReturnTime() {
        return myTotalReturnTime;
    }

    /**
     * A way to get a message with the information of the type of store used and
     * the total time it took to get n values from that store.
     * 
     * @return String time and store message
     */
    public String getTotalReturnTimeMsg() {
        final StringBuilder sb = new StringBuilder(128);
        sb.append("The total time for this operation using a:\n ");
        sb.append(myStringCountMap.getClass());
        //A magic number but one use I don't think calls for a static field
        sb.append("\nwas " + ((double) (getMyTotalReturnTime() / 100000.0)));
        sb.append(" milliseconds!");
        
        return sb.toString();
    }

    
    /**
     * A way to get the list to be returned.
     * 
     * @return list of strings
     */
    public List<RealWord> getReturnList() {

        
        return myReturnList;
    }

    /**
     * A way to return the list of words that was last read in.
     * 
     * @return list of words read in from the input file.
     */
    public List<String> getReadList() {
        return myReadList;
    }
    
    /**
     * A way to return the list of words that was refined then read into the map.
     * 
     * @return list of words read in from the input file.
     */
    public List<String> getRefinedList() {
        return myRefinedList;
    }
    

    /**
     * A way to print out the contents of a list of strings with each element on
     * its own line.
     * 
     * @param thePrintList list of strings to be printed out
     * @return single contiguous string containing all elements in the parameter
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

    // end of ReadFileOutputCounts class
}
