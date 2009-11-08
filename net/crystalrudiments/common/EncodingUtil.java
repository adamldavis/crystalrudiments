package net.crystalrudiments.common;

import net.crystalrudiments.lang.VBObject;

/**
 * Supports ways to encode data.
 * @author Adam Davis
 */
public class EncodingUtil extends VBObject
{
    public static final String  FILE_CHARS = "#$%&'()+цтыщ€÷№йвдаеклипомƒ≈…бнуъс";
    public static final int ALPHA_ENCODE_MAX = 69;

    /** All methods are static, so instance not needed. */
    private EncodingUtil()
    {}
    
    /**
     * Encodes an int as a String of length two.
     * @param sdn Int to encode from rand -2295 to 2295.
     * @return String of length two.
     * @see EncodingUtil#decode(String)
     */
    public static String encode( int sdn )
    {
        String ret;
        //Range of function: [-2295, 2295]
        if ( sdn < -2295 || sdn > 2295 )
        {
            Exception e = new Exception( "Out of range [-2295, 2295] for Encode." );
            e.printStackTrace();
        }
        ret = Chr( Int( sdn / 50 ) + 80 ) + Chr( ( sdn % 50 ) + 80 );
        return ret;
    }

    /**
     * Decodes an int from a String of length two.
     * @param sds String of length two encoded using encode method.
     * @return Int from -2295 to +2295.
     * @see EncodingUtil#encode(String)
     */
    public static int decode( String sds ) throws Exception
    {
        int ret = 0;
        if ( sds.length() != 2 )
        {
            Exception e = new Exception( "Parameter sds=" + sds + " is wrong size." );
            //e.printStackTrace();
            throw e;
        }
        //try {

        ret = ( Asc( left( sds, 1 ) ) - 80 ) * 50;
        ret += Asc( right( sds, 1 ) ) - 80;
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
        return ret;
    }

    /**
     * Uses alphabet and numbers ie: 0-9A-Z«-№. Asc(0->9) = 48->57 <BR>
     * Asc(A-Z) = 65-90 <BR>
     * Asc(?) "#$%&'()+цтыщ€÷№йвдаеклипомƒ≈…бнуъс" (34) <BR>
     */
    public static int alphaDecode( String s )
    {
        int ret = 0;
        try
        {
            if ( Asc( s ) >= 65 && Asc( s ) <= 90 )
            {
                ret = Asc( s ) - 65 + 10;

            } else if ( Asc( s ) >= 48 && Asc( s ) <= 57 )
            {
                ret = CInt( s );
                ;
            } else
            {
                ret = InStr( 1, FILE_CHARS, s ) + 35; //Asc(s) - 128 + 36
            } //if
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
        return ret;
    } //function

    /**
     * Range: 0-69 <BR>
     * "#$%&'()+цтыщ€÷№йвдаеклипомƒ≈…бнуъс" <BR>
     * usedtobe0-62 .
     */
    public static String alphaEncode( int i )
    {
        String ret = "";
        if ( i < 10 )
        {
            ret = ( CStr( i ) );
        } else if ( i < 36 )
        {
            ret = Chr( i + 65 - 10 );
        } else
        {
            if ( i > ALPHA_ENCODE_MAX )
            {
                MsgBox( "Gone out of bounds. Please back up." );
                ret = "«";
            } else
            {
                ret = Mid( FILE_CHARS, i - 35, 1 ); //Chr(i + 128 - 36)
            } //if
        } //if
        return ret;
    }

}