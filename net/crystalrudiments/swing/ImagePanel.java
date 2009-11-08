package net.crystalrudiments.swing;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A panel for displaying some image. Java supports PNG and JPEG images
 * natively.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 */
public class ImagePanel extends JPanel
{

    private String fileName = null;

    private Image myImage = null;

    public ImagePanel()
    {
        super();
    }

    public ImagePanel( final String fname )
    {
        fileName = fname;
        final File file = new File( fname );
        Thread t = new Thread()
        {

            public void run()
            {
                if ( file.exists() )
                    loadImage( fname );
                else
                    System.err.println( "Error: image file \"" + fname + "\" not found." );
            }
        };
        t.start();
    }

    public void paint( Graphics g )
    {
        //System.out.println("paint called.");
        if ( myImage != null )
        {
            g.drawImage( myImage, 0, 0, null );
        }
    }

    private static int count = 0;

    /**
     * Loads the given image and waits for it to load.
     */
    public void loadImage( String fileName )
    {
        Image img = Toolkit.getDefaultToolkit().getImage( fileName );
        try
        {
            MediaTracker tracker = new MediaTracker( this );
            tracker.addImage( img, count );
            tracker.waitForID( count );
            count++;
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
        myImage = img;
    }

    public Image getImage()
    {
        return myImage;
    }

    public static void main( String[] args )
    {
        ImagePanel ip = new ImagePanel( "images" + File.separator + "alina1.png" );
        JFrame f = new JFrame( "View" );
        f.setSize( 128, 128 );
        f.getContentPane().setLayout( new BorderLayout() );
        f.getContentPane().add( ip );
        f.show();
        f.repaint();
    }
}