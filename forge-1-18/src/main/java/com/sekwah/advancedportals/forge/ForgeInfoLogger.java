package com.sekwah.advancedportals.forge;

//import com.sekwah.advancedportals.core.util.InfoLogger;
import org.slf4j.Logger;

public class ForgeInfoLogger{// extends InfoLogger {
    private final Logger logger;

    public ForgeInfoLogger(Logger logger) {
        super();
        this.logger = logger;
    }

    //@Override
    public void warning(String s) {
        logger.warn(s);
    }

//    @Override
    public void info(String s) {
        logger.info(s);
    }

//    @Override
    public void error(Exception e) {
        logger.error(e.getMessage(), e);
    }
}
