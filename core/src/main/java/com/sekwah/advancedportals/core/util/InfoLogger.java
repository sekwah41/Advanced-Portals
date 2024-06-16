package com.sekwah.advancedportals.core.util;


public abstract class InfoLogger {

    /**
     * Problematic messages
     * @param s warning message
     */
    public abstract void warning(String s);

    /**
     * General information logging
     * @param s info message
     */
    public abstract void info(String s);

    public abstract void error(Exception e);
}
