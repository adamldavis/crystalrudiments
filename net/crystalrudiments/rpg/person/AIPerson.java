/*
 * Revisions:
 * 
 * 6/17/03 - Version 1.0
 */
package net.crystalrudiments.rpg.person;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import net.crystalrudiments.io.InOutHelp;
import net.crystalrudiments.rpg.RPGConstants;

/**
 * Represents a character in the game and contains all of its behavior
 * and speech.
 * 
 * @author <A HREF="mailto:gte459u@mail.gatech.edu">Adam L. Davis </A>
 */
public class AIPerson extends Person implements RPGConstants {

    private static final boolean bDEBUG = true;

    private int emotion = 0;

    private List flags = null;
    
    private InOutHelp ioh = new InOutHelp();

    public AIPerson() {
        this("default", 0, 0, 0);
    }

    public AIPerson(String name) {
        this(name, 0, 0, 0);
    }

    /** Loads the given file. */
    public AIPerson(String name, int x, int y, int iImage) {
        this.name = name;
        fileName = PEOPLE_DIR + System.getProperty("file.separator") + name
                + ".char";
        posx = x;
        posy = y;
        imageNum = iImage;
        flags = new Vector();
        load();
    }

    public int getFlagsSize() {
        return flags.size();
    }

    /**
     * This simply print out the file in the same order it was read in.
     */
    public void saveState() {
        PrintWriter pw = null;
        pw = ioh.openPW(fileName);
        if (pw == null) return;
        //save
        for (int i = 0; i < flags.size(); i++) {
            pw.println((String) flags.get(i));
        }
        ioh.close(pw);
    }

    /**
     * Loads the char file and puts it in two Vectors: flags and dialog. Dialog
     * starts when the first "SAY" is seen.
     */
    public void load() {
        BufferedReader br = null;
        br = ioh.openBR(fileName);
        if (null == br) return;
        //load
        StringTokenizer strtok = null;
        String sLine = "";
        while (sLine != null) {
            sLine = InOutHelp.readLine(br);
            if (sLine == null) break;
            flags.add(sLine);
            if (sLine.startsWith("emotion = ")) {
                setEmotion(sLine.substring(10).toLowerCase());
            }
            if (sLine.startsWith("position = ")) {
                String pos = sLine.substring(11);
                int comma = pos.indexOf(',');
                posx = Integer.parseInt(pos.substring(0, comma).trim());
                posy = Integer.parseInt(pos.substring(comma + 1).trim());
            }
        }
        ioh.close(br);
    }

    public void changeX(int dx) {
        final String POS = "position = ";
        super.changeX(dx);
        String flag = (String) findFlag(POS);
        if (flag.equals(""))
            flags.add(POS + posx + ", " + posy);
        else
            flags.set(flags.indexOf(flag), (POS + posx + ", " + posy));
    }

    public void changeY(int dy) {
        final String POS = "position = ";
        super.changeY(dy);
        String flag = (String) findFlag(POS);
        if (flag.equals(""))
            flags.add(POS + posx + ", " + posy);
        else
            flags.set(flags.indexOf(flag), POS + posx + ", " + posy);
    }

    public boolean hasEmotion(int emotionConstant) {
        return ((emotion & emotionConstant) > 0);
    }

    /**
     * Takes in a list of emotions, for example (happy, sad, tired, mad, ...)
     */
    public void setEmotion(String sLine) {
        emotion = 0;
        if (sLine.indexOf("happy") >= 0) {
            emotion |= HAPPY;
        }
        if (sLine.indexOf("sad") >= 0) {
            emotion |= SAD;
        }
        if (sLine.indexOf("tired") >= 0) {
            emotion |= TIRED;
        }
        if (sLine.indexOf("mad") >= 0) {
            emotion |= MAD;
        }
        if (sLine.indexOf("depressed") >= 0) {
            emotion |= DEPRESSED;
        }
        if (sLine.indexOf("sick") >= 0) {
            emotion |= SICK;
        }
        if (sLine.indexOf("afraid") >= 0) {
            emotion |= AFRAID;
        }
        //replace flag
        setFlag("emotion", sLine);
    }

    public void setFlag(String flag, String newValue) {
        String oldFlag = findFlag(flag);
        String newFlag = flag + " = " + newValue;
        if (bDEBUG)
                System.out.println("oldFlag was " + oldFlag + " ; now is "
                        + newFlag);
        if (oldFlag.equals("")) {
            flags.add(newFlag);
        } else
            flags.set(flags.indexOf(oldFlag), newFlag);
    }

    public String findFlag(String flag) {
        String sLine = null;
        //
        for (int i = 0; i < flags.size(); i++) {
            sLine = (String) flags.get(i);
            if (sLine.charAt(0) == '#') {
                //skip
                continue;
            }
            if (sLine.startsWith(flag)) { return sLine; }
        }
        return "";
    }

    public String talk() {
	//TODO : get talking block!
        return "";
    }

    /** Makes a conversation with given agent.
     */
    public static void main(String[] args) {
        AIPerson a = null;
        List replies = null;
        String input = "";
        InOutHelp ioh = new InOutHelp();
        if (args.length == 0)
            a = new AIPerson("default");
        else
            a = new AIPerson(args[0]);
        while (!input.equals("exit")) {
            //
            if (replies != null) {
                char choice = '0';
                while (choice == '0') {
                    System.out.println(replies.toString());
                    System.out.print(">");
                    input = ioh.getInputLine();
                    if (input.equals("exit")) break;
                    try {
                        choice = input.charAt(0);
                    } catch (Exception e) {
                        choice = '0';
                    }
                }
            }
            System.out.println(a.talk());
            if (replies == null) break;
        }
        System.out.println("[exiting .........................]");
    }
}
