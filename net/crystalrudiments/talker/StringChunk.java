
package net.crystalrudiments.talker;

import java.util.Vector;

import net.crystalrudiments.lang.AdamObject;

/**
* StringChunk.java
* 
* @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
* @version Version 1.0, October 27, 2001
*/
public class StringChunk extends AdamObject
{
	private static final boolean bDEBUG = false;
	
	String str;
	int points;
	
	public StringChunk(int p, String s)
	{
		points=p;
		str = s;
	}
	public StringChunk(String s) { this(0,s);}
	
	public String toString() { 	return "("+str+")"+points;}
	
	public String getString(){	return str;}
	public int getPoints() {	return points;}
	
	public void addPoint() {	points++;}
	public void setPoints(int p){	points=p;}
	
	/**
	 * Splits this string chunk into parts depending on
	 * given string. The inputed stringchunk acts (almost) 
	 * like a delimiter (a point is added for every time it 
	 * is found.) This StringChunk remains unchanged. If
	 * strch isn't found then null is returned.
	 *
	 * <code>eg. StringChunk s = new StringChunk("gotobedtonight");
	 *	StringChunk[] arr = s.split("to");
	 *	arr = { "go","bed","night" };
	 * </code>
	 */
	public StringChunk[] split(StringChunk strch)
	{
		StringChunk[] ret = null;
		int ind=0;
		
		if (bDEBUG) print("In split\n");
		
		if (strch==null) {
			error("s==null::StringChunk:split(StringChunk)");
			exit();
		}
		if (str==null) {
			error("str==null::StringChunk:split(StringChunk)");
			exit();
		}
		if (strch.equals("")) {
			error("strch.getString()==<nothing>");
			return null;
		}
		
		if ( ( ind = str.indexOf(strch.getString()) )>-1 ) 
		{
			strch.addPoint();
			Vector v = splithelp(str, strch, ind);
			
			ret = new StringChunk[v.size()];
			for (int i=0; i<v.size(); i++) {
				ret[i] = (StringChunk) v.elementAt(i);
			}
		}
		return ret;
	}
	private Vector splithelp(String s1, StringChunk strch, int ind)
	{
		String s = strch.getString();
		Vector v = new Vector(4);
		int test = ind + s.length();
		
		if (bDEBUG) print("In splithelp\n");
		
		if (ind!=0) 
			v.add( new StringChunk(points, s1.substring(0,ind)) );
		//v.add( new StringChunk(points+1, s) );
		
		if (test<s1.length())
		{
			int i;
			if (bDEBUG) print("S1="+s1+"\n");
			s1 = s1.substring(test);
			if (( i = s1.indexOf(s) )>-1) {
				strch.addPoint();
				v.addAll( splithelp(s1, strch, i) ); 
			} else {
				v.add( new StringChunk(points, s1) );
			}
		}
		return v;
	}
	/**
	 * Clone method.
	 */
	public Object clone() {
		StringChunk sc = new StringChunk(points, str);
		return sc;
	}
	/**
	 * Tells if this object equals the parameter Object.
	 */
	public boolean equals(Object obj)
	{
		if (!(obj instanceof StringChunk)) return false;
		StringChunk sc = (StringChunk)obj;
		
		return equals(sc.getString());
	}
	/**
	 * Hashcode used for java.util.Hashtable.
	 * If two Objects are equal, then their hashCode's have to be the same integer!
	 * @see java.lang.Object
	 */
	public int hashCode()
	{
		return str.hashCode();
	}
	/**
	 * Tells if the string in this object equals the
	 * parameter string.
	 */
	public boolean equals(String s)
	{
		return (str.equals(s));
	}
	/**
	 * Testing main.
	 */
	public static void main(String[] args)
	{
		StringChunk sc = new StringChunk("i have a fish");
		
		StringChunk[] arr = sc.split(new StringChunk(" a "));
		
		for (int i=0;i<arr.length; i++) print(arr[i].toString());
		print("\n");
		
		sc = new StringChunk("go to bed tonight");
		
		arr = sc.split(new StringChunk("to"));
		
		for (int i=0;i<arr.length; i++) print(arr[i].toString());
		print("\n");
		
		sc = new StringChunk("a a ");
		
		arr = sc.split(new StringChunk(" a "));
		
		if (arr==null) print("arr==null");
		else {
			print("arr!=null::arr.length="+arr.length+"\n"); 
			for (int i=0;i<arr.length; i++) print("["+arr[i].toString()+"]");
		}
		print("\n");
	}
}