package net.crystalrudiments.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.0, July 6, 2004
 */
public class Task {

    String name;

    int hours;

    Date dueDate;

    public Task() {
        this("");
    }

    public Task(String str) {
        StringTokenizer tok = new StringTokenizer(str, ",");
        DateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        name = tok.nextToken().trim();
        hours = Integer.parseInt(tok.nextToken().trim());
        try {
            dueDate = format.parse(tok.nextToken());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Task(String name, int hours) {
        this(name, hours, new Date());
    }

    public Task(String name, int hours, Date dueDate) {
        this.name = name;
        this.hours = hours;
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public int getHours() {
        return hours;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getTimeLeft() {
        if (hours == 0) {
            return "Completed";
        } else if (hours == -1) { return "Unknown"; }
        return String.valueOf(hours) + " hours";
    }

    public String toString() {
        return name + ", " + getTimeLeft() + ", " + dueDate.toString();
    }

    /**
     * Orders by due-date (ascending), then by hours (descending), and lastly by
     * name (ascending).
     */
    public int compareTo(Object obj) {
        if (obj instanceof Task) {
            Task t2 = (Task) obj;
            int num = getDueDate().compareTo(t2.getDueDate());
            if (num == 0) {
                num = t2.getHours() - getHours();
                if (num == 0) { return getName().compareTo(t2.getName()); }
            }
            return num;
        }
        return 100;
    }

    public boolean equals(Object o) {
        return (this.compareTo(o) == 0);
    }
}