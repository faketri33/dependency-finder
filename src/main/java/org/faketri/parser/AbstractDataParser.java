package org.faketri.parser;

import org.faketri.dto.BuildSystem;
import org.faketri.dto.Modules;

import java.io.ByteArrayInputStream;

public interface AbstractDataParser {

    int canParse(BuildSystem buildSystem);
    Modules parse(ByteArrayInputStream data);
}
