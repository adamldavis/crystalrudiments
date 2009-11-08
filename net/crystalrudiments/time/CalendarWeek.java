package net.crystalrudiments.time;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Represents the user's "week calendar".
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.1, March 17, 2004
 */
public class CalendarWeek implements MeetingConstants {

    public static final boolean bDEBUG = true;

    List meetings = null;

    File file = null;

    int lineNum = 0;

    private int numHours = 16;

    private int firstHour = 6;

    private int lastHour = 6 + 16;

    public CalendarWeek() {
        meetings = new LinkedList();
        load();
    }

    public CalendarWeek(File file) {
        meetings = new LinkedList();
        this.file = file;
        load(file);
    }

    public void add(Meeting m) {
        meetings.add(m);
    }

    public void remove(Meeting m) {
        meetings.remove(m);
    }

    private void load() {
        file = new File(WEEK_FILE);

        load(file);
    }

    public List getMeetings() {
        return meetings;
    }

    public int getLastHour() {
        return lastHour;
    }

    public int getFirstHour() {
        return firstHour;
    }

    public int getNumHours() {
        return numHours;
    }

    public List getNameList() {
        //get unique names in calendar.
        List nameList = new ArrayList();

        for (Iterator iter = getMeetings().iterator(); iter.hasNext();) {
            Meeting m = (Meeting) iter.next();
            if (!nameList.contains(m.getPlace())) {
                nameList.add(m.getPlace());
            }
        }
        return nameList;
    }

    /**
     * Loads a week file in form on each line: startTime-endTime, event_name,
     * place_name, days_of_week
     * <P>
     * EX: 17:00-17:30, APIE Meeting, College of Computing, Monday Friday <BR>
     * Ignores blank-lines.
     */
    private void load(File file) {
        BufferedReader br = null;

        if (!file.exists()) return; //fnf
        String str;
        try {
            br = new BufferedReader(new FileReader(file));
            for (lineNum = 0; (str = br.readLine()) != null; lineNum++) {
                if (str.equals("")) continue;
                addMeeting(str);
            }
            numHours = lastHour - firstHour;
            br.close();
        } catch (Exception e) {
            if (bDEBUG) e.printStackTrace();
        }
    }

    public void saveAll() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(file));
            for (Iterator iter = meetings.iterator(); iter.hasNext();) {
                Meeting meet = (Meeting) iter.next();
                pw.println(meet.toString());
            }
            pw.close();
        } catch (IOException e) {
            if (bDEBUG) e.printStackTrace();
        }
    }

    public void addMeeting(String str1) {
        StringTokenizer strtok = new StringTokenizer(str1, ",");
        if (strtok.countTokens() != 4) {
            System.err.println("Number of tokens is not correct on line "
                    + lineNum + ".");
        }
        String str = strtok.nextToken();
        int st = Meeting.getTime(str.substring(0, str.indexOf('-')));
        int et = Meeting.getTime(str.substring(str.indexOf('-') + 1));
        String name = strtok.nextToken().trim();
        String place = strtok.nextToken().trim();
        List days = Meeting.getDaysFrom(strtok.nextToken().trim());
        if ((st / 60) < firstHour) firstHour = (st / 60);
        if ((et + 59) / 60 > lastHour) lastHour = (et + 59) / 60;
        for (Iterator iter = days.iterator(); iter.hasNext();) {
            Date date = (Date) iter.next();
            Meeting m = new Meeting(date, st, et, name, place);

            meetings.add(m);
        }
    }

    public void sort() {
        Object[] array = meetings.toArray();
        Arrays.sort(array);
        meetings = new LinkedList();
        for (int i = 0; i < array.length; i++)
            meetings.add(array[i]);
    }

    public String toString() {
        String str = "";
        for (Iterator iter = meetings.iterator(); iter.hasNext();) {
            str += (iter.next()).toString();
            str += "\n";
        }
        return str;
    }

}//CalendarWeek

