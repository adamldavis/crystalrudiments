/**
 * Talker.java
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, September 24, 2001
 */
package net.crystalrudiments.talker;

import java.io.BufferedReader;
import java.io.PrintWriter;

import net.crystalrudiments.io.InOutHelp;
import net.crystalrudiments.lang.AdamObject;


class Talker extends AdamObject implements TalkerConstants
{
	/** Debug, true if debugging.*/
	private static final boolean bDEBUG = true;
	
	/** InOutHelp, does file in/out. */
	private InOutHelp ioh;
	
	/** TalkerBrain, the brains of the outfit.*/
	private TalkerBrain tb;
	
	/**
     *
     * Default constructor.
     *
     */
	public Talker() 
	{
		this( FWORDS, FSYNTAX, FMEMORY );
	}
	/**
	 * Constructor which uses text files to initialize database.
	 * Files: words, syntax, & memory.
	 *
	 */
	public Talker(String fwords, String fsyntax, String fmemory)
	{
		BufferedReader br;
		
		if (bDEBUG) print("In Talker()\n");
		ioh = new InOutHelp(); //initialize ioh.
		tb = new TalkerBrain();
		
		br = ioh.openBR(fwords);
		if (br!=null) {
			ioh.close(br);
		}
		
		br = ioh.openBR(fsyntax);
		if (br!=null) {
			ioh.close(br);
		}
		
		br = ioh.openBR(fmemory);
		if (br!=null) {
			ioh.close(br);
		}
	}
	
	public void saveFiles(String fwords, String fsyntax, String fmemory)
	{
		PrintWriter pw= null;
		
		pw = ioh.openPW(fwords);
		if (pw!=null) {
			ioh.close(pw);
		}
		
		pw = ioh.openPW(fsyntax);
		if (pw!=null) {
			ioh.close(pw);
		}
		
		pw = ioh.openPW(fmemory);
		if (pw!=null) {
			ioh.close(pw);
		}
	}
	
	public void start()
	{
		String str;
		
		print(">"); //prompt user.
		
		while ((str = ioh.getInputLine()) != null)
		{
			if (str.length()>0 && str.charAt(0)=='@') 
			{
				if (str.equals("@exit")) exit();
				else if (str.equals("@help")) print(toString());
				else if (str.equals("@debug")) tb.debugMessage();
				else if (str.equals("@stats")) tb.printStats();
				else if (str.indexOf("@s")==0) {
					try {
						String snum = str.substring(3);
						tb.inputScore( Integer.parseInt(snum) );
					} catch (Exception e) {
						print("Syntax Error: @s <integer>");
					}
				}//if
			}
			else
			{
				tb.input(str);
			}
			print(">");
		}//while
	}//start()
	
	/** 
	* ToString method.*/
	public String toString()
	{
		return ( MY_INFO );
	}
	/** 
	* Main method.
	*/
	public static void main(String[] args)
	{
		Talker t = new Talker();
		
		t.print(t.toString());
		t.start();
		
	}
}//Talker