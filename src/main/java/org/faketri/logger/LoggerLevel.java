package org.faketri.logger;

public enum LoggerLevel {
    TRACE,
    DEBUG,
    INFO,
    ERROR;

    public boolean isEnabledFor(LoggerLevel configuredLevel) {
        return this.ordinal() >= configuredLevel.ordinal();
    }
}
