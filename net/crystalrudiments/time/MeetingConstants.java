package net.crystalrudiments.time;

import java.util.*;
import java.awt.*;

public interface MeetingConstants {

    public static final String WEEK_FILE = "week.in";

    public static final String[] DAY_NAMES = { "Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday"};

    public static final int CAL_FIRST_DAY = Calendar.SUNDAY;

    public static final long MILLIS_IN_DAY = 1000 * 60 * 60 * 24;

    public static final Color[] COLORS = { Color.red, Color.magenta,
            Color.green, Color.cyan, Color.blue, Color.pink, Color.orange,
            Color.gray, Color.orange.darker(), Color.yellow,
            Color.cyan.darker(), Color.magenta.darker(), Color.green.darker(),
            Color.blue.darker(), Color.darkGray, Color.yellow.darker(),
            Color.red, Color.magenta, Color.green, Color.cyan, Color.blue,
            Color.pink, Color.orange, Color.gray, Color.orange.darker(),
            Color.yellow, Color.cyan.darker(), Color.magenta.darker(),
            Color.green.darker(), Color.blue.darker(), Color.darkGray,
            Color.yellow.darker()

    };

    public static final Color[] RAINBOW = { Color.red, Color.magenta,
            Color.magenta.darker(), Color.blue.darker(), Color.blue,
            Color.cyan, Color.cyan.darker(), Color.green.darker(), Color.green,
            Color.yellow, Color.yellow.darker(), Color.orange,
            Color.orange.darker(), Color.gray, Color.darkGray, Color.pink,
            Color.red, Color.magenta, Color.magenta.darker(),
            Color.blue.darker(), Color.blue, Color.cyan, Color.cyan.darker(),
            Color.green.darker(), Color.green, Color.yellow,
            Color.yellow.darker(), Color.orange, Color.orange.darker(),
            Color.gray, Color.darkGray, Color.pink};
}//MeetingConstants
