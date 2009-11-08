
package net.crystalrudiments.gravitron;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
* Implements many of the Listeners for the Gravitron application.
*
* @author <A HREF="http://www.adamldavis.com">Adam L. Davis </A>
* @version Version 1.0, January 21, 2002
*/
public class GravListener implements ActionListener, ComponentListener, ChangeListener
{
	/*-----------------------------------------------------------------------*/
	// THE CONTROLLER
	/*-----------------------------------------------------------------------*/
	/** bDEBUG = true if debugging. */
	private static final boolean bDEBUG = false;
	
	private static final int DELAY = 160;
	
	private Timer timer = null;
	
	private GravPanel gravPanel = null;
	
	public GravListener(GravPanel gp)
	{
		timer = new Timer(DELAY, this);
		gravPanel = gp;
		gp.addComponentListener(this);
	}
	/**
	 * This method does whatever is required when the timer goes off.
	 * In this program this method moves the current piece down a space.
	 */
	public void timerEvent() 
	{
		gravPanel.timerCall();
	}
	
	/*
	 * Called when the panel is resized.
	 */
	public void componentResized(ComponentEvent e)
	{
		gravPanel.repaint();
	}
	public void componentMoved(ComponentEvent e){;}
	public void componentShown(ComponentEvent e){;}
	public void componentHidden(ComponentEvent e){;}
	
	public void stateChanged(ChangeEvent e)
	{
		gravPanel.sliderChanged();
	}
	public void actionPerformed( ActionEvent event)
	{
		if ( event == null ) {// No action to process.
			System.err.println("actionPerformed: event==null"); 
			System.exit( 0 );
		}
		if (bDEBUG) {
			System.out.println("In actionPerformed( ActionEvent event )" +
			"... the event is: " + event.getActionCommand());
		}
		String str = event.getActionCommand();
		
		if ( str == null ) //Timer called us.
		{ 
			timerEvent();
		}
		/*else if ( str.equals(LABEL_PAUSE) ) //"Pause"
		{
			pauseGame(true);
		}
		else if ( str.equals(LABEL_RESUME) ) //"Resume"
		{
			pauseGame(false);
		}
		else if (str.equals(MENU_INST)) //"Instructions"
		{
			pauseGame(true);
			sim.showInstructions();
		}
		else if (str.equals(MENU_EXIT)) //"Exit"
		{
			sim.setVisible(false);
			System.exit(0);
		}
		else {
		}//if*/
	}
	/**
	* Pauses or unpauses the game.
	*/
	public void pauseGame(boolean pause)
	{
		if (timer!=null) {
			if (pause) timer.stop();
			else timer.start();
		} else {
			System.err.println("Timer==null");
			System.exit(0);
		}
	}
}//GravListener