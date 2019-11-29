package tests.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import ru.Excel.open.excelOpen;

/**
 * Метод для считывания данных с excel и перевода их в jtable
 */
public class excelToJTable {

    static private excelOpen excelOpen = new excelOpen(); // Класс, в котором через FileChooser получаем полный путь к выбираемому файлу excel  

    public static void main(String[] args) throws IOException {
//    public void excelToJTable() throws IOException {
        String selectFile = excelOpen.getSelectExcelFile(); // Выбираем файл и получаем полный путь к нему (вызывается окно JFileChooser)
        // Если файл был выбран, то читаем его, если никакой файл не был выбран или произошла ошибка - ничего не делаем
        if (selectFile != null) {
            // Читаем файл excel
            FileInputStream inputStream = new FileInputStream(new File(selectFile));

            // Get the workbook instance for XLS file
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

            // Получаем первую сраницу из файла (книги)
            HSSFSheet sheet = workbook.getSheetAt(0);

            // Создаем итератор (функцию перебора элементов) для всех строк на странице
            Iterator<Row> rowIterator = sheet.iterator();

            // Пока на странице есть следующяя строка:
            while (rowIterator.hasNext()) {
                // берем следующую строку
                Row row = rowIterator.next();
                // Создаем итератор (функцию перебора элементов) для всех ячеек в строке
                Iterator<Cell> cellIterator = row.cellIterator();
                // Пока в строке есть следующая ячейка

                outerr:
                while (cellIterator.hasNext()) {
                    // берем следующую ячейку
                    Cell cell = cellIterator.next();
                    // Получаем тип ячейки (хранимой в ней информации)
                    CellType cellType = cell.getCellType();

                    //will iterate over the Merged cells
                    // getNumMergedRegions() - дает количество объединенных областей
                    for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
                        CellRangeAddress region = sheet.getMergedRegion(i); //Region of merged cells

                        int colIndex = region.getFirstColumn(); //number of columns merged
                        int rowNum = region.getFirstRow();      //number of rows merged
                        //check first cell of the region
                        if (rowNum == cell.getRowIndex() && colIndex == cell.getColumnIndex()) {
                            System.out.println(colIndex + " / " + cell.getColumnIndex() + " ; " + rowNum + " / " + cell.getRowIndex());
                            Cell mergedCells = sheet.getRow(rowNum).getCell(colIndex);
                            CellType typeOfMergedCells = mergedCells.getCellType();
                            switch (typeOfMergedCells) {
                                case _NONE:
                                    System.out.print("");
                                    System.out.print("\t");
                                    break;
                                case BOOLEAN:
                                    System.out.print(mergedCells.getBooleanCellValue());
                                    System.out.print("\t");
                                    break;
                                case BLANK:
                                    System.out.print("null");
                                    System.out.print("\t");
                                    break;
                                case FORMULA:
                                    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                                    // Получаем значение полученное при расчете по формуле в ячейке
                                    System.out.print(evaluator.evaluate(mergedCells).getNumberValue());
                                    break;
                                case NUMERIC:
                                    System.out.print(mergedCells.getNumericCellValue());
                                    System.out.print("\t");
                                    break;
                                case STRING:
                                    System.out.print(mergedCells.getStringCellValue());
                                    System.out.print("\t");
                                    break;
                                case ERROR:
                                    System.out.print("!");
                                    System.out.print("\t");
                                    break;
                            }
                            continue outerr;
                        }
                    }
                    //the data in merge cells is always present on the first cell. All other cells(in merged region) are considered blank
                    if (cellType == CellType.BLANK || cell == null) {
                        continue;
                    }

                    // В зависимости от типа ячейки поизводим какие-либо действия
                    switch (cellType) {
                        case _NONE:
                            System.out.print("");
                            System.out.print("\t");
                            break;
                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue());
                            System.out.print("\t");
                            break;
                        case BLANK:
                            System.out.print("");
                            System.out.print("\t");
                            break;
                        case FORMULA:
                            // Выводим формулу, хранящуюся в ячейке
//                    System.out.print(cell.getCellFormula());
//                    System.out.print("\t");

                            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                            // Получаем значение полученное при расчете по формуле в ячейке
                            System.out.print(evaluator.evaluate(cell).getNumberValue());
                            break;
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue());
                            System.out.print("\t");
                            break;
                        case STRING:
                            System.out.print(cell.getStringCellValue());
                            System.out.print("\t");
                            break;
                        case ERROR:
                            System.out.print("!");
                            System.out.print("\t");
                            break;
                    }

                }
                System.out.println("");
            }
        }
    }

}
