package org.faketri.core;

import org.faketri.dto.BuildSystem;
import org.faketri.dto.Modules;
import org.faketri.exceptions.GlobalProxyHandler;
import org.faketri.logger.BaseLoggerFactory;
import org.faketri.logger.Logger;
import org.faketri.parser.AbstractDataParser;
import org.faketri.reader.AbstractDirectoryReader;
import org.faketri.reader.AbstractFileReader;

import java.io.File;
import java.util.List;
import java.util.Objects;

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

        BuildSystem bs = Objects.requireNonNull(BuildSystemDetector.detect(files));
        log.info(bs.getFileName());
        var bf = dataReader.read(bs.getFileName());

        AbstractDataParser proxyParse = GlobalProxyHandler.newProxy(dataParserFactory.getParser(bs), AbstractDataParser.class);

        return proxyParse.parse(bf);
    }
}
