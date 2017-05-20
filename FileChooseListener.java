/**
 * Chad Chapman
 * CS 342 Winter 2017
 * Assignment 4
 */

package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A class for selecting a file when notified.
 * @author Chad Chapman
 * @version 17 Feb 2017
 *
 */
public class FileChooseListener implements ActionListener {

    /** New line char ot avoid PMD warnings. */
    private static final String NEW_LINE = "\n";
    
    /** The file choosing interface. */
    private final JFileChooser myChooser;
    
    /** Parent component to call file chooser. */
    private final JComponent myParentComponent;

    /** The file that is currently selected. */
    private File myCurrentChosenFile;
    
    /** File that maybe should be a String. */
    private File myCurrentHomeDirectory;
    
    /** Display area to send messages up to. */
    private final JTextArea myDisplayArea;
    
    /** Field to get input from user. */
    private final JTextField myInputArea;
    
    /**
     * Constructor for this class.
     * 
     * @param theParentComponent parent to call file chooser with
     * @param theDisplayArea area to display messages
     * @param theUserInputArea filed to get user input
     */
    public FileChooseListener(final JComponent theParentComponent, 
                              final JTextArea theDisplayArea, 
                              final JTextField theUserInputArea) {
        myParentComponent = theParentComponent;
        myDisplayArea = theDisplayArea;
        myInputArea = theUserInputArea;
        myCurrentHomeDirectory = new File("./");
        myChooser = new JFileChooser();
        
        setupChooser();
    }
    
    /** A way to dial in this file chooser class. */
    private void setupChooser() {
        myChooser.setCurrentDirectory(myCurrentHomeDirectory);
        //System.out.println(myChooser.getCurrentDirectory().getAbsolutePath());
    }

    @Override
    public void actionPerformed(final ActionEvent arg0) {
                
//        final FileNameExtensionFilter filter =
//                        new FileNameExtensionFilter("Text Files Only", "txt");
//        myChooser.setFileFilter(filter);
        final int returnVal = myChooser.showOpenDialog(myParentComponent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            myCurrentChosenFile = myChooser.getSelectedFile();
            myInputArea.setEnabled(true);
            myDisplayArea.append(getProceedMsg());
         // System.out.println("You chose to open this file: "
                       // + myChooser.getSelectedFile().getName());
         // System.out.println("current chosen file -> " + getMyCurrentChosenFile().getName());
        }
    }
    
    /** 
     * A way to return the current selected file.
     * 
     * @return File currently chosen
     */
    public File getMyCurrentChosenFile() {
        return myCurrentChosenFile;
    }
    
    /**
     * A way to print the chosen file's directory path.
     * 
     * @return String chosen file path
     */
    public String getChosenFileDirectoryStatement() {
        return "The chosen file is at: " + myCurrentChosenFile.getAbsolutePath() + NEW_LINE;
    }
    
    /**
     * A way to print the current home directory.
     * 
     * @return String home directory path
     */
    public String getCurrentHomeDirectory() {
        return "The current home directory is: " 
                        + myCurrentHomeDirectory.getAbsolutePath() + NEW_LINE;
    }
    
    /**
     * A way to return the name of the current chosen file.
     * 
     * @return String chosen file name
     */
    public String getReturnChoiceStatement() {
        return "You chose to open this file: "
                        + myChooser.getSelectedFile().getName() + NEW_LINE;
    }
    
    /**
     * A way to return a message to relay further instructions to the user.
     * 
     * @return String message for user to proceed
     */
    public String getProceedMsg() {
        final StringBuilder sb = new StringBuilder(128);
        sb.append(getReturnChoiceStatement());
        sb.append(getChosenFileDirectoryStatement());
        sb.append("In the field below, please enter the number of most used words "
                        + "in this file you would like returned");
        sb.append(NEW_LINE);
        
        return sb.toString();
    }

    // end of FileChooserListener class
}
