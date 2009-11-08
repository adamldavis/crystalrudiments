/* Revisions:
 *
 * 6/28/03 - version 1. Used for JMiedor.
 */
package net.crystalrudiments.rpg.editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.crystalrudiments.common.logging.LoggerFactory;
import net.crystalrudiments.rpg.BoardMakerWizard;
import net.crystalrudiments.rpg.MapPanel;
import net.crystalrudiments.rpg.Messages;

/**
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam Davis </A>
 * @version Version 1.0, June 28, 2003
 */
public class EditorMain extends JFrame implements EditorConstants, IEditor
{
    private static final Logger LOG = LoggerFactory.createLogger( EditorMain.class );

    static final boolean DOUBLE_BUFFERED = true;

    private MapPanel map = null;

    private EditorBoard board = null;

    private EditorHelp editor = null;

    private BoardMakerWizard boardMaker = null;

    /** Source folder. */
    public String source;

    public String tileSource;

    public String peopleSource;

    public String mapSource;

    /** MenuBar. */
    private MenuBar menuBar;

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

    private JPanel statusPanel;

    private JLabel lblRandom;

    private JLabel lblAuto;

    private JLabel lblCode;

    private JLabel lblPosition;

    private JLabel lblSize; //blocksize

    /* event handler */
    private IEditorControl eventHandler = null;

    /**
     */
    public EditorMain()
    {
        super( Messages.getString("Editor.title", "rpg.editor") ); //$NON-NLS-1$
        this.setSize( 800, 600 );

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
        board = ( new EditorBoard() );
        editor = new EditorHelp( board );
        map = ( new MapPanel( board ) );
        map.setOmnivision( true ); //see all
        content.add( map );

        content.add( makeStatusPanel(), BorderLayout.SOUTH );

        super.setMenuBar( makeMenuBar() );

        makeEventHandler();

        this.repaint();
    }

    private void makeEventHandler()
    {
        eventHandler = new EditorControl( this, this );

        mnuInstruct.addActionListener( eventHandler ); //Instructions

        mnuNew.addActionListener( eventHandler ); //New
        mnuSave.addActionListener( eventHandler ); //Save
        mnuLoad.addActionListener( eventHandler ); //Load
        mnuExit.addActionListener( eventHandler ); //Exit
        this.addKeyListener( eventHandler );
        map.addMouseListener( eventHandler );
    }

    private JPanel makeStatusPanel()
    {
        GridLayout layout = new GridLayout( 1, 5 );
        statusPanel = new JPanel();
        statusPanel.setLayout( layout );
        lblPosition = new JLabel( Messages.getString("Editor.1", "rpg.editor") ); //$NON-NLS-1$
        lblCode = new JLabel( Messages.getString("Editor.2", "rpg.editor") ); //$NON-NLS-1$
        lblRandom = new JLabel( Messages.getString("Editor.3", "rpg.editor") ); //$NON-NLS-1$
        lblAuto = new JLabel( Messages.getString("Editor.4", "rpg.editor") ); //$NON-NLS-1$
        lblSize = new JLabel( Messages.getString("Editor.5", "rpg.editor") ); //$NON-NLS-1$
        statusPanel.add( lblPosition );
        statusPanel.add( lblCode );
        statusPanel.add( lblRandom );
        statusPanel.add( lblAuto );
        statusPanel.add( lblSize );

        return statusPanel;
    }

    private MenuBar makeMenuBar()
    {
        menuBar = new MenuBar();
        mnuGame = new Menu( Messages.getString("Editor.6", "rpg.editor") ); //init menu //$NON-NLS-1$
        mnuHelp = new Menu( Messages.getString("Editor.7", "rpg.editor") ); //$NON-NLS-1$
        mnuInstruct = new MenuItem( Messages.getString("Editor.8", "rpg.editor") ); //$NON-NLS-1$
        mnuNew = new MenuItem( Messages.getString("Editor.9", "rpg.editor") ); //$NON-NLS-1$
        mnuSave = new MenuItem( Messages.getString("Editor.10", "rpg.editor") ); //$NON-NLS-1$
        mnuLoad = new MenuItem( Messages.getString("Editor.11", "rpg.editor") ); //$NON-NLS-1$
        mnuExit = new MenuItem( Messages.getString("Editor.12", "rpg.editor") ); //$NON-NLS-1$

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
        JFrame frameInst = new JFrame( Messages.getString("Editor.13", "rpg.editor") ); //$NON-NLS-1$
        Container main = frameInst.getContentPane();

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
            s = "file:" + System.getProperty( "user.dir" ) + System.getProperty( "file.separator" ) + Messages.getString("Editor.17", "rpg.editor"); //$NON-NLS-3$ //$NON-NLS-4$
            helpURL = new java.net.URL( s );
            /* ... use the URL to initialize the editor pane ... */
        } catch ( Exception e )
        {
            LOG.severe( Messages.getString("Editor.18", "rpg.editor") + s ); //$NON-NLS-1$
        }

        try
        {
            editorPane.setPage( helpURL );
        } catch ( java.io.IOException e )
        {
            LOG.severe( "Attempted to read a bad URL: " + helpURL ); //$NON-NLS-1$
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

    /**
     * Loads a map (location) based on user input.
     */
    public void loadState()
    {
        String code = JOptionPane.showInputDialog( Messages.getString("Editor.20", "rpg.editor") ); //$NON-NLS-1$
        String nfcode = null; //not found code.

        if ( null != code && code.length() == 3 )
        {
            nfcode = board.load( code );
            while ( nfcode != null )
            {
                board.makeNewMap( nfcode );
                nfcode = board.load( code );
            }
            map.setPos( 49, 49 );
            this.repaint();
        }
    }

    /**
     * Saves any modified data.
     */
    public void saveState()
    {
        board.save( false );
        JOptionPane.showMessageDialog( this, Messages.getString("Editor.21", "rpg.editor") ); //$NON-NLS-1$
    }

    /**
     * Reloads state, thus erasing changes made.
     */
    private void reloadState()
    {
        board.load( board.getCode() ); //Reload
        this.repaint();
    }

    public void rightClick( int x, int y, boolean shift, boolean control )
    {
        if ( shift && !control )
        {
            int tile = board.getTile( map.convertX( x ), map.convertY( y ) );
            int tnum = editor.getToolTileIndexOf( tile );
            if ( tnum > 0 )
                editor.setTileNum( -1 * tnum );
            else
                editor.setTileNum( tile );
        } else if ( !shift && !control )
        {
            editor.changeTile( 2, map.convertX( x ), map.convertY( y ) );
            this.repaint();
        }
    }

    public void leftClick( int x, int y, boolean shift, boolean control )
    {
        if ( control && !shift )
        {
            chooseTile();
        }
        editor.changeTile( 1, map.convertX( x ), map.convertY( y ) );
        this.repaint();
    }

    public void middleClick( int x, int y, boolean shift, boolean control )
    {
        editor.changeTile( shift ? -1 : 0, map.convertX( x ), map.convertY( y ) );
        this.repaint();
    }

    public void chooseTile()
    {
        String message = Messages.getString("Editor.22", "rpg.editor"); //$NON-NLS-1$
        Object[] possibleValues = EditorHelp.getTileNames();
        Object selectedValue = JOptionPane.showInputDialog( null, message, Messages.getString("Editor.23", "rpg.editor"), JOptionPane.INFORMATION_MESSAGE, //$NON-NLS-1$
                null, possibleValues, possibleValues[0] );
        if ( selectedValue != null )
        {
            editor.setTileNum( ( String ) selectedValue );
        }
    }

    public void choosePerson()
    {
        String message = Messages.getString("Editor.24", "rpg.editor"); //$NON-NLS-1$
        Object[] possibleValues = EditorHelp.getPossiblePeople();
        Object selectedValue = JOptionPane.showInputDialog( null, message, Messages.getString("Editor.25", "rpg.editor"), JOptionPane.INFORMATION_MESSAGE, //$NON-NLS-1$
                null, possibleValues, possibleValues[0] );
        if ( selectedValue != null )
        {
            editor.setCurrentPerson( ( String ) selectedValue );
        }
    }

    public void chooseObject()
    {
        String message = Messages.getString("Editor.26", "rpg.editor"); //$NON-NLS-1$
        Object[] possibleValues = EditorHelp.getPossibleObjects();
        Object selectedValue = JOptionPane.showInputDialog( null, message, Messages.getString("Editor.27", "rpg.editor"), JOptionPane.INFORMATION_MESSAGE, //$NON-NLS-1$
                null, possibleValues, possibleValues[0] );
        if ( selectedValue != null )
        {
            editor.setCurrentObject( ( String ) selectedValue );
        }
    }

    public void keyTyped( char c, String modifiers )
    {
        switch ( c ) {
        case 'a':
            if ( modifiers.equals( kALT ) )
            {
                board.changeAutoSave();
                refreshStatus();
            } else
                editor.setTileNum( Messages.getString("Editor.28", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'A':
            editor.setTileNum( Messages.getString("Editor.29", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'b':
            editor.setTileNum( Messages.getString("Editor.30", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'B':
            editor.setTileNum( Messages.getString("Editor.31", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'c':
            editor.setTileNum( Messages.getString("Editor.32", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'C':
            editor.setTileNum( Messages.getString("Editor.33", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'd':
            editor.setTileNum( Messages.getString("Editor.34", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'D':
            editor.setTileNum( Messages.getString("EditorHelp.door", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'e':
            editor.setTileNum( Messages.getString("Editor.36", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'E':
            editor.setTileNum( Messages.getString("Editor.37", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'f':
            break;
        case 'g':
            editor.setTileNum( Messages.getString("Editor.38", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'h':
            break;
        case 'i':
            editor.setTileNum( Messages.getString("Editor.39", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'j':
            break;
        case 'k':
            editor.setTileNum( Messages.getString("Editor.40", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'l':
            if ( modifiers.equals( kALT ) ) loadState();
            break;
        case 'L':
            editor.setTileNum( Messages.getString("EditorHelp.wall", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'm':
            if ( modifiers.equals( kALT ) ) JOptionPane.showMessageDialog( this, board.getCode() );
            break;
        case 'n':
            if ( modifiers.equals( kALT ) ) JOptionPane.showMessageDialog( this, editor.getTileNames() );
            break;
        case 'o':
            if ( modifiers.equals( kALT ) )
                chooseObject();
            else
                editor.setTileNum( Messages.getString("Editor.42", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'O':
            if ( modifiers.equals( "Alt+Shift" ) ) editor.setCurrentObject( Messages.getString("Editor.44", "rpg.editor") ); //$NON-NLS-2$
            break;
        case 'p':
            if ( modifiers.equals( kALT ) )
                choosePerson();
            else
                editor.setTileNum( Messages.getString("Editor.45", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'P':
            if ( modifiers.equals( "Alt+Shift" ) ) editor.setCurrentPerson( "delete" );
            break;
        case 'q':
            break;
        case 'r':
            if ( modifiers.equals( kALT ) )
            {
                editor.flipRandomized();
                refreshStatus();
            }
            break;
        case 'R':
            if ( modifiers.indexOf( kALT ) > -1 ) reloadState();
        case 's':
            if ( modifiers.equals( kALT ) )
                saveState();
            else
                editor.setTileNum( Messages.getString("Editor.48", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'S':
            editor.setTileNum( Messages.getString("Editor.49", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 't':
            if ( modifiers.equals( kALT ) )
            {
                String str = JOptionPane.showInputDialog( Messages.getString("Editor.50", "rpg.editor") ); //$NON-NLS-1$
                editor.setTileNum( str );
            } else
                editor.setTileNum( Messages.getString("Editor.51", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'u':
            break;
        case 'v':
            break;
        case 'w':
            editor.setTileNum( Messages.getString("Editor.52", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'W':
            editor.setTileNum( Messages.getString("Editor.53", "rpg.editor") ); //$NON-NLS-1$
            break;
        case 'x':
            break;
        case 'y':
            break;
        case 'z':
            break;
        case '-':
            break;
        case ' ': //space -- show location on big map.
            if ( boardMaker == null )
                boardMaker = new BoardMakerWizard( board.getCode() );
            else
                boardMaker.setCode( board.getCode() );
            boardMaker.show();
            break;
        case '1':
            if ( modifiers.equals( kALT ) )
            {
                editor.setBlockSize( 1 );
                refreshStatus();
            } else
                move( '1' );
            break;
        case '2':
            if ( modifiers.equals( kALT ) )
            {
                editor.setBlockSize( 2 );
                refreshStatus();
            } else
                move( 'd' );
            break;
        case '3':
            if ( modifiers.equals( kALT ) )
            {
                editor.setBlockSize( 3 );
                refreshStatus();
            } else
                move( '3' );
            break;
        case '4':
            if ( modifiers.equals( kALT ) )
            {
                editor.setBlockSize( 4 );
                refreshStatus();
            } else
                move( 'l' );
            break;
        case '5':
            if ( modifiers.equals( kALT ) )
            {
                editor.setBlockSize( 5 );
                refreshStatus();
            }
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
        default:
            System.out.print( c );
        }
    }

    public void move( char c )
    {
        map.move( c );
        this.repaint();
    }

    /** Overides {@link JFrame#repaint()}. */
    public void repaint()
    {

        refreshStatus();
        super.repaint();
    }

    public void refreshStatus()
    {
        lblRandom.setEnabled( editor.getRandomized() );
        lblAuto.setEnabled( board.getAutoSave() );
        lblPosition.setText( "(" + map.getPosX() + ", " + map.getPosY() + ")" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        lblCode.setText( board.getCode() );
        lblSize.setText( Messages.getString("Editor.57", "rpg.editor") + editor.getBlockSize() ); //$NON-NLS-1$
    }

    /**
     * Starts the program.
     */
    public static void main( String[] args )
    {
        EditorMain jm = new EditorMain();
        jm.show();
    }
}
