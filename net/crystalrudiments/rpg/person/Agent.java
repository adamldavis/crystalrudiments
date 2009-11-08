/*
 * Revisions:
 *
 * 6/17/03 - Version 1.0
 */
package net.crystalrudiments.rpg.person;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import net.crystalrudiments.rpg.IOHelper;
import net.crystalrudiments.rpg.RPGConstants;

/**
 * Represents a character in the game and contains all of its behavior
 * and speech.
 * 
 * @author <A HREF="mailto:gte459u@mail.gatech.edu">Adam L. Davis </A>
 */
public class Agent extends Person implements RPGConstants
{

    private static final boolean bDEBUG = true;

    private int emotion = 0;

    private List flags = null;

    private Dialog dialog = null;
    
    private IOHelper ioh = new IOHelper();

    public Agent()
    {
        this( "default", 0, 0, 0 );
    }

    public Agent( String name )
    {
        this( name, 0, 0, 0 );
    }

    /** Loads the given file. */
    public Agent( String name, int x, int y, int iImage )
    {
        this.name = name;
        fileName = PEOPLE_DIR + System.getProperty( "file.separator" ) + name + ".char";
        posx = x;
        posy = y;
        imageNum = iImage;
        flags = new Vector();
        dialog = new Dialog( this );
        load();
    }

    public int getFlagsSize()
    {
        return flags.size();
    }

    /**
     * This simply print out the file in the same order it was read in.
     */
    public void saveState()
    {
        PrintWriter pw = null;
        pw = IOHelper.open( fileName, pw );
        if ( pw == null ) return;
        //save
        for ( int i = 0; i < flags.size(); i++ )
        {
            pw.println( ( String ) flags.get( i ) );
        }
        for ( int i = 0; i < dialog.size(); i++ )
        {
            pw.println( ( String ) dialog.get( i ) );
        }
        IOHelper.close( pw );
    }

    /**
     * Loads the char file and puts it in two Vectors: flags and dialog. Dialog
     * starts when the first "SAY" is seen.
     */
    public void load()
    {
        BufferedReader br = null;
        br = IOHelper.open( fileName, br );
        if ( null == br ) return;
        //load
        StringTokenizer strtok = null;
        String sLine = "";
        boolean bDialog = false;
        while ( sLine != null )
        {
            sLine = IOHelper.readLine( br );
            if ( sLine == null ) break;
            if ( sLine.startsWith( "SAY" ) ) bDialog = true;
            if ( bDialog )
            {
                dialog.add( sLine );
            } else
            {
                flags.add( sLine );

                if ( sLine.startsWith( "emotion = " ) )
                {
                    setEmotion( sLine.substring( 10 ).toLowerCase() );
                }
                if ( sLine.startsWith( "dialog position = " ) )
                {
                    dialog.setPosition( ( sLine.toLowerCase() ).substring( 18 ) );
                }
                if ( sLine.startsWith( "position = " ) )
                {
                    String pos = sLine.substring( 11 );
                    int comma = pos.indexOf( ',' );
                    posx = Integer.parseInt( pos.substring( 0, comma ).trim() );
                    posy = Integer.parseInt( pos.substring( comma + 1 ).trim() );
                }
            }
        }
        IOHelper.close( br );
    }

    public void changeX( int dx )
    {
        final String POS = "position = ";
        super.changeX( dx );
        String flag = ( String ) findFlag( POS );
        if ( flag.equals( "" ) )
            flags.add( POS + posx + ", " + posy );
        else
            flags.set( flags.indexOf( flag ), ( POS + posx + ", " + posy ) );
    }

    public void changeY( int dy )
    {
        final String POS = "position = ";
        super.changeY( dy );
        String flag = ( String ) findFlag( POS );
        if ( flag.equals( "" ) )
            flags.add( POS + posx + ", " + posy );
        else
            flags.set( flags.indexOf( flag ), POS + posx + ", " + posy );
    }

    public boolean hasEmotion( int emotionConstant )
    {
        return ( ( emotion & emotionConstant ) > 0 );
    }

    /**
     * Takes in a list of emotions, for example (happy, sad, tired, mad, ...)
     */
    public void setEmotion( String sLine )
    {
        emotion = 0;
        if ( sLine.indexOf( "happy" ) >= 0 )
        {
            emotion |= HAPPY;
        }
        if ( sLine.indexOf( "sad" ) >= 0 )
        {
            emotion |= SAD;
        }
        if ( sLine.indexOf( "tired" ) >= 0 )
        {
            emotion |= TIRED;
        }
        if ( sLine.indexOf( "mad" ) >= 0 )
        {
            emotion |= MAD;
        }
        if ( sLine.indexOf( "depressed" ) >= 0 )
        {
            emotion |= DEPRESSED;
        }
        if ( sLine.indexOf( "sick" ) >= 0 )
        {
            emotion |= SICK;
        }
        if ( sLine.indexOf( "afraid" ) >= 0 )
        {
            emotion |= AFRAID;
        }
        //replace flag
        setFlag( "emotion", sLine );
    }

    public void setFlag( String flag, String newValue )
    {
        String oldFlag = findFlag( flag );
        String newFlag = flag + " = " + newValue;
        if ( bDEBUG ) System.out.println( "oldFlag was " + oldFlag + " ; now is " + newFlag );
        if ( oldFlag.equals( "" ) )
        {
            flags.add( newFlag );
        } else
            flags.set( flags.indexOf( oldFlag ), newFlag );
    }

    public String findFlag( String flag )
    {
        String sLine = null;
        //
        for ( int i = 0; i < flags.size(); i++ )
        {
            sLine = ( String ) flags.get( i );
            if ( sLine.charAt( 0 ) == '#' )
            {
                //skip
                continue;
            }
            if ( sLine.startsWith( flag ) ) { return sLine; }
        }
        return "";
    }

    public String talk()
    {
        return dialog.talk();
    }

    public boolean makeReply( char choice )
    {
        return dialog.makeReply( choice );
    }

    public List getReplies()
    {
        return dialog.getReplies();
    }

    /**
     * Makes a conversation with given agent.
     */
    public static void main( String[] args )
    {
        Agent a = null;
        List replies = null;
        String input = "";
        IOHelper ioh = new IOHelper();
        if ( args.length == 0 )
            a = new Agent( "default" );
        else
            a = new Agent( args[0] );

        while ( !input.equals( "exit" ) )
        {
            //
            if ( replies != null )
            {
                char choice = '0';
                while ( choice == '0' )
                {
                    System.out.println( replies.toString() );
                    System.out.print( ">" );
                    input = ioh.getInputLine();
                    if ( input.equals( "exit" ) ) break;
                    try
                    {
                        choice = input.charAt( 0 );
                    } catch ( Exception e )
                    {
                        choice = '0';
                    }
                    if ( !a.makeReply( choice ) ) choice = '0';
                }
            }
            System.out.println( a.talk() );
            replies = a.getReplies();
            if ( replies == null ) break;
        }
        System.out.println( "[exiting .........................]" );
    }
}
