/*
 * Created on May 14, 2005 by Adam. 
 * ILifeModel
 */
package net.crystalrudiments.alife;

import java.awt.Graphics;
import java.io.Serializable;

/**
 * Interface to models of artificial life for the ArtLife project.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, May 14, 2005
 */
public interface ILifeModel extends Serializable
{

    /**
     * Gets the coding sequence for this model.
     * 
     * @return The model's coding sequence.
     */
    public String getSequence();
    
    /**
     * Returns the double array for storing variables used by this model's
     * algorithm.
     * 
     * @return The array used to store variables.
     */
    public double[] getArray();
    
    /**
     * Sets the width of this component.
     * 
     * @param w
     *            width
     */
    public void setWidth( int w );

    /**
     * Sets the height of this component.
     * 
     * @param h
     *            Height
     */
    public void setHeight( int h );

    /**
     * Returns the width of the model's matrix.
     * 
     * @return Width of matrix.
     */
    public int getWidth();

    /**
     * Returns the height of the model's matrix.
     * 
     * @return Height of matrix.
     */
    public int getHeight();

    /**
     * Paints the model.
     * 
     * @param g
     *            Graphics of the GUI.
     */
    public void paintModel( Graphics g );

    /**
     * Call before calling repaint on this object.
     */
    public void prepareForPaint();

    /**
     * Returns a slightly mutated copy of this ALifeModel.
     */
    public ILifeModel reproduce();

    /**
     * Mutates this Object.
     */
    public void mutate();

    /**
     * Overrides Object.equals() Tells whether this equals another ALifeModel.
     */
    public boolean equals( Object obj );
}