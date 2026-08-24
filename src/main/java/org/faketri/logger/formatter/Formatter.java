package org.faketri.logger.formatter;

import org.faketri.logger.LoggerLevel;

public interface Formatter {

    String format(LoggerLevel level, String name, String message);
    String formatWithArgs(LoggerLevel level, String name, String message, Object... args);
}
