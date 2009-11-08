/*
 * Created on Jun 13, 2005 by Adam. 
 * IGameObject
 */
package net.crystalrudiments.rpg.game;

import java.io.Serializable;

import net.crystalrudiments.rpg.person.Agent;
import net.crystalrudiments.rpg.person.Person;
import net.crystalrudiments.rpg.person.Player;

/**
 * Interface for objects used in the game.
 * Contains constants used by objects.
 * Any object requiring specific behavior should implement
 * this interface.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, Jun 13, 2005
 */
public interface IGameObject extends Serializable, Comparable, Cloneable 
{
    final String RIGHT_HAND = "right-hand";
    final String LEFT_HAND = "left-hand";
    final String HEAD = "head";
    final String BODY = "body";

    //---ACCESSORS:
    public int getType();

    public String toString();

    public String getName();

    public long getHowLong();

    //---MODIFIERS:
    public void addTime( int sec );

    public void setLocation( String loc );

    //---CLONE, COMPARE
    public int compareTo( Object obj );

    public boolean equals( Object obj );

    public Object clone();

    //---ACTIONS:
    public void activateOn( IGameObject go );

    /**
     * Activate an object on myself or another person.
     */
    public void activateOn( Person person );

    public void activateOn( Agent agent );

    public void activateOn( Player player );
}