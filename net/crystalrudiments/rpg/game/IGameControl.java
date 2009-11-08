/*
 * Created on Jun 17, 2005 by Adam. 
 * IGameControl
 */
package net.crystalrudiments.rpg.game;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * TODO Fill in this comment.
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, Jun 17, 2005
 */
public interface IGameControl extends ActionListener, MouseListener, KeyListener
{

    /**
     * Refreshes the view.
     *  
     */
    public void refreshView();

}