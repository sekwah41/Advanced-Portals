package com.sekwah.advancedportals.core.util;

public abstract class InfoLogger {

    /**
     * Problematic messages
     * @param s warning message
     */
    public abstract void logWarning(String s);

    /**
     * General information logging
     * @param s info message
     */
    public abstract void log(String s);
}
