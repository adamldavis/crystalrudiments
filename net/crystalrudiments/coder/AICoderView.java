/**
 * JFrame: AICoderView
 *
 *
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.0
 */

package net.crystalrudiments.coder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class AICoderView extends JFrame
{
	/** bDEBUG is true if debugging. */
	private static final boolean bDEBUG = true;
	
	//put components here...
	JTextArea textArea;
	
	JButton button;
	
	JLabel label;
	
	private static final String BUTTON_NAME = "Compile";
	
	/**
	* Default Constructor
	*/
	public AICoderView()
	{
		this.setSize(400, 400);
		this.setTitle("AICoderView");
		
		addComponents();
		addEventHandling();
		
		this.show();
	}//AICoderView
	
	/**
	* Adds components to JFrame.
	*/
	private void addComponents()
	{
		//add components here
		textArea = new JTextArea();
		button = new JButton(BUTTON_NAME);
		label = new JLabel();
		
		Container content = this.getContentPane();
		content.setLayout( new BorderLayout() );
		
		content.add(textArea);
		content.add(label, BorderLayout.NORTH);
		content.add(button, BorderLayout.SOUTH);
	}
	
	/**
	* Adds event listeners.
	*/
	private void addEventHandling()
	{
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Coder c = new Coder();
				label.setText( c.calculate(textArea.getText()) );
			}
		});
	}
	
	/**
	* Main method. Run by java compiler.
	*/
	public static void main(String args[])
	{
		AICoderView mainFrame = new AICoderView();
	}//main
	
}//AICoderView