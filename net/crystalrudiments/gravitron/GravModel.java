
package net.crystalrudiments.gravitron;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/**
 * Contains the model for planets in space.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis </A>
 * @version Version 1.0, January 21, 2002
 */
public class GravModel implements Serializable
{
    private static final long serialVersionUID = 1231222L;
    
	/** bDEBUG is true if debugging. */
	private static final boolean bDEBUG = false;
	
	/** Gravity constant.*/
	protected static final double Grav = 89.9786d;
	
	protected static final double FAR_AWAY = 1200d;
	
	protected static final double FAR_X = 600d;
	
	protected static final double FAR_Y = 380d;
	
	/** Zoom property.*/
	private int zoom;
	/** Which planet is selected as "center of universe".*/
	private int selected;
	/** The array which holds values for planets.*/
	private Vector planets;
	/** The array which holds previous values for planets.*/
	private Vector oldPlanets;
	/** The average ratio of speed to mass.*/
	public double ratioOfSpeed;
	/**
	 * The default constructor.
	 */
	public GravModel() 
	{
		planets = new Vector(10);
		selected=0;
		zoom=1;
	}
	/** Change Zoom property.*/
	public void setZoom(int z)
	{
		zoom =z;
	}
	/** Returns the Zoom property, how far out the viewer is.*/
	public int getZoom() { return zoom;}
	
	private void setUpOldPlanets()
	{
		if (bDEBUG) System.out.println("In setUpOldPlanets()");
		oldPlanets = new Vector(10);
		int i;
		for (i=0; i<planets.size(); i++)
		{
			oldPlanets.add( ((Planet) planets.get(i)).clone() );
		}
	}
	/**
	 * Moves the planets according to the rules of gravity, acceleration
	 * and velocity. 
	 * BUG: When three or more planets collide the result may be that of
	 * all joining together or only joining in pairs or anything in between.
	 * @param fraction What fraction of time should pass.
	 */
	public void movePlanets(int fraction)
	{
		int i,j;
		double dx, dy, dr;
		Planet p1, p2;
		/* killList says if a planet is going to disappear (i.e.get "killed").*/
		boolean[] killList = new boolean[planets.size()];
		for (i=0;i<killList.length;i++) killList[i]=false;
		setUpOldPlanets();
		if (planets.size()==0) return;
		
		for (i=0; i<planets.size(); i++)
		{
			if (killList[i]) continue;
			p1 = (Planet)planets.get(i);
			//if (p1.x > FAR_X || p1.x < -FAR_X) p1.vx = -p1.vx;
			//if (p1.y > FAR_Y || p1.y < -FAR_Y) p1.vy = -p1.vy;
			if ((p1.x > FAR_X*zoom || p1.x < -FAR_X*zoom) ||
		    	(p1.y > FAR_Y*zoom || p1.y < -FAR_Y*zoom))
		    {
		    	killList[i] = true;
		    	continue;
		    }
		    for (j=0; j<oldPlanets.size(); j++)
			{
        		if (j==i) continue;
        		p2 = (Planet)oldPlanets.get(j);
        		
                dx = p2.x - p1.x;
        		dy = p2.y - p1.y;
        		dr = Math.sqrt(dx * dx + dy * dy);
        		
        		if ( !killList[j] && dr < (p1.rad + p2.rad) )
        		{
        			double mm;
        			//bouncePlanets(i, j, dx, dy);
        			killList[j] = true;
        			mm = p1.m+p2.m;
        			p1.vx = (p1.m * p1.vx + p2.m * p2.vx) / mm; //crude physics
					p1.vy = (p1.m * p1.vy + p2.m * p2.vy) / mm;
					p1.setM(mm);
					if (selected == j) selected = i;
					continue;
        		}
		        if (dx > 100d)
		            p1.accelx( ( Grav * dx / Math.pow(dr, 3d) ) * p2.m / (double)fraction );
		        else
		            p1.accelx( Grav * dx * p2.m / (fraction * Math.pow(dr, 3d)) );

		        if (dy > 100d)
		            p1.accely( ( Grav * dy / Math.pow(dr, 3d) ) * p2.m / (double)fraction );
		        else
		            p1.accely( Grav * dy * p2.m / (fraction * Math.pow(dr, 3d)) );
			}
		}
		/*Kill all destined to be killed.*/
		for (i=killList.length-1; i>=0; i--)
		{
			if (killList[i]) killPlanet(i);
		}
		/*Find the planet w/ the largest mass and select it.
		* while moving every planet. */
		Planet bigP = (Planet) planets.get(0);
		
		for (i=0; i<planets.size(); i++)
		{
			p1 = (Planet)planets.get(i);
			p1.move(fraction);
			if (p1.m > bigP.m) {
				selected = i;
				bigP = p1;
			}
		}
		/* Calculate ratio of speed to mass.*/
		ratioOfSpeed = 0d;
		for (i=0; i<planets.size(); i++)
		{
			double tmp;
			p1 = (Planet)planets.get(i);
			tmp = Math.sqrt(p1.vy*p1.vy + p1.vx*p1.vx) / p1.m;
			if (tmp>ratioOfSpeed) ratioOfSpeed = tmp;
		}
		//ratioOfSpeed /= (double) planets.size();
		/*Move all planets according to selected (largest) planet.*/
		for (i=0; i<planets.size(); i++)
		{
			p1 = (Planet)planets.get(i);
			p1.x -= bigP.x;
			p1.y -= bigP.y;
			p1.vx -= bigP.vx;
			p1.vy -= bigP.vy;
		}
	} //movePlanets
	/* 
	 * Replaces the indicated planet with the last planet in the 
	 * Vector "planets" and then removes the last element of that
	 * list.
	 */
	private void killPlanet(int index)
	{
		Object lastObj;
		lastObj = planets.lastElement();
		planets.setElementAt(lastObj, index);
		planets.removeElementAt(planets.size()-1);
	}
		
	/* Accessors & Modifiers *********************************/
	/** Returns the List of planets.*/
	public List getPlanets()
	{
		return planets;
	}
	/** Adds a new planet to the space-model.
	 * 
	 * @param x Location X-coordinate.
	 * @param y Location Y-coordinate.
	 * @param vx Speed in X-direction.
	 * @param vy Speed in Y-direction.
	 * @param r Radius of the planet.
	 * @see Planet
	 */
	public void addPlanet(double x, double y, double vx, double vy, int r)
	{
		planets.add( new Planet(x, y, vx, vy, r) );
	}
}//GravModel