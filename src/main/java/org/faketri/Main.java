package org.faketri;

import org.faketri.core.CoreFinderDependency;
import org.faketri.core.DataParserFactory;
import org.faketri.dto.Modules;
import org.faketri.dto.os.OS;
import org.faketri.provider.Provider;
import org.faketri.provider.impl.BrewProvider;
import org.faketri.provider.impl.DNFProvider;
import org.faketri.provider.impl.DefaultProvidersFactory;
import org.faketri.proxy.GlobalProxyHandler;
import org.faketri.logger.BaseLoggerFactory;
import org.faketri.logger.Logger;
import org.faketri.logger.LoggerLevel;
import org.faketri.parser.impl.maven.MavenDataParser;
import org.faketri.reader.AbstractDirectoryReader;
import org.faketri.reader.AbstractFileReader;
import org.faketri.reader.impl.DefaultDataReader;
import org.faketri.reader.impl.DirectoryReader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
        providers.register(new BrewProvider());
        log.info(os.toString());
    }

    static void main(String[] args) {

        if (args.length == 0) throw new IllegalArgumentException("First argument is missing");

        AbstractFileReader proxyReader = GlobalProxyHandler.newProxy(new DefaultDataReader(), AbstractFileReader.class);
        AbstractDirectoryReader proxyDirReader = GlobalProxyHandler.newProxy(new DirectoryReader(), AbstractDirectoryReader.class);

        CoreFinderDependency cfd = new CoreFinderDependency(args[0], proxyReader, proxyDirReader, bs);
        Modules pr =  cfd.getAll();

        log.info("{}", pr);

        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        Provider provider = providers.getProvider(os.getPackageManager().getName());

        pr.getDeps().forEach(dep ->{
            executor.submit(() -> provider.existInSystem(dep));
            log.info("{}", dep);
        });

        executor.close();
    }
}
