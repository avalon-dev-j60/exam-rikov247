package tests.jBro;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * Класс определяющий отображение заголовка таблицы (хэдера)
 */
public class HeaderRenderer implements TableCellRenderer {

    private int horAlignment;
    private DefaultTableCellRenderer renderer;

    public HeaderRenderer(JTable table, int horizontalAlignment) {
        horAlignment = horizontalAlignment;
        renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = renderer.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);
        JLabel label = (JLabel) component;
        label.setHorizontalAlignment(horAlignment);
        return label;
    }
}
