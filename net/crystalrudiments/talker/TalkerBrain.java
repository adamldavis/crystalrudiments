/**
 * TalkerBrain.java
 * Created: October 21, 2001
 *
 * Revisions:
 * 2001/11/05 - Added Hashtable for words and implemented handling input.
 *
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0.0001, November 5, 2001
 */
package net.crystalrudiments.talker;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import net.crystalrudiments.io.InOutHelp;
import net.crystalrudiments.lang.AdamObject;
import net.crystalrudiments.util.VectorTree;


public class TalkerBrain extends AdamObject implements TalkerConstants
{
	/** Debug, true if debugging.*/
	public int debug = 3;
	
	private static final int DEBUG_MAX = 3;
	
	/** Hashtable of words.*/
	private Hashtable hashWords;
	
	/* --Implements grouping by putting vectors in vectors.---*/
	/** Object holding groups of words or groups of groups.*/
	private VectorTree words;
	
	/** Holds the rules for syntax, refering to word groups in "words".
	 * eg. "<1;0;10;> || <4;4;> <10;1;>"
	 */
	private VectorTree syntax;
	
	/** Holds the pure longe-term memory for this bot.*/
	private VectorTree memory;
	
	/** Keeps track of the number of searches.*/
	private int num_search;
	
	/**
     *
     * Default constructor.
     *
     */
	public TalkerBrain()
	{
		words = 	new VectorTree();
		syntax = 	new VectorTree();
		memory =  	new VectorTree();
		hashWords = new Hashtable();
	}
	
	public void debugMore() { if (debug<DEBUG_MAX) debug++;}
	public void debugLess() { if (debug>0) debug--;}
	public int getDebug() { return debug;}
	
	public void inputScore(int score)
	{
		
	}
	
	public String output()
	{
		String ret="<no output>";
		
		//do stuff...
		
		return ret;
	}
	/**
	 * Handles the input from the user.
	 */
	public void input(String str)
	{
		handleInput(StringMan.lower(str));
	}
	private void handleInput(String str)
	{
		StringTokenizer strtok = new StringTokenizer(str);
		ArrayList arr = new ArrayList( strtok.countTokens() );
		Vector mem = new Vector();
		String s;
		int i;
		
		memory.add(mem); //Adds this new Vector to memory
		
		for (i=0; strtok.hasMoreTokens(); i++)
		{
			s = strtok.nextToken();
			if (!hashWords.containsKey(s)) {
				arr.add(s);
				mem.add(s); //add string to memory regardless.
				hashWords.put(s, new StringChunk(s));
			} else {
				if (debug>0) if (memory.get(s)==null) error("HOLY CRAP!!!");
				mem.add(memory.get(s)); //adds a pointer to the existing string.
			}
		}
		/* now memory should have the sentence stored in the form of "mem"*/
		//use mem to do syntax & words.
		for (i=0; i<mem.size(); i++)
		{
			/*try and find a syntax already existing that has any of the
			 * strings we have.*/
			String syn;
			if ( null != (syn = (String) syntax.get(mem.get(i))) ) {
				
			}
		}
		
		mem.trimToSize(); //b/c this Vector will never again be altered.
	}
	
	
	/**
	 * The debug message should go through and 
	 * give errors if there are any problems.
	 */
	public void debugMessage()
	{
		//Print out debug Message..
		int num;
		Stack os = new Stack();
		Object o;
		String name="NA";
		
		for (num=0; num<4; num++) 
		{
			name = NAMES[num];
			switch(num) {
				case 0: os.push(words.getHeadVector());	break; 
				case 1: os.push(syntax.getHeadVector()); break; 
				case 2: os.push(memory.getHeadVector()); break; 
				case 3: os.push(new Vector(hashWords.values()) ); break;
				default:
			}
			while (!os.empty()) 
			{
				o = os.pop();
				if (o instanceof Vector) {
					for (int i=0; i<((Vector)o).size(); i++)
						os.push(((Vector)o).elementAt(i));
				}
				else if (o instanceof String) print(o+",");
				else if (o instanceof StringChunk) print(o+",");
				else if (o instanceof AdamObject) print(o+",");
				else error(name+" is corrupt::o="+o);
			}//while
			print("\n");
		}
	}
	/**
	 * This method should print how many leafs there are
	 * on each tree and give a general overview of the
	 * size and shape of each VectorTree.
	 */
	public void printStats()
	{
		//print out the statistics...
		int[] vecs = {0,0,0};
		int[] strs = {0,0,0};
		int num;
		Stack os = new Stack();
		Object o;
		for (num=0; num<3; num++) 
		{
			switch(num) {
				case 0: os.push(words.getHeadVector()); break;
				case 1: os.push(syntax.getHeadVector()); break;
				case 2: os.push(memory.getHeadVector()); break;
				default:
			}
			while (!os.empty()) 
			{
				o = os.pop();
				if (o instanceof Vector) {
					vecs[num]++;
					for (int i=0; i<((Vector)o).size(); i++)
						os.push(((Vector)o).elementAt(i));
				} 
				else if (num==0 && (o instanceof StringChunk))
				{
					strs[num]++;
				} 
				else if (num==1 && (o instanceof String))
				{
					strs[num]++;
				}
				else if (num==2 && (o instanceof String))
				{
					strs[num]++;
				}
				else
					error("Wrong data-type::<"+NAMES[num]+">");
			}//while
		}
		print("<words> has "+vecs[0]+" Vectors and "+strs[0]+" StringChunks.\n");
		print("<syntax> has "+vecs[1]+" Vectors and "+strs[1]+" Strings.\n");
		print("<memory> has "+vecs[2]+" Vectors and "+strs[2]+" Strings.\n");
		print("<hashWords> has "+hashWords.size()+" Strings.\n");
	}

	/**
	 * Testing main.
	 */
	public static void main(String[] args)
	{
		TalkerBrain tb = new TalkerBrain();
		InOutHelp ioh = new InOutHelp();
		String in="";
		print("p=printStats\nd=debug\no=organize\n!=exit\n"
			+ "m=give more debug output\nl=give less debug-ouput\n"
			+ "?=gives value of debug\n");
		while (!in.equals("!"))
		{
			print(">");
			in = ioh.getInputLine();
			if (in.length()==1) {
				char c = in.charAt(0);
				switch(c) {
					case 'p': tb.printStats(); break;
					case 'd': tb.debugMessage(); break;
					case 'o': //tb.organizeWords(); 
						break;
					case 'm': tb.debugMore(); break;
					case 'l': tb.debugLess(); break;
					case '?': print("debug="+tb.getDebug()+"\n"); break;
					case '!': break;
					default: error("Please type more than 1 character");
				}
			}
			else if (in.equals("")); /*don't do nothin'*/
			else tb.input(in);
		}
	}
}//TalkerBrain