/*
 * 09/24/03 - version 1.
 */
package net.crystalrudiments.rpg.game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.crystalrudiments.rpg.Messages;
import net.crystalrudiments.rpg.RPGConstants;
import net.crystalrudiments.rpg.person.AIPerson;
import net.crystalrudiments.swing.ImagePanel;

/**
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 */
public class TalkingWindow extends JFrame implements RPGConstants, ActionListener
{

    public static final String[] PEOPLE_IMAGES = Messages.getArray("TalkingWindow.", 0, 63, "rpg.game"); 

    AIPerson person = null;

    JPanel topPanel = null;

    ImagePanel imgPanel = null;

    JEditorPane editorPane = null;

    JScrollPane editorPaneScrollPane = null;

    JButton button = new JButton( Messages.getString("TalkingWindow.continue", "rpg.game") ); //$NON-NLS-1$

    public TalkingWindow( AIPerson person )
    {
        super( person.getName() );
        this.person = person;
        this.getContentPane().setLayout( new GridLayout( 2, 1, 0, 0 ) );

        topPanel = new JPanel();
        topPanel.setLayout( new BorderLayout() );

        int num = person.getImageNum();
        editorPane = new JEditorPane();
        editorPaneScrollPane = new JScrollPane( editorPane );
        editorPane.setEnabled( false );

        imgPanel = new ImagePanel( IMAGE_DIR + File.separator + PEOPLE_IMAGES[num] );
        imgPanel.setSize( 128, 128 );

        topPanel.add( imgPanel );
        topPanel.add( button, BorderLayout.EAST );
        button.addActionListener( this );

        this.getContentPane().add( topPanel );
        this.getContentPane().add( editorPaneScrollPane );
        this.setSize( 525, 525 );

        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = ( dim.width - w ) / 2;
        int y = ( dim.height - h ) / 2;

        // Move the window
        this.setLocation( x, y );
        talk();
    }

    public void talk()
    {
        //String message = "Choose a response.";
        String say = person.talk();
        if ( say.indexOf( "[end]" ) > -1 ) //$NON-NLS-1$
        {
            shutDown();
        }
        editorPane.setText( say );
    }

    public void shutDown()
    {
        this.hide();
    }

    public void actionPerformed( ActionEvent e )
    {
        //System.out.println(e.paramString());
        Object source = e.getSource();
        if ( source instanceof JButton )
        {

            talk();
        }
    }

    public static void main( String[] args )
    {
        AIPerson person = new AIPerson( "default", 0, 0, 0 ); //$NON-NLS-1$
        TalkingWindow tw = new TalkingWindow( person );
        tw.show();
    }
}
