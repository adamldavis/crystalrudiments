/*
 * Created on Jan 27, 2005 by Adam. 
 *
 */
package net.crystalrudiments.common.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Logs to a file with prefix log and suffix txt in the current default
 * (run-time) directory.
 */
public class DefaultLogger extends Logger
{

    public DefaultLogger()
    {
        this( "Logger" );
    }

    public DefaultLogger( String name )
    {
        super( name, null );
        FileHandler handler = null;
        try
        {
            handler = new FileHandler( "log%g%u.txt" );
            handler.setFormatter( new SimpleFormatter() );
        } catch ( SecurityException e )
        {
            e.printStackTrace();
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
        this.addHandler( handler );
    }
}
