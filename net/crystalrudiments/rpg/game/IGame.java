/*
 * Created on Jun 13, 2005 by Adam. 
 * IGameEventListener
 */
package net.crystalrudiments.rpg.game;

import net.crystalrudiments.rpg.person.Person;

/**
 * Interface for the object that handles actual events in the Game.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, Jun 13, 2005
 */
public interface IGame
{

    /**
     * Shows the Instructions.
     */
    public void showInstructions();

    public void newGame();

    public void saveState();

    public void loadState();

    public void rightClick( int x, int y, boolean shift, boolean control );

    public void leftClick( int x, int y, boolean shift, boolean control );

    public void middleClick( int x, int y, boolean shift, boolean control );

    public void keyTyped( char c, String modifiers );

    /**
     * Only moves map if player can move... Might change board if needed, (ex:
     * boulders).
     */
    public void move( char c );

    /**
     * Attempts to move the given Person in given direction and returns result
     * (pass/fail).
     */
    public boolean move( char c, Person person, IGameBoard board );

    /**
     * Actions caused by spacebar: pick up object, move on stairs, activate
     * tile, etc.
     */
    public void spaceBar( int px, int py );

    /**
     * Plays a sound; name given relative to sound folder.
     */
    public void playSound( String fileName );

    /**
     * not yet implemented.
     */
    public void playMidi( String fileName );
}