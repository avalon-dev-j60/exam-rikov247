package ru.Excel.Save.templates.savePattern;

import ru.Excel.Save.templates.savePattern.xls.*;
import ru.Excel.Save.templates.savePattern.xlsx.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.quinto.swing.table.view.JBroTable;
import ru.Excel.Save.templates.AsHSSFwithPattern;
import ru.Excel.Save.templates.AsXSSFwithPattern;
import ru.cartogram.CreateCartogram;

/**
 * В данном классе реализовано сохранение данных из Java таблицы в Excel
 * таблицу, если уже был создан Excel файл из шаблона, соответствующий Java
 * таблице.
 */
public class SaveInExistingFile extends JPanel {

    // Данные для старой ведомости
    private int columnStartNowData = 5;
    private int columnStartNowTable = 7;
    private int[] sectionOrIntersectionNow = {3, 92}; // строки начала заполнения данных
    private int[] rowTimeNow = {6, 27, 48, 69, 95}; // строки заполнения времени
    private int[] rowDirectionNow = {7, 28, 49, 70, 98}; // строки для заполнения названия направлений
    private int[] columnDirectionNow = {7, 7, 17, 27, 27, 37}; // столбцы для заполнения направлений

    // Данные для новой ведомости
    private int columnStartFutureData = 4;
    private int columnStartFutureTable = 6;
    private int[] sectionOrIntersectionFuture = {3, 116}; // строки начала заполнения данных
    private int[] rowTimeFuture = {6, 33, 60, 87, 119}; // строки заполнения времени
    private int[] rowDirectionFuture = {7, 34, 61, 88, 122}; // строки для заполнения названия направлений
    private int[] columnDirectionFuture = {6, 6, 16, 26, 26, 36}; // столбцы для заполнения направлений

    public SaveInExistingFile(String fullFileName, JBroTable table, String kindOfStatement, String typeOfDirection, int page, int rowStart, CreateCartogram cartogram) throws IOException, FileNotFoundException {
        Workbook workBook = null;
        HSSFSheet hssfSheet = null;
        XSSFSheet xssfSheet = null;
        try {
            // Пытаемся прочитать Excel файл
            FileInputStream inputStream = new FileInputStream(new File(fullFileName));
            if (fullFileName.endsWith(".xls")) { // если файл с расширением .xls,
                workBook = new HSSFWorkbook(inputStream); // загружаем файл как книгу Excel
                hssfSheet = ((HSSFWorkbook) workBook).getSheetAt(page); // Получаем первую страницу книги Excel
            }
            if (fullFileName.endsWith(".xlsx")) { // если файл с расширением .xls,
                workBook = new XSSFWorkbook(inputStream); // загружаем файл как книгу Excel
                xssfSheet = ((XSSFWorkbook) workBook).getSheetAt(page); // Получаем первую страницу книги Excel
            }
        } catch (FileNotFoundException e) {
            String message = e.getMessage().substring(e.getMessage().lastIndexOf("(") - 1).trim(); // сообщение с ошибкой
            // Если не получилось (ЕГО УДАЛИЛИ), то создаем новый такой файл
            if (message.equals("(Не удается найти указанный файл)")) {
                JOptionPane.showMessageDialog(this, "Упс! Файл excel был удален. Сейчас попробуем его восстановить и сохранить туда ваши данные.");
                // Создаем файл
                if (fullFileName.endsWith(".xls")) { // если установлен фильтр .xls,
                    new AsHSSFwithPattern(fullFileName, kindOfStatement + "/4");  // производим сохранение таблицы Java (table) в файл (filename) с расширением ".xls" (предварительно удаляем любой текст с названием расширения и добавляем расширение - чтобы избежать повторного введения расширения;             
                }
                if (fullFileName.endsWith(".xlsx")) { // если установлен фильтр .xlsx,
                    new AsXSSFwithPattern(fullFileName, kindOfStatement + "/4");  // производим сохранение таблицы Java (table) в файл (filename) с расширением ".xls" (предварительно удаляем любой текст с названием расширения и добавляем расширение - чтобы избежать повторного введения расширения;
                }
                // Если новый файл был создан, то дальше в него выполним сохранение. Если не был, то выбрасываем сообщение!
                FileInputStream inputStream = new FileInputStream(new File(fullFileName));
                if (fullFileName.endsWith(".xls")) { // если файл с расширением .xls,
                    workBook = new HSSFWorkbook(inputStream); // загружаем файл как книгу Excel
                    hssfSheet = ((HSSFWorkbook) workBook).getSheetAt(page); // Получаем первую страницу книги Excel
                }
                if (fullFileName.endsWith(".xlsx")) { // если файл с расширением .xls,
                    workBook = new XSSFWorkbook(inputStream); // загружаем файл как книгу Excel
                    xssfSheet = ((XSSFWorkbook) workBook).getSheetAt(page); // Получаем первую страницу книги Excel
                }
                JOptionPane.showMessageDialog(this, "Восстановление прошло успешно. Картограммы также восстановлены.");
            }
        }
        // Если открытый файл, это xls файл, то:
        if (hssfSheet != null) {
            if (kindOfStatement.equalsIgnoreCase("Now")) {
                if (typeOfDirection.equalsIgnoreCase("4")) {
                    new Now4(table, hssfSheet, rowStart, columnStartNowTable);
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
                // Записываем данные с картограммы в Excel файл
                if (cartogram != null) {
                    copyDataToExcel(hssfSheet, null, cartogram, columnStartNowData, sectionOrIntersectionNow, rowTimeNow);
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
                // Записываем данные с картограммы в Excel файл
                if (cartogram != null) {
                    copyDataToExcel(hssfSheet, null, cartogram, columnStartFutureData, sectionOrIntersectionFuture, rowTimeFuture);
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
                // Записываем данные с картограммы в Excel файл
                if (cartogram != null) {
                    copyDataToExcel(null, xssfSheet, cartogram, columnStartNowData, sectionOrIntersectionNow, rowTimeNow);
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
                // Записываем данные с картограммы в Excel файл
                if (cartogram != null) {
                    copyDataToExcel(null, xssfSheet, cartogram, columnStartFutureData, sectionOrIntersectionFuture, rowTimeFuture);
                    copyStreetToExcel(null, xssfSheet, cartogram, rowDirectionFuture, columnDirectionFuture);
                    // сохраняем картограмму
                    cartogram.saveChangeValue();
                }
            }
        }
        // Создаем файл и открываем выходной поток в этот файл
        if (workBook != null) {
            File file = new File(fullFileName);
            try (FileOutputStream out = new FileOutputStream(file)) {
                workBook.write(out); // записываем созданный файл (книгу) в выходной поток - конечный файл
                workBook.close(); // закрываем поток и книгу
            }  // записываем созданный файл (книгу) в выходной поток - конечный файл
        }
    }

    public SaveInExistingFile(String fullFileName, JBroTable table, String kindOfStatement, String typeOfDirection, int page, int rowStart) throws IOException {
        this(fullFileName, table, kindOfStatement, typeOfDirection, page, rowStart, null);
    }

    // Переносим данные в таблицу, соответствующую нынешней (старой) ведомости: перекресток, время, дата, день недели
    private void copyDataToExcel(HSSFSheet hssfSheet, XSSFSheet xsssfSheet, CreateCartogram cartogram, int columnStart, int[] sectionOrIntersection, int[] rowTime) {
        int SectionOrIntersection1 = sectionOrIntersection[0];
        int SectionOrIntersection2 = sectionOrIntersection[1];

        Sheet sheet = new XSSFWorkbook().createSheet();
        if (hssfSheet != null) {
            sheet = hssfSheet;
        }
        if (xsssfSheet != null) {
            sheet = xsssfSheet;
        }

        // "Участок/перекресток"
        String value1Section = String.valueOf(cartogram.getValueTspan1("SectionOrIntersection")); // получаем значение из таблицы в виде СТРОКИ 
        String value2Section = String.valueOf(cartogram.getValueTspan2("SectionOrIntersection"));

        sheet.getRow(SectionOrIntersection1).getCell(columnStart).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(SectionOrIntersection2).getCell(columnStart).setCellType(CellType.STRING);
        sheet.getRow(SectionOrIntersection1).getCell(columnStart).setCellValue(value1Section + value2Section); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(SectionOrIntersection2).getCell(columnStart).setCellValue(value1Section + value2Section);

        // "Дата"
        String value1Date = String.valueOf(cartogram.getValueTspan1("Date")); // получаем значение из таблицы в виде СТРОКИ 
        String value2Date = String.valueOf(cartogram.getValueTspan2("Date"));

        sheet.getRow(SectionOrIntersection1 + 1).getCell(columnStart).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(SectionOrIntersection2 + 1).getCell(columnStart).setCellType(CellType.STRING);
        sheet.getRow(SectionOrIntersection1 + 1).getCell(columnStart).setCellValue(value1Date + value2Date); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(SectionOrIntersection2 + 1).getCell(columnStart).setCellValue(value1Date + value2Date);

        // "День недели"
        String value1DayOfWeek = String.valueOf(cartogram.getValueTspan1("DayOfWeek")); // получаем значение из таблицы в виде СТРОКИ 
        String value2DayOfWeek = String.valueOf(cartogram.getValueTspan2("DayOfWeek"));

        sheet.getRow(SectionOrIntersection1 + 2).getCell(columnStart).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(SectionOrIntersection2 + 2).getCell(columnStart).setCellType(CellType.STRING);
        sheet.getRow(SectionOrIntersection1 + 2).getCell(columnStart).setCellValue(value1DayOfWeek + value2DayOfWeek); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(SectionOrIntersection2 + 2).getCell(columnStart).setCellValue(value1DayOfWeek + value2DayOfWeek);

        // "Время"
        String value1Time = String.valueOf(cartogram.getValueTspan1("Time")); // получаем значение из таблицы в виде СТРОКИ 
        String value2Time = String.valueOf(cartogram.getValueTspan2("Time")).contains("_") ? "не указано" : String.valueOf(cartogram.getValueTspan2("Time")); // Если время не указали или указали не полностью (строка пустая, то передаем нужный текст)

        // Если время указано полностью, то заполняем эту графу в файле Excel, если нет - то ничего не делаем
        if (!value2Time.equals("не указано")) {
            int allMinute = Integer.valueOf(value2Time.substring(0, value2Time.indexOf(":"))) * 60
                    + Integer.valueOf(value2Time.substring(value2Time.indexOf(":") + 1, value2Time.lastIndexOf("-"))); // время переведенное в минуты
            // Рассчет времени + 15 минут
            int hour1 = (int) Math.round((allMinute + 15) / 60); // часы
            int minutes1 = Math.round((allMinute + 15) % 60); // остаток от деления (минуты)
            // Считываем стартовое введенное время
            String startTime = value2Time.substring(0, value2Time.indexOf(":"))
                    + ":" + value2Time.substring(value2Time.indexOf(":") + 1, value2Time.lastIndexOf("-"));
            // Время + 15 минут
            String interval1 = String.format("%02d:%02d", hour1, minutes1);
            // Время + 30 минут
            String interval2 = String.format("%02d:%02d", (int) Math.round((allMinute + 30) / 60), Math.round((allMinute + 30) % 60));
            // Время + 45 минут
            String interval3 = String.format("%02d:%02d", (int) Math.round((allMinute + 45) / 60), Math.round((allMinute + 45) % 60));
            // Время + 60 минут
            String interval4 = String.format("%02d:%02d", (int) Math.round((allMinute + 60) / 60), Math.round((allMinute + 60) % 60));
            // Считываем финальное введенное время
            String finalTime = value2Time.substring(value2Time.lastIndexOf("-") + 1, value2Time.lastIndexOf(":"))
                    + ":" + value2Time.substring(value2Time.lastIndexOf(":") + 1, value2Time.length());

            sheet.getRow(rowTime[0]).getCell(columnStart).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
            sheet.getRow(rowTime[0]).getCell(columnStart).setCellValue("Интервал 1. " + value1Time + startTime + " - " + interval1); // Устанавливаем значение из таблицы в соответствующую ячейку
            sheet.getRow(rowTime[1]).getCell(columnStart).setCellType(CellType.STRING);
            sheet.getRow(rowTime[1]).getCell(columnStart).setCellValue("Интервал 2. " + value1Time + interval1 + " - " + interval2); // Устанавливаем значение из таблицы в соответствующую ячейку
            sheet.getRow(rowTime[2]).getCell(columnStart).setCellType(CellType.STRING);
            sheet.getRow(rowTime[2]).getCell(columnStart).setCellValue("Интервал 3. " + value1Time + interval2 + " - " + interval3); // Устанавливаем значение из таблицы в соответствующую ячейку
            sheet.getRow(rowTime[3]).getCell(columnStart).setCellType(CellType.STRING);
            sheet.getRow(rowTime[3]).getCell(columnStart).setCellValue("Интервал 4. " + value1Time + interval3 + " - " + interval4); // Устанавливаем значение из таблицы в соответствующую ячейку
            sheet.getRow(rowTime[4]).getCell(columnStart).setCellType(CellType.STRING);
            sheet.getRow(rowTime[4]).getCell(columnStart).setCellValue(value1Time + startTime + " - " + finalTime);
        }
    }

    // Переносим данные в таблицу, соответствующую нынешней (старой) ведомости: названия направлений
    private void copyStreetToExcel(HSSFSheet hssfSheet, XSSFSheet xsssfSheet, CreateCartogram cartogram, int[] rowDirection, int[] columnDirection) {
        int columnDirection13 = columnDirection[0];
        int columnDirection1 = columnDirection[1];
        int columnDirection3 = columnDirection[2];
        int columnDirection24 = columnDirection[3];
        int columnDirection2 = columnDirection[4];
        int columnDirection4 = columnDirection[5];
        int rowDirection1 = rowDirection[0];
        int rowDirection2 = rowDirection[1];
        int rowDirection3 = rowDirection[2];
        int rowDirection4 = rowDirection[3];
        int rowDirection5 = rowDirection[4];

        Sheet sheet = new XSSFWorkbook().createSheet();
        if (hssfSheet != null) {
            sheet = hssfSheet;
        }
        if (xsssfSheet != null) {
            sheet = xsssfSheet;
        }

        // "Участок/перекресток"
        String value13Direction = String.valueOf(cartogram.getFullValue("StreetName_Vertical1")).isEmpty()
                ? String.valueOf(cartogram.getFullValue("StreetName_Vertical2"))
                : String.valueOf(cartogram.getFullValue("StreetName_Vertical1")); // получаем значение из таблицы в виде СТРОКИ 
        String value24Direction = String.valueOf(cartogram.getFullValue("StreetName_Horizontal1")).isEmpty()
                ? String.valueOf(cartogram.getFullValue("StreetName_Horizontal2"))
                : String.valueOf(cartogram.getFullValue("StreetName_Horizontal1")); // получаем значение из таблицы в виде СТРОКИ 
        String value1Direction = String.valueOf(cartogram.getFullValue("StreetName_Up"));
        String value2Direction = String.valueOf(cartogram.getFullValue("StreetName_Right"));
        String value3Direction = String.valueOf(cartogram.getFullValue("StreetName_Down"));
        String value4Direction = String.valueOf(cartogram.getFullValue("StreetName_Left"));

        // Направление 1-3
        sheet.getRow(rowDirection1).getCell(columnDirection13).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(rowDirection1).getCell(columnDirection13).setCellValue(value13Direction); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(rowDirection2).getCell(columnDirection13).setCellType(CellType.STRING);
        sheet.getRow(rowDirection2).getCell(columnDirection13).setCellValue(value13Direction);
        sheet.getRow(rowDirection3).getCell(columnDirection13).setCellType(CellType.STRING);
        sheet.getRow(rowDirection3).getCell(columnDirection13).setCellValue(value13Direction);
        sheet.getRow(rowDirection4).getCell(columnDirection13).setCellType(CellType.STRING);
        sheet.getRow(rowDirection4).getCell(columnDirection13).setCellValue(value13Direction);
        sheet.getRow(rowDirection5).getCell(columnDirection13).setCellType(CellType.STRING);
        sheet.getRow(rowDirection5).getCell(columnDirection13).setCellValue(value13Direction);
        // Направление 1
        sheet.getRow(rowDirection1 + 1).getCell(columnDirection1).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(rowDirection1 + 1).getCell(columnDirection1).setCellValue(value1Direction); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(rowDirection2 + 1).getCell(columnDirection1).setCellType(CellType.STRING);
        sheet.getRow(rowDirection2 + 1).getCell(columnDirection1).setCellValue(value1Direction);
        sheet.getRow(rowDirection3 + 1).getCell(columnDirection1).setCellType(CellType.STRING);
        sheet.getRow(rowDirection3 + 1).getCell(columnDirection1).setCellValue(value1Direction);
        sheet.getRow(rowDirection4 + 1).getCell(columnDirection1).setCellType(CellType.STRING);
        sheet.getRow(rowDirection4 + 1).getCell(columnDirection1).setCellValue(value1Direction);
        sheet.getRow(rowDirection5 + 1).getCell(columnDirection1).setCellType(CellType.STRING);
        sheet.getRow(rowDirection5 + 1).getCell(columnDirection1).setCellValue(value1Direction);
        // Направление 3
        sheet.getRow(rowDirection1 + 1).getCell(columnDirection3).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(rowDirection1 + 1).getCell(columnDirection3).setCellValue(value3Direction); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(rowDirection2 + 1).getCell(columnDirection3).setCellType(CellType.STRING);
        sheet.getRow(rowDirection2 + 1).getCell(columnDirection3).setCellValue(value3Direction);
        sheet.getRow(rowDirection3 + 1).getCell(columnDirection3).setCellType(CellType.STRING);
        sheet.getRow(rowDirection3 + 1).getCell(columnDirection3).setCellValue(value3Direction);
        sheet.getRow(rowDirection4 + 1).getCell(columnDirection3).setCellType(CellType.STRING);
        sheet.getRow(rowDirection4 + 1).getCell(columnDirection3).setCellValue(value3Direction);
        sheet.getRow(rowDirection5 + 1).getCell(columnDirection3).setCellType(CellType.STRING);
        sheet.getRow(rowDirection5 + 1).getCell(columnDirection3).setCellValue(value3Direction);

        // Направление 2-4
        sheet.getRow(rowDirection1).getCell(columnDirection24).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(rowDirection1).getCell(columnDirection24).setCellValue(value24Direction); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(rowDirection2).getCell(columnDirection24).setCellType(CellType.STRING);
        sheet.getRow(rowDirection2).getCell(columnDirection24).setCellValue(value24Direction);
        sheet.getRow(rowDirection3).getCell(columnDirection24).setCellType(CellType.STRING);
        sheet.getRow(rowDirection3).getCell(columnDirection24).setCellValue(value24Direction);
        sheet.getRow(rowDirection4).getCell(columnDirection24).setCellType(CellType.STRING);
        sheet.getRow(rowDirection4).getCell(columnDirection24).setCellValue(value24Direction);
        sheet.getRow(rowDirection5).getCell(columnDirection24).setCellType(CellType.STRING);
        sheet.getRow(rowDirection5).getCell(columnDirection24).setCellValue(value24Direction);
        // Направление 2
        sheet.getRow(rowDirection1 + 1).getCell(columnDirection2).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(rowDirection1 + 1).getCell(columnDirection2).setCellValue(value2Direction); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(rowDirection2 + 1).getCell(columnDirection2).setCellType(CellType.STRING);
        sheet.getRow(rowDirection2 + 1).getCell(columnDirection2).setCellValue(value2Direction);
        sheet.getRow(rowDirection3 + 1).getCell(columnDirection2).setCellType(CellType.STRING);
        sheet.getRow(rowDirection3 + 1).getCell(columnDirection2).setCellValue(value2Direction);
        sheet.getRow(rowDirection4 + 1).getCell(columnDirection2).setCellType(CellType.STRING);
        sheet.getRow(rowDirection4 + 1).getCell(columnDirection2).setCellValue(value2Direction);
        sheet.getRow(rowDirection5 + 1).getCell(columnDirection2).setCellType(CellType.STRING);
        sheet.getRow(rowDirection5 + 1).getCell(columnDirection2).setCellValue(value2Direction);
        // Направление 4
        sheet.getRow(rowDirection1 + 1).getCell(columnDirection4).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(rowDirection1 + 1).getCell(columnDirection4).setCellValue(value4Direction); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(rowDirection2 + 1).getCell(columnDirection4).setCellType(CellType.STRING);
        sheet.getRow(rowDirection2 + 1).getCell(columnDirection4).setCellValue(value4Direction);
        sheet.getRow(rowDirection3 + 1).getCell(columnDirection4).setCellType(CellType.STRING);
        sheet.getRow(rowDirection3 + 1).getCell(columnDirection4).setCellValue(value4Direction);
        sheet.getRow(rowDirection4 + 1).getCell(columnDirection4).setCellType(CellType.STRING);
        sheet.getRow(rowDirection4 + 1).getCell(columnDirection4).setCellValue(value4Direction);
        sheet.getRow(rowDirection5 + 1).getCell(columnDirection4).setCellType(CellType.STRING);
        sheet.getRow(rowDirection5 + 1).getCell(columnDirection4).setCellValue(value4Direction);
    }

}
