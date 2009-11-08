/**
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, May 27, 2001
 */
package net.crystalrudiments.math;

import java.awt.geom.Line2D;

public class Math2D {

    private Math2D() {}
    
    /**
     * Calculates the distance between the given coordinates.
     * @see Math#sqrt(double)
     * @param x1 First dimension of first coordinate.
     * @param y1 Second dimension of first coordinate.
     * @param x2 First dimension of second coordinate.
     * @param y2 Second dimension of second coordinate.
     * @return The double-precision distance between the given coordinates.
     */
    public static double distance(int x1, int y1, int x2, int y2) {
        double xdis = x2 - x1;
        double ydis = y2 - y1;
        return Math.sqrt(xdis * xdis + ydis * ydis);
    }
    
    /**
     * Calculates the distance between the given coordinates.
     * @see Math#sqrt(double)
     * @param x1 First dimension of first coordinate.
     * @param y1 Second dimension of first coordinate.
     * @param x2 First dimension of second coordinate.
     * @param y2 Second dimension of second coordinate.
     * @return The double-precision distance between the given coordinates.
     */
    public static double distance(double x1, double y1, double x2, double y2) {
        double xdis = x2 - x1;
        double ydis = y2 - y1;
        return Math.sqrt(xdis * xdis + ydis * ydis);
    }

    /**
     * Calculates the distance (length) of the given line.
     * 
     * @param line Line using double precision.
     * @return Length of the line.
     */
    public static double distance(Line2D line) {
        double xdis = line.getX2() - line.getX1();
        double ydis = line.getY2() - line.getY1();
        return Math.sqrt(xdis * xdis + ydis * ydis);
    }
    
    /**
     * Returns the average of the given values.
     * 
     * @param v1
     *            Value one.
     * @param v2
     *            Value two.
     * @return Average of v1 and v2.
     */
    public static float average( float v1, float v2 )
    {
        return ( v1 + v2 ) / 2f;
    }
    
    /**
     * Returns the average of the given values.
     * 
     * @param v1
     *            Value one.
     * @param v2
     *            Value two.
     * @return Average of v1 and v2.
     */
    public static double average( double v1, double v2 )
    {
        return ( v1 + v2 ) / 2f;
    }
    
	/**
	 * Keeps the num between zero and 'max' in a MOD fashion.
	 * 
	 * @param num
	 *            Number to fix.
	 * @param max
	 *            Maximum value to return.
	 * @return A number between 0 and max using MOD of num.
	 */
	public static float fixNum(float num, float max) {
		if (num < 0f)
			num = (-1f) * num;
		if (num > max) {
			num = num % max;
		}
		return num;
	}
	
	/**
	 * Keeps the num between zero and 'max' in a MOD fashion.
	 * 
	 * @param num
	 *            Number to fix.
	 * @param max
	 *            Maximum value to return.
	 * @return A number between 0 and max using MOD of num.
	 */
	public static int fixNum(int num, int max) {
		if (num < 0f)
			num = (-1) * num;
		if (num > max) {
			num = num % max;
		}
		return num;
	}

	/**
	 * Scales x into a range (0,scale) from a range (0,original). <BR>
	 * 
	 * @param x
	 *            The number to scale.
	 * @param scale
	 *            The scale you want.
	 * @param original
	 *            The original maximum of the scale x is from.
	 * @return X scaled using inputed scale and original maximum.
	 */
	public static int scale(double x, int scale, int original) {
		int retval;
		retval = (int) ((x * scale) / (double) original);
		return retval;
	}
}//Math2D
