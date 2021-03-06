/*
 * Created on May 8, 2005 by Adam.
 *  
 */
package net.crystalrudiments.randomword;

import java.awt.HeadlessException;

import javax.swing.JApplet;

/**
 * RandomWordApplet
 * 
 * @author <A HREF="http://www.adamldavis.com">Adam L. Davis</A>
 * @version 1.0, May 8, 2005
 */
public class RandomWordApplet extends JApplet {

    private java.awt.Button button1;

    private java.awt.Button button2;

    private java.awt.List list1;

    private javax.swing.JSlider jSlider1;

    private RandomWord r = new RandomWord();

    /**
     * @throws java.awt.HeadlessException
     */
    public RandomWordApplet() throws HeadlessException {
        super();
        initComponents();
    }

    private void clearList(java.awt.event.ActionEvent evt) {
        list1.clear();
    }

    private void makeWord(java.awt.event.ActionEvent evt) {
        String word = r.makeRandomWord(jSlider1.getValue());
        list1.add(word);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the FormEditor.
     */
    private void initComponents() {
        button1 = new java.awt.Button();
        button2 = new java.awt.Button();
        list1 = new java.awt.List();
        jSlider1 = new javax.swing.JSlider();
        setBackground(new java.awt.Color(37, 218, 37));

        button1.setFont(new java.awt.Font("Dialog", 0, 11));
        button1.setLabel("Make Word");
        button1.setBackground(java.awt.Color.lightGray);
        button1.setForeground(java.awt.Color.black);
        button1.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeWord(evt);
            }
        });

        getContentPane().add(button1, java.awt.BorderLayout.WEST);

        button2.setFont(new java.awt.Font("Dialog", 0, 11));
        button2.setLabel("Clear List");
        button2.setBackground(java.awt.Color.lightGray);
        button2.setForeground(java.awt.Color.black);
        button2.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearList(evt);
            }
        });

        getContentPane().add(button2, java.awt.BorderLayout.EAST);

        list1.setFont(new java.awt.Font("Dialog", 0, 11));
        list1.setBackground(java.awt.Color.white);
        list1.setForeground(java.awt.Color.black);

        getContentPane().add(list1, java.awt.BorderLayout.CENTER);

        jSlider1.setSnapToTicks(true);
        jSlider1.setBorder(new javax.swing.border.BevelBorder(0));
        jSlider1.setMinorTickSpacing(1);
        jSlider1.setPaintTicks(true);
        jSlider1.setMinimum(3);
        jSlider1.setMajorTickSpacing(5);
        jSlider1.setMaximum(10);
        jSlider1.setValue(6);

        getContentPane().add(jSlider1, java.awt.BorderLayout.SOUTH);

    }

}