package ru.Excel.Save.templates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.quinto.swing.table.view.JBroTable;

/**
 * Данный класс считывает данные с Java Table и сохраняет их в таблицу .xlsx
 */
public class AsXSSFwithPattern {

    public AsXSSFwithPattern(String filename, String pattern) throws IOException {

        // Read XSLX file
        FileInputStream inputStream = new FileInputStream(new File("patterns/" + pattern + ".xlsx"));

        // Создание файла (книги) для всех новых (xlsx, docx, pptx) файлов Microsoft Office
        XSSFWorkbook workBook = new XSSFWorkbook(inputStream);

        // Создаем файл и открываем выходной поток в этот файл
        File file = new File(filename);
        FileOutputStream out = new FileOutputStream(file);
        workBook.write(out); // записываем созданный файл (книгу) в выходной поток - конечный файл
        // закрываем поток и книгу
        out.close();
        workBook.close();
    }
}
