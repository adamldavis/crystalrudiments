/*
 * Created on Jun 18, 2005 by Adam. 
 * GameObjectFactory
 */
package net.crystalrudiments.rpg.game;


/**
 * Makes game object instances.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, Jun 18, 2005
 */
public class GameObjectFactory
{

    /**
     * 
     */
    private GameObjectFactory()
    {
    }
    
    public static IGameObject makeGameObject( int type )
    {
        return new GameObject( type );
    }
    

    public static IGameObject makeGameObject( int type, String location, long howLong )
    {
        return new GameObject( type, location, howLong );
    }
}
