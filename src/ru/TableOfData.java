package ru;

import java.awt.FlowLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import org.quinto.swing.table.model.IModelFieldGroup;
import org.quinto.swing.table.model.ModelData;
import org.quinto.swing.table.model.ModelField;
import org.quinto.swing.table.model.ModelFieldGroup;
import org.quinto.swing.table.model.ModelRow;
import org.quinto.swing.table.view.JBroTable;

public class TableOfData extends JBroTable {

    private JBroTable table = new JBroTable();

    public TableOfData() throws Exception {
        // Hierarchically create columns and column groups.
        // Each node of columns tree is an instance of IModelFieldGroup.
        // Leafs are always ModelFields.
        // Roots can be either ModelFields or ModelFieldGroups.
        IModelFieldGroup groups[] = new IModelFieldGroup[]{
            new ModelFieldGroup("A", "A")
            .withChild(new ModelField("B", "B"))
            .withChild(new ModelField("C", "C").withRowspan(2)), // Установка дополнительного количества строк в объединение к разделенной области
            new ModelFieldGroup("D", "D")
            .withChild(new ModelField("E", "E"))
            .withChild(new ModelField("F", "F")),
            new ModelField("G", "G"),
            new ModelFieldGroup("H", "H")
            .withChild(new ModelFieldGroup("I", "I")
            .withChild(new ModelField("J", "J")))
            .withChild(new ModelField("K", "K"))
            .withChild(new ModelFieldGroup("L", "L")
            .withChild(new ModelField("M", "M"))
            .withChild(new ModelField("N", "N")))
        };

        // Get leafs of columns tree.
        ModelField fields[] = ModelFieldGroup.getBottomFields(groups);

        // Sample data.
        ModelRow rows[] = new ModelRow[10];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new ModelRow(fields.length);
            for (int j = 0; j < fields.length; j++) {
                rows[i].setValue(j, fields[j].getCaption() + i);
            }
        }

        // Table.
        ModelData data = new ModelData(groups);
        data.setRows(rows);
        table.setData(data);
        System.out.println(table.isEditing());
        table.setEditingColumn(0);
        table.setEditingRow(0);
    }

    public JTable getTable() {
        return table;
    }

    public JScrollPane getTableInScrollPane() {
        return table.getScrollPane();
    }
    
}
