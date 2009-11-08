/* Revisions:
 *
 * 6/27/03 - rev 3. Used for JMiedor.
 */
package net.crystalrudiments.rpg;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.util.Hashtable;

import javax.swing.JPanel;

import net.crystalrudiments.rpg.person.Person;

/**
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, June 27, 2003
 */
public class MapPanel extends JPanel implements RPGConstants
{

    private static final boolean bDEBUG = true;

    Hashtable htImages = null; //contains Image objects, which are loaded on
                               // demand.

    IBoard board = null; //model with tiles and people.

    static final String[] images = Messages.getArray("MapPanel.", 0, 6, "rpg");
    
    static final String IMG_PEOPLE = Messages.getString("MapPanel.7", "rpg"); //$NON-NLS-1$

    static final String IMG_OBJECTS = Messages.getString("MapPanel.8", "rpg"); //$NON-NLS-1$

    static final String IMG_PLAYER = Messages.getString("MapPanel.9", "rpg"); //$NON-NLS-1$

    private int posx, posy;

    private int xshift, yshift;

    private boolean omnivision = false; //see all? for editor.

    private boolean playerVisible = false;

    /**
     */
    public MapPanel()
    {
        super();
        htImages = new Hashtable();
        loadImage( IMG_PEOPLE );
        loadImage( IMG_OBJECTS );
        setPos( 49, 49 );
        //this.setSize(600, 800);
        //this.repaint();
    }

    public MapPanel( IBoard board )
    {
        this(); //init

        this.board = board;
        this.repaint();
    }

    public void showPlayer()
    {
        playerVisible = true;
        loadImage( IMG_PLAYER );
    }

    public void setOmnivision( boolean yes )
    {
        omnivision = yes;
    }

    /** Modifier for Board (model). */
    public void setBoard( IBoard board )
    {

        this.board = board;
        System.gc(); //run garbage collector to free memory.
        this.repaint();
    }

    public int getPosX()
    {
        return posx;
    }

    public int getPosY()
    {
        return posy;
    }

    public void setPos( int x, int y )
    {
        posx = x;
        posy = y;
        refreshShift();
        mapJump();
        this.repaint();
    }

    public void move( char c )
    {
        switch ( c ) {
        case 'u':
            setPos( getPosX(), getPosY() - 1 );
            break;
        case 'd':
            setPos( getPosX(), getPosY() + 1 );
            break;
        case 'r':
            setPos( getPosX() + 1, getPosY() );
            break;
        case 'l':
            setPos( getPosX() - 1, getPosY() );
            break;
        case '1':
            setPos( getPosX() - 1, getPosY() + 1 );
            break;
        case '3':
            setPos( getPosX() + 1, getPosY() + 1 );
            break;
        case '7':
            setPos( getPosX() - 1, getPosY() - 1 );
            break;
        case '9':
            setPos( getPosX() + 1, getPosY() - 1 );
            break;
        }
        //this.repaint();
    }

    private void refreshShift()
    {
        xshift = ( this.getWidth() / TILE_W ) / 2 + 1;
        yshift = ( this.getHeight() / TILE_H ) / 2 + 1;
        //if (bDEBUG) System.out.println("shift = " + xshift + ", " + yshift);
    }

    /**
     * Handles map saving and loading if necessary based on position. <BR>
     * 1, 2 <BR>
     * 3, 4 <BR>
     */
    private void mapJump()
    {
        int oldQuad = 0;
        int newQuad = 0;
        if ( posx > 95 )
        {
            if ( posy > 95 )
            {
                oldQuad = 4;
                newQuad = 1;
            } else if ( posy < 5 )
            {
                oldQuad = 2;
                newQuad = 3;
            } else if ( posy < 50 )
            {
                oldQuad = 2;
                newQuad = 1;
            } else
            {
                oldQuad = 4;
                newQuad = 1;
            }
        } else if ( posx < 5 )
        {
            if ( posy > 95 )
            {
                oldQuad = 3;
                newQuad = 2;
            } else if ( posy < 5 )
            {
                oldQuad = 1;
                newQuad = 4;
            } else if ( posy < 50 )
            {
                oldQuad = 1;
                newQuad = 2;
            } else
            {
                oldQuad = 3;
                newQuad = 4;
            }
        } else if ( posx < 50 )
        {
            if ( posy > 95 )
            {
                oldQuad = 3;
                newQuad = 1;
            } else if ( posy < 5 )
            {
                oldQuad = 1;
                newQuad = 3;
            }
        } else
        {
            if ( posy > 95 )
            {
                oldQuad = 4;
                newQuad = 1;
            } else if ( posy < 5 )
            {
                oldQuad = 2;
                newQuad = 4;
            }
        }
        if ( newQuad > 0 )
        {
            adjustPositionTo( oldQuad, newQuad );
            try
            {
                board.moveMap( oldQuad, newQuad );
            } catch ( MoveException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * adjust posx and posy to new quadrant. <BR>
     * 1, 2 <BR>
     * 3, 4 <BR>
     */
    void adjustPositionTo( int quadNum, int newQuad )
    {
        switch ( quadNum ) {
        //break;
        case 1:
            if ( newQuad == 2 || newQuad == 4 ) posx = posx + 50;
            if ( newQuad == 3 || newQuad == 4 ) posy = posy + 50;
            break;
        case 2:
            if ( newQuad == 1 || newQuad == 3 ) posx = posx - 50;
            if ( newQuad == 4 || newQuad == 3 ) posy = posy + 50;
            break;
        case 3:
            if ( newQuad == 4 || newQuad == 2 ) posx = posx + 50;
            if ( newQuad == 1 || newQuad == 2 ) posy = posy - 50;
            break;
        case 4:
            if ( newQuad == 3 || newQuad == 1 ) posx = posx - 50;
            if ( newQuad == 2 || newQuad == 1 ) posy = posy - 50;
        } //switch
    } //method

    /**
     */
    public void paint( Graphics g )
    {

        refreshShift();
        //int width = img.getWidth(this);
        //int height = img.getHeight(this);
        //	BufferedImage bi = new BufferedImage(width, height,
        //			     BufferedImage.TYPE_INT_RGB);
        if ( board != null )
        {
            for ( int i = posx - xshift; i < posx + xshift; i++ )
            {
                for ( int j = posy - yshift; j < posy + yshift; j++ )
                {
                    paintTile( g, i - ( posx - xshift ), j - ( posy - yshift ), i, j );
                }
            }
        }
        if ( playerVisible )
        {
            int sx = 0;
            int sy = PERSON_H * 2;
            Image img = ( Image ) htImages.get( IMG_PLAYER );
            g.drawImage( img, xshift * TILE_W - 22, yshift * TILE_H - 22, xshift * TILE_W - 22 + PERSON_W, yshift
                    * TILE_H - 22 + PERSON_H, sx, sy, sx + PERSON_W, sy + PERSON_H, null );
        }
    }

    /**
     * Paints the given tile unto g.
     */
    public void paintTile( Graphics g, int dx, int dy, int x, int y )
    {
        /*
         * abstract boolean drawImage(Image img, int dx1, int dy1, int dx2, int
         * dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer)
         */
        //Graphics2D biContext = (Graphics2D) g; // bi.createGraphics();
        int tile = 0;
        boolean see = false;

        if ( x < 0 || y < 0 || x >= 100 || y >= 100 )
            tile = 0;
        else
        {
            tile = board.getTile( x, y );
            if ( omnivision )
                see = true;
            else
                see = board.getSee( x, y );
        }
        if ( !see ) tile = 0;
        if ( tile < 0 )
        { //System.err.println("Error: tile < 0: (x,y)=" + x + "," + y); tile =
          // 0; }
            tile = Math.abs( tile );
        }
        Image img = null;
        int picnum = ( tile / 100 );
        tile = ( tile % 100 );
        if ( !htImages.containsKey( images[picnum] ) )
        {
            loadImage( images[picnum] );
        }
        img = ( Image ) htImages.get( images[picnum] );

        int sx = 1 + ( TILE_W + 1 ) * ( tile % 10 );
        int sy = 1 + ( TILE_H + 1 ) * ( tile / 10 );
        //if (dx < 0 || dy < 0) return;

        g.drawImage( img, dx * TILE_W - 22, dy * TILE_H - 22, ( dx + 1 ) * TILE_W - 22, ( dy + 1 ) * TILE_H - 22, sx,
                sy, sx + TILE_W, sy + TILE_H, null );
        if ( see )
        {
            //draw objects and people.
            Person p = board.getPerson( x, y );
            int person = ( ( p == null ) ? -1 : p.getImageNum() );
            int object = board.getObject( x, y );
            if ( person > -1 )
            {
                sx = 1 + ( PERSON_W + 2 ) * ( person % 8 );
                sy = 1 + ( PERSON_H + 2 ) * ( person / 8 );
                img = ( Image ) htImages.get( IMG_PEOPLE );
                g.drawImage( img, dx * TILE_W - 22, dy * TILE_H - 22, dx * TILE_W - 22 + PERSON_W, dy * TILE_H - 22
                        + PERSON_H, sx, sy, sx + PERSON_W, sy + PERSON_H, null );
            }
            if ( object > -1 )
            {
                sx = 1 + ( OBJECT_W + 2 ) * ( object % 10 );
                sy = 1 + ( OBJECT_H + 2 ) * ( object / 10 );
                img = ( Image ) htImages.get( IMG_OBJECTS );
                g.drawImage( img, dx * TILE_W - 22, dy * TILE_H - 22, dx * TILE_W - 22 + OBJECT_W, dy * TILE_H - 22
                        + OBJECT_H, sx, sy, sx + OBJECT_W, sy + OBJECT_H, null );
            }
        }
    }

    /**
     * converts from pixel to map coordinates.
     */
    public int convertX( int x )
    {
        return ( ( x + 22 ) / TILE_W ) + ( posx - xshift );
    }

    /**
     * converts from pixel to map coordinates.
     */
    public int convertY( int y )
    {
        return ( ( y + 22 ) / TILE_H ) + ( posy - yshift );
    }

    /**
     * Loads the given image and waits for it to load and adds it to htImages
     * using the filename as the key.
     */
    private void loadImage( String fileName )
    {
        Image img = Toolkit.getDefaultToolkit()
                .getImage( IMAGE_DIR + System.getProperty( "file.separator" ) + fileName ); //$NON-NLS-1$
        try
        {
            MediaTracker tracker = new MediaTracker( this );
            tracker.addImage( img, 0 );
            tracker.waitForID( 0 );
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
        htImages.put( fileName, img );
    }
}
