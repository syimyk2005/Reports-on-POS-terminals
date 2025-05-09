package report_service.service;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;


public class ReportGeneratorService {
    public static void main(String[] args) {
            try {
                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = workbook.createSheet("FirstSheet");
                HSSFRow rowHead = sheet.createRow((short) 0);
                rowHead.createCell(0).setCellValue("No.");
                rowHead.createCell(1).setCellValue("Name");
                rowHead.createCell(2).setCellValue("Address");
                rowHead.createCell(3).setCellValue("Email");

                HSSFRow row = sheet.createRow((short) 1);
                row.createCell(0).setCellValue("1");
                row.createCell(1).setCellValue("Sankumarsingh");
                row.createCell(2).setCellValue("India");
                row.createCell(3).setCellValue("sankumarsingh@gmail.com");

                FileOutputStream fileOut = new FileOutputStream("report.xlsx");
                workbook.write(fileOut);
                fileOut.close();
                workbook.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
