package org.faketri.logger;

public interface Formatter {

    String format(LoggerLevel level, String name, String message);
    String formatWithArgs(LoggerLevel level, String name, String message, Object... args);
}
