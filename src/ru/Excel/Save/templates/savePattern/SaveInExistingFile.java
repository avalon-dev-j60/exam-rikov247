package ru.Excel.Save.templates.savePattern;

import ru.Excel.Save.templates.savePattern.xls.*;
import ru.Excel.Save.templates.savePattern.xlsx.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JPanel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.quinto.swing.table.view.JBroTable;

/**
 * В данном классе реализовано сохранение данных из Java таблицы в Excel
 * таблицу, если уже был создан Excel файл из шаблона, соответствующий Java
 * таблице.
 */
public class SaveInExistingFile extends JPanel {

    public SaveInExistingFile(String fullFileName, JBroTable table, String kindOfStatement, String typeOfDirection) throws IOException {

        // Читаем Excel файл
        FileInputStream inputStream = new FileInputStream(new File(fullFileName));

        if (fullFileName.endsWith(".xls")) { // если файл с расширением .xls,
            HSSFWorkbook workBook = new HSSFWorkbook(inputStream); // загружаем файл как книгу Excel
            HSSFSheet sheet = workBook.getSheetAt(0); // Получаем первую страницу книги Excel
            if (kindOfStatement.equalsIgnoreCase("старая")) {
                if (typeOfDirection.equalsIgnoreCase("4")) {
                    new Now4(table, sheet);
                }
                if (typeOfDirection.equalsIgnoreCase("4 кольцо")) {
                    new Now4Circle(table, sheet);
                }
                if (typeOfDirection.equalsIgnoreCase("3 вверх")) {
                    new Now3Up(table, sheet);
                }
                if (typeOfDirection.equalsIgnoreCase("3 вправо")) {
                    new Now3Up(table, sheet);
                }
            }
            if (kindOfStatement.equalsIgnoreCase("новая")) {
                if (typeOfDirection.equalsIgnoreCase("4")) {
                    new Future4(table, sheet);
                }
                if (typeOfDirection.equalsIgnoreCase("4 кольцо")) {
                    new Future4Circle(table, sheet);
                }
                if (typeOfDirection.equalsIgnoreCase("3 вверх")) {
                    new Future3Right(table, sheet);
                }
                if (typeOfDirection.equalsIgnoreCase("3 вправо")) {
                    new Future3Right(table, sheet);
                }
            }

            // Создаем файл и открываем выходной поток в этот файл
            File file = new File(fullFileName);
            FileOutputStream out = new FileOutputStream(file);
            workBook.write(out); // записываем созданный файл (книгу) в выходной поток - конечный файл
            // закрываем поток и книгу
            out.close();
            workBook.close();
        }
        
        if (fullFileName.endsWith(".xlsx")) { // если файл с расширением .xls,
            XSSFWorkbook workBook = new XSSFWorkbook(inputStream); // загружаем файл как книгу Excel
            XSSFSheet sheet = workBook.getSheetAt(0); // Получаем первую страницу книги Excel
            if (kindOfStatement.equalsIgnoreCase("старая")) {
                if (typeOfDirection.equalsIgnoreCase("4")) {
                    new Now4XSLX(table, sheet);
                }
                if (typeOfDirection.equalsIgnoreCase("4 кольцо")) {
                    new Now4CircleXSLX(table, sheet);
                }
                if (typeOfDirection.equalsIgnoreCase("3 вверх")) {
                    new Now3UpXSLX(table, sheet);
                }
                if (typeOfDirection.equalsIgnoreCase("3 вправо")) {
                    new Now3UpXSLX(table, sheet);
                }
            }
            if (kindOfStatement.equalsIgnoreCase("новая")) {
                if (typeOfDirection.equalsIgnoreCase("4")) {
                    new Future4XSLX(table, sheet);
                }
                if (typeOfDirection.equalsIgnoreCase("4 кольцо")) {
                    new Future4CircleXSLX(table, sheet);
                }
                if (typeOfDirection.equalsIgnoreCase("3 вверх")) {
                    new Future3RightXSLX(table, sheet);
                }
                if (typeOfDirection.equalsIgnoreCase("3 вправо")) {
                    new Future3RightXSLX(table, sheet);
                }
            }

            // Создаем файл и открываем выходной поток в этот файл
            File file = new File(fullFileName);
            FileOutputStream out = new FileOutputStream(file);
            workBook.write(out); // записываем созданный файл (книгу) в выходной поток - конечный файл
            // закрываем поток и книгу
            out.close();
            workBook.close();
        }

    }
}
