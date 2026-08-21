package org.faketri.logger;

import java.util.function.Supplier;

public abstract class BaseLogger {

    private LoggerLevel level;

    BaseLogger(LoggerLevel level) {
        this.level = level;
    }

    void handler(LoggerLevel level, Supplier<String> supplier){
        if (level.isEnabledFor(getLevel()))
            print(supplier.get());
    }

    private void print(String message){
        IO.println(message);
    }

    public LoggerLevel getLevel() {
        return level;
    }

    public void setLevel(LoggerLevel level) {
        this.level = level;
    }
}
