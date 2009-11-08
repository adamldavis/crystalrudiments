
 
package net.crystalrudiments.lang;
import java.util.StringTokenizer;
/**
 * JavaC: Class for methods that are built in the C language.
 *
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0.0
 */
public class JavaC {
	
	/**
	 * String Tokenizer for method strtok.
	 */
	private StringTokenizer mytok;
	
	/**
	 * Default constructor.
	 */
	public JavaC() {
		mytok=null;
	}
	
	/**
	 * Tokenizes the given string.  The first call
	 * to this method will return the first token.
	 * Subsequent calls (with null as the first parameter)
	 * will return the subsequent tokens, using the beginning delimiter,
	 * or the second parameter if not null.
	 * <P>
	 * More than one delimiter can be specified. So you could code: 
	 * word = strtok(string, " \t;:,"). 
	 * 
	 * @param str1 String to tokenize, or null if you already gave a string.
	 * @param delim Delimeter string or null.
	 */
	public String strtok(String str1, String delim) 
	{
		String ret=null;
		
		if (str1 == null) //then mytok should have already been initialized.
		{
			if (mytok==null) {
				error("strtok: mytok should have been initialized already.");
			}
			if (mytok.hasMoreTokens()) ret = delim==null ? mytok.nextToken() : mytok.nextToken(delim);
			else ret = null;
		} 
		else {
			mytok = new StringTokenizer(str1, delim);
			if (mytok.hasMoreTokens()) ret = mytok.nextToken();
		}
		return ret;
	}//method
	
	/*---------------------------------------------------------------------------*/
	/**
	 * Returns {@link Math#random()}.
	 */
	public double drand48()
	{
		return(java.lang.Math.random());
	}
	/*---------------------------------------------------------------------------*/
	/**
	 * Uses Java's System out print to print the text.
	 * 
	 * @param txt Text to Print to Screen.
	 */
	public void printf(String txt)
	{
		System.out.print(txt);
	}
	/* Lesson in C:
	 \n........................ New line 
	\\........................ Backslash 
	\"........................ Quotation mark(") 
	\a........................ Alarm. Makes the PC-speaker beep. 
	\t........................ Tabulate. 
	
	The in-line reference's to variable's are: 
	
	%d........................ integer. 
	%f........................ floating, real. 
	%c........................ character. 
	%s........................ string. (Later!) 
	*/
	
	/**
	 * Replaces a <code>%d</code> character with the given int.
	 * <BR>
	 * @param txt Text to Print to Screen.
	 * @param num Int Parameter to print.
	 */
	public void printf(String txt, int num)
	{ 
		int place = txt.indexOf("%d");
		
		if (place==-1) 
			error("printf: No %d character" );
		else {
			txt = txt.substring(0, place) + String.valueOf(num) +
				txt.substring(place + 2);
			System.out.print(txt);
		}
	}
	public void printf(String txt, String str1)
	{ 
		int place = txt.indexOf("%s");
		
		if (place==-1) 
			error("printf: No %s character" );
		else {
			txt = txt.substring(0, place) + str1 +
				txt.substring(place + 2);
			System.out.print(txt);
		}
	}
	
	/*
	 * Method of Language C, searchs for any character in second string
	 * in the first string.
	 * @return Null if no match is found.
	 */
	public Character strpbrk(String str1, String chars) {
		//strpbrk(sentence[i], STOP)!=null)
		int i;
		Character letter=null;
		
		for (i=0; i<chars.length(); i++) {
			if (str1.indexOf(chars.charAt(i)) >=0)
				letter = new Character( chars.charAt(i) );
		}//for
		return letter;
	}//strpbrk
	
	/*
	 * Method of Language C, returns the length of the string.
	 *
	 */
	public int strlen(String str) {
		return str.length();
	}
	
	/*
	 * Method of Language C, returns a pointer to the beginning of 
	 * the sub-string or NULL if not found. 
	 *
	 */
	public String strstr(String str1, String str2) 
	{
		String ret=null;
		int num=0;
		num = str1.indexOf(str2);
		if (num>=0) ret = str1.substring(num);
		return ret;
	}
	/**
	 * strchr will find the first matching character in a string.
	 *
	 */
	public String strchr(String string, char character)
	{
		String ret=null;
		int num=0;
		num = string.indexOf(character);
		if (num>=0) ret = new String( string.substring(num) );
		return ret;
	}
	
	/*
	 * Method of Language C, returns s1.compareTo(s2).
	 * Returns less than zero if s1 less than s2,
	 *  greater than zero if s1 greater than s2,
	 *  and zero if s1 equals s2.
	 */
	public int strcmp(String s1, String s2) {
		return s1.compareTo(s2);
	}
	
	public int strcmp(char[] c1, char[] c2) {
		String s1 = new String(c1);
		String s2 = new String(c2);
		return s1.compareTo(s2);
	}
	
	/**
	 * @return 1 if char is a letter, 2 if digit, 0 if neither.
	 */
	public int isalpha(char c) 
	{
		if (Character.isLetterOrDigit(c)) return (2);
		if (Character.isLetter(c)) return (1);
		return (0);
	}
	
	/*---------------------------------------------------------------------------*/
	/**
	 *		Function:	lower
	 *
	 *		Purpose:		Convert a string to lowercase
	 */
	public String lower(String txt)
	{
		/*
		 *		Sanity check
		 */
		if(txt==null) {
			error("lower: Argument string==null");
			//return;
		}
		else {
			txt = txt.toLowerCase();
		}
		return txt;
	}
	
	/*---------------------------------------------------------------------------*/
	/**
	 *		Function:	upper
	 *
	 *		Purpose:		Convert a string to uppercase
	 */
	public String upper(String txt)
	{
		/*
		 *		Sanity check
		 */
		if(txt==null) {
			error("upper: Argument string==null");
			//return;
		}
		else {
			txt = txt.toUpperCase();
		}
		return txt;
	}
	
	/*---------------------------------------------------------------------------*/
	/**
	 *		Function:	rnd
	 *
	 *		Purpose:		Return a random long integer between 0 and range-1.
	 */
	public long rnd(long range)
	{
		long num;
	
		/*
		 *		Sanity check
		 */
		if(range<=0) {
			error("rnd: Invalid range of "+ String.valueOf(range)); //edited
			return(0);
		}
	
		/*
		 *		Generate random numbers until one is valid
		 */
		double rawResult=0d;
		
		do {
			rawResult = Math.random();
			num=(long)(range * rawResult);
		} while(num>=range);
		
		return (num);
	}
	
	/*---------------------------------------------------------------------------*/
	/*
	 *		Function:	error
	 *
	 *		Purpose:		Write a message to the error log
	 */
	public void error(String fmt, int num)
	{
		/*
		 *		Print the message
		 */
		System.err.println(fmt + num);
	}
	public void error(String fmt) {
		//printf(fmt);
		System.err.println(fmt);
	}

}//JavaC