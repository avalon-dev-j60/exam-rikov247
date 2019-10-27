package tests.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
 
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
 
public class UpdateExcelDemo {
 
   public static void main(String[] args) throws IOException {
 
       File file = new File("F:/Т-образный ВПРАВО — НОВЫЙ.xls");
       // Read XSL file
       FileInputStream inputStream = new FileInputStream(file);
 
       // Get the workbook instance for XLS file
       HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
 
       // Get first sheet from the workbook
       HSSFSheet sheet = workbook.getSheetAt(0);
 
       HSSFCell cell = sheet.getRow(13).getCell(8);
       cell.setCellValue(1);
      
       cell = sheet.getRow(14).getCell(8);
       cell.setCellValue(2);
      
       cell = sheet.getRow(15).getCell(8);
       cell.setCellValue(3);
 
       inputStream.close();
 
       // Write File
//       FileOutputStream out = new FileOutputStream(file);
//       workbook.write(out);
//       out.close();
       
       // Write in new File
       FileOutputStream out1 = new FileOutputStream("F:/Дороги.xls");
       workbook.write(out1);
       out1.close();
 
   }
 
}
