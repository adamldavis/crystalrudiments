package net.crystalrudiments.time;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Represents an event on the user's "week calendar".
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis </A>
 * @version 1.1, March 17, 2004
 */
public class Meeting implements Comparable, MeetingConstants
{

    Date date = null; //date representing the Day for this meeting.

    int startTime; //startime in minutes.

    int endTime; //endtime in minutes.

    String name;

    String place;

    public Meeting( Date date, int startTime, int endTime, String name, String place )
    {
        super();
        setMeeting( date, startTime, endTime, name, place );
    }

    public Meeting()
    {
        this( new Date(), 0, 0, "", "" );
    }

    public int getStartTime()
    {
        return startTime;
    }

    public int getEndTime()
    {
        return endTime;
    }

    public Date getDate()
    {
        return date;
    }

    public String getName()
    {
        return name;
    }

    public String getPlace()
    {
        return place;
    }

    public void setPlace( String name )
    {
        this.place = place;
    }

    public void setMeeting( Date startTime, Date endTime, String name, String place )
    {
        Calendar cal = new GregorianCalendar();
        cal.setTime( startTime );
        int smin = cal.get( Calendar.HOUR_OF_DAY ) * 60 + cal.get( Calendar.MINUTE );
        cal.setTime( endTime );
        int emin = cal.get( Calendar.HOUR_OF_DAY ) * 60 + cal.get( Calendar.MINUTE );
        setMeeting( startTime, smin, emin, name, place );
    }

    public void setMeeting( Date date, int startTime, int endTime, String name, String place )
    {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.place = place;
    }

    public static int getTime( String str )
    {
        int min = 0;
        String mer = str.substring( str.length() - 2 ); //last two letters.
        StringTokenizer strtok = new StringTokenizer( str.substring( 0, str.length() - 2 ), ": " );

        if ( strtok.countTokens() != 2 )
        {
            System.err.println( "Error with Time on " + str );
        }
        if ( mer.toLowerCase().equals( "pm" ) ) min += 60 * 12;
        min += 60 * Integer.parseInt( strtok.nextToken() );
        min += Integer.parseInt( strtok.nextToken() );
        return min;
    }

    public static List getDaysFrom( String str )
    {
        StringTokenizer strtok = new StringTokenizer( str, " " );
        List list = new LinkedList();
        Date date = new Date(); //today's date.
        final String[] names = DAY_NAMES;
        while ( strtok.hasMoreTokens() )
        {
            Calendar cal = new GregorianCalendar();
            long time = cal.getTime().getTime(); //millis
            String token = strtok.nextToken();
            int iday = 0;
            for ( int i = 0; i < names.length; i++ )
            {
                if ( names[i].equals( token ) )
                {
                    iday = i;
                    break;
                }
            }//i
            int today = ( cal.get( Calendar.DAY_OF_WEEK ) - CAL_FIRST_DAY );
            if ( today < 0 ) today += 7;
            int diff = iday - today;
            time += diff * MILLIS_IN_DAY; //move that many days
                                          // forward/backward.
            date = new Date( time );
            list.add( date );
        }
        return list;
    }

    public static String convertToTime( int i )
    {
        int hour = ( i / 60 );
        String m = "AM";
        if ( hour > 12 )
        {
            hour -= 12;
            m = "PM";
        }
        return String.valueOf( hour ) + ":" + ( i % 60 ) + m;
    }

    public static String convertToDay( Date date )
    {
        final String[] names = DAY_NAMES;
        Calendar cal = new GregorianCalendar();
        cal.setTime( date );
        int i = cal.get( Calendar.DAY_OF_WEEK ) - CAL_FIRST_DAY;
        if ( i < 0 ) i += 7;
        return names[i];
    }

    /** Returns file formated String of this Meeting. */
    public String toString()
    {
        String str = "";
        str = convertToTime( startTime ) + "-" + convertToTime( endTime ) + ", ";
        if ( name == null )
            str += "n/a, ";
        else
            str += name + ", ";
        if ( place == null )
            str += "n/a, ";
        else
            str += place + ", ";
        str += convertToDay( date );
        return str;
    }

    /**
     * Returns comparison by ascending time order.
     */
    public int compareTo( Object obj )
    {
        if ( obj instanceof Meeting )
        {
            Meeting m2 = ( Meeting ) obj;
            Date d2 = m2.getDate();
            if ( getDate().compareTo( d2 ) < 0 )
            {
                return -1;
            } else if ( getDate().compareTo( d2 ) > 0 )
            {
                return 1;
            } else
            { //same day;
                return getStartTime() - m2.getStartTime();
            }
        }
        return 100;
    }

    public boolean equals( Object obj )
    {
        return ( this.compareTo( obj ) == 0 );
    }

}//Meeting
