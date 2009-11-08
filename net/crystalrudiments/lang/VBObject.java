
/*
 * 07/26/03 - version 1.
 */
package net.crystalrudiments.lang;

import java.util.StringTokenizer;

import javax.swing.JOptionPane;

/**
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 */
public class VBObject implements VBConstants {

    /*
     * runs the garbage collector.
     */
    public static void DoEvents() {
        System.gc(); //run the garbage collector :-)
    }

    public static void MsgBox(String str) {
        //show string...
        JOptionPane.showMessageDialog(null, str);
    }

    public static int MsgBox(String str, int type) {
        int ret = 0;
        //handle cases
        switch (type) {
        case vbOKOnly:
            ret = vbOK;
            MsgBox(str);
            break;
        case vbOKCancel:
            ret = JOptionPane.showConfirmDialog(null, str, "Select an Option",
                    JOptionPane.OK_CANCEL_OPTION);
            break;
        case vbYesNo:
            ret = JOptionPane.showConfirmDialog(null, str, "Select an Option",
                    JOptionPane.YES_NO_OPTION);
            break;
        case vbYesNoCancel:
            ret = JOptionPane.showConfirmDialog(null, str);
            break;
        default:
        //nothing
        }
        switch (ret) {
        case JOptionPane.OK_OPTION:
            ret = vbOK;
            break;
        case JOptionPane.CANCEL_OPTION:
            ret = vbCancel;
            break;
        //case JOptionPane.YES_OPTION: ret = vbYes;
        //break;
        case JOptionPane.NO_OPTION:
            ret = vbNo;
            break;
        case JOptionPane.CLOSED_OPTION:
            ret = vbCancel;
            break;
        default:
        //nothing
        }
        return ret;
    }

    public static int Int(int i) {
        return i;
    }

    public static int CInt(Object o) {
        if (o instanceof String) {
            return Integer.parseInt(o.toString());
        } else {
            return 0;
        }
    }

    public static String[] Split(String str, String delim) {
        StringTokenizer strtok;
        String[] ret;
        strtok = new StringTokenizer(str, delim);
        ret = new String[strtok.countTokens()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = strtok.nextToken();
        }
        return ret;
    }

    public static String CStr(Object o) {
        return o.toString();
    }

    public static String CStr(int i) {
        return ("" + i);
    }

    public static String left(String str, int n) {
        return str.substring(0, n);
    }

    public static String right(String str, int n) {
        return str.substring(str.length() - n);
    }

    public static String Left(String str, int n) {
        return left(str, n);
    }

    public static String Right(String str, int n) {
        return right(str, n);
    }

    public static int InStr(int offset, String str, String find) {
        return str.indexOf(find, offset - 1) + 1;
    }

    public static String Mid(String str, int index, int n) {
        return str.substring(index - 1, index - 1 + n);
    }

    public static int Asc(String str) throws Exception {
        if (str.length() != 1)
                System.err
                        .println("Error: VBObject.Asc --- Input string should be length 1, it is "
                                + str.length() + ".");
        int ret = ascii.indexOf(str);
        if (ret == -1 && Character.isISOControl(str.charAt(0))) ret = 31;
        if (ret == -1) { throw new Exception("Error: " + str
                + " is out of range for VBObject.Asc(String)."); }
        return ret + 31;
    }

    //  31 to 129
    public static String Chr(int i) {
        if (i < 31 || i >= (31 + ascii.length())) {
            System.err.println("Error: " + i
                    + " is out of range for VBObject.Chr(int).");
            return "_";
        }
        return ascii.substring(i - 31, i - 31 + 1);
    }

    public static double Rnd() {
        return Math.random();
    }

    /**
     * dec = number of decimal places. CAUTION: doesn't work for very large
     * values.
     */
    public static double Round(double input, int dec) {
        double mult = 10;
        mult = Math.pow(10d, dec);

        return Math.round(input * mult) / mult;
    }

    public static int Abs(int i) {
        return Math.abs(i);
    }
}//class
