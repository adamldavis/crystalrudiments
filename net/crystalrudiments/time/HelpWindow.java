package net.crystalrudiments.time;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * The window used for composing email messages and sending them much like most
 * email client programs.
 * @version 1.0
 */

public class HelpWindow extends JFrame {

    BorderLayout paneBorderLayout = new BorderLayout();

    //JPanel txtAreaPane = new JPanel();
    JScrollPane txtAreaScrollPane = new JScrollPane();

    JTextArea txtaBodyText = new JTextArea();

    JButton jOKButton = new JButton();

    JEditorPane editorPane;

    public String getHelp() {
        return txtaBodyText.getText();
    }

    public void setHelp(String s) {
        txtaBodyText.setText(s);
    }

    public HelpWindow(ActionListener aController) {
        try {
            jbInit(aController);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit(ActionListener aController) throws Exception {
        //Set the main layout
        this.getContentPane().setLayout(paneBorderLayout);
        URL helpURL = null;
        //Set up the Body Text Box
        txtaBodyText.setLineWrap(true);
        txtaBodyText.setWrapStyleWord(true);
        //txtAreaPane.setLayout(txtAreaGridLayout);

        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        //create a URL object for the TextSamplerDemoHelp.html file...
        String s = null;
        try {
            s = "file:" + System.getProperty("user.dir")
                    + System.getProperty("file.separator") + "Help.html";
            helpURL = new URL(s);
            /* ... use the URL to initialize the editor pane ... */
        } catch (Exception e) {
            System.err.println("Couldn't create help URL: " + s);
        }

        try {
            editorPane.setPage(helpURL);
        } catch (IOException e) {
            System.err.println("Attempted to read a bad URL: " + helpURL);
        }

        //Set up the SEND button
        jOKButton.setActionCommand("HelpOK");
        jOKButton.setText("OK");
        jOKButton.addActionListener(aController);

        //Add the scroll pane to the textbox
        //txtAreaPane.add(txtAreaScrollPane, null);
        txtAreaScrollPane.getViewport().add(editorPane, null);

        //Position the labels and text boxes
        this.getContentPane().add(txtAreaScrollPane, BorderLayout.CENTER);
        //this.getContentPane().add(editorPane, BorderLayout.CENTER);
        this.getContentPane().add(jOKButton, BorderLayout.SOUTH);

        //Add the panels to the top and the Center of the screen
        //this.getContentPane().add(actionPanel, BorderLayout.NORTH);
        //this.getContentPane().add(txtAreaPane, BorderLayout.CENTER);
        this.setSize(new Dimension(700, 700));

        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Load help file
        //this.setHelp(inputHelp());
        //   System.out.println("YOLANDA");
        // Determine the new location of the window
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        // Move the window
        this.setLocation(x, y);
    }

    public static void main(String[] args) {
        HelpWindow aWindow = new HelpWindow(null);
        aWindow.show();
    }//end of main
}