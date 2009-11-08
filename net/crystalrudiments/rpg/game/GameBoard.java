/*
 * Revisions:
 *
 * 6/17/03 - Version 1.0
 */
package net.crystalrudiments.rpg.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import net.crystalrudiments.common.EncodingUtil;
import net.crystalrudiments.rpg.Board;
import net.crystalrudiments.rpg.IOHelper;
import net.crystalrudiments.rpg.Messages;
import net.crystalrudiments.rpg.RPGConstants;
import net.crystalrudiments.rpg.person.Player;

/**
 * Represents the map (board) in the JMiedor game. Contains all Persons on the
 * board, all map tiles, objects, and sight information about the board.
 * <P>
 * The board acts as a liason between the characters and the {@link Player}.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 */
public class GameBoard extends Board implements RPGConstants, IGameBoard
{

    static final boolean bDEBUG = true;

    private SortedSet mapChanges = null;

    private String changesFile = "default.chng"; //$NON-NLS-1$

    public GameBoard()
    {
        this( FIRST_MAP );
    }

    public GameBoard( String code )
    {
        this( code, null, null, null );
    }

    /**
     * Please use this constructor with inputs depending on the current "saved
     * game" chosen. For instance, each game instance has its own people file
     * and object file.
     */
    public GameBoard( String code, String pfile, String ofile, String changesFile )
    {
        super( code, pfile, ofile );
        mapChanges = new TreeSet();
        if ( changesFile != null )
        {
            this.changesFile = changesFile;
            loadChanges();
            applyChanges();
        }
    }

    public void setChangesFile( String str )
    {
        File file = new File( str );
        makeIfNeeded( file, Messages.getString("GameBoard.1", "rpg.game") ); //$NON-NLS-1$
        changesFile = str;
    }

    public void setPeopleFile( String s )
    {
        File file = new File( s );
        makeIfNeeded( file, Messages.getString("GameBoard.2", "rpg.game") ); //$NON-NLS-1$
        super.setPeopleFile( s );
    }

    public void setObjectsFile( String s )
    {
        File file = new File( s );
        makeIfNeeded( file, Messages.getString("GameBoard.3", "rpg.game") ); //$NON-NLS-1$
        super.setObjectsFile( s );
    }

    public void makeIfNeeded( File file, String str )
    {
        if ( !file.exists() )
        {
            PrintWriter pw = null;
            pw = IOHelper.open( file.getAbsolutePath(), pw );
            pw.println( str );
            IOHelper.close( pw );
        }
    }

    /** Saves all quadrants and people. */
    public void save()
    {
        //save quadrants.
        saveChanges();

        super.savePeople();
        super.saveObjects();
    }

    /**
     * Moves the map by loading the proper files in the proper place given the
     * input.
     */
    public void moveMap( int oldQuad, int newQuad )
    {
        //quad = [1-4]
        //1 2
        //3 4
        if ( oldQuad == newQuad ) return;
        this.save(); //only saves changes.
        int dx = 0;
        int dy = 0;
        if ( ( oldQuad + newQuad ) % 2 == 1 )
        { //exactly one is odd.
            if ( oldQuad < newQuad )
                dx = -1;
            else
                dx = 1;
        }
        if ( ( oldQuad + newQuad ) > 3 && ( ( oldQuad + newQuad ) < 7 ) )
        {
            if ( oldQuad < newQuad )
                dy = -1;
            else
                dy = 1;
        }
        String code = getCode( 0 );
        int mapX = alphaDecode( left( code, 1 ) );
        int mapY = alphaDecode( code.substring( 1, 2 ) );
        code = alphaEncode( mapX + dx ) + alphaEncode( mapY + dy ) + code.substring( 2 );
        String nfcode = super.load( code );
        if ( nfcode != null )
        {
            System.err.println( "GameBoard: Went out of bounds." ); //$NON-NLS-1$
        } else
        {
            loadChanges();
            applyChanges();
        }
    }

    private final static int alphaDecode( String s )
    {
        return EncodingUtil.alphaDecode( s );
    }

    private final static String alphaEncode( int i )
    {
        return EncodingUtil.alphaEncode( i );
    }

    /** Saves any changes made to current maps to one "Game" file. */
    protected void saveChanges()
    {
        int num = ( int ) ( Math.random() * 99900d ) + 99;
        File ftemp = new File( "temp" + num + ".temp" ); //$NON-NLS-1$ //$NON-NLS-2$
        File fout = new File( changesFile );
        if ( mapChanges != null && mapChanges.size() > 0 ) super.saveInterlace( mapChanges, ftemp, fout );
    }

    protected void loadChanges()
    {
        if ( changesFile == null )
        {
            System.err.println( "Danger: changes file is null." ); //$NON-NLS-1$
            return;
        }
        mapChanges.clear(); //first clear the list of changes.
        BufferedReader br = null;
        boolean incode = false;
        br = IOHelper.open( changesFile, br );
        if ( br == null ) return;
        //load
        String sLine = ""; //$NON-NLS-1$
        for ( int i = 0; sLine != null; i++ )
        {
            sLine = IOHelper.readLine( br );
            if ( sLine == null ) break;
            if ( i == 0 && ( !sLine.startsWith( Messages.getString("GameBoard.4", "rpg.game") ) ) ) //$NON-NLS-1$
            {
                System.err.println( "Error: corrupted changes file. line 0" ); //$NON-NLS-1$
                break;
            }
            if ( i > 0 )
            {
                if ( sLine.length() == 3 )
                {
                    incode = false;
                    for ( int n = 0; n < 4; n++ )
                    {
                        if ( super.getCode( n ).equals( sLine ) ) incode = true;
                    }
                }
                if ( incode ) mapChanges.add( sLine );
            }
        }
        IOHelper.close( br );
    }

    protected void applyChanges()
    {
        int quad = -1;
        int x = 0, y = 0;
        for ( Iterator iter = mapChanges.iterator(); iter.hasNext(); )
        {
            String line = ( String ) iter.next();
            if ( line.length() == 3 )
            {
                quad = -1;
                for ( int i = 0; i < 4; i++ )
                    if ( line.equals( getCode( i ) ) ) quad = i;
                continue;
            } else
            {
                if ( quad == -1 ) continue;
                int tile = 0;
                try
                {
                    tile = Integer.parseInt( line.substring( line.lastIndexOf( " " ) + 1 ) ); //$NON-NLS-1$
                } catch ( Exception e )
                {
                    e.printStackTrace();
                    continue;
                }
                String xandy = line.substring( line.indexOf( "," ) + 1, line.lastIndexOf( "," ) ).trim(); //$NON-NLS-1$ //$NON-NLS-2$
                try
                {
                    x = Integer.parseInt( xandy.substring( 0, xandy.indexOf( ',' ) ) );
                } catch ( Exception e )
                {
                    e.printStackTrace();
                    continue;
                }
                try
                {
                    y = Integer.parseInt( xandy.substring( xandy.indexOf( ',' ) + 1 ).trim() );
                } catch ( Exception e )
                {
                    e.printStackTrace();
                    continue;
                }
                switch ( quad ) {
                case 0:
                    break;
                case 1:
                    x += 50;
                    break;
                case 2:
                    y += 50;
                    break;
                case 3:
                    x += 50;
                    y += 50;
                    break;
                }
                super.setTile( x, y, tile );
            }
        }
    } //method

    //---------------------------ACCESSORS AND MODIFIERS::
    //public void setFileName(String fname) { fileName = fname;}
    //public String getFileName() { return fileName; }

    /** Logs changes to mapChanges. */
    public void setTile( int x, int y, int tile )
    {
        if ( tile != getTile( x, y ) )
        {
            removeChangesAt( x, y );
            if ( !mapChanges.contains( getCode( x, y ) ) )
            {
                mapChanges.add( getCode( x, y ) );
            }
            mapChanges.add( getCode( x, y ) + ", " + ( x % 50 ) + ", " + ( y % 50 ) + ", " + tile ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            super.setTile( x, y, tile );
        }
    }

    private void removeChangesAt( int x, int y )
    {
        String code = getCode( x, y );
        for ( Iterator iter = mapChanges.iterator(); iter.hasNext(); )
        {
            String change = ( String ) iter.next();
            if ( change.length() > 3 && code.equals( change.substring( 0, 3 ) ) )
            {
                String temp = change.substring( 0, change.lastIndexOf( ", " ) ); //$NON-NLS-1$
                if ( temp.equals( code + ", " + ( x % 50 ) + ", " + ( y % 50 ) ) ) //$NON-NLS-1$ //$NON-NLS-2$
                {
                    iter.remove();
                }
            }
        }
    }

    public static void main( String[] args )
    {

        IGameBoard g = new GameBoard();
        //print out tiles
        for ( int x = 0; x < 100; x++ )
        {
            String sline = ""; //$NON-NLS-1$
            //for (int y=0; y < 100; y++) {
            //sline += " " + (g.getTile(x, y));
            //}System.out.println(sline);
        }
    }
}//GameBoard
