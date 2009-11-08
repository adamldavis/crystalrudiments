/*
 * Revisions:
 *
 * 6/17/03 - Version 1.0
 */
package net.crystalrudiments.rpg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFrame;

import net.crystalrudiments.common.EncodingUtil;
import net.crystalrudiments.rpg.editor.EditorBoard;

/**
 * Program for creating new map files.
 * <P>
 * 
 * @author <A HREF="mailto:gte459u@mail.gatech.edu">Adam Davis </A>
 */
public class BoardMakerWizard extends JFrame implements RPGConstants, MouseListener
{

    public static final String MAP_MAKER_0_1 = "Map Maker 0.1";

    private IBoard board = null;

    private final int X_OFF = 28; //left space

    private final int Y_OFF = 38; //top spcae

    private final int X_SIZ = 12; //width of each block (in pixels).

    private final int Y_SIZ = 12; //height

    private final int X_MAX = 57; //max x considered

    private final int Y_MAX = 60;

    private final int X_MIN = 0;

    private final int Y_MIN = 17;

    private boolean onframe = false;

    private boolean[][] table; //for storing whether or not a map exists.

    private Point point = null;

    public BoardMakerWizard()
    {
        super( MAP_MAKER_0_1 );
        this.setSize( 800, 600 );

        this.addWindowListener( new WindowAdapter()
        {

            public void windowClosing( WindowEvent e )
            {
                dispose();
                System.exit( 0 );
            }
        } );
        table = new boolean[( X_MAX - X_MIN )][( Y_MAX - Y_MIN )];
        refreshTable();

        this.addMouseListener( this );
        this.repaint();
    }

    /** Call this just to display the given location code. */
    public BoardMakerWizard( String code )
    {
        super( MAP_MAKER_0_1 );
        this.setSize( 800, 600 );

        table = new boolean[( X_MAX - X_MIN )][( Y_MAX - Y_MIN )];
        refreshTable();
        setCode( code );
        this.addMouseListener( this );
        this.repaint();
    }

    public void setCode( String code )
    {
        point = new Point( EncodingUtil.alphaDecode( code.substring( 0, 1 ) ), EncodingUtil.alphaDecode( code
                .substring( 1, 2 ) ) );
    }

    private void refreshTable()
    {
        for ( int i = X_MIN; i < X_MAX; i++ )
        {
            for ( int j = Y_MIN; j < Y_MAX; j++ )
            {
                refreshTable( i, j );
            }//j
        }//i
    }

    private void refreshTable( int i, int j )
    {
        String code = EncodingUtil.alphaEncode( i ) + EncodingUtil.alphaEncode( j ) + "M";
        table[i - X_MIN][j - Y_MIN] = !mapExists( code );
    }

    /**
     * Loads the given image and waits for it to load .
     */
    private Image loadImage( String fileName )
    {
        Image img = Toolkit.getDefaultToolkit()
                .getImage( IMAGE_DIR + System.getProperty( "file.separator" ) + fileName );
        try
        {
            MediaTracker tracker = new MediaTracker( this );
            tracker.addImage( img, 0 );
            tracker.waitForID( 0 );
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
        return img;
    }

    public void paint( Graphics g )
    {
        Image img = loadImage( "miniMap3.jpg" );
        g.drawImage( img, 0, 0, null );
        int ix = img.getWidth( null );
        int iy = img.getHeight( null );
        if ( ix > 0 && iy > 0 )
        {
            this.setSize( ix + 0, iy + 0 );
        }
        if ( !onframe ) return;
        g.setColor( Color.red );
        for ( int i = X_MIN; i < X_MAX; i++ )
        {
            for ( int j = Y_MIN; j < Y_MAX; j++ )
            {

                if ( table[i - X_MIN][j - Y_MIN] )
                {
                    g.drawOval( i * X_SIZ + X_OFF, ( j - Y_MIN ) * Y_SIZ + Y_OFF, X_SIZ, Y_SIZ );
                }
            }//j
        }//i
        if ( point != null )
        {
            int px = ( int ) point.getX() * X_SIZ + X_OFF;
            int py = ( int ) ( point.getY() - Y_MIN ) * Y_SIZ + Y_OFF;
            g.setColor( Color.white );
            g.drawOval( px, py, X_SIZ, Y_SIZ );
        }
    }

    public static boolean mapExists( String code )
    {
        File file = ( new File( MAP_DIR + File.separator + "map" + code + ".txt" ) );
        return file.exists();
    }

    /** Invoked when the mouse enters a component. */
    public void mouseEntered( MouseEvent e )
    {
        //System.out.println("Mouse entered map.");
        onframe = true;
        this.repaint();
    }

    /** Invoked when the mouse exits a component. */
    public void mouseExited( MouseEvent e )
    {
        onframe = false;
        this.repaint();
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
        x1 = e.getX();
        y1 = e.getY();
        //System.out.print("\tx = " + x1 + "\t y = " + y1);
        x1 = ( x1 - X_OFF ) / X_SIZ;
        y1 = ( y1 - Y_OFF ) / Y_SIZ + 17;
        String code = EncodingUtil.alphaEncode( x1 ) + EncodingUtil.alphaEncode( y1 ) + "M";

        if ( !mapExists( code ) )
        {
            EditorBoard.makeNewMap( code );
            refreshTable( x1, y1 );
        } else
        {
            System.out.println( "\t code = " + code + " --- Exists." );
        }
    }

    /**
     * Invoked when the mouse has been clicked on a component.
     */
    public void mouseClicked( MouseEvent e )
    {
        int modifiers = e.getModifiers();
        String str = "";
        //button1==left, button2==middle, button3==right.
        //don't use ALT - its used by the OS.
        //only one button can be clicked at a time.
    }

    public static void main( String[] args )
    {
        BoardMakerWizard bm = new BoardMakerWizard();
        bm.show();
    }
}
