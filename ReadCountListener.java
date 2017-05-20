/**
 * Chad Chapman CS 342 Winter 2017 Assignment 4
 */

package listeners;

import applications.ReadFileOutputCounts;
import items.RealWord;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JTextArea;

/**
 * A class to read a given file and return a List of specified most common words
 * found in the file.
 * 
 * @author Chad Chapman
 * @version 15 Feb 2017
 *
 */
public class ReadCountListener implements ActionListener {

    /** User specified value to determine length of return list. */
    private final Integer myOutputNCount;

    /** List to hold the top n words found in the file. */
    private String myOutputString;

    /** File being read in. */
    private final File myInputFile;

    /** String to denote which data structure to use for storing counts. */
    private final String myCountStoreChoice;

    /** Area to display information to the program user. */
    private final JTextArea myDisplayArea;

    /**
     * Constructor for this listener.
     * 
     * @param theInputFile file to read from
     * @param theCountStore name of count store to use
     * @param theDisplay area to display program information
     * @param theOutputNCount user chosen number of top counts to display
     */
    public ReadCountListener(final File theInputFile, final String theCountStore,
                             final JTextArea theDisplay, final int theOutputNCount) {
        myInputFile = theInputFile;
        myCountStoreChoice = theCountStore;
        myOutputNCount = theOutputNCount;
        myDisplayArea = theDisplay;
        //I understand the PMD warning but I made the decision not to refactor 
        //as I think this just makes it more readable.
        myOutputString = null;
    }

    @Override
    public void actionPerformed(final ActionEvent theEvent) {

        final ReadFileOutputCounts outputCounter =
                        new ReadFileOutputCounts(myInputFile, myCountStoreChoice,
                                                 myOutputNCount);
        makeReadCountOutputMsg(outputCounter);

        myDisplayArea.append(myOutputString);
        myDisplayArea.append(outputCounter.getTotalReturnTimeMsg());
    }

    /**
     * A way to create a user friendly output message.
     * 
     * @param theCounter number of top counts to be returned
     */
    private void makeReadCountOutputMsg(final ReadFileOutputCounts theCounter) {
        final List<RealWord> countedList = theCounter.getReturnList();

        final StringBuilder sb = new StringBuilder(64);
        sb.append("These were the top ");
        sb.append(myOutputNCount);
        sb.append(" words found in the file: \n");
        for (final RealWord rw : countedList) {
            sb.append(rw.toMsgString());
        }
        myOutputString = sb.toString();
    }

    /**
     * A way to get the output string.
     * 
     * @return string of output
     */
    public String getReadCountOutputMsg() {
        return myOutputString;
    }

}
