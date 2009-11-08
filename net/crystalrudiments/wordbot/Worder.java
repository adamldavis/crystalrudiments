
//import java.util.*;
package net.crystalrudiments.wordbot;

import java.util.Collection;
import java.util.Vector;

import net.crystalrudiments.io.InOutHelp;
import net.crystalrudiments.lang.AdamObject;

/**
* Worder.java
* 	The function of this class is to quickly match incomplete strings with
* other strings in an attempt to find what string was originally intended to
* be communicated. For instance, this may be used for a spell checker in a
* program or for a dictionary program. The individual functions could be used
* from a static context or the class could be initialized so that variables
* pertaining to Worder could be encapsulated in that object.
* 
* @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
* @version Version 1.0, September 24, 2001
*/
public class Worder extends AdamObject
{
	/** If length-difference is greater than this then we move on.*/
	static final int LENGTH_CUT_OFF = 3; 
	
	static final int KEY_MULT = 4;
	
	/** The minimum number of points used for a key-stroke. The values given to
	* each key on the keyboard range from 0 to 192. This value KEY_MIN is added
	* to penalize a more standard amount for an extra character.*/
	static final int KEY_MIN = 384;
	
	/** The average number of points used for a key-stroke. The values given to
	* each key on the keyboard range from 0 to 192. This value KEY_AVE is used
	* to try and remove the damage done by extra characters.*/
	static final int KEY_AVE = 96;
	
	static final int KEY_CUT_OFF = 1794;
	
	/** Holds an array of strings which represent the keys of the keyboard.*/
	static final String keys[] =
	{
		"1234567890-=",
		"qwertyuiop[]",
		"asdfghjkl;'\"",
		"zxcvbnm,./;?"
	};
	static final int PLACE = 1; //points given for out-of-place letter.
	
	static final int EXTRA = 2; //points given for extra letter.
	
	static final int WRONG = 3; //points given for wrong letter.
	
	static final int MAX_WORD_LENGTH = 14;
	
	/** The maximum percent deviation to tolerate in compare methods.*/
	public double cut_off;
	
	protected Vector words;
	
	boolean caseMatters;
	
	public Worder(Collection words) {
		this(words, 0.22d, false);
	}
	/** Initializes with given collection of words, and cut-off value.
     * @param word Collection of words 
     * @param cut_off  The maximum percent deviation to tolerate in compare methods.
     */
    public Worder( Collection word, double cut_off )
    {
        this(word, cut_off, false);
    }
    /**Initializes with given collection of words, and whether or not case matters.
     * @param word Collection of words
     * @param caseMatters False to ignore case.
     */
    public Worder( Collection word, boolean caseMatters )
    {
        this(word, 0.22d, caseMatters);
    }
    /**
     * Initializes with given collection of words, cut-off, and caseMatters.
     * @param word Collection of words
     * @param cut_off  The maximum percent deviation to tolerate in compare methods.
     * @param caseMatters False to ignore case.
     */
    public Worder( Collection word, double cut_off, boolean caseMatters )
    {
        this.cut_off = cut_off;
        this.words = new Vector(word);
        this.caseMatters = caseMatters;
    }
	/**
	 * Returns absolute value of the difference of the sums of the byte values for
	 * each string. Uses String.getBytes() (not compatible with JDK 1.1).
	 * This is useful becuase it will return zero even when the characters of the
	 * two strings are in completely different order (unlike String.equals()).
	 * <BR> eg. stringCompare("ratface", "facerat")==0.
	 */
	public static int stringCompare(String s1, String s2)
	{
		if (s1.length()!=s2.length()) return -1;
		
		return stringCompare(s1, s2, s1.length());
	}
	public static int stringCompare(String s1, String s2, int stopPoint)
	{
		byte[] s1b = s1.getBytes();
		byte[] s2b = s2.getBytes();
		int t1=0, t2=0, i;
		for (i=0; i<stopPoint; i++) 
		{
			t1 += s1b[i];
			t2 += s2b[i];
		}
		return Math.abs(t2-t1);
	}
	
	/** 
	 * Gives simple estimation of similarity of two Strings based on the closeness of
	 * the keys on the keyboard for every character in each String. Simply adds up
	 * the points given to each character based on their position on the keyboard.
	 * Note: This runs at O(N) where N is the total length of both strings.
	 * Also: The values returned are not garaunteed to make sense.
	 * (Suggested use: use this first to weed out obvious non-matches).
	 * @param s1 First string.
	 * @param s2 Second string.
	 */
	public static int simpleKeyCompare(String s1, String s2)
	{
		int i,j,k,n;
		int[] v = new int[2];
		String[] s = new String[2];
		s[0]=s1.toLowerCase();
		s[1]=s2.toLowerCase();
		v[0]=0; v[1]=0;
		
		for (n=0; n<2; n++)
		for (i=0; i<s[n].length(); i++)
		{
			for (j=0; j<keys.length; j++)
			{
				k= keys[j].indexOf(s[n].charAt(i));
				if (k>=0) { 
					v[n] += (KEY_MIN + KEY_MULT*k + j); 
					//KEY_MIN is used to penalize more for extra letters.
					break; 
				}
			}
		}
		return Math.abs(v[0]-v[1]);
	}
	/**
	 * Helper method to compare(String), does safety checks
	 * and pre-calculations and calls compare(String,String,int,int).
	 * NOTE: Does consider the length of strings.
	 * @param s1 The test string.
	 * @param s2 The target string.
	 * @param useCase Whether or not case matters.
	 * @return Double value indicating how far off s1 is from s2. (like strcmp).
	 */
	public static double compare(String s1, String s2, boolean useCase, double cutoff)
	{
		double dc;
		if (s1.length() > MAX_WORD_LENGTH) /*safety checks.*/
		{
			error("Worder::compare(String,String,boolean):s1.length() > MAX_WORD_LENGTH");
			return 1d;
		}
		if (s2.length() > MAX_WORD_LENGTH)
		{
			error("Worder::compare(String,String,boolean):s2.length() > MAX_WORD_LENGTH");
			return 1d;
		}
		if (!useCase) {
			s1 = s1.toLowerCase(); 
			s2 = s2.toLowerCase(); 
		}
		dc = (double)compare(s1, s2, 0, s2.length()*WRONG, cutoff); //Call Absolute Method.
		if (s2.length()==0) {
			//this should never happen, but i check for it anyway.
			error("s2.length()==0"); 
			return dc; 
		}
		return ( dc / ( (double)(s2.length()*WRONG) ) ); 
	}
	/**
	 * Recursive helper method to compare(String).
	 * A wrong letter counts for more than an extra letter. The value wmax is
	 * used for speed, so that the recursing ends once we know that the test string
	 * is too different from the target string.
	 * @param s1 The test string.
	 * @param s2 The target string.
	 * @param pts The number of points accumalated thus far.
	 * @param wmax The value s2.length()*WRONG used to calculate
	 * if we have surpassed the cutoff value. If wmax=0, we will ignore the
	 * cutoff value.
	 * @return Point value indicating how far off s1 is from s2.
	 */
	static int compare(String s1, String s2, int pts, int wmax, double cutoff)
	{
		int num=0;
		int ret=0;
		int i;
		
		if (s1.length()>s2.length()) {/*switch the strings.*/
			String tmp=s1;
			s1 = s2;
			s2 = tmp;
		}
		if (wmax!=0) {
			if (( (double)pts/(double)wmax )>cutoff)
				return pts;
		}
		/*try and find extra letters to remove to make 
		them the same. (royza, boyz) extra letters should
		count less than *wrong* letters, eg. (boy, aaa)=big difference
		(boy, boyaaa)=little difference.*/
		// compare(boyz, royza)
		// 1+compare(boyz, oyza) -- 1+compare(oyz, oyza)
		// 2+compare(boyz, yza) -- 1+compare("", a)
		// 3+compare(boyz, za) -- =1+1 = 2
		// 4+compare(boyz, a)
		// =5
		if (s1.equals(s2)) return pts; //terminating condition.
		if (s1.length()==0) return ( s2.length()*EXTRA + pts );
		if (stringCompare(s1,s2)==0) {
			//if the two strings have same characters then they are out of order.
			//so simply add PLACE for every mismatch.
			//party,ytrap:4 :: face,faec:2 :: party,ytpar:5 :: you,ouy:3.
			for (i=0; i< s2.length(); i++)
			{
				if (i>=s1.length()) { 
					ret += compare("", s2.substring(i), pts, wmax, cutoff); 
					break; 
				}
				else if (s1.charAt(i)!=s2.charAt(i)) {
					ret+=PLACE;
				}
			}
			if (i==s2.length()) return (pts + ret);
		}
		else for (i=0; i< s2.length(); i++)
		{
			if (i>=s1.length()) { 
				ret = compare("", s2.substring(i), pts, wmax, cutoff); 
				break; 
			}
			else if (s1.charAt(i)!=s2.charAt(i)) 
			{
				//remove the offending character.
				s2 = s2.substring(0,i) + s2.substring(i+1,s2.length());
				num = compare(s1, s2, EXTRA + pts, wmax, cutoff); //EXTRA LETTER.
				
				s1 = s1.substring(0,i) + s1.substring(i+1,s1.length());
				ret = compare(s1, s2, WRONG + pts, wmax, cutoff); //WRONG LETTER.
				
				if (num<ret) ret = num;
				break;
			}
			/*otherwise, s1.charAt(i)==s2.charAt(i);*/
		}
		return ret; //should only be executed when recursing!!!
	}
	/**
	 * Finds the most similar string to comp that is found in the
	 * array word[]. If nothing is similar enough (according to
	 * the local cutoff values) it returns null.
	 *
	 * @return Index from word[] or -1 if no match is found or there was an error.
	 */
	public int findBestMatch(String comp)
	{
		int n;
		double dp;
		double oldp = 32767d;
		int ret=-1;
		String sw;
		
		for (n=0; n<words.size(); n++)
		{
			sw = (String)words.get(n);
			/*if the difference in length is too great then move on. */
			if ( Math.abs(comp.length()-sw.length()) <= LENGTH_CUT_OFF ) 	//NOTICE, NO ";"
			if (simpleKeyCompare(comp, sw) <= KEY_CUT_OFF) 					//NOTICE, NO ";"
			if ( (dp = compare(comp, sw, caseMatters, cut_off)) <= cut_off ) 
			{
				if (dp==0d) return (n);
				if (dp < oldp) {
					oldp = dp;
					ret = n;
				}
			}
		}
		/* compare(adam, bobo) = 1 completely different.
		 * compare(adam, adbo) = 0.5 half-similar.
		 * compare(adam, bobobubu) = 2 absurdly different...*/
		return ret;
	}
	public int simpleSearch(String comp)
	{
		for (int n=0; n<words.size(); n++)
		{
			if ( comp.equals((String)words.get(n)) ) return n;
		}
		return -1;
	}
	public String getWord(int index)
	{
		if (index<0 || index>=words.size()) return null;
		return (String) words.get(index);
	}
	public void addWord(String s)
	{
		words.add(s);
	}
	/**
	 * Main test method.
	 */
	public static void main(String[] args)
	{
		InOutHelp ioh = new InOutHelp();
		Worder w;
		Vector vect = new Vector();
		String s="";
		String[] months = {"January", "February", "March", "April", "May",
			"June", "July", "August", "September", "October", "November",
			"December" };
		for (int j=0; j<months.length; j++)
			print(months[j] + ", ");
			
		print("\n");
		for (int n=0; n<months.length; n++) vect.add(months[n]);
		w = new Worder(vect, 0.25d);
		
		for (int i=0; !s.equals("q"); i++)
		{
			int n;
			
			double wcmp=0d;
			
			print(">");
			s = ioh.getInputLine();
			
			for (n=0; n<months.length; n++)
			{
				if ( (wcmp = compare(s, months[n], false, 0.25d)) <= 0.25d ) {
					int kc;
					double d; 
					int d1, d2;
					kc = simpleKeyCompare(s, months[n]);
					/* the following just prints out the double in a standard format.*/
					d = wcmp;
					d1 = (int)d;
					d2 = (int)(100000d*(d-(double)d1));
					print(months[n]+":\t"+ d +":\t" +kc+"\n");
				}
			}
			print( "bestmatch::" + w.getWord(w.findBestMatch(s)) +"\n");
		}
	} //main
}//Worder	