/*
 * Revisions:
 *
 * 6/17/03 - Version 1.0
 */
package net.crystalrudiments.rpg.person;

/**
 * Represents a character in the game; either player or computer agent.
 * Contains data such as strength, endurance, dexterity, magic, etc.
 * 
 * @author <A HREF="mailto:gte459u@mail.gatech.edu">Adam Davis </A>
 */
public abstract class Person
{

    public static final int HAPPY = 1;

    public static final int SAD = 2;

    public static final int TIRED = 4;

    public static final int MAD = 8;

    public static final int DEPRESSED = 16;

    public static final int SICK = 32;

    public static final int AFRAID = 64;

    String fileName = "";

    String name = "";

    String mapCode = "";

    int strength = 0;

    int endurance = 0;

    int dexterity = 0;

    int magic = 0;

    int health = 0;

    int attack = 0;

    int defense = 0;

    int posx = 0;

    int posy = 0;

    int imageNum = 0;

    public Person()
    {
        posx = 49;
        posy = 49;
    }

    public String toString()
    {
        return name;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setFileName( String fname )
    {
        fileName = fname;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setMapCode( String code )
    {
        mapCode = code;
    }

    public String getMapCode()
    {
        return mapCode;
    }

    public void setImageNum( int i )
    {
        imageNum = i;
    }

    public int getImageNum()
    {
        return imageNum;
    }

    public abstract void saveState();

    public abstract void load();

    /** accesses posx. */
    public int getX()
    {
        return posx;
    }

    public int getY()
    {
        return posy;
    }

    public void changeX( int dx )
    {
        posx += dx;
    }

    public void changeY( int dy )
    {
        posy += dy;
    }

    public void setPosition( int x, int y )
    {
        posx = x;
        posy = y;
    }
}
