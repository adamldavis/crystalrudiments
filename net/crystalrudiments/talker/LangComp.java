/**
 * LangComp.java
 * This automates the process of creating data structures for WordBot 
 * which imitate an artificial intelligence. This program is an intermediate
 * step from compiler to program. (i.e. this program makes an interface which
 * is used by the main java code file, which is then compiled to make the final
 * product.)
 *
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, December 13, 2001
 */
package net.crystalrudiments.talker;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;

import net.crystalrudiments.io.InOutHelp;
import net.crystalrudiments.wordbot.WordBot;
import net.crystalrudiments.wordbot.WordBotLang;


public class LangComp extends WordBot implements WordBotLang
{
	public static final String HELP_MSG =
		"***LangComp.java*** A Program for writing conversation bots.\n"+
		"(Press 'q' at any time to back out)\n"+
		"\t- Print _S_tats  \n"+
		"\t- _A_dding Mode \n"+
		"\t- Syno_N_ym Mode \n"+
		"\t- _H_elp		\n"+
		"\t- _Q_uit  \n";
	public static final String FNAME = "WordBotLang";
	public static final String[] TABS = 
	{
		"\t","\t\t","\t\t\t","\t\t\t\t","\t\t\t\t\t","\t\t\t\t\t\t"
	};
	Vector wvec = null; //Vector of Vectors
	Vector sing = null;
	Vector[] res = new Vector[2]; //2 Vectors of Vectors
	PrintWriter pw = null;
	InOutHelp ioh = null;
	
	public LangComp()
	{
		int i,j;
		wvec = new Vector();
		sing = new Vector();
		res[0] = new Vector(); //these hold Vectors
		res[1] = new Vector(); //....
		
		for (i=0; i<WORDS.length; i++) 
		{
			Vector tmp = new Vector();
			for (j=0; j<WORDS[i].length; j++) tmp.add(WORDS[i][j]);
			wvec.add(tmp);
		}
		for (i=0; i<SINGLES.length; i++) sing.add(SINGLES[i]);
		for (i=0; i<RES.length; i++) 
		{
			Vector rtmp0 = new Vector();
			Vector rtmp1 = new Vector();
			for (j=0; j<RES[i][0].length; j++) rtmp0.add(RES[i][0][j]);
			for (j=0; j<RES[i][1].length; j++) rtmp1.add(RES[i][1][j]);
			res[0].add(rtmp0);
			res[1].add(rtmp1);
		}
		ioh= new InOutHelp();
		convertAllToString(); //translates everything into plain english.
	}
	public void saveToFile()
	{
		saveToFile(FNAME+".java");
	}
	public void saveToFile(String name)
	{
		convertAllToHex(); //converts everything to Hex code.
		pw = ioh.openPW( name );
		printHeader();
		fprint("public interface "+ FNAME +"\n{\n");
		printSingles();
		printWords();
		printRes();
		fprint("}//"+FNAME+"	\n");
		close();
	}
	private void convertAllToString()
	{
		int i,j;
		String prev,next;
		Vector temp;
		for (i=0; i<res[0].size(); i++)
		{
			temp = (Vector) res[0].get(i);
			for (j=0; j<temp.size(); j++)
			{
				next = hexToStr((String)temp.get(j));
				prev = (String) temp.set(j, next);
			}//j
		}//i
	}
	/**
	* This method looks at every String in RES and attempts to replace each
	* word with a Hex value refering to an existing word in the "dictionary".
	* If this fails then it adds a new "single" word and uses that new Hex
	* value.
	*/
	private void convertAllToHex()
	{
		int i,j;
		String prev,next;
		Vector temp;
		for (i=0; i<res[0].size(); i++)
		{
			temp = (Vector) res[0].get(i);
			for (j=0; j<temp.size(); j++)
			{
				next = strToHex((String)temp.get(j));
				prev = (String) temp.set(j, next);
			}//j
		}//i
	}
	
	private void printHeader()
	{
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
		String mydate = df.format(new Date());
		
		fprint("/** \n"+
		" * "+ FNAME +".java \n *\n"+
		" * @version Version 1.0, "+ mydate +"\n"+
		" */\n");
	}
	private void printSingles()
	{
		int i,n=5;
		fprint("\tstatic final String[] SINGLES =\n\t{");
		for (i=0; i<sing.size()-1; i++, n++)
		{
			if (n > 3) { n=0; fprint("\n"+TABS[1]); }
			fprint("\"" + sing.get(i) + "\",");
		}
		if (n > 3) { n=0; fprint("\n"+TABS[1]); }
		fprint("\"" + sing.get(i) + "\"\n");
		fprint("\t};\n");
	}
	private void printWords()
	{
		int i,j;
		Vector wv;
		fprint("\t/** \n"+
		"\t* This structure holds groups of wvec which are synonyms.\n"+
		"\t* Allows for variable sized array blocks. \n"+
		"\t*/\n");
		fprint("\tstatic final String[][] WORDS =\n\t{\n");
		for (i=0; i<wvec.size(); i++)
		{
			wv =(Vector) wvec.get(i);
			fprint(TABS[1] + "{");
			for (j=0; j<wv.size()-1; j++) 
			{
				fprint("\""+ wv.get(j) +"\",");
			}
			if (i==wvec.size()-1) fprint("\""+ wv.get(j) +"\"}\n");
			else fprint("\""+ wv.get(j) +"\"},\n");
		}
		fprint("\t};\n");
	}
	private void printRes()
	{
		int i,j;
		Vector[] tv = new Vector[2];
		fprint(TABS[0]+"/** \n"+
		"\t* This structure holds the responses. The first half of each line\n"+
		"\t* is the inputed syntax. The second half is a list of possible \n"+
		"\t* responses to that syntax.\n"+
		"\t*/\n");
		fprint("\tstatic final String[][][] RES =\n\t{\n");
		for (i=0; i<res[0].size(); i++)
		{
			tv[0] =(Vector) res[0].get(i);
			tv[1] =(Vector) res[1].get(i);
			fprint(TABS[1] + "{{");
			for (j=0; j<tv[0].size(); j++) 
			{
				if (j<tv[0].size()-1) fprint("\""+ tv[0].get(j) +"\", ");
				else fprint("\""+ tv[0].get(j) +"\"");
			}
			fprint("},{");
			for (j=0; j<tv[1].size()-1; j++) 
			{
				fprint("\""+ tv[1].get(j) +"\", ");
			}
			if (i==res[0].size()-1) fprint("\""+ tv[1].get(j) +"\"}}\n");
			else fprint("\""+ tv[1].get(j) +"\"}},\n");
		}
		fprint("\t};\n");
	}
	
	private void fprint(String str){
		ioh.fprintf(pw, str);
		System.out.print(str);
	}
	private void close(){
		ioh.close(pw);
	}
	public String stats()
	{
		String ret = new String();
		ret += "Size: Singles Vector = \t\t" + sing.size() +"\n";
		ret += "Size: Words Vector =   \t\t" + wvec.size() +"\n";
		ret += "Size: Response Vector =\t\t" + res[0].size() +"\n";
		
		return ret;
	}
	public void addMode()
	{
		String user="";
		String comp="";
		
		while (!comp.equals("q"))
		{
			System.out.print("Write lists seperated by semi-colons.\n");
			System.out.print("user>");
			user = ioh.getInputLine();
			if (user.equals("q")) break;
			System.out.print("COMP>");
			comp = ioh.getInputLine();
			if (comp.equals("q")) System.err.println("Invalid input");
			else {
				addRes(user, comp);
			}
		}
	}
	private void addRes(String user, String comp)
	{
		Vector tmp0 = strToList(user, ";");
		Vector tmp1 = strToList(comp, ";");
		res[0].add(tmp0);
		res[1].add(tmp1);
	}
	/**
	* Finds the string refered to by the hex value and returns it.
	*/
	private String hexToStr(String s)
	{
		String ret="";
		int i,j,n;
		StringTokenizer strtok = new StringTokenizer(s, " ?!."); //handle ?!. seperately...
		while ( strtok.hasMoreTokens() )
		{
			String token = strtok.nextToken();
			int num = hexToInt(token);
			if (num>=WORDS_OFFSET) {
				ret += (String) ((Vector)wvec.get(num-WORDS_OFFSET)).get(0);
			}
			else {
				ret += (String) sing.get(num);
			}
			ret +=" ";
		}
		char c = s.charAt(s.length()-1);
		if (c=='!'||c=='?'||c=='.') ret += String.valueOf(c);
		else ret += "."; /*Default*/
		return ret;
	}
	private String strToHex(String s)
	{
		String ret=new String();
		int i,j,n;
		StringTokenizer strtok = new StringTokenizer(s, " ,;:?!."); //handle ?!. seperately...
		//find best match for every word..
		//if no match, then add word to dictionary.
		//and add each "hex" to the input string..
		while ( strtok.hasMoreTokens() )
		{
			String token = strtok.nextToken();
			n = super.singles.findBestMatch(token);
			if (n==-1) {
				n = super.words.findBestMatch(token);
				if (n==-1) {
					//Couldn't find any matches!!!!
					sing.add(token);
					super.singles.addWord(token);
					n = super.singles.findBestMatch(token);
				}
				for (i=0,j=0; i<wvec.size(); i++) {
					j+=((Vector)wvec.get(i)).size();
					if (j>=n) n = i + WORDS_OFFSET;
				}
			}
			ret += numToHex(n) + " ";
		}
		char c = s.charAt(s.length()-1);
		if (c=='!'||c=='?'||c=='.') ret += String.valueOf(c);
		else ret += "."; /*Default*/
		return ret;
	}
	public void synMode()
	{
		String in="";
		Vector vec;
		boolean copy=false;
		int i,j;
		System.out.println("Write list seperated by commas or semi-colons.");
		while (!in.equals("q"))
		{
			copy =false;
			System.out.print("SYNONYMS>");
			in = ioh.getInputLine();
			if (in.equals("q")) break;
			vec = strToList(in, ",;");
			for (i=0; i<wvec.size(); i++) {
				for (j=0; j<vec.size(); j++) {
					Vector temp = (Vector)wvec.get(i);
					if ( temp.contains(vec.get(j)) ) {
						TreeSet ts = new TreeSet();
						ts.addAll(temp);
						ts.addAll(vec);
						vec = new Vector(ts);
						temp = (Vector)wvec.set(i, vec);
						System.out.println("Vector replaced::"+temp+"::-->::"+vec+"::");
						copy =true;
						break;
					}
				}
				if (copy) break;
			}
			for (j=0; j<vec.size(); j++) {
				if ( sing.contains(vec.get(j)) ) {
					boolean removed;
					removed = sing.remove(vec.get(j));
					if (removed)
						System.out.println("Word Removed::"+vec.get(j)+"::");
					else
						System.out.println("Couldn't Remove::"+vec.get(j)+"::");
				}
			}
			if (!copy) wvec.add(vec);
		}
	}//synMode
	private Vector strToList(String list, String delim)
	{
		Vector ret=new Vector();
		StringTokenizer strtok = new StringTokenizer(list, delim);
		
		while (strtok.hasMoreTokens()) {
			ret.add(strtok.nextToken());
		}
		return ret;
	}
			
	/** Main Method. */
	public static void main(String[] args)
	{
		LangComp c = new LangComp();
		InOutHelp ioh = new InOutHelp();
		String in="";
		
		System.out.print(HELP_MSG);
		while (!in.equals("q"))
		{
			System.out.print(">");
			in = ioh.getInputLine();
			if (in.length()==1) {
				char cc = in.charAt(0);
				switch(cc) {
					case 's': System.out.print("stats=\n"+c.stats()); break;
					case 'h': System.out.print(HELP_MSG); break;
					case 'a': c.addMode(); break;
					case 'n': c.synMode(); break;
					case 'q': break;
					default: System.err.println("Please type more than 1 character");
				}
			}
			else if (in.equals("")); /*don't do nothin'*/
			else {
				System.err.println("Please try again.");
				System.out.print(HELP_MSG);
			}
		}
		c.saveToFile();
	}
} //LangComp.java