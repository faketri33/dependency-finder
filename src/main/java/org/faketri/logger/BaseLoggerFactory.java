package org.faketri.logger;

public class BaseLoggerFactory{

    private static LoggerConfiguration configuration;

    private BaseLoggerFactory() {
        /* This utility class should not be instantiated */
    }

    public static Logger getLogger(Class<?> clazz) {
        return IOPLogger.of(clazz.getSimpleName(), getConfiguration());
    }

    public static LoggerConfiguration getConfiguration() {
        if (configuration == null) configuration = new LoggerConfiguration();
        return configuration;
    }

    public static void setConfiguration(LoggerConfiguration configuration) {
        BaseLoggerFactory.configuration = configuration;
    }
}
