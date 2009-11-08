/*
 * Revisions:
 *
 * 6/17/03 - Version 1.0
 */
package net.crystalrudiments.rpg.person;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import net.crystalrudiments.rpg.RPGConstants;
import net.crystalrudiments.rpg.game.GameObjectFactory;

/**
 * Represents the player (user) in the game.
 * 
 * @author <A HREF="mailto:gte459u@mail.gatech.edu">Adam Davis </A>
 * @version 1.0, June 18, 2003
 */
public class Player extends Person implements Serializable, RPGConstants
{

    private static final long serialVersionUID = 5134174891L;

    static final boolean bDEBUG = true;

    /** Holds IGameObject instances. */
    private final List inventory = new LinkedList(); //will hold GameObjects.

    /**
     * Constructor.
     */
    public Player( String name )
    {
        this.name = name;
        fileName = name + ".plyr";
        setMapCode( FIRST_MAP );
        this.initValues( 100, 100, 100, 0, 100, 100, 100 );
    }

    public void pickUp( int objId )
    {
        inventory.add( GameObjectFactory.makeGameObject( objId ) );
    }

    public List getInventory()
    {
        return inventory;
    }

    public void initValues( int strength, int endurance, int dexterity, int magic, int health, int attack, int defense )
    {
        this.strength = strength;
        this.endurance = endurance;
        this.dexterity = dexterity;
        this.magic = magic;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
    }

    public void save() throws IOException
    {
        Player.savePlayer( this );
    }

    //--SAVING AND LOADING:

    public static void savePlayer( Player player ) throws IOException
    {
        FileOutputStream fout = new FileOutputStream( player.getFileName() );
        ObjectOutputStream out = new ObjectOutputStream( fout );
        out.writeObject( player );
        out.close();
    }

    public static Player loadPlayer( String fname ) throws IOException
    {
        FileInputStream fin = new FileInputStream( fname );
        ObjectInputStream in = new ObjectInputStream( fin );
        Player p = null;
        try
        {
            p = ( Player ) in.readObject();
        } catch ( ClassNotFoundException cnfe )
        {
            cnfe.printStackTrace();
        }
        in.close();
        p.setFileName( fname );
        return p;
    }

    public String toString()
    {
        String ret = getName();
        ret += "\nStrength: " + strength;
        ret += "\nEdurance: " + endurance;
        ret += "\nDexterity: " + dexterity;
        ret += "\nMagic: " + magic;
        ret += "\nHealth: " + health;
        ret += "\nAttack: " + attack;
        ret += "\nDefense: " + defense;
        ret += "\n";
        return ret;
    }

    public static void main( String[] args )
    {
        Player p = new Player( "test" );
        try
        {
            Player.savePlayer( p );
            p = Player.loadPlayer( "test.plyr" );
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
        System.out.println( p );
    }

    /**
     * @see net.crystalrudiments.rpg.person.Person#saveState()
     */
    public void saveState()
    {
        try
        {
            save();
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    /**
     * @see net.crystalrudiments.rpg.person.Person#load()
     */
    public void load()
    {
        try
        {
            Player p = Player.loadPlayer( this.fileName );
            this.copy( p );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }

    }

    /**
     * @param p
     */
    private void copy( Player p )
    {
        this.attack = p.attack;
        this.defense = p.defense;
        this.dexterity = p.dexterity;
        this.endurance = p.endurance;
        this.health = p.health;
        this.imageNum = p.imageNum;
        this.inventory.clear();
        this.inventory.addAll( p.inventory );
        this.magic = p.magic;
        this.mapCode = p.mapCode;
        this.name = p.name;
        this.posx = p.posx;
        this.posy = p.posy;
        this.strength = p.strength;
    }
}
