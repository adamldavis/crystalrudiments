package net.crystalrudiments.time;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Holds options for Time project.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.0, April 4, 2004
 */
public class Options {

    public static final String OPT_FILE = "options.txt";

    static final String IMAGE_NAME = "imageNormal.jpg";

    public static final String[] MAP_IMAGES = { IMAGE_NAME, "imageNews.jpg",
            "imageBright.jpg", "imageBW.jpg"};

    public static final Color[] COLORS = { Color.red, Color.magenta,
            Color.green, Color.cyan, Color.blue, Color.pink, Color.orange,
            Color.gray, Color.orange.darker(), Color.yellow,
            Color.cyan.darker(), Color.magenta.darker(), Color.green.darker(),
            Color.blue.darker(), Color.darkGray, Color.yellow.darker(),
            Color.white};

    public static final String[] COLOR_NAMES = { "red", "magenta", "green",
            "cyan", "blue", "pink", "orange", "gray", "dark orange", "yellow",
            "dark cyan", "dark magenta", "dark green", "dark blue",
            "dark gray", "dark yellow", "white"};

    public static final Color DEFAULT_COLOR = Color.lightGray;

    public static String mapImage = IMAGE_NAME;

    public static String scheduleColors = "random";

    public static Color calendarColor = DEFAULT_COLOR;

    public static int scheduleDivisor = 3;

    private static boolean loaded = false;

    private static Options singleton = null;

    /** Default constructor: This class is a singleton. */
    public Options() {
        load();
    }

    public static Options getInstance() {
        if (singleton == null) singleton = new Options();
        return singleton;
    }

    public void load() {
        if (!loaded) {
            load(OPT_FILE);
            loaded = true;
        }
    }

    public void load(String fileName) {
        File file = new File(fileName);
        BufferedReader br = null;

        if (!file.exists()) return;
        try {
            br = new BufferedReader(new FileReader(file));
            for (String line = ""; ((line = br.readLine()) != null);) {
                StringTokenizer tok = new StringTokenizer(line, "\t");
                parseTokens(tok);
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    private void parseTokens(StringTokenizer tok) {
        String type = tok.nextToken().toLowerCase();
        String value = tok.nextToken();
        if (type.startsWith("schedulecol")) {
            scheduleColors = value;
        } else if (type.startsWith("calendarcol")) {
            calendarColor = parseColor(value);
        } else if (type.startsWith("scheduledivis")) {
            scheduleDivisor = Integer.parseInt(value);
        } else if (type.startsWith("mapimag")) {
            mapImage = value;
        }
    }

    public Color parseColor(String str) {
        for (int i = 0; i < COLORS.length; i++) {
            if (str.equals(COLOR_NAMES[i])) { return COLORS[i]; }
        }
        return DEFAULT_COLOR;
    }

    public String nameOf(Color color) {
        for (int i = 0; i < COLORS.length; i++) {
            if (color.equals(COLORS[i])) { return COLOR_NAMES[i]; }
        }
        return "gray";
    }

    public void save() {
        save(OPT_FILE);
    }

    public void save(String fileName) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(fileName));
            pw.println("ScheduleColors\t" + scheduleColors);
            pw.println("CalendarColor\t" + nameOf(calendarColor));
            pw.println("ScheduleDivisor\t" + scheduleDivisor);
            pw.println("MapImage\t" + mapImage);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}//Options
