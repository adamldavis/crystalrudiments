package net.crystalrudiments.time;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.0, July 6, 2004
 */
public class TimeManager {

    CalendarWeek calendarWeek = null;

    TaskList taskList = null;

    private static final long MILLIS_IN_DAY = 1000 * 60 * 60 * 24;

    public TimeManager() {
        this(null, null);
    }

    public TimeManager(CalendarWeek calendarWeek, TaskList tlist) {
        setCalendarWeek(calendarWeek);
        setTaskList(tlist);
    }

    public void setCalendarWeek(CalendarWeek calendarWeek) {
        this.calendarWeek = calendarWeek;
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    public CalendarWeek getCalendarWeek() {
        return calendarWeek;
    }

    public void manage() {
        Calendar cal = new GregorianCalendar();
        Date date = new Date(); //today
        while (true) {
            boolean okay = false;
            long time = cal.getTime().getTime(); //millis
            time += MILLIS_IN_DAY;
            date = new Date(time);
            cal.setTime(date);
            for (Iterator iter = taskList.iterator(); iter.hasNext();) {
                Task tsk = (Task) iter.next();
                if (tsk.getDueDate().compareTo(date) > 0) okay = true;
            }
            if (!okay) break;
        }
    }

    public static void main(String[] args) {
        TimeManager tm = new TimeManager();
        tm.manage();
    }

}//TimeManager
