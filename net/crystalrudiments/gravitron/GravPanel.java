
package net.crystalrudiments.gravitron;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
/**
* Panel for displaying the simulation of planets in space.
* Also contains the methods for user interaction, such as
* clicking and dragging the mouse.
*
* @author <A HREF="http://www.adamldavis.com">Adam L. Davis </A>
* @version Version 1.0, January 21, 2002
*/
public class GravPanel extends JPanel
{
	/*-----------------------------------------------------------------------*/
	// THE VIEW
	/*-----------------------------------------------------------------------*/
	
	/** bDEBUG = true if debugging */
	private static final boolean bDEBUG = false;
	
	private static final String TITLE = "Gravitron - Adam L. Davis";
	
	private static final int RATIO = 4;
	
	private Line2D line;
	private boolean lineDraw = false;
	
	private GravModel model = null;
	
	private GravListener control = null;
	
	private JSlider slider = null; 
	
	public GravPanel(GravModel gm)
	{
		newMouseAdapter();
		control = new GravListener(this);
		slider = new JSlider(1, 10, 1); 
		slider.addChangeListener(control);
		this.add(slider);
		model = gm;
	}
	private void newMouseAdapter()
	{
		this.addMouseListener (new MouseAdapter () 
		{
	        public void mousePressed (MouseEvent evt) { 
	            mousePress (evt);
	        }
	        public void mouseReleased (MouseEvent evt) { 
	            mouseRelease (evt);
	        }
        });
		this.addMouseMotionListener (new MouseMotionAdapter () 
		{
	        public void mouseDragged (MouseEvent evt) { //mouseClicked
	            mouseDrag (evt);
	        }
	        public void mouseMoved (MouseEvent evt) {}
        });
    }
	/*-----------------------------------------------------------------------*/
	//The paint methods.
	
	/**
	 * The paintComponent method. Overides paintComponent( Graphics g)
	 * @param g The Graphics object that is used to paint on this panel.
	 */
	public void paintComponent( Graphics g )
	{
		List planets;
		int i; int j;
		int w; int h;
		w = this.getWidth();
		h = this.getHeight();
		
		g.setColor(Color.black);
		g.fillRect(0,0,w,h);
		
		if (lineDraw)
		{
			g.setColor(Color.green);
			g.drawLine((int) line.getX1(),(int) line.getY1(),
				(int)line.getX2(), (int)line.getY2() );
		}
		planets = model.getPlanets();
		
		for (i=0; i<planets.size(); i++)
		{
			paintPlanet(g, (Planet)planets.get(i));
		}
	}
	
	private void paintPlanet(Graphics g, Planet p)
	{
		int ix,iy;
		int z = model.getZoom();
		ix = (int) p.x - p.rad;
		iy = (int) p.y - p.rad;
		
		g.setColor(p.colr);
		g.drawOval(ix/z + this.getWidth()/2, iy/z + this.getHeight()/2, p.rad*2/z, p.rad*2/z);
	}
	
	/*-----------------------------------------------------------------------*/
	// THE CONTROL
	/*-----------------------------------------------------------------------*/
	/**
	* Called when the controller gets a Timer event.
	*/
	public void timerCall()
	{
		int frac;
		if (bDEBUG) System.err.println("ratioOfSpeed ="+model.ratioOfSpeed);
		frac = (int)model.ratioOfSpeed + 1;
		for (int n=0; n<frac; n++) model.movePlanets(frac);
		this.repaint();
	}
	/** 
	 * True to pause, false to re-start.
	 * @param yes Whether to pause or start.
	 */
	public void pause(boolean yes)
	{
		control.pauseGame(yes);
	}
	/**
	* Called when the slider is moved.
	*/
	public void sliderChanged()
	{
		model.setZoom(slider.getValue());
	}
	/**
	* Called when the mouse is dragged.
	* @param evt The event.
	*/
	public void mouseDrag (MouseEvent evt)
	{
		if (bDEBUG) {
			System.out.println("in mouseDrag: "+ evt.paramString() );
		}
		double dx = (double)evt.getX();
		double dy = (double)evt.getY();
		line.setLine(line.getX1(), line.getY1(), dx, dy);
		this.repaint();
	}
	public void mousePress (MouseEvent evt)
	{
		if (bDEBUG) {
			System.out.println("in mousePress: "+ evt.paramString() );
		}
		double dx = (double)evt.getX();
		double dy = (double)evt.getY();
		line = new Line2D.Double(dx, dy, dx, dy);
		lineDraw = true;
	}
	public void mouseRelease (MouseEvent evt)
	{
		if (bDEBUG) {
			System.out.println("in mouseRelease: "+ evt.paramString() );
		}
		int z = model.getZoom();
		double dx = (double)evt.getX();
		double dy = (double)evt.getY();
		double vx = (dx-line.getX1())/RATIO;
		double vy = (dy-line.getY1())/RATIO;
		double w, h;
		w = (double)this.getWidth()/2;
		h = (double)this.getHeight()/2;
		
		model.addPlanet((line.getX1()-w) * z, (line.getY1()-h) * z,
			 vx * z, vy * z, evt.getModifiers()-2);
		lineDraw = false;
		this.repaint();
	}
	
	
	/*-----------------------------------------------------------------------*/
	//Main method.
	
	public static void main (String[] args) {
		JFrame f = new JFrame(TITLE);
		f.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		GravModel gm = new GravModel();
		GravPanel pp = new GravPanel(gm);
		f.getContentPane().add(pp);
		f.setSize(100,120);
		f.setVisible(true);
		pp.pause(false);
	}
	
} //GravPanel