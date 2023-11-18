package org.example.service;

import org.example.model.Decision;
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
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class KierownikApp extends JFrame{
    private int id;
    private String name;
    private String regFile = "registrations.txt";
    private String reportFile = "reports.txt";
    private static int decisionsNumber = 0;
    private static int kierownikNumber = 0;
    private List<Registration> currentRegistrationList;
    private int currentRegistrationID = 0;
    private List<Report> currentReportList;
    private int currentReportID = 0;
    private Table treeTableModel;

    // GUI
    private JPanel kierownikPanel;
    private JTable table;
    private JButton previousRegButton;
    private JButton readButton;
    private JButton nextRegButton;
    private JButton noApproveButton;
    private JButton approveButton;
    private JButton prevReportB;
    private JButton nextReportB;
    private JLabel kierownikLabel;
    private JLabel registrationLabel;
    private JLabel id_registrationLabel;
    private JLabel id_obywatelLabel;
    private JLabel statusLabel;
    private JLabel dateLabel;
    private JScrollPane ScrollPane;
    private JLabel treeListLabel;
    private JLabel id_reportLabel;
    private JLabel id_registrationLabel2;
    private JLabel decisionLabel;
    private JButton acceptButton;
    private JLabel id_obywatelLabel2;
    private JButton readReportsButton;
    private JLabel descriptionLabel;

    public KierownikApp(){
        this(null);
    }
    public KierownikApp(String name){
        this.id = ++kierownikNumber;
        this.name = name;

        this.setTitle("KierownikApp");                                  // set title of frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           // exit out off application
        this.setResizable(false);                                      // preventing frame from being resized
        this.setSize(700, 500);                            // setting size
        this.setVisible(true);                                         // making frame visible
        this.add(kierownikPanel);

        kierownikLabel.setText(String.format("Kierownik nr " + this.id + " : " + this.name));
        setUpButtons();
        id_registrationLabel.setText(String.format("Registration ID: -"));
        id_obywatelLabel.setText(String.format("Obywatel ID: -"));
        statusLabel.setText("Status: -");
        dateLabel.setText("Date: -");
        id_reportLabel.setText(String.format("Report ID: -"));
        id_obywatelLabel2.setText(String.format("Obywatel ID: -"));
        id_registrationLabel2.setText(String.format("Registration ID: -"));
        descriptionLabel.setText(String.format("Description: -"));
    }

    public void setUpButtons(){
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
        previousRegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == previousRegButton){
                    if(currentRegistrationID > 0){
                        try {
                            uploadRegistration(--currentRegistrationID);
                            //System.out.println(currentRegistrationID);
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });

        nextRegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == nextRegButton){
                    if(currentRegistrationID < currentRegistrationList.size()-1){
                        try {
                            uploadRegistration(++currentRegistrationID);
                            //System.out.println(currentRegistrationID);
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == acceptButton){
                    try {
                        sendToKontrolerApp(currentRegistrationList.get(currentRegistrationID));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        prevReportB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == prevReportB){
                    if(currentReportID > 0){
                        try {
                            uploadReport(--currentReportID);
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });

        nextReportB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == nextReportB){
                    if(currentReportID < currentReportList.size()-1){
                        try {
                            uploadReport(++currentReportID);
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });

        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(e.getSource() == approveButton){
                   Decision decision = new Decision(++decisionsNumber, currentReportList.get(currentReportID).getId_registration(), id, currentReportList.get(currentReportID).getDescription());
                   try {
                       decision.createFile();
                   } catch (IOException ex) {
                       throw new RuntimeException(ex);
                   }
                   setFinishedStatus();

                   try {
                       writeRegistration(currentRegistrationList);
                   } catch (IOException ex) {
                       throw new RuntimeException(ex);
                   }

                   currentReportList.remove(currentReportID);
                   try {
                       writeReport(currentReportList);
                   } catch (IOException ex) {
                       throw new RuntimeException(ex);
                   }
               }
            }
        });

        noApproveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == noApproveButton){
                    setFinishedStatus();
                    currentReportList.remove(currentReportID);
                    try {
                        writeReport(currentReportList);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        readReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == readReportsButton){
                    try {
                        currentReportList = readReports();
                        currentReportID = 0;
                        uploadReport(0);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

    }

    public void setFinishedStatus(){
        int registrationID = currentReportList.get(currentReportID).getId_registration();

        for (Registration registration : currentRegistrationList){
            if(registration.getId_registration() == registrationID){
                registration.setStatus("finished");
                break;
            }
        }
    }
    public void uploadRegistration(int id) throws FileNotFoundException {
        currentRegistrationList = readRegistration();

        Registration registration = currentRegistrationList.get(id);
        id_registrationLabel.setText(String.format("Registration ID: " + registration.getId_registration()));
        id_obywatelLabel.setText(String.format("Obywatel ID: " + registration.getId_obywatela()));
        statusLabel.setText("Status: " + registration.getStatus());
        dateLabel.setText("Date: " + registration.getDate());
        setUpTable(registration);
    }

    public void uploadReport(int id) throws FileNotFoundException {
        currentReportList = readReports();

        Report report = currentReportList.get(id);
        id_reportLabel.setText(String.format("Report ID: " + report.getId_report()));
        id_obywatelLabel2.setText(String.format("Obywatel ID: " + report.getId_obywatel()));
        id_registrationLabel2.setText(String.format("Registration ID: " + report.getId_registration()));
        descriptionLabel.setText(String.format("Description: " + report.getDescription()));
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

    public void writeReport(List<Report> reportList) throws IOException {
        File file = new File(this.reportFile);
        file.createNewFile();

        for (int i=0; i<reportList.size(); i++){
            Report report = reportList.get(i);
            if(i==0)
                report.createFile(false);
            else
                report.createFile( true);
        }
    }

    public void sendToKontrolerApp(Registration acceptedReg) throws IOException {
        for(int i=0; i<currentRegistrationList.size(); i++){
            if (currentRegistrationList.get(i) == acceptedReg)
                currentRegistrationList.get(i).setStatus("taken");
        }
        writeRegistration(currentRegistrationList);
    }

    public List<Report> readReports() throws FileNotFoundException {
        final Scanner reader = new Scanner(new File(this.reportFile));
        List<Report> reportList = new ArrayList<>();

        String line;
        String[] rep;
        Report report;

        while (reader.hasNextLine()){
            line = reader.nextLine();
            rep = line.split(",");
            report = new Report(parseInt(rep[0]), parseInt(rep[1]), parseInt(rep[2]), rep[3]);
            reportList.add(report);
        }
        return reportList;
    }

    private void setUpTable(Registration registration){
        treeTableModel = new Table(registration.getTreeList());
        table.setModel(treeTableModel);
        table.setAutoCreateRowSorter(false);
    }
}
