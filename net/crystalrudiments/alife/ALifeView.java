
package net.crystalrudiments.alife;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;

import net.crystalrudiments.util.SQueue;

/**
 * This is a panel which represents a 'living' graphic image.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam Davis </A>
 * @version Version 2.0, April 28, 2005
 */
public class ALifeView extends JPanel implements ILifeView
{

    /*-----------------------------------------------------------------------*/
    // THE VIEW
    /*-----------------------------------------------------------------------*/

    /** bDEBUG = true if debugging */
    private static final boolean bDEBUG = false;

    private static final boolean IS_DOUBLE_BUFFERED = false;

    /** At what resolution to start drawing. */
    //								      4 4^2 4^3 4^4 4^5 4^6 4^7 4^8
    private static final int[] RES =
    { 1, 5, 21, 85, 341, 1365, 5461, 21845, 87381 };

    private static final int[] SIDE =
    { 8, 16, 32, 64, 128, 256, 512, 1024, 2048 };

    private ILifeModel model; //the model.

    private boolean paint_gradual;

    /**
     * Used for gradual resolution painting. Is filled with Box's
     */
    private SQueue paintQ;

    /**
     * Default constructor.
     */
    public ALifeView()
    {
        this( new ALifeModel() );
    }

    /**
     * Constructor.
     */
    public ALifeView( ILifeModel model )
    {
        this( model, false );
    }

    /**
     * Constructor.
     */
    public ALifeView( ILifeModel model, boolean paint_gradual )
    {
        super( IS_DOUBLE_BUFFERED );
        //super.setOpaque(true);
        this.model = model;
        this.paintQ = new SQueue();
        this.paint_gradual = paint_gradual;
    }

    public ILifeModel getModel()
    {
        return model;
    }

    public void setModel( ILifeModel m )
    {
        model = new ALifeModel( m );
        paintQ.clear();
    }
    
    public void resetModel()
    {
        model = new ALifeModel();
        paintQ.clear();
    }

    /*-----------------------------------------------------------------------*/
    //The paint methods.
    /**
     * The paintComponent method. Overides paintComponent( Graphics g)
     * 
     * @param g
     *            The Graphics object that is used to paint on this panel.
     */
    public void paintComponent( Graphics g )
    {
        model.setWidth( this.getWidth() );
        model.setHeight( this.getHeight() );

        // I call the super paintComponent
        super.paintComponent( g );

        model.paintModel( g );
    }

    class Box
    {

        public int x;

        public int y;

        public int width;

        public int height;

        public Box( int x, int y, int w, int h )
        {
            width = w;
            height = h;
            this.x = x;
            this.y = y;
        }
    }

    /**
     * DEBUG main.
     */
    public static void main( String args[] )
    {
        Frame f = new Frame();
        ALifeView lv = new ALifeView();

        f.addWindowListener( new WindowAdapter()
        {

            public void windowClosing( WindowEvent e )
            {
                System.exit( 0 );
            }
        } );

        f.add( lv );
        f.setSize( 100, 120 );
        f.show();
    }

}
