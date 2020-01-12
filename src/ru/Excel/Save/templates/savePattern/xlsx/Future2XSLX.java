package ru.Excel.Save.templates.savePattern.xlsx;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.quinto.swing.table.view.JBroTable;

/**
 * Класс, сохраняющий данные из таблицы Java в таблицу Excel по выбранному
 * шаблону. 2 направления.
 */
public class Future2XSLX {
    
    public Future2XSLX(JBroTable table, XSSFSheet sheet, int rowStart, int columnStart) {
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

        // ПЕРЕБОР СТРОКИ таблицы (row - строка начала таблицы; column - столбец начала таблицы)
        int row = rowStart;
        for (int j = 0; j < table.getRowCount() - 1; j++) {
            // ПЕРЕБОР СТОЛБЦОВ Java
            int column = columnStart + 22; // это столбец внутри Excel файла
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
                    if (i == 0) { // Этот столбец записываем, но в нем нужно заполнить сокрытие не нужных столбцов
                        // Для сокрытия малого направления направо и разворота
                        for (int y = 0; y < 4; y++) {
                            sheet.setColumnHidden(i + column + 2 + y, true);
                        }
                        // Для сокрытия первого и третьего направлений полностью
                        for (int y = 0; y < 22; y++) {
                            sheet.setColumnHidden(column - 22 + y, true);
                        }
                    }
                    if (i == 4) { // Этот столбец записываем, но в нем нужно заполнить сокрытие не нужных столбцов
                        // Для сокрытия малого направления направо и разворота
                        for (int y = 0; y < 4; y++) {
                            sheet.setColumnHidden(i + column + 6 + 2 + y, true);
                        }
                        // Для сокрытия малого направления налево
                        for (int y = 0; y < 2; y++) {
                            sheet.setColumnHidden(i + column + 6 - 2 + y, true);
                        }
                        // переходим в excel на 6 столбцов вправо
                        column = column + 6;
                    }
                    // Перенос чисел из Java table в Excel table
                    sheet.getRow(j + row).getCell(i + column).setCellType(CellType.NUMERIC); // Устанавливаем тип ячейки - ЧИСЛА

                    String valueTable = String.valueOf(table.getValueAt(j, i)); // получаем значение из таблицы в виде СТРОКИ
                    Double value = Double.valueOf(valueTable); // получаем значение из таблицы в виде ЧИСЛА DOUBLE
                    sheet.getRow(j + row).getCell(i + column).setCellValue(value); // Устанавливаем значение из таблицы в соответствующую ячейку
                }
            }
        }
        sheet.setForceFormulaRecalculation(true); // Пересчет всех формул на странице
    }

}
