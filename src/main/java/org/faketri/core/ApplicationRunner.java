package org.faketri.core;

import com.sun.jdi.ClassNotLoadedException;

public class ApplicationRunner {
    private ApplicationRunner() throws ClassNotLoadedException {
        throw new ClassNotLoadedException("It's utility class");
    }

    public static Application run(String path){
      return new Application(ApplicationContext.run(), path);
    }
}
