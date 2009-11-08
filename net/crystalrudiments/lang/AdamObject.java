
package net.crystalrudiments.lang;
/**
 * My own personal Object.
 * Holds standard helpful functions.
 */
public class AdamObject
{
	/** Encapsulates System..err..println().
	*/
	public static void error(String str)
	{
		System.err.println("Error: " + str);
	}
	/**
	 * Encapsulates System..out..print().
	 */
	public static void print(String str)
	{
		System.out.print(str);
	}
	/**
	 * Encapsulates System..exit(0);
	 */
	public static void exit()
	{
		System.exit(0);
	}
}//AdamObject