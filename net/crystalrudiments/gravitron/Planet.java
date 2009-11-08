
package net.crystalrudiments.gravitron;

import java.awt.Color;
import java.io.Serializable;
/**
 * Contains data representing a Planet in space.
 * 
 * @author <A HREF="mailto:gte459u@prism.gatech.edu">Adam Davis </A>
 * @version Version 1.0, January 21, 2002
 */
public class Planet implements Serializable
{
    private static final long serialVersionUID = 12312L;
    
    public static final float MAX_RADIUS = 100;

    public static final double RO = 0.05d;

    public static final double VOLUM = Math.pow( ( 3d / ( 4d * RO * Math.PI ) ), (1d/3d) );

    /** Location of the planet.*/
    double x, y;

    /** Velocity of the planet.*/
    double vx, vy;

    /** Radius of the planet.*/
    int rad;

    /** Mass of the planet.*/
    public double m;

    /** Color of the planet.*/
    public Color colr;

    public Planet( double xx, double yy, double vxx, double vyy, int rr )
    {
        rad = rr;
        m = Math.pow( ( ( double ) rr ) / VOLUM, 3d );
        colr = Color.getHSBColor( ( ( float ) rr / MAX_RADIUS ) % 1f, 1f, 1f );
        x = xx;
        y = yy;
        vx = vxx;
        vy = vyy;
    }

    public void setM( double m )
    {
        this.m = m;
        rad = ( int ) ( VOLUM * Math.pow( m, ( 1d / 3d ) ) );
        colr = Color.getHSBColor( ( ( float ) rad / MAX_RADIUS ) % 1f, 1f, 1f );
    }

    public void move()
    {
        x += vx;
        y += vy;
    }

    public void move( int fraction )
    {
        x += vx / ( ( double ) fraction );
        y += vy / ( ( double ) fraction );
    }

    public void accelx( double ax )
    {
        vx += ax;
    }

    public void accely( double ay )
    {
        vy += ay;
    }

    public Object clone()
    {
        Planet p = new Planet( this.x, this.y, this.vx, this.vy, this.rad );
        return p;
    }
}//Planet
