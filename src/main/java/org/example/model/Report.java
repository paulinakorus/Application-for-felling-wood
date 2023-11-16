package org.example.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Report {

    private String nameFile = "reports.txt";
    private int id_report;
    private int id_registration;
    private String description;
    public Report(){
        this(0, 0, null);
    }

    public Report(int id_report, int id_registration, String description){
        this.id_report = id_report;
        this.id_registration = id_report;
        this.description = description;
    }

    public void createFile() throws IOException {
        File file = new File(this.nameFile);
        if(!file.exists()) {
            file.createNewFile();
            insertFile();
        }else{
            insertFile();
        }
    }

    public void insertFile(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nameFile, true))) {
            writer.write(this.id_report + "," + this.id_registration + "," + this.description + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public int getId_report() {
        return id_report;
    }

    public void setId_report(int id_report) {
        this.id_report = id_report;
    }

    public int getId_registration() {
        return id_registration;
    }

    public void setId_registration(int id_registration) {
        this.id_registration = id_registration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
