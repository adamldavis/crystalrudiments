package net.crystalrudiments.math;

/**
 * Polygon3D.java
 * 
 * Created June 3, 2001
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 */
public class Polygon3D
{

    int[] vert; //vertices.

    int color;

    /** Normal vector of this polygon. */
    Point3D normal;

    /** Midpoint of this polygon. */
    Point3D mid;

    public Polygon3D( int[] v, int c, Point3D n, Point3D m )
    {
        vert = v;
        color = c;
        normal = n;
        mid = m;
    }

    public int[] getVerts()
    {
        return vert;
    }

    public int getColor()
    {
        return color;
    }

    public Point3D getNormal()
    {
        return normal;
    }

    public Point3D getMid()
    {
        return mid;
    }

    public void setNormal( Point3D n )
    {
        normal = n;
    }

    public void setMid( Point3D m )
    {
        mid = m;
    }

    public Object clone()
    {
        Point3D n;
        Point3D m;
        int[] v = new int[vert.length];
        for ( int i = 0; i < v.length; i++ )
            v[i] = vert[i];
        n = ( Point3D ) normal.clone();
        m = ( Point3D ) mid.clone();

        return ( new Polygon3D( v, color, n, m ) );
    }
}//class Polygon3D
