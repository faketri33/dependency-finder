package org.faketri.logger.formatter;


import org.faketri.logger.LoggerLevel;

import java.util.Objects;

public class DefaultFormatter implements Formatter{

    private enum Type {
        NAME,
        MESSAGE,
    }

    private String colorPaletteByLogLevel(LoggerLevel level){
        return switch (level) {
            case TRACE -> ConsoleColors.CYAN + level + ConsoleColors.RESET;
            case DEBUG -> ConsoleColors.YELLOW_BOLD + level + ConsoleColors.RESET;
            case INFO -> ConsoleColors.GREEN_BOLD + level + ConsoleColors.RESET;
            case ERROR -> ConsoleColors.RED_BOLD + level + ConsoleColors.RESET;
        };
    }

    private String colorPaletteByMessage(Type type, String message){
        return switch (type){
            case NAME -> ConsoleColors.RED_BOLD + message + ConsoleColors.RESET;
            case MESSAGE -> ConsoleColors.BLUE + message + ConsoleColors.RESET;
            default -> "";
        };
    }

    @Override
    public String format(LoggerLevel level, String name, String message) {
        return colorPaletteByLogLevel(level) +
               colorPaletteByMessage(Type.NAME, name) +
               colorPaletteByMessage(Type.MESSAGE, message);
    }

    @Override
    public String formatWithArgs(LoggerLevel level, String name, String message, Object... args) {
        for (Object obj : args)
            message = message.replaceFirst("\\{\\}", Objects.toString(obj, "unknown"));
        return format(level, name, message);
    }
}
