package org.faketri.logger;

public class BaseLoggerFactory{

    private static IOPLogger logger;

    private BaseLoggerFactory() {
        /* This utility class should not be instantiated */
    }

    private static IOPLogger getIopInstance(String name){
        if (logger == null) logger = new IOPLogger(name);
        return logger;
    }

    public static Logger getLogger(Class<?> clazz) {
        return getIopInstance(clazz.getSimpleName());
    }
}
