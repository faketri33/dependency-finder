package org.faketri.provider;

import org.faketri.dto.Dependency;

public interface Provider {

    int canCheck(String systemProvider);
    boolean existInSystem(Dependency dependency);
}
