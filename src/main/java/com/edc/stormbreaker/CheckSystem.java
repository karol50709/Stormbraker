package com.edc.stormbreaker;

public class CheckSystem {

    private static String OS = "unkown";

    public CheckSystem() {
        OS = System.getProperty("os.name").toLowerCase();
    }

    public boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }
}