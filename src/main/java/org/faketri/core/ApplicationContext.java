package org.faketri.core;


import org.faketri.provider.Provider;
import org.faketri.provider.ProvidersFactory;
import org.faketri.provider.impl.DefaultProvidersFactory;
import org.faketri.reader.AbstractDirectoryReader;
import org.faketri.reader.AbstractFileReader;
import org.faketri.reader.impl.DefaultDataReader;
import org.faketri.reader.impl.DirectoryReader;

public class ApplicationContext {

    private ApplicationConfigure configure;

    private final ProvidersFactory providersFactory;
    private final AbstractFileReader fileReader;
    private final AbstractDirectoryReader directoryReader;

    private ApplicationContext(ProvidersFactory providersFactory, AbstractFileReader fileReader, AbstractDirectoryReader directoryReader) {
        this.providersFactory = providersFactory;
        this.fileReader = fileReader;
        this.directoryReader = directoryReader;
    }

    public static ApplicationContext run(){
        ApplicationContext app = new ApplicationContext(new DefaultProvidersFactory(), new DefaultDataReader(), new DirectoryReader());
        app.configuration();
        return app;
    }

    public Provider getSystemProvider(){
        return providersFactory.getProvider(configure.getSystemPackageManager().getName());
    }

    void configuration(){
        configure = new ApplicationConfigure();
        configure.defaultConfigurationLoad();
    }

    public ProvidersFactory getProvidersFactory() {
        return providersFactory;
    }

    public AbstractFileReader getFileReader() {
        return fileReader;
    }

    public AbstractDirectoryReader getDirectoryReader() {
        return directoryReader;
    }
}
