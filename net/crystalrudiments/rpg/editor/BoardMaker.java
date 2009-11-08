package net.crystalrudiments.rpg.editor;

import net.crystalrudiments.rpg.Board;
import net.crystalrudiments.rpg.Messages;
import net.crystalrudiments.rpg.land.LandMaker;

/**
 * Creates new game "Board" files.
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @see net.crystalrudiments.rpg.land.LandMaker
 */
public class BoardMaker extends Board implements EditorConstants, IBoardMaker
{

    //private static final int percent_of_depth = 26 ; //more == more walls,
    // less == less walls

    private LandMaker landMaker = new LandMaker();
    
    /** Creates a new file with given code and type and somewhat random tiles. */
    public void makeNewMap( String code, String type )
    {
        int tile = 0, otile = 0;
        boolean pattern = false;
        String xString = ""; //$NON-NLS-1$
        EditorHelp editor = new EditorHelp( this );
        int x, y;
        if ( type.equals( Messages.getString("BoardMaker.1", "rpg.editor") ) ) //$NON-NLS-1$
            tile = 1;
        else if ( type.equals( Messages.getString("BoardMaker.2", "rpg.editor") ) ) //$NON-NLS-1$
        {
            pattern = true;
            tile = 120;
            otile = 101;
        } else if ( type.equals( Messages.getString("BoardMaker.3", "rpg.editor") ) ) //$NON-NLS-1$
        {
            tile = 101;
            otile = 120;
        } else if ( type.equals( Messages.getString("BoardMaker.4", "rpg.editor") ) ) //$NON-NLS-1$
        {
            tile = 401;
            otile = toolTile[EditorHelp.getToolTileOf( Messages.getString("BoardMaker.5", "rpg.editor") )]; //$NON-NLS-1$
        } else if ( type.equals( Messages.getString("BoardMaker.6", "rpg.editor") ) ) //$NON-NLS-1$
            tile = 501;
        else if ( type.equals( Messages.getString("BoardMaker.7", "rpg.editor") ) ) //$NON-NLS-1$
        {
            tile = 201;
            otile = 101;
        } else if ( type.equals( Messages.getString("BoardMaker.8", "rpg.editor") ) ) //$NON-NLS-1$
        {
            tile = 301;
            otile = 101;
        } else if ( type.equals( Messages.getString("BoardMaker.9", "rpg.editor") ) ) //$NON-NLS-1$
            tile = 602;
        else if ( type.equals( Messages.getString("BoardMaker.10", "rpg.editor") ) ) //$NON-NLS-1$
        {
            pattern = true;
            tile = 210;
        } else if ( type.equals( Messages.getString("BoardMaker.11", "rpg.editor") ) ) //$NON-NLS-1$
        {
            tile = 560;
        } else
        {
            System.err.println( Messages.getString("BoardMaker.12", "rpg.editor") + type ); //$NON-NLS-1$
            return;
        }
        landMaker.makeLand();
        for ( x = 0; x < landMaker.getArraySize() * 2; x++ )
            for ( y = 0; y < landMaker.getArraySize() * 2; y++ )
            {
                if ( !getGround( x / 2, y / 2 ) || otile == 0 )
                {
                    if ( pattern )
                        setTile( x, y, EditorHelp.patternTile( x, y, tile ) );
                    else
                        setTile( x, y, EditorHelp.tileRandomize( tile ) );
                } else
                {
                    setTile( x, y, EditorHelp.tileRandomize( otile ) );
                }
            }
        if ( otile != 0 )
        {
            for ( x = 0; x < landMaker.getArraySize() * 2; x++ )
                for ( y = 0; y < landMaker.getArraySize() * 2; y++ )
                {
                    if ( getGround( x / 2, y / 2 ) ) editor.smoothTile( x, y, true );
                }
        }
        saveMap( code, 1 );
    }


    /** Returns an int from 0 to inum. */
    public static int random( int inum )
    {
        double d = Math.random() * inum;
        return ( int ) d;
    }

    public int getHeight( int x, int y )
    {
        return landMaker.getHeight(x, y);
    }

    public boolean getGround( int x, int y )
    {
        return landMaker.getGround( x, y );
    }
}

