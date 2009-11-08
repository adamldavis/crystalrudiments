package net.crystalrudiments.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Static methods supporting zip functions.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 */
public class ZipUtils
{
    /** All methods are static, so instance not needed. */
    private ZipUtils()
    {
    }

    public static byte[] toBigEndian( int len )
    {
        byte[] bsize = new byte[4];

        //copy length in big endian format:
        bsize[0] = ( byte ) ( len >> 24 );
        bsize[1] = ( byte ) ( len >> 16 );
        bsize[2] = ( byte ) ( len >> 8 );
        bsize[4] = ( byte ) len;
        return bsize;
    }

    public static void saveArray( File file, Object[] object ) throws IOException
    {
        GZIPOutputStream outputStream = new GZIPOutputStream( new FileOutputStream( file ) );

        for ( int i = 0; i < object.length; i++ )
        {

            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream( ba );
            oo.writeObject( object[i] );
            oo.flush();
            ba.flush();
            oo.close();
            byte[] bytes = ba.toByteArray();
            byte[] bsize = toBigEndian( bytes.length );
            outputStream.write( bsize, 0, bsize.length );
            outputStream.write( bytes, 0, bytes.length );
            outputStream.flush();
        }
        outputStream.finish();
        outputStream.close();
    }

    public static void saveZipped( File file, Object object ) throws IOException
    {
        GZIPOutputStream outputStream = new GZIPOutputStream( new FileOutputStream( file ) );

        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream( ba );
        oo.writeObject( object );
        oo.flush();
        oo.close();
        byte[] bytes = ba.toByteArray();
        byte[] bsize = toBigEndian( bytes.length );
        outputStream.write( bsize, 0, bsize.length );
        outputStream.write( bytes, 0, bytes.length );
        outputStream.flush();
        outputStream.finish();
        outputStream.close();
    }

    public static int parseIntLength( GZIPInputStream inputStream ) throws IOException
    {
        byte[] bsize = new byte[4];
        for ( int i = 0; i < bsize.length; i++ )
            bsize[i] = ( byte ) inputStream.read();
        int len = parseBigEndian( bsize );
        return len;
    }

    public static Object loadZipped( File file ) throws IOException
    {
        GZIPInputStream inputStream = new GZIPInputStream( new FileInputStream( file ) );
        return loadNextObject( inputStream );
    }

    public static Object loadNextObject( GZIPInputStream inputStream ) throws IOException
    {
        int len = parseIntLength( inputStream );
        byte[] bytes = new byte[len];
        for ( int i = 0; i < len; )
        {
            byte[] temp = new byte[Math.min( 1024, len - i )];
            int n = inputStream.read( temp, 0, temp.length );
            if ( n < 0 ) throw new IOException( "Reached end of the zip stream unexpectantly." );

            for ( int j = 0; j < n; j++ )
            {
                bytes[i + j] = temp[j];
            }
            i += n;
        }
        ObjectInputStream stream = new ObjectInputStream( new ByteArrayInputStream( bytes ) );
        try
        {
            return stream.readObject();
        } catch ( ClassNotFoundException ex )
        {
            throw new IOException( "Class not found: " + ex.getMessage() );
        }

    }

    /**
     * Parses big endian number from bytes
     */
    public static int parseBigEndian( byte[] bsize )
    {
        int len = 0;
        len += bsize[0] << 24;
        len += bsize[1] << 16;
        len += bsize[2] << 8;
        len += bsize[3];
        return len;
    }

    /**
     * Parses big endian number from bytes
     */
    public static long parseBigEndianLong( byte[] bsize )
    {
        int len = 0;
        int i = 0;
        for ( int n = 8 * ( bsize.length - 1 ); i < bsize.length; i++, n -= 8 )
        {
            if ( n != 0 )
                len += ( int ) bsize[i] << n;
            else
                len += ( int ) bsize[i];
        }
        return len;
    }

}
