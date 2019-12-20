package ru.Excel.Save.templates;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Данный класс считывает данные с Java Table и сохраняет их в таблицу .xlsx
 */
public class AsXSSFwithPattern {

    public AsXSSFwithPattern(String filename, String pattern) throws IOException {

        // Read XSLX file
        InputStream patternPath = AsHSSFwithPattern.class.getResourceAsStream("/resources/patterns/" + pattern + ".xlsx");

        // Создаем файл и открываем выходной поток в этот файл
        // Создание файла (книги) для всех новых (xlsx, docx, pptx) файлов Microsoft Office
        // Try с ресурсами автоматически закрывает поток и книгу
        try (XSSFWorkbook workBook = new XSSFWorkbook(patternPath)) {
            // Создаем файл и открываем выходной поток в этот файл
            File file = new File(filename);
            try (FileOutputStream out = new FileOutputStream(file)) {
                workBook.write(out); // записываем созданный файл (книгу) в выходной поток - конечный файл
            }
        }
    }
}
