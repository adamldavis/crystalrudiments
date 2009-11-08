package net.crystalrudiments.util;

/**
 * A very simple date object with extremely small memory footprint.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.0, 2005
 */
public class SimpleDate
{

    int year;

    byte month;

    byte day;

    /**
     * Creates a date with the given values.
     * 
     * @param year
     *            Year (any integer)
     * @param month
     *            Month (1-12)
     * @param day
     *            Day (1-31)
     */
    public SimpleDate( int year, int month, int day )
    {
        this.year = year;
        this.month = ( byte ) month;
        this.day = ( byte ) day;
    }

    public String toString()
    {
        String ret;
        ret = String.valueOf( year ) + "-" + String.valueOf( month ) + "-" + String.valueOf( day );
        return ret;
    }
}
