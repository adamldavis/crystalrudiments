package net.crystalrudiments.path;

import java.util.Iterator;

import net.crystalrudiments.util.LinkedList;

/**
 * Represents a path through some 2D space.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, May 29, 2001
 */
public class Path extends LinkedList
{

    public Path()
    {
        super();
    }

    public int[] getArray()
    {
        int[] ret = new int[super.size()];
        int i=0;
        for ( Iterator iter = super.iterator(); iter.hasNext(); i++)
        {
            ret[i] =  ((Integer) iter.next()).intValue();
        }
        return ret;
    }

    public void add( int direction )
    {
        super.add( new Integer( direction ) );
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    public String toString()
    {
        return super.toString();
    }

    public static void main( String[] args )
    {
        Path p = new Path();
        for ( int i = 0; i < 10; i++ )
        {
            p.add( i );
        }
        System.out.println( p );
    }
}//Path
