package org.faketri.reader;

import java.io.ByteArrayInputStream;

public interface AbstractFileReader {

    ByteArrayInputStream read(String path);
}
