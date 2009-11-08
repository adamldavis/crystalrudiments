package net.crystalrudiments.coder;
/**
 * CoderFace
 *
 *
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.0
 */

public interface CoderFace
{
	/* Any variables here are considered final by java. */
	
	/** 
	* Operators.
	*/
	char[] oper = 
	{
		'+', '-', '*', '/', '%', '^'
	};
	
	abstract String interpret(String str);
	
	abstract String calculate(String str);
	
	abstract String toString();
}//CoderFace