package net.crystalrudiments.time;

import java.io.*;
import java.util.*;

/**
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.0, July 6, 2004
 */
public class TextUI implements UIConstants, MeetingConstants {

    private CalendarWeek calweek;

    public TextUI() {
        this(null);
    }

    public TextUI(File file) {
        if (file == null) {
            calweek = new CalendarWeek();
        } else {
            calweek = new CalendarWeek(file);
        }
    }

    private static void println(String str) {
        System.out.println(str);
    }

    private static void print(String str) {
        System.out.print(str);
    }

    /** handles user input */
    public void handleInput(String input) {
        StringTokenizer tok = new StringTokenizer(input, " ");
        String token = tok.nextToken();
        if (token.equals("save")) {
            calweek.sort();
            calweek.saveAll();
        } else if (token.equals("load")) {
            if (tok.hasMoreTokens() == false) return;
            String fname = tok.nextToken();
            if (fname.length() > 0) {
                calweek = new CalendarWeek(new File(fname));
            }
        } else if (token.equals("addm")) {
            calweek.addMeeting(input.substring(token.length()).trim());
        } else if (token.equals("help") || token.equals("?")) {
            println(HELP_MSG);
        } else if (token.equals("print")) {
            println(calweek.toString());
        } else if (token.equals("learn")) {
            println(calweek.toString());
        } else {
            println("Unknown command: " + input + "\n(Type help)");
        }
    }

    /** Holds the user-input loop. */
    public void start() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = "";

        while (true) {
            print(">");
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line.equals("exit") || line.equals("quit")) break;
            //allow ui to handle input:
            handleInput(line);
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.println("Takes no arguments.");
            return;
        }
        println("");
        println(ABOUT_MSG);
        println(HELP_MSG);
        TextUI tui = new TextUI();
        tui.start();
    }

}//TextUI
