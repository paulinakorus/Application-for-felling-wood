package org.example.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Registration {
    private String nameFile = "registrations.txt";
    //private PrintWriter inputFile;
    //private static File file;
    private int id_registration;
    private int id_obywatela;
    private List<Tree> treeList = new ArrayList<>();
    private String status;
    private String date;

    public Registration(){
        this(0, 0, null, null, null);
    }

    public Registration(int id_registration, int id_obywatela, List<Tree> treeList, String status, String date){
        this.id_registration = id_registration;
        this.id_obywatela = id_obywatela;
        this.treeList = treeList;
        this.status = status;
        this.date = date;
    }

    public void insertTree(String name, double diameter){
        int id_tree;
        if (treeList != null)
            id_tree = this.treeList.size()+1;
        else
            id_tree = 1;
        Tree oneTree = new Tree(id_tree, name, diameter);
        this.treeList.add(oneTree);
    }

    public void createFile(String status, Boolean addToExisting) throws IOException {
        File file = new File(this.nameFile);
        if(!file.exists()) {
            file.createNewFile();
            insertFile(status, addToExisting);
        }else{
            insertFile(status, addToExisting);
        }
    }

    public void insertFile(String status, Boolean addToExisting){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nameFile, addToExisting))) {
            writer.write(id_registration + "," + id_obywatela + ",");
            for (int i = 0; i < this.treeList.size(); i++) {
                writer.write(this.treeList.get(i).getId_tree() + ";" + this.treeList.get(i).getName() + ";" + this.treeList.get(i).getDiameter());
                if(i != this.treeList.size()-1)
                    writer.write(":");
            }
            this.status = status;
            writer.write("," + status);
            writer.write("," + this.date + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertNewFile(String status){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nameFile, true))) {
            writer.write(id_registration + "," + id_obywatela + ",");
            for (int i = 0; i < this.treeList.size(); i++) {
                writer.write(this.treeList.get(i).getId_tree() + ";" + this.treeList.get(i).getName() + ";" + this.treeList.get(i).getDiameter());
                if(i != this.treeList.size()-1)
                    writer.write(":");
            }
            this.status = status;
            writer.write("," + status);
            writer.write("," + this.date + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getId_registration() {
        return id_registration;
    }

    public void setId_registration(int id_registration) {
        this.id_registration = id_registration;
    }

    public int getId_obywatela() {
        return id_obywatela;
    }

    public void setId_obywatela(int id_obywatela) {
        this.id_obywatela = id_obywatela;
    }

    public List<Tree> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<Tree> treeList) {
        this.treeList = treeList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
