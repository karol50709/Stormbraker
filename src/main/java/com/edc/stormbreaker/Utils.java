package com.edc.stormbreaker;

import java.io.File;

public class Utils {

    public static boolean validfile(String path){
        boolean temp=true;
        File file = new File(path);
        String lowerfilename=file.getAbsolutePath().toLowerCase();
        if (!file.exists()){
            temp=false;
        }
        if (!file.canRead()){
            temp=false;
        }
        if(!lowerfilename.endsWith("csv") && !lowerfilename.endsWith("txt") && !lowerfilename.endsWith("xls")){
            temp=false;
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
