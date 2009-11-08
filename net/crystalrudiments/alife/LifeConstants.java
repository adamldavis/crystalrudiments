/*
 * Interface LifeConstants.
 *
 */
package net.crystalrudiments.alife;

/**
 * Contains constants for the ALife project.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis </A>
 * @version Version 1.0, April 28, 2001
 */
public interface LifeConstants
{

    /**
     * Initial size of gene.
     */
    static final int SIZE_GENE = 8; //initial size of genes.

    /**
     * Base used for sequences.
     */
    static final int BASE = 8; //Base used for sequences.

    /**
     * dimension 'size' of screen. (arrays)
     */
    static final int SCALE = 256; //dimension 'size' of screen. (arrays)

    /**
     * Number of pictures to display in window at a time.
     */
    static final int NUM_PICS = 9;

    /**
     * maximum color int.
     */
    static final int MAX_COLOR = 255; //maximum color int.

    /**
     * how many times to iterate.
     */
    static final int REPEAT = 100; //how many times to iterate.

    /**
     * max-initial # of operations in eq.
     */
    static final int INIT_EQ_LENGTH = 3; //max-initial # of operations in eq.

    /**
     * 1 chance out of this to remove a part of a Gene's sequence.
     */
    static final int PROB_REMOVE = 15; //1 chance out of this.

    /**
     * 1 chance out of this to add to a Gene's sequence.
     */
    static final int PROB_ADD = 10; //1/THIS.

    /**
     * 1 chance out of this to move a part of a Gene's sequence.
     */
    static final int PROB_MOVE = 9; //1/THIS.

    /**
     * 1 chance out of this to 'spin' a Gene's sequence, meaning swapping back
     * to front.
     */
    static final int PROB_SPIN = 30; //1/THIS.

    /**
     * initial size of array a[i].
     */
    static final int INIT_SIZE_A = 32; //initial size of array a[i].

    /**
     * the range of random array A.
     */
    static final int RANDOM_A = 1; //the range of random a.

    /*-----------------------------------------------------------------------*/
    //MENU CONSTANTS
    /**
     * The string for the game menu button.
     */
    public static final String MENU_GAME = "File";

    /**
     * The string for the exit menu button.
     */
    public static final String MENU_EXIT = "Exit";

    /**
     * The string for the new game menu button.
     */
    public static final String MENU_NEW = "New";

    /**
     * The string for the load game menu button.
     */
    public static final String MENU_LOAD = "Load State";

    /**
     * The string for the save game menu button.
     */
    public static final String MENU_SAVE = "Save State";

    /**
     * The string for the help menu button.
     */
    public static final String MENU_HELP = "Help";

    /**
     * The string for the instructions menu button and the title of the
     * Instructions frame.
     */
    public static final String MENU_INST = "Instructions";

    /**
     * The instructions.
     */
    public static final String TEXT_INSTRUCT = "Art Life: \n" 
        + "   Creates pictures using random equations and\n"
            + "uses artificial life to reproduce and mutate these\n" 
            + "images based on user input.\n"
            + "   Click the picture that is most appealing to you\n"
            + "and its equations will be used to create the next\n"
            + "succession of pictures. Right-click a picture to\n" 
            + "see its enlargement.";

    /*-----------------------------------------------------------------------*/
    /*
     * Interprets the equations and returns a color.
     * 
     * public Color eqRGB(int x, int y) { int r_=0; int g_=0; int b_=0; double
     * raw=0d; int colr; //hue double alpha; //brightness String eq; eq = eq1;
     * //equation...
     * 
     * //THE EQUATION STUFF.... raw = getValue(eq.charAt(0),x,y); //eg. "x/b-y*"
     * 
     * while (eq.length() > 2) { raw = calculate(raw, eq.substring(1), x, y);
     * //eg. "/b-y*" eq = eq.substring(2); //eg. "b-y*" } colr = (int)
     * (16777216d*raw); //convert raw to Color.
     *  // fix the Color within limits. //colr = fixNum(colr, 16777216); if
     * (colr > 16777216) colr = 16777216; // <--Keep colr within bounds. if
     * (colr < 0) colr = 0;
     * 
     * //EQUATION2.... eq = eq2;
     * 
     * raw = getValue(eq.charAt(0),x,y); //eg. "x/b-y*"
     * 
     * while (eq.length() > 2) { raw = calculate(raw, eq.substring(1), x, y);
     * //eg. "/b-y*" eq = eq.substring(2); //eg. "b-y*" } alpha = raw;
     * 
     * if (alpha > 1d) alpha = 1d; // <---keep alpha down to 1. if (alpha < 0d)
     * alpha = 0d;
     * 
     * 
     * r_ = (int) (alpha * (double) fixNum(colr, MAX_COLOR)); g_ = (int) (alpha *
     * (double) fixNum((colr/256), MAX_COLOR)); b_ = (int) (alpha * (double)
     * fixNum((colr/65536), MAX_COLOR));
     * 
     * try { return (new Color(r_,g_,b_)); } catch (Exception e) {
     * System.err.println("interpretEq: " + e); System.exit(0); } return
     * Color.red; }//eqRGB
     *  * Interprets the equations and returns a color.
     * 
     * public Color eqFirst(int x, int y) { int r_=0; int g_=0; int b_=0; int
     * big; double bright;
     * 
     * //use the eq. here... //big = (int) (withinBounds(a, x, y, width, height) *
     * 256); //big += 256 * (int) (withinBounds(b, x, y, width, height) * 256);
     * //big += 65536 * (int) (withinBounds(c, x, y, width, height) * 256);
     * 
     * bright = withinBounds(a, x, y); big = 256 * (int) (bright *
     * withinBounds(b, x, y) * 256); big += 65536 * (int) (bright *
     * withinBounds(c, x, y) * 256);
     * 
     * if (big > 16777216) System.out.println("---> big is too big!"); //big =
     * fixNum(big, 16777216);
     * 
     * if ((x==1)&&(y==1)) if (bDEBUG) System.out.println("big =" + big);
     * 
     * //if ((int)(big/65536)==256) System.out.println("------->256"); //if
     * ((int)(big/65536)==255) System.out.println("---->255");
     * 
     * r_ = fixNum(big, MAX_COLOR); g_ = fixNum((big/256), MAX_COLOR); b_ =
     * fixNum((big/65536), MAX_COLOR);
     * 
     * try { return (new Color(r_,g_,b_)); } catch (Exception e) {
     * System.err.println("interpretEq: " + e); System.exit(0); } return
     * Color.red; }//eqFirst
     */
}