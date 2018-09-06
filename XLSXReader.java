package com.edc.stormbreaker;




import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class XLSXReader implements Saves {
    @Override
    public void saveGui(String file, String delimiter, String Endofline, String Charset, Core controler) {

            try{
                    InputStream ExcelFileToRead = new FileInputStream(file);
                    XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);


                    for (int i=0; i<wb.getNumberOfSheets();i++) {

                        XSSFSheet sheet = wb.getSheetAt(0);
                        XSSFRow row;
                        XSSFCell cell;

                        Iterator rows = sheet.rowIterator();

                        ArrayList<String> stringArrayList = new ArrayList<>();

                        while (rows.hasNext()) {
                            row = (XSSFRow) rows.next();
                            Iterator cells = row.cellIterator();
                            String str;
                            StringBuilder stringBuilder = new StringBuilder();
                            while (cells.hasNext()) {
                                cell = (XSSFCell) cells.next();

                                if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                                    //System.out.print(cell.getStringCellValue() + " ");
                                    stringBuilder.append(cell.getStringCellValue() +delimiter);
                                } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                                    stringBuilder.append(cell.getNumericCellValue() +delimiter);
                                } else {
                                    //U Can Handel Boolean, Formula, Errors
                                }
                            }
                            //stringBuilder.append(Endofline);
                            stringArrayList.add(stringBuilder.toString());
                        }

                        ExcelReading.saveToCSV(stringArrayList,controler,Endofline,file+wb.getSheetName(i)+".csv",Charset);
                    }

                }


        catch (IOException e){
                e.printStackTrace();
        }
/*        catch (InvalidFormatException e){
                e.printStackTrace();
        }*/

    }

    @Override
    public void saveNonGui(String file, String delimiter, String Endofline, String Charset) {

    }
}
