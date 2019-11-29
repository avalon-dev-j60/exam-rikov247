package ru.Excel.open;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.tree.TreePath;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * В данном классе реализовано сохранение данных из Java таблицы в Excel
 * таблицу, если уже был создан Excel файл из шаблона, соответствующий Java
 * таблице.
 */
public class SaveInInfoAboutProject extends JPanel {

    String kindOfStatement;
    String typeOfDirection;
    TreePath[] paths;
    // Данные о проекте
    private int rowKindOfStatement = 0;
    private int rowTypeOfDirection = 1;
    private int rowStartOfTreePath = 2;

    Workbook workBook = null;
    HSSFSheet hssfSheet = null;
    XSSFSheet xssfSheet = null;

    public SaveInInfoAboutProject(String fullFileName, String kindOfStatement, String typeOfDirection, TreePath[] paths) {
        this.kindOfStatement = kindOfStatement;
        this.typeOfDirection = typeOfDirection;
        this.paths = paths;

        int page = 0;

        // Пытаемся прочитать Excel файл
        try {
            FileInputStream inputStream = new FileInputStream(new File(fullFileName));
            if (fullFileName.endsWith(".xls")) { // если файл с расширением .xls,
                workBook = new HSSFWorkbook(inputStream); // загружаем файл как книгу Excel
                hssfSheet = ((HSSFWorkbook) workBook).getSheetAt(page); // Получаем первую страницу книги Excel
            }
            if (fullFileName.endsWith(".xlsx")) { // если файл с расширением .xls,
                workBook = new XSSFWorkbook(inputStream); // загружаем файл как книгу Excel
                xssfSheet = ((XSSFWorkbook) workBook).getSheetAt(page); // Получаем первую страницу книги Excel  
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveInInfoAboutProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaveInInfoAboutProject.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Если открытый файл, это xls файл, то:
        if (hssfSheet != null) {
            copyDataToExcel(hssfSheet, null);
        }
        if (xssfSheet != null) {
            copyDataToExcel(null, xssfSheet);
        }
        // Создаем файл и открываем выходной поток в этот файл
        if (workBook != null) {
            File file = new File(fullFileName);
            try ( FileOutputStream out = new FileOutputStream(file)) {
                workBook.write(out); // записываем созданный файл (книгу) в выходной поток - конечный файл
                workBook.close(); // закрываем поток и книгу
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SaveInInfoAboutProject.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SaveInInfoAboutProject.class.getName()).log(Level.SEVERE, null, ex);
            }  // записываем созданный файл (книгу) в выходной поток - конечный файл  // записываем созданный файл (книгу) в выходной поток - конечный файл
        }
    }

    // Переносим данные в таблицу, соответствующую нынешней (старой) ведомости: названия направлений
    private void copyDataToExcel(HSSFSheet hssfSheet, XSSFSheet xsssfSheet) {

        Sheet sheet = new XSSFWorkbook().createSheet();
        if (hssfSheet != null) {
            sheet = hssfSheet;
        }
        if (xsssfSheet != null) {
            sheet = xsssfSheet;
        }

        // Переносим данные в файл excel
        sheet.getRow(rowKindOfStatement).getCell(1).setCellValue(kindOfStatement);
        sheet.getRow(rowTypeOfDirection).getCell(1).setCellValue(typeOfDirection);

        // Перебираем элементы Tree
        for (int i = 0, j = 0; i < paths.length; i++) {
            String temp = String.valueOf(paths[i].getLastPathComponent());
            if (temp != null) {
                // не записываем "Учитываемые единицы"
                if (temp.equalsIgnoreCase("Учитываемые единицы")) {
                    j = -1; // переходим на строчку назад (потому что строка i = пустая, в нее ничего не записалось)
                } else {
                    sheet.getRow(rowStartOfTreePath + i + j).getCell(1).setCellValue(temp); // переносим данные
                }
            }
        }

    }

}
