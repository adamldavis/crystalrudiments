/*
 * Created on Aug 28, 2005 by Adam. 
 * GameModel
 */
package net.crystalrudiments.rpg.game;

import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import net.crystalrudiments.common.GlobalOptions;
import net.crystalrudiments.common.logging.LoggerFactory;
import net.crystalrudiments.rpg.MapPanel;
import net.crystalrudiments.rpg.Messages;
import net.crystalrudiments.rpg.MoveException;
import net.crystalrudiments.rpg.person.Player;


/**
 * Contains the board and player and logic to manipulate them.
 * Copyright: 2005
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, Aug 28, 2005
 */
public class GameModel
{

    private static final Logger LOG = LoggerFactory.getDefaultLogger();

    private static final boolean OMNIVISION = true;

    private static final boolean bDEBUG = GameMain.bDEBUG;
    
    private IGameBoard board = null;
    
    private Player player = null;
    
    private GlobalOptions options = new GlobalOptions(Messages.getString("GameView.options.xml", "rpg.game")); //$NON-NLS-1$

    private MapPanel map = null;

    private IGame game  = null;
    
    
    public GameModel( MapPanel map, IGame game )
    {
        this.map = map;
        this.game = game;
    }
    
    public void newGame()
    {
        Object message = Messages.getString("GameView.19", "rpg.game"); //$NON-NLS-1$
        String name = JOptionPane.showInputDialog( map, message );
        if ( name == null ) return;

        player = new Player( name );

        board = new GameBoard(); //loads defaults...
        board.setPeopleFile( name + Messages.getString("GameView.20", "rpg.game") ); //$NON-NLS-1$
        board.setObjectsFile( name + Messages.getString("GameView.21", "rpg.game") ); //$NON-NLS-1$
        board.setChangesFile( name + Messages.getString("GameView.22", "rpg.game") ); //$NON-NLS-1$
        map.setBoard( board );
        map.setOmnivision( OMNIVISION ); //see
                                         // all<-----------------------------<!
        map.showPlayer();
        saveState();
    }

    public void saveState()
    {
        if ( board == null || player == null ) return;

        LOG.info( Messages.getString("GameView.23", "rpg.game") ); //$NON-NLS-1$
        board.save();

        try
        {
            player.save();
        } catch ( IOException ioe )
        {
            String msg = Messages.getString("GameView.24", "rpg.game") + ( bDEBUG ? ioe.toString() : "" ); //$NON-NLS-1$ //$NON-NLS-2$
            LOG.severe( msg );
            JOptionPane.showMessageDialog( null, msg );
            return;
        }
        JOptionPane.showMessageDialog( map, Messages.getString("GameView.26", "rpg.game") ); //$NON-NLS-1$
    }

    public void loadState()
    {
        Object message = Messages.getString("GameView.27", "rpg.game"); //$NON-NLS-1$
        String name = JOptionPane.showInputDialog( map, message );
        if ( name == null ) return;
        try
        {
            player = Player.loadPlayer( name + Messages.getString("GameView.28", "rpg.game") ); //$NON-NLS-1$
        } catch ( IOException ioe )
        {
            String msg = Messages.getString("GameView.29", "rpg.game") + ( bDEBUG ? ioe.toString() : "" ); //$NON-NLS-1$ //$NON-NLS-2$
            LOG.severe( msg );
            JOptionPane.showMessageDialog( null, msg );
            return;
        }
        board = new GameBoard( player.getMapCode(), name + Messages.getString("GameView.31", "rpg.game"), name + Messages.getString("GameView.32", "rpg.game"), name + Messages.getString("GameView.33", "rpg.game") ); //loads //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                                                                                                    // defaults...
        map.setBoard( board );
        map.setOmnivision( OMNIVISION ); //see
                                         // all<-----------------------------<!
        map.showPlayer();
        map.setPos( player.getX(), player.getY() );
    }
    
    public void spaceBar()
    {
        if ( player != null ) spaceBar( player.getX(), player.getY() );
    }
    


    /**
     * Actions caused by spacebar: pick up object, move on stairs, activate
     * tile, etc.
     */
    public void spaceBar( int px, int py )
    {
        int objectNum = board.getObject( px, py );
        if ( objectNum > -1 )
        {
            player.pickUp( objectNum );
            board.removeObjectAt( px, py );
            String fname = options.getSoundFileName( Messages.getString("GameView.pickup", "rpg.game") ); //$NON-NLS-1$
            if ( fname != null ) game.playSound( fname );
        } else
        {
            int tile = board.getTile( px, py );
            if ( tile == 23 )
            {//stairs...
                try
                {
                    board.moveUp();
                } catch ( MoveException me )
                {
                    me.printStackTrace();
                }
            }
            if ( tile == 24 )
            {
                try
                {
                    board.moveDown();
                } catch ( MoveException me )
                {
                    me.printStackTrace();
                }
            }
        }
    }

    /**
     * @return Returns the board.
     */
    public IGameBoard getBoard()
    {
        return board;
    }
    
    /**
     * @return Returns the player.
     */
    public Player getPlayer()
    {
        return player;
    }
}
