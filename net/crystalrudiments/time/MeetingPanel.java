package net.crystalrudiments.time;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JPanel;

/**
 * Enables us to view the current calendar week of the user.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis </A>
 * @version 1.1, March 17, 2004
 */
public class MeetingPanel extends JPanel implements MeetingConstants
{

    public static final int TEXT_HEIGHT = 10;

    public static final int LETTER_WIDTH = 3;

    ///public static final Color MEETING_COLOR = Color.cyan;
    static int ownerWidth = 100;

    static int ownerHeight = 100;

    static int numHours = 18;

    static int firstHour = 6;

    Meeting meeting;

    Color myColor = Color.lightGray;

    public static void setOwnerWidth( int w )
    {
        ownerWidth = w;
    }

    public static void setOwnerHeight( int h )
    {
        ownerHeight = h;
    }

    public static void setFirstHour( int h )
    {
        firstHour = h;
    }

    public static void setNumHours( int h )
    {
        numHours = h;
    }

    public int getStartTime()
    {
        return meeting.startTime;
    }

    public int getEndTime()
    {
        return meeting.endTime;
    }

    public Date getDate()
    {
        return meeting.date;
    }

    public String getName()
    {
        return meeting.name;
    }

    public String getPlace()
    {
        return meeting.place;
    }

    public MeetingPanel( Meeting meeting )
    {
        this.meeting = meeting;
        myColor = ( Options.getInstance().calendarColor );
    }

    public MeetingPanel( Date date, int startTime, int endTime, String name, String place )
    {
        super();
        myColor = ( Options.getInstance().calendarColor );
        meeting = new Meeting();
        meeting.setMeeting( date, startTime, endTime, name, place );
    }

    public MeetingPanel()
    {
        this( new Date(), 0, 0, null, null );
    }

    public Meeting getMeeting()
    {
        return meeting;
    }

    public void paint( Graphics g )
    {
        if ( g == null ) return;
        this.reshape();
        int w = getWidth();
        int h = getHeight();
        Font font = g.getFont().deriveFont( ( float ) TEXT_HEIGHT );
        g.setFont( font );
        g.setColor( myColor );
        g.fill3DRect( 0, 0, w, h, true );
        if ( myColor.getAlpha() > 150 )
            g.setColor( Color.black );
        else
            g.setColor( Color.white );
        g.drawString( getName(), ( w / 2 ) - getName().length() * LETTER_WIDTH, ( h / 2 ) - TEXT_HEIGHT );
        g.drawString( getPlace(), ( w / 2 ) - getPlace().length() * LETTER_WIDTH, ( h / 2 ) );
        String stime = Meeting.convertToTime( getStartTime() ) + "-" + Meeting.convertToTime( getEndTime() );
        g.drawString( stime, ( w / 2 ) - stime.length() * LETTER_WIDTH, ( h / 2 ) + TEXT_HEIGHT );
    }

    public void reshape()
    {
        Calendar cal = new GregorianCalendar();
        cal.setTime( getDate() );
        int dow = cal.get( Calendar.DAY_OF_WEEK ) - CAL_FIRST_DAY;
        if ( dow < 0 ) dow += 7;
        int x = dow * ownerWidth / 7;
        int y = ( meeting.getStartTime() - firstHour * 60 ) * ownerHeight / ( 60 * numHours );
        int w = ownerWidth / 7;
        int h = ( meeting.getEndTime() - meeting.getStartTime() ) * ownerHeight / ( 60 * numHours );
        Dimension d = new Dimension( w, h );
        Point p = new Point( x, y );
        this.setLocation( p );
        this.setSize( d );
    }

    public String toString()
    {
        return meeting.toString();
    }
}
