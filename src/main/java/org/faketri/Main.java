package org.faketri;

import org.faketri.core.CoreFinderDependency;
import org.faketri.core.DataParserFactory;
import org.faketri.dto.Dependency;
import org.faketri.dto.Modules;
import org.faketri.dto.os.OS;
import org.faketri.provider.Provider;
import org.faketri.provider.impl.DNFProvider;
import org.faketri.provider.impl.DefaultProvidersFactory;
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
    private static final DefaultProvidersFactory providers = new DefaultProvidersFactory();

    private static final OS os = new OS(System.getProperty("os.name"));

    static {
        GlobalProxyHandler.enableProfiling();
        BaseLoggerFactory.getConfiguration().setLevel(LoggerLevel.DEBUG);

        bs.register(new MavenDataParser());

        providers.register(new DNFProvider());
        log.info(os.toString());
    }

    static void main() {
        AbstractFileReader proxyReader = GlobalProxyHandler.newProxy(new DefaultDataReader(), AbstractFileReader.class);
        AbstractDirectoryReader proxyDirReader = GlobalProxyHandler.newProxy(new DirectoryReader(), AbstractDirectoryReader.class);

        CoreFinderDependency cfd = new CoreFinderDependency("/home/faketri/git/my/dependency-reader",
                proxyReader,
                proxyDirReader,
                bs
        );
        Modules pr =  cfd.getAll();

        log.info("{}", pr);

        Provider provider = GlobalProxyHandler.newProxy(providers.getProvider(os.getPackageManager().getName()), Provider.class);

        for (Dependency dep : pr.getDeps()) {
            provider.existInSystem(dep);
            log.info("{}", dep);
        }
    }
}
