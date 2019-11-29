package ru.Excel.open;

import ru.Excel.Save.templates.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Данный класс считывает данные с Java Table и сохраняет их в таблицу .xlsx
 */
public class AsXSSFwithPattern1 {

    public AsXSSFwithPattern1(String filename, String pattern) throws IOException {

        // Read XSLX file
        String patternPath = AsHSSFwithPattern1.class.getResource("/resources/patterns/" + pattern + ".xlsx").getPath();
        FileInputStream inputStream = new FileInputStream(new File(patternPath));

        // Создаем файл и открываем выходной поток в этот файл
        // Создание файла (книги) для всех новых (xlsx, docx, pptx) файлов Microsoft Office
        // Try с ресурсами автоматически закрывает поток и книгу
        try (XSSFWorkbook workBook = new XSSFWorkbook(inputStream)) {
            // Создаем файл и открываем выходной поток в этот файл
            File file = new File(filename);
            try (FileOutputStream out = new FileOutputStream(file)) {
                workBook.write(out); // записываем созданный файл (книгу) в выходной поток - конечный файл
            }
        }
    }
}
