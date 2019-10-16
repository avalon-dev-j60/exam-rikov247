package ru.trafficClicker;

import java.awt.event.ActionEvent;
import org.quinto.swing.table.view.JBroTable;

/**
 * Слушатель количества кликов по кнопке и замены значения в ячейке таблицы
 */
public class OnButtonClick {

    private JBroTable table; // таблица
    private String fieldName; // идентификатор столбца
    private String rowName; // идентификатор строки
    private int row; // строка

    // Конструктор принимающий строку, идентификатор строки и идентификатор столбца
    public OnButtonClick(JBroTable table, String rowName, String fieldName) {
        this.table = table;
        this.rowName = rowName;
        this.fieldName = fieldName;
    }

    // chooser - для установки выбора нужной ячейки (от нужного конструктора)
    public void onButtonClick(ActionEvent e) {
        
        // Определяем номер строки по идентификатору строки
        for (int i = 0; i < table.getModel().getData().getRows().length; i++) {
        String temp = String.valueOf(table.getData().getRows()[i].getValue(1)); // значение во 2 столбце в проверяемой ячейке (вид транспорта)    
            if (temp.equalsIgnoreCase(rowName)) { // находим строку,
                row = i; // сохраняем номер этой строки
            }
        }
        
        int temp = 0; // переменная для прибавления единицы к информации в ячейке, которая соответствует кнопке к ней привязанной
        if (fieldName != null && table != null) {
            Object object = table.getValueAt(row, fieldName); // получаем значение в ячейке
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
            int columnFieldName = table.convertColumnIndexToView(fieldName);
            table.setValueAt(String.valueOf(temp), row, columnFieldName);
        }

    }

}
