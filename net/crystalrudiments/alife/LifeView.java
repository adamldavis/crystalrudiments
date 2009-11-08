
package net.crystalrudiments.alife;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPanel;

/**
 * 
 * This is a panel which represents a 'living' graphic image.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis </A>
 * @deprecated May 5, 2005, Use {@link net.crystalrudiments.alife.ALifeView}.
 */
public class LifeView extends JPanel implements LifeConstants, ILifeView
{

    /*-----------------------------------------------------------------------*/
    // THE VIEW
    /*-----------------------------------------------------------------------*/

    /** bDEBUG = true if debugging */
    private static final boolean bDEBUG = false;

    private static final boolean IS_DOUBLE_BUFFERED = false;

    private ALifeModel model; //the model.

    /**
     * Default constructor.
     */
    public LifeView()
    {
        super( IS_DOUBLE_BUFFERED );
        model = new ALifeModel();
    }

    public LifeView( ALifeModel model )
    {
        super( IS_DOUBLE_BUFFERED );
        this.model = model;
    }

    public ILifeModel getModel()
    {
        return model;
    }
    
    /**
     * @see net.crystalrudiments.alife.ILifeView#setModel(net.crystalrudiments.alife.ILifeModel)
     */
    public void setModel( ILifeModel m )
    {
        model = new ALifeModel( m );
    }

    /**
     * Return a slightly mutated version of self.
     * 
     * public LifeView reproduce() { return ( new
     * LifeView(this.model.reproduce()) ); }
     */

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
        int i = 0;
        int j = 0;
        int width;
        int height;
        if ( bDEBUG ) System.out.println( "In LifeView: paintComponent." );

        // I call the super paintComponent
        super.paintComponent( g );

        width = this.getWidth();
        height = this.getHeight();

        g.setColor( Color.black );
        g.drawRect( 0, 0, width - 1, height - 1 );
        g.setColor( Color.white );
        g.fillRect( 1, 1, width - 2, height - 2 );

        //Point p1 = new Point();
        //Point p2 = new Point();

        //do stuff
        /*
         * for (i=1; i <REPEAT; i++) { int x1, x2, y1, y2;
         * 
         * p1 = interpretEq1(i); p2 = interpretEq3(i);
         * 
         * x1 = Point.fix(p1.x, width); x2 = Point.fix(p2.x, width); y1 =
         * Point.fix(p1.y, height); y2 = Point.fix(p2.y, height);
         * 
         * g.setColor(interpretEq2(i, p1));
         * 
         * g.drawLine(x1, y1, x2, y2); }//i
         */
        model.setWidth( width );
        model.setHeight( height );

        model.paintModel( g );

    }

    /**
     * DEBUG main.
     */
    public static void main( String args[] )
    {
        Frame f = new Frame();
        LifeView lv = new LifeView();

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
