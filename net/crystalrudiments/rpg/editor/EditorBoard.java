/*
 * Revisions:
 *
 * 6/17/03 - Version 1.0
 */
package net.crystalrudiments.rpg.editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import net.crystalrudiments.rpg.Board;
import net.crystalrudiments.rpg.IOHelper;
import net.crystalrudiments.rpg.Messages;
import net.crystalrudiments.rpg.MoveException;
import net.crystalrudiments.rpg.RPGConstants;
import net.crystalrudiments.rpg.land.LandMaker;

/**
 * Represents the map (board) in the Editor. Contains all Persons on the
 * board, all map tiles, objects, and sight information about the board.
 * <P>
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 */
public class EditorBoard extends Board implements RPGConstants, IEditorBoard
{

    static final boolean bDEBUG = true;

    private static IBoardMaker boardMaker = null; //used by makeNewMap

    public EditorBoard()
    {
        this( FIRST_MAP );
    }

    public EditorBoard( String code )
    {
        this( code, null, null );
    }

    public EditorBoard( String code, String pfile, String ofile )
    {
        super( code, pfile, ofile );
    }

    /** Saves only quadrants that have been changed and people and objects. */
    public void save()
    {
        //save dirty quadrants.
        super.save( false );
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
        String nfcode = null;
        String code = super.getCode( 0 );

        if ( oldQuad == newQuad ) return;
        try
        {
            super.moveMap( oldQuad, newQuad );
        } catch ( MoveException me )
        {
            nfcode = me.getCode();
        }

        Object selection = null;
        // Make a new file if not exists:
        while ( nfcode != null )
        {
            selection = makeNewMap( nfcode );
            if ( selection == null )
            {
                try
                {
                    super.moveMap( newQuad, oldQuad );
                } //move back.
                catch ( MoveException me )
                {
                    nfcode = me.getCode();
                }
                return;
            }
            nfcode = load( code );
        }
    }

    /**
     * Shows a dialog requesting a type of map, then it creates that map with
     * the given code. Saves it as maps/map <code>.txt.  Uses LandMaker.
     * If user chooses Cancel, returns null.
     *
     * @return null if user chose Cancel, selection otherwise.
     * @see LandMaker
     */
    public static Object makeNewMap( String code )
    {
        String message = code + Messages.getString("EditorBoard.no.map", "rpg.editor"); //$NON-NLS-1$
        //Object[] possibleValues =
        //{ "Castle", "Forest", "Ocean", "Desert", "Swamp", "Island", "Cave", "Beach", "Field", "Evil" };
        final Object[] possibleValues = Messages.getArray("BoardMaker.", 1, 11, "rpg.editor");
        
        Object selectedValue = javax.swing.JOptionPane.showInputDialog( null, message, Messages.getString("EditorBoard.new.map", "rpg.editor"), //$NON-NLS-1$
                javax.swing.JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0] );
        if ( boardMaker == null ) boardMaker = new BoardMaker();
        if ( selectedValue != null )
        {
            boardMaker.makeNewMap( code, ( ( String ) selectedValue ).toLowerCase() );
        }
        return selectedValue;
    }

    //---------------------------ACCESSORS AND MODIFIERS::
    //public void setFileName(String fname) { fileName = fname;}
    //public String getFileName() { return fileName; }

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
            System.err.println( Messages.getString("EditorBoard.error.could.not.find", "rpg.editor") + str ); //$NON-NLS-1$
            return -1;
        }
        return n;
    }

    /** sorts a file. */
    protected static void sortFile( File f )
    {
        PrintWriter pw = null;
        BufferedReader br = null;
        SortedSet sset = new TreeSet();
        String title;
        br = IOHelper.open( f.getName(), br );
        if ( br == null )
            return;
        else
            title = IOHelper.readLine( br ); //reads in first line.
        String line = ""; //$NON-NLS-1$
        do
        {
            line = IOHelper.readLine( br );
            if ( line != null ) sset.add( line );
        } while ( line != null );
        IOHelper.close( br );

        pw = IOHelper.open( f.getName(), pw );
        if ( pw == null )
            return;
        else
            pw.println( title );
        Iterator iter = sset.iterator();
        do
        {
            line = ( String ) iter.next();
            pw.println( line );
        } while ( iter.hasNext() );

        IOHelper.close( pw );
    }

    /**
     * Use EditorBoard -sort to sort object and people files.
     */
    public static void main( String[] args )
    {

        if ( args.length == 1 && args[0].equals( "-sort" ) ) //$NON-NLS-1$
        {
            sortFile( new File( OBJECTS_FILE ) );
            sortFile( new File( PEOPLE_FILE ) );
            return;
        }
    }
}
