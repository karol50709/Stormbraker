package com.edc.stormbreaker;

public interface Saves {

    public void saveGui(String file, String delimiter, String Endofline, String Charset, Core controler);

    public void saveNonGui(String file, String delimiter, String Endofline, String Charset);

}
