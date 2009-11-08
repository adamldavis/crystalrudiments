/*
 * StringMan.java
 * String Manipulator.
 */
package net.crystalrudiments.talker;

import java.util.*;

/**
 * String Manipulator.
 */
public class StringMan
{
	private StringMan() {}
	
	public static String lower(String str)
	{
		return str.toLowerCase();
	}
	/**
	 * This method tokenizes the input using standard white-space
	 * delimiters and returns only unique strings. It simply doesn't
	 * add a string if ArrayList.contains(String) returns true.
	 * @param str The string to tokenize.
	 * @return An ArrayList of unique tokens.
	 */
	public static ArrayList uniqueTokenize(String str)
	{
		StringTokenizer strtok = new StringTokenizer(str);
		ArrayList ret = new ArrayList( strtok.countTokens() );
		String s;
		
		for (int i=0; strtok.hasMoreTokens(); i++)
		{
			s = strtok.nextToken();
			if (!ret.contains(s)) ret.add(s);
		}
		return ret;
	}
} 