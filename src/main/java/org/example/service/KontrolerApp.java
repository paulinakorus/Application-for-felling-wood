package org.example.service;

import org.example.model.Registration;
import org.example.model.Report;
import org.example.model.Tree;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class KontrolerApp extends JFrame {
    private int id;
    private String name;
    private String regFile = "registrations.txt";
    private String reportFile = "reports.txt";
    private static int reportsNumber = 0;
    private static int kontrolerNumber = 0;
    private JPanel kontrolerPanel;
    private JTable table1;
    private JTextField descriptionField;
    private JButton sendButton;
    private JButton nextButton;
    private JButton previousButton;
    private JLabel kontrolerLabel;
    private JLabel reportLabel;
    private JLabel registrationsLabel;
    private JLabel id_registrationLabel;
    private JLabel id_obywatel;
    private JLabel statusLabel;
    private JLabel listTreeLabel;
    private JLabel descriptionLabel;
    private JLabel id_reportLabel;
    private JScrollPane listTree;

    public KontrolerApp(){
        this(null);
    }
    public KontrolerApp (String name){
        this.id = ++kontrolerNumber;
        this.name = name;

        this.setTitle("KontrolerApp");                                  // set title of frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           // exit out off application
        this.setResizable(false);                                      // preventing frame from being resized
        this.setSize(700, 500);                            // setting size
        this.setVisible(true);                                         // making frame visible
        this.add(kontrolerPanel);
    }

    public List<Registration> readRegistration() throws FileNotFoundException {
        final Scanner reader = new Scanner(new File(this.regFile));
        List<Registration> registrationList = new ArrayList<>();

        String line;
        String[] regist;
        String[] trees;
        String[] tree;
        Registration registration;

        while (reader.hasNextLine()){
            List<Tree> treeList = new ArrayList<>();
            line = reader.nextLine();
            regist = line.split(",");
            trees = regist[2].split(":");

            for (String splitted : trees) {
                tree = splitted.split(";");
                treeList.add(new Tree(parseInt(tree[0]), tree[1], Double.parseDouble(tree[2])));
            }

            registration = new Registration(parseInt(regist[0]), parseInt(regist[1]), treeList, regist[3], regist[4]);
            registrationList.add(registration);
        }
        return registrationList;
    }

    public void newReport(Registration registration) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Podaj opis");
        System.out.print("  opis: ");
        String description = input.nextLine();
        Report newReport = new Report(++this.reportsNumber, registration.getId_registration(), description);
        newReport.createFile();
    }
}
