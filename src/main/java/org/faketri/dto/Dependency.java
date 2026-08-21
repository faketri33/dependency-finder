package org.faketri.dto;

public class Dependency {

    private String name;
    private Version version;
    private String type;

    public Dependency(String name) {
        this.name = name;
    }

    public Dependency(String name, Version version, String type) {
        this.name = name;
        this.version = version;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
