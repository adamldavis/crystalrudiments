
package net.crystalrudiments.talker;
/**
 * 
 * TalkerConstants
 */
public interface TalkerConstants
{
	static final String MY_INFO =
	"Talker: Version 1.0\n" +
	"written by Adam L. Davis\n" +
	"A learning program meant to be taught a language\n"+
	"and converse with the user.\n" +
	"\tCommand: \tMeaning:\n"+
	"\t@help \t\tGives this message\n"+
	"\t@debug\t\tPrints out debug message\n"+
	"\t@stats\t\tPrints out stats\n"+
	"\t@s <integer>\tMethod by which to score the program\n"+
	"(how the program learns).\n"+
	"\t@exit \t\tEnds the program\n";
	
	static final String[] NAMES = { "words","syntax","memory","hashWords" };
	
	static final int STACK_MAX = 256;
	
	static final int VECTOR_SIZE = 16;
	static final int VECTOR_INT = 0;
	
	/* File for words.*/
	static final String FWORDS = "words.txt";
	
	/* File for syntax.*/
	static final String FSYNTAX = "syntax.txt";
	
	/* File for memory.*/
	static final String FMEMORY = "memory.txt";
	
	/* MAX int.*/
	static final int MAX_INT = 2147483647; /*((2^32) - 1)*/
}