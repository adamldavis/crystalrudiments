package net.crystalrudiments.coder;
/**
 * AICoder
 *
 *
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.0
 */

public class AICoder extends Coder implements AICoderFace
{
	/** bDEBUG is true if debugging. */
	private static final boolean bDEBUG = true;
	
	/**
	* Default Constructor
	*/
	public AICoder()
	{
		System.out.println("In AICoder()");
	}
	
	/**
	* Does math on string given. 
	* @param str Operation of type "true||false", etc...
	*/
	public boolean interpretBoolean(String str)
	{
		if (bDEBUG) System.out.println("in AICoder: interpretBoolean: "
			+ "str = " + str);
		int operation=-1;
		boolean value = false;
		boolean x = false;
		boolean y = false;
		int i=0;
		int[] num = new int[5];
		num[0] = str.indexOf("||");
		num[1] = str.indexOf("&&");
		
		for (i=0; i<num.length; i++) {
			if (num[0] > -1) operation = i;
		}
		
		if (operation==-1)
		{
			x = Boolean.valueOf(str).booleanValue();
			return x;
		}
		
		try {
			x = Boolean.valueOf( str.substring(0,num[operation]) ).booleanValue();
			y = Boolean.valueOf( str.substring(num[operation]+1) ).booleanValue();
		}
		catch (Exception e) 
		{
			System.out.println("Error: could not get Boolean because:\n"+e);
			return false;
		}
		
		switch (operation) {
			case 0: value = x&&y; break;
			case 1: value = x||y; break;
			default:
		}//switch
		
		return value;
		
	}//interpretMath
	
	/**
	* Interprets an operation with two variables.
	* PRE: Spaces and delimeters should not be in str.
	*/
	public String interpret(String str)
	{
		if (str.equals("")) return "";
		if (bDEBUG) System.out.println("in AICoder: interpret");
		String ret;
		
		if (str.indexOf("true")>=0)
		{
			return String.valueOf(interpretBoolean(str));
		}
		else if (str.indexOf("false")>=0)
		{
			return String.valueOf(interpretBoolean(str));
		}
		else
		{
			ret = super.interpret(str);
		}
		return ret;
	}
	
	public void doStuff() 
	{
		System.out.println("DOING STUFF!");
	}
	
	/**
	* Main method. Run by java compiler.
	*/
	public static void main(String args[])
	{
		Coder c = new AICoder();
		
		System.out.println
		(
			"matchParen( `abc(abc(def)ghi)`,4 ) = "
			+ c.matchParen( "abc(abc(def)ghi)",4 ) 
		);
		
		System.out.println("answer = 8*2=16 ===>" 
			+ c.calculate("(4+4)*(3-1)") +".");
	}//main
	
}//AICoder
