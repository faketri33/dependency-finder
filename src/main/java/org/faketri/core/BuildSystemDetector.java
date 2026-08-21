package org.faketri.core;

import org.faketri.dto.BuildSystem;
import org.faketri.exceptions.NotFindBuildSystemException;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BuildSystemDetector {
    private BuildSystemDetector() {
        /* This utility class should not be instantiated */
    }

    private static final Map<String, String> buildSystems;

    static {
        buildSystems = Map.of("pom.xml", BuildSystemConstants.MAVEN.name());
    }

    /**
     *  Find build system in files directory
     *  @return - {@link BuildSystem}.
     *  @throws org.faketri.exceptions.NotFindBuildSystemException if not find system.
     *  */
    public static BuildSystem detect(List<File> files){
        Objects.requireNonNull(files);
        for (File file : files)
            if (buildSystems.containsKey(file.getName()))
                return new BuildSystem(buildSystems.get(file.getName()), file.getAbsolutePath());
        throw new NotFindBuildSystemException();
    }
}
