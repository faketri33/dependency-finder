package org.faketri.core;

import org.faketri.dto.BuildSystem;
import org.faketri.dto.Project;
import org.faketri.parser.AbstractDataParser;
import org.faketri.reader.AbstractDirectoryReader;
import org.faketri.reader.AbstractFileReader;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class CoreFinderDependency {

    private final AbstractFileReader dataReader;
    private final AbstractDirectoryReader directoryReader;

    private final DataParserFactory dataParserFactory;

    public CoreFinderDependency(AbstractFileReader dataReader, AbstractDirectoryReader directoryReader, DataParserFactory dataParserFactory) {
        this.dataReader = dataReader;
        this.directoryReader = directoryReader;
        this.dataParserFactory = dataParserFactory;
    }

    public Project getAll(String path){
        List<File> files = directoryReader.read(path);

        BuildSystem bs = Objects.requireNonNull(BuildSystemDetector.detect(files));
        IO.println(bs.getFileName());
        var bf = dataReader.read(bs.getFileName());

        AbstractDataParser dataParser = dataParserFactory.getParser(bs);

        return dataParser.parse(bf);
    }
}
