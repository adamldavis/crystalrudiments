package net.crystalrudiments.path;

/**
 * RandomPathFinder.java
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, May 27, 2001
 */

public class RandomPathFinder extends PathFinder
{

    private static final boolean bDEBUG = true;

    private static final int MAX_REPEAT = 10;

    /**
     * Used to try and connect to a random point between the starting point and
     * end point.
     */
    private CrudePathFinder cpf;

    public RandomPathFinder()
    {
        super();
    }

    /**
     * Initializes this pathfinder with a maximum possible distance for a path.
     * @param maxDistance Maximum possible distance of a path in the grid.
     */
    public RandomPathFinder( int maxDistance )
    {
        super( maxDistance );
    }

    /**
     * Finds a shortest path given an array and a starting point and end point.
     * Returns path in the form of an array of directions. <BR>
     * 0 = up, 1 = right, 2 = down, 3 = left.
     * 
     * @param grid
     *            True means it is an obstacle.
     */
    public int[] findPath( PathGrid grid, int x1, int y1, int x2, int y2 )
    {
        int[] ret = null;
        int[] temp;
        int n = 0;

        /* call the super. */
        if ( super.hasProblem( grid, x1, y1, x2, y2 ) ) return null;

        while ( true ) //ret==null
        {
            n++;
            if ( n > MAX_REPEAT ) break;
            temp = makeRandomPath( grid, x1, y1, x2, y2 );
            if ( temp != null ) ret = temp;
        }
        if ( bDEBUG ) System.out.println( "RandomPF n= " + n );
        return ret;
    }

    /**
     * PRE: "mygrid" has been initialized.
     */
    private int[] makeRandomPath( PathGrid grid, int x1, int y1, int x2, int y2 )
    {
        int[] ret = null;
        int[] p1;
        int[] p2;
        int x;
        int y;
        int max = findMax( x1, y1, x2, y2 );
        while ( true )
        {
            x = randomPos( x1, x2, max );
            y = randomPos( y1, y2, max );
            if ( super.hasProblem( grid, x1, y1, x, y ) ) continue;
            if ( super.hasProblem( grid, x, y, x2, y2 ) ) continue;
            if ( doneGrid.getGrid( x, y ) ) continue; //keeps us from
                                                      // repeating.
            doneGrid.setGrid( x, y, true );
            break;
        }
        cpf = new CrudePathFinder( super.getDistance() );

        p1 = cpf.findPath( grid, x1, y1, x, y );
        if ( p1 == null ) return null;
        p2 = cpf.findPath( grid, x, y, x2, y2 );
        if ( p2 == null ) return null;

        ret = pathCombine( p1, p2 );
        if ( ret.length > distance )
            return null;
        else
            distance = ret.length;
        return ret;
    }

    /**
     * Makes a random position based on max, n1, and n2.
     */
    private int randomPos( int n1, int n2, int max )
    {
        //find the middle of n1 & n2 then use max.
        int mid = ( ( n1 + n2 ) / 2 );
        int ran = ( int ) ( Math.random() * ( double ) ( max + 1 ) );

        return ( mid + ran - ( max / 2 ) );
    }

    /**
     * Finds max.
     */
    private int findMax( int x1, int y1, int x2, int y2 )
    {
        int xdif = Math.abs( x2 - x1 );
        int ydif = Math.abs( y2 - y1 );
        if ( ydif > xdif )
            return ydif;
        else
            return xdif;
    }

    private int[] reversePath( int[] path )
    {
        int[] rev = null;
        int leng = path.length;
        rev = new int[leng];

        for ( int i = 0; i < leng; i++ )
            rev[i] = oppositeDir( path[leng - 1 - i] );

        return rev;
    }

    private int oppositeDir( int dir )
    {
        return ( ( dir + 2 ) % 4 );
    }

    private int[] pathCombine( int[] p1, int[] p2 )
    {
        int i;
        int[] ret = new int[p1.length + p2.length];
        for ( i = 0; i < p1.length; i++ )
        {
            ret[i] = p1[i];
        }
        for ( i = 0; i < p2.length; i++ )
        {
            ret[i + p1.length] = p2[i];
        }

        return ret;
    }
}//END OF CLASS RandomPathFinder
