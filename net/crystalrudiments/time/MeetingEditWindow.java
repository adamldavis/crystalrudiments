package net.crystalrudiments.time;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Enables us to edit a MeetingPanel.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.1, March 17, 2004
 */
public class MeetingEditWindow extends JFrame implements MeetingConstants {

    MeetingPanel mp = null;

    WeekViewer wv = null;

    static final int WIDTH = 300;

    static final int HEIGHT = 300;

    JButton okButton;

    JButton cancelButton;

    JTextField nameArea;

    JTextField placeArea;

    JTextField dateArea;

    JTextField stArea;

    JTextField etArea;

    public MeetingEditWindow() {
        this(new MeetingPanel(), null);
    }

    public MeetingEditWindow(MeetingPanel mp, WeekViewer wv) {
        super("Meeting Edit Window");
        this.wv = wv;
        setSize(WIDTH, HEIGHT);
        GridLayout grid = new GridLayout(6, 2, 10, 10);
        this.getContentPane().setLayout(grid);
        init();
        this.addWindowListener(wv);
        setMeetingPanel(mp);
    }

    public void init() {
        JLabel label;

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        okButton.addActionListener(wv);
        cancelButton.addActionListener(wv);
        nameArea = new JTextField();
        placeArea = new JTextField();
        dateArea = new JTextField();
        stArea = new JTextField();
        etArea = new JTextField();
        label = new JLabel("Name:");
        this.getContentPane().add(label);
        this.getContentPane().add(nameArea);
        label = new JLabel("Place:");
        this.getContentPane().add(label);
        this.getContentPane().add(placeArea);
        label = new JLabel("Date:");
        this.getContentPane().add(label);
        this.getContentPane().add(dateArea);
        label = new JLabel("Start Time:");
        this.getContentPane().add(label);
        this.getContentPane().add(stArea);
        label = new JLabel("End Time:");
        this.getContentPane().add(label);
        this.getContentPane().add(etArea);
        this.getContentPane().add(okButton);
        this.getContentPane().add(cancelButton);

        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        // Move the window
        this.setLocation(x, y);
    }

    public void setMeetingPanel(MeetingPanel mp) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        this.mp = mp;
        nameArea.setText(mp.getName());
        placeArea.setText(mp.getPlace());
        dateArea.setText("" + format.format(mp.getDate()));
        stArea.setText("" + Meeting.convertToTime(mp.getStartTime()));
        etArea.setText("" + Meeting.convertToTime(mp.getEndTime()));
        this.show();
    }

    public void makeChanges() {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String[] names = DAY_NAMES;
        Calendar cal = new GregorianCalendar();
        cal.setFirstDayOfWeek(cal.MONDAY);
        long time = cal.getTime().getTime(); //millis
        long minute = 1000 * 60;
        int st = Meeting.getTime(stArea.getText());
        int et = Meeting.getTime(etArea.getText());
        String name = nameArea.getText();
        String place = placeArea.getText();
        try {
            date = format.parse(dateArea.getText());
        } catch (ParseException pe) {
            System.err.println("Couldn't parse date: " + dateArea.getText());
        }
        mp.getMeeting().setMeeting(date, st, et, name, place);
    }

}//MeetingEditWindow
