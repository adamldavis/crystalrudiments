/*
 * Created on Jun 17, 2005 by Adam. 
 * IEditorControl
 */
package net.crystalrudiments.rpg.editor;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * This is the event handler for this program. 
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, Jun 17, 2005
 */
public interface IEditorControl extends ActionListener, MouseListener, KeyListener
{

    /**
     * Refreshes the view.
     *  
     */
    public void refreshView();

}