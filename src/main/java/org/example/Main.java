package org.example;

import org.example.model.Registration;
import org.example.model.Report;
import org.example.service.KierownikApp;
import org.example.service.KontrolerApp;
import org.example.service.MainForm;
import org.example.service.ObywatelApp;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        /*String[] name = {"Paulina", "Korus"};
        ObywatelApp obywatel = new ObywatelApp(1, name);
        obywatel.newRegistration();
        //obywatel.newRegistration();
        List<Registration> regList = obywatel.readRegistration();
        //System.out.println(regList);

        String[] name1 = {"Natalia", "Polak"};
        //ObywatelApp obywatel2 = new ObywatelApp(2, name1);
        //obywatel2.newRegistration();

        KierownikApp kierownik = new KierownikApp(1, name1);
        KontrolerApp kontroler = new KontrolerApp(1, name);
        kontroler.newReport(regList.get(0));
        List<Report> reportList = kierownik.readReports();
        kierownik.decision(reportList.get(0));

        //kontroler.newReport();*/
        MainForm mainForm = new MainForm();
    }
}