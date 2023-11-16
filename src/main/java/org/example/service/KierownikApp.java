package org.example.service;

import org.example.model.Decision;
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

public class KierownikApp extends JFrame{
    private int id;
    private String name;
    private String regFile = "registrations.txt";
    private String reportFile = "reports.txt";
    private static int decisionsNumber = 0;
    private static int kierownikNumber = 0;
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
    private JLabel descriptionLabel;
    private JLabel decisionLabel;

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

    public void sendToKontrolerApp(Registration acceptedReg) throws IOException {
        List<Registration> registrationList = readRegistration();
        for (Registration registration : registrationList){
            if (registration == acceptedReg)
                registration.createFile("taken", true);
            else
                registration.createFile("submissioned", true);
        }
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
            report = new Report(parseInt(rep[0]), parseInt(rep[1]), rep[2]);
            reportList.add(report);
        }
        return reportList;
    }

    public void decision(Report report) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Decyzja: ");
        Boolean dec = input.nextBoolean();

        List<Registration> registrationList = new ArrayList<>();
        registrationList = readRegistration();
        Registration registration = registrationList.get(report.getId_registration()-1);
        if(dec){
            //
            Scanner input1 = new Scanner(System.in);
            System.out.println("Podaj opis");
            System.out.print("  opis: ");
            String description = input1.nextLine();
            //

            Decision decision = new Decision(++decisionsNumber, registration.getId_registration(), this.id, description);
            decision.createFile();
            registration.setStatus("finished");
            writeRegistration(registrationList);
        }else{
            registration.setStatus("finished");
            writeRegistration(registrationList);
        }
    }


}
