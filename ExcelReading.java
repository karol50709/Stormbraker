package com.edc.stormbreaker;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExcelReading implements Saves{



    public static void echoAsCSV(Sheet sheet,String delim,ArrayList<String> list) {
        Row row = null;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < row.getLastCellNum(); j++) {

                if (row.getCell(j)==null){
                    stringBuilder.append(";");
                }
                else if(!row.getCell(j).toString().contains(delim)) {
                    row.getCell(j).setCellType(CellType.STRING);
                    stringBuilder.append(row.getCell(j) + delim);
                }
                else{
                    stringBuilder.append('"'+row.getCell(j).toString()+'"' + delim);
                }
            }
            list.add(stringBuilder.toString());


        }}

    public static void saveToCSV(ArrayList<String> list,Core controller,String eof, String outputPath,String charset) {

        /*if (Endofline=="windows") {
            eof = "\r\n";
        } else {
            eof = "\n";
        } */

        try {
            System.out.println(eof);
            System.out.println("Ilość elementów: "+list.size());
            Writer writer = new PrintWriter(outputPath, charset);
            for (int i = 0; i < list.size(); i++) {
                writer.write(list.get(i));
                writer.write(eof);
            }
            try {
                writer.close();
            } catch (IOException e) {
                controller.showWarningWindow("Wystąpił wyjątek", "IOException", e.getMessage());
            }

        } catch (IOException e) {
            controller.showWarningWindow("Wystąpił wyjątek", "IOException", e.getMessage());
        }
    }

        public static void saveToCSV(ArrayList<String> list,String Endofline, String outputPath,String charset){


            String eof="";
            if (Endofline.equals("windows")) {
                eof = "\r\n";
            } else {
                eof = "\n";
            }
            try {
                System.out.println("Ilość rekordów: "+list.size()+1);
                Writer writer = new PrintWriter(outputPath,charset);
                for (int i = 0; i < list.size(); i++) {
                    writer.write(list.get(i));
                    writer.write(eof);
                }
                try{
                    writer.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }

            }


            catch (IOException e){
                e.printStackTrace();
            }




    }



    @Override
    public void saveGui(String file, String delimiter, String Endofline, String Charset, Core controler) {
        long timestart = System.currentTimeMillis();

        try {

            Workbook wb = WorkbookFactory.create(new File(file));


            for(int i=0;i<wb.getNumberOfSheets();i++) {
                ArrayList<String> stringArrayList = new ArrayList<>();
                System.out.println(wb.getSheetAt(i).getSheetName());
                echoAsCSV(wb.getSheetAt(i),delimiter,stringArrayList);
                saveToCSV(stringArrayList,controler,Endofline,file+wb.getSheetName(i)+".csv",Charset);
            }
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
        long time = System.currentTimeMillis() - timestart;
        controler.showInformationWindow("OK!", "Wygenerowano plik csv", "Czas: "+time);
    }

    @Override
    public void saveNonGui(String file, String delimiter, String Endofline, String Charset) {

        //String tempName= file.replace("/",System.getProperty("file.separator"));
        long timestart = System.currentTimeMillis();

        try {

            Workbook wb = WorkbookFactory.create(new File(file));

            Logger.getLogger(ExcelReading.class.getName()).log(Level.INFO,"W pliku "+file+" występuje arkuszy: "+wb.getNumberOfSheets());

            for(int i=0;i<wb.getNumberOfSheets();i++) {
                ArrayList<String> stringArrayList = new ArrayList<>();
                Logger.getLogger(ExcelReading.class.getName()).log(Level.INFO,"Przetwarzam arkusz  "+wb.getSheetAt(i).getSheetName());
                echoAsCSV(wb.getSheetAt(i),delimiter,stringArrayList);
                saveToCSV(stringArrayList,Endofline,file+wb.getSheetName(i)+".csv",Charset);
                Logger.getLogger(ExcelReading.class.getName()).log(Level.INFO,"Wygenerowano plik "+file+wb.getSheetName(i)+".csv");

            }
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExcelReading.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
        long time = System.currentTimeMillis() - timestart;
        Logger.getLogger(ExcelReading.class.getName()).log(Level.INFO,"Wygenerowano pliki .csv Czas: " +time + " ms");
    }

    }

