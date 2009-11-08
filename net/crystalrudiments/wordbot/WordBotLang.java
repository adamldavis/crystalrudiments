package net.crystalrudiments.wordbot;
/** 
 * WordBotLang.java 
 *
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, December 15, 2001
 */
public interface WordBotLang
{
	static final String[] SINGLES =
	{
		"exit","how","what","where",
		"when","who","which","has",
		"an","this","that","it",
		"the","a","on","of",
		"at","with","is","are",
		"do","can","does","will",
		"did","would","could","should",
		"i","you","we","us",
		"them","they","those","some",
		"most","these","he","she",
		"me","under","above","around",
		"in","good","bad","fine",
		"okay","ugly","pretty","cute",
		"hot","cold","temperature","mild",
		"warm","happy","glad","sad",
		"depressed","lonely","go","went",
		"have","had","see","hear",
		"smell","taste","touch","feel",
		"eat","sleep","time","weather",
		"look","read","type","talk",
		"speak","print","work","play",
		"looked","typed","talked","spoken",
		"printed","worked","played","leave",
		"return","left","right","wrong",
		"correct","incorrect","affirmative","negative",
		"positive","plus","times","minus",
		"one","two","three","four",
		"five","six","seven","eight",
		"nine","ten","eleven","twelve",
		"thirteen","fourteen","fifteen","sixteen",
		"seventeen","eighteen","nineteen","twenty",
		"thirty","forty","fifty","sixty",
		"seventy","eighty","ninety","hundred",
		"thousand","million","billion","trillion",
		"logarithm","factorial","multiplied","sum",
		"product","sine","cosine","pi",
		"exactly","clock","o'clock","no",
		"idea","why","tell","your",
		"name","sorry","not","telling",
		"like","sucks","inside","know",
		"from","home","might","stupid",
		"big","dork"
	};
	/** 
	* This structure holds groups of wvec which are synonyms.
	* Allows for variable sized array blocks. 
	*/
	static final String[][] WORDS =
	{
		{"hello","hi"},
		{"bye","good-bye"},
		{"cya","see ya","ttyl"},
		{"isn't","is not"},
		{"aren't","are not"},
		{"don't","do not"},
		{"can't","can not"},
		{"doesn't","does not"},
		{"what's","whats","what is"},
		{"hasn't","has not"},
		{"haven't","have not"},
		{"weren't","were not"},
		{"i'm","i am"},
		{"angry","mad","pissed","pissed off"},
		{"stupid","retarded","moronic","itiodic"},
		{"insane","crazy","loony","screwy"},
		{"abnormal","different","odd","strange","weird"},
		{"suck","blow"},
		{"dork","geek","nerd","loser"},
		{"freak","psycho","psychopath","weirdo"},
		{"how'd","how would"},
		{"you're","you are"},
		{"they're","they are"},
		{"we're","we are"}
	};
	/** 
	* This structure holds the responses. The first half of each line
	* is the inputed syntax. The second half is a list of possible 
	* responses to that syntax.
	*/
	static final String[][][] RES =
	{
		{{"1000 ?"},{"yeah, i'm here", "yes?"}},
		{{"1000 ."},{"hello", "hey", "how's it going?"}},
		{{"1000 ."},{"bye", "adios", "hasta luego"}},
		{{"2 4D 12 B ?", "1007 C 4D ?", "14 1D A2 2 4D B 12 ?"},{"it's _ o'clock.", "tell me more of this, 'time'", "time flies, doesn't it?"}},
		{{"1007 9A 9B ?", "14 1D 43 D 9B .", "99 28 9A 9B ."},{"i don't have a name", "you can call me Al...he he", "names are stupid"}},
		{{"3 13 1D A3 ?", "3 12 9A A4 ?", "14 1D 43 D A4 ?"},{"i live in the computer, stupid", "i'm from 1010111001110010.", "home rhymes with gnome!"}},
		{{"A5 A6 .", "1D A0 !", "A5 D A7 A8 .", "A5 D A8 ."},{"i know you are but what am i", "no i'm not", "shut up", " i don't have to take this you know.", "hmmm...do you really have to insult me?"}}
	};
}//WordBotLang	
