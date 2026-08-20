package org.faketri.logger;


public class DefaultFormatter implements Formatter{

    @Override
    public String format(LoggerLevel level, String name, String message) {
        return ConsoleColors.GREEN_BOLD + level + ConsoleColors.RESET +
                ConsoleColors.BLUE + name + ConsoleColors.RESET +
                ConsoleColors.RED_BOLD + message + ConsoleColors.RESET;
    }

    @Override
    public String formatWithArgs(LoggerLevel level, String name, String message, Object... args) {
        for (Object obj : args)
            message = message.replaceFirst("\\{\\}", obj.toString());
        return format(level, name, message);
    }
}
