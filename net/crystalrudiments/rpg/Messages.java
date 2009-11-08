/*
 * Created on Jun 14, 2005 by Adam. 
 * Messages
 */
package net.crystalrudiments.rpg;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Holds logic for loading externalized constants.
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, Jun 14, 2005
 */
public class Messages
{

    private static final String BUNDLE_NAME = "net.crystalrudiments.rpg.messages";//$NON-NLS-1$

    private static final ResourceBundle BUNDLE_RPG = ResourceBundle.getBundle( BUNDLE_NAME );

    private static final String BUNDLE_NAME2 = "net.crystalrudiments.rpg.editor.messages";//$NON-NLS-1$

    private static final ResourceBundle BUNDLE_RPG_EDITOR = ResourceBundle.getBundle( BUNDLE_NAME2 );

    private static final String BUNDLE_NAME3 = "net.crystalrudiments.rpg.game.messages";//$NON-NLS-1$

    private static final ResourceBundle BUNDLE_RPG_GAME = ResourceBundle.getBundle( BUNDLE_NAME3 );

    
    private static final Map bundleMap = new HashMap();
    {
        bundleMap.put("rpg", BUNDLE_RPG);
        bundleMap.put("rpg.editor", BUNDLE_RPG_EDITOR);
        bundleMap.put("rpg.game", BUNDLE_RPG_GAME);
    }
    
    private Messages()
    {
    }

    public static String getString( String key, String bundleName )
    {
        try
        {
            return ((ResourceBundle) bundleMap.get(bundleName)).getString( key );
        } 
        catch ( MissingResourceException e )
        {
            return '!' + key + '!';
        }
    }

    /** Uses properties file to get a static array of strings.
     * @param prefix Prefix of messages.
     * @param start Inclusive starting int.
     * @param end Inclusive ending.
     * @param bundleName Name of resource-bundle to use.
     * @return Array of strings from "prefix.start" to "prefix.end" from properties file.
     */
    public static String[] getArray(String prefix, int start, int end, String bundleName)
    {
        List list = new LinkedList();
        for (int i=start; i <= end; i++)
        {
            list.add( getString(prefix + i, bundleName) );
        }
        return (String[]) list.toArray( new String[list.size()] );
    }
}
