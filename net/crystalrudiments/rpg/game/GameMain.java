/*
 * Revisions:
 * 
 * 6/17/03 - rev 3. Used for JMiedor.
 */
package net.crystalrudiments.rpg.game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.logging.Logger;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.crystalrudiments.common.logging.LoggerFactory;
import net.crystalrudiments.rpg.IOHelper;
import net.crystalrudiments.rpg.MapPanel;
import net.crystalrudiments.rpg.Messages;
import net.crystalrudiments.rpg.RPGConstants;
import net.crystalrudiments.rpg.person.AIPerson;
import net.crystalrudiments.rpg.person.Person;
import net.crystalrudiments.sound.SoundPlayer;
import net.crystalrudiments.sound.midi.MidiPlayer;

/**
 * Copyright: 2005
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version Version 1.0, June 17, 2003
 */
public class GameMain extends JFrame implements RPGConstants, IGame
{

    private static final String MAIN_TITLE = Messages.getString("GameView.title", "rpg.game"); //$NON-NLS-1$

    static final boolean bDEBUG = true;

    static final boolean DOUBLE_BUFFERED = true;

    private MapPanel map = null;

    private MenuBar menuBar = null;

    private IGameControl eventHandler = null;

    /** Menu for starting new game. */
    private Menu mnuGame;

    /** Menu for Help. */
    private Menu mnuHelp;

    /** MenuItem for instructions. */
    private MenuItem mnuInstruct;

    /** MenItem for exiting. */
    private MenuItem mnuExit;

    private MenuItem mnuNew;

    private MenuItem mnuLoad;

    private MenuItem mnuSave;

    private IOHelper ioh = null;

    private MidiPlayer midiPlayer = null;

    private SoundPlayer soundPlayer = null;
    
    private GameModel model = new GameModel( map, this );
    
    private static final Logger LOG = LoggerFactory.getDefaultLogger();

    /**
     * Default constructor.
     *
     */
    public GameMain()
    {

        super( MAIN_TITLE );
        LOG.info( "initializing window." ); //$NON-NLS-1$
        this.setSize( 800, 600 );
        ioh = new IOHelper();
        this.addWindowListener( new WindowAdapter()
        {

            public void windowClosing( WindowEvent e )
            {
                dispose();
                System.exit( 0 );
            }
        } );
        Container content = this.getContentPane();
        content.setLayout( new BorderLayout() );
        //board = (new GameBoard());
        //map = (new MapPanel(board));

        content.add( ( map = new MapPanel() ) );

        //content.add( makeStatusPanel(), BorderLayout.SOUTH );

        super.setMenuBar( makeMenuBar() );

        makeEventHandler();

        this.repaint();
    }

    private void makeEventHandler()
    {
        LOG.info( "Making event handler." ); //$NON-NLS-1$
        eventHandler = new GameControl( this, this );

        mnuInstruct.addActionListener( eventHandler ); //Instructions

        mnuNew.addActionListener( eventHandler ); //New
        mnuSave.addActionListener( eventHandler ); //Save
        mnuLoad.addActionListener( eventHandler ); //Load
        mnuExit.addActionListener( eventHandler ); //Exit
        this.addKeyListener( eventHandler );
        map.addMouseListener( eventHandler );
    }

    private MenuBar makeMenuBar()
    {
        menuBar = new MenuBar();
        mnuGame = new Menu( Messages.getString("GameView.4", "rpg.game") ); //init menu //$NON-NLS-1$
        mnuHelp = new Menu( Messages.getString("GameView.5", "rpg.game") ); //$NON-NLS-1$
        mnuInstruct = new MenuItem( Messages.getString("GameView.6", "rpg.game") ); //$NON-NLS-1$
        mnuNew = new MenuItem( Messages.getString("GameView.7", "rpg.game") ); //$NON-NLS-1$
        mnuSave = new MenuItem( Messages.getString("GameView.8", "rpg.game") ); //$NON-NLS-1$
        mnuLoad = new MenuItem( Messages.getString("GameView.9", "rpg.game") ); //$NON-NLS-1$
        mnuExit = new MenuItem( Messages.getString("GameView.10", "rpg.game") ); //$NON-NLS-1$

        mnuGame.add( mnuNew );
        mnuGame.add( mnuSave );
        mnuGame.add( mnuLoad );
        mnuGame.add( mnuExit );
        mnuHelp.add( mnuInstruct );
        menuBar.add( mnuGame );
        menuBar.add( mnuHelp );

        return menuBar;
    }

    /**
     * Shows the Instructions.
     */
    public void showInstructions()
    {
        BorderLayout paneBorderLayout = new BorderLayout();
        //JPanel txtAreaPane = new JPanel();
        JScrollPane txtAreaScrollPane = new JScrollPane();
        JTextArea txtaBodyText = new JTextArea();
        JEditorPane editorPane;
        JFrame frameInst = new JFrame( Messages.getString("GameView.11", "rpg.game") ); //$NON-NLS-1$
        Container main = frameInst.getContentPane();

        LOG.info( "Showing instructions." ); //$NON-NLS-1$
        //Set the main layout
        frameInst.getContentPane().setLayout( paneBorderLayout );
        java.net.URL helpURL = null;
        //Set up the Body Text Box
        txtaBodyText.setLineWrap( true );
        txtaBodyText.setWrapStyleWord( true );
        //txtAreaPane.setLayout(txtAreaGridLayout);

        editorPane = new JEditorPane();
        editorPane.setEditable( false );
        //create a URL object for the TextSamplerDemoHelp.html file...
        String s = null;
        try
        {
            s = "file:" + System.getProperty( "user.dir" ) + System.getProperty( "file.separator" ) + Messages.getString("GameView.16", "rpg.game"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            helpURL = new java.net.URL( s );
            /* ... use the URL to initialize the editor pane ... */
        } catch ( Exception e )
        {
            System.err.println( Messages.getString("GameView.1", "rpg.game") + s ); //$NON-NLS-1$
        }

        try
        {
            editorPane.setPage( helpURL );
        } catch ( java.io.IOException e )
        {
            System.err.println( Messages.getString("GameView.3", "rpg.game") + helpURL ); //$NON-NLS-1$
        }

        //Add the scroll pane to the textbox
        //txtAreaPane.add(txtAreaScrollPane, null);
        txtAreaScrollPane.getViewport().add( editorPane, null );

        //Position the labels and text boxes
        frameInst.getContentPane().add( txtAreaScrollPane, BorderLayout.CENTER );

        //Add the panels to the top and the Center of the screen
        //this.getContentPane().add(actionPanel, BorderLayout.NORTH);
        //this.getContentPane().add(txtAreaPane, BorderLayout.CENTER);
        frameInst.setSize( new Dimension( 600, 480 ) );

        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = ( dim.width - w ) / 2;
        int y = ( dim.height - h ) / 2;

        // Move the window
        frameInst.setLocation( x, y );
        frameInst.show();
    }

    public void newGame()
    {
        model.newGame();
    }

    public void rightClick( int x, int y, boolean shift, boolean control )
    {
        LOG.info(
                "Right click at (" + x + "," + y + ")  shift=" + shift + "  control=" + control ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }

    public void leftClick( int x, int y, boolean shift, boolean control )
    {
        LOG.info(
                "Left click at (" + x + "," + y + ")  shift=" + shift + "  control=" + control ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }

    public void middleClick( int x, int y, boolean shift, boolean control )
    {
        LOG.info(
                "Middle click at (" + x + "," + y + ")  shift=" + shift + "  control=" + control ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }

    public void keyTyped( char c, String modifiers )
    {
        switch ( c ) {
        case '1':
            move( '1' );
            break;
        case '2':
            move( 'd' );
            break;
        case '3':
            move( '3' );
            break;
        case '4':
            move( 'l' );
            break;
        case '5':
            break;
        case '6':
            move( 'r' );
            break;
        case '7':
            move( '7' );
            break;
        case '8':
            move( 'u' );
            break;
        case '9':
            move( '9' );
            break;
        case '0':
            break;
        case ' ':
            model.spaceBar();
            break;
        case '+':
            break;
        case '-':
            break;
        case '*':
            break;
        case 's':
            if ( modifiers.equals( kALT ) ) saveState();
            break;
        case 'l':
            if ( modifiers.equals( kALT ) ) loadState();
            break;
        case 'n':
            if ( modifiers.equals( kALT ) ) newGame();
            break;
        case 'q':
            if ( modifiers.equals( kALT ) )
            {
                dispose();
                System.exit( 0 );
            }
            break;
        case 'm':
        case '\n':
            //System.out.println("newline");
            LOG.info( "newline pressed." ); //$NON-NLS-1$
            break;
        default:
            LOG.fine( "key pressed: " + c ); //$NON-NLS-1$
        //System.out.print(c);
        }
    }

    /**
     * Only moves map if model.getPlayer() can move... Might change model.getBoard() if needed, (ex:
     * boulders).
     */
    public void move( char c )
    {
        if ( model.getPlayer() == null || model.getBoard() == null ) return; //abort.
        if ( this.move( c, model.getPlayer(), model.getBoard() ) )
        {
            map.move( c );
            model.getPlayer().setMapCode( model.getBoard().getCode() );
            model.getPlayer().setPosition( map.getPosX(), map.getPosY() );
        }
        this.repaint();
    }

    /**
     * Attempts to move the given Person in given direction and returns result
     * (pass/fail).
     */
    public boolean move( char c, Person person, IGameBoard board )
    {
        boolean okay = true;
        int nx = person.getX(), ny = person.getY();
        int tile;
        switch ( c ) {
        case 'u':
            ny--;
            break;
        case 'd':
            ny++;
            break;
        case 'l':
            nx--;
            break;
        case 'r':
            nx++;
            break;
        case '1':
            nx--;
            ny++;
            break;
        case '3':
            nx++;
            ny++;
            break;
        case '7':
            nx--;
            ny--;
            break;
        case '9':
            nx++;
            ny--;
            break;
        default:
            okay = false;
            if ( bDEBUG ) System.err.println( "ERROR: unknown move command:" + c ); //$NON-NLS-1$
            LOG.warning( "unknown move command: " + c ); //$NON-NLS-1$
            return okay;
        }
        tile = board.getTile( nx, ny );
        if ( tile >= 150 && tile < 170 )
        { //mystic walls.
            if ( ( tile % 10 ) == 1 && ( tile % 10 ) == 8 )
            { //doors.
                board.setTile( nx, ny, ++tile );
                okay = true;
            }
            if ( ( tile % 10 ) != 2 && ( tile % 10 ) != 9 ) okay = false;
        } else if ( tile >= 70 && tile < 90 )
            okay = false; //garden.
        else if ( tile >= 110 && tile < 130 )
        {
            okay = false; //forest
        } else if ( tile >= 410 && tile < 430 )
        {
            okay = false; //caverock
        } else if ( tile >= 200 && tile < 400 )
        { //beach(200) and swamp(300).
            if ( tile >= 330 ) okay = false;
            tile = tile % 100;
            if ( tile == 10 || tile == 11 || tile == 20 || tile == 21 ) okay = false;
        } else if ( ( tile >= 30 && tile < 60 ) || ( tile >= 530 && tile < 560 ) )
        {
            //evil and castle walls.
            okay = false;
            if ( tile < 100 )
            { 
                okay = okay || handleEvilDoors( board, nx, ny, tile );
            }
            tile = tile % 100;
            if ( tile % 10 == 9 ) okay = true;
            if ( tile == 41 || tile == 51 || tile == 37 ) okay = true;
        } else if ( ( tile == 510 || tile == 511 ) || tile == 520 || tile == 521 )
        {
            okay = false; //desert
        }

        if ( board.getPerson( nx, ny ) != null )
        {
            okay = false; //can't walk on people.
            //talk to them?
            TalkingWindow tw = new TalkingWindow( (( AIPerson ) board.getPerson( nx, ny )) );
            tw.show();
        }
        //	if (bDEBUG) System.out.println("tile = " + tile + " @ ("+nx+"," +
        // ny+") " + okay);
        return okay;
    }

    private boolean handleEvilDoors( IGameBoard board, int nx, int ny, int tile )
    {
        boolean okay = false;
        if ( tile == 38 || tile == 48 )
        {
            board.setTile( nx, ny, 1 + tile );
            okay = true;
        }
        else if ( tile == 40 || tile == 50 )
        {
            board.setTile( nx, ny, 1 + tile );
            okay = true;
        }
        else if ( tile == 36 )
        {
            board.setTile( nx, ny, 1 + tile );
            okay = true;
        }
        return okay;
    }

    /** Overides {@link JFrame#repaint()}. */
    public void repaint()
    {

        //refreshStatus();
        super.repaint();
    }

    /**
     * Plays a sound; name given relative to sound folder.
     */
    public void playSound( String fileName )
    {
        if ( soundPlayer == null )
        {
            soundPlayer = new SoundPlayer();
        }
        File file = new File( SOUNDS_DIR, fileName );
        try
        {
            soundPlayer.play( file );
        } catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * not yet implemented.
     */
    public void playMidi( String fileName )
    {
        if ( midiPlayer == null )
        {
            midiPlayer = new MidiPlayer();
        }
        midiPlayer.stop();
        File file = new File( fileName );
        midiPlayer.play( file );
    }

    public static void main( String args[] )
    {
        GameMain jm = new GameMain();
        jm.show();
    }

    /**
     * @see net.crystalrudiments.rpg.game.IGame#saveState()
     */
    public void saveState()
    {
        model.saveState();
    }

    /**
     * @see net.crystalrudiments.rpg.game.IGame#loadState()
     */
    public void loadState()
    {
        model.loadState();
    }

    /**
     * @see net.crystalrudiments.rpg.game.IGame#spaceBar(int, int)
     */
    public void spaceBar( int px, int py )
    {
        model.spaceBar( px, py );
    }
} 
