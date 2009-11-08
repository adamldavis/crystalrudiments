package net.crystalrudiments.alife;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Adam's Artificial Life: LifeModel This is a model of a 'living' graphic
 * image. <BR>
 * 
 * <PRE>
 * 
 * LifeModel Class
 * 
 * Revisions: 1.4 April 28, 2001 Removed saturation & brightness eq's
 * altogether. 1.3 April 28, 2001 Added method "equals(Object)" 1.2 April 27,
 * 2001 Added eq3 which controls saturation. 1.1 April 27, 2001 Saved original
 * as "LifeModel1" and made this file. Made array of variables called a instead
 * of the matrices a, b, & c. 1.0 April 25, 2001 Created LifeModel, made only
 * random matrices.
 * 
 * </PRE>
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis </A>
 * @version Version 1.3, April 28, 2001
 * @deprecated May 5, 2005, Use {@link net.crystalrudiments.alife.ALifeModel}.
 */
public class LifeModel implements LifeConstants, ILifeModel
{

    /*-----------------------------------------------------------------------*/
    // THE MODEL
    /*-----------------------------------------------------------------------*/

    /** bDEBUG = true if debugging */
    private static final boolean bDEBUG = true;

    private String eq1; //controls the color (hue).

    private double[] a;

    private int a_index = 0; //holds position in a[][#].

    private int mywidth; //local value of screen width.

    private int myheight; //local value of screen height.

    /**
     * Default constructor.
     */
    public LifeModel()
    {
        if ( bDEBUG ) System.out.println( "In LifeView: genesis." );

        eq1 = createRandomEq();

        if ( bDEBUG ) System.out.println( "eq1 = " + eq1 );

        a = makeRandomMatrix();
        a_index = 0;

        mywidth = 100;
        myheight = 100;
    }

    /**
     * Constructor that makes a clone.
     * 
     * @param model
     *            To clone.
     */
    public LifeModel( LifeModel model )
    {
        if ( bDEBUG ) System.out.println( "In LifeModel(LifeModel model)." );

        eq1 = model.getEq1();

        a = model.getArray();

        mywidth = model.getWidth();
        myheight = model.getHeight();
    }

    /**
     * Sets the width of the model array.
     * 
     * @param w
     *            width
     */
    public void setWidth( int w )
    {
        this.mywidth = w;
    }

    /**
     * Sets the height of the model array.
     * 
     * @param h
     *            Height of the model
     */
    public void setHeight( int h )
    {
        this.myheight = h;
    }

    /**
     * Sets the equation of the model.
     * 
     * @param eq
     *            Some Equation, using a special format.
     */
    public void setEq1( String eq )
    {
        this.eq1 = eq;
    }

    /**
     * Returns equation 1.
     */
    public String getEq1()
    {
        return eq1;
    }

    /**
     * Returns the A array.
     * 
     * @return The A array.
     */
    public double[] getArray()
    {
        return a;
    }

    /**
     * Width.
     * 
     * @return my width
     */
    public int getWidth()
    {
        return mywidth;
    }

    /**
     * Height.
     * 
     * @return my height
     */
    public int getHeight()
    {
        return myheight;
    }

    private double[] makeRandomMatrix()
    {
        int i = 0;
        int j = 0;
        double[] a;
        int x = ( INIT_SIZE_A );

        a = new double[x];

        for ( i = 0; i < a.length; i++ )
        {
            a[i] = random( RANDOM_A );
        }
        return a;
    }

    /**
     * Returns a random double
     */
    private double random( int range )
    {
        return Math.random() * ( ( double ) range );
    }

    /**
     * Creates a random equation.
     */
    private String createRandomEq()
    {
        if ( bDEBUG ) System.out.println( "In createRandomEq()" );
        String retstr = "";
        int num;
        int i;
        num = ( int ) random( INIT_EQ_LENGTH ) + 1; //number of iterations.

        for ( i = 0; i < num; i++ )
        {
            retstr += addVariable();
        }//i

        return retstr;

    }//createRandomEq

    /**
     * 
     * @return
     */
    public String addVariable()
    {
        String retstr = "";
        int ran;
        ran = ( int ) random( 5 );
        switch ( ran ) {
        case 0:
            retstr += "@";
            break; //@=constant
        case 1:
            retstr += "x";
            break;
        case 2:
            retstr += "y";
            break;
        case 3:
            retstr += "X";
            break;
        case 4:
            retstr += "Y";
            break;
        default:
            System.err.println( "Error: LifeModel:addVariable" );
        }//switch

        ran = ( int ) random( 5 );
        switch ( ran ) {
        case 0:
            retstr += "+";
            break;
        case 1:
            retstr += "-";
            break;
        case 2:
            retstr += "*";
            break;
        case 3:
            retstr += "/";
            break;
        case 4:
            retstr += "%";
            break;
        default:
            System.err.println( "Error: LifeModel:addVariable" );
        }//switch

        return retstr;
    }//addVariable

    /**
     * Keeps the num between zero and 'max' in a MOD fashion.
     *  
     */
    public float fixNum( float num, float max )
    {
        if ( num < 0f ) num = ( -1f ) * num;
        if ( num > max )
        {
            num = num % max;
        }
        return num;
    }//fixNum

    /**
     * Scales x into a range (0,scale) from a range (0,original). <BR>
     * 
     * @param x
     *            The number to scale.
     * @param scale
     *            The scale you want.
     * @param original
     *            The original maximum of the scale x is from.
     */
    public int scale( int x, int scale, int original )
    {
        int retval;
        retval = ( x * scale ) / original;
        return retval;
    }

    /**
     * Gets the value specified.
     * 
     * @variable Char representing a variable.
     * @return The value specified.
     */
    private double getValue( char variable, int x, int y )
    {
        double ret = 0d;
        switch ( variable ) {
        case 'x':
            ret = ( double ) x / ( double ) mywidth;
            break;
        case 'y':
            ret = ( double ) y / ( double ) myheight;
            break;
        case 'X':
            ret = 1d - ( ( double ) x / ( double ) mywidth );
            break;
        case 'Y':
            ret = 1d - ( ( double ) y / ( double ) myheight );
            break;
        case '@':
            ret = a[a_index];
            a_index++;
            break;
        default:
        }
        return ret;
    }//getValue

    /**
     * Does calculation to num and returns result. Pass in a 2-char String for
     * eq (eg. "+x"). Num is the first operand.
     */
    private double calculate( double num, String eq, int x, int y )
    {
        char operator = ' ';
        char c2 = ' ';
        try
        {
            operator = eq.charAt( 0 );
            c2 = eq.charAt( 1 );
        } catch ( Exception e )
        {
            System.err.println( "Error: calculate: " + e );
            System.err.println( "eq = " + eq );
            System.exit( 0 );
        }
        double value = 0d;

        switch ( operator ) {
        case '+':
            value = num + getValue( c2, x, y );
            break;
        case '-':
            value = num - getValue( c2, x, y );
            break;
        case '*':
            value = num * getValue( c2, x, y );
            break;
        case '/':
            try
            {
                value = num / getValue( c2, x, y );
            } catch ( Exception e )
            {
                value = 1d;
                if ( bDEBUG ) System.out.println( "div zero" );
            }
            break;
        case '%':
            value = num % getValue( c2, x, y );
            break;
        default:
        }
        return value;
    }//calculate

    /**
     * Does actual equation calculation.
     */
    private double getRawEq( String eq, int x, int y )
    {
        double raw;
        a_index = 0; //initiates the array a[i].

        raw = getValue( eq.charAt( 0 ), x, y ); //eg. "x/b-y*"

        while ( eq.length() > 2 )
        {
            raw = calculate( raw, eq.substring( 1 ), x, y ); //eg. "/b-y*"
            eq = eq.substring( 2 ); //eg. "b-y*"
        }
        return raw;
    }

    /*
     * Interprets the equations and returns a color.
     */
    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public Color interpretEq( int x, int y )
    {
        return eqHSB( x, y );
    }

    /*
     * Interprets the equations and returns a color.
     */
    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public Color eqHSB( int x, int y )
    {
        double raw = 0d;
        float hue; //hue.
        float alpha; //brightness.
        float sat; //staturation.

        //THE EQUATION STUFF....
        raw = getRawEq( eq1, x, y );
        hue = ( float ) raw;

        // fix the Color within limits.
        hue = fixNum( hue, 1f );

        alpha = 1f;
        sat = 1f;

        try
        {
            return ( Color.getHSBColor( hue, sat, alpha ) );
        } catch ( Exception e )
        {
            System.err.println( "interpretEq: " + e );
            System.exit( 0 );
        }
        return Color.red;
    }//eqHSB

    /**
     * Returns a slightly mutated copy of this LifeModel.
     */
    public ILifeModel reproduce()
    {
        LifeModel m = new LifeModel( this ); //m = model.

        m.setEq1( mutate( m.getEq1() ) );

        if ( bDEBUG ) System.out.println( "eq1 clone= " + m.getEq1() );

        return m;
    }

    /**
     * Mutates an equation!
     */
    private String mutate( String eq )
    {
        int num;
        num = ( int ) random( PROB_ADD );

        if ( num == 0 )
        {
            num = ( int ) random( eq.length() ) + 1;
            num = num - num % 2;
            if ( num == eq.length() )
            {
                //add to end.
                eq += addVariable();
            } else if ( num == 0 )
            {
                //add in beginning.
                eq = addVariable() + eq;
            } else
            {
                //inbetweens...
                String str1 = eq.substring( 0, num );
                String str2 = eq.substring( num );

                eq = str1 + addVariable() + str2;
            }
        }//if

        if ( eq.length() > 2 ) //we're going to remove possibly.
        {
            num = ( int ) random( PROB_REMOVE );

            if ( num == 0 )
            {
                num = ( int ) random( eq.length() ) + 1;
                num = num - num % 2;
                if ( num == eq.length() )
                {
                    //do nothing, (at end).
                } else if ( num == 0 )
                {
                    //at beginning.
                    eq = eq.substring( 2 );
                } else
                {
                    //inbetweens...
                    if ( ( num + 2 ) == eq.length() )
                    {
                        eq = eq.substring( 0, num );
                    } else
                    {
                        eq = eq.substring( 0, num ) + eq.substring( num + 2 );
                    }
                }
            }//if (num==0)
        }//if (eq.length() > 2)
        return eq;
    }//mutate

    /**
     * Overrides Object.equals() Tells whether this equals another LifeModel.
     */
    public boolean equals( Object obj )
    {
        boolean ret = false;
        LifeModel model;
        if ( obj instanceof LifeModel )
        {
            model = ( LifeModel ) obj;
            if ( model.getEq1().equals( this.getEq1() ) ) ret = true;
        }
        return ret;
    }//equals

    /**
     * Debug test main.
     */
    public static void main( String[] args )
    {
        ILifeModel lm = new LifeModel();

        ILifeModel[] child = new LifeModel[5];

        for ( int i = 0; i < child.length; i++ )
            child[i] = lm.reproduce();
        //		
        //		float n = 14.24f;
        //		float m = 10.0f;
        //		
        //		System.out.println("fixNum("+n+", "+m+")=" + lm.fixNum(n, m));
    }

    /**
     * @see net.crystalrudiments.alife.ILifeModel#getSequence()
     */
    public String getSequence()
    {
        return this.getEq1();
    }

    /**
     * @see net.crystalrudiments.alife.ILifeModel#paintModel(java.awt.Graphics)
     */
    public void paintModel( Graphics g )
    {
        int x, y;
        for ( x = 0; x < this.mywidth; x++ )
        {
            for ( y = 0; y < this.myheight; y++ )
            {
                g.setColor( interpretEq( x, y ) );
                g.drawLine( x, y, x, y );
            }
        }
    }

    /**
     * @see net.crystalrudiments.alife.ILifeModel#prepareForPaint()
     */
    public void prepareForPaint()
    {
    }

    /**
     * @see net.crystalrudiments.alife.ILifeModel#mutate()
     */
    public void mutate()
    {
        this.setEq1( this.mutate( getEq1() ) );
    }
}