package com.edc.stormbreaker;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CSVReading implements Saves {



    @Override
    public void saveGui(String file, String delimiter, String Endofline, String Charset, Core controler)  {
        long timestart = System.currentTimeMillis();
        Core view = controler;
        File file1 = new File(file);
        String name = file1.getName();
        ArrayList<ArrayList<String>> arList = new ArrayList<ArrayList<String>>();
        ArrayList<String> al = null;
        //view.increaseprogres();



        try {
            Scanner scanner = new Scanner(new FileInputStream(file1), Charset).useDelimiter(Endofline);


            while (scanner.hasNext()) {
                al = new ArrayList<String>();
                String strar[] = scanner.next().split(delimiter);
                //view.increaseprogres();

                for (int j = 0; j < strar.length; j++) {

                    String edit = strar[j];
                    al.add(edit);
                }

                arList.add(al);
            }
            //view.increaseprogres();
            try {
                HSSFWorkbook hwb = new HSSFWorkbook();
                HSSFSheet sheet = hwb.createSheet("new sheet");

                for (int k = 0; k < arList.size(); k++) {
                    ArrayList<String> ardata = (ArrayList<String>) arList.get(k);
                    HSSFRow row = sheet.createRow((int) 0 + k);

                    for (int p = 0; p < ardata.size(); p++) {

                        HSSFCell cell = row.createCell((int) p);
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(ardata.get(p).toString());

                    }
                }
                // view.increaseprogres();


                FileOutputStream fileOut = new FileOutputStream(
                        file1.getParentFile().getAbsolutePath() + "\\" + name
                                + ".xls");


                hwb.write(fileOut);
                fileOut.close();

                System.out.println(name + ".xls został wygenerowany");
                //view.increaseprogres();
                long time = System.currentTimeMillis() - timestart;

                view.showInformationWindow("OK!", "Wygenerowano plik " + file1.getParentFile().getAbsolutePath() + "\\" + name
                        + ".xls" + "\r\nCzas: " + time + " ms", "");


            } catch (Exception ex) {
                ex.printStackTrace();
                view.showWarningWindow("Wystąpił wyjątek","Exception",ex.getMessage());
            }
        }
        catch (FileNotFoundException e){
            view.showWarningWindow("Wystąpił wyjątek","FileNotFoundException",e.getMessage());
        }
    }

    @Override
    public void saveNonGui(String file, String delimiter, String Endofline, String Charset) {
        File file1 = new File(file);
        String name = file1.getName();
        ArrayList<ArrayList<String>> arList = new ArrayList<ArrayList<String>>();
        ArrayList<String> al = null;

        //TODO: weryfikacja mechanizmu
        long timestart = System.currentTimeMillis();
        String eof="";
        if (Endofline.equals("windows")) {
            eof = "\r\n";
        } else {
            eof = "\n";
        }
        try {


            Scanner scanner = new Scanner(new FileInputStream(file1), Charset).useDelimiter(eof);


            while (scanner.hasNext()) {
                al = new ArrayList<String>();
                String strar[] = scanner.next().split(delimiter);


                for (int j = 0; j < strar.length; j++) {

                    String edit = strar[j];
                    al.add(edit);
                }

                arList.add(al);
            }

            try {
                HSSFWorkbook hwb = new HSSFWorkbook();
                HSSFSheet sheet = hwb.createSheet("new sheet");

                for (int k = 0; k < arList.size(); k++) {
                    ArrayList<String> ardata = (ArrayList<String>) arList.get(k);
                    HSSFRow row = sheet.createRow((int) 0 + k);

                    for (int p = 0; p < ardata.size(); p++) {

                        HSSFCell cell = row.createCell((int) p);
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(ardata.get(p).toString());

                    }
                }

                //TODO: zmienić znak \\ / w zależności od systemu na którym pracuje - sprawdź

                String temp = "";
                CheckSystem checkSystem = new CheckSystem();
                if (checkSystem.isWindows()) {
                    temp = "\\";
                } else {
                    temp = "/";
                }

                FileOutputStream fileOut = new FileOutputStream(
                        file1.getParentFile().getAbsolutePath() + temp + name
                                + ".xls");


                hwb.write(fileOut);
                fileOut.close();
                Logger.getLogger(CSVReading.class.getName()).log(Level.INFO,"Wygenerowano plik "+name+".xls");
                System.out.println(name + ".xls został wygenerowany");

                long time = System.currentTimeMillis() - timestart;
                Logger.getLogger(CSVReading.class.getName()).log(Level.INFO,"Czas: " + time + " ms");

            } catch (IOException ex) {
                Logger.getLogger(CSVReading.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
        catch (FileNotFoundException ex){
            Logger.getLogger(CSVReading.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}