/**
 * EditorControl.java
 *
 * <P>
 * EditorControl Class
 *
 * Revisions:  1.0  June 28, 2003
 *                  Created EditorControl.java.
 *
 */
package net.crystalrudiments.rpg.editor;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import net.crystalrudiments.rpg.Messages;

/**
 * 
 * This is the event handler for this program.
 * 
 * @author <A HREF="http://www.adamldavis.com/">Adam L. Davis </A>
 * @version Version 1.0, June 28, 2003
 */
public class EditorControl implements  EditorConstants, IEditorControl
{

    /*-----------------------------------------------------------------------*/
    // THE CONTROLLER
    /*-----------------------------------------------------------------------*/

    /** bDEBUG = true if debugging. */
    private static final boolean bDEBUG = false;

    /** The reference to the gui that will be the view for this program. */
    private IEditor editor = null;

    private JFrame view = null;
    /**
     * Creates a new event handler that is bound by the gui view that's passed
     * as a parameter.
     * 
     * @param editor
     *            The view that this control connects to.
     */
    public EditorControl( JFrame view, IEditor editor )
    {
        if ( bDEBUG ) System.out.println( "In EditorControl( JMEditor view )" ); //$NON-NLS-1$
        // If the gui is null, then this class is being used improperly and
        // should
        // exit.
        if ( editor == null )
        {
            System.err.println( "Error: view == null" ); //$NON-NLS-1$
            System.exit( 0 );
        }

        // Add the view to the control.
        this.editor = editor;//<------------------------------
        this.view = view;

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
     * the proper action. It receives all the button actions from the view.
     * 
     * @param event
     *            The action event that needs to be processed.
     */
    public void actionPerformed( ActionEvent event )
    {
        if ( event == null )
        {// No action to process.
            System.err.println( "actionPerformed: event==null" ); //$NON-NLS-1$
            return;
        }
        String str = event.getActionCommand();

        if ( bDEBUG )
        {
            System.out.println( "In actionPerformed( ActionEvent event )" + //$NON-NLS-1$
                    "... the event is: " + str ); //$NON-NLS-1$
        }

        if ( str == null ) //null
        {
        } else if ( str.equals( Messages.getString( "EditorControl.instructions", "rpg.editor" ) ) ) //"Instructions"
                                                                                                     // //$NON-NLS-1$
        {
            editor.showInstructions();
        } else if ( str.equals( Messages.getString( "EditorControl.new", "rpg.editor" ) ) ) //"New"
                                                                                            // //$NON-NLS-1$
        {
            //view.reinitPics();
            refreshView();
        } else if ( str.equals( Messages.getString( "EditorControl.save", "rpg.editor" ) ) ) //"Save"
                                                                                             // //$NON-NLS-1$
        {
            editor.saveState();
        } else if ( str.equals( Messages.getString( "EditorControl.load", "rpg.editor" ) ) ) //"Load"
                                                                                             // //$NON-NLS-1$
        {
            editor.loadState();
        } else if ( str.equals( Messages.getString( "EditorControl.exit", "rpg.editor" ) ) ) //"Exit"
                                                                                             // //$NON-NLS-1$
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
            editor.move( 'u' );
            break;
        case KeyEvent.VK_DOWN:
            editor.move( 'd' );
            break;
        case KeyEvent.VK_RIGHT:
            editor.move( 'r' );
            break;
        case KeyEvent.VK_LEFT:
            editor.move( 'l' );
            break;
        default:
        }
    }

    /** Invoked when a key has been released. */
    public void keyReleased( KeyEvent e )
    {

    }

    /** Invoked when a key has been typed. */
    public void keyTyped( KeyEvent e )
    {
        //System.out.println( e.getKeyChar());
        //System.out.println( e.getKeyModifiersText( e.getModifiers() ));
        editor.keyTyped( e.getKeyChar(), e.getKeyModifiersText( e.getModifiers() ) );
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
        if ( ( modifiers & e.BUTTON1_MASK ) == e.BUTTON1_MASK )
        {
            //str += ("Button 1 clicked in map. ");
            editor.leftClick( e.getX(), e.getY(), e.isShiftDown(), e.isControlDown() );
        } else if ( ( modifiers & e.BUTTON2_MASK ) == e.BUTTON2_MASK )
        {
            //str += ("Button 2 clicked in map. ");
            editor.middleClick( e.getX(), e.getY(), e.isShiftDown(), e.isControlDown() );
        } else if ( ( modifiers & e.BUTTON3_MASK ) == e.BUTTON3_MASK )
        {
            //str += ("Button 3 clicked in map. ");
            editor.rightClick( e.getX(), e.getY(), e.isShiftDown(), e.isControlDown() );
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

    int x1 = 0;

    int y1 = 0;

    /** Invoked when a mouse button has been pressed on a component. */
    public void mousePressed( MouseEvent e )
    {
        x1 = e.getX();
        y1 = e.getY();
    }

    /** Invoked when a mouse button has been released on a component. */
    public void mouseReleased( MouseEvent e )
    {
        int x2, y2;
        float dx, dy;
        int diffx, diffy;
        x2 = e.getX();
        y2 = e.getY();
        diffx = ( x2 - x1 );
        diffy = ( y2 - y1 );
        if ( diffx == 0 && diffy == 0 ) return;

        if ( Math.abs( diffy ) > Math.abs( diffx ) )
        {
            dy = ( diffy > 0 ) ? 1f : -1f;
            dx = ( float ) diffx / Math.abs( diffy );
        } else
        {
            dx = ( diffx > 0 ) ? 1f : -1f;
            dy = ( float ) diffy / Math.abs( diffx );
        }
        //MouseEvent(Component source, int id, long when, int modifiers, int x,
        // int y, int clickCount, boolean popupTrigger)
        boolean stop = false;
        for ( float i = ( float ) x1, j = ( float ) y1; !stop; i += dx, j += dy )
        {
            MouseEvent me = new MouseEvent( ( Component ) e.getSource(), e.getID(), e.getWhen(), e.getModifiers(),
                    ( int ) i, ( int ) j, e.getClickCount(), false );
            if ( diffx > 0 )
                stop = ( i > x2 );
            else
                stop = ( i < x2 );
            if ( diffy > 0 )
                stop = stop || ( j > y2 );
            else
                stop = stop || ( j < y2 );

            mouseClicked( me );
        }
    }
}
