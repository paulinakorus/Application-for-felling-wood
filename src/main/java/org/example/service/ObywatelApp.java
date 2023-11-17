package org.example.service;

import org.example.model.Registration;
import org.example.model.Tree;
import org.w3c.dom.ls.LSException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class ObywatelApp extends JFrame{
    private int id_obywatel;
    private String name;
    private static int registrationsNumber = 0;
    private String nameFile = "registrations.txt";
    private static int obywatelNumber = 0;

    //GUI
    private JPanel obywatelPanel;
    private JTextField nameTreeField;
    private JTextField diameterTreeField;
    private JButton insertTreeB;
    private JButton insertRegistrationB;
    private JButton previousButton;
    private JButton nextButton;
    private JLabel id_obywatela1L;
    private JLabel id_registration1L;
    private JLabel treesLabel;
    private JLabel nameTreeL;
    private JLabel diameterLabel;
    private JLabel newRegLabel;
    private JLabel previousRegistrationL;
    private JButton delateButton;
    private JButton editButton;
    private JLabel id_registration2L;
    private JLabel id_obywatela2L;
    private JLabel statusL;
    private JLabel dateL;
    private JLabel trees2Label;
    private JTable table;
    private JLabel obywatelLabel;
    private JButton readButton;
    private Table treeTableModel;
    private Registration currentRegistration;
    private List<Registration> currentRegistrationList;
    private int currentRegistrationID = 0;
    public ObywatelApp() throws IOException {
        this(null);
    }
    public ObywatelApp(String name) throws IOException {
        this.id_obywatel = ++obywatelNumber;
        this.name = name;

        this.setTitle("ObywatelApp");                                  // set title of frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           // exit out off application
        this.setResizable(false);                                      // preventing frame from being resized
        this.setSize(700, 500);                            // setting size
        this.setVisible(true);                                         // making frame visible
        this.add(obywatelPanel);

        setUpButtons();
        obywatelLabel.setText(String.format("Obywatel nr " + this.id_obywatel + " : " + this.name));
        newRegistration();
        id_registration2L.setText(String.format("Registration ID: -"));
        id_obywatela2L.setText(String.format("Obywatel ID: -"));
        statusL.setText("Status: -");
        dateL.setText("Date: -");
    }
    public void writeRegistration(List<Registration> registrationList) throws IOException {
        File file = new File(this.nameFile);
        file.createNewFile();

        for (int i=0; i<registrationList.size(); i++){
            Registration registration = registrationList.get(i);
            if(i==0)
                registration.createFile(registration.getStatus(), false);
            else
                registration.createFile(registration.getStatus(), true);
        }
    }
    private void setUpButtons(){
        insertTreeB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == insertTreeB){
                    currentRegistration.insertTree(nameTreeField.getText(), Double.parseDouble(diameterTreeField.getText()));
                }
            }
        });
        insertRegistrationB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == insertRegistrationB){
                    try {
                        if (currentRegistration.getTreeList() != null /*&& !existRegistration(currentRegistration)*/)
                            currentRegistration.createFile("submissioned", true);
                        newRegistration();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == readButton){
                    try {
                        currentRegistrationList = readRegistration();
                        currentRegistrationID = 0;
                        uploadRegistration(0);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == previousButton){
                    if(currentRegistrationID > 0){
                        try {
                            uploadRegistration(--currentRegistrationID);
                            System.out.println(currentRegistrationID);
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == nextButton){
                    if(currentRegistrationID < currentRegistrationList.size()-1){
                        try {
                            uploadRegistration(++currentRegistrationID);
                            System.out.println(currentRegistrationID);
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
    }

    public void uploadRegistration(int id) throws FileNotFoundException {
        currentRegistrationList = readRegistration();

        Registration registration = currentRegistrationList.get(id);
        id_registration2L.setText(String.format("Registration ID: " + registration.getId_registration()));
        id_obywatela2L.setText(String.format("Obywatel ID: " + this.id_obywatel));
        statusL.setText("Status: " + registration.getStatus());
        dateL.setText("Date: " + registration.getDate());
        setUpTable(registration);
    }

    public void newRegistration() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        List<Tree> treeList = new ArrayList<>();
        String status = null;
        currentRegistration = new Registration(++registrationsNumber, this.id_obywatel, treeList, status, date);
        setUpLabels();
    }

    private void setUpLabels(){
        id_obywatela1L.setText(String.format("Obywatel ID: " + this.id_obywatel));
        id_registration1L.setText(String.format("Registration ID: " + registrationsNumber));
    }

    public Boolean existRegistration(Registration newRegistration) throws FileNotFoundException {
        List<Registration> registrationList = readRegistration();
        for (Registration registration: registrationList){
            if(registration.getId_registration() == newRegistration.getId_registration())
                return true;
        }
        return false;
    }

    public List<Registration> readRegistration() throws FileNotFoundException {
        final Scanner reader = new Scanner(new File(this.nameFile));
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

    public void delateRegistration(Registration registration) throws IOException {
        List<Registration> registrationList = readRegistration();
        registrationList.remove(registration);
        writeRegistration(registrationList);
    }

    private void setUpTable(Registration registration){
        treeTableModel = new Table(registration.getTreeList());
        table.setModel(treeTableModel);
        table.setAutoCreateRowSorter(false);
    }

    //public void editRegistration(){};
}
