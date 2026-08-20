package org.faketri.reader.impl;

import org.faketri.logger.BaseLoggerFactory;
import org.faketri.logger.Logger;
import org.faketri.reader.AbstractFileReader;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DefaultDataReader implements AbstractFileReader {
    private static final Logger log = BaseLoggerFactory.getLogger(DefaultDataReader.class);

    @Override
    public ByteArrayInputStream read(String path) {
        File file = new File(path);
        ByteArrayInputStream bf;
        try (FileInputStream fos = new FileInputStream(file)) {
            //log.info(new String(fos.readAllBytes(), StandardCharsets.UTF_8));
            bf = new ByteArrayInputStream(fos.readAllBytes());
            log.info(bf.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bf;
    }

}
