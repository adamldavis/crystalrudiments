
package net.crystalrudiments.alife;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Main class of program <i>ArtLife</i>
 * <p>
 * Art Life: ALife Creates pictures using random equations and uses
 * artificial life to reproduce and mutate these images based on user input.
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam Davis </A>
 * @version Version 1.2, May 15, 2005
 */
public class ALife extends JFrame implements LifeConstants
{

    /** bDEBUG = true if debugging */
    private static final boolean bDEBUG = false;

    /** If the main panel and pics panel are double buffered or not. */
    private static final boolean DOUBLE_BUFFERED = false;

    private JPanel grid; //Panel that contains pics.

    private ALifeView[] pics;

    private static final String TITLE = "ArtLife - 1.2";

    /** Event Handler */
    private ALifeControl eventHandler;

    /** MenuBar for starting a new game with level selection. */
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

    /**
     * Default constructor.
     */
    public ALife()
    {
        super( TITLE );

        this.addWindowListener( new WindowAdapter()
        {

            public void windowClosing( WindowEvent e )
            {
                dispose();
                System.exit( 0 );
            }
        } );

        JPanel content = new JPanel( DOUBLE_BUFFERED );
        //this.setContentPane(content);
        this.getContentPane().add( content );

        content.setLayout( new BorderLayout() );

        content.add( makePicsFrame() );
        super.setMenuBar( makeMenuBar() );

        makeEventHandler();

        this.repaint();
    }

    private void makeEventHandler()
    {
        eventHandler = new ALifeControl( this );

        mnuInstruct.addActionListener( eventHandler ); //Instructions

        mnuNew.addActionListener( eventHandler ); //New
        mnuSave.addActionListener( eventHandler ); //Save
        mnuLoad.addActionListener( eventHandler ); //Load
        mnuExit.addActionListener( eventHandler ); //Exit
    }

    private MenuBar makeMenuBar()
    {
        menuBar = new MenuBar();
        mnuGame = new Menu( MENU_GAME ); //init menu
        mnuHelp = new Menu( MENU_HELP );
        mnuInstruct = new MenuItem( MENU_INST );
        mnuNew = new MenuItem( MENU_NEW );
        mnuSave = new MenuItem( MENU_SAVE );
        mnuLoad = new MenuItem( MENU_LOAD );
        mnuExit = new MenuItem( MENU_EXIT );

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
     * Initiates array of viewers (pics[]).
     */
    public void initPics()
    {
        pics = new ALifeView[NUM_PICS];
        for ( int i = 0; i < NUM_PICS; i++ )
        {
            pics[i] = new ALifeView();
        }//i
    }

    /**
     * RE-Initiates array of viewers (pics[]).
     */
    public void reinitPics()
    {
        for ( int i = 0; i < NUM_PICS; i++ )
        {
            pics[i].resetModel();
        }//i
    }

    private JPanel makePicsFrame()
    {
        grid = new JPanel( DOUBLE_BUFFERED );

        grid.setLayout( new GridLayout( 3, 3, 10, 10 ) );

        initPics();

        for ( int i = 0; i < NUM_PICS; i++ )
        {
            grid.add( pics[i] );
            pics[i].addMouseListener( new MouseAdapter()
            {
                public void mouseClicked( MouseEvent evt )
                { //mousePressed
                    picsClicked( evt );
                }
            } );
        }//i
        return grid;

    }

    /**
     * Called when a picture is clicked.
     * 
     * @param evt
     *            The event.
     */
    public void picsClicked( MouseEvent evt )
    {
        ILifeModel mother = null;

        if ( bDEBUG )
        {
            System.out.print( "in picsClicked: " );
            System.out.println( evt.paramString() );
            System.out.println( evt.getModifiers() );
        }
        //get the model of the picture clicked.
        try
        {
            mother = ( ( ILifeView ) evt.getComponent() ).getModel();
        } catch ( Exception e )
        {
            System.out.println( "Oh mother: " + e );
            System.exit( 0 );
        }

        if ( evt.getModifiers() > 10 )
        { //the event was a Left-Click.
            makeBabies( mother );
            this.repaint();
        } else
        { //the event was a Right-Click.
            showBigger( mother );
        }//if
    }

    /**
     * Reproduce the input and then display the babies.
     */
    private void makeBabies( ILifeModel mother )
    {
        int n = 0;
        int i = 0;
        int m = 0;

        //make upper-left corner = mother.
        pics[0].setModel( mother );

        for ( n = 1; n < NUM_PICS; n++ )
        {
            if ( bDEBUG ) System.out.println( "in picsClicked: for n" );
            ILifeModel child = null;
            boolean found = false;

            //ensure unique child -- search for previous instances of child.
            for ( i = 0; i < 10; i++ ) //this 'for' loop limits the # of
            // iterations.
            {
                found = false;
                child = mother.reproduce();
                for ( m = 0; m < n; m++ )
                {
                    if ( child.equals( pics[m].getModel() ) ) found = true;
                }
                if ( !found ) break;
            }//i

            pics[n].setModel( child );
        }//n
    }

    /**
     * Pops up a window with specific image blown up.
     */
    private void showBigger( ILifeModel model )
    {
        Frame window = new Frame( "Zoom In" );

        ALifeView pic = new ALifeView( model, false );
        window.add( pic );
        window.setSize( 640, 480 );
        window.setResizable( false );

        window.addWindowListener( new WindowAdapter()
        {

            public void windowClosing( WindowEvent e )
            {
                dispose();
            }
        } );
        window.show();
    }

    /**
     * Shows the Instructions.
     */
    public void showInstructions()
    {
        JFrame frameInst = new JFrame( MENU_INST );
        Container main = frameInst.getContentPane();

        JTextArea jText = new JTextArea( TEXT_INSTRUCT );

        //jText.setFont( new Font( "Courier", Font.PLAIN, 14 ) );
        jText.setEnabled( false );

        main.setLayout( new BorderLayout() );
        main.add( jText, BorderLayout.CENTER );

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frameInst.setLocation( (d.width - 300) / 2, (d.height - 200) / 2 );
        frameInst.setSize( 300, 200 );
        frameInst.show();
    }

    public void saveState()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(this);
        File file = chooser.getSelectedFile();
        if ( file == null ) return;
        if ( file.getName().indexOf( '.' ) < 0 )
        {
            file = new File( file.getAbsolutePath() + ".ser" );
        }
        try
        {
            if (!file.exists()) file.createNewFile();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeInt( pics.length );
            for ( int i = 0; i < pics.length; i++ )
            {
                out.writeObject( pics[i].getModel() );
            }
            out.flush();
            out.close();
        } 
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        } 
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        finally {
            this.repaint();
        }
    }

    public void loadState()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
        chooser.showOpenDialog(this);
        File file = chooser.getSelectedFile();
        if (file == null) return;
        try
        {
            if (!file.exists()) return;
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            int num = in.readInt();
            for ( int i = 0; i < num; i++ )
            {
                pics[i].setModel((ILifeModel) in.readObject());
            }
            in.close();
        } 
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        } 
        catch ( IOException e )
        {
            e.printStackTrace();
        } 
        catch ( ClassNotFoundException e )
        {
            e.printStackTrace();
        }
        finally {
            this.repaint();
        }
    }

    /*-----------------------------------------------------------------------*/
    /**
     * Accessor for pics used by ALifeControl.
     */
    public ILifeModel[] getModels()
    {
        ILifeModel[] models = new ILifeModel[pics.length];

        for ( int i = 0; i < models.length; i++ )
        {
            models[i] = pics[i].getModel();
        }
        return models;

    }

    /**
     * Modifier for pics used by ALifeControl.
     */
    public void setModels( ILifeModel[] models )
    {
        for ( int i = 0; i < NUM_PICS; i++ )
        {
            pics[i].setModel( models[i] );
        }
    }

    /*-----------------------------------------------------------------------*/
    /**
     * 
     * main.
     *  
     */
    public static void main( String args[] )
    {
        ALife mainFrame = new ALife();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation( (d.width - 400) / 2, (d.height - 400) / 2 );
        mainFrame.setSize( 400, 400 );
        mainFrame.setVisible( true );
    }

}
