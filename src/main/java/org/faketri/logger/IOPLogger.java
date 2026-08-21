package org.faketri.logger;

import java.util.function.Supplier;

public class IOPLogger extends BaseLogger implements Logger{

    private final String name;
    private final Formatter formatter;

    public IOPLogger(String name) {
        super(LoggerLevel.INFO);
        this.name = name;
        formatter = new DefaultFormatter();
    }

    public IOPLogger(String name, Formatter formatter, LoggerLevel level) {
        super(level);
        this.name = name;
        this.formatter = formatter;
    }

    @Override
    public void info(String msg) {
        handler(LoggerLevel.INFO, () -> formatter.format(LoggerLevel.INFO, name, msg));
    }

    @Override
    public void info(String msg, Object... obj) {
        handler(LoggerLevel.INFO, () -> formatter.formatWithArgs(LoggerLevel.INFO, name, msg, obj));
    }

    @Override
    public void debug(String msg) {
        handler(LoggerLevel.DEBUG, () -> formatter.format(LoggerLevel.DEBUG, name, msg));
    }

    @Override
    public void debug(String msg, Object... obj) {
        handler(LoggerLevel.DEBUG, () -> formatter.formatWithArgs(LoggerLevel.DEBUG, name, msg, obj));
    }

    @Override
    public void error(String msg) {
        handler(LoggerLevel.ERROR, () -> formatter.format(LoggerLevel.ERROR, name, msg));
    }

    @Override
    public void error(String msg, Object... obj) {
        handler(LoggerLevel.ERROR, () -> formatter.formatWithArgs(LoggerLevel.ERROR, name, msg, obj));
    }
}
