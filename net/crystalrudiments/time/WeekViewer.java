package net.crystalrudiments.time;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Enables us to view the current calendar week of the user.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.1, March 17, 2004
 */
public class WeekViewer extends JPanel implements MeetingConstants,
        ActionListener, MouseListener, WindowListener {

    public static final boolean bDEBUG = true;

    List meetingPanels = null;

    private boolean editMutex = false;

    File file = null;

    CalendarWeek week = null;

    JButton btnAdd = null;

    MeetingEditWindow mew = null;

    //    JViewport viewPort = null;
    JPanel panel = null; //panel that holds MeetingPanels.

    public WeekViewer(File file) {
        this.file = file;
        meetingPanels = new LinkedList();

        load(file);

        reshape();
        addMouseListener(this);
        if (bDEBUG) System.out.println(meetingPanels);
    }

    public WeekViewer(String fileName) {
        this(new File(fileName));
    }

    public WeekViewer() {
        this(new File(WEEK_FILE));
    }

    public void saveAll() {
        if (week != null) {
            week.saveAll();
        } else {
            System.err.println("WeekViewer: Tried to save while week is null.");
        }
    }

    public void load() {
        load(new File(WEEK_FILE));
    }

    public void load(File file) {
        week = new CalendarWeek(file);

        this.clear();
        initPanel(week);

        for (Iterator iter = week.getMeetings().iterator(); iter.hasNext();) {

            MeetingPanel mp = new MeetingPanel((Meeting) iter.next());
            mp.setSize(new Dimension(50, 50));
            meetingPanels.add(mp);
            panel.add(mp);
        }
    }

    private void clear() {
        meetingPanels = new LinkedList();
        this.removeAll();
    }

    private void initPanel(CalendarWeek week) {
        BorderLayout border = new BorderLayout();
        GridLayout grid = new GridLayout(1, DAY_NAMES.length, 10, 10);
        GridLayout hourgrid = new GridLayout(week.getNumHours(), 1, 10, 10);
        JPanel west = new JPanel(hourgrid);
        JPanel north = new JPanel(grid);
        JPanel south = new JPanel();
        this.setLayout(border);
        panel = new JPanel(null);
        //viewPort = new JViewport();
        //viewPort.setView( panel );
        //viewPort.setViewSize(new Dimension(this.getWidth(),
        // this.getHeight()));

        for (int i = 0; i < DAY_NAMES.length; i++) {
            JLabel label = new JLabel(DAY_NAMES[i]);
            label.setHorizontalAlignment(JLabel.CENTER);
            north.add(label);
        }
        for (int i = week.getFirstHour(); i < week.getLastHour(); i++) {
            JLabel label = new JLabel(Meeting.convertToTime(i * 60));
            label.setHorizontalAlignment(JLabel.CENTER);
            west.add(label);
        }
        btnAdd = new JButton("Add");
        btnAdd.addActionListener(this);
        south.add(btnAdd);
        this.add(panel);
        //this.add( north, BorderLayout.NORTH );
        this.add(west, BorderLayout.WEST);
        this.add(south, BorderLayout.SOUTH);
    }

    public void paint(Graphics g) {
        reshape();
        super.paint(g);
    }

    public void reshape() {
        MeetingPanel.setOwnerWidth(panel.getWidth());
        MeetingPanel.setOwnerHeight(panel.getHeight());
        MeetingPanel.setFirstHour(week.getFirstHour());
        MeetingPanel.setNumHours(week.getNumHours());
        for (Iterator iter = meetingPanels.iterator(); iter.hasNext();) {
            MeetingPanel mp = (MeetingPanel) iter.next();
            mp.reshape();
        }
    }

    public void windowClosing(WindowEvent e) {
        editMutex = false;
        if (bDEBUG) System.out.println(e);
        saveAll();
        reshape();
    }

    public void windowActivated(WindowEvent e) {
        ;
    }

    public void windowClosed(WindowEvent e) {
        editMutex = false;
        if (bDEBUG) System.out.println(e);
        reshape();
        saveAll(); //save the changes.
    }

    public void windowDeactivated(WindowEvent e) {
        ;
    }

    public void windowDeiconified(WindowEvent e) {
        ;
    }

    public void windowIconified(WindowEvent e) {
        ;
    }

    public void windowOpened(WindowEvent e) {
        ;
    }

    public void actionPerformed(ActionEvent e) {
        if (editMutex == false && e.getActionCommand().equals("Add")) {
            Meeting m = new Meeting();
            MeetingPanel mp = new MeetingPanel(m);
            if (mew == null) {
                mew = new MeetingEditWindow(mp, this);
            } else {
                mew.setMeetingPanel(mp);
                mew.show();
            }
            editMutex = true;
        } else if (e.getSource() instanceof JButton) {
            String str = e.getActionCommand().toLowerCase();
            if (bDEBUG) System.out.println("MEW action: " + str);
            if (str.startsWith("ok")) {
                mew.makeChanges();
                if (!meetingPanels.contains(mew.mp)) {
                    Meeting m = mew.mp.getMeeting();
                    week.add(m);
                    MeetingPanel mp = new MeetingPanel(m);
                    panel.add(mp);
                    meetingPanels.add(mp);
                }
                saveAll();
                reshape();
            }
            if (mew != null) {
                mew.hide();
                editMutex = false;
                //mew.dispose();
                //mew = null;
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        //Invoked when the mouse has been clicked on a component.
        Component c = this.getComponentAt(e.getX(), e.getY());
        if (c instanceof JPanel)
                c = c.getComponentAt(e.getX() - c.getX(), e.getY() - c.getY());
        int iChoice = 0;
        if (c instanceof MeetingPanel) {
            MeetingPanel mp = (MeetingPanel) c;
            if (bDEBUG) System.out.println(mp);
            if (e.getModifiers() == (InputEvent.BUTTON1_MASK & MouseEvent.MOUSE_CLICKED)) {
                //edit
                if (bDEBUG) System.out.println("edit");
                if (!editMutex) {
                    if (mew == null) {
                        mew = new MeetingEditWindow(mp, this);
                    } else {
                        mew.setMeetingPanel(mp);
                        mew.show();
                    }
                    editMutex = true;
                }
            }
            if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
                //delete/
                if (bDEBUG) System.out.println("delete");
                String message = "Are you sure you want to delete this meeting.";
                String sTitle = "Delete Meeting";
                iChoice = JOptionPane.showConfirmDialog(this, message, sTitle,
                        JOptionPane.YES_NO_OPTION);
                if (iChoice == JOptionPane.YES_OPTION) {
                    panel.remove(mp);
                    meetingPanels.remove(mp);
                    week.remove(mp.getMeeting());
                    repaint();
                    saveAll();
                }
            }
        }//c
    }

    public void mouseEntered(MouseEvent e) {
        //Invoked when the mouse enters a component.
    }

    public void mouseExited(MouseEvent e) {
        //Invoked when the mouse exits a component.
    }

    public void mousePressed(MouseEvent e) {
        //Invoked when a mouse button has been pressed on a component.
    }

    public void mouseReleased(MouseEvent e) {
        //Invoked when a mouse button has been released on a component.
    }

    public static void main(String[] args) {

        WeekViewer wv = null;
        File file = new File(WEEK_FILE);

        if (args.length == 1) {
            file = new File(args[0]);
        }
        if (!file.exists()) {
            String str = "Usage:\njava snanny.WeekViewer <filename>";
            System.out.println(str);
            return;
        }
        wv = new WeekViewer(file);
        JFrame frame = new JFrame("WeekViewer");
        frame.setSize(800, 600);
        frame.getContentPane().add(wv);
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.show();
    }

}//WeekViewer
