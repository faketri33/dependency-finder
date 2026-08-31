package org.faketri.repository;

import org.faketri.dto.Modules;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

public class InMemoryRepository implements Repository{

    private final HashMap<String, Modules> modules = new HashMap<>();

    @Override
    public Collection<Modules> get() {
        return modules.values();
    }

    @Override
    public Optional<Modules> findByName(String name) {
        return Optional.of(modules.get(name));
    }

    @Override
    public void save(Modules modules) {
        this.modules.put(modules.getName(), modules);
    }
}
