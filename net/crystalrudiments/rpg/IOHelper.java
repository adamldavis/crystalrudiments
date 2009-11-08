/* Revisions:
 * 3/28/01 - Version 1.1, by Adam. called InOutHelp.
 * 
 * 3/22/03 - revision 2. Changed name to SpamFilterIOHelper. included in
 * package SpamFilter. Changed some methods to static. Added method 
 * #getFileContents(String).
 * 
 * 6/17/03 - rev 3. Used for JMiedor.
 */
package net.crystalrudiments.rpg;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Class used for opening files for reading or writing and getting input from
 * the user.
 *
 *
 * @author <A HREF="http://www.adamldavis.com/">Adam L. Davis</A>
 * @version Version 1.3, June 17, 2003
 */
public class IOHelper {
	
    private static final boolean bDEBUG = true;
    /**
     * Reads from System.in.
     */
    private InputStreamReader inputer;
    /**
     * Buffered reader for user input.
     */
    private BufferedReader buffy;
    
    //may cause problems, check this static
    private static File file;

    public File getFile(){
	return file;
    }//getFile()

    public void setFile(File x){
        IOHelper.file = x;
    }//setFile

    /**
     * Default constructor.
     */
    public IOHelper() {
	inputer = new InputStreamReader(System.in);
	buffy = new BufferedReader(inputer);
    }
    
    /**
     *
     * Returns string input from user.
     *
     */
    public String getInputLine() {
	
	String line =null;
        try{
	    line = buffy.readLine();
        }
        catch(IOException e){
	    System.out.println(e + " in JMiedorIOHelper: getInputLine");
        }
        return line;
    }//end method
    
    /**
     *
     * Reads a line from file.
     *
     */
    public static String readLine(BufferedReader file) {
	String retstr = null;
	
	if (null == file) {
	    System.err.println("(Error) JMiedorIOHelper:" + 
			       " Tried to read line from null.");
	    return null;
	}
	try {
	    retstr = file.readLine();
	}
	catch (IOException ioe) {
	    System.out.println(ioe + " in JMiedorIOHelper: readLine");
	}
	return retstr;
    }
    
    /**
     * Open a file for Input, using BufferedReader.
     * <BR>
     * @param fname Name of file you want to open for read.
     * @param br The BufferedReader you want to use on that file.
     * @return null if opening failed, otherwise the BufferedReader.
     */
    public static BufferedReader open(String fname, BufferedReader br) {
	boolean worked = true;
	
	FileReader fr = null;
	br = null;
        
        //produce new filereader
        try{
	    fr = new FileReader(fname); 
        }
        catch(FileNotFoundException e){
	    worked = false;
	    System.err.println("Can't find file: " + fname);
	}
	if (worked) {
	    //produce buffered reader.
	    br = new BufferedReader(fr);
	}
	return br;
    }//openBR
    
    /**
     * Closes the given BufferedReader.
     * @param br the BufferedReader that you want to close.
     */
    public static void close(BufferedReader br) {
	if (br==null) return;
	try {
	    br.close(); //close file.
        }
        catch(IOException e) {
	    System.err.println("Error: Closing BR didn't work.");
        }//try
    }//closeBR
    
    /**
     * Opens file outputFile for output and returns a PrintWriter.
     * Returns false if it didn't work.
     * @param outputFile Name of file you want to open for write.
     * @param pw PrintWriter you want to use on that file.
     * @return null if it couldn't open file.
     */
    public static PrintWriter open(String outputFile, PrintWriter pw) {
	boolean worked = true;
	file = new File(outputFile);

	FileWriter fw = null;
        pw = null;
        
	try {
	    fw = new FileWriter(file);
	}
	catch(IOException e) {
	    worked = false;
	    System.out.println("-->FileWriter: Couldn't find file.");
	}//try
	
	if (worked) pw = new PrintWriter(fw);
        
        return pw;
    }//openFR


    
    /**
     * Closes a file using the PrintWriter given.
     * @param pw the PrintWriter that you want to close.
     */
    public static void close(PrintWriter pw) {
	if (pw == null) return;
	try {
	    pw.close(); //close file.
        }
        catch(Exception e) {
	    System.err.println("Error: Closing Pw didn't work.");
        }//try
    }//closePW


    /**
     * Gets the contents of a file. 
     * @param fileName String fileName to get contents of.
     * @return The contents of the given file as a String.
     */
    public static String getFileContents(String fileName) {
	BufferedReader br = null;
	String sRet = "";
	String sRead = "";

	br = open(fileName, br);

	if (null == br) {
	    //this means that file didn't exist or error.
	    System.err.println("Error loading file.");
	}
	else {
	    do {
		sRead = readLine(br);
		if (null != sRead) sRet += " " + sRead;
	    } while (sRead != null);

	    close( br );
	    return sRet;
	}
	return "";
    }

}
