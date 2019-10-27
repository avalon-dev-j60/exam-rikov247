package tests.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JTable;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;

public class ReadExcelDemo {

//    public static void main(String[] args) throws IOException {
// 
//        // Read XSL file
//        FileInputStream inputStream = new FileInputStream(new File("F:/Т-образный ВПРАВО — НОВЫЙ.xls"));
// 
//        // Get the workbook instance for XLS file
//        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
// 
//        // Get first sheet from the workbook
//        HSSFSheet sheet = workbook.getSheetAt(0);
// 
//        // Get iterator to all the rows in current sheet
//        Iterator<Row> rowIterator = sheet.iterator();
// 
//        while (rowIterator.hasNext()) {
//            Row row = rowIterator.next();
//            // Get iterator to all cells of current row
//            Iterator<Cell> cellIterator = row.cellIterator();
// 
//            while (cellIterator.hasNext()) {
//                Cell cell = cellIterator.next();
// 
//                // Change to getCellType() if using POI 4.x
//                CellType cellType = cell.getCellType();
// 
//                switch (cellType) {
//                case _NONE:
//                    System.out.print("");
//                    System.out.print("\t");
//                    break;
//                case BOOLEAN:
//                    System.out.print(cell.getBooleanCellValue());
//                    System.out.print("\t");
//                    break;
//                case BLANK:
//                    System.out.print("");
//                    System.out.print("\t");
//                    break;
////                case FORMULA:
////                    // Formula
////                    System.out.print(cell.getCellFormula());
////                    System.out.print("\t");
//                     
////                    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
////                    // Print out value evaluated by formula
////                    System.out.print(evaluator.evaluate(cell).getNumberValue());
////                    break;
//                case NUMERIC:
//                    System.out.print(cell.getNumericCellValue());
//                    System.out.print("\t");
//                    break;
//                case STRING:
//                    System.out.print(cell.getStringCellValue());
//                    System.out.print("\t");
//                    break;
//                case ERROR:
//                    System.out.print("!");
//                    System.out.print("\t");
//                    break;
//                }
// 
//            }
//            System.out.println("");
//        }
//    }
    public void ReadExcelDemo(JTable jtable) throws IOException {
        // Read XSL file
        FileInputStream inputStream = new FileInputStream(new File("F:/Т-образный ВПРАВО — НОВЫЙ.xls"));

        // Get the workbook instance for XLS file
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

        workbook.setMissingCellPolicy(MissingCellPolicy.RETURN_BLANK_AS_NULL);
        DataFormatter fmt = new DataFormatter();

        for (int sn = 0; sn < workbook.getNumberOfSheets(); sn++) {
            Sheet sheet = workbook.getSheetAt(sn);
            for (int rn = sheet.getFirstRowNum(); rn <= sheet.getLastRowNum(); rn++) {
                Row row = sheet.getRow(rn);
                if (row == null) {
                    // There is no data in this row, handle as needed
                } else {
                    // Row "rn" has data
                    for (int cn = 0; cn < row.getLastCellNum(); cn++) {
                        Cell cell = row.getCell(cn);
                        if (cell == null) {
                            // This cell is empty/blank/un-used, handle as needed
                        } else {
                            String cellStr = fmt.formatCellValue(cell);
                            // Do something with the value
                        }
                    }
                }
            }
        }

        // Get first sheet from the workbook
        HSSFSheet sheet = workbook.getSheetAt(0);

        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // Get iterator to all cells of current row
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                // Change to getCellType() if using POI 4.x
                CellType cellType = cell.getCellType();

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
//                case FORMULA:
//                    // Formula
//                    System.out.print(cell.getCellFormula());
//                    System.out.print("\t");

//                    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
//                    // Print out value evaluated by formula
//                    System.out.print(evaluator.evaluate(cell).getNumberValue());
//                    break;
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
