
package net.crystalrudiments.alife;

import java.awt.Color;
import java.awt.Graphics;

import net.crystalrudiments.math.Math2D;

//o- try adding if, then statements.
//o- the chances of getting a rainbow should be about as 
// probable as getting random colors; right now it starts w/ a
// rainbow.
//o- hue, saturation, & brightness are inseperable quantities.

/**
 * Model of the MVC paradigm for the ALife project. This is a model of a
 * 'living' graphic image.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam Davis </A>
 * @version Version 2.0, April 28, 2005
 *  
 */
public class ALifeModel implements LifeConstants, ILifeModel
{
    
    public static final double MUTATE_QUANTUM = 0.05d;
    private static final long serialVersionUID = 1042L;
    /*-----------------------------------------------------------------------*/
    // THE MODEL
    /*-----------------------------------------------------------------------*/
    /** bDEBUG = true if debugging */
    private static final boolean bDEBUG = false;

    private String seq; //dna sequence.

    private double[] a;

    private double[] b;

    private float[][][] matrix = null; //holds the colors of this image.

    private int a_index = 0; //holds position in a[][#].

    private int width; //local value of screen width.

    private int height; //local value of screen height.
    private int addProb = PROB_ADD;
    private int moveProb = PROB_MOVE;
    private int removeProb = PROB_REMOVE;
    private int spinProb = PROB_SPIN;

    /**
     * Default constructor.
     */
    public ALifeModel()
    {
        if ( bDEBUG ) System.out.println( "In LifeView: genesis." );

        seq = randomSeq();

        if ( bDEBUG ) System.out.println( "eq1 = " + seq );

        a = makeRandomArray();
        b = copy( a );
        a_index = 0;

        width = 100;
        height = 100;
        remakeMatrix();
    }

    /**
     * Constructor that makes a clone.
     * 
     * @param model
     *            To clone.
     */
    public ALifeModel( ILifeModel model )
    {
        if ( bDEBUG ) System.out.println( "In ALifeModel(ALifeModel model)." );

        seq = model.getSequence();

        a = model.getArray();
        b = copy( a );
        a_index = 0;

        width = model.getWidth();
        height = model.getHeight();
        remakeMatrix();
    }

    private double[] copy( double[] arr )
    {
        double[] bb = new double[arr.length];
        for ( int i = 0; i < arr.length; i++ )
            bb[i] = arr[i];
        return bb;
    }

    /**
     * Sets the width of this component.
     * 
     * @param w
     *            width
     */
    public void setWidth( int w )
    {
        this.width = w;
        remakeMatrix();
    }

    /**
     * Sets the height of this component.
     * 
     * @param h
     *            Height
     */
    public void setHeight( int h )
    {
        this.height = h;
        remakeMatrix();
    }

    /**
     * Rebuilds the matrix containing values for this Model.
     */
    protected void remakeMatrix()
    {
        matrix = new float[width][height][3];
    }

    /**
     * Gets the coding (DNA) sequence for this model.
     * 
     * @return The model's coding sequence.
     */
    public String getSequence()
    {
        return seq;
    }

    /**
     * Returns the double array for storing variables used by this model's
     * algorithm.
     * 
     * @return The array used to store variables.
     */
    public double[] getArray()
    {
        return a;
    }

    /**
     * Returns the width of the model's matrix.
     * 
     * @return Width of matrix.
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Returns the height of the model's matrix.
     * 
     * @return Height of matrix.
     */
    public int getHeight()
    {
        return height;
    }

    public static double[] makeRandomArray()
    {
        int i;
        double[] a = new double[INIT_SIZE_A];

        for ( i = 0; i < a.length; i++ )
        {
            a[i] = random( RANDOM_A );
        }
        return a;
    }

    /**
     * Returns a random double
     */
    public static double random( int range )
    {
        return Math.random() * ( ( double ) range );
    }

    /**
     * Creates a random sequence.
     */
    private String randomSeq()
    {
        if ( bDEBUG ) System.out.println( "In createRandomEq()" );
        String retstr = new String();
        int num;
        num = INIT_EQ_LENGTH + 1; //number of genes.

        for ( int i = 0; i < num; i++ )
        {
            retstr += makeGene();
        }//i
        return retstr;

    }

    private String makeGene()
    {
        String retstr = new String();
        for ( int j = 0; j < SIZE_GENE; j++ ) //8
        {
            retstr += String.valueOf( ( int ) ( Math.random() * BASE ) );
        }//j
        return retstr;
    }

    /**
     * Gets the value specified.
     * 
     * @param variable
     *            Char representing a variable.
     * @return The value specified.
     */
    private double getValue( int var, double x, double y )
    {
        double ret = 0d;
        switch ( var ) {
        case 0:
            ret = x;
            break;
        case 1:
            ret = y;
            break;
        case 2:
            ret = 1d - x;
            break;
        case 3:
            ret = 1d - y;
            break;
        case 4:
            ret = a[a_index];
            a_index++;
            a_index %= INIT_SIZE_A;
            break;
        case 5:
            ret = random( 1 );
            break;
        case 6:
            ret = x * y;
            break;
        case 7:
            ret = x * x;
            break;
        case 8:
            ret = y * y;
            break;
        case 9:
            ret = Math.cos( x );
        case 10:
            ret = Math.sin( x * y );
        case 11:
            ret = Math.cos( y );
        default:
            if ( var < 22 )
                ret = Math.sin( a[var] * x );
            else if ( var < 32 )
                ret = Math.sin( a[var - 10] * y );
            else
                ret = a[var - 32];
            break;
        }
        return ret;
    }

    /**
     * Paints the model.
     * 
     * @param g
     *            Graphics of the GUI.
     */
    public void paintModel( Graphics g )
    {
        if ( matrix == null || matrix.length == 0) return;
        
        int x, y;
        for ( x = 0; x < matrix.length; x++ )
        {
            for ( y = 0; y < matrix[0].length; y++ )
            {
                paintPix(g,x,y);
            }
        }
    }

    /**
     *  
     */
    private void paintPix( Graphics g, int ix, int iy )
    {
        Color kolor;
        int i, n1, n2, n3, n4, index;
        double hue = 0, sat = 0, bri = 0;
        double dx, dy, rx = 0, ry = 0; /*
                                        * rx,ry are the numbers used for
                                        * equations.
                                        */
        double div = 1d;
        for ( i = 0; i <= seq.length() - SIZE_GENE; i += SIZE_GENE )
        {
            dx = ( ( double ) ix / width );
            dy = ( ( double ) iy / height );
            n1 = cToInt( seq.charAt( i ) ) * BASE + cToInt( seq.charAt( i + 1 ) );
            n2 = cToInt( seq.charAt( i + 2 ) ) * BASE + cToInt( seq.charAt( i + 3 ) );
            n3 = cToInt( seq.charAt( i + 4 ) ) * BASE + cToInt( seq.charAt( i + 5 ) );
            n4 = cToInt( seq.charAt( i + 6 ) ) * BASE + cToInt( seq.charAt( i + 7 ) );
            /* num should be 0 <= num < 64 */
            /* div should get smaller as i->spot. */
            index = n3 % 32;
            switch ( n1 % 20 ) {
            case 0:
                rx = dx;
                ry = dy;
                break;
            case 1:
                rx = dy;
                ry = dx;
                break;
            case 2:
                rx = dx;
                ry = 1d - dy;
                break;
            case 3:
                rx = 1d - dx;
                ry = dy;
                break;
            case 4:
                rx = Math.cos( dx * Math.PI / 2d );
                ry = Math.sin( dy * Math.PI / 2d );
                break;
            case 5:
                rx = Math.sin( dx * Math.PI / 2d );
                ry = Math.cos( dy * Math.PI / 2d );
                break;
            case 6:
                rx = Math.cos( dx * Math.PI - Math.PI / 2d );
                ry = Math.sin( dy * Math.PI );
                break;
            case 7:
                rx = Math.sin( dx * Math.PI );
                ry = Math.cos( dy * Math.PI - Math.PI / 2d );
                break;
            case 8:
                rx = Math.cos( dx * Math.PI * 2d ) % 1d;
                ry = dy;
                break;
            case 9:
                rx = Math.sin( dx * Math.PI * 2d ) % 1d;
                ry = dy;
                break;
            case 10:
                rx = dx;
                ry = Math.sin( dy * Math.PI * 2d ) % 1d;
                break;
            case 11:
                rx = dx;
                ry = Math.cos( dy * Math.PI * 2d ) % 1d;
                break;
            case 12:
                rx = 2 * dx % 1d;
                ry = dy;
                break;
            case 13:
                rx = dx;
                ry = 2 * dy % 1d;
                break;
            case 14:
                rx = Math.cos( dx * Math.PI * 2d ) % 1d;
                ry = 2 * dy % 1d;
                break;
            case 15:
                rx = Math.sin( dx * Math.PI * 2d ) % 1d;
                ry = 2 * dy % 1d;
                break;
            case 16:
                rx = 2 * dx % 1d;
                ry = Math.sin( dy * Math.PI * 2d ) % 1d;
                break;
            case 17:
                rx = 2 * dx % 1d;
                ry = Math.cos( dy * Math.PI * 2d ) % 1d;
                break;
            case 18:
                rx = dx;
                ry = dy;
                break;
            case 19:
                rx = dx;
                ry = dy;
                break;
            default:
                rx = dx;
                ry = dy;
                break;
            }
            switch ( n2 % 20 ) {
            case 0:
                a[index] = getValue( n4, rx, ry );
                break;
            case 1:
                a[index] = getValue( n4, rx, ry );
                break;
            case 2:
                a[index] %= getValue( n4, rx, ry );
                break;
            case 3:
                a[index] *= getValue( n4, rx, ry );
                break;
            case 4:
                a[index] = ( n4 ) / 64d;
                break;
            case 5:
                a[index] %= ( n4 ) / 64d;
                break;
            case 6:
                a[index] *= ( n4 ) / 64d;
                break;
            case 7:
                a[index] = a[n4 % 32];
                break;
            case 8:
                a[index] = ( a[index] + a[n4 % 32] ) % 1d;
                break;
            case 9:
                a[index] = ( a[index] + a[( index + 1 ) % 32] ) % 1d;
                break;
            case 10:
                a[index] = 1d - a[index];
                break;
            case 11:
                a[index] = a[n4 % 32] * a[n4 % 32];
                break;
            case 12:
                a[index] = Math.cos( rx );
                break;
            case 13:
                a[index] = Math.sin( rx );
                break;
            case 14:
                a[index] = Math.cos( ry );
                break;
            case 15:
                a[index] = Math.sin( ry );
                break;
            case 16:
                a[index] *= a[n4 % 32];
                break;
            case 17:
                a[index] = a[index] % a[n4 % 32];
                break;
            case 18:
                a[n4 % 32] *= a[index];
                break;
            case 19:
                a[n4 % 32] = a[index];
                break;
            default:
                /*
                 * div = (double)seq.length(); bri += getValue(n2, dx, dy); sat +=
                 * getValue(n3, dx, dy); hue += getValue(n4, dx, dy);
                 */
                break;
            }//switch
            div = ( double ) seq.length() / BASE;
            //index = (n1/20)*8 + (n2/20)*2 + (n3/32);
            hue += a[index] / div;
            sat += a[( index + 1 ) % 32] / div;
            bri += a[( index + 2 ) % 32] / div;
        }//i
        kolor = Color.getHSBColor( ( float ) hue % 1f, ( float ) sat % 1f, ( float ) bri % 1f );
        g.setColor( kolor );
        g.drawLine( ix, iy, ix, iy );
    }

    /**
     * Call before calling repaint on this object.
     */
    public void prepareForPaint()
    {
        int i, j, k;
        for ( i = 0; i < width; i++ )
            for ( j = 0; j < height; j++ )
                for ( k = 0; k < 3; k++ )
                    matrix[i][j][k] = 0f;
        a = copy( b );
        a_index = 0;
    }

    /**
     * Draw a line on graphics.
     */
    private void drawLine( Graphics g )
    {
        int x1, y1, x2, y2;
        x1 = Math2D.scale( a[0], width, 1 );
        y1 = Math2D.scale( a[1], height, 1 );
        x2 = Math2D.scale( a[2], width, 1 );
        y2 = Math2D.scale( a[3], height, 1 );
        g.drawLine( x1, y1, x2, y2 );
    }

    private void drawRect( Graphics g )
    {
        int x1, y1, w, h;
        x1 = Math2D.scale( a[4], width, 1 );
        y1 = Math2D.scale( a[5], height, 1 );
        w = Math2D.scale( a[6], width, 1 );
        h = Math2D.scale( a[7], height, 1 );
        g.drawRect( x1, y1, w, h );
    }

    private void drawOval( Graphics g )
    {
        int x1, y1, w, h;
        x1 = Math2D.scale( a[8], width, 1 );
        y1 = Math2D.scale( a[9], height, 1 );
        w = Math2D.scale( a[10], width, 1 );
        h = Math2D.scale( a[11], height, 1 );
        g.drawOval( x1, y1, w, h );
    }

    private void drawArc( Graphics g )
    {
        int x1, y1, w, h, angle0, angle1;
        x1 = Math2D.scale( a[12], width, 1 );
        y1 = Math2D.scale( a[13], height, 1 );
        w = Math2D.scale( a[14], width, 1 );
        h = Math2D.scale( a[15], height, 1 );
        angle0 = Math2D.scale( a[16], 360, 1 );
        angle1 = Math2D.scale( a[17], 360, 1 );
        g.drawArc( x1, y1, w, h, angle0, angle1 );
    }

    /**
     * Returns a slightly mutated copy of this ALifeModel.
     */
    public ILifeModel reproduce()
    {
        ILifeModel m = new ALifeModel( this ); //m = model.

        m.mutate();

        if ( bDEBUG ) System.out.println( "seq clone= " + m.getSequence() );

        return m;
    }

    /**
     * Mutates this Object.
     */
    public void mutate()
    {
        int num, i;
        seq = mutate( seq );
        for ( i = 0; i < INIT_SIZE_A; i++ )
        {
            if ( a[i] < MUTATE_QUANTUM )
                a[i] += Math.random() * 1.01D * ( MUTATE_QUANTUM );
            else if ( a[i] > (1.0 - MUTATE_QUANTUM) )
                a[i] -= Math.random() * ( MUTATE_QUANTUM );
            else
            {
                a[i] += Math.random() * ( MUTATE_QUANTUM ) - (MUTATE_QUANTUM / 2);
            }
        }
    }

    /**
     * Mutates an equation!
     */
    private String mutate( String seq )
    {
        String code;
        int num;
        int i;
        num = ( int ) random( addProb );
        if ( num == 1 )
        {
            code = makeGene();
            i = ( int ) random( seq.length() / 2 );
            if ( i * 2 >= seq.length() )
                seq += code;
            else
                seq = seq.substring( 0, i * 2 ) + code + seq.substring( i * 2 );
        }
        num = ( int ) random( removeProb );
        if ( num == 1 && seq.length() > 4)
        {
            i = ( int ) random( ( seq.length() - 7 ) / 2 );
            if ( i * 2 + 8 >= seq.length() )
                seq = seq.substring( 0, i * 2 );
            else
                seq = seq.substring( 0, i * 2 ) + seq.substring( i * 2 + 8 );
        }
        num = ( int ) random( moveProb );
        if ( num == 1 )
        {
            //remove
            i = ( int ) random( ( seq.length() - 7 ) / 2 );
            code = seq.substring( i * 2, i * 2 + 8 );
            if ( i * 2 + 8 >= seq.length() )
                seq = seq.substring( 0, i * 2 );
            else
                seq = seq.substring( 0, i * 2 ) + seq.substring( i * 2 + 8 );
            //add
            i = ( int ) random( seq.length() / 2 );
            if ( i * 2 >= seq.length() )
                seq += code;
            else
                seq = seq.substring( 0, i * 2 ) + code + seq.substring( i * 2 );
        }
        num = ( int ) random( spinProb );
        if ( num == 1 )
        {
            //remove
            i = ( int ) random( Math.max( 0, seq.length() - 7 ) / 2 );
            code = seq.substring( i * 2, i * 2 + Math.min( seq.length() - i * 2, 8 ) );
            if ( i * 2 + 8 >= seq.length() )
                seq = seq.substring( 0, i * 2 );
            else
                seq = seq.substring( 0, i * 2 ) + seq.substring( i * 2 + 8 );
            //reverse
            code = reverseString( code );
            //add
            i = ( int ) random( seq.length() / 2 );
            if ( i * 2 >= seq.length() )
                seq += code;
            else
                seq = seq.substring( 0, i * 2 ) + code + seq.substring( i * 2 );
        }
        return seq;
    }

    /**
     * Reverse String.
     */
    public static String reverseString( String str )
    {
        StringBuffer sb = new StringBuffer( str );
        sb.reverse();
        return sb.toString();
    }

    /** Sets the one over probability of adding a gene. Larger number equals less probable.
     * @param addProb The addProb to set.
     */
    public void setAddProb( int addProb )
    {
        this.addProb = addProb;
    }
    /**Sets the one over probability of moving a gene. Larger number equals less probable.
     * @param moveProb The moveProb to set.
     */
    public void setMoveProb( int moveProb )
    {
        this.moveProb = moveProb;
    }
    /** Sets the one over probability of removing a gene. Larger number equals less probable.
     * @param removeProb The removeProb to set.
     */
    public void setRemoveProb( int removeProb )
    {
        this.removeProb = removeProb;
    }
    /** Sets the one over probability of removing a section, 
     * spinning it, and re-adding it. Larger number equals less probable.
     * @param spinProb The spinProb to set.
     */
    public void setSpinProb( int spinProb )
    {
        this.spinProb = spinProb;
    }
    /**
     * Overrides Object.equals() Tells whether this equals another ALifeModel.
     */
    public boolean equals( Object obj )
    {
        boolean ret = false;
        ALifeModel model;
        if ( obj instanceof ILifeModel )
        {
            model = ( ALifeModel ) obj;
            if ( model.getSequence().equals( this.getSequence() ) ) ret = true;
        }
        return ret;
    }

    /**
     * converts char to int.
     */
    private int cToInt( char c )
    {
        return Integer.parseInt( String.valueOf( c ) );
    }

    /**
     * Debug test main.
     */
    public static void main( String[] args )
    {
        ILifeModel lm = new ALifeModel();

        ILifeModel[] child = new ILifeModel[5];

        for ( int i = 0; i < child.length; i++ )
            child[i] = lm.reproduce();

        float n = 14.24f;
        float m = 10.0f;

        System.out.println( "fixNum(" + n + ", " + m + ")=" + Math2D.fixNum( n, m ) );
    }
}
