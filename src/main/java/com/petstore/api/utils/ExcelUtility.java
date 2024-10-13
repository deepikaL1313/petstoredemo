package com.petstore.api.utils;

import jxl.Sheet;
import jxl.Workbook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;

public class ExcelUtility {

    private Logger log = LogManager.getLogger(this.getClass().getName());
    /**
     * @param relativeExcelFile - The relative path to the excel file from current project directory.
     *
     * Example: If your project name is saucedemo and your .xls file is in the directory com/ecommerce/frontend/data
     * then relativeExcelFile should be "com/ecommerce/frontend/data"
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
     * @param sheetName - This represents the sheet name to be read from.
     * @param keyword - This represents the start/end keyword.
     *
     * @return 2D data array
     */

    public String[][] getDataFromExcelSheet(String relativeExcelFile, String sheetName, String keyword){
        String[][] tabArray=null;
        try{

            FileInputStream io = new FileInputStream(relativeExcelFile);
            jxl.Workbook workbook = Workbook.getWorkbook(io);
            Sheet sheet = workbook.getSheet(sheetName);
            int startRow,startCol, endRow, endCol,ci,cj;
            jxl.Cell tableStart=sheet.findCell(keyword);
            startRow=tableStart.getRow();
            startCol=tableStart.getColumn();
            jxl.Cell tableEnd= sheet.findCell(keyword, startCol+1,startRow+1, 100, 64000,  false);

            endRow=tableEnd.getRow();
            endCol=tableEnd.getColumn();
            log.info("startRow="+startRow+", endRow="+endRow+", " +
                    "startCol="+startCol+", endCol="+endCol);
            tabArray=new String[endRow-startRow-1][endCol-startCol-1];
            ci=0;

            for (int i=startRow+1;i<endRow;i++,ci++){
                cj=0;
                for (int j=startCol+1;j<endCol;j++,cj++){
                    tabArray[ci][cj]=sheet.getCell(j,i).getContents();
                }
            }
        }
        catch (Exception e)    {
            log.error("Error in method getDataFromExcelSheet()"+ e);
        }
        return(tabArray);
    }
}
