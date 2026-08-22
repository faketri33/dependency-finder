package org.faketri.logger;



public class IOPLogger extends BaseLogger implements Logger{

    private final String name;

    private IOPLogger(String name, LoggerConfiguration configuration) {
        super(configuration);
        this.name = name;
    }

    public static IOPLogger of(String name){
        LoggerConfiguration configuration = new LoggerConfiguration();
        return new IOPLogger(name, configuration);
    }

    public static IOPLogger of(String name, LoggerConfiguration configuration){
        return new IOPLogger(name, configuration);
    }


    @Override
    public void info(String msg) {
        handler(LoggerLevel.INFO, () -> getConfiguration().getFormatter().format(LoggerLevel.INFO, name, msg));
    }

    @Override
    public void info(String msg, Object... obj) {
        handler(LoggerLevel.INFO, () -> getConfiguration().getFormatter().formatWithArgs(LoggerLevel.INFO, name, msg, obj));
    }

    @Override
    public void debug(String msg) {
        handler(LoggerLevel.DEBUG, () -> getConfiguration().getFormatter().format(LoggerLevel.DEBUG, name, msg));
    }

    @Override
    public void debug(String msg, Object... obj) {
        handler(LoggerLevel.DEBUG, () -> getConfiguration().getFormatter().formatWithArgs(LoggerLevel.DEBUG, name, msg, obj));
    }

    @Override
    public void error(String msg) {
        handler(LoggerLevel.ERROR, () -> getConfiguration().getFormatter().format(LoggerLevel.ERROR, name, msg));
    }

    @Override
    public void error(String msg, Object... obj) {
        handler(LoggerLevel.ERROR, () -> getConfiguration().getFormatter().formatWithArgs(LoggerLevel.ERROR, name, msg, obj));
    }
}
