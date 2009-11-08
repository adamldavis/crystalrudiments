/**
 * Yoda
 * Entry for Artifical Intelligence N.V. (Ai) - Challenge:
 * A program that is supposed to learn how to play a game without
 * knowing the rules.
 *
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.0 Sept. 7, 2001
 */

package net.crystalrudiments.yoda;

import net.crystalrudiments.io.InOutHelp;

class Yoda
{
	/** bDEBUG is true if debugging. */
	private static final boolean bDEBUG = true;
	
	/** Channel: Input (string is optional)*/
	private static final String INPUT = "@input ";
	/** Channel: Output (string is optional)*/
	private static final String OUTPUT = "@output ";
	/** Channel: Score */
	private static final String SCORE = "@score ";
	/** Channel: Command */
	private static final String COMMAND = "@command ";
	private static final String PLAY = "play";
	private static final String NEW = "new";
	/** Channel: Info */
	private static final String INFO = "@info ";
	
	private static final String NAME = "name Yoda";
	private static final String EXIT = "exit";
	private static final String SYMBOL = "symbol ";
	private static final String BAD_INPUT = "Error: Bad Input";
	
	private static final String HELLO = 
		"#Yoda: 2001, by Adam Davis\n" +
		"#Ready";
		
	/*--------------------------------*/
	private YodaBrain yb;
	
	private boolean playing=false;
	/**
	* Default Constructor
	*/
	public Yoda()
	{
		info(NAME);
	}
	/** 
	* Loops on input.
	*/
	private void start()
	{
		String str="";
		InOutHelp ioh = new InOutHelp();
		do {
			str = ioh.getInputLine();
			if (str.startsWith("#")); //comment
			else if (str.startsWith(COMMAND)) giveCommand(str);
			else if (str.startsWith(INPUT)) giveInput(str);
			else if (str.indexOf(' ')==-1) giveInput(str); //default channel.
			else if (str.startsWith(SCORE)) giveScore(str);
			else {
				error(BAD_INPUT + ":start");
			}
		} while(true);//do
	}
	/**
	* Purpose: Deals with commands.
	*/
	private void giveCommand(String str)
	{
		str = str.substring(COMMAND.length());
		
		if (str.equals(EXIT)) stop();
		else if (str.equals(PLAY)) play();
		else if (str.equals(NEW)) startNew();
		else if (str.startsWith(SYMBOL)) addSymbol(str);
		else {
			error(BAD_INPUT + ":giveCommand");
		}
	}
	/**
	* @command new: Starts new game.
	*/
	private void startNew() 
	{
		yb = new YodaBrain();
	}
	/**
	* @command play: Gives one output.
	*/
	private void play() {
		output(yb.play());
	}
	/**
	* @command symbol *: Adds a symbol.
	*/
	private void addSymbol(String str) 
	{
		str = str.substring(SYMBOL.length());
		if (yb==null) error("yb not initialized!");
		else yb.addSymbol(str);
	}
	/**
	* @command stop <BR>
	* Purpose: Ends this program.
	*/
	private void stop()
	{
		info( EXIT );
		System.exit(0);
	}
	/**
	* Deals with input.
	*/
	private void giveInput(String str)
	{
		if (str.startsWith(INPUT)) str = str.substring(INPUT.length());
		yb.giveInput(str);
	}
	/**
	* Deals with score.
	*/
	private void giveScore(String str)
	{
		str = str.substring(SCORE.length());
		double d;
		try {
		    d = Double.parseDouble(str);
		} 
		catch(Exception e) {
			error("Error reading double");
			d = 0.00;
		}
		yb.giveScore(d);
	}
	
	/**
	* Self-made print function for output channel.
	*/
	public void output(String str)
	{
		System.out.println(OUTPUT + str);
	}
	/**
	* Self-made print function for info channel.
	*/
	public void info(String str)
	{
		System.out.println(INFO + str);
	}
	/**
	* Gives error messages.*/
	public static void error(String str)
	{	
		System.out.println("#-->" + str); 
	}
	/**
	* Main method. Run by java compiler.
	*/
	public static void main(String args[])
	{
		Yoda y = new Yoda();
		System.out.println(HELLO);
		y.start();
	}//main
	
}//Yoda
