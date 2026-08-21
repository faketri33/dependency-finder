package org.faketri.reader.impl;


import org.faketri.reader.AbstractDirectoryReader;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DirectoryReader implements AbstractDirectoryReader {

    @Override
    public List<File> read(String path) {
        if (path == null || path.isEmpty()) throw new IllegalArgumentException("Path cannot be null or empty");
        File file = new File(path);
        File[] fileArray = Objects.requireNonNull(file.listFiles());
        return Arrays.asList(fileArray);
    }

}
