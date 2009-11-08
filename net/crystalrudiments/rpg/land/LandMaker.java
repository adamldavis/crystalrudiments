package net.crystalrudiments.rpg.land;

import java.util.Arrays;

/**
 * Creates random geological surfaces.
 * <P>
 * Copyright: 2005
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.0
 */
public class LandMaker implements ILandMaker
{
    //private static final int percent_of_depth = 26 ; //more == more walls,
    // less == less walls
    private int arraySize = 25;

    private int size5th = 5; //1/5 of dim

    private int outsideHeight = 0; //hight outside map.

    private int initialHeight = 0;

    private int maximumHeight = 75;

    private int groundHeightMax = 1;

    private int percentOfDepth = 30;

    private boolean[][] ground;

    //ground(x, y) = [2]=wall, [0]=road
    private int[][] height;

    private int[][] hi;

	/**
	 * @param arraySize
	 * @param outsideHeight
	 * @param initialHeight
	 * @param maximumHeight
	 * @param groundHeightMax
	 * @param percentOfDepth
	 * @throws IndexOutOfBoundsException If arraySize is not multiple of five.
	 */
	public LandMaker(int arraySize, int outsideHeight, int initialHeight,
			int maximumHeight, int groundHeightMax, int percentOfDepth) throws IndexOutOfBoundsException {
		this();
		this.arraySize = arraySize;
		this.outsideHeight = outsideHeight;
		this.initialHeight = initialHeight;
		this.maximumHeight = maximumHeight;
		this.groundHeightMax = groundHeightMax;
		this.percentOfDepth = percentOfDepth;
		
		if (arraySize % 5 != 0)
		{
			throw new IndexOutOfBoundsException("ArraySize is not a multiple of 5.");
		}
		this.size5th = arraySize / 5;
	}
	/**
	 * @param arraySize
	 * @throws IndexOutOfBoundsException If arraySize is not multiple of five.
	 */
	public LandMaker(int arraySize) throws IndexOutOfBoundsException
	{
		this(arraySize, 0, 0, 75, 1, 30);
	}
    public LandMaker()
    {
        init();
    }

    void init()
    {
        height = new int[arraySize][arraySize];
        ground = new boolean[arraySize][arraySize];
        hi = new int[size5th][size5th];
        for ( int i = 0; i < arraySize; i++ )
            Arrays.fill( ground[i], false );
        for ( int i = 0; i < arraySize; i++ )
            Arrays.fill( height[i], initialHeight );
    }

    /** defaults to depth = 26. */
    public void makeLand()
    {
        makeLand( this.percentOfDepth );
    }

    /**
     * Makes a square map with random looking splatches of ground.
     * @param depth Sets the percentOfDepth variable.
     */
    public void makeLand( int depth )
    {
        this.percentOfDepth = depth;
        int i, j;
        int rndx = 0, rndy = 0;
        int aa = 0, wnum = 0;
        //''
        for ( j = 0; j < size5th; j++ )
        {
            for ( i = 0; i < size5th; i++ )
            {
                hi[i][j] = random( 40 ) - ( percentOfDepth / 7 );
            } //Next i,
        } //j
        //hi[0][0] = 40; //assures normal hight in beginning.
        //'
        while ( true )
        {
            for ( i = ( aa ); i < ( arraySize - aa ); i += ( arraySize - 1 - 2 * aa ) )
            {
                for ( j = ( aa ); j < ( arraySize - aa ); j++ )
                {
                    randomHeight( i, j, hi[( i / 5 )][( j / 5 )] );
                } //Next j
            } //Next i
            for ( j = ( aa ); j < ( arraySize - aa ); j += ( arraySize - 1 - 2 * aa ) )
            {
                for ( i = ( aa ); i < ( arraySize - aa ); i++ )
                {
                    randomHeight( i, j, hi[( i / 5 )][( j / 5 )] );
                } 
            } 
            if ( ( arraySize - 1 - 2 * ( aa + 1 ) ) <= 0 ) break;
            aa = aa + 1;
        }
        for ( i = 0; i < arraySize; i++ )
            for ( j = 0; j < arraySize; j++ )
            {
                if ( getHeight( i, j ) < groundHeightMax )
                {
                    ground[i][j] = true;
                    wnum = wnum + 1;
                }
            }
        //
        for ( j = 1; j < arraySize - 1; j++ )
        {
            for ( i = 1; i < arraySize - 1; i++ )
            {
                if ( ground[i - 1][j - 1] && ground[i + 1][j + 1] && ground[i + 1][j - 1] && ground[i - 1][j + 1]
                        && ground[i - 1][j] && ground[i + 1][j] && ground[i][j - 1] && ground[i][j + 1] )
                {
                    ground[i][j] = true;
                }
            } 
        }
        //
        /*
         * for (j = 0 ; j <= 142; j++) for (i = 0; i < DIM; i++ ) { if (
         * ground[i][j] == 2){ hight[i][j] = 50; } else { hight[i][j] = 0; }
         * //if }
         *///Next i, j
        rndx = random( arraySize );
        rndy = random( arraySize );
        ground[rndx][rndy] = true;
    } //method

    private void randomHeight( int a, int b, int hinum )
    {
        int rndz;
        int num1;
        int num2;
        rndz = ( random( 9 ) - percentOfDepth / 7 );
        num1 = getHeight( a - 1, b ) + getHeight( a + 1, b ) + getHeight( a, b - 1 ) + getHeight( a, b + 1 ) + hinum;
        num2 = ( num1 / 5 ); //take the average.
        height[a][b] = num2 + rndz;
    } //method

    /** Returns an int from 0 to inum. */
    public static int random( int inum )
    {
        double d = Math.random() * inum;
        return ( int ) d;
    }

    public int getHeight( int x, int y )
    {
        if ( x < arraySize && y < arraySize && x > 0 && y > 0 ) { return height[x][y]; }
        return outsideHeight;
    }

    public boolean getGround( int x, int y )
    {
        if ( x < arraySize && y < arraySize && x > 0 && y > 0 ) { return ground[x][y]; }
        return false;
    }

    public static void main( String args[] )
    {
        ILandMaker m = new LandMaker();

        m.makeLand();

        for ( int i = 0; i < 50; i++ )
        {
            for ( int j = 0; j < 50; j++ )
            {
                if ( m.getGround( i, j ) )
                    System.out.print( "-" );
                else
                    System.out.print( "*" );
            }
            System.out.println( "" );
        }
    }
	/**
	 * The size of the 2D arrays used for the land-map.
	 * @return Returns the arraySize.
	 */
	public int getArraySize() {
		return arraySize;
	}
	/**
	 * Returns an array representing which tiles are below groundHeightMax.
	 * @return Returns the ground.
	 */
	public boolean[][] getGround() {
		return ground;
	}
	/**
	 * Returns the height map 2D array.
	 * @return Returns the height map 2D array.
	 */
	public int[][] getHeight() {
		return height;
	}
} //---END---

