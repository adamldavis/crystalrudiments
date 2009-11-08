

package net.crystalrudiments.gravitron;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JFrame;
/**
 * JApplet: Gravitron
 *
 *
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis </A>
 * @version Version 1.0, January 21, 2002
 */
public class GravitronApplet extends JApplet implements ActionListener
{
	/** bDEBUG is true if debugging. */
	private static final boolean bDEBUG = false;
	
	private JFrame f = null;
	private Button button = null;
	private GravPanel pp = null;
	private GravModel gm = null;
	/*-----------------------------------------------------------------------*/
	/**
	* Init Method. First thing called when applet is run.
	*/
	public void init()
	{
		super.init();
		/* Initiate the button.*/
		button = new Button();
		button.setLabel( " Start " );
		button.addActionListener(this);
		this.getContentPane().add(button);
		this.setSize(100,50);
		
	}//init
	public void start()
	{
		if (pp!=null) pp.pause(false);
	}
	public void stop()
	{
		if (pp!=null) pp.pause(true);
	}
	public void actionPerformed( ActionEvent evt)
	{
		/* initiate inside stuff...*/
		gm = new GravModel();
		pp = new GravPanel(gm);
		
		/* initiate JFrame.*/
		f = new JFrame("Gravitron - Adam L. Davis");
		f.getContentPane().add(pp);
		f.setSize(600,400);
		f.setVisible(true);
		/*start*/
		pp.pause(false);
	}

	/**
	* Paint method that is caled whenever the applet needs to be
	* repainted.
	*/
	public void paint(Graphics g)
	{
		if (f!=null) f.repaint();
	}
	
	/*-----------------------------------------------------------------------*/

}//END OF APPLET Gravitron
