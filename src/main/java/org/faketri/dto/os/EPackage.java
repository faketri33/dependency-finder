package org.faketri.dto.os;

public enum EPackage {

    DNF("Dandified YUM","/bin/dnf"),
    APT("Advanced Package Tool","/bin/apt"),
    PACMAN("Pacman","/bin/pacman"),
    BREW("Homebrew", "/bin/brew");

    private final String name;
    private final String command;

    EPackage(String name, String command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }
}
