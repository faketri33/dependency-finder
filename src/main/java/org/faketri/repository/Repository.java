package org.faketri.repository;

import org.faketri.dto.Modules;

import java.util.Collection;
import java.util.Optional;

public interface Repository {

    Collection<Modules> get();
    Optional<Modules> findByName(String name);

    void save(Modules modules);
}
