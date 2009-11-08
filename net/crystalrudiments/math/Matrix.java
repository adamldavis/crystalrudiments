/**
 * Matrix.java
 * 
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.0
 */
package net.crystalrudiments.math;

import net.crystalrudiments.io.InOutHelp;
import net.crystalrudiments.lang.AdamObject;

public class Matrix extends AdamObject implements MatrixFinal
{

    /** bDEBUG is true if debugging. */
    private static final boolean bDEBUG = true;

    InOutHelp ioh;

    /**
     * Init Method. First thing called when app is run.
     */
    public void init()
    {
        ioh = new InOutHelp();
    }

    /**
     * 
     * Default constructor.
     *  
     */
    public Matrix()
    {
        init();
    }

    public String strMath( String a, String b, char op )
    {
        if ( a.equals( "0" ) ) return b;
        if ( b.equals( "0" ) )
            return a;
        else
            return ( "(" + a + ")" + op + "(" + b + ")" );
    }

    public String stringSub( String a, String b )
    {
        return strMath( a, b, '-' );
    }

    public String stringIntegerSub( String a, Integer b )
    {
        return strMath( a, b.toString(), '-' );
    }

    public String stringAdd( String a, String b )
    {
        return strMath( a, b, '+' );
    }

    public String stringIntegerAdd( String a, Integer b )
    {
        return strMath( a, b.toString(), '+' );
    }

    public String stringMult( String a, String b )
    {
        if ( a.equals( "0" ) )
            return a;
        else if ( b.equals( "0" ) )
            return b;
        else if ( a.equals( "1" ) )
            return b;
        else if ( b.equals( "1" ) )
            return a;

        else
            return ( "(" + a + ")" + "*" + "(" + b + ")" );
    }

    public String stringIntegerMult( String a, Integer b )
    {
        return stringMult( b.toString(), a );
    }

    public Object[][] makeArray( int x, int y )
    {
        return ( new Object[x][y] );
    }

    /**
     * Does some mathmatical operation to two Objects.
     */
    public Object doMath( Object a, Object b, int type )
    {
        if ( a instanceof Integer && b instanceof Integer )
        {
            Integer iA = ( Integer ) ( a );
            Integer iB = ( Integer ) ( b );
            switch ( type ) {
            case ADD:
                return new Integer( iA.intValue() + iB.intValue() );
            case MULT:
                return new Integer( iA.intValue() * iB.intValue() );
            case SUBTRACT:
                return new Integer( iA.intValue() - iB.intValue() );
            default:
            }
        } else if ( a instanceof String && b instanceof Integer )
        {
            switch ( type ) {
            case ADD:
                return stringIntegerAdd( ( String ) ( a ), ( Integer ) ( b ) );
            case MULT:
                return stringIntegerMult( ( String ) ( a ), ( Integer ) ( b ) );
            case SUBTRACT:
                return stringIntegerSub( ( String ) ( a ), ( Integer ) ( b ) );
            default:
            }
        } else if ( a instanceof Integer && b instanceof String )
        {
            switch ( type ) {
            case ADD:
                return stringIntegerAdd( ( String ) ( b ), ( Integer ) ( a ) );
            case MULT:
                return stringIntegerMult( ( String ) ( b ), ( Integer ) ( a ) );
            case SUBTRACT:
                return stringIntegerSub( ( String ) ( b ), ( Integer ) ( a ) );
            default:
            }
        } else if ( a instanceof String && b instanceof String )
        {
            switch ( type ) {
            case ADD:
                return stringAdd( ( String ) a, ( String ) b );
            case MULT:
                return stringMult( ( String ) a, ( String ) b );
            case SUBTRACT:
                return stringSub( ( String ) a, ( String ) b );
            default:
            }
        } else
        {
            error( "Matrix:matrixAdd::Unknown formats of a & b" );
        }
        return null;
    }

    public Object[][] getMatrix( String name, int xm, int ym )
    {
        String buffer;
        Object[][] ret = makeArray( xm, ym );
        int i, j;

        for ( i = 0; i < xm; i++ )
        {
            for ( j = 0; j < ym; j++ )
            {
                print( name + ":" + i + "," + j + ">" );
                buffer = ioh.getInputLine();
                try
                {
                    ret[i][j] = new Integer( Integer.parseInt( buffer ) );
                } catch ( Exception e )
                {
                    ret[i][j] = new String( buffer );
                }
            }
        }
        return ret;
    }

    public Object[][] matrixAdd( Object[][] a, int xm, int ym, Object[][] b )
    {
        Object[][] ret = makeArray( xm, ym );
        int i, j;
        for ( i = 0; i < xm; i++ )
            for ( j = 0; j < ym; j++ )
            {
                ret[i][j] = doMath( a[i][j], b[i][j], ADD );
            }

        return ret;
    }

    public Object[][] matrixMult( Object[][] a, int xm, int ym, Object[][] b )
    {
        Object[][] ret = makeArray( xm, ym );
        int i, j;
        for ( i = 0; i < xm; i++ )
            for ( j = 0; j < ym; j++ )
            {
                ret[i][j] = doMath( a[i][j], b[i][j], MULT );
            }
        return ret;
    }

    /*
     * Object findDet(Object[][] a, int xm, int ym) { int i,j; Object det;
     * Vector vec1 = new Vector(); //to add. Vector vec2 = new Vector(); //to
     * substract. if (xm!=ym) { error("Det only works for square matrices.");
     * return " <error>"; } return findDet(a, xm, ym); }
     */
    /**
     * Recursive algorithm for finding determinate.
     */
    public Object findDet( Object[][] a, int xm, int ym )
    {
        Object det = new Integer( 0 );
        int sp = 0;

        if ( ( xm ) == 2 ) //if this is a 2*2 matrix.
        {
            Object d1 = doMath( a[0][0], a[1][1], MULT );
            Object d2 = doMath( a[0][1], a[1][0], MULT );
            det = doMath( d1, d2, SUBTRACT );
        } else
        {
            Object[] d = new Object[xm];
            Object[][][] na = new Object[xm][xm - 1][ym - 1]; //submatrices of
                                                              // a.

            for ( int n = 0; n < ( ym ); n++ )
            {
                for ( int i = 0; i < ( xm - 1 ); i++ )
                {
                    sp = 0;
                    for ( int j = 0; j < ( ym - 1 ); j++ )
                    { //we want all "columns" except n
                        if ( j == n ) sp = 1;
                        na[n][i][j] = a[i + 1][j + sp]; //excluding top row.
                    }
                }
            }
            for ( int i = 0; i < ( xm ); i++ )
            {
                d[i] = doMath( a[0][i], findDet( na[i], xm - 1, ym - 1 ), MULT );
            }
            for ( int n = 0; n < ( xm ); n++ )
            {
                if ( n % 2 == 0 )
                    det = doMath( det, d[n], ADD );
                else
                    det = doMath( det, d[n], SUBTRACT );
            }
        }
        return det;
    }

    public void printMatrix( Object[][] f )
    {
        int i, j;

        print( "{\n" );
        for ( i = 0; i < f.length; i++ )
        {
            print( "{" );
            for ( j = 0; j < f[i].length; j++ )
            {
                print( f[i][j] + ", " );
            }
            print( "}\n" );
        }
        print( "}\n" );
    }

    public void twoMatrixMath( int type )
    {
        int a1, a2;

        Object[][] a, b, f;
        String buffer;

        print( "rows = " );
        buffer = ioh.getInputLine();
        a1 = Integer.parseInt( buffer );

        print( "columns = " );
        buffer = ioh.getInputLine();
        a2 = Integer.parseInt( buffer );

        a = getMatrix( "a", a1, a2 );
        b = getMatrix( "b", a1, a2 );
        f = null;

        print( "a=" );
        printMatrix( a );
        print( "b=" );
        printMatrix( b );

        switch ( type ) {
        case ADD:
            f = matrixAdd( a, a1, a2, b );
            break;
        case ENTRY_MULT:
            f = matrixMult( a, a1, a2, b );
            break;
        case MULT:
            f = matrixMult( a, a1, a2, b );
            break;
        default:
            error( "Matrix::Invalid type" );
            f = makeArray( 1, 1 );
        }
        print( "Resulting Matrix:\n" );

        printMatrix( f );
    }

    public void oneMatrixMath( int type )
    {
        int a1, a2;
        int i, j;
        Object[][] a;
        String buffer;

        print( "rows = " );
        buffer = ioh.getInputLine();
        a1 = Integer.parseInt( buffer );

        print( "columns = " );
        buffer = ioh.getInputLine();
        a2 = Integer.parseInt( buffer );

        a = getMatrix( "a", a1, a2 );

        printMatrix( a );

        switch ( type ) {
        case DET:
            print( "Determinant = " + findDet( a, a1, a2 ) + "\n" );
            break;
        case EIGEN_VALUES:
            break;
        case EIGEN_VECTORS:
            break;
        default:
            error( "Matrix::Invalid type" );
        }
    }

    /*
     * Main method.
     */
    public static void main( String[] args )
    {
        InOutHelp ioh = new InOutHelp();
        Matrix m = new Matrix();
        String input = " ";

        while ( input.indexOf( "!" ) < 0 )
        {
            input = " ";
            print( "Math:\t<a>=add, <e>=entry-wise multiply, " + "<m>=matrix-multipy, <d>=determinants\n\t"
                    + "<g>=eigen-values, <v>=eigen-vectors, " + "<j>=jacobi-iterations,\n\t<q>=QR-decomposition\n\t"
                    + "<!>=exit\n>" );
            input = ioh.getInputLine();

            switch ( input.charAt( 0 ) ) {
            case 'a':
                m.twoMatrixMath( ADD );
            case 'e':
                m.twoMatrixMath( ENTRY_MULT );
            case 'm':
                m.twoMatrixMath( MULT );
            case 'd':
                m.oneMatrixMath( DET );
            case 'g':
                break;
            case 'v':
                break;
            case 'j':
                break;
            case 'q':
                break;
            case '!':
                break;
            default:
                error( "Invalid input" );
                break;
            }
        }
    }

}//END OF Matrix
