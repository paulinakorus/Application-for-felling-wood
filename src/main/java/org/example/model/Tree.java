package org.example.model;

public class Tree {
    private int id_tree;
    private String name;
    private double diameter;

    public Tree(){
        this(0, null, 0.0);
    }

    public Tree(int id_tree, String name, double diameter){
        this.id_tree = id_tree;
        this.name = name;
        this.diameter = diameter;
    }

    public int getId_tree() {
        return id_tree;
    }

    public void setId_tree(int id_tree) {
        this.id_tree = id_tree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }
}
