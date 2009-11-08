package net.crystalrudiments.wordbot;
/** 
 * WordBotConstants.java
 *
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, December 6, 2001
 */
public interface WordBotConstants
{
	static final String[] SINGLES =
	{
		"exit","how","what","where",		/*#0001-#0003*/
		"when","who",				/*04-05*/
		"which","has","an",			/*06-08*/
		"this","that","it","the", 	/*09-0C*/
		"a","on","of","at","with", 	/*0D-11*/
		"is","are","do",			/*12-14*/
		"can","does", 				/*15-16*/
		"will","did",				/*17-18*/
		"would","could","should", 	/*19-1B*/
		"I","you","we","us",		/*1C-1F*/
		"them","they","those",		/*20-22*/
		"some","most","these",		/*23-25*/
		"he","she","me",			/*26-28*/
		"under","above","around","in",		/*29-2C*/
		"good","bad","fine","okay",			/*2D-30*/
		"ugly","pretty","cute","hot",		/*31-34*/
		"cold","temperature","mild","warm",	/*35-38*/
		"happy","angry","mad","glad",		/*39-3C*/
		"pissed","sad","depressed","lonely",/*3D-40*/
		"go","went","have","had",			/*41-44*/
		"see","hear","smell","taste",		/*45-48*/
		"touch","feel","eat","sleep",		/*49-4C*/
		"time","weather",
		"look","read","type","talk",
		"speak","print","work","play",
		"looked","typed","talked",
		"spoken","printed","worked","played",
		"leave","return",
		"left","right",
		"wrong","correct",
		"incorrect","affirmative",
		"negative","positive",
		
		"plus","times","minus",
		"one","two","three","four","five",
		"six","seven","eight","nine","ten",
		"eleven","twelve","thirteen","fourteen","fifteen",
		"sixteen","seventeen","eighteen","nineteen","twenty",
		"thirty","forty","fifty","sixty","seventy","eighty",
		"ninety","hundred","thousand","million","billion","trillion",
		"logarithm","factorial","multiplied","sum","product",
		"sine","cosine",
		"pi"
	};
	/** 
	* This structure holds groups of words which are synonyms.
	* Allows for variable sized array blocks. 
	*/
	static final String[][] WORDS =
	{
		{"hello","hi"},		//#1000
		{"bye","good-bye"},	//1
		{"cya","see ya","ttyl"},	//2
		{"isn't","is not"},		//3
		{"aren't","are not"},	//4
		{"don't","do not"},	//5
		{"can't","can not"},	//6
		{"doesn't","does not"}	//7
		
	};
	/**
	* This structure holds the responses. The first half of each line
	* is the inputed syntax. The second half is a list of possible responses
	* to that syntax.
	*/
	static final String[][][] RES =
	{
		{{"1001 ?"},{"yeah, i'm here","yes?"}},
		{{"1001 ."},{"hello", "hey", "how's it going?"}},
		{{"1002 ."},{"bye","adios","hasta luego"}}
	};
	
}//WordBotConstants