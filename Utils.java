package com.edc.stormbreaker;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    public static boolean validfile(String path){
        boolean temp=true;
        File file = new File(path);
        Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, "Walidacja pliku: "+file.getAbsolutePath());
        String lowerfilename=file.getAbsolutePath().toLowerCase();
        if (!file.getAbsoluteFile().exists()){
            temp=false;
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, "Plik:"+file.getName()+" nie istnieje");
        }
        if (!file.canRead()){
            temp=false;
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, "Plik:"+file.getName()+" ma brak praw odczytu");
        }
        if(!lowerfilename.endsWith("csv") && !lowerfilename.endsWith("txt") && !lowerfilename.endsWith("xls") && !lowerfilename.endsWith("xlsx")){
            temp=false;
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, "Plik:"+file.getName()+" posiada złe rozszerzenie");
        }

        return temp;
    }

    public static boolean validarguments(String[] args){
        boolean temp=true;
        if (args.length!=8){
            temp=false;
        }
        return temp;
    }
}
