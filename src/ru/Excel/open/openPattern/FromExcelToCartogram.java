package ru.Excel.open.openPattern;

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
import org.quinto.swing.table.view.JBroTable;

import ru.Excel.open.openPattern.xls.*;
import ru.Excel.open.openPattern.xlsx.*;
import ru.cartogram.CreateCartogram;

/**
 * В данном классе реализовано сохранение данных из Java таблицы в Excel
 * таблицу, если уже был создан Excel файл из шаблона, соответствующий Java
 * таблице.
 */
public class FromExcelToCartogram extends JPanel {

    private JBroTable table;
    private String fullFileName;
    private String kindOfStatement;
    private String typeOfDirection;
    private int page;
    private int rowStart;
    private CreateCartogram cartogram;

    private Workbook workBook = new HSSFWorkbook();
    private HSSFSheet hssfSheet = null;
    private XSSFSheet xssfSheet = null;

    private Now4 now4;

    // Данные для старой ведомости
    private int columnStartNowData = 5;
    private int columnStartNowTable = 7;
    private int sectionOrIntersectionNow = 3; // строки начала заполнения данных
    private int rowTimeNow = 95; // строки заполнения времени
    private int rowDirectionNow = 7; // строки для заполнения названия направлений
    private int[] columnDirectionNow = {7, 7, 17, 27, 27, 37}; // столбцы для заполнения направлений

    // Данные для новой ведомости
    private int columnStartFutureData = 4;
    private int columnStartFutureTable = 6;
    private int sectionOrIntersectionFuture = 3; // строки начала заполнения данных
    private int rowTimeFuture = 119; // строки заполнения времени
    private int rowDirectionFuture = 7; // строки для заполнения названия направлений
    private int[] columnDirectionFuture = {6, 6, 16, 26, 26, 36}; // столбцы для заполнения направлений

    public FromExcelToCartogram(String fullFileName, JBroTable table, String kindOfStatement, String typeOfDirection, int page, int rowStart, CreateCartogram cartogram) throws IOException, FileNotFoundException {
        this.fullFileName = fullFileName;
        this.table = table;
        this.kindOfStatement = kindOfStatement;
        this.typeOfDirection = typeOfDirection;
        this.page = page;
        this.rowStart = rowStart;
        this.cartogram = cartogram;
    }

    public FromExcelToCartogram(String fullFileName, JBroTable table, String kindOfStatement, String typeOfDirection, int page, int rowStart) throws IOException {
        this(fullFileName, table, kindOfStatement, typeOfDirection, page, rowStart, null);
    }

    // Переносим данные из таблицы, соответствующей нынешней (старой) ведомости: перекресток, время, дата, день недели
    private void copyDataFromExcel(HSSFSheet hssfSheet, XSSFSheet xsssfSheet, CreateCartogram cartogram, int columnStart, int sectionOrIntersection, int rowTime) {

        Sheet sheet = new XSSFWorkbook().createSheet();
        if (hssfSheet != null) {
            sheet = hssfSheet;
        }
        if (xsssfSheet != null) {
            sheet = xsssfSheet;
        }

        // "Участок/перекресток"
        // Получаем значение из проекта Excel
        String valueSection = sheet.getRow(sectionOrIntersection).getCell(columnStart).getStringCellValue().substring(sheet.getRow(sectionOrIntersection).getCell(columnStart).getStringCellValue().indexOf(":") + 1).trim();
        // Переносим значение на картограмму (с картограммы автоматически значение перенесется в панель конфигурации картограммы)
        cartogram.changeValueWithoutSaveTspan2("SectionOrIntersection", valueSection);
        // "Дата"
        String valueDate = sheet.getRow(sectionOrIntersection + 1).getCell(columnStart).getStringCellValue().substring(sheet.getRow(sectionOrIntersection + 1).getCell(columnStart).getStringCellValue().indexOf(":") + 1).trim();
        cartogram.changeValueWithoutSaveTspan2("Date", valueDate);
        // "День недели"
        String valueDayOfWeek = sheet.getRow(sectionOrIntersection + 2).getCell(columnStart).getStringCellValue().substring(sheet.getRow(sectionOrIntersection + 2).getCell(columnStart).getStringCellValue().indexOf(":") + 1).trim();
        cartogram.changeValueWithoutSaveTspan2("DayOfWeek", valueDayOfWeek);
        // "Время"
        String fullTime = sheet.getRow(rowTime).getCell(columnStart).getStringCellValue().substring(sheet.getRow(rowTime).getCell(columnStart).getStringCellValue().indexOf(":") + 1).trim();
        // Если длина чистого времени (без текста до) больше 11 символов (минимум - без проблеов) и содержит символ "-" (значит время указали), то
        if (fullTime.length() >= 11 && fullTime.contains("-")) {
            String valueStartTime = sheet.getRow(rowTime).getCell(columnStart).getStringCellValue().substring(
                    sheet.getRow(rowTime).getCell(columnStart).getStringCellValue().indexOf(":") + 1,
                    sheet.getRow(rowTime).getCell(columnStart).getStringCellValue().indexOf("-")
            ).trim();
            String valueFinalTime = sheet.getRow(rowTime).getCell(columnStart).getStringCellValue().substring(
                    sheet.getRow(rowTime).getCell(columnStart).getStringCellValue().indexOf("-") + 1
            ).trim();
            cartogram.changeValueWithoutSaveTspan2("Time", valueStartTime + "-" + valueFinalTime);
        }
    }

    // Переносим данные из таблицы, соответствующей нынешней (старой) ведомости: названия направлений
    private void copyStreetToExcel(HSSFSheet hssfSheet, XSSFSheet xsssfSheet, CreateCartogram cartogram, int rowDirection, int[] columnDirection) {
        int columnDirection13 = columnDirection[0];
        int columnDirection1 = columnDirection[1];
        int columnDirection3 = columnDirection[2];
        int columnDirection24 = columnDirection[3];
        int columnDirection2 = columnDirection[4];
        int columnDirection4 = columnDirection[5];

        Sheet sheet = new XSSFWorkbook().createSheet();
        if (hssfSheet != null) {
            sheet = hssfSheet;
        }
        if (xsssfSheet != null) {
            sheet = xsssfSheet;
        }

        // Направление 1-3  
        String value13Direction = sheet.getRow(rowDirection).getCell(columnDirection13).getStringCellValue().trim();
        cartogram.changeValueWithoutSave("StreetName_Vertical1", value13Direction);
        cartogram.changeValueWithoutSave("StreetName_Vertical2", value13Direction);
//        cartogram.changeValueWithoutSave("StreetName_Vertical1", value13Direction, value13Direction);
        // Направление 2-4
        String value24Direction = sheet.getRow(rowDirection).getCell(columnDirection24).getStringCellValue().trim();
        cartogram.changeValueWithoutSave("StreetName_Horizontal1", value24Direction);
        cartogram.changeValueWithoutSave("StreetName_Horizontal2", value24Direction);
//        cartogram.changeValueWithoutSave("StreetName_Horizontal1", value24Direction, value24Direction);
        // Направление 1
        String value1Direction = sheet.getRow(rowDirection + 1).getCell(columnDirection1).getStringCellValue().trim();
        cartogram.changeValueWithoutSave("StreetName_Up", value1Direction);
        // Направление 2
        String value2Direction = sheet.getRow(rowDirection + 1).getCell(columnDirection2).getStringCellValue().trim();
        cartogram.changeValueWithoutSave("StreetName_Right", value2Direction);
        // Направление 3
        String value3Direction = sheet.getRow(rowDirection + 1).getCell(columnDirection3).getStringCellValue().trim();
        cartogram.changeValueWithoutSave("StreetName_Down", value3Direction);
        // Направление 4
        String value4Direction = sheet.getRow(rowDirection + 1).getCell(columnDirection4).getStringCellValue().trim();
        cartogram.changeValueWithoutSave("StreetName_Left", value4Direction);
    }

    public void doCount() {
        workBook = new HSSFWorkbook();
        hssfSheet = null;
        xssfSheet = null;
        // Пытаемся прочитать Excel файл (поток сам закроется)
        try (FileInputStream inputStream = new FileInputStream(new File(fullFileName))) {
            if (fullFileName.endsWith(".xls")) { // если файл с расширением .xls,
                workBook = new HSSFWorkbook(inputStream); // загружаем файл как книгу Excel
                hssfSheet = ((HSSFWorkbook) workBook).getSheetAt(page); // Получаем первую страницу книги Excel
            }
            if (fullFileName.endsWith(".xlsx")) { // если файл с расширением .xls,
                workBook = new XSSFWorkbook(inputStream); // загружаем файл как книгу Excel
                xssfSheet = ((XSSFWorkbook) workBook).getSheetAt(page); // Получаем первую страницу книги Excel
            }
        } catch (IOException e) {
            Logger.getLogger(FromExcelToCartogram.class.getName()).log(Level.SEVERE, null, e);
        }

        // Если открытый файл, это xls файл, то:
        if (hssfSheet != null) {
            if (kindOfStatement.equalsIgnoreCase("Now")) {
                if (typeOfDirection.equalsIgnoreCase("4")) {
                    now4 = new Now4(table, hssfSheet, rowStart, columnStartNowTable);
                    now4.count();
                }
                if (typeOfDirection.equalsIgnoreCase("4Circle")) {
                    new Now4Circle(table, hssfSheet, rowStart, columnStartNowTable);
                }
                if (typeOfDirection.equalsIgnoreCase("3Up")) {
                    new Now3Up(table, hssfSheet, rowStart, columnStartNowTable);
                }
                if (typeOfDirection.equalsIgnoreCase("3Right")) {
                    new Now3Right(table, hssfSheet, rowStart, columnStartNowTable);
                }
                if (typeOfDirection.equalsIgnoreCase("3Down")) {
                    new Now3Down(table, hssfSheet, rowStart, columnStartNowTable);
                }
                if (typeOfDirection.equalsIgnoreCase("3Left")) {
                    new Now3Left(table, hssfSheet, rowStart, columnStartNowTable);
                }
                if (typeOfDirection.equalsIgnoreCase("2")) {
                    new Now2(table, hssfSheet, rowStart, columnStartNowTable);
                }
                // Записываем данные с картограммы в Excel файл
                if (cartogram != null) {
                    copyDataFromExcel(hssfSheet, null, cartogram, columnStartNowData, sectionOrIntersectionNow, rowTimeNow);
                    copyStreetToExcel(hssfSheet, null, cartogram, rowDirectionNow, columnDirectionNow);
                    // сохраняем все изменения!
                    cartogram.saveChangeValue();
                }
            }
            if (kindOfStatement.equalsIgnoreCase("Future")) {
                if (typeOfDirection.equalsIgnoreCase("4")) {
                    new Future4(table, hssfSheet, rowStart, columnStartFutureTable);
                }
                if (typeOfDirection.equalsIgnoreCase("4Circle")) {
                    new Future4Circle(table, hssfSheet, rowStart, columnStartFutureTable);
                }
                if (typeOfDirection.equalsIgnoreCase("3Up")) {
                    new Future3Up(table, hssfSheet, rowStart, columnStartFutureTable);
                }
                if (typeOfDirection.equalsIgnoreCase("3Right")) {
                    new Future3Right(table, hssfSheet, rowStart, columnStartFutureTable);
                }
                if (typeOfDirection.equalsIgnoreCase("3Down")) {
                    new Future3Down(table, hssfSheet, rowStart, columnStartFutureTable);
                }
                if (typeOfDirection.equalsIgnoreCase("3Left")) {
                    new Future3Left(table, hssfSheet, rowStart, columnStartFutureTable);
                }
                if (typeOfDirection.equalsIgnoreCase("2")) {
                    new Future2(table, hssfSheet, rowStart, columnStartFutureTable);
                }
                // Записываем данные с картограммы в Excel файл
                if (cartogram != null) {
                    copyDataFromExcel(hssfSheet, null, cartogram, columnStartFutureData, sectionOrIntersectionFuture, rowTimeFuture);
                    copyStreetToExcel(hssfSheet, null, cartogram, rowDirectionFuture, columnDirectionFuture);
                    // сохраняем все изменения!
                    cartogram.saveChangeValue();
                }
            }
        }
        if (xssfSheet != null) {
            if (kindOfStatement.equalsIgnoreCase("Now")) {
                if (typeOfDirection.equalsIgnoreCase("4")) {
                    new Now4XSLX(table, xssfSheet, rowStart, columnStartNowTable);
                }
                if (typeOfDirection.equalsIgnoreCase("4Circle")) {
                    new Now4CircleXSLX(table, xssfSheet, rowStart, columnStartNowTable);
                }
                if (typeOfDirection.equalsIgnoreCase("3Up")) {
                    new Now3UpXSLX(table, xssfSheet, rowStart, columnStartNowTable);
                }
                if (typeOfDirection.equalsIgnoreCase("3Right")) {
                    new Now3RightXSLX(table, xssfSheet, rowStart, columnStartNowTable);
                }
                if (typeOfDirection.equalsIgnoreCase("3Down")) {
                    new Now3DownXSLX(table, xssfSheet, rowStart, columnStartNowTable);
                }
                if (typeOfDirection.equalsIgnoreCase("3Left")) {
                    new Now3LeftXSLX(table, xssfSheet, rowStart, columnStartNowTable);
                }
                if (typeOfDirection.equalsIgnoreCase("2")) {
                    new Now2XSLX(table, xssfSheet, rowStart, columnStartNowTable);
                }
                // Записываем данные с картограммы в Excel файл
                if (cartogram != null) {
                    copyDataFromExcel(null, xssfSheet, cartogram, columnStartNowData, sectionOrIntersectionNow, rowTimeNow);
                    copyStreetToExcel(null, xssfSheet, cartogram, rowDirectionNow, columnDirectionNow);
                    // сохраняем все изменения!
                    cartogram.saveChangeValue();
                }
            }
            if (kindOfStatement.equalsIgnoreCase("Future")) {
                if (typeOfDirection.equalsIgnoreCase("4")) {
                    new Future4XSLX(table, xssfSheet, rowStart, columnStartFutureTable);
                }
                if (typeOfDirection.equalsIgnoreCase("4Cicle")) {
                    new Future4CircleXSLX(table, xssfSheet, rowStart, columnStartFutureTable);
                }
                if (typeOfDirection.equalsIgnoreCase("3Up")) {
                    new Future3UpXSLX(table, xssfSheet, rowStart, columnStartFutureTable);
                }
                if (typeOfDirection.equalsIgnoreCase("3Right")) {
                    new Future3RightXSLX(table, xssfSheet, rowStart, columnStartFutureTable);
                }
                if (typeOfDirection.equalsIgnoreCase("3Down")) {
                    new Future3DownXSLX(table, xssfSheet, rowStart, columnStartFutureTable);
                }
                if (typeOfDirection.equalsIgnoreCase("3Left")) {
                    new Future3LeftXSLX(table, xssfSheet, rowStart, columnStartFutureTable);
                }
                if (typeOfDirection.equalsIgnoreCase("2")) {
                    new Future2XSLX(table, xssfSheet, rowStart, columnStartFutureTable);
                }
                // Записываем данные с картограммы в Excel файл
                if (cartogram != null) {
                    copyDataFromExcel(null, xssfSheet, cartogram, columnStartFutureData, sectionOrIntersectionFuture, rowTimeFuture);
                    copyStreetToExcel(null, xssfSheet, cartogram, rowDirectionFuture, columnDirectionFuture);
                    // сохраняем картограмму
                    cartogram.saveChangeValue();
                }
            }
        }
        try {
            workBook.close();
        } catch (IOException ex) {
            Logger.getLogger(FromExcelToCartogram.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
