/*
 * Revisions:
 *
 * 6/17/03 - Version 1.0
 */
package net.crystalrudiments.rpg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;

import net.crystalrudiments.common.EncodingUtil;
import net.crystalrudiments.common.logging.LoggerFactory;
import net.crystalrudiments.lang.VBObject;
import net.crystalrudiments.rpg.person.AIPerson;
import net.crystalrudiments.rpg.person.Person;
import net.crystalrudiments.rpg.person.Player;

/**
 * Represents the map (board) in the game. Contains all Persons on the
 * board, all map tiles, objects, and sight information about the board.
 * <P>
 * The board acts as a liason between the characters and the {@link Player}.
 * <P>
 * Copyright: 2005
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 2.0
 */
public class Board extends VBObject implements RPGConstants, IBoard
{

    static final boolean bDEBUG = true;

    /** contains agent and Player.*/
    private List people; 

    /**
     * contains the names and positions of every person.
     */
    private SortedSet peopleNames; 

    /** set of objects on current map.*/
    private SortedSet objects; 

    private int[][] tiles;

    private int[][] light;

    private boolean[][] see;

    private String[] mapCodes;

    private boolean[] dirty;

    /** Auto save for editor.*/
    private boolean autoSave = true; 

    private static String peopleFile = "";

    private static String objectsFile = "";

    public Board()
    {
        this( FIRST_MAP );
    }

    public Board( String code )
    {
        this( code, null, null );
    }

    public Board( String code, String pfile, String ofile )
    {
        tiles = new int[DIMENSION][DIMENSION];
        light = new int[DIMENSION][DIMENSION];
        see = new boolean[DIMENSION][DIMENSION];
        mapCodes = new String[4];
        dirty = new boolean[4];
        people = new Vector();
        peopleNames = new TreeSet();
        objects = new TreeSet();
        if ( pfile == null && peopleFile.equals( "" ) )
            peopleFile = PEOPLE_FILE;
        else if ( pfile != null ) peopleFile = pfile;
        if ( ofile == null && objectsFile.equals( "" ) )
            objectsFile = OBJECTS_FILE;
        else if ( ofile != null ) objectsFile = ofile;
        load( code );
    }

    /**
     * Loads map files and people using "code" as upper left corner. Returns
     * code of file not found if applicable.
     */
    public String load( String code )
    {
        int x = EncodingUtil.alphaDecode( code.substring( 0, 1 ) );
        int y = EncodingUtil.alphaDecode( code.substring( 1, 2 ) );
        mapCodes[0] = code;
        mapCodes[1] = EncodingUtil.alphaEncode( x + 1 ) + code.substring( 1 );
        mapCodes[2] = EncodingUtil.alphaEncode( x ) + EncodingUtil.alphaEncode( y + 1 ) + code.substring( 2 );
        mapCodes[3] = EncodingUtil.alphaEncode( x + 1 ) + EncodingUtil.alphaEncode( y + 1 ) + code.substring( 2 );
        if ( peopleNames.size() == 0 )
        {
            loadPeople();
        }
        people.clear();
        objects.clear();
        for ( int i = 0; i < 4; i++ )
        {
            try
            {
                loadMap( mapCodes[i], i + 1 );
            } catch ( FileNotFoundException fnfe )
            {
                return mapCodes[i];
            }
            loadPeople( mapCodes[i] );
            loadObjects( mapCodes[i] );
        }
        Arrays.fill( dirty, false );
        return null;
    }

    /** Saves all quadrants and people. */
    public void save( boolean force )
    {
        //save quadrants.
        for ( int i = 0; i < 4; i++ )
        {
            if ( force || dirty[i] ) saveMap( mapCodes[i], i + 1 );
        }
        Arrays.fill( dirty, false );
        savePeople();
        saveObjects();
    }

    public void moveUp() throws MoveException
    {
        //TODO: fill in.
        if ( autoSave ) this.save( false ); //only saves if changed.
        String code = mapCodes[0];
        int mapZ = EncodingUtil.alphaDecode( code.substring( 2 ) );
        code = code.substring( 0, 2 ) + EncodingUtil.alphaEncode( mapZ + 1 );
        String nfcode = load( code );

        if ( nfcode != null )
        {
            LoggerFactory.getDefaultLogger().warning( "Map not found: " + nfcode );
            throw new MoveException( nfcode );
        }
    }

    public void moveDown() throws MoveException
    {
        //TODO: fill in.
        if ( autoSave ) this.save( false ); //only saves if changed.
        String code = mapCodes[0];
        int mapZ = EncodingUtil.alphaDecode( code.substring( 2 ) );
        code = code.substring( 0, 2 ) + EncodingUtil.alphaEncode( mapZ - 1 );
        String nfcode = load( code );

        if ( nfcode != null )
        {
            LoggerFactory.getDefaultLogger().warning( "Map not found: " + nfcode );
            throw new MoveException( nfcode );
        }
    }

    /**
     * Moves the map by loading the proper files in the proper place given the
     * input.
     */
    public void moveMap( int oldQuad, int newQuad ) throws MoveException
    {
        //quad = [1-4]
        //1 2
        //3 4
        if ( oldQuad == newQuad ) return;
        if ( autoSave ) this.save( false ); //only saves if changed.
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
        String code = mapCodes[0];
        int mapX = EncodingUtil.alphaDecode( left( code, 1 ) );
        int mapY = EncodingUtil.alphaDecode( code.substring( 1, 2 ) );
        code = EncodingUtil.alphaEncode( mapX + dx ) + EncodingUtil.alphaEncode( mapY + dy ) + code.substring( 2 );
        String nfcode = load( code );
        if ( nfcode != null )
        {
            LoggerFactory.getDefaultLogger().warning( "Map not found: " + nfcode );
            throw new MoveException( nfcode );
        }
    }

    //---------------------------ACCESSORS AND MODIFIERS::
    //public void setFileName(String fname) { fileName = fname;}
    //public String getFileName() { return fileName; }
    public void setPeopleFile( String s )
    {
        peopleFile = s;
    }

    public void setObjectsFile( String s )
    {
        objectsFile = s;
    }

    public String getCode()
    {
        return getCode( 0 );
    }

    public String getCode( int i )
    {
        return mapCodes[i];
    }

    public String getCode( int x, int y )
    {
        if ( x < 50 && y < 50 )
            return mapCodes[0];
        else if ( y < 50 )
            return mapCodes[1];
        else if ( x < 50 )
            return mapCodes[2];
        else
            return mapCodes[3];
    }

    /** Set whether to automatically save changes when jumping maps. */
    public void setAutoSave( boolean yes )
    {
        autoSave = yes;
    }

    public boolean getAutoSave()
    {
        return autoSave;
    }

    /** Asks user. */
    public void changeAutoSave()
    {
        int ret = MsgBox( "Auto-Save on?", vbYesNo );
        setAutoSave( vbYes == ret );
    }

    public int getTile( int x, int y )
    {
        if ( x >= 0 && x < DIMENSION && y >= 0 && y < DIMENSION )
        {
            return tiles[x][y];
        } else
            return 0;
    }

    /** sets the given tile if within bounds and set dirty bit. */
    public void setTile( int x, int y, int tile )
    {
        if ( x >= 0 && x < DIMENSION && y >= 0 && y < DIMENSION )
        {
            tiles[x][y] = tile;
            if ( x < ( DIMENSION / 2 ) )
            {
                if ( y < ( DIMENSION / 2 ) ) dirty[0] = true;
                if ( y >= ( DIMENSION / 2 ) ) dirty[2] = true;
            } else if ( x >= ( DIMENSION / 2 ) )
            {
                if ( y < ( DIMENSION / 2 ) ) dirty[1] = true;
                if ( y >= ( DIMENSION / 2 ) ) dirty[3] = true;
            }
        }
    }

    public int getLight( int x, int y )
    {
        if ( x >= 0 && x < DIMENSION && y >= 0 && y < DIMENSION )
        {
            return light[x][y];
        } else
            return 0;
    }

    public void setLight( int x, int y, int lite )
    {
        if ( x >= 0 && x < DIMENSION && y >= 0 && y < DIMENSION )
        {
            light[x][y] = lite;
            if ( x < ( DIMENSION / 2 ) )
            {
                if ( y < ( DIMENSION / 2 ) ) dirty[0] = true;
                if ( y >= ( DIMENSION / 2 ) ) dirty[2] = true;
            } else if ( x >= ( DIMENSION / 2 ) )
            {
                if ( y < ( DIMENSION / 2 ) ) dirty[1] = true;
                if ( y >= ( DIMENSION / 2 ) ) dirty[3] = true;
            }
        }
    }

    public boolean getSee( int x, int y )
    {
        if ( x >= 0 && x < DIMENSION && y >= 0 && y < DIMENSION )
        {
            return see[x][y];
        } else
            return false;
    }

    /** Gets the int representing the person, if any, on (x,y). */
    public Person getPerson( int x, int y )
    {
        for ( int i = 0; i < people.size(); i++ )
        {
            Person p = ( Person ) people.get( i );
            if ( p.getMapCode().equals( getCode( x, y ) ) && p.getX() == ( x % 50 ) && p.getY() == ( y % 50 ) ) { return p; }
        }
        return null;
    }

    /** Gets the int representing the object, if any, on (x,y). */
    public int getObject( int x, int y )
    {
        String code = getCode( x, y );
        String[] sArr = null;
        for ( Iterator iter = objects.iterator(); iter.hasNext(); )
        {
            String str = ( String ) iter.next();
            if ( str.length() == 3 ) continue; //skip place holders.
            sArr = Split( str, ", " );
            if ( sArr.length != 4 ) System.err.println( "Error parsing objects list:" + str );
            if ( sArr[0].equals( code ) )
            {
                if ( sArr[1].equals( String.valueOf( x % 50 ) ) )
                {
                    if ( sArr[2].equals( String.valueOf( y % 50 ) ) )
                    {
                        try
                        {
                            return Integer.parseInt( sArr[3] );
                        } catch ( NumberFormatException e )
                        {
                            e.printStackTrace();
                            return -1;
                        }
                    }
                }
            }
        }//i
        return -1;
    }

    private int getPersonIndexOf( String str )
    {
        int n = -1;
        for ( int i = 0; i < PERSON_NAMES.length; i++ )
        {
            if ( PERSON_NAMES[i].equals( str ) )
            {
                n = i;
                break;
            }
        }
        if ( n < 0 || n >= PERSON_NAMES.length )
        {
            System.err.println( "Error: couldn't find " + str + " in PERSON_NAMES." );
            return -1;
        }
        return n;
    }

    /**
     * Puts the given object at x, y position. objects is implemented as a
     * TreeSet, therefore is always sorted. OBJECTS XMM XMM, 14, 15, 1
     */
    public void putObject( int obj, int x, int y )
    {
        String code = getCode( x, y );
        if ( !objects.contains( code ) ) objects.add( code );
        objects.add( code + ", " + CStr( x % 50 ) + ", " + CStr( y % 50 ) + ", " + CStr( obj ) );
    }

    public void putPerson( int x, int y, String name, int img )
    {
        addPerson( getCode( x, y ) + ", " + CStr( x % 50 ) + ", " + CStr( y % 50 ) + ", " + name + ", " + CStr( img ) );
    }

    /** Adds a line representing a person (ex: XMM, 12, 13, adam, 1). */
    public void addPerson( String str )
    {
        String code = str.substring( 0, 3 );
        peopleNames.add( str );
        if ( !peopleNames.contains( code ) ) peopleNames.add( code );
        loadPeople( str.substring( 0, 3 ) );
    }

    public void removeObjectAt( int x, int y )
    {
        String code = getCode( x, y );
        String line = null;
        Iterator iter = objects.iterator();
        if ( !iter.hasNext() ) return;
        for ( line = ( String ) iter.next(); iter.hasNext(); line = ( String ) iter.next() )
        {
            if ( line != null )
            {
                if ( line.startsWith( code + ", " + ( x % 50 ) + ", " + ( y % 50 ) ) )
                {
                    iter.remove();
                    break;
                }
            }
        }//for
    }

    public void removePersonAt( int x, int y )
    {
        String code = getCode( x, y );
        String line = null;
        Iterator iter = peopleNames.iterator();
        if ( !iter.hasNext() ) return;
        for ( line = ( String ) iter.next(); iter.hasNext(); line = ( String ) iter.next() )
        {
            if ( line != null )
            {
                if ( line.startsWith( code + ", " + ( x % 50 ) + ", " + ( y % 50 ) ) )
                {
                    iter.remove();
                    break;
                }
            }
        }//for
        for ( iter = people.iterator(); iter.hasNext(); )
        {
            Person prsn = ( Person ) iter.next();
            if ( prsn.getMapCode().equals( code ) && prsn.getX() == ( x % 50 ) && prsn.getY() == ( y % 50 ) )
            {
                iter.remove();
                break;
            }
        }
    }

    /**
     * Uses light(x,y) to "lighten" up all squares on the map with the same
     * light value.
     */
    public void lightRoom( int x, int y )
    {
        int i, j, num;
        num = light[x][y];
        for ( j = 0; j < DIMENSION; j++ )
            for ( i = 0; i < DIMENSION; i++ )
            {
                if ( light[i][j] == num )
                {
                    see[i][j] = true;
                    lightEffect( i, j );
                } //if
            } //Next i, j
    } //method

    /**
     * Lights any squares around the given position if light(x,y)==0.
     */
    public void lightEffect( int x, int y )
    {
        if ( light[x][y + 1] == 0 ) see[x][y + 1] = true;
        if ( light[x][y - 1] == 0 ) see[x][y - 1] = true;
        if ( light[x + 1][y + 1] == 0 ) see[x + 1][y + 1] = true;
        if ( light[x + 1][y - 1] == 0 ) see[x + 1][y - 1] = true;
        if ( light[x - 1][y + 1] == 0 ) see[x - 1][y + 1] = true;
        if ( light[x - 1][y - 1] == 0 ) see[x - 1][y - 1] = true;
        if ( light[x + 1][y] == 0 ) see[x + 1][y] = true;
        if ( light[x - 1][y] == 0 ) see[x - 1][y] = true;
    }

    /**
     * Replaces only the subset covered by "sset" in the file fout.
     */
    public static void saveInterlace( SortedSet sset, File ftemp, File fout )
    {
        PrintWriter pw = null;
        BufferedReader br = null;
        boolean incode = false;
        String code = "";
        br = IOHelper.open( fout.getName(), br );
        if ( br == null ) return;

        pw = IOHelper.open( ftemp.getName(), pw );
        if ( pw == null ) return;
        //	if (bDEBUG) System.out.println( "sset = " + sset );
        Iterator iter = sset.iterator();
        String str = ( String ) iter.next();
        if ( str.length() == 3 )
        {
            code = str;
        } else
        {
            System.err.println( "Error: first of sset (" + str + ") is not code!" );
            code = str.substring( 0, 3 );
        }
        for ( String line = IOHelper.readLine( br ); ( line != null ); )
        {

            if ( line.equals( code ) )
            { //now replace CODE-lines with sset CODE-lines.
                while ( line != null && line.startsWith( code ) )
                {
                    line = IOHelper.readLine( br );
                }
                for ( ; str.startsWith( code ); str = ( String ) iter.next() )
                {
                    pw.println( str );
                    if ( !iter.hasNext() ) break;
                }
                if ( !str.startsWith( code ) )
                {
                    if ( str.length() != 3 ) System.err.println( "file syntax error (should be code): " + str );
                    code = str;
                }
            } else
            {
                //if code goes before current line....
                if ( line.length() == 3 && line.compareTo( code ) > 0 && iter.hasNext() )
                {
                    for ( ; str.startsWith( code ); str = ( String ) iter.next() )
                    {
                        pw.println( str );
                        if ( !iter.hasNext() ) break;
                    }
                    if ( iter.hasNext() ) code = str;
                }
                pw.println( line );
                line = IOHelper.readLine( br );
            }
        }
        while ( iter.hasNext() )
        { //get rid of remaining...
            pw.println( str );
            str = ( String ) iter.next();
            if ( !iter.hasNext() ) pw.println( str );
        }

        IOHelper.close( pw );
        IOHelper.close( br );

        if ( fout.delete() && ftemp.renameTo( fout ) )
        {
            ftemp.delete();
        } else
        {
            String message = "Error trying to switch " + ftemp.getName() + " file and " + fout.getName() + " file.";
            System.err.println( message );
            LoggerFactory.getDefaultLogger().severe( message );
        }
    }

    /** Saves objects to objectsFile. */
    public void saveObjects()
    {
        if ( objects.size() == 0 ) return;
        File ftemp = new File( "temp1011.tmp" );
        File fobj = new File( objectsFile );
        if ( !fobj.exists() )
        {
            PrintWriter pw = null;
            pw = IOHelper.open( fobj.getName(), pw );
            pw.println( "OBJECTS" );
            IOHelper.close( pw );
        }
        saveInterlace( objects, ftemp, fobj );
    }

    /**
     * Saves peopleNames to peopleFile and people (Agents).
     */
    public void savePeople()
    {
        if ( peopleNames.size() == 0 ) return;
        File ftemp = new File( "temp1012.tmp" );
        File fpep = new File( peopleFile );
        if ( !fpep.exists() )
        {
            PrintWriter pw = null;
            pw = IOHelper.open( fpep.getName(), pw );
            pw.println( "PEOPLE" );
            IOHelper.close( pw );
        }
        saveInterlace( peopleNames, ftemp, fpep );

        //save changes to Agents.
        for ( int i = 0; i < people.size(); i++ )
        {
            ( ( AIPerson ) people.get( i ) ).saveState();
        }
    }

    public void saveMap( String code, int quad )
    {
        //
        int x, y;
        int xOffset = 0, yOffset = 0;
        int fnum;
        String xString;
        //On Error GoTo exitsub;
        //
        switch ( quad ) {
        //break;
        case 1:
            xOffset = 0;
            yOffset = 0;
            break;
        case 2:
            xOffset = 50;
            yOffset = 0;
            break;
        case 3:
            xOffset = 0;
            yOffset = 50;
            break;
        case 4:
            xOffset = 50;
            yOffset = 50;
        default:
        } //switch
        //

        PrintWriter pw = null;
        pw = IOHelper.open( MAP_DIR + File.separator + "map" + code + ".txt", pw );
        if ( pw == null ) return;
        for ( y = 0; y < 50; y++ )
        {
            xString = "";
            for ( x = 0; x < 50; x++ )
            {
                xString = xString + EncodingUtil.encode( tiles[x + xOffset][y + yOffset] )
                        + EncodingUtil.encode( light[x + xOffset][y + yOffset] );
            } //Next x
            pw.println( xString );
        } //Next y
        //GoTo endsub;
        //
        //exitsub:;
        //Close( #fnum);
        //MsgBox( "Error: Could ! Save.");
        //
        //endsub:;
        //Close( #fnum);
        IOHelper.close( pw );
    }

    /**
     * Loads a map.
     */
    public void loadMap( String code, int quad ) throws FileNotFoundException
    {
        int numb, i;
        int x, y;
        int xOffset = 0, yOffset = 0;
        String stg;
        String xStg;
        File file = ( new File( MAP_DIR + File.separator + "map" + code + ".txt" ) );
        //
        //mapCodes[quad] = code ; //<--Change mapCode to reflect change.
        //
        if ( file.exists() == false ) {

        throw new FileNotFoundException( file.getName() );

        //MsgBox( "File not found: " + MAP_DIR + "map" + code + ".txt");
        //return;
        } //if
        //
        switch ( quad ) {
        case 1:
            xOffset = 0;
            yOffset = 0;
            break;
        case 2:
            xOffset = 50;
            yOffset = 0;
            break;
        case 3:
            xOffset = 0;
            yOffset = 50;
            break;
        case 4:
            xOffset = 50;
            yOffset = 50;
        } //switch

        try
        {
            MapManager.open( file );
        } catch ( IOException e )
        {
            LoggerFactory.getDefaultLogger().warning( "Couldn't open file: " + file );
        }

        for ( y = 0; y < 50; y++ )
        {
            xStg = MapManager.readLine();
            for ( x = 0; x < 50; x++ )
            {
                //if (xStg.indexOf("\r") > -1)
                // System.out.println("Ahhhh!!!!!");
                stg = xStg.substring( x * 4, x * 4 + 4 ); //Mid(xStg, (x) * 4 +
                                                          // 1, 4);
                try
                {
                    tiles[x + xOffset][y + yOffset] = EncodingUtil.decode( left( stg, 2 ) );
                    light[x + xOffset][y + yOffset] = EncodingUtil.decode( right( stg, 2 ) );
                } catch ( Exception e )
                {
                    System.err.println( "stg=" + stg + " (x,y)=" + x + ", " + y + ", " + code );
                    e.printStackTrace();
                }
            } //Next x
        } //Next y
        //
        MapManager.close();
    }

    /** Loads relevant object lines only. */
    public void loadObjects( String code )
    {
        BufferedReader br = null;
        boolean incode = false;
        br = IOHelper.open( objectsFile, br );
        if ( br == null ) return;
        //load
        String sLine = "";
        for ( int i = 0; sLine != null; i++ )
        {
            sLine = IOHelper.readLine( br );
            if ( sLine == null ) break;
            if ( i == 0 && ( !sLine.startsWith( "OBJECTS" ) ) )
            {
                System.err.println( "corrupted Objects file. line 0" );
                break;
            }
            if ( i > 0 && sLine != null )
            {
                if ( sLine.length() == 3 )
                {
                    if ( sLine.equals( code ) )
                        incode = true;
                    else
                        incode = false;
                }
                if ( incode ) objects.add( sLine );
            }
        }
        IOHelper.close( br );
    }

    /**
     * Only loads Agents that appear in the given "code" map.
     */
    public void loadPeople( String code )
    {
        String str = "   ";
        StringTokenizer strtok = null;
        boolean incode = false;

        for ( Iterator iter = peopleNames.iterator(); iter.hasNext(); )
        {
            str = ( String ) iter.next();
            if ( str.length() == 3 )
            {
                if ( str.equals( code ) )
                    incode = true;
                else
                    incode = false;
                if ( str.compareTo( code ) > 0 ) break; //passed code already.
            } else if ( incode )
            {
                strtok = new StringTokenizer( str, ", " );
                if ( strtok.countTokens() != 5 || ( !strtok.nextToken().equals( code ) ) )
                {
                    System.err.println( "corrupted People set: " + str );
                    continue;
                }
                int x = Integer.parseInt( strtok.nextToken() );
                int y = Integer.parseInt( strtok.nextToken() );
                String name = strtok.nextToken();
                int img = Integer.parseInt( strtok.nextToken() );
                Person prsn = new AIPerson( name, x, y, img );
                prsn.setMapCode( code );
                people.add( prsn );
            }
        }
    }

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
    public void loadPeople()
    {
        BufferedReader br = null;
        br = IOHelper.open( peopleFile, br );
        if ( br == null ) return;
        //load
        String sLine = "";
        for ( int i = 0; sLine != null; i++ )
        {
            sLine = IOHelper.readLine( br );
            if ( sLine == null ) break;
            if ( i == 0 && ( !sLine.startsWith( "PEOPLE" ) ) )
            {
                System.err.println( "corrupted People file. line 0" );
                break;
            }
            if ( i > 0 && sLine != null )
            {
                peopleNames.add( sLine );
            }
        }
        IOHelper.close( br );
    }

}
