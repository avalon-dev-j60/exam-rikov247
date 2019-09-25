package tests.jBro;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import org.quinto.swing.table.view.JBroTable;

/**
 * Класс определяющий отображение ячеек таблицы
 */
public class CellRenderer extends JTextField implements TableCellRenderer {

    private int horAlignment;
    private DefaultTableCellRenderer renderer;
    private JLabel label;

    // Консруктор, который принимает выравнивание в ячейке
    public CellRenderer(JBroTable table, int horizontalAlignment) {
        renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        horAlignment = horizontalAlignment;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = renderer.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);

        label = (JLabel) component; // Текстовое поле в котором будет происходить отображение значений
        label.setHorizontalAlignment(horAlignment); // выравнивание текста в ячейке

        label.setBackground(UIManager.getColor("Table.background"));
        if (row == 0) {
            label.setBackground(new Color(30, 30, 200, 80));
            label.setOpaque(true);
        }
        for (int i = row; i > 0 && i < 6; i++) {
            label.setBackground(new Color(255, 128, 0, 80));
            label.setOpaque(true);
        }
        for (int i = row; i > 5 && i < 12; i++) {
            label.setBackground(new Color(255, 203, 209, 80));
            label.setOpaque(true);
        }
        for (int i = row; i > 11 && i < 17; i++) {
            label.setBackground(new Color(127, 255, 0, 80));
            label.setOpaque(true);
        }
        if (row == 17) {
            label.setBackground(new Color(0, 191, 255, 80));
            label.setOpaque(true);
        }

        // Действия при выборе ячейки (или выделении многих
        if (isSelected) {
            label.setOpaque(true);
            // Окраска поверх ячейки - если выбрана, то показываем цвет выбора в данном UI, если нет, то показываем цвет стандартный для ячейки
            label.setForeground(isSelected
                    ? UIManager.getColor("Table.selectionForeground")
                    : UIManager.getColor("Table.foreground"));
            // Окраска заднего фона
            label.setBackground(isSelected
                    ? UIManager.getColor("Table.selectionBackground")
                    : UIManager.getColor("Table.background"));
            // если ячейка имеет фокус, то показываем границу вокруг ячейки, если не имеет - пустая граница
            label.setBorder(hasFocus
                    ? BorderFactory.createLineBorder(UIManager.getColor("Table.selectionForeground"), 1)
                    : BorderFactory.createEmptyBorder(2, 2, 2, 2));
        }
        return label;
    }

    public void setHorizontalAlignment() {
        label.setHorizontalAlignment(horAlignment); // выравнивание текста в ячейке
    }

    public void setBackgroundRow(int row) {
        label.setBackground(UIManager.getColor("Table.background"));
        if (row == 0) {
            label.setBackground(new Color(30, 30, 200, 80));
            label.setOpaque(true);
        }
        for (int i = row; i > 0 && i < 6; i++) {
            label.setBackground(new Color(255, 128, 0, 80));
            label.setOpaque(true);
        }
        for (int i = row; i > 5 && i < 12; i++) {
            label.setBackground(new Color(255, 203, 209, 80));
            label.setOpaque(true);
        }
        for (int i = row; i > 11 && i < 17; i++) {
            label.setBackground(new Color(127, 255, 0, 80));
            label.setOpaque(true);
        }
        if (row == 17) {
            label.setBackground(new Color(0, 191, 255, 80));
            label.setOpaque(true);
        }
    }

}
