/*
 * Created on May 12, 2005 by Adam. 
 * Interface to land map builders.
 *
 */
package net.crystalrudiments.rpg.land;

/**
 * ILandMaker
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 */
public interface ILandMaker {
	
	/** Makes the land-map and creates the Height and Ground arrays.
	 * Calls {@link #makeLand(int)}. 
	 */
	public void makeLand();

	/**
	 * Makes a square map with random looking splatches of ground.
	 * 
	 * @param depth
	 *            Sets the percentOfDepth variable used for creating the map.
	 */
	public void makeLand(int depth);

	public int getHeight(int x, int y);

	public boolean getGround(int x, int y);

	/**
	 * The size of the 2D arrays used for the land-map.
	 * 
	 * @return Returns the arraySize.
	 */
	public int getArraySize();

	/**
	 * Returns an array representing which tiles are below groundHeightMax.
	 * 
	 * @return Returns the ground.
	 */
	public boolean[][] getGround();

	/**
	 * Returns the height map 2D array.
	 * 
	 * @return Returns the height map 2D array.
	 */
	public int[][] getHeight();
}