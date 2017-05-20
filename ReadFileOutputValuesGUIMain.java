package programgui;

/**
 * Chad Chapman
 * CSS 342B Winter 2016
 * Assignment 2
 */

/**
 * A class for running the StackApps class of program.
 * Heavily borrows from StackDemoMain.
 * 
 * @author Alan Fowler
 * @author Chad Chapman
 * @version 14 Jan 2017
 *
 */
public final class ReadFileOutputValuesGUIMain {

    
    /**
     * Private constructor to inhibit instantiation.
     */
    private ReadFileOutputValuesGUIMain() {
    }

    /**
     * Simple graphical decimal to binary number converter.
     * 
     * @param theArgs array of Strings
     */
    public static void main(final String[] theArgs) {
        new ReadFileOutputValuesGUI().display();
    }
}
