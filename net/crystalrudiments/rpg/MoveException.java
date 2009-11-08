package net.crystalrudiments.rpg;

/**
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis </A>
 */
public class MoveException extends Exception
{

    String code = "";

    public MoveException()
    {
        super( "Can not find Map file; move not acceptable." );
    }

    public MoveException( String code )
    {
        super( "Can not find Map file " + code + "; move not acceptable." );
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

}