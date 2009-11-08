/*
 * Created on Jun 17, 2005 by Adam. 
 * IGameBoard
 */
package net.crystalrudiments.rpg.game;

import java.io.File;

import net.crystalrudiments.rpg.IBoard;
import net.crystalrudiments.rpg.person.Player;

/**
 * Represents the map (board) in the JMiedor game. Contains all Persons on the
 * board, all map tiles, objects, and sight information about the board.
 * <P>
 * The board acts as a liason between the characters and the {@link Player}.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, Jun 17, 2005
 */
public interface IGameBoard extends IBoard
{

    public void setChangesFile( String str );

    public void makeIfNeeded( File file, String str );

    /** Saves all quadrants and people. */
    public void save();

    /**
     * Moves the map by loading the proper files in the proper place given the
     * input.
     */
    public void moveMap( int oldQuad, int newQuad );

    //---------------------------ACCESSORS AND MODIFIERS::
    public void setTile( int x, int y, int tile );
}