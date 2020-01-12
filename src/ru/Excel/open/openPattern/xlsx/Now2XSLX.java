package ru.Excel.open.openPattern.xlsx;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.quinto.swing.table.view.JBroTable;

/**
 * Класс, сохраняющий данные из таблицы Java в таблицу Excel по выбранному
 * шаблону. 2 направления.
 */
public class Now2XSLX {

    public Now2XSLX(JBroTable table, XSSFSheet sheet, int rowStart, int columnStart) {
        // Определяем столбцы, куда не переносим данные (чтобы сохранить Excel формулы)
        int[] columnTotal = new int[2]; // массив для хранения столбцов "Итого"
        int[] columnPE = new int[4]; // массив для хранения столбцов "ПЕ"
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

        // ПЕРЕБОР СТРОКИ таблицы (112 - строка начала таблицы; 17 - столбец начала таблицы)
        int row = rowStart;
        for (int j = 0; j < table.getRowCount() - 1; j++) {
            int column = columnStart + 22; // это столбец внутри Excel файла
            // ПЕРЕБОР СТОЛБЦОВ Java
            for (int i = 0; i < table.getColumnCount() - 2; i++) { // цикл по столбцам таблицы Java (JTable) в выбранной строке
                // ПРЕСЕКАЕМ ПЕРЕНОС значений подсчитанных в Java table в Excel table из столбцов ИТОГО (чтобы сохранить Excel формулы)
                // ПРЕСЕКАЕМ ПЕРЕНОС значений подсчитанных в Java table в Excel table из столбцов ПЕ (чтобы сохранить Excel формулы)
                if (i == columnTotal[0] || i == columnTotal[1]
                        || i == columnPE[0] || i == columnPE[1]
                        || i == columnPE[2] || i == columnPE[3]) {
                    // не записываем в таблицу ничего и просто идем дальше по порядку
                } // Записываем значения
                else {
                    // Пропускаем столбцы в Excel, которые не участвуют в подсчете (их нет в java таблице)
                    // i - столбец в Java. column - в Excel таблице
                    // Этот выбор зависит от типа перекрестка
                    if (i == 0) {
                    }
                    if (i == 4) {
                        // переходим в excel на 6 столбцов вправо
                        column = column + 6;
                    }
                    int valueInt = (int) sheet.getRow(j + row).getCell(i + column).getNumericCellValue();
                    String value = String.valueOf(valueInt); // Устанавливаем значение из таблицы в соответствующую ячейку
                    table.setValueAt(value, j, i);
                }
            }
        }
    }

}
