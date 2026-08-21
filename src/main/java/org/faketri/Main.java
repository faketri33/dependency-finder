package org.faketri;

import org.faketri.core.CoreFinderDependency;
import org.faketri.core.DataParserFactory;
import org.faketri.dto.Dependency;
import org.faketri.dto.Modules;
import org.faketri.proxy.GlobalProxyHandler;
import org.faketri.logger.BaseLoggerFactory;
import org.faketri.logger.Logger;
import org.faketri.logger.LoggerLevel;
import org.faketri.parser.impl.MavenDataParser;
import org.faketri.reader.AbstractDirectoryReader;
import org.faketri.reader.AbstractFileReader;
import org.faketri.reader.impl.DefaultDataReader;
import org.faketri.reader.impl.DirectoryReader;


public class Main {
    private static final Logger log = BaseLoggerFactory.getLogger(Main.class);

    private static final DataParserFactory bs = new DataParserFactory();

    static {
        GlobalProxyHandler.enableProfiling();
        log.setLogLevel(LoggerLevel.TRACE);

        bs.register(new MavenDataParser());
    }

    static void main() {

        AbstractFileReader proxyReader = GlobalProxyHandler.newProxy(new DefaultDataReader(), AbstractFileReader.class);
        AbstractDirectoryReader proxyDirReader = GlobalProxyHandler.newProxy(new DirectoryReader(), AbstractDirectoryReader.class);

        CoreFinderDependency cfd = new CoreFinderDependency("/Users/vilkov/projects/dependency-finder",
                proxyReader,
                proxyDirReader,
                bs
        );
        Modules pr =  cfd.getAll();

        log.info("{}", pr);
        for (Dependency dep : pr.getDeps())
            log.info("{}", dep);

    }
}
