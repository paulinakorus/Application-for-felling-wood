package org.example.service;

import org.example.model.Registration;
import org.example.model.Report;
import org.example.model.Tree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class KontrolerApp extends JFrame {
    private int id;
    private String name;
    private String regFile = "registrations.txt";
    private String reportFile = "reports.txt";
    private static int reportsNumber = 1;
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
    private JLabel id_obywatelLabel;
    private JLabel statusLabel;
    private JLabel listTreeLabel;
    private JLabel descriptionLabel;
    private JLabel id_reportLabel;
    private JScrollPane listTree;
    private JButton readButton;
    private JLabel dateLabel;
    private JLabel registrationLabel2;
    private Table treeTableModel;
    private int currentRegistrationID = 0;
    private List<Registration> currentRegistrationList;
    private Report currentReport;

    public KontrolerApp() throws IOException {
        this(null);
    }
    public KontrolerApp (String name) throws IOException {
        this.id = ++kontrolerNumber;
        this.name = name;

        this.setTitle("KontrolerApp");                                  // set title of frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           // exit out off application
        this.setResizable(false);                                      // preventing frame from being resized
        this.setSize(700, 500);                            // setting size
        this.setVisible(true);                                         // making frame visible
        this.add(kontrolerPanel);

        kontrolerLabel.setText(String.format("Kontroler nr " + this.id + " : " + this.name));
        setUpButtons();
        id_reportLabel.setText(String.format("Report ID: -"));
        registrationLabel2.setText(String.format("Registration ID: -"));
        id_registrationLabel.setText(String.format("Registration ID: -"));
        id_obywatelLabel.setText(String.format("Obywatel ID: -"));
        statusLabel.setText("Status: -");
        dateLabel.setText("Date: -");
    }

    public void setUpButtons(){
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == readButton){
                    try {
                        currentRegistrationList = readRegistration();
                        currentRegistrationID = 0;
                        uploadRegistration(currentRegistrationID);
                        newReport();
                        //System.out.println(currentRegistrationList.get(0).getStatus());
                    } catch (IOException ex) {
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
                            currentReport.setId_registration((currentRegistrationList.get(currentRegistrationID).getId_registration()));
                            setUpReportLabels();
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
                            currentReport.setId_registration((currentRegistrationList.get(currentRegistrationID).getId_registration()));
                            setUpReportLabels();
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == sendButton){
                    try {
                        if(currentRegistrationList != null){
                            newReport();
                            currentReport.createFile();
                            ++reportsNumber;
                            currentRegistrationList.get(currentRegistrationID).setStatus("sended");
                            writeRegistration(currentRegistrationList);
                            currentRegistrationList = readRegistration();
                            currentRegistrationID = 0;
                            uploadRegistration(currentRegistrationID);
                            newReport();
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    public void uploadRegistration(int id) throws FileNotFoundException {
        Registration registration = currentRegistrationList.get(id);
        id_registrationLabel.setText(String.format("Registration ID: " + registration.getId_registration()));
        id_obywatelLabel.setText(String.format("Obywatel ID: " + registration.getId_obywatela()));
        statusLabel.setText("Status: " + registration.getStatus());
        dateLabel.setText("Date: " + registration.getDate());
        setUpTable(registration);
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
            if (Objects.equals(registration.getStatus(), "taken"))
                registrationList.add(registration);
        }
        return registrationList;
    }
    public void writeRegistration(List<Registration> registrationList) throws IOException {
        File file = new File(this.regFile);
        file.createNewFile();

        for (int i=0; i<registrationList.size(); i++){
            Registration registration = registrationList.get(i);
            if(i==0)
                registration.createFile(registration.getStatus(), false);
            else
                registration.createFile(registration.getStatus(), true);
        }
    }

    public void newReport() throws IOException {
        this.currentReport = new Report(this.reportsNumber, currentRegistrationList.get(currentRegistrationID).getId_registration(), descriptionField.getText());
        setUpReportLabels();
    }

    private void setUpReportLabels(){
        id_reportLabel.setText(String.format("Report ID: " + currentReport.getId_report()));
        registrationLabel2.setText(String.format("Registration ID: " + currentReport.getId_registration()));
    }

    private void setUpTable(Registration registration){
        treeTableModel = new Table(registration.getTreeList());
        table1.setModel(treeTableModel);
        table1.setAutoCreateRowSorter(false);
    }

}
