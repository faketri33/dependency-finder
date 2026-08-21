package org.faketri.reader.impl;

import org.faketri.reader.AbstractFileReader;

import java.io.*;

public class DefaultDataReader implements AbstractFileReader {

    @Override
    public ByteArrayInputStream read(String path) {
        File file = new File(path);
        ByteArrayInputStream bf;
        try (FileInputStream fos = new FileInputStream(file)) {
            bf = new ByteArrayInputStream(fos.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bf;
    }

}
