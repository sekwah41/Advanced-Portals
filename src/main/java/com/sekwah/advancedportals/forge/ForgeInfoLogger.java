package com.sekwah.advancedportals.forge;

import com.sekwah.advancedportals.core.util.InfoLogger;
import org.apache.logging.log4j.Logger;

public class ForgeInfoLogger extends InfoLogger {

    private final Logger logger;

    public ForgeInfoLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void logWarning(String s) {
        this.logger.warn(s);
    }

    @Override
    public void log(String s) {
        this.logger.info(s);
    }
}
