package net.crystalrudiments.hem;

import java.io.BufferedReader;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import net.crystalrudiments.io.InOutHelp;

public class Hem implements HemConstants
{

    InOutHelp ioh = null;

    Hashtable dict = null; //vector of Words's.

    Vector answers = null; //vector of Answer's.

    Hashtable templates = null; //hashtable of Template's.

    String[] wordTypes =
    { YES, NO, OTHER, DIRECTION, NOUN, PRONOUN, POSSESIVE, VERB, ADJECTIVE, ADVERB, PREPOSITION, QUESTION, NUMBER };

    int emotion = 0;

    public Hem()
    {
        this( "hem.words", "hem.dat", "hem.nfo" );
    }

    public Hem( String wordsFile, String answerFile, String templateFile )
    {
        ioh = new InOutHelp();
        dict = loadWords( wordsFile );
        answers = loadAnswers( answerFile );
        templates = loadTemplates( templateFile );
    }

    public Hashtable loadWords( String fname )
    {
        BufferedReader br = null;
        String line = null;
        StringTokenizer strtok = null;
        Hashtable hash = new Hashtable();
        Words newWords = null;

        if ( ( br = ioh.openBR( fname ) ) != null )
        {
            while ( true )
            {
                line = InOutHelp.readLine( br );
                if ( line == null ) break;
                if ( line.startsWith( "#" ) )
                {
                    if ( newWords != null ) hash.put( newWords.getType(), newWords );
                    newWords = new Words();
                    newWords.setType( line.substring( 2 ) );
                } else
                {
                    strtok = new StringTokenizer( line, " :" );
                    while ( strtok.hasMoreTokens() )
                    {
                        newWords.addWord( strtok.nextToken() );
                    }
                }//#
            }//while true
            if ( newWords != null ) hash.put( newWords.getType(), newWords );
        }
        return hash;
    }//loadWords

    public Vector loadAnswers( String fname )
    {
        BufferedReader br = null;
        String line = null;
        Vector vec = new Vector();
        Answer newAns = null;
        boolean expectResp = false;

        if ( ( br = ioh.openBR( fname ) ) != null )
        {
            while ( true )
            {
                line = InOutHelp.readLine( br );
                if ( line == null ) break;
                if ( line.startsWith( "#" ) )
                {
                    expectResp = false;
                    if ( newAns != null )
                    {
                        newAns.initUsed();
                        vec.add( newAns );
                    }
                    newAns = new Answer();
                } else if ( line.startsWith( "-" ) )
                {
                    expectResp = true;
                } else
                {
                    if ( expectResp )
                    {
                        newAns.addResponse( line );
                    } else
                    {
                        newAns.addKeyword( line );
                    }
                }//#
            }//while true
            if ( newAns != null )
            {
                newAns.initUsed();
                vec.add( newAns );
            }
        }
        return vec;
    }//loadAnswers

    public Hashtable loadTemplates( String fname )
    {
        BufferedReader br = null;
        String line = null;
        Hashtable hash = new Hashtable();
        Template temp = null;

        if ( ( br = ioh.openBR( fname ) ) != null )
        {
            while ( true )
            {
                line = InOutHelp.readLine( br );
                if ( line == null ) break;
                if ( line.startsWith( "#" ) )
                {
                    if ( temp != null )
                    {
                        temp.initUsed();
                        hash.put( temp.category, temp );
                    }
                    temp = new Template();
                    temp.category = line.substring( 2 );
                } else
                {
                    temp.sentences.add( line );
                }//#
            }//while true
            if ( temp != null )
            {
                temp.initUsed();
                hash.put( temp.category, temp );
            }
        }
        return hash;
    }//loadAnswers

    public String findAnswer( String input )
    {
        String out = null;
        for ( int i = 0; i < answers.size(); i++ )
        {
            out = ( ( Answer ) answers.get( i ) ).match( input );
            if ( out != null ) break;
        }
        return out;
    }

    public String findTemplate( String input )
    {
        String out = "";
        Template temp = null;
        StringTokenizer st = null;
        String token;
        Vector types = new Vector();
        if ( input.equals( "" ) )
        {
            temp = ( Template ) templates.get( SILENT );
            out = temp.getSentence();
        } else
        {
            st = new StringTokenizer( input, " " );
            while ( st.hasMoreTokens() )
            {
                token = st.nextToken();
                for ( int i = 0; i < wordTypes.length; i++ )
                {
                    if ( ( ( Words ) dict.get( wordTypes[i] ) ).contains( token ) )
                    {
                        types.add( wordTypes[i] );
                    }
                }

            }
            if ( types.isEmpty() ) temp = ( Template ) templates.get( NONSENSE );
            if ( types.contains( QUESTION ) )
            {
                if ( types.contains( NUMBER ) )
                {
                    temp = ( Template ) templates.get( MATH );
                } else if ( types.size() > 3 )
                {
                    temp = ( Template ) templates.get( MISUNDERSTOOD );
                } else
                {
                    temp = ( Template ) templates.get( GENERIC );
                }
            } else
            {
                if ( types.contains( YES ) && types.contains( NO ) )
                {
                    temp = ( Template ) templates.get( NONSENSE );
                }
                if ( types.contains( YES ) )
                {
                    temp = ( Template ) templates.get( OKAY );
                }
                if ( types.contains( NO ) )
                {
                    temp = ( Template ) templates.get( NO );
                }
                if ( temp == null ) temp = ( Template ) templates.get( UNKNOWN );
            }
            out = temp.getSentence();
        }
        return out;
    }

    public void start()
    {
        String input = "";
        String output = "";
        StringTokenizer strtok;
        boolean stop = false;
        while ( !stop )
        {
            System.out.print( ">" );
            input = ioh.getInputLine();
            if ( input.equals( "exit" ) ) break;
            //remove all punctuations and lower case it.
            strtok = new StringTokenizer( input.toLowerCase(), ",.!?;[/]\\" );
            input = "";
            while ( strtok.hasMoreTokens() )
            {
                input += strtok.nextToken();
            }
            output = findAnswer( input );
            if ( output == null )
            {
                output = findTemplate( input );
            }

            if ( output == null )
            {
                output = "";
            }
            System.out.println( output );
        }
    }

    public static void main( String[] args )
    {
        //
        Hem h = new Hem();
        h.start();
    }

}//ENDCLASS
