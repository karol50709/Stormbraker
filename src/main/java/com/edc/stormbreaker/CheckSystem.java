package com.edc.stormbreaker;

public class CheckSystem {

    private static String OS = System.getProperty("os.name").toLowerCase();



    public static boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }
}