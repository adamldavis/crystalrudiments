package net.crystalrudiments.rpg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import net.crystalrudiments.common.EncodingUtil;
import net.crystalrudiments.common.ZipUtils;
import net.crystalrudiments.common.logging.LoggerFactory;

/**
 * Static methods for handling loading and saving of maps.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis </A>
 */
public class MapManager
{

    private static final Logger LOG = LoggerFactory.getDefaultLogger();

    private static final int MAP_OPEN = 1;

    private static final int ZIP_MAP_OPEN = 2;

    private static final int MAP_CLOSED = 0;

    private static int state = MAP_CLOSED;

    private static int size = 0;

    public static final String PREFIX = "";

    public static final String SUFFIX = ".map";

    private MapManager()
    {
    }

    public static String makeFileName( String code )
    {
        StringBuffer name = new StringBuffer( PREFIX );
        int ix = EncodingUtil.alphaDecode( code.substring( 0, 1 ) );
        int iy = EncodingUtil.alphaDecode( code.substring( 1, 2 ) );
        int iz = EncodingUtil.alphaDecode( code.substring( 2, 3 ) );
        name.append( ix ).append( "_" ).append( iy ).append( "_" ).append( iz ).append( SUFFIX );
        return name.toString();
    }

    public static void open( String code ) throws IOException
    {
        File oldFile = new File( "map" + code + ".txt" );
        File newFile = new File( makeFileName( code ) );
        if ( newFile.exists() ) open( newFile );
        else if ( oldFile.exists() )
            open( oldFile );
        else
            throw new IOException( "No map file exists for " + code );
    }

    public static void open( File file ) throws IOException
    {
        String name = file.getName().toLowerCase();

        if ( name.startsWith( "map" ) && name.endsWith( "txt" ) )
        {
            String code = name.substring( 3, 6 );
            File newFile = new File( makeFileName( code ) );

            BufferedReader br = null;
            String line = null;
            br = new BufferedReader( new FileReader( newFile ) );
            List list = new ArrayList( 50 );
            while ( ( line = br.readLine() ) != null )
            {
                list.add( line );
            }
            ZipUtils.saveArray( newFile, list.toArray() );
            open( newFile );
            return;
        } else if ( name.endsWith( SUFFIX ) )
        {

            inputStream = new GZIPInputStream( new FileInputStream( name ) );
            ////size = parseIntLength( inputStream );
        } else
            throw new IOException( "unknown file type" + file );

        state = ZIP_MAP_OPEN;
    }

    private static GZIPInputStream inputStream = null;

    public static String readLine()
    {
        String line = null;
        if ( state == ZIP_MAP_OPEN )
        {

            try
            {
                line = ( String ) ZipUtils.loadNextObject( inputStream );
            } catch ( IOException e )
            {
                LOG.severe( e.toString() );
            }
        }
        return line;
    }

    public static void close()
    {
        if ( state == ZIP_MAP_OPEN )
        {
            try
            {
                inputStream.close();
                inputStream = null;
            } catch ( IOException e )
            {
                LOG.warning( "Problem closing file" );
            }
        }
    }

}
