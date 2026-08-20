package org.faketri.dto;

public class Version {

    private final String strFormat;

    public Version(String version) {
        this.strFormat = version;
    }

    @Override
    public String toString() {
        return "version = " + strFormat;
    }
}
