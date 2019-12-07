package ru.settings;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import org.quinto.swing.table.view.JBroTable;

/**
 * Класс определяющий отображение ячеек таблицы
 */
public class CellRendererHotButtonTable extends JLabel implements TableCellRenderer {

    private int horizontalAlignment = JLabel.CENTER; // выравнивание текста в ячейках
    private DefaultTableCellRenderer renderer;
    private JLabel label = new JLabel(" ");

    // Консруктор, который принимает выравнивание в ячейке
    public CellRendererHotButtonTable(JBroTable table) {
        renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        label = (JLabel) component; // Текстовое поле в котором будет происходить отображение значений
        label.setHorizontalAlignment(horizontalAlignment); // выравнивание текста в ячейке СТАНДАРТНОЕ
        if (row == 0) {
            label.setHorizontalAlignment(JLabel.LEFT); 
        }

        // Установка закраски строк СТАНДАРТНУЮ
        setBackgroundRow((JBroTable) table, row);

        // Установка жирного шрифта для требуемых ячеек
        for (int i = 0; i < ((JBroTable) table).getModel().getData().getFieldsCount(); i++) {
            if (((JBroTable) table).getModel().getData().getFields()[i].getIdentifier().startsWith("Горячие клавиши")) {
                if (((JBroTable) table).convertColumnIndexToView(i) == column) {
                    label.setFont(new Font(null, Font.BOLD, 12));
                }
            }
        }
        if (row == 0) {
            label.setFont(new Font(null, Font.ITALIC, 12));
        }

        // Действия при выборе ячейки (или выделении многих)
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

    public void setHorizontalAlignment(int horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment; // передаем выравнивание для ячеек
    }

    // Установка цвета для строк
    public void setBackgroundRow(JBroTable table, int row) {
        Color firstColor = Color.white; // белый
        Color secondColor = Color.LIGHT_GRAY; // серый

        if (row % 2 == 0) {
            label.setBackground(firstColor);
            label.setOpaque(true);
        }
        if (row != 0 && row % 2 == 0.5) {
            label.setBackground(secondColor);
            label.setOpaque(true);
        }
    }

}
