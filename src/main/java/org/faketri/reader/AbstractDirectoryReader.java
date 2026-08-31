package org.faketri.reader;

import java.io.File;
import java.util.List;

public interface AbstractDirectoryReader {
    List<File> read(String path);
    List<File> readDeep(String path, int deep);
}
