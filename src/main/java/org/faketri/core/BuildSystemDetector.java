package org.faketri.core;

import org.faketri.dto.BuildSystem;

import java.io.File;
import java.util.List;
import java.util.Map;

public class BuildSystemDetector {
    private BuildSystemDetector() {
        /* This utility class should not be instantiated */
    }

    private static final Map<String, String> buildSystems;

    static {
        buildSystems = Map.of("pom.xml", BuildSystemConstants.MAVEN.name());
    }

    public static BuildSystem detect(List<File> files){
        for (File file : files) {
            if (buildSystems.containsKey(file.getName()))
                return new BuildSystem(buildSystems.get(file.getName()), file.getAbsolutePath());
        }
        return null;
    }
}
