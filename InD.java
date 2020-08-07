
/**
 * The InD or Input Dialog class. This class is used to create dialog boxes that allow the user to input dialog, allowing the program to take in values for certain tasks
 * This code was given by Bill Viggers
 */
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.Random;
import java.awt.event.*;
import java.util.Scanner; 
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class InD extends JDialog{
    private String remember;
    public InD(String question){
        super(new JFrame(question), question);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.setMinimumSize(new Dimension(question.length()*7,100));
        JTextField reply = new JTextField();
        JButton clickMe = new JButton("press here, ya dummy!");
        clickMe.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    remember = reply.getText();
                    close();
                }
            }
        );
        this.setLayout(new GridLayout(2, 1, 5, 5));
        this.add(reply);
        this.add(clickMe);
        this.pack();
        setModal(true);
    }

    private void close(){
        this.dispose();
    }

    public String getText(){
        return remember;
    }

    
}