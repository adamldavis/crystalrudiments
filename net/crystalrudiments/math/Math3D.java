package net.crystalrudiments.math;
//Progress::
// Math3D(int res): 7:45--7:55
// Sin & Cos : 8:00--8:15pm
// FlightSim : 8:15--8:30pm
// FlightSimView: 8:35--
public class Math3D
{
	/** bDEBUG is true if debugging. */
	private static final boolean bDEBUG = false;
	
	public static final double DEG_TO_RAD = (Math.PI/180d);
	public static final double HALFPI = (Math.PI/2d);
	public static final double QUARTPI = (Math.PI/4d);
	public static final double BIPI = (Math.PI*2d);
	private double[] sine;
	private double[] cosine;
	private int res;
	
	public Math3D(int res)
	{
		this.res = res;
		sine = new double[res];
		cosine = new double[res];
		for (int i=0; i<res; i++) {
			sine[i] = Math.sin(((double)i/(double)res)*HALFPI);
			cosine[i] = Math.cos(((double)i/(double)res)*HALFPI);
			if (bDEBUG) System.out.println("sine["+i+"]=" + sine[i]);
			if (bDEBUG) System.out.println("cosine["+i+"]=" + cosine[i]);
		}
	}
	/**
	* Converts a three dimensional value of x or y into the
	* two-demensional translation of it for the screen.
	* @param xx X (or y) position.
	* @param zz Z position.
	* @param z0 Distance from viewer to screen (in pixels).
	*/
	public double value2d(double xx, double zz, int z0)
	{
		return (z0 * xx)/(zz+(double)z0);
	}
	/**
	* @param xx X (or y) position.
	* @param xm Maximum x(or y).
	* @param zz Z position.
	* @param z0 Distance from viewer to screen (in pixels).
	*/
	public double oldvalue3d(double xx,double xm, double zz, int z0){
		double zratio;
		zratio = zz / ((double)z0 + zz);
		return xx * (1 - zratio) + (xm * zratio);
	}
	/*
	*Purpose: spins 3d points around central axis.
	*/
	public Point3D spin3D(Point3D org,
	double tx, double ty, double tz) {
		Point3D sp;
		double xx,yy,zz;
		 xx = (org.getX() * Cos(ty) * Cos(tz))
		 + (org.getY() * (Sin(tx) * Sin(ty) * Cos(tz) - Cos(tx) * Sin(tz)))
		 + (org.getZ() * (Cos(tx) * Sin(ty) * Cos(tz) + Sin(tx) * Sin(tz)));
		
		 yy = (org.getX() * Cos(ty) * Sin(tz))
		 + (org.getY() * (Sin(tx) * Sin(ty) * Sin(tz) + Cos(tx) * Cos(tz)))
		 + (org.getZ() * (Cos(tx) * Sin(ty) * Sin(tz) - Sin(tx) * Cos(tz)));
		
		 zz = -(org.getX() * Sin(ty))
		 + (org.getY() * Sin(tx) * Cos(ty))
		 + (org.getZ() * (Cos(tx) * Cos(ty)));
		 
		sp = new Point3D(xx,yy,zz);
		return sp;
	}
	public double Sin(double d) {
		d = (d/HALFPI);
		if (d<0d) {
			return Sin((d+4d)*HALFPI);
		}
		else if (d<1d) {
			d *= (double)res;
			return sine[(int)d];
		}else if (d<2d) {
			d = (d-1d)*(double)res;
			return cosine[(int)d];
		}else if (d<3d) {
			d = (d-2d)*(double)res;
			return -sine[(int)d];
		}else if (d<4d) {
			d = (d-3d)*(double)res;
			return -cosine[(int)d];
		} else
		return Sin((d-4d)*HALFPI);
	}
	public double Cos(double d) {
		d = (d/HALFPI);
		if (d<0d) {
			return Cos((d+4d)*HALFPI);
		}
		else if (d<1d) {
			d *= (double)res;
			return cosine[(int)d];
		}else if (d<2d) {
			d = (d-1d)*(double)res;
			return -sine[(int)d];
		}else if (d<3d) {
			d = (d-2d)*(double)res;
			return -cosine[(int)d];
		}else if (d<4d) {
			d = (d-3d)*(double)res;
			return sine[(int)d];
		}
		return Cos((d-4d)*HALFPI);
	}
	/**
	* Returns d mod 2*PI.
	*/
	public double inCircle(double d) {
		if (d<0d) return inCircle(d+BIPI);
		else if (d>=BIPI) return (d % BIPI);
		else return d;
	}
	public static void main(String[] args)
	{
		Math3D m = new Math3D(100);
		double d = -HALFPI;
		
		System.out.println("inCircle("+d+")="+m.inCircle(d));
		System.out.println("Sin("+d+")="+m.Sin(d));
		System.out.println("Cos("+d+")="+m.Cos(d));
		int i=0;
		for (d=0; d<BIPI; d+=QUARTPI) 
		{
			System.out.println("Sin("+i+"/4pi)="+m.Sin(d));
			System.out.println("Cos("+i+"/4pi)="+m.Cos(d));
			i++;
		}
	}
}//class Math3D