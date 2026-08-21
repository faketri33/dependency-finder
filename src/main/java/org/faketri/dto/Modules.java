package org.faketri.dto;

import java.util.List;

public class Modules {

    private String name;
    private List<Modules> modules;
    private List<Dependency> deps;
    private Version version;
    private BuildSystem buildSystem;

    public Modules(Version version, String name) {
        this.version = version;
        this.name = name;
    }

    public Modules(String name, List<Dependency> deps) {
        this.name = name;
        this.deps = deps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dependency> getDeps() {
        return deps;
    }

    public void setDeps(List<Dependency> deps) {
        this.deps = deps;
    }

    public BuildSystem getBuildSystem() {
        return buildSystem;
    }

    public void setBuildSystem(BuildSystem buildSystem) {
        this.buildSystem = buildSystem;
    }

    public List<Modules> getModules() {
        return modules;
    }

    public void setModules(List<Modules> modules) {
        this.modules = modules;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Modules{" +
                "name='" + name + '\'' +
                ", buildSystem=" + buildSystem +
                ", version=" + version +
                '}';
    }
}
