/*
 * Created on Jan 27, 2005 by Adam. 
 * Written to replace err print statements.
 *
 */
package net.crystalrudiments.common.logging;

import java.util.logging.Logger;

/**
 * LoggerFactory creates Loggers.
 */
public class LoggerFactory
{

    private static final Logger logger = new DefaultLogger();

    public static Logger getDefaultLogger()
    {
        return logger;
    }

    /**
     *  No instance should ever be made.
     */
    private LoggerFactory()
    {
    }

    public static Logger createLogger()
    {
        return new DefaultLogger();
    }

    public static Logger createLogger(String name)
    {
        return new DefaultLogger(name);
    }

    public static Logger createLogger(Class clazz)
    {
        return new DefaultLogger( clazz.getName() );
    }
}
