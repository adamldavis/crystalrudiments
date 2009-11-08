
/**
 * HOlds the constants for JMEditor.
 *
 * @author <A HREF="http://www.adamldavis.com/">Adam L. Davis</A>
 */
package net.crystalrudiments.rpg;

public interface RPGConstants {
    
    public static final String kALT = "Alt"; //$NON-NLS-1$
    public static final String kCTRL = "Ctrl"; //$NON-NLS-1$
    
    /** Tile width. */
    public static final int TILE_W = 46;
    /** Tile height. */
    public static final int TILE_H = 44;
    public static final int PERSON_W = 44;
    public static final int PERSON_H = 42;
    public static final int OBJECT_W = 38;
    public static final int OBJECT_H = 36;
    public static final String IMAGE_DIR = Messages.getString("RPGConstants.images", "rpg"); //$NON-NLS-1$
    public static final String MAP_DIR = Messages.getString("RPGConstants.maps", "rpg"); //$NON-NLS-1$
    public static final String PEOPLE_DIR = Messages.getString("RPGConstants.people", "rpg"); //$NON-NLS-1$
    public static final String SONGS_DIR = Messages.getString("RPGConstants.songs", "rpg"); //$NON-NLS-1$
    public static final String SOUNDS_DIR = Messages.getString("RPGConstants.sounds", "rpg"); //$NON-NLS-1$
    public static final String FIRST_MAP = Messages.getString("RPGConstants.first.map", "rpg"); //$NON-NLS-1$
    
    public static final int  X_SHIFT = 11;
    public static final int  Y_SHIFT = 7;
    public static final int  NUM_TILES_X = 23;
    public static final int  NUM_TILES_Y = 15;
    //    public static final int  FIRST_MAP = "XMM";
    public static final String  FILE_CHARS = "#$%&'()+цтыщ€÷№йвдаеклипомƒ≈…бнуъс"; //$NON-NLS-1$
    //''Public Const UNUSED_CHARS = ",-;=@[]^_`{}~"
    public static final int  ALPHA_ENCODE_MAX = 69;
    public static final String  CAPTION_DELIM = ":"; //$NON-NLS-1$
    
    public static final String[] SONGS = Messages.getArray("RPGConstants.", 10, 23, "rpg");
    
    public static final String[] PERSON_NAMES = Messages.getArray("RPGConstants.", 24, 86, "rpg");
    
    public static final String[] OBJECT_NAMES = Messages.getArray("RPGConstants.", 87, 198, "rpg"); 
}
