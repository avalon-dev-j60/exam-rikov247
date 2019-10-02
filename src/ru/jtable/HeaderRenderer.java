package ru.jtable;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * Класс определяющий отображение заголовка таблицы (хэдера)
 */
public class HeaderRenderer implements TableCellRenderer {

    private int horizontalAlignment = JLabel.CENTER;
    private DefaultTableCellRenderer renderer;

    public HeaderRenderer(JTable table) {
        renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        JLabel label = (JLabel) component;
        label.setHorizontalAlignment(horizontalAlignment);
        return label;
    }

    public void setHorizontalAlignment(int horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment; // передаем выравнивание для ячеек
    }
}
