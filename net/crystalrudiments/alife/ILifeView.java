/*
 * Created on Jun 19, 2005 by Adam. 
 * ILifeView
 */
package net.crystalrudiments.alife;

import java.awt.Graphics;

/**
 * This is the interface for a panel which represents a 'living' graphic image.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, Jun 19, 2005
 */
public interface ILifeView
{

    public ILifeModel getModel();

    public void setModel( ILifeModel m );

    public void paintComponent( Graphics g );
}