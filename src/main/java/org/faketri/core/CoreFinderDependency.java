package org.faketri.core;

import org.faketri.dto.BuildSystem;
import org.faketri.dto.Modules;
import org.faketri.logger.BaseLoggerFactory;
import org.faketri.logger.Logger;
import org.faketri.parser.AbstractDataParser;
import org.faketri.reader.AbstractDirectoryReader;
import org.faketri.reader.AbstractFileReader;

import java.io.File;
import java.util.List;

public class CoreFinderDependency {

    private static final Logger log = BaseLoggerFactory.getLogger(CoreFinderDependency.class);

    private final String path;

    private final AbstractFileReader dataReader;
    private final AbstractDirectoryReader directoryReader;

    private final DataParserFactory dataParserFactory;

    public CoreFinderDependency(String path, AbstractFileReader dataReader, AbstractDirectoryReader directoryReader, DataParserFactory dataParserFactory) {
        this.path = path;
        this.dataReader = dataReader;
        this.directoryReader = directoryReader;
        this.dataParserFactory = dataParserFactory;
    }

    public Modules getAll(){
        List<File> files = directoryReader.read(path);

        BuildSystem bs = BuildSystemDetector.detect(files);

        log.debug(bs.getFileName());
        var bf = dataReader.read(bs.getFileName());

        AbstractDataParser proxyParse = dataParserFactory.getParser(bs);
        Modules module = proxyParse.parse(bf);
        module.setBuildSystem(bs);

        return module;
    }
}
