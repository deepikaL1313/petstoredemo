package com.petstore.api.utils;

import java.io.FileInputStream;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

public class ExcelUtility {

    // Logger for logging information
    private Logger log = LogManager.getLogger(this.getClass().getName());
    /**
     * Retrieves data from a specified Excel sheet based on a keyword.
     *
     * Excel File format
     *
     * <table border="1">
     *	<tr ><td>keyword<td>          <td>           <td>         <td></tr>
     *  <tr><td>       <td> data 1.1 <td> data 2.1  <td>         <td></tr>
     *  <tr><td>       <td> data 1.2 <td> data 2.2  <td>         <td></tr>
     *  <tr><td>       <td>          <td>           <td> keyword <td></tr>
     * </table>
     *
     *
     * @param sheetName The name of the sheet to read data from.
     *  @param keyword The keyword to locate the data table.
     *  @return A 2D array containing the extracted data.
     **/

    public String[][] getDataFromExcelSheet(String relativeExcelFile, String sheetName, String keyword){
        String[][] tabArray=null;
        try{

            InputStream inputStream =new ClassPathResource(relativeExcelFile).getInputStream();

            // Open the workbook and sheet
            jxl.Workbook workbook = Workbook.getWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);

            // Find start and end cells of the data table
            int startRow, startCol, endRow, endCol, ci, cj;
            jxl.Cell tableStart = sheet.findCell(keyword);
            startRow = tableStart.getRow();
            startCol = tableStart.getColumn();
            jxl.Cell tableEnd = sheet.findCell(keyword, startCol + 1, startRow + 1, 100, 64000, false);
            endRow = tableEnd.getRow();
            endCol = tableEnd.getColumn();

            // Log the positions of the start and end cells
            log.info("startRow=" + startRow + ", endRow=" + endRow + ", " +
                    "startCol=" + startCol + ", endCol=" + endCol);

            // Initialize array to store table data
            tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];
            ci = 0;

            // Read table data into the array
            for (int i = startRow + 1; i < endRow; i++, ci++){
                cj = 0;
                for (int j = startCol + 1; j < endCol; j++, cj++){
                    tabArray[ci][cj] = sheet.getCell(j, i).getContents();
                }
            }
        } catch (Exception e) {
            log.error("Error in method getDataFromExcelSheet(): " + e);
        }
        return tabArray;
    }
}
