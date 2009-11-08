
package net.crystalrudiments.alife;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Control of the MVC paradigm for the ALife project. This is the event handler
 * for this program.
 * 
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam Davis </A>
 * @version Version 20, May 10, 2005
 */
public class ALifeControl implements ActionListener, LifeConstants {

	/*-----------------------------------------------------------------------*/
	// THE CONTROLLER
	/*-----------------------------------------------------------------------*/

	/** bDEBUG = true if debugging. */
	private static final boolean bDEBUG = false;

	/** The reference to the gui that will be the view for this program. */
	private ALife view = null;

	/**
	 * Creates a new event handler that is bound by the gui view that's passed
	 * as a parameter.
	 * 
	 * @param view
	 *            The view that this control connects to.
	 */
	public ALifeControl(ALife view) {
		if (bDEBUG)
			System.out.println("In ALifeControl");
		// If the gui is null, then this class is being used improperly and
		// should
		// exit.
		if (view == null) {
			System.err.println("Error: view == null");
			System.exit(0);
		}

		// Add the view to the control.
		this.view = view;//<------------------------------

	}

	/**
	 * Refreshes the view.
	 *  
	 */
	public void refreshView() {
		view.repaint();

	}

	/**
	 * This function recieves an action event and interprets the event to take
	 * the proper action. It receives all the button actions from the view.
	 * 
	 * @param event
	 *            The action event that needs to be processed.
	 */
	public void actionPerformed(ActionEvent event) {
		if (event == null) {// No action to process.
			System.err.println("actionPerformed: event==null");
			return;
		}
		String str = event.getActionCommand();

		if (bDEBUG) {
			System.out.println("In actionPerformed( ActionEvent event )"
					+ "... the event is: " + str);
		}

		if (str == null) //null
		{
		} else if (str.equals(MENU_INST)) //"Instructions"
		{
			view.showInstructions();
		} else if (str.equals(MENU_NEW)) //"New"
		{
			view.reinitPics();
			refreshView();
		} else if (str.equals(MENU_SAVE)) //"Save"
		{
			view.saveState();
		} else if (str.equals(MENU_LOAD)) //"Load"
		{
			view.loadState();
		} else if (str.equals(MENU_EXIT)) //"Exit"
		{
			view.setVisible(false);
			System.exit(0);
		}//if

	}

}
