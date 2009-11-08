/*
 * Created on May 12, 2005 by Adam. 
 * Interface for "SimplePathGrid"
 *
 */
package net.crystalrudiments.path;

/**
 * Interface for 2D grids of boolean values.
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, May 27, 2001
 */
public interface PathGrid
{

    public boolean getGrid( int x, int y );

    public void setGrid( int x, int y, boolean yes );

    public int getWidth();

    public int getHeight();

    public PathGrid copy();

    public String toString();
}