package ru.jtable;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.quinto.swing.table.view.JBroTable;
import org.quinto.swing.table.view.JBroTableHeader;

/**
 * Метод для автоматической установки ширины столбцов и высоты строк
 */
public class AutoResize {

    // Метод установки высоты строк
    private static void adjustJTableRowSizes(JTable jTable) {
        for (int row = 0; row < jTable.getRowCount(); row++) {
            int maxHeight = 0;
            for (int column = 0; column < jTable.getColumnCount(); column++) {
                TableCellRenderer cellRenderer = jTable.getCellRenderer(row, column);
                Object valueAt = jTable.getValueAt(row, column);
                Component tableCellRendererComponent = cellRenderer.getTableCellRendererComponent(jTable, valueAt, false, false, row, column);
                int heightPreferable = tableCellRendererComponent.getPreferredSize().height;
                maxHeight = Math.max(heightPreferable, maxHeight);
            }
            jTable.setRowHeight(row, maxHeight);
        }
    }

    private static int width;

    private static void columnSize(JBroTable table, int levelHeader, int column) {

        TableColumn tableSuperColumn = table.getTableHeader().getUI().getHeader(levelHeader).getColumnModel().getColumn(column);
        TableCellRenderer renderer = tableSuperColumn.getHeaderRenderer();

        Component comp = renderer.getTableCellRendererComponent(table, tableSuperColumn.getHeaderValue(), false, false, 0, 0);

        if (comp.getPreferredSize().width > width) {
            width = (int) (comp.getPreferredSize().width);
        }
    }

    // Устанавливаем ширину столбцов
    private static void setWidth(JBroTable table, int column, int width) {
        DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
        TableColumn col = columnModel.getColumn(column); // получаем столбец

        col.setPreferredWidth(width);
        col.setWidth(width);
    }

    // метод установки ширины столбцов
    private static void adjustColumnSizes(JBroTable table, int column, int margin) {
        DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
        TableColumn col = columnModel.getColumn(column); // получаем столбец
        int width; // переменная ширины столбца
        TableCellRenderer renderer = col.getHeaderRenderer();

        if (renderer == null) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;

        // Проходим по всем строкам, проверяем содержимое и считываем ширину содержимого
        for (int r = 0; r < table.getRowCount(); r++) {
            renderer = table.getCellRenderer(r, column);
            comp = renderer.getTableCellRendererComponent(table, table.getValueAt(r, column), false, false, r, column);
            int currentWidth = comp.getPreferredSize().width;
            width = Math.max(width, currentWidth);
        }

        width += 2 * margin; // добавляем отступы к ширине

        // устанавливаем в качестве ширины колонки полученную ширину
        col.setPreferredWidth(width);
        col.setWidth(width);
    }

    // Итоговый метод для автоматической установки ширины столбцов и высоты строк
    public static void update(JBroTable table) {
        adjustJTableRowSizes(table); // изменение высоты строк

        // Изменение ширины столцов (не трогая два последних). Не работает, как нужно
        JBroTableHeader header = table.getTableHeader();
        // Смотрим только на 3 уровень и считываем ширину значений из всех столбцов на уровне 3. В качестве ширины записываем максимальную ширину
        for (int j = 0; j < header.getUI().getHeader(2).getColumnModel().getColumnCount(); j++) {
            columnSize(table, 2, j);
        }

        for (int i = 0; i < table.getColumnCount() - 2; i++) {
            setWidth(table, i, ((int) (width / 2)) - 1);
        }

        // Изменение ширины столцов (не трогая два последних)
//        for (int i = 0; i < table.getColumnCount() - 2; i++) {
//            adjustColumnSizes(table, i, 9);
//        }
    }
}
