package org.faketri.dto.os;


public class OS {
    private final String name;
    private final PackageManager manager;
    private String version;

    public OS(String name) {
        this.name = name;
        manager = new PackageManager(name);
    }

    public String getName() {
        return name;
    }

    public PackageManager getPackageManager() {
        return manager;
    }

    @Override
    public String toString() {
        return name + " " + manager.getName() + " " + version;
    }
}
