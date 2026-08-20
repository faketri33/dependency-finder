package org.faketri.parser;

import org.faketri.dto.BuildSystem;

public interface AbstractDataParserFactory {

    AbstractDataParser getParser(BuildSystem buildSystem);
}
