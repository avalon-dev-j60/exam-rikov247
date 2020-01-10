package ru.Excel.open;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

/**
 * Данный класс считывает данные с Java Table и сохраняет их в таблицу .xls
 */
public class AsHSSFwithPattern1 {

    public AsHSSFwithPattern1(String fileName) throws IOException {

        // Read XSL file
        FileInputStream inputStream = new FileInputStream(fileName);

        String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        if (extension.equalsIgnoreCase("xls")) {
            // Создаем файл и открываем выходной поток в этот файл
            // Создание файла (книги) для всех старых (xls, doc, ppt) файлов Microsoft Office из уже существующего файла (шаблона)
            try ( HSSFWorkbook workBook = new HSSFWorkbook(inputStream)) {
                // Создаем файл и открываем выходной поток в этот файл
                HSSFSheet hssfSheet = workBook.getSheetAt(0);
                hssfSheet.getRow(0).getCell(1).getStringCellValue();

                File file = new File(fileName);
                FileOutputStream out = new FileOutputStream(file);
                workBook.write(out); // записываем созданный файл (книгу) в выходной поток - конечный файл
                // закрываем поток и книгу
                out.close();
            }
        }
    }
}
