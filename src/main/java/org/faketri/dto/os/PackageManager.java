package org.faketri.dto.os;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class PackageManager {
    private final EPackage manager;

    public PackageManager(String systemName){
        this.manager = findPackageManager(systemName);
    }

    public String getName() {
        return manager.getName();
    }

    EPackage findPackageManager(String name){
        if (name.toLowerCase().contains("windows")) return null;

        return Arrays.stream(EPackage.values())
                .filter(e -> Files.isExecutable(Path.of(e.getCommand())))
                .findFirst()
                .orElseThrow();
    }
}
