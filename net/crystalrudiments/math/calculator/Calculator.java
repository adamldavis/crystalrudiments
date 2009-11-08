package net.crystalrudiments.math.calculator;
/**
 * Calculator Class
 * 
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis </A>
 * @version Version 1.0,  April 5, 2001
 */

public class Calculator 
{
	private static final int MAX_SIZE = 256;
	private static final boolean DEBUG = true;
	
	public static void main(String[] args)
	{
		String alpha = "(abcd(efg) (hi(jklmno)pqrs(tuv)) (wxyz))";
		
		String[] list=null;
		
		list = Calculator.breakApart(alpha);
		
		for (int i=0; i<list.length; i++)
		{
			System.out.println(list[i]);
		}
	}//main
	
	/**
	 * Private constructor denotes a static class
	 * and no instances should be made.
	 */
	private Calculator() {}
	
	public static String[] breakApart(String input)
	{
		int num=0; //number of (open) parentheses.
		int i=0;
		int j=0;
		int k=0;
		int size=1; //position in array
		String[] array; //array to return.
		
		array = new String[MAX_SIZE];
		array[0] = input;
		
		for (i=0; i<input.length(); i++)
		{
			if ( input.charAt(i)=='(' ) 
			{
				num++;
				int num2=0; //# )
				j=1;
				while (num2<1)
				{
					if (i+j >= input.length()) {
						System.out.println( "Big error!!!!" );
						break;
					}
					if ( input.charAt(i+j)==')' ) num2++;
					if ( input.charAt(i+j)=='(' ) num2--;
					j++;
				}
				//put in new string
				if (DEBUG) System.out.println( "substring("+i+", "+(i+j)+")" );
				array[size] = input.substring(i,i+j);
				size++;
			}
			else if ( input.charAt(i)==')' ) {
				num--;
			}
		}//for i
		
		//change size of array and copy data.
		String[] olda = array;
		array = new String[size];
		for (k=0; k<size; k++) array[k] = olda[k];
		
		return array;
	}
	
}