
package net.crystalrudiments.rpg.game;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import net.crystalrudiments.common.logging.LoggerFactory;
import net.crystalrudiments.rpg.Messages;
import net.crystalrudiments.rpg.RPGConstants;
/**
 * 
 * This is the event handler for this program.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, September 22, 2003
 */
public class GameControl implements RPGConstants, IGameControl
{

    /*-----------------------------------------------------------------------*/
    // THE CONTROLLER
    /*-----------------------------------------------------------------------*/

    /** bDEBUG = true if debugging. */
    private static final boolean bDEBUG = false;

    /** The reference to the gui that will be the view for this program. */
    private JFrame view = null;

    /** The interface for handling actual game events. */
    private IGame game = null;

    /**
     * Creates a new event handler that is bound by the gui view that's passed
     * as a parameter.
     * 
     * @param view
     *            The view that this control connects to.
     */
    public GameControl( JFrame view, IGame game )
    {
        if ( bDEBUG ) System.out.println( "In GameControl( JMiedor view )" ); //$NON-NLS-1$
        // If the gui is null, then this class is being used improperly and
        // should
        // exit.
        if ( view == null )
        {
            System.err.println( "Error: view == null" ); //$NON-NLS-1$
            LoggerFactory.getDefaultLogger().severe( "GameControl: view == null" ); //$NON-NLS-1$
            System.exit( 0 );
        }

        // Add the view to the control.
        this.view = view;//<------------------------------
        this.game = game;
    }

    /**
     * Refreshes the view.
     *  
     */
    public void refreshView()
    {
        view.repaint();
    }

    /**
     * This function recieves an action event and interprets the event to take
     * the proper action. It receives all the button actions from the eventHandler.
     * 
     * @param event
     *            The action event that needs to be processed.
     */
    public void actionPerformed( ActionEvent event )
    {
        if ( event == null )
        {// No action to process.
            LoggerFactory.getDefaultLogger().info( "actionPerformed: event==null" ); //$NON-NLS-1$
            return;
        }
        String str = event.getActionCommand();

        LoggerFactory.getDefaultLogger().info( "In actionPerformed( ActionEvent event )" + "... the event is: " + str ); //$NON-NLS-1$ //$NON-NLS-2$

        if ( str == null ) //null
        {
        } else if ( str.equals( Messages.getString("GameControl.inst", "rpg.game") ) ) //"Instructions" //$NON-NLS-1$
        {
            game.showInstructions();
        } else if ( str.equals( Messages.getString("GameControl.new", "rpg.game") ) ) //"New" //$NON-NLS-1$
        {
            game.newGame();
        } else if ( str.equals( Messages.getString("GameControl.save", "rpg.game") ) ) //"Save" //$NON-NLS-1$
        {
            game.saveState();
        } else if ( str.equals( Messages.getString("GameControl.load", "rpg.game") ) ) //"Load" //$NON-NLS-1$
        {
            game.loadState();
        } else if ( str.equals( Messages.getString("GameControl.exit", "rpg.game") ) ) //"Exit" //$NON-NLS-1$
        {
            view.setVisible( false );
            view.dispose();
            System.exit( 0 );
        }//if

    }

    /** Invoked when a key has been pressed. */
    public void keyPressed( KeyEvent e )
    {
        switch ( e.getKeyCode() ) {
        case KeyEvent.VK_UP:
            game.move( 'u' );
            break;
        case KeyEvent.VK_DOWN:
            game.move( 'd' );
            break;
        case KeyEvent.VK_RIGHT:
            game.move( 'r' );
            break;
        case KeyEvent.VK_LEFT:
            game.move( 'l' );
            break;
        default:
        }
    }

    /** Invoked when a key has been released. */
    public void keyReleased( KeyEvent e )
    {
        ;
    }

    /** Invoked when a key has been typed. */
    public void keyTyped( KeyEvent e )
    {
        //System.out.println( e.getKeyChar());
        //System.out.println( e.getKeyModifiersText( e.getModifiers() ));
        game.keyTyped( e.getKeyChar(), KeyEvent.getKeyModifiersText( e.getModifiers() ) );
    }

    /**
     * Invoked when the mouse has been clicked on a component.
     */
    public void mouseClicked( MouseEvent e )
    {
        int modifiers = e.getModifiers();
        String str = ""; //$NON-NLS-1$
        //button1==left, button2==middle, button3==right.
        //don't use ALT - its used by the OS.
        //only one button can be clicked at a time.
        if ( ( modifiers & MouseEvent.BUTTON1_MASK ) == MouseEvent.BUTTON1_MASK )
        {
            //str += ("Button 1 clicked in map. ");
            game.leftClick( e.getX(), e.getY(), e.isShiftDown(), e.isControlDown() );
        } else if ( ( modifiers & MouseEvent.BUTTON2_MASK ) == MouseEvent.BUTTON2_MASK )
        {
            //str += ("Button 2 clicked in map. ");
            game.middleClick( e.getX(), e.getY(), e.isShiftDown(), e.isControlDown() );
        } else if ( ( modifiers & MouseEvent.BUTTON3_MASK ) == MouseEvent.BUTTON3_MASK )
        {
            //str += ("Button 3 clicked in map. ");
            game.rightClick( e.getX(), e.getY(), e.isShiftDown(), e.isControlDown() );
        }
    }

    /** Invoked when the mouse enters a component. */
    public void mouseEntered( MouseEvent e )
    {
        //System.out.println("Mouse entered map.");
    }

    /** Invoked when the mouse exits a component. */
    public void mouseExited( MouseEvent e )
    {
    }

    /** Invoked when a mouse button has been pressed on a component. */
    public void mousePressed( MouseEvent e )
    {
    }

    /** Invoked when a mouse button has been released on a component. */
    public void mouseReleased( MouseEvent e )
    {
        ;
    }

}
