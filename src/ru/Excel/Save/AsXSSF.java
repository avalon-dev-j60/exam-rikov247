package ru.Excel.Save;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JTable;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.quinto.swing.table.view.JBroTable;

/**
 * Данный класс считывает данные с Java Table и сохраняет их в таблицу .xlsx
 */

public class AsXSSF {

    public AsXSSF(JBroTable table, String filename) throws IOException {

        // Создание файла (книги) для всех новых (xlsx, docx, pptx) файлов Microsoft Office
        XSSFWorkbook workBook = new XSSFWorkbook();

        XSSFSheet sheet = workBook.createSheet("Интенсивность"); // Создается страница в файле
        Row row; // переменная для строки
        Cell cell; // переменная для ячейки

        // Извлекаем данные из заголовков таблицы Java (JTable)
        row = sheet.createRow(0); // Создаем нулевую строку на странице в файле excel = header
        for (int i = 0; i < table.getColumnCount(); i++) { // цикл по столбцам таблицы Java (JTable) в выбранной строке
            cell = row.createCell(i); // создаем ячейку в строке
            String value = String.valueOf(table.getColumnName(i)); // извлекаем текстовое значение из header (заголовков) таблицы Java (JTable)
            // Чтобы вместо null в пустых ячейках ничего не было
            if (value.equalsIgnoreCase("null")) {
                value = "";
            }
            cell.setCellValue(value); // записываем в ячейку таблицы excel значение из ячейки таблицы Java (JTable)
        }
        // Извлекаем данные из ячеек таблицы Java (JTable)
        for (int j = 1; j <= table.getRowCount(); j++) {
            row = sheet.createRow(j); // Создаем первую строку (после заголовка) на странице в файле excel = header
            for (int i = 0; i < table.getColumnCount(); i++) { // цикл по столбцам таблицы Java (JTable) в выбранной строке
                cell = row.createCell(i); // создаем ячейку в строке
                String valueCell = String.valueOf(table.getValueAt(j - 1, i)); // извлекаем текстовое значение из ячейки таблицы Java (JTable)
                // Чтобы вместо null в пустых ячейках ничего не было
                if (valueCell.equalsIgnoreCase("null")) {
                    valueCell = "";
                }
                cell.setCellValue(valueCell); // записываем в ячейку таблицы excel значение из ячейки таблицы Java (JTable)
            }
        }

        // Создаем файл и открываем выходной поток в этот файл
        File file = new File(filename);
        FileOutputStream out = new FileOutputStream(file);
        workBook.write(out); // записываем созданный файл (книгу) в выходной поток - конечный файл
        // закрываем поток и книгу
        out.close();
        workBook.close();
    }
}
