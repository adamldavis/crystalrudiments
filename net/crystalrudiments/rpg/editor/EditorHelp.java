package net.crystalrudiments.rpg.editor;

import java.util.Arrays;
import java.util.logging.Logger;

import net.crystalrudiments.common.logging.LoggerFactory;
import net.crystalrudiments.rpg.IBoard;
import net.crystalrudiments.rpg.Messages;

/**
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, June 28, 2003
 */
public class EditorHelp implements EditorConstants
{

    private static final Logger LOG = LoggerFactory.createLogger();
    
    public static final int[] DX =
    { 0, 1, -1, 0, 1, -1, 0, 1, -1, 2, -2, 2, -2, 2, 1, 0, -1, -2, 2, -2, 2, 1, 0, -1, -2 };

    public static final int[] DY =
    { 0, 0, 0, 1, 1, 1, -1, -1, -1, 0, 0, 1, 1, 2, 2, 2, 2, 2, -1, -1, -2, -2, -2, -2, -2 };

    private IBoard board = null;

    private int tilenum = 0; //set by clicking toolbar button.

    private boolean randomized = false; //option

    private int blockSize = 1;

    private int currentPerson = -1;

    private int currentObject = -1;

    public EditorHelp( IBoard board )
    {
        super();
        this.board = board;
    }

    /** @see #getRandomized() */
    public void setRandomized( boolean b )
    {
        randomized = b;
    }

    public void setBlockSize( int n )
    {
        blockSize = n;
    }

    public int getBlockSize()
    {
        return blockSize;
    }

    public void setTileNum( int tn )
    {
        //if (tn < 0 || tn >= toolTile.length) {
        //  LOG.severe("Error: tileNum = " + tn + " is out of range.");
        //  return;
        //}
        tilenum = tn;
    }

    public static int getToolTileOf( String str )
    {
        int n = -1;
        for ( int i = 0; i < TILE_NAMES.length; i++ )
        {
            if ( TILE_NAMES[i].equals( str ) )
            {
                n = i;
                break;
            }
        }
        return n;
    }

    public static int getToolTileIndexOf( int tile )
    {
        int n = -1;
        for ( int i = 0; i < toolTile.length; i++ )
        {
            if ( toolTile[i] == tile )
            {
                n = i;
                break;
            }
        }
        return n;
    }

    public void setTileNum( String str )
    {
        int n = -1;
        if ( str == null ) return;
        n = getToolTileOf( str );
        if ( n < 0 || n >= toolTile.length )
        {
            if ( str.equals( Messages.getString("EditorHelp.door", "rpg.editor") ) ) //$NON-NLS-1$
                tilenum = ADOOR;
            else if ( str.equals( Messages.getString("EditorHelp.wall", "rpg.editor") ) ) //$NON-NLS-1$
                tilenum = AWALL;
            else if ( str.equals( Messages.getString("EditorHelp.sign", "rpg.editor") ) ) //$NON-NLS-1$
                tilenum = ASIGN;
            else
                LOG.severe( "Error: couldn't find " + str + " in TILE_NAMES." ); //$NON-NLS-1$ //$NON-NLS-2$
            return;
        } else
            setTileNum( -1 * n );
    }

    public void setCurrentPerson( String str )
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
        if ( str.equals( Messages.getString("EditorHelp.delete", "rpg.editor") ) ) n = iDELETE; //$NON-NLS-1$
        if ( n < 0 )
        {
            LOG.severe( "Error: couldn't find " + str + " in PERSON_NAMES." ); //$NON-NLS-1$ //$NON-NLS-2$
            return;
        }
        currentPerson = n;
    }

    public void setCurrentObject( String str )
    {
        int n = -1;
        for ( int i = 0; i < OBJECT_NAMES.length; i++ )
        {
            if ( OBJECT_NAMES[i].equals( str ) )
            {
                n = i;
                break;
            }
        }
        if ( str.equals( Messages.getString("EditorHelp.delete", "rpg.editor") ) ) n = iDELETE; //$NON-NLS-1$
        if ( n < 0 )
        {
            LOG.severe( "Error: couldn't find " + str + " in OBJECT_NAMES." ); //$NON-NLS-1$ //$NON-NLS-2$
            return;
        }
        currentObject = n;
    }

    /**
     * returns variable, whether to randomize plain tiles (ex: grass).
     */
    public boolean getRandomized()
    {
        return randomized;
    }

    /** Flips the bit. */
    public void flipRandomized()
    {
        randomized = !randomized;
    }

    /*
     * public static void Initialize( ) { toolTile[0] = 0; toolTile[1] = 101 ;
     * //grass toolTile[2] = 120 ; //trees toolTile[3] = 1 ; //castle floor
     * toolTile[4] = 31 ; //c-wall toolTile[5] = 301 ; //swamp toolTile[6] = 130 ;
     * //pond water toolTile[7] = 18 ; //checkerboard toolTile[8] = 201 ;
     * //beach toolTile[9] = 210 ; //ocean toolTile[10] = 501 ; //desert
     * toolTile[11] = 401 ; //cave toolTile[12] = 410 ; //cavewall toolTile[13] =
     * 501 ; //island toolTile[14] = 174 ; //wooden-flor toolTile[15] = 184 ;
     * //wooden-chair } //method
     */

    /** Returns a string[] with all possible person names. */
    public static String[] getPossiblePeople()
    {
        return PERSON_NAMES;
    }

    /** Returns a string[] with all possible object names. */
    public static String[] getPossibleObjects()
    {
        return OBJECT_NAMES;
    }

    /**
     * Returns string[] with all of the possible tile names.
     */
    public static String[] getTileNames()
    {
        return TILE_NAMES;
    }

    /** max number of tiles in picture. */
    public static int maxTiles( int n )
    {
        switch ( n ) {
        case 0:
            return 90; //c
        case 1:
            return 90; //f
        case 2:
            return 50; //b
        case 3:
            return 50; //s
        case 4:
            return 50; //C
        case 5:
            return 60; //D
        case 6:
            return 50; //I
        case 7:
            return 50;
        default:
            return 50;
        }
    }

    /** Puts the currently chosen person at the location. */
    public void putPerson( int x, int y )
    {
        if ( currentPerson == iDELETE )
            board.removePersonAt( x, y );
        else
            board.putPerson( x, y, PERSON_NAMES[currentPerson], currentPerson );
        currentPerson = -1;
    }

    /** Puts the currently chosen object at x,y. */
    public void putObject( int x, int y )
    {
        if ( currentObject == iDELETE )
            board.removeObjectAt( x, y );
        else
            board.putObject( currentObject, x, y );
        currentObject = -1;
    }

    /**
     * Handles chaging of the tiles on the map using mouse or keyboard.
     */
    public void changeTile( int num, int x, int y )
    {
        if ( currentPerson > -1 )
        {
            putPerson( x, y );
            return;
        } else if ( currentObject > -1 )
        {
            putObject( x, y );
            return;
        }
        switch ( blockSize ) {
        case 1:
            changeOneTile( num, x, y );
            break;
        case 2:
            changeOneTile( num, x, y );
            changeOneTile( num, x + 1, y );
            changeOneTile( num, x + 1, y + 1 );
            changeOneTile( num, x, y + 1 );
            break;
        case 3:
            for ( int i = 0; i < 3 * 3; i++ )
            {
                changeOneTile( num, DX[i] + x, DY[i] + y );
            }
            break;
        case 4:
            for ( int i = 0; i < 4 * 4; i++ )
            {
                changeOneTile( num, DX[i] + x, DY[i] + y );
            }
            break;
        case 5:
            for ( int i = 0; i < 5 * 5; i++ )
            {
                changeOneTile( num, DX[i] + x, DY[i] + y );
            }
            break;
        default:
        }
    }

    private void changeOneTile( int num, int x, int y )
    {
        int pnum;
        int tnum;
        boolean bamm = false;
        if ( x <= 0 || y <= 0 || x >= 101 || y >= 101 ) { return; } //if
        //
        tnum = board.getTile( x, y );
        pnum = tnum / 100;
        //
        switch ( num ) {
        //break;
        case -1:
            tnum = tnum - 1;
            break;
        case 0:
            tnum = tnum + 1;
            break;
        case 1:
            ;
            if ( tilenum < 0 )
            {
                ;
                if ( tilenum == -4 )
                {
                    handleWall( x, y ); //WALLS!
                    return; //---so that changes persist.
                } else if ( tilenum == ADOOR )
                {
                    tnum = makeDoor( board.getTile( x, y ) );
                } else if ( tilenum == ASIGN )
                {
                    tnum = makeSign( board.getTile( x, y ) );
                } else if ( tilenum == AWALL )
                {
                    handleWall( x, y ); //WALLS!
                    return; //---so that changes persist.
                } else
                {
                    tnum = toolTile[-1 * tilenum]; //for the toolBar selection.
                    ;
                } //if
            } //if
            else
            {
                tnum = tilenum;
            } //for special tiles from MenuBar
            if ( tnum == 70 )
            {
                handleGarden( x, y ); //GARDEN!
                return;
            } else if ( typeOf( tnum ) == OCEAN || typeOf( tnum ) == FOREST || typeOf( tnum ) == ROCK || tnum == 18
                    || tnum == 19 || /* checkered tiles. */
                    -1 * tilenum == getToolTileOf( Messages.getString("EditorHelp.big.tree", "rpg.editor") ) ) //$NON-NLS-1$
            {
                tnum = patternTile( x, y, tnum );
            } else if ( randomized ) tnum = tileRandomize( tnum );

            //GoTo BAMM;
            bamm = true;
            break;
        case 2:
            ;
            tnum = tnum + 1; //increment tilenum, but loops after +10
            if ( tilenum == ADOOR || tilenum == ASIGN || tilenum == AWALL )
            {
                //do nothin'
            } else if ( tilenum < 0 )
            {
                if ( tnum > toolTile[-1 * tilenum] + 10 )
                {
                    tnum = toolTile[-1 * tilenum];
                } //if
            } else
            {
                if ( tnum > ( tilenum + 10 ) )
                {
                    tnum = tilenum;
                } //if
            } //if
            ;
        } //switch
        //------------>Next, we handle crossing over b/t bitmaps.
        if ( !bamm )
        {
            if ( ( tnum - pnum * 100 ) > maxTiles( pnum ) )
            {
                if ( pnum == ISLAND )
                {
                    pnum = CASTLE;
                    tnum = 0;
                } else
                {
                    tnum = ( pnum + 1 ) * 100;
                } //if
            } else if ( tnum < 0 )
            {
                pnum = ISLAND;
                tnum = maxTiles( ISLAND ) + pnum * 100;

            } else if ( tnum / 100 < pnum )
            {
                pnum = tnum / 100;
                tnum = maxTiles( pnum ) + pnum * 100;
            } //if
        } //if (! bamm)
        //
        //BAMM:;
        board.setTile( x, y, tnum );
        smoothTile( x, y, true );
    } 

    //patterns tiles such as ocean, big tree, trees, cave walls...
    public static int patternTile( int x, int y, int tnum )
    {
        int hun = tnum - ( tnum % 100 );
        if ( tnum == 18 || tnum == 19 )
        {
            if ( ( x + y ) % 2 == 1 )
                return 18;
            else
                return 19;
        }
        if ( ( y % 2 ) == 1 )
        {
            if ( ( x % 2 ) == 1 )
                return hun + 10;
            else
                return hun + 11;
        } else
        {
            if ( ( x % 2 ) == 1 )
                return hun + 20;
            else
                return hun + 21;
        }
    }

    //Makes a door tile depending on given tilenum
    private static int makeDoor( int t )
    {
        int ret = 0;
        if ( t < 100 )
            ret = 0;
        else if ( t < 500 )
            ret = 100;
        else
            ret = 500;
        switch ( t % 100 ) {
        case 33:
            ret += 38;
            break;
        case 32:
            ret += 40;
            break;
        case 34:
            ret += 48 + ( ( int ) ( 2 * Math.random() ) ) * 2;
            break;
        //mystic...
        case 52:
            ret += 50;
            break;
        case 62:
            ret += 60;
            break;
        case 53:
            ret += 58;
            break;
        case 63:
            ret += 68;
            break;
        default:
            ret += 36;
        }
        return ret;
    } //function

    //Makes a sign tile depending on given tilenum
    private static int makeSign( int t )
    {
        int ret;
        ret = 100 * ( t / 100 ) + 9;
        return ret;
    }

    //Randomizes a tile
    public static int tileRandomize( int t )
    {
        int ret = t;
        int rand;
        if ( t == 1 || t == 560 )
        { //castle or evil.
            rand = ( int ) ( Math.random() * 14 ) + 1;
            if ( rand < 4 )
            {
                ret = t - ( t % 10 ) + rand - ( ( t > 500 ) ? 1 : 0 );
            } else
            {
                ret = t;
            } //if
            ;
        } else if ( ( t % 100 ) == 1 )
        {
            rand = ( int ) ( Math.random() * ( 9d / TILES_RANDOM ) ) + 1;
            if ( rand < 9 )
            {
                ret = t - ( t % 100 ) + rand;
            } else
            {
                ret = t - ( t % 100 ) + 1;
            } //if
            ;
        } else if ( typeOf( t ) == FOREST )
        {
            rand = ( int ) ( Math.random() * 3 );
            switch ( rand ) {
            case 0:
                ret = ( t - ( t % 100 ) ) + 10;
            case 1:
                ret = ( t - ( t % 100 ) ) + 11;
            case 2:
                ret = ( t - ( t % 100 ) ) + 20;
            case 3:
                ret = ( t - ( t % 100 ) ) + 21;
            }

        } //if
        return ret;
    }

    //Handles the creation of garden tiles...
    private void handleGarden( int x, int y )
    {
        boolean left = false;
        boolean right = false;
        boolean up = false;
        boolean down = false;
        int tnum = 0;
        if ( typeOf( board.getTile( x + 1, y ) ) == GARDEN ) right = true;
        if ( typeOf( board.getTile( x - 1, y ) ) == GARDEN ) left = true;
        if ( typeOf( board.getTile( x, y + 1 ) ) == GARDEN ) down = true;
        if ( typeOf( board.getTile( x, y - 1 ) ) == GARDEN ) up = true;
        //
        if ( right && left )
        {
            if ( up && down ) tnum = 71;
            if ( down ) tnum = 72;
            if ( up ) tnum = 83;

        } else if ( up && down )
        {
            if ( left ) tnum = 76;
            if ( right ) tnum = 86;

        } else if ( up )
        {
            if ( right ) tnum = 84;
            if ( left ) tnum = 85;

        } else if ( down )
        {
            if ( right ) tnum = 74;
            if ( left ) tnum = 75;
            ;
        } else
        {
            tnum = 70;
        } //if
        board.setTile( x, y, tnum );
    }

    //Handles the creation of walls....
    private void handleWall( int x, int y )
    {
        int tnum;
        //<30, treate as castle floor.
        //>=70 treat as grass floor...
        //c = Castle, g = Grass, b = blank, w = wall...
        char left;
        char right;
        char bot;
        char top;
        left = assignWallLetter( x - 1, y );
        right = assignWallLetter( x + 1, y );
        bot = assignWallLetter( x, y + 1 );
        top = assignWallLetter( x, y - 1 );
        //default:
        tnum = 31;
        //
        if ( ( left == 'w' ) && ( right == 'w' ) )
        {
            tnum = tileBetweenWater( tnum ); 
        }
        else if ( left == 'c' )
        {
            tnum = tileLeftCastle( tnum, right, bot );
        } 
        else if ( ( left == 'g' ) )
        {
            tnum = tileLeftGrass( tnum, right, bot, top );
        } 
        else if ( ( left == 'w' ) )
        {
            tnum = tileLeftWater( tnum, right, bot, top );
        } 
        else if ( ( left == 'b' ) )
        {
            tnum = tileLeftBlank( tnum, right, bot, top );
        } //if
        int temp = board.getTile( x, y );
        if ( temp < 500 )
            board.setTile( x, y, tnum );
        else
            board.setTile( x, y, tnum - 31 + 531 );
    }

    /**
     * @param tnum
     * @return
     */
    private int tileBetweenWater( int tnum )
    {
        if ( randomized )
        {
            int rand = ( int ) ( Math.random() * 15 );
            //break;
            if ( rand > 10 )
                tnum = 30;
            else if ( rand == 2 )
                tnum = 52;
            else
                tnum = 31;
        }
        return tnum;
    }

    /**
     * @param tnum
     * @param right
     * @param bottom
     * @return
     */
    private int tileLeftCastle( int tnum, char right, char bottom )
    {
        if ( right == 'g' )
            tnum = 32;
        else if ( ( right == 'w' ) && ( bottom == 'w' ) )
            tnum = 44;
        else if ( ( right == 'c' ) )
            tnum = 34;
        else if ( ( right == 'b' ) ) tnum = 53;
        return tnum;
    }

    /**
     * @param tnum
     * @param right
     * @param bottom
     * @param top
     * @return
     */
    private int tileLeftGrass( int tnum, char right, char bottom, char top )
    {
        if ( ( right == 'c' ) )
            tnum = 33;
        else if ( ( right == 'g' ) )
            tnum = 35;
        else if ( ( right == 'w' ) && ( bottom == 'w' ) )
            tnum = 43;
        else if ( ( right == 'w' ) && ( top == 'w' ) && ( bottom != 'w' ) ) tnum = 45;
        return tnum;
    }

    /**
     * @param tnum
     * @param right
     * @param bottom
     * @param top
     * @return
     */
    private int tileLeftBlank( int tnum, char right, char bottom, char top )
    {
        if ( ( right == 'c' ) )
            tnum = 54;
        else if ( ( right == 'w' ) && ( bottom == 'w' ) )
            tnum = 56;
        else if ( ( right == 'w' ) && ( top == 'w' ) && ( bottom != 'w' ) ) tnum = 57;
        return tnum;
    }

    /**
     * @param tnum
     * @param right
     * @param bottom
     * @param top
     * @return
     */
    private int tileLeftWater( int tnum, char right, char bottom, char top )
    {
        if ( ( right == 'b' ) && ( top == 'w' ) && ( bottom != 'w' ) )
            tnum = 58;
        else if ( ( right == 'g' ) && ( bottom == 'w' ) )
            tnum = 42;
        else if ( ( right == 'w' ) && ( bottom == 'w' ) )
            tnum = 44;
        else if ( ( right == 'b' ) && ( bottom == 'w' ) )
            tnum = 55;
        else if ( ( right == 'g' ) && ( top == 'w' ) && ( bottom != 'w' ) )
            tnum = 46;
        else if ( ( bottom == 'w' ) ) tnum = 44;
        return tnum;
    }

    private char assignWallLetter( int x, int y )
    {
        char ret;
        int tile;
        switch ( ( tile = board.getTile( x, y ) ) ) {
        //break;
        case 0:
            ret = 'b'; //blank;
            break;
        case 47:
            ret = 'c'; //castle;
            break;

        default:
            if ( tile < 30 || ( tile >= 560 && tile < 563 ) )
                ret = 'c';
            else if ( ( tile >= 70 && tile < 100 ) || ( tile > 500 && tile < 530 ) )
                ret = 'g';
            else if ( tile >= 59 && tile < 100 )
                ret = 'c';
            else
                ret = 'w'; //wall;
        } //switch
        return ret;
    }

    /**
     * Handles smooth transitions b/t types of certain tiles: swamp -> grass
     * cavewall -> caveground
     */
    public void smoothTile( int x, int y, boolean recurse )
    {
        int tnum;
        boolean[] grid = new boolean[9];
        int ref;
        ; //<--the first tile in the sequence of "smooth tiles"
        int tileType;
        Arrays.fill( grid, false );
        //
        if ( recurse )
        {
            if ( board.getTile( x + 1, y ) != 0 ) smoothTile( x + 1, y, false );
            if ( board.getTile( x - 1, y ) != 0 ) smoothTile( x - 1, y, false );
            if ( board.getTile( x, y + 1 ) != 0 ) smoothTile( x, y + 1, false );
            if ( board.getTile( x, y - 1 ) != 0 ) smoothTile( x, y - 1, false );
            if ( board.getTile( x + 1, y + 1 ) != 0 ) smoothTile( x + 1, y + 1, false );
            if ( board.getTile( x + 1, y - 1 ) != 0 ) smoothTile( x + 1, y - 1, false );
            if ( board.getTile( x - 1, y + 1 ) != 0 ) smoothTile( x - 1, y + 1, false );
            if ( board.getTile( x - 1, y - 1 ) != 0 ) smoothTile( x - 1, y - 1, false );
        } //if
        tnum = board.getTile( x, y );
        tileType = typeOf( tnum );
        //get reference number; ie. first number in bitmap for "smooth" tiles.
        switch ( tileType ) {
        //break;
        case GRASS:
            ref = 132;
            break;
        case FOREST:
            ref = 112;
            break;
        case SWAMP:
            ref = SWAMP * 100 + 12;
            break;
        case OCEAN:
            ref = SWAMP * 100 + 32;
            break;
        case BEACH:
            ref = BEACH * 100 + 12;
            break;
        case ROCK:
            ref = CAVES * 100 + 12;
            break;
        case CAVES:
            ref = CAVES * 100 + 32;
            break;
        case DESERT:
            ref = DESERT * 100 + 12;
            break;
        case GARDEN:
            ref = 70; //<--this one is very different....;
            break;
        case WOOD_FLOOR:
            ref = FOREST * 100 + 52;
            break;
        default:
            return; //<--we don't need to change anything.;
        } //switch
        //
        if ( otherTile( tileType, board.getTile( x - 1, y - 1 ) ) ) grid[1] = true;
        if ( otherTile( tileType, board.getTile( x, y - 1 ) ) ) grid[2] = true;
        if ( otherTile( tileType, board.getTile( x + 1, y - 1 ) ) ) grid[3] = true;
        if ( otherTile( tileType, board.getTile( x + 1, y ) ) ) grid[4] = true;
        if ( otherTile( tileType, board.getTile( x + 1, y + 1 ) ) ) grid[5] = true;
        if ( otherTile( tileType, board.getTile( x, y + 1 ) ) ) grid[6] = true;
        if ( otherTile( tileType, board.getTile( x - 1, y + 1 ) ) ) grid[7] = true;
        if ( otherTile( tileType, board.getTile( x - 1, y ) ) ) grid[8] = true;
        //
        if ( grid[8] && grid[6] && grid[4] )
        {
            tnum = ref + 6;
        } else if ( grid[8] && grid[6] && grid[2] )
        {
            tnum = ref + 7;
        } else if ( grid[2] && grid[4] && grid[6] )
        {
            tnum = ref + 16;
        } else if ( grid[8] && grid[2] && grid[4] )
        {
            tnum = ref + 17;
        } else if ( grid[8] && grid[2] )
        {
            tnum = ref + 13;
        } else if ( grid[8] && grid[6] )
        {
            tnum = ref + 3;
        } else if ( grid[4] && grid[6] )
        {
            tnum = ref + 2;
        } else if ( grid[2] && grid[4] )
        {
            tnum = ref + 12;
        } else if ( grid[6] )
        {
            tnum = ref + 10;
        } else if ( grid[8] )
        {
            tnum = ref + 11;
        } else if ( grid[4] )
        {
            tnum = ref + 1;
        } else if ( grid[2] )
        {
            tnum = ref;
        } else if ( grid[5] )
        {
            tnum = ref + 4;
        } else if ( grid[3] )
        {
            tnum = ref + 14;
        } else if ( grid[1] )
        {
            tnum = ref + 15;
        } else if ( grid[7] )
        {
            tnum = ref + 5;
        } //if
        //
        //tile(x, y) = tnum ; //<--sets the result.
        board.setTile( x, y, tnum );
    }

    /**
     * Tells whether "other" is the "opposite" of the tileType given. this is to
     * simplify the process of transition tiles.
     */
    private static boolean otherTile( int tileTyp, int other )
    {
        boolean ret = false;
        switch ( tileTyp ) {
        //break;
        case FOREST:
            ;
            ret = !( typeOf( other ) == FOREST );
            break;
        case GRASS:
            ;
            ret = ( typeOf( other ) == BEACH || typeOf( other ) == WATER );
            break;
        case BEACH:
            ;
            ret = ( typeOf( other ) == OCEAN || typeOf( other ) == WATER );
            break;
        case CAVES:
            ;
            ret = ( typeOf( other ) == GRASS );
            break;
        case ROCK:
            ;
            ret = ( typeOf( other ) == CAVES );
            break;
        case SWAMP:
            ;
            ret = ( typeOf( other ) == GRASS );
            break;
        case OCEAN:
            ;
            ret = ( typeOf( other ) == SWAMP );
            break;
        case DESERT:
            ;
            ret = ( typeOf( other ) == GRASS );
            //Case GARDEN:
            //''ret = (Type_Of(other) = GRASS)
            break;
        case WOOD_FLOOR:
            ;
            ret = !( typeOf( other ) == WOOD_FLOOR );
            break;
        default:
            ; //IGNORE;
        } //switch
        return ret;
    }

    /**
     * tells us the type of some tile as a constant. i.e:
     * BEACH,ROCK,GRASS,FOREST,SWAMP,GARDEN,CAVES,ISLAND,WATER,OCEAN
     */
    private static int typeOf( int tyle )
    {
        int ret = 0;
        if ( tyle > 100 && tyle < 110 )
        {
            ret = GRASS;
        } else if ( tyle > 200 && tyle < 210 )
        {
            ret = BEACH;
        } else if ( tyle == 210 || tyle == 211 || tyle == 220 || tyle == 221 || ( tyle >= 330 && tyle <= 349 ) )
        {
            ret = OCEAN;
        } else if ( tyle == 130 || tyle == 131 || tyle == 140 || tyle == 141 )
        {
            ret = WATER;
        } else if ( tyle > 109 && tyle < 130 )
        {
            ret = FOREST;
        } else if ( tyle >= 150 && tyle < 170 || ( tyle > 170 && tyle < 190 && ( tyle % 10 > 3 ) ) )
        {
            ret = WOOD_FLOOR;

        } else if ( ( tyle >= 70 && tyle <= 75 ) || ( tyle >= 80 && tyle <= 85 ) )
        {
            ret = GARDEN;
        } else if ( tyle > 500 && tyle < 530 )
        {
            ret = DESERT;
        } else if ( tyle > 409 && tyle < 430 )
        {
            ret = ROCK;
        } else if ( tyle > 400 && tyle < 410 )
        {
            ret = CAVES;
        } else if ( tyle > 0 && tyle < 24 )
        {
            ret = CASTLE;
        } else if ( tyle > 300 && tyle < 350 )
        {
            ret = SWAMP;
        } else if ( tyle > 600 && tyle < 660 )
        {
            ret = ISLAND;
        } //if
        return ret;
    }

}

