package org.faketri.dto;

import java.util.Collections;
import java.util.Set;

public class Project {

    private String name;
    private Set<Module> modules;
    private Version version;
    private BuildSystem buildSystem;

    public Project() {
    }

    public Project(String name, Set<Module> modules, Version version) {
        this.name = name;
        this.modules = modules;
        this.version = version;
    }


    public void addModule(Module mod){
        modules.add(mod);
    }

    public void removeModule(Module mod){
        modules.remove(mod);
    }

    public void removeByName(String name){
        modules.removeIf(mod -> mod.getName().equals(name));
    }

    public String getName() {
        return name;
    }

    public Set<Module> getModules() {
        return Collections.unmodifiableSet(modules);
    }

    public Version getVersion() {
        return version;
    }

    public BuildSystem getBuildSystem() {
        return buildSystem;
    }

    public void setBuildSystem(BuildSystem buildSystem) {
        this.buildSystem = buildSystem;
    }


}
