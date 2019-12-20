package ru.Excel.Save.templates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Данный класс считывает данные с Java Table и сохраняет их в таблицу .xls
 */
public class AsHSSFwithPattern {

    public AsHSSFwithPattern(String filename, String pattern) throws IOException {

        // Read XSL file
        InputStream patternPath = AsHSSFwithPattern.class.getResourceAsStream("/resources/patterns/" + pattern + ".xls");

        // Создаем файл и открываем выходной поток в этот файл
        // Создание файла (книги) для всех старых (xls, doc, ppt) файлов Microsoft Office из уже существующего файла (шаблона)
        try (HSSFWorkbook workBook = new HSSFWorkbook(patternPath)) {
            // Создаем файл и открываем выходной поток в этот файл
            File file = new File(filename);
            FileOutputStream out = new FileOutputStream(file);
            workBook.write(out); // записываем созданный файл (книгу) в выходной поток - конечный файл
            // закрываем поток и книгу
            out.close();
        }
    }
}
