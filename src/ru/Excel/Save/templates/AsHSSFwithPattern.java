package ru.Excel.Save.templates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JTable;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.quinto.swing.table.view.JBroTable;

/**
 * Данный класс считывает данные с Java Table и сохраняет их в таблицу .xls
 */
public class AsHSSFwithPattern {

    public AsHSSFwithPattern(String filename, String pattern) throws IOException {

        // Read XSL file
        FileInputStream inputStream = new FileInputStream(new File("patterns/" + pattern + ".xls"));

        // Создание файла (книги) для всех старых (xls, doc, ppt) файлов Microsoft Office из уже существующего файла (шаблона)
        HSSFWorkbook workBook = new HSSFWorkbook(inputStream);

        // Создаем файл и открываем выходной поток в этот файл
        File file = new File(filename);
        FileOutputStream out = new FileOutputStream(file);
        workBook.write(out); // записываем созданный файл (книгу) в выходной поток - конечный файл
        // закрываем поток и книгу
        out.close();
        workBook.close();
    }
}
