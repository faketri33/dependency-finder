package org.faketri.logger;

public interface Logger {

    void info(String msg);
    void info(String msg, Object... obj);

    void debug(String msg);
    void debug(String msg, Object... obj);

    void error(String msg);
    void error(String msg, Object... obj);
}
