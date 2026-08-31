package org.faketri.core;

import org.faketri.provider.Provider;

public class Application {

    private final ApplicationContext context;
    private final String path;

    public Application(ApplicationContext context, String path) {
        this.context = context;
        this.path = path;
    }

    public Provider getSystemProvider(){
        return context.getSystemProvider();
    }
}
