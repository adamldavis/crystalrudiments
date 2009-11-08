
package net.crystalrudiments.wordbot;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;

import net.crystalrudiments.io.InOutHelp;
import net.crystalrudiments.lang.AdamObject;

/**
* WordBot.java
* 	
* 
* @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
* @version Version 1.0, December 6, 2001
*/
public class WordBot extends AdamObject implements WordBotLang
{
	protected static final int WORDS_OFFSET = 4096;
	
	protected Worder singles;
	protected Worder words;
	private Worder res;
	private Vector wvect;
	private Vector svect;
	private Vector rvect;
	
	/** Keeps track of what responses have been used.*/
	private boolean[][] resUsed;
	
	private String strInput;
	
	/** 
	* Default Constructor that does initializing. 
	* Initializes all of the Worder's, Vector's and others.
	*/
	public WordBot()
	{
		int i,j;
		wvect = new Vector();
		rvect = new Vector();
		svect = new Vector();
		resUsed = new boolean[RES.length][];
		
		for (i=0; i<WORDS.length; i++) 
		{
			for (j=0; j<WORDS[i].length; j++) wvect.add(WORDS[i][j]);
		}
		for (i=0; i<RES.length; i++) 
		{
			resUsed[i] = new boolean[RES[i][1].length];
			for (j=0; j<RES[i][1].length; j++) resUsed[i][j]=false;
			for (j=0; j<RES[i][0].length; j++) rvect.add(RES[i][0][j]);
		}
		words = new Worder(wvect);
		for (i=0; i<SINGLES.length; i++) svect.add(SINGLES[i]);
		singles = new Worder(svect);
	}
	public void input(String s)
	{
		int i,j,n;
		StringTokenizer strtok = new StringTokenizer(s, " ,;:?!."); //handle ?!. seperately...
		//init strInput...
		strInput = new String();
		//find best match for every word..
		//and add each "hex" to the input string..
		while ( strtok.hasMoreTokens() )
		{
			String token = strtok.nextToken();
			n = singles.findBestMatch(token);
			if (n==-1) {
				n = words.findBestMatch(token);
				if (n==-1) {
					//Couldn't find any matches!!!!
					strInput += new String(token) + " ";
					continue;
				}
				for (i=0,j=0; i<WORDS.length; i++) {
					j+=WORDS[i].length;
					if (j>=n) n = i + WORDS_OFFSET;
				}
			}
			strInput += numToHex(n) + " ";
		}
		char c = s.charAt(s.length()-1);
		if (c=='!'||c=='?'||c=='.') strInput += String.valueOf(c);
		else strInput += "."; /*Default*/
		return;
	}
	public String output()
	{
		String ret = new String();
		return ret;
	}
	public String stats()
	{
		int i;
		TreeSet ts = new TreeSet();
		String ret = new String();
		ret += "Size: Singles Vector = " + svect.size() +"\n";
		ret += "Size: Words Vector = " + wvect.size() +"\n";
		for (i=0; i<svect.size(); i++){
			if ( ts.contains(svect.get(i)) ) {
				ret += "Duplicate: " + svect.get(i) +" @ s"+ i;
			}
			else ts.add(svect.get(i));
		}
		for (i=0; i<wvect.size(); i++){
			if ( ts.contains(wvect.get(i)) ) {
				ret += "Duplicate: " + wvect.get(i) +" @ w"+ i;
			}
			else ts.add(wvect.get(i));
		}
		ret += "Total number of Words = " + ( svect.size() + wvect.size() ) +"\n";
		ret += "Number of Unique Words = " + ts.size() +"\n";
		return ret;
	}
	public String debugMessage()
	{
		String ret = strInput + "\n";
		return ret;
	}
	/** Converts an int to a String hexidecimal.*/
	protected String numToHex(int num)
	{
		String ret = "";
		int i;
		if (num<16) {
			switch(num) {
				case 15: return "F";
				case 14: return "E";
				case 13: return "D";
				case 12: return "C";
				case 11: return "B";
				case 10: return "A";
				default: return String.valueOf(num);
			}
		}
		for (i=0; num!=0; i++)
		{
			ret = numToHex(num % 16) + ret;
			num = num/16;
		}
		return ret;
	}
	/** Converts an int to a String hexidecimal.*/
	protected int hexToInt(String str)
	{
		int ret =0;
		int i,j;
		if (str.length()==1) {
			switch(str.charAt(0)) {
				case 'F': return 15;
				case 'E': return 14;
				case 'D': return 13;
				case 'C': return 12;
				case 'B': return 11;
				case 'A': return 10;
				default: 
					try{
						ret = Integer.parseInt(str);
					} catch(Exception e) {
						System.err.println("Tried to convert string to Hex:\n"+e);
					}
					return ret;
			}
		}
		for (i=0; i<str.length(); i++)
		{ 
			int num=0;
			num = hexToInt(str.substring(i,i+1));
			for (j=0; j<(str.length()-i-1); j++) num *=16;	//10AC 0123
			ret +=num;
		}
		return ret;
	}
			
	public static void main(String[] args)
	{
		WordBot w = new WordBot();
		InOutHelp ioh = new InOutHelp();
		String in="";
		print("s=print_S_tats  \n"+"d=_D_ebug  message \n"+"q=_Q_uit  \n");
		while (!in.equals("q"))
		{
			print(">");
			in = ioh.getInputLine();
			if (in.length()==1) {
				char c = in.charAt(0);
				switch(c) {
					case 's': print("stats=\n"+w.stats()); break;
					case 'd': print("debug=\n"+w.debugMessage()); break;
					case 'q': break;
					default: error("Please type more than 1 character");
				}
			}
			else if (in.equals("")); /*don't do nothin'*/
			else {
				w.input(in);
				print(w.output());
			}
		}
	}//main
}//WordBot