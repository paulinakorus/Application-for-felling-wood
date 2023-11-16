package org.example.service;


import org.example.model.Tree;

import javax.swing.table.AbstractTableModel;
import java.util.Comparator;
import java.util.List;

public class Table extends AbstractTableModel {
    private final String[] COLUMNS = new String[]{"Id", "Name", "Diameter"};
    private List<Tree> treeList;

    public Table(List <Tree> treeList){
        this.treeList = treeList.stream().sorted(Comparator.comparing(c -> c.getId_tree())).toList();
    }

    @Override
    public int getRowCount() {
        return treeList.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tree tree = treeList.get(rowIndex);
        return switch (columnIndex){
            case 0 -> tree.getId_tree();
            case 1 -> tree.getName();
            case 2 -> String.format("%.2f", tree.getDiameter());
            default -> "-";
        };
    }
    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }
}
