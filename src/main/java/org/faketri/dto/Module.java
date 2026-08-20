package org.faketri.dto;

import java.util.List;

public class Module {

    private String name;
    private List<Dependency> deps;
    private BuildSystem buildSystem;

    public Module(String name, List<Dependency> deps) {
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
}
