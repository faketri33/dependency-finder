package org.faketri.logger;

import java.util.function.Supplier;

public abstract class BaseLogger {

    private final LoggerConfiguration configuration;

    BaseLogger(LoggerConfiguration configuration) {
        this.configuration = configuration;
    }

    void handler(LoggerLevel level, Supplier<String> supplier){
        if (level.isEnabledFor(getLevel()))
            print(supplier.get());
    }

    private void print(String message){
        IO.println(message);
    }

    public LoggerLevel getLevel() {
        return configuration.getLevel();
    }

    public LoggerConfiguration getConfiguration() {
        return configuration;
    }
}
