package ru.Excel.open.openPattern.xls;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.quinto.swing.table.view.JBroTable;

/**
 * Класс, сохраняющий данные из таблицы Java в таблицу Excel по выбранному
 * шаблону. 4 направления.
 */
public class Now4 {

    JBroTable table;
    HSSFSheet sheet;
    int rowStart;
    int columnStart;

    public Now4(JBroTable table, HSSFSheet sheet, int rowStart, int columnStart) {
        this.table = table;
        this.sheet = sheet;
        this.rowStart = rowStart;
        this.columnStart = columnStart;
    }

    public void count() {
        // Определяем столбцы, куда не переносим данные. 
        int[] columnTotal = new int[4]; // массив для хранения столбцов "Итого"
        int[] columnPE = new int[20]; // массив для хранения столбцов "ПЕ"
        int myInt = 0; // переменная для выбора следующего элемента в массиве выше, если нашли новый столбец "Итого"
        int unAccountedColumns = table.getData().getFieldsCount() - table.getColumnModel().getColumnCount(); // считаем неучитываемые столбцы (либо фиксированный, либо не отображаемые) 

        // Ищем столбцы "Итого" - пропускаем и ФЕ и ПЕ
        for (int i = 0; i < table.getModel().getData().getFieldsCount(); i++) {
            boolean totalTemp = table.getModel().getData().getFields()[i].getIdentifier().startsWith("ФЕ Итого"); // логическая переменная, которая становится true, если нашли столбец "Итого"
            myInt += totalTemp ? 1 : 0; // переводим логическую величину в int
            if (totalTemp) { // если нашли столбец "ФЕ Итого"
                columnTotal[-1 + myInt] = i - unAccountedColumns; // записываем в массив номер этого столбца
            }
        }
        myInt = 0;
        // Ищем столбцы "ПЕ", не считая "ПЕ Всего" - последнего столбца
        for (int i = 0; i < table.getModel().getData().getFieldsCount(); i++) {
            boolean totalTemp = table.getModel().getData().getFields()[i].getIdentifier().startsWith("ПЕ")
                    & !table.getModel().getData().getFields()[i].getIdentifier().startsWith("ПЕ Всего"); // логическая переменная, которая становится true, если нашли столбец содержащий ПЕ и это не "ПЕ Итого"
            myInt += totalTemp ? 1 : 0; // переводим логическую величину в int
            if (totalTemp) { // если нашли столбец "ПЕ"
                columnPE[-1 + myInt] = i - unAccountedColumns; // записываем в массив номер этого столбца
            }
        }

        // Перебираем строки таблицы (row - строка начала таблицы; column - столбец начала таблицы)
        int row = rowStart;

//        long end1 = System.currentTimeMillis();
        for (int j = 0; j < table.getRowCount() - 1; j++) {
            int column = columnStart;
            for (int i = 0; i < table.getColumnCount() - 2; i++) { // цикл по столбцам таблицы Java (JTable) в выбранной строке
                // ПРЕСЕКАЕМ ПЕРЕНОС значений подсчитанных в Java table в Excel table из столбцов ИТОГО (чтобы сохранить Excel формулы)
                // ПРЕСЕКАЕМ ПЕРЕНОС значений подсчитанных в Java table в Excel table из столбцов ПЕ (чтобы сохранить Excel формулы)
                if (i == columnTotal[0] || i == columnTotal[1] || i == columnTotal[2] || i == columnTotal[2]
                        || i == columnPE[0] || i == columnPE[1] || i == columnPE[2] || i == columnPE[3]
                        || i == columnPE[4] || i == columnPE[5] || i == columnPE[6] || i == columnPE[7]
                        || i == columnPE[8] || i == columnPE[9] || i == columnPE[10] || i == columnPE[11]
                        || i == columnPE[12] || i == columnPE[13] || i == columnPE[14] || i == columnPE[15]
                        || i == columnPE[16] || i == columnPE[17] || i == columnPE[18] || i == columnPE[19]) {
                    // не записываем в таблицу ничего и просто идем дальше по порядку
                } // Записываем значения
                else {
                    int valueInt = (int) sheet.getRow(j + row).getCell(i + column).getNumericCellValue();
                    String value = String.valueOf(valueInt); // Устанавливаем значение из таблицы в соответствующую ячейку
//                    long end1s = System.currentTimeMillis();
                    table.setValueAt(value, j, i);
//                    long end2s = System.currentTimeMillis();
//                    System.out.println("TimeS: " + (end2s - end1s) + " мс");
                }
            }
        }

//        long end2 = System.currentTimeMillis();
//        System.out.println("Time: " + (end2 - end1) + " мс");
    }

}
