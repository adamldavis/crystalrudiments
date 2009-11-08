/**
 * Coder
 *
 * ~ 5/6/01 - made it handle multiple operators. (added countOperators)
 *
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.0
 */
package net.crystalrudiments.coder;

class Coder implements CoderFace
{
	/** bDEBUG is true if debugging. */
	private static final boolean bDEBUG = true;
	
	
	/**
	* Default Constructor
	*/
	public Coder()
	{
		System.out.println("In Coder()");
	}
	
	/**
	* counts number of operators. 
	*/
	public int countOperators(String str)
	{
		int i;
		int n=0;
		int num=0;
		
		for (i=0; i<str.length(); i++)
		for (n = 0; n<oper.length; n++)
		{
			if (str.charAt(i)==oper[n]) { num++; break; }
		}
		if (bDEBUG) System.out.println
			("In countOperators: str= " + str + ": num = " + num);
		return num;
	}//countOperators
	
	/**
	* Does math on string given. 
	* @param str Operation of type "a/b", "a%b", "a-b+c+....", etc...
	*/
	public float interpretMath(String str)
	{
		if (bDEBUG) System.out.println("in Coder: interpretMath: "
			+ "str = " + str);
		int operation=-1;
		int num_opers = 0;
		float value=0f;
		float x=0f;
		float y=0f;
		int i=0;
		int n=0;
		int[] num = new int[oper.length];
		
		for (i=0; i<num.length; i++) 
		{
			num[i] = str.indexOf(oper[i]);
			if (num[i] > 0) operation = i;
		}//i
		
		num_opers = countOperators(str);
		
		if (num_opers==0)
		{
			try {
				x = Float.parseFloat(str);
			}
			catch (Exception e) {
				System.out.println("Error: could not parseFloat because:\n"+e);
				return 0f;
			}
			return x;
		}
		else if (num_opers>1)
		{
			int start=0;
			int stop = str.length();
			
			for (i=0; i<str.length(); i++) //isolates the priority operation.
			for (n = 0; n<oper.length; n++) {
				if (str.charAt(i)==oper[n]) 
				{
					if ((i<num[operation])
					&& (i>start)) start = i+1;
					if ((i>num[operation])
					&& (i<stop)) stop = i;
				}
			}//n,i
			str = str.substring(0,start) 
			+ String.valueOf( interpretMath(str.substring(start, stop)) )
			+ str.substring(stop, str.length());
			
			return (interpretMath(str)); //recursive call.
		}//if
		
		if (bDEBUG) System.out.println("---->1 operator");
		try {
			x = Float.parseFloat(str.substring(0,num[operation]));
			y = Float.parseFloat( str.substring(num[operation]+1) );
		}
		catch (Exception e) 
		{
			System.out.println("Error: could not parseFloat because:\n"+e);
			System.exit(0);
		}
		
		switch (operation) {
			case 0: value = x+y; break;
			case 1: value = x-y; break;
			case 2: value = x*y; break;
			case 3: if (y==0) System.err.println("Error: division by zero");
				else value = x/y;
				break;
			case 4: value = x%y; break;
			case 5: value = power(x, y); break;
			default:
		}//switch
		
		return value;
		
	}//interpretMath
	
	public float power(float x, float y)
	{
		if (y==1f) {
			return x;
		}
		else if (y==0f) {
			return 1f;
		}
		else if (y<0) {
			return (1f/power(x,-y));
		}
		else if (y>0) {
			double raw;
			raw = ((double) y) *Math.log((double) x);
			raw = Math.exp(raw);
			return (float) raw;
		}
		return 0f;
	}//power
	
	/**
	* Interprets an operation with two variables.
	* PRE: Spaces and delimeters should not be in str.
	*/
	public String interpret(String str)
	{
		if (bDEBUG) System.out.println("in Coder: interpret");
		String ret;
		float f;
		
		f = interpretMath(str);
		ret = String.valueOf(f);
		
		return ret;
	}
	
	/**
	* Remove spaces.
	*/
	public String removeSpaces(String str)
	{
		int x;
		
		while (true) 
		{
			x = str.indexOf(' ');
			if (x>-1) {
				str = str.substring(0,x) + 
					str.substring(x+1, str.length());
			}
			else break;
		}//do
		
		return str;
		
	}//removeSpaces
	
	/**
	* Removes things like spaces and extraneous operators.
	*/
	private String trimFat(String str)
	{
		if (bDEBUG) System.out.println("in trimFat, str = "+ str);
		int i;
		boolean messy=false;
		//remove spaces.
		str = removeSpaces(str);
		
		//remove extraneous operators.
		do {
			messy = false;
			for (i=0; i<oper.length; i++)
			{
				if (str.charAt(0)==oper[i]) {
					str = str.substring(1);
					messy = true;
				}
				if (str.charAt(0)==oper[i]) {
					str = str.substring(0,str.length()-1);
					messy = true;
				}
			}//i
		} while (messy && (str.length()>0));
		
		return str;
	}//trimFat
	
	
	/**
	* Goes through parenthesis and does calculations.
	*/
	public String calculate(String str) 
	{
		if (bDEBUG) System.out.println( "calculate: str = " + str );
		int i;
		String str0;
		String str1;
		String str2;
		String ret = null;
		boolean parenthesis = false;
		
		// (1+4+2) * (7+3) - (4)
		// str0,     str1,   str2
		if (str.equals("")) return "";
		
		str = trimFat(str);
		if (str.equals("")) return "";
		
		for (i=0; i<str.length(); i++)
		{
			if (str.charAt(i)=='(') 
			{
				parenthesis = true;
				int num = matchParen(str,i);
				
				str0 = str.substring(0, i);
				str1 = calculate( str.substring(i+1, num) );
				str2 = str.substring(num+1, str.length());
				
				ret = calculate(str0 + str1 + str2);
			}
		}//i
		
		if (!parenthesis) // no parens found.
		{
			ret = interpret(str);
		}
		return ret;
	}//calculate
	
	/**
	* Finds the matching parenthesis that matches ends a segment.
	*/
	public int matchParen(String input, int i)
	{
		if (bDEBUG) System.out.println( "matchParen: i = " + i );
		int j;
		int num=0; //# of ')'
		
		j=1;
		while (num<1)
		{
			if (i+j >= input.length()) {
				System.err.println( "Missing end parenthesis." );
				return 0;
				//System.exit(0);
			}
			if ( input.charAt(i+j)==')' ) num++;
			if ( input.charAt(i+j)=='(' ) num--;
			j++;
		}
		//put in new string
		
		return (i+j-1);
	}//matchParen
	
	
	/**
	* Main method. Run by java compiler.
	*/
	public static void main(String args[])
	{
		Coder c = new Coder();
		
		System.out.println
		(
			"matchParen( `abc(abc(def)ghi)`,4 ) = "
			+ c.matchParen( "abc(abc(def)ghi)",4 ) 
		);
		
		System.out.println("answer = 8+2+5+6 ===>" 
			+ c.calculate("8+2+5+6") +".");
	}//main
	
}//Coder