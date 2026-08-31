package org.faketri.command;

import org.faketri.core.BuildSystemDetector;
import org.faketri.dto.BuildSystem;
import org.faketri.reader.AbstractDirectoryReader;
import org.faketri.reader.AbstractFileReader;

import java.io.File;
import java.util.List;

public class ParseProjectCommand {
    private final String path;

    private final AbstractFileReader dataReader;
    private final AbstractDirectoryReader directoryReader;

    private List<File> files;

    private BuildSystem buildSystem;

    public ParseProjectCommand(String path, AbstractFileReader dataReader, AbstractDirectoryReader directoryReader) {
        this.path = path;
        this.dataReader = dataReader;
        this.directoryReader = directoryReader;
        detectBuildSystem();
    }

    private void detectBuildSystem(){
        files = readDirectory(0);
        buildSystem = BuildSystemDetector.detect(files);
    }

    public List<File> readDirectory(int deep){
        return directoryReader.read(path);
    }

    public BuildSystem getBuildSystem(){
        return buildSystem;
    }
}
