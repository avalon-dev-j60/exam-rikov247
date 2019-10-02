package ru.trafficClicker;

import java.awt.event.ActionEvent;
import org.quinto.swing.table.view.JBroTable;

/**
 * Слушатель количества кликов по кнопке и замены значения в ячейке таблицы
 */
public class OnButtonClick {

    private JBroTable table; // таблица
    private String fieldName; // идентификатор столбца
    private int row; // строка
    private int column; // столбец

    private int chooser = 2; // переменная для выбора того, по какому конструктору считать клики

    // Конструктор принимающий строку и столбец
    public OnButtonClick(JBroTable table, int row, int column) {
        this.table = table;
        this.row = row;
        this.column = column;

        chooser = 0;
    }

    // Конструктор принимающий строку и идентификатор столбца
    public OnButtonClick(JBroTable table, int row, String fieldName) {
        this.table = table;
        this.row = row;
        this.fieldName = fieldName;

        chooser = 1;
    }

    // chooser - для установки выбора нужной ячейки (от нужного конструктора)
    public void onButtonClick(ActionEvent e) {
        int unAccountedColumns = table.getData().getFieldsCount() - table.getColumnModel().getColumnCount(); // считаем неучитываемые столбцы (либо фиксированный, либо не отображаемые) 
        int temp = 0; // переменная для прибавления единицы к информации в ячейке, которая соответствует кнопке к ней привязанной
        if (chooser == 1 && fieldName != null && table != null) {
            Object object = table.getData().getValue(row, fieldName); // получаем значение в ячейке
            if (object instanceof String) { // если в ячейке строка, то:
                try {
                    temp = Integer.valueOf(String.valueOf(object)); // переводим значение из ячейки в Integer (для этого сначала в String)
                    temp++; // прибавляем единицу
                } catch (NumberFormatException ex) { // ловим исключения
                    System.err.println("Неверный формат строки!");
                }
            }
            if (object instanceof Integer) { // если в ячейке число int , то к нему и прибавляем единицу
                temp = (Integer) object;
                temp++;
            }
            // Если использовать table.setValueAt, то он сам будем уведомлять таблицу об изменении данных в ней (то есть все листенеры таблицы будут оповещены)
            // Устанавливаем новое значение (измененное на единицу - соотвествует количеству кликов) в ячейку, соответствующую привязанной кнопке
            int columnFieldName = table.getData().getIndexOfModelField(fieldName) - unAccountedColumns;
            table.setValueAt(String.valueOf(temp), row, columnFieldName);
        }

        // для того, что определять столбец не по идентификатору, а просто по номеру
        //        if (chooser == 0 && table != null) {
//            Object object = table.getValueAt(row, column);
//            if (object instanceof String) {
//                try {
//                    temp = Integer.valueOf((String) object);
//                    temp++;
//                } catch (NumberFormatException ex) {
//                    System.err.println("Неверный формат строки!");
//                }
//            }
//            if (object instanceof Integer) {
//                temp = (Integer) object;
//                temp++;
//            }
//            table.setValueAt(temp, row, column);
//        }
    }

}
