package org.faketri.parser;

import org.faketri.dto.BuildSystem;
import org.faketri.dto.Project;

import java.io.ByteArrayInputStream;

public interface AbstractDataParser {

    int canParse(BuildSystem buildSystem);
    Project parse(ByteArrayInputStream data);
}
