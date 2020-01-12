package ru.Excel.open;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * В данном классе реализовано получение данных из Excel.
 */
public class InfoFromProject extends JPanel {

    String kindOfStatement;
    String typeOfDirection;
    String[] treePaths = new String[10];
    // Данные о проекте
    private int rowKindOfStatement = 0;
    private int rowTypeOfDirection = 1;
    private int rowStartOfTreePath = 2;

    Workbook workBook = null;
    HSSFSheet hssfSheet = null;
    XSSFSheet xssfSheet = null;
    Sheet sheet = new XSSFWorkbook().createSheet();

    public InfoFromProject(String fullFileName) {
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
            Logger.getLogger(InfoFromProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InfoFromProject.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Получаем страницу из книги правильного формата 
        sheet = new XSSFWorkbook().createSheet();
        if (hssfSheet != null) {
            sheet = hssfSheet;
        }
        if (xssfSheet != null) {
            sheet = xssfSheet;
        }
    }

    // Получаем данные о видах транспорта для подсчета
    public String[] getTreePath() {
        // Перебираем элементы Tree
        int length = 0; // Длина элементов (если элемента нет, то возвращается пустая строка)
        for (int i = 0; i < treePaths.length; i++) {
            String temp = sheet.getRow(rowStartOfTreePath + i).getCell(1).getStringCellValue();
            treePaths[i] = temp; // переносим данные
            // Если считываемый элемент не пустой или не null, то учитываем его
            if (!temp.trim().isEmpty()) {
                length++;
            }
        }
        String[] treePath = {""}; // новый массив (изначально с одним пустым элементом, на случай, если ничего не заполнено в Excel
        if (length > 0) {
            treePath = new String[length]; // Новый массив длинной в количество элементов
            for (int i = 0; i < length; i++) {
                treePath[i] = treePaths[i]; // переносим элементы из массива, содержащего количество типов транспорта и ПУСТЫЕ СТРОКИ, в массив, который будет содержать только полезную информацию
            }
        }
        // возвращаем массив типов транспорта БЕЗ ПУСТЫХ СТРОК (не считая варианта, когда никакой информации вообще нет в excel
        return treePath;
    }

    // Получаем данные о виде ведомости (старая / новая)
    public String getKindOfStatement() {
        String kindOfStatement = sheet.getRow(rowKindOfStatement).getCell(1).getStringCellValue();
        if (kindOfStatement.equalsIgnoreCase("Now")) {
            return "Старая";
        }
        if (kindOfStatement.equalsIgnoreCase("Future")) {
            return "Новая";
        }
        return kindOfStatement;
    }

    // Получаем информацию о количестве направлений подсчета
    public String getTypeOfDirection() {
        String typeOfDirection = sheet.getRow(rowTypeOfDirection).getCell(1).getStringCellValue();
        if (typeOfDirection.equalsIgnoreCase("2")) {
            return "2 (сечение)";
        }
        if (typeOfDirection.equalsIgnoreCase("3Up")) {
            return "3 вверх";
        }
        if (typeOfDirection.equalsIgnoreCase("3Right")) {
            return "3 вправо";
        }
        if (typeOfDirection.equalsIgnoreCase("3Down")) {
            return "3 вниз";
        }
        if (typeOfDirection.equalsIgnoreCase("3Left")) {
            return "3 влево";
        }
        if (typeOfDirection.equalsIgnoreCase("4")) {
            return "4";
        }
        if (typeOfDirection.equalsIgnoreCase("4Circle")) {
            return "4 кольцо";
        }
        return typeOfDirection;
    }

    // После получения всей информации из Excel нужно закрыть книгу
    public void closeBook() {
        // Закрываем книгу
        if (workBook != null) {
            try {
                workBook.close(); // закрываем поток и книгу
            } catch (IOException ex) {
                Logger.getLogger(InfoFromProject.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
