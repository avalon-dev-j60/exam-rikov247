package tests.jBroTable;

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
public class CellRenderer extends JLabel implements TableCellRenderer {

    private int horizontalAlignment = JLabel.CENTER;
    private DefaultTableCellRenderer renderer;
    private JLabel label = new JLabel(" ");

    // Консруктор, который принимает выравнивание в ячейке
    public CellRenderer(JBroTable table) {
        renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        label = (JLabel) component; // Текстовое поле в котором будет происходить отображение значений
        label.setHorizontalAlignment(horizontalAlignment); // выравнивание текста в ячейке СТАНДАРТНОЕ

        setBackgroundRow(row); // устанавливаем закраску строк СТАНДАРТНУЮ

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

    public void setBackgroundRow(int row) {
        Color firstColor = new Color(0, 0, 200, 80); // синий
        Color secondColor = new Color(255, 128, 0, 80); // красный
        Color thirdColor = new Color(255, 203, 209, 80); // розовый
        Color fourthColor = new Color(127, 255, 0, 80); // салатовый
        Color fifthColor = new Color(0, 191, 255, 80); // бирюзовый
        
        if (row == 0) {
            label.setBackground(firstColor);
            label.setOpaque(true);
        }
        for (int i = row; i > 0 && i < 6; i++) {
            label.setBackground(secondColor);
            label.setOpaque(true);
        }
        for (int i = row; i > 5 && i < 12; i++) {
            label.setBackground(thirdColor);
            label.setOpaque(true);
        }
        for (int i = row; i > 11 && i < 17; i++) {
            label.setBackground(fourthColor);
            label.setOpaque(true);
        }
        if (row == 17) {
            label.setBackground(fifthColor);
            label.setOpaque(true);
        }
    }

}
