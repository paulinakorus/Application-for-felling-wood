package org.example.service;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame{
    private JButton obywatelButton;
    private JButton kierownikButton;
    private JButton kontrolerButton;
    private JLabel chooseLabel;
    private JTextField obywatelNameField;
    private JTextField kontrolerNameField;
    private JTextField kierownikNameField;
    private JLabel obywatelNameLabel;
    private JLabel kontrolerNameLabel;
    private JLabel kierownikNameLabel;
    private JPanel mainPanel;

    public MainForm(){
        this.setTitle("ObywatelApp");                                  // set title of frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           // exit out off application
        this.setResizable(false);                                      // preventing frame from being resized
        this.setSize(700, 500);                            // setting size
        this.setVisible(true);                                         // making frame visible
        this.add(mainPanel);

        obywatel();
        kontroler();
        kierownik();
    }

    private void obywatel(){
        obywatelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==obywatelButton){
                    ObywatelApp obywatel = new ObywatelApp(obywatelNameField.getText());
                }
            }
        });
    }

    private void kontroler(){
        kontrolerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==kontrolerButton){
                    ObywatelApp obywatel = new ObywatelApp(kontrolerNameField.getText());
                }
            }
        });
    }

    private void kierownik(){
        kierownikButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==kierownikButton){
                    ObywatelApp obywatel = new ObywatelApp(kierownikNameField.getText());
                }
            }
        });
    }
}
