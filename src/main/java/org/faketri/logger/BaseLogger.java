package org.faketri.logger;

public abstract class BaseLogger {

    private LoggerLevel level;

    BaseLogger(LoggerLevel level) {
        this.level = level;
    }

    protected void handler(LoggerLevel loggerLevel, String msg){
        if (loggerLevel.isEnabledFor(getLevel()))
            print(msg);
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
