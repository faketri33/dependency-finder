package org.faketri.core;

import org.faketri.dto.os.OS;
import org.faketri.dto.os.PackageManager;
import org.faketri.logger.BaseLoggerFactory;
import org.faketri.logger.LoggerConfiguration;
import org.faketri.logger.LoggerLevel;
import org.faketri.logger.formatter.DefaultFormatter;
import org.faketri.proxy.GlobalProxyHandler;

public class ApplicationConfigure {

    private final OS os = new OS(System.getProperty("os.name"));


    public void defaultConfigurationLoad(){
        BaseLoggerFactory.setConfiguration(new LoggerConfiguration(LoggerLevel.DEBUG, new DefaultFormatter()));
        GlobalProxyHandler.enableProfiling();
    }

    public PackageManager getSystemPackageManager() {
        return os.getPackageManager();
    }
}
