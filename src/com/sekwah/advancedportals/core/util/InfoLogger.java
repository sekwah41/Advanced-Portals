package com.sekwah.advancedportals.core.util;

public abstract class InfoLogger {

    /**
     * Problematic messages
     * @param s
     */
    public abstract void logWarning(String s);

    /**
     * General information logging
     * @param s
     */
    public abstract void log(String s);
}
