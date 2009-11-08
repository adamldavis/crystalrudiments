/*
 * Created on Jun 13, 2005 by Adam. 
 * IBoard
 */
package net.crystalrudiments.rpg;

import java.io.FileNotFoundException;

import net.crystalrudiments.rpg.person.Person;
import net.crystalrudiments.rpg.person.Player;

/**
 * Interface to the game board, which represents the map of the game. 
 * The board acts as a liason between the characters and the {@link Player}.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis </A>
 * @version Version 1.0, Jun 13, 2005
 */
public interface IBoard
{

    public static final int DIMENSION = 100;

    public static final String PEOPLE_FILE = "people00.dat";

    public static final String OBJECTS_FILE = "objects0.dat";

    /**
     * Loads map files and people using "code" as upper left corner. Returns
     * code of file not found if applicable.
     */
    public String load( String code );

    /** Saves all quadrants and people. */
    public void save( boolean force );

    public void moveUp() throws MoveException;

    public void moveDown() throws MoveException;

    /**
     * Moves the map by loading the proper files in the proper place given the
     * input.
     */
    public void moveMap( int oldQuad, int newQuad ) throws MoveException;

    //---------------------------ACCESSORS AND MODIFIERS::
    public void setPeopleFile( String s );

    public void setObjectsFile( String s );

    public String getCode();

    public String getCode( int i );

    public String getCode( int x, int y );

    /** Set whether to automatically save changes when jumping maps. */
    public void setAutoSave( boolean yes );

    public boolean getAutoSave();

    /** Asks user. */
    public void changeAutoSave();

    public int getTile( int x, int y );

    /** sets the given tile if within bounds and set dirty bit. */
    public void setTile( int x, int y, int tile );

    public int getLight( int x, int y );

    public void setLight( int x, int y, int lite );

    public boolean getSee( int x, int y );

    /** Gets the int representing the person, if any, on (x,y). */
    public Person getPerson( int x, int y );

    /** Gets the int representing the object, if any, on (x,y). */
    public int getObject( int x, int y );

    /**
     * Puts the given object at x, y position. objects is implemented as a
     * TreeSet, therefore is always sorted. OBJECTS XMM XMM, 14, 15, 1
     */
    public void putObject( int obj, int x, int y );

    public void putPerson( int x, int y, String name, int img );

    /** Adds a line representing a person (ex: XMM, 12, 13, adam, 1). */
    public void addPerson( String str );

    public void removeObjectAt( int x, int y );

    public void removePersonAt( int x, int y );

    /**
     * Uses light(x,y) to "lighten" up all squares on the map with the same
     * light value.
     */
    public void lightRoom( int x, int y );

    /**
     * Lights any squares around the given position if light(x,y)==0.
     */
    public void lightEffect( int x, int y );

    /** Saves objects to objectsFile. */
    public void saveObjects();

    /**
     * Saves peopleNames to peopleFile and people (Agents).
     */
    public void savePeople();

    public void saveMap( String code, int quad );

    /**
     * Loads a map.
     */
    public void loadMap( String code, int quad ) throws FileNotFoundException;

    /** Loads relevant object lines only. */
    public void loadObjects( String code );

    /**
     * Only loads Agents that appear in the given "code" map.
     */
    public void loadPeople( String code );

    /**
     * Loads people files with the following format: CODE, x, y, name, imagei
     * <BR>
     * PEOPLE <BR>
     * XMM <BR>
     * XMM, 42, 15, adam, 1 <BR>
     * XMM, 41, 15, landon, 3 <BR>
     * XYM <BR>
     * XYM, 33, 1, morasha, 5 <BR>
     * Puts the result in the SortedSet peopleNames.
     * 
     * @see AIPerson
     */
    public void loadPeople();
}