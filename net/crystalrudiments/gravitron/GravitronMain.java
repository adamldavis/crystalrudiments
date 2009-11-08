/*
 * Created on May 14, 2005 by Adam. 
 * GravitronMain
 */
package net.crystalrudiments.gravitron;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Contains the main method for running the <i>Gravitron</i> Application.
 * Allows the user to create a solar system and simulates it.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis </A>
 * @version Version 1.0, May 14, 2005
 */
public class GravitronMain
{

    private GravModel gm;

    private GravPanel pp;

    /**
     *  
     */
    public GravitronMain()
    {
        super();

        /* initiate inside stuff... */
        gm = new GravModel();
        pp = new GravPanel( gm );
    }

    public void show()
    {
        JFrame f = null;
        /* initiate JFrame. */
        f = new JFrame( "Gravitron - Adam L. Davis" );
        f.addWindowListener( new WindowAdapter() {

            public void windowClosed( WindowEvent arg0 )
            {
                System.exit( 0 );
            }
            
        });
        f.getContentPane().add( pp );
        f.setSize( 600, 400 );
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation( ( d.width - f.getWidth() ) / 2, (d.height - f.getHeight()) / 2 );
        f.setVisible( true );
        /* start */
        pp.pause( false );
    }

    public static void main( String[] args )
    {
        GravitronMain main = new GravitronMain();
        main.show();
    }
}
