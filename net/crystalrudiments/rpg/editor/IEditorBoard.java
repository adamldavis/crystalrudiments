/*
 * Created on Jun 15, 2005 by Adam. 
 * IEditorBoard
 */
package net.crystalrudiments.rpg.editor;

/**
 *  Represents the map (board) in the Editor. Contains all Persons on the
 * board, all map tiles, objects, and sight information about the board.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, Jun 15, 2005
 */
public interface IEditorBoard
{

    /** Saves only quadrants that have been changed and people and objects. */
    public void save();

    /**
     * Moves the map by loading the proper files in the proper place given the
     * input.
     */
    public void moveMap( int oldQuad, int newQuad );
}