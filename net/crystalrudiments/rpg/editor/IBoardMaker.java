/*
 * Created on Jun 14, 2005 by Adam. 
 * IBoardMaker
 */
package net.crystalrudiments.rpg.editor;


/**
 * Creates new game "Board" files.
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, Jun 14, 2005
 * @see net.crystalrudiments.rpg.Board
 */
public interface IBoardMaker
{

    /** Creates a new file with given code and type and somewhat random tiles. */
    public void makeNewMap( String code, String type );
}