/*
 * HOlds the constants for JMEditor.
 */
package net.crystalrudiments.rpg.editor;

import net.crystalrudiments.rpg.Messages;
import net.crystalrudiments.rpg.RPGConstants;

/** 
 * Holds the constants for Editor.
 * @author <A HREF="http://www.adamldavis.com/">Adam L. Davis</A>
 */
public interface EditorConstants extends RPGConstants {

    public static final int iDELETE = 1024;
    public static final int  CASTLE = 0;
    public static final int  FOREST = 1;
    public static final int  BEACH = 2;
    public static final int  SWAMP = 3;
    public static final int  CAVES = 4;
    public static final int  DESERT = 5;
    public static final int  ISLAND = 6;
    //types of tiles
    public static final int  GRASS = 7;
    public static final int  ROCK = 8;
    public static final int  WATER = 9;
    public static final int  GARDEN = 10;
    public static final int  OCEAN = 11;
    public static final int  WOOD_FLOOR = 12;
    public static final int  EVIL_FLOOR = 13;
    //special tiles
    public static final int  ADOOR = -100;
    public static final int  ASIGN = -101;
    public static final int  AWALL = -102;
    
    public static final double TILES_RANDOM = 0.15d;
    
    public static final int[] toolTile = {0, 101, 120, 1, 31, 301, 130, 18, 201, 210, 501, 401, 410, 501, 174, 184,
					  14, 67, 66, 68, 25, 17, 20, 23, 47, 22, 70, 16, 80, 60, 64, 520, 521,
					  302, 310, 173, 183, 182, 170, 181, 172, 180, 171, 174, 560, 530}; //<--maps tilenum to real tiles.

    public static final String[] TILE_NAMES = Messages.getArray("EditorConstants.", 0, 45, "rpg.editor");
}
