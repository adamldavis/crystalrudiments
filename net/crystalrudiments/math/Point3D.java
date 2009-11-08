package net.crystalrudiments.math;
import java.awt.geom.Point2D;

public class Point3D extends Point2D.Double
{
	double z;
	public double getZ() {return z;}
	/**
	* Constructs and initializes a point at 
	* the origin (0,0,0) of the coordinate space.
	*/
	public Point3D() { super(); z=0d; }
	/**
	* Constructs and initializes a point at the
	* specified (x, y) location in the coordinate space.
	*/
	public Point3D(int x,int y,int z) {
		this.x = (double)x;
		this.y = (double)y;
		this.z = (double)z;
	}
	public Point3D(double x,double y,double z) {
		this.x = x;this.y = y;this.z = z;
	}
	/**
	* Constructs and initializes a point with the same 
	* location as the specified Point3D object.
	*/
	public Point3D(Point3D pt) {
		this(pt.getX(),pt.getY(),pt.getZ());
	}
	public Object clone() {
		Point3D p = new Point3D();
		p.setLocation(x,y,z);
		return p;
	}
	public void setLocation(double x,double y,double z)
	{
		super.x=x; super.y=y; this.z=z;
	}
	public void translate(double x,double y,double z)
	{
		super.x+=x; super.y+=y; this.z+=z;
	}
	public void move(double x,double y,double z)
	{
		super.x=x; super.y=y; this.z=z;
	}
	public String toString() {
		return ("("+x+","+y+","+z+")");
	}
}//class Point3D