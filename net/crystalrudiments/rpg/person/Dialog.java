package net.crystalrudiments.rpg.person;

import java.util.*;

/**
 * Contains speech and behavior for a character.
 * 
 * @author <A HREF="mailto:gte459u@mail.gatech.edu">Adam L. Davis </A>
 */
public class Dialog
{

    private Agent agent = null;

    private List dialog = null;

    private int currentLine = 0; //current line in dialog.

    private String position = "0"; //number of position (eg. 2.1.5)

    private List replies = null;

    private String sSay = ""; //String for the computer to say.

    public Dialog( Agent agent )
    {
        dialog = new Vector();
        replies = new LinkedList();
        position = "0";
        currentLine = 0;
        this.agent = agent;
    }

    public void setPosition( String pos )
    {
        position = pos;
    }

    public int size()
    {
        return dialog.size();
    }

    public void add( Object obj )
    {
        dialog.add( obj );
    }

    public Object get( int i )
    {
        return dialog.get( i );
    }

    public String talk()
    {
        String ret = "";
        if ( sSay.indexOf( "[end]" ) > -1 )
        {
            executeCode( sSay );
            return sSay;
        }
        if ( sSay.length() > 0 )
        {
            ret += executeCode( sSay );
            sSay = removeCode( sSay );
        }
        if ( currentLine >= dialog.size() )
        {
            System.err.println( "Past end of dialog Error." );
            return ret;
        }
        System.out.println( "----->Position: " + position );
        String line = ( String ) dialog.get( currentLine );

        if ( line.startsWith( "SAY" ) )
        {
            if ( !line.substring( 3 ).trim().equals( position ) )
            {
                System.err.println( "Error: position out of whack! line " + ( currentLine + agent.getFlagsSize() ) );
            }
            currentLine++;
        } else
        {
            System.err.println( "File is probably corrupted. No SAY found on line "
                    + ( currentLine + agent.getFlagsSize() ) );
        }
        for ( int i = 0; currentLine < dialog.size(); i++ )
        {
            line = ( String ) dialog.get( currentLine );
            ret += executeCode( line );
            line = removeCode( line );
            if ( !( line.startsWith( "SAY" ) || line.startsWith( "REP" ) ) )
            {
                currentLine++;
                ret += line + "\n";
            } else
            {
                break;
            }
        }
        if ( ret.equals( "" ) )
        {
            currentLine++;
            ret = "empty_string";
        }
        return sSay + "\n" + ret;
    }

    /**
     * REmoves any code from this line (assuming only one pair of brackets per
     * line).
     */
    public String removeCode( String line )
    {
        int i1 = line.indexOf( '[' );
        int i2 = line.indexOf( ']' );
        if ( i1 == -1 || i2 == -1 )
            return line;
        else
        {
            return line.substring( 0, i1 ) + line.substring( i2 + 1 );
        }
    }

    /**
     * Executes code on this line. Assumes only one action per line. Returns any
     * text to be said.
     */
    private String executeCode( String line )
    {
        String ret = "";
        int i1 = line.indexOf( '[' );
        int i2 = line.indexOf( ']' );
        if ( i1 == -1 || i2 == -1 )
            return ret;
        else
        {
            String code = line.substring( i1 + 1, i2 ).trim();
            int p1 = code.indexOf( '(' );
            int p2 = code.indexOf( ')' );

            if ( code.startsWith( "GOTO " ) )
            {
                String go = code.substring( 5 );
                for ( int i = 0; i < dialog.size(); i++ )
                {
                    String str = ( String ) dialog.get( i );
                    if ( str.equals( go ) )
                    {
                        currentLine = i;
                        position = go;
                        return ret;
                    }
                }//i
                System.err.println( "Error: couldn't execute " + code + " on line "
                        + ( currentLine + agent.getFlagsSize() ) );
            } else if ( code.startsWith( "SAY " ) )
            {
                ret = code.substring( 4 );
            } else if ( code.startsWith( "IF " ) )
            {
                String bf = code.substring( p1 + 1, p2 ).trim();
                //boolean formula.
                String flag = bf.substring( 0, bf.indexOf( '=' ) );
                String realFlag = agent.findFlag( flag );
                String value = bf.substring( bf.indexOf( '=' ) + 1 ).trim();
                if ( realFlag.indexOf( value ) > -1 )
                {
                    executeCode( "[" + code.substring( p2 + 1 ).trim() + "]" );
                }
            } else if ( code.startsWith( "giveAmulet" ) )
            {
            } else if ( code.startsWith( "takeMoney(" ) )
            {
                int amount = Integer.parseInt( code.substring( p1 + 1, p2 ) );

            } else if ( code.startsWith( "setFlag(" ) )
            {
                int comma = code.indexOf( ',' );
                String flag = code.substring( p1 + 1, comma ).trim();
                String newValue = code.substring( comma + 1, p2 ).trim();
                agent.setFlag( flag, newValue );
            } else if ( code.startsWith( "setEmotion(" ) )
            {
                String emotion = code.substring( p1 + 1, p2 ).trim();
                agent.setEmotion( emotion );
            } else if ( code.toLowerCase().equals( "end" ) )
            {
                position = "0"; //reset dialog's position to beginning.
                ret = "[end]";
            }
        }
        return ret;
    }

    /**
     * Returns a set of replies for the user to pick from. Each line gets added
     * to the Vector returned, except for lines starting with TAB or blank
     * lines.
     */
    public List getReplies()
    {
        List ret = new Vector();
        if ( currentLine >= dialog.size() )
        {
            System.err.println( "Past end of dialog Error." );
            return null;
        }
        for ( int i = 0; currentLine < dialog.size(); i++ )
        {
            String line = ( String ) dialog.get( currentLine );
            if ( i == 0 && line.startsWith( "REP" ) )
            {
                currentLine++;
                continue;
            }
            if ( !( line.startsWith( "SAY" ) || line.startsWith( "REP" ) ) )
            {
                currentLine++;
                if ( !line.startsWith( "\t" ) && !line.equals( "" ) ) ret.add( line );
            } else
            {
                break;
            }
        }
        if ( ret.isEmpty() ) ret = null;
        replies = ret;
        return ret;
    }

    /**
     * Attempt to make a reply to the Agent. Returns true if it worked, false
     * otherwise.
     */
    public boolean makeReply( char choice )
    {
        int cLine = currentLine;
        //reset the say variale, which is said by the agent next time he/she
        // talks.
        sSay = "";
        if ( replies == null ) return false;
        if ( choice < 'a' || ( int ) ( choice - 'a' ) > replies.size() ) return false;
        //now register reply: two ways:
        //1 - jump to next SAY statement.
        // example: if position=="1" & choice==b, find SAY 1.b.0
        String newPos = position + "." + String.valueOf( choice ) + ".0";
        String[] tooFar = ( posIncrement( position ) );
        for ( int i = cLine; i < dialog.size(); i++ )
        {
            String temp = ( String ) dialog.get( i );
            if ( temp.equals( "SAY " + newPos ) )
            {
                currentLine = i;
                position = newPos;
                return true;
            }
            for ( int j = 0; j < tooFar.length; j++ )
            {
                if ( temp.equals( "SAY " + tooFar[j] ) )
                {
                    //this means that there is no SAY response,
                    //therefore, go on to the next thingy...
                    position = tooFar[j];
                    currentLine = i;
                    break;
                }
            }
            if ( cLine != currentLine ) break;
        }
        //2 - Use TABed agent response.
        // example: go back up and use TABed lines.
        for ( int i = cLine; i > 0; i-- )
        {
            String curr = ( String ) dialog.get( i );
            if ( curr.startsWith( String.valueOf( choice ) + ". " ) )
            {
                boolean seenTab = false;
                for ( int j = i + 1; j < dialog.size(); j++ )
                {
                    curr = ( String ) dialog.get( j );
                    if ( curr.startsWith( "\t" ) )
                    {
                        seenTab = true;
                        sSay += curr.substring( 1 ) + "\n";
                    } else
                    {
                        if ( seenTab ) break;
                    }
                }
                break;
            }
            if ( curr.startsWith( "REP" ) )
            {
                break;
            }
        }
        return true;
    }

    /**
     * Increments the dialog position to the next logical * Say statement.
     * 5.a.6.e.0 --> (5.a.6.e.1, 5.a.7, 6)
     */
    private static String[] posIncrement( String position )
    {
        int index = position.length();
        int endIndex = position.length();
        String[] ret = new String[( position.length() + 3 ) / 4];
        //
        for ( int i = 0; i < ret.length; i++ )
        {
            if ( i == 0 ) index = position.lastIndexOf( ".", index );
            int lastInt = Integer.parseInt( position.substring( index + 1, endIndex ) );
            lastInt++;
            ret[i] = position.substring( 0, index + 1 ) + String.valueOf( lastInt );
            //back down to next level:
            index = position.lastIndexOf( ".", index - 1 );
            endIndex = index;
            index = position.lastIndexOf( ".", index - 1 ); //intentionally
                                                            // twice
        }
        return ret;
    }

    public static void main( String[] args )
    {

        //if (args.length < 1) {
        //   System.out.println
        //("Usage: java jmiedor.Dialog <file_name.char>");
        //}
        //else {
        //    File file = new File(args[0]);

        String pos = "5.a.6.e.0";
        String[] ret = Dialog.posIncrement( pos );

        System.out.println( "should be 5.a.6.e.0 --> (5.a.6.e.1, 5.a.7, 6)\n\n " );

        for ( int i = 0; i < ret.length; i++ )
        {
            System.out.println( "\t" + ret[i] );
        }
    }//main
}
