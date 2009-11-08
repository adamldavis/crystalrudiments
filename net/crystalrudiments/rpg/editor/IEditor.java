/*
 * Created on Jun 17, 2005 by Adam. 
 * IEditor
 */
package net.crystalrudiments.rpg.editor;

/**
 * Interface to any Game-Editor implementations.
 *   
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, Jun 17, 2005
 */
public interface IEditor
{

    /**
     * Shows the Instructions.
     */
    public void showInstructions();

    /**
     * Loads a map (location) based on user input.
     */
    public void loadState();

    /**
     * Saves any modified data.
     */
    public void saveState();

    public void rightClick( int x, int y, boolean shift, boolean control );

    public void leftClick( int x, int y, boolean shift, boolean control );

    public void middleClick( int x, int y, boolean shift, boolean control );

    public void chooseTile();

    public void choosePerson();

    public void chooseObject();

    public void keyTyped( char c, String modifiers );

    public void move( char c );

    public void refreshStatus();
}