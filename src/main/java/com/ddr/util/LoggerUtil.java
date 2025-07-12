package com.ddr.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    private LoggerUtil() { }

    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
}
