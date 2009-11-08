package net.crystalrudiments.rpg.game;

import java.util.logging.Logger;

import net.crystalrudiments.common.logging.LoggerFactory;
import net.crystalrudiments.rpg.RPGConstants;
import net.crystalrudiments.rpg.person.Agent;
import net.crystalrudiments.rpg.person.Person;
import net.crystalrudiments.rpg.person.Player;

/**
 * Represents an object in the player's inventory.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis </A>
 * @version 1.0, June 18, 2003
 */
public class GameObject implements RPGConstants, IGameObject
{

    private static final Logger LOG = LoggerFactory.createLogger( GameObject.class );

    int type = 0;

    /** where is it stored? (lefthand, head, body...)*/
    String location = null;

    /** how long the player's had it in seconds.*/
    long howLong = 0; 

    public GameObject( int type )
    {
        this( type, null, 0 );
    }

    public GameObject( int type, String location, long howLong )
    {
        this.type = type;
        this.location = location;
        this.howLong = howLong;
    }

    //---ACCESSORS:
    public int getType()
    {
        return type;
    }

    public String toString()
    {
        return getType() + ":" + getName();
    }

    public String getName()
    {
        return OBJECT_NAMES[type];
    }

    public long getHowLong()
    {
        return howLong;
    }

    //---MODIFIERS:
    public void addTime( int sec )
    {
        howLong += sec;
        makeTimeEffect();
    }

    public void setLocation( String loc )
    {
        location = loc;
    }

    //---CLONE, COMPARE
    public int compareTo( Object obj )
    {
        if ( obj instanceof IGameObject )
        {
            return type - ( ( IGameObject ) obj ).getType();
        } else
            return -1;
    }

    public boolean equals( Object obj )
    {
        return ( compareTo( obj ) == 0 );
    }

    public Object clone()
    {
        Object ret = new GameObject( type, location, howLong );
        return ret;
    }

    //---ACTIONS:
    /** Activates this object on the given object. */
    public void activateOn( IGameObject go )
    {
        //TODO: File in behavior for all objects!
        LOG.info( "Activating " + this + " on " + go );

    }

    /**
     * Activate an object on myself or another person.
     */
    public void activateOn( Person person )
    {
        LOG.info( "Activating " + this + " on " + person );
        if ( person instanceof Player )
        {
            //instance of Player...
            activateOn( ( Player ) person );
        } else
        {
            //give it to or use it on that person.
            activateOn( ( Agent ) person );
        }
    }

    /** Should be overridden by sub-classes.*/
    public void activateOn( Agent agent )
    {
    }

    /** Should be overridden by sub-classes.*/
    public void activateOn( Player player )
    {
    }

    /** Should be overridden by sub-classes.*/
    protected void makeTimeEffect()
    {
        //Do something time based.
    }

}
