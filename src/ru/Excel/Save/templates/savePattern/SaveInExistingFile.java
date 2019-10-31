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
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.quinto.swing.table.view.JBroTable;
import ru.cartogram.CreateCartogram;

/**
 * В данном классе реализовано сохранение данных из Java таблицы в Excel
 * таблицу, если уже был создан Excel файл из шаблона, соответствующий Java
 * таблице.
 */
public class SaveInExistingFile extends JPanel {

    private int page = 0;

    public SaveInExistingFile(String fullFileName, JBroTable table, String kindOfStatement, String typeOfDirection, int periodOfTime, CreateCartogram cartogram) throws IOException {

        page = periodOfTime;
        // Читаем Excel файл
        FileInputStream inputStream = new FileInputStream(new File(fullFileName));
        if (fullFileName.endsWith(".xls")) { // если файл с расширением .xls,
            HSSFWorkbook workBook = new HSSFWorkbook(inputStream); // загружаем файл как книгу Excel
            HSSFSheet sheet = workBook.getSheetAt(page); // Получаем первую страницу книги Excel
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

            // Записываем данные с картограммы в Excel файл
            copyDataToExcelNow(sheet, cartogram);
            copyStreetToExcelNow(sheet, cartogram);
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
            XSSFSheet sheet = workBook.getSheetAt(page); // Получаем первую страницу книги Excel
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
            // Записываем данные с картограммы в Excel файл

            // Создаем файл и открываем выходной поток в этот файл
            File file = new File(fullFileName);
            FileOutputStream out = new FileOutputStream(file);
            workBook.write(out); // записываем созданный файл (книгу) в выходной поток - конечный файл
            // закрываем поток и книгу
            out.close();
            workBook.close();
        }

    }

    public SaveInExistingFile(String fullFileName, JBroTable table, String kindOfStatement, String typeOfDirection, int periodOfTime, int rowStart, CreateCartogram cartogram) throws IOException {
        page = periodOfTime;
        // Читаем Excel файл
        FileInputStream inputStream = new FileInputStream(new File(fullFileName));
        if (fullFileName.endsWith(".xls")) { // если файл с расширением .xls,
            HSSFWorkbook workBook = new HSSFWorkbook(inputStream); // загружаем файл как книгу Excel
            HSSFSheet sheet = workBook.getSheetAt(page); // Получаем первую страницу книги Excel
            if (kindOfStatement.equalsIgnoreCase("старая")) {
                if (typeOfDirection.equalsIgnoreCase("4")) {
                    new Now4(table, sheet, rowStart);
                }
                if (typeOfDirection.equalsIgnoreCase("4 кольцо")) {
                    new Now4Circle(table, sheet, rowStart);
                }
                if (typeOfDirection.equalsIgnoreCase("3 вверх")) {
                    new Now3Up(table, sheet, rowStart);
                }
                if (typeOfDirection.equalsIgnoreCase("3 вправо")) {
                    new Now3Up(table, sheet, rowStart);
                }
            }
            if (kindOfStatement.equalsIgnoreCase("новая")) {
                if (typeOfDirection.equalsIgnoreCase("4")) {
                    new Future4(table, sheet, rowStart);
                }
                if (typeOfDirection.equalsIgnoreCase("4 кольцо")) {
                    new Future4Circle(table, sheet, rowStart);
                }
                if (typeOfDirection.equalsIgnoreCase("3 вверх")) {
                    new Future3Right(table, sheet, rowStart);
                }
                if (typeOfDirection.equalsIgnoreCase("3 вправо")) {
                    new Future3Right(table, sheet, rowStart);
                }
            }

            // Записываем данные с картограммы в Excel файл
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
            XSSFSheet sheet = workBook.getSheetAt(page); // Получаем первую страницу книги Excel
            if (kindOfStatement.equalsIgnoreCase("старая")) {
                if (typeOfDirection.equalsIgnoreCase("4")) {
                    new Now4XSLX(table, sheet, rowStart);
                }
                if (typeOfDirection.equalsIgnoreCase("4 кольцо")) {
                    new Now4CircleXSLX(table, sheet, rowStart);
                }
                if (typeOfDirection.equalsIgnoreCase("3 вверх")) {
                    new Now3UpXSLX(table, sheet, rowStart);
                }
                if (typeOfDirection.equalsIgnoreCase("3 вправо")) {
                    new Now3UpXSLX(table, sheet, rowStart);
                }
            }
            if (kindOfStatement.equalsIgnoreCase("новая")) {
                if (typeOfDirection.equalsIgnoreCase("4")) {
                    new Future4XSLX(table, sheet, rowStart);
                }
                if (typeOfDirection.equalsIgnoreCase("4 кольцо")) {
                    new Future4CircleXSLX(table, sheet, rowStart);
                }
                if (typeOfDirection.equalsIgnoreCase("3 вверх")) {
                    new Future3RightXSLX(table, sheet, rowStart);
                }
                if (typeOfDirection.equalsIgnoreCase("3 вправо")) {
                    new Future3RightXSLX(table, sheet, rowStart);
                }
            }

            // Записываем данные с картограммы в Excel файл
            // Создаем файл и открываем выходной поток в этот файл
            File file = new File(fullFileName);
            FileOutputStream out = new FileOutputStream(file);
            workBook.write(out); // записываем созданный файл (книгу) в выходной поток - конечный файл
            // закрываем поток и книгу
            out.close();
            workBook.close();
        }

    }

    // Переносим данные: перекресток, время, дата, день недели
    private void copyDataToExcelNow(HSSFSheet sheet, CreateCartogram cartogram) {
        int SectionOrIntersection1 = 3;
        int SectionOrIntersection2 = 74;
        int SectionOrIntersection3 = 101;

        // "Участок/перекресток"
        String value1Section = String.valueOf(cartogram.getValueTspan1("SectionOrIntersection")); // получаем значение из таблицы в виде СТРОКИ 
        String value2Section = String.valueOf(cartogram.getValueTspan2("SectionOrIntersection"));

        sheet.getRow(SectionOrIntersection1).getCell(5).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(SectionOrIntersection2).getCell(5).setCellType(CellType.STRING);
        sheet.getRow(SectionOrIntersection3).getCell(5).setCellType(CellType.STRING);
        sheet.getRow(SectionOrIntersection1).getCell(5).setCellValue(value1Section + value2Section); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(SectionOrIntersection2).getCell(5).setCellValue(value1Section + value2Section);
        sheet.getRow(SectionOrIntersection3).getCell(5).setCellValue(value1Section + value2Section);

        // "Дата"
        String value1Date = String.valueOf(cartogram.getValueTspan1("Date")); // получаем значение из таблицы в виде СТРОКИ 
        String value2Date = String.valueOf(cartogram.getValueTspan2("Date"));

        sheet.getRow(SectionOrIntersection1 + 1).getCell(5).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(SectionOrIntersection2 + 1).getCell(5).setCellType(CellType.STRING);
        sheet.getRow(SectionOrIntersection3 + 1).getCell(5).setCellType(CellType.STRING);
        sheet.getRow(SectionOrIntersection1 + 1).getCell(5).setCellValue(value1Date + value2Date); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(SectionOrIntersection2 + 1).getCell(5).setCellValue(value1Date + value2Date);
        sheet.getRow(SectionOrIntersection3 + 1).getCell(5).setCellValue(value1Date + value2Date);

        // "День недели"
        String value1DayOfWeek = String.valueOf(cartogram.getValueTspan1("DayOfWeek")); // получаем значение из таблицы в виде СТРОКИ 
        String value2DayOfWeek = String.valueOf(cartogram.getValueTspan2("DayOfWeek"));

        sheet.getRow(SectionOrIntersection1 + 2).getCell(5).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(SectionOrIntersection2 + 2).getCell(5).setCellType(CellType.STRING);
        sheet.getRow(SectionOrIntersection3 + 2).getCell(5).setCellType(CellType.STRING);
        sheet.getRow(SectionOrIntersection1 + 2).getCell(5).setCellValue(value1DayOfWeek + value2DayOfWeek); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(SectionOrIntersection2 + 2).getCell(5).setCellValue(value1DayOfWeek + value2DayOfWeek);
        sheet.getRow(SectionOrIntersection3 + 2).getCell(5).setCellValue(value1DayOfWeek + value2DayOfWeek);

        // "Время"
        String value1Time = String.valueOf(cartogram.getValueTspan1("Time")); // получаем значение из таблицы в виде СТРОКИ 
        String value2Time = String.valueOf(cartogram.getValueTspan2("Time"));

        sheet.getRow(SectionOrIntersection1 + 3).getCell(5).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(27).getCell(5).setCellType(CellType.STRING);
        sheet.getRow(48).getCell(5).setCellType(CellType.STRING);
        sheet.getRow(78).getCell(5).setCellType(CellType.STRING);
        sheet.getRow(SectionOrIntersection3 + 3).getCell(5).setCellType(CellType.STRING);

        int allMinute = Integer.valueOf(value2Time.substring(0, value2Time.lastIndexOf(":"))) * 60
                + Integer.valueOf(value2Time.substring(value2Time.lastIndexOf(":") + 1, value2Time.length())); // время переведенное в минуты

        int hour1 = (int) Math.round((allMinute + 15) / 60); // часы
        int minutes1 = Math.round((allMinute + 15) % 60); // остаток от деления (минуты)
        String interval1 = String.format("%02d:%02d", hour1, minutes1);
        String interval2 = String.format("%02d:%02d", (int) Math.round((allMinute + 30) / 60), Math.round((allMinute + 30) % 60));
        String interval3 = String.format("%02d:%02d", (int) Math.round((allMinute + 45) / 60), Math.round((allMinute + 45) % 60));
        String interval4 = String.format("%02d:%02d", (int) Math.round((allMinute + 60) / 60), Math.round((allMinute + 60) % 60));
        sheet.getRow(SectionOrIntersection1 + 3).getCell(5).setCellValue("Интервал 1. " + value1Time + value2Time); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(27).getCell(5).setCellValue("Интервал 2. " + value1Time + interval1); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(48).getCell(5).setCellValue("Интервал 3. " + value1Time + interval2); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(78).getCell(5).setCellValue("Интервал 4. " + value1Time + interval3); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(SectionOrIntersection3 + 3).getCell(5).setCellValue(value1Time + value2Time + " - " + interval4);
    }

    // Переносим данные: названия направлений
    private void copyStreetToExcelNow(HSSFSheet sheet, CreateCartogram cartogram) {
        int rowDirection1 = 7;
        int cloumnDirection13 = 7;
        int cloumnDirection1 = 7;
        int cloumnDirection3 = 17;
        int cloumnDirection24 = 27;
        int cloumnDirection2 = 27;
        int cloumnDirection4 = 37;
        int rowDirection2 = 28;
        int rowDirection3 = 49;
        int rowDirection4 = 79;
        int rowDirection5 = 107;

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
        sheet.getRow(rowDirection1).getCell(cloumnDirection13).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(rowDirection1).getCell(cloumnDirection13).setCellValue(value13Direction); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(rowDirection2).getCell(cloumnDirection13).setCellType(CellType.STRING);
        sheet.getRow(rowDirection2).getCell(cloumnDirection13).setCellValue(value13Direction);
        sheet.getRow(rowDirection3).getCell(cloumnDirection13).setCellType(CellType.STRING);
        sheet.getRow(rowDirection3).getCell(cloumnDirection13).setCellValue(value13Direction);
        sheet.getRow(rowDirection4).getCell(cloumnDirection13).setCellType(CellType.STRING);
        sheet.getRow(rowDirection4).getCell(cloumnDirection13).setCellValue(value13Direction);
        sheet.getRow(rowDirection5).getCell(cloumnDirection13).setCellType(CellType.STRING);
        sheet.getRow(rowDirection5).getCell(cloumnDirection13).setCellValue(value13Direction);
        // Направление 1
        sheet.getRow(rowDirection1 + 1).getCell(cloumnDirection1).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(rowDirection1 + 1).getCell(cloumnDirection1).setCellValue(value1Direction); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(rowDirection2 + 1).getCell(cloumnDirection1).setCellType(CellType.STRING);
        sheet.getRow(rowDirection2 + 1).getCell(cloumnDirection1).setCellValue(value1Direction);
        sheet.getRow(rowDirection3 + 1).getCell(cloumnDirection1).setCellType(CellType.STRING);
        sheet.getRow(rowDirection3 + 1).getCell(cloumnDirection1).setCellValue(value1Direction);
        sheet.getRow(rowDirection4 + 1).getCell(cloumnDirection1).setCellType(CellType.STRING);
        sheet.getRow(rowDirection4 + 1).getCell(cloumnDirection1).setCellValue(value1Direction);
        sheet.getRow(rowDirection5 + 1).getCell(cloumnDirection1).setCellType(CellType.STRING);
        sheet.getRow(rowDirection5 + 1).getCell(cloumnDirection1).setCellValue(value1Direction);
        // Направление 3
        sheet.getRow(rowDirection1 + 1).getCell(cloumnDirection3).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(rowDirection1 + 1).getCell(cloumnDirection3).setCellValue(value3Direction); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(rowDirection2 + 1).getCell(cloumnDirection3).setCellType(CellType.STRING);
        sheet.getRow(rowDirection2 + 1).getCell(cloumnDirection3).setCellValue(value3Direction);
        sheet.getRow(rowDirection3 + 1).getCell(cloumnDirection3).setCellType(CellType.STRING);
        sheet.getRow(rowDirection3 + 1).getCell(cloumnDirection3).setCellValue(value3Direction);
        sheet.getRow(rowDirection4 + 1).getCell(cloumnDirection3).setCellType(CellType.STRING);
        sheet.getRow(rowDirection4 + 1).getCell(cloumnDirection3).setCellValue(value3Direction);
        sheet.getRow(rowDirection5 + 1).getCell(cloumnDirection3).setCellType(CellType.STRING);
        sheet.getRow(rowDirection5 + 1).getCell(cloumnDirection3).setCellValue(value3Direction);

        // Направление 2-4
        sheet.getRow(rowDirection1).getCell(cloumnDirection24).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(rowDirection1).getCell(cloumnDirection24).setCellValue(value24Direction); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(rowDirection2).getCell(cloumnDirection24).setCellType(CellType.STRING);
        sheet.getRow(rowDirection2).getCell(cloumnDirection24).setCellValue(value24Direction);
        sheet.getRow(rowDirection3).getCell(cloumnDirection24).setCellType(CellType.STRING);
        sheet.getRow(rowDirection3).getCell(cloumnDirection24).setCellValue(value24Direction);
        sheet.getRow(rowDirection4).getCell(cloumnDirection24).setCellType(CellType.STRING);
        sheet.getRow(rowDirection4).getCell(cloumnDirection24).setCellValue(value24Direction);
        sheet.getRow(rowDirection5).getCell(cloumnDirection24).setCellType(CellType.STRING);
        sheet.getRow(rowDirection5).getCell(cloumnDirection24).setCellValue(value24Direction);
        // Направление 2
        sheet.getRow(rowDirection1 + 1).getCell(cloumnDirection2).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(rowDirection1 + 1).getCell(cloumnDirection2).setCellValue(value2Direction); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(rowDirection2 + 1).getCell(cloumnDirection2).setCellType(CellType.STRING);
        sheet.getRow(rowDirection2 + 1).getCell(cloumnDirection2).setCellValue(value2Direction);
        sheet.getRow(rowDirection3 + 1).getCell(cloumnDirection2).setCellType(CellType.STRING);
        sheet.getRow(rowDirection3 + 1).getCell(cloumnDirection2).setCellValue(value2Direction);
        sheet.getRow(rowDirection4 + 1).getCell(cloumnDirection2).setCellType(CellType.STRING);
        sheet.getRow(rowDirection4 + 1).getCell(cloumnDirection2).setCellValue(value2Direction);
        sheet.getRow(rowDirection5 + 1).getCell(cloumnDirection2).setCellType(CellType.STRING);
        sheet.getRow(rowDirection5 + 1).getCell(cloumnDirection2).setCellValue(value2Direction);
        // Направление 4
        sheet.getRow(rowDirection1 + 1).getCell(cloumnDirection4).setCellType(CellType.STRING); // Устанавливаем тип ячейки - СТРОКА
        sheet.getRow(rowDirection1 + 1).getCell(cloumnDirection4).setCellValue(value4Direction); // Устанавливаем значение из таблицы в соответствующую ячейку
        sheet.getRow(rowDirection2 + 1).getCell(cloumnDirection4).setCellType(CellType.STRING);
        sheet.getRow(rowDirection2 + 1).getCell(cloumnDirection4).setCellValue(value4Direction);
        sheet.getRow(rowDirection3 + 1).getCell(cloumnDirection4).setCellType(CellType.STRING);
        sheet.getRow(rowDirection3 + 1).getCell(cloumnDirection4).setCellValue(value4Direction);
        sheet.getRow(rowDirection4 + 1).getCell(cloumnDirection4).setCellType(CellType.STRING);
        sheet.getRow(rowDirection4 + 1).getCell(cloumnDirection4).setCellValue(value4Direction);
        sheet.getRow(rowDirection5 + 1).getCell(cloumnDirection4).setCellType(CellType.STRING);
        sheet.getRow(rowDirection5 + 1).getCell(cloumnDirection4).setCellValue(value4Direction);
    }

}
