package org.faketri.logger;

public class LoggerConfiguration {

    private LoggerLevel level;
    private Formatter formatter;

    public LoggerConfiguration() {
        setLevel(LoggerLevel.INFO);
        setFormatter(new DefaultFormatter());
    }

    protected LoggerLevel getLevel() {
        return level;
    }

    public void setLevel(LoggerLevel level) {
        this.level = level;
    }

    protected Formatter getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }
}
