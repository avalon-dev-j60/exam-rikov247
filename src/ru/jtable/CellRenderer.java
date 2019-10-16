package ru.jtable;

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
public class CellRenderer extends JLabel implements TableCellRenderer {

    private int horizontalAlignment = JLabel.CENTER; // выравнивание текста в ячейках
    private DefaultTableCellRenderer renderer;
    private JLabel label = new JLabel(" ");

    private String typeOfStatement; // Тип таблицы (новый или старый)

    // Консруктор, который принимает выравнивание в ячейке
    public CellRenderer(JBroTable table, String typeOfStatement) {
        renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        this.typeOfStatement = typeOfStatement; // Тип таблицы (новый или старый)
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        label = (JLabel) component; // Текстовое поле в котором будет происходить отображение значений
        label.setHorizontalAlignment(horizontalAlignment); // выравнивание текста в ячейке СТАНДАРТНОЕ

        // Установка закраски строк СТАНДАРТНУЮ
        setBackgroundRow((JBroTable) table, row);

        // Установка жирного шрифта для требуемых ячеек
        for (int i = 0; i < ((JBroTable) table).getModel().getData().getFieldsCount(); i++) {
            if (((JBroTable) table).getModel().getData().getFields()[i].getIdentifier().startsWith("ФЕ Всего")
                    || ((JBroTable) table).getModel().getData().getFields()[i].getIdentifier().startsWith("ПЕ Всего")
                    || ((JBroTable) table).getModel().getData().getFields()[i].getIdentifier().startsWith("Тип транспорта")
                    || ((JBroTable) table).getModel().getData().getFields()[i].getIdentifier().startsWith("Вид транспорта")
                    || ((JBroTable) table).getModel().getData().getFields()[i].getIdentifier().startsWith("ФЕ Итого")
                    || ((JBroTable) table).getModel().getData().getFields()[i].getIdentifier().startsWith("ПЕ Итого")) {
                if (((JBroTable) table).convertColumnIndexToView(i) == column) {
                    label.setFont(new Font(null, Font.BOLD, 12));
                }
            }
        }

        if (((JBroTable) table).getModel().getData().getRowsCount() - 1 == row) {
            label.setFont(new Font(null, Font.BOLD, 12));
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

    // Работает для SLAVE TABLE! (сбрасывается при взаимодействии с таблицей)
    Color color = Color.red;
    int clor = 1;
    String pmet;

    // Установка цвета для строк
    public void setBackgroundRow(JBroTable table, int row) {
        if (typeOfStatement.equalsIgnoreCase("Now")) {
            doColorNow(row);
        }
        if (typeOfStatement.equalsIgnoreCase("Future")) {
            doColorFuture(row);
        }

//        Color firstColor = new Color(0, 0, 200, 80); // синий
//        Color secondColor = new Color(255, 128, 0, 80); // красный
//        Color thirdColor = new Color(255, 203, 209, 80); // розовый
//        Color fourthColor = new Color(127, 255, 0, 80); // салатовый
//        Color fifthColor = new Color(0, 191, 255, 80); // бирюзовый
//        Color sixColor = Color.white; // белый
//        if (row == 0) {
//            color = firstColor;
//        }
//        if (row > 0) {
//            pmet = (String) table.getData().getRows()[row - 1].getValue(0);
//            String temp = (String) table.getData().getRows()[row].getValue(0);
//            if (temp.equalsIgnoreCase(pmet)) {
//                color = color;
//            } else {
//                switch (clor) {
//                    case 1:
//                        color = secondColor;
//                        break;
//                    case 2:
//                        color = thirdColor;
//                        break;
//                    case 3:
//                        color = fourthColor;
//                        break;
//                    case 4:
//                        color = fifthColor;
//                        break;
//                    case 5:
//                        color = sixColor;
//                        break;
//                }
//                clor++;
//            }
//        }
//        return color;
//         1 строка
    }

    public void doColorFuture(int row) {
        Color firstColor = new Color(0, 0, 200, 80); // синий
        Color secondColor = new Color(255, 128, 0, 80); // красный
        Color thirdColor = new Color(255, 203, 209, 80); // розовый
        Color fourthColor = new Color(127, 255, 0, 80); // салатовый
        Color fifthColor = new Color(0, 191, 255, 80); // бирюзовый
        Color sixColor = Color.white; // белый

        if (row == 0) {
            label.setBackground(firstColor);
            label.setOpaque(true);
        }
        // от 2 до 6 строки
        for (int i = row; i >= 1 && i <= 5; i++) {
            label.setBackground(secondColor);
            label.setOpaque(true);
        }
        // от 7 до 12 строки
        for (int i = row; i >= 6 && i <= 11; i++) {
            label.setBackground(thirdColor);
            label.setOpaque(true);
        }
        // от 13 до 17 строки
        for (int i = row; i >= 12 && i <= 16; i++) {
            label.setBackground(fourthColor);
            label.setOpaque(true);
        }
        // 18 строка
        if (row == 17) {
            label.setBackground(fifthColor);
            label.setOpaque(true);
        }
        // 19 строка
        if (row == 18) {
            label.setBackground(sixColor);
            label.setOpaque(true);
        }
    }

    public void doColorNow(int row) {
        Color firstColor = new Color(0, 0, 200, 80); // синий
        Color secondColor = new Color(255, 128, 0, 80); // красный
        Color thirdColor = new Color(255, 173, 209, 80); // розовый
        Color fourthColor = new Color(127, 255, 0, 80); // салатовый
        Color fifthColor = new Color(0, 191, 255, 80); // бирюзовый
        Color sixColor = Color.white; // белый

        if (row == 0) {
            label.setBackground(firstColor);
            label.setOpaque(true);
        }
        // от 2 до 4 строки
        for (int i = row; i >= 1 && i <= 3; i++) {
            label.setBackground(secondColor);
            label.setOpaque(true);
        }
        if (row == 4) {
            label.setBackground(thirdColor);
            label.setOpaque(true);
        }
        // от 6 до 10 строки
        for (int i = row; i >= 5 && i <= 9; i++) {
            label.setBackground(fourthColor);
            label.setOpaque(true);
        }
        // 11 строка
        if (row == 10) {
            label.setBackground(fifthColor);
            label.setOpaque(true);
        }
        // 12 строка
        if (row == 11) {
            label.setBackground(sixColor);
            label.setOpaque(true);
        }
    }

}
