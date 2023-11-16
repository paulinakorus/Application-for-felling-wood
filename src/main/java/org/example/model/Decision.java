package org.example.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Decision {
    private String nameFile = "decision.txt";
    private int id_decision;
    private int id_registration;
    private int id_kierownika;
    private String description;

    public Decision(){
        this(0, 0, 0, null);
    }

    public Decision(int id_decision, int id_registration, int id_kierownika, String description){
        this.id_decision = id_decision;
        this.id_registration = id_registration;
        this.id_kierownika = id_kierownika;
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
            writer.write(this.id_decision + "," + this.id_registration + "," + this.id_kierownika + "," +  this.description + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
