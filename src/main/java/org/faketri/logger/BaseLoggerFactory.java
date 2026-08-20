package org.faketri.logger;

public class BaseLoggerFactory{
    private BaseLoggerFactory() {
        /* This utility class should not be instantiated */
    }


    public static Logger getLogger(Class<?> clazz) {
        return new IOPLogger(clazz.getSimpleName());
    }
}
