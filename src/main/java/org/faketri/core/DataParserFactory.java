package org.faketri.core;

import org.faketri.dto.BuildSystem;
import org.faketri.parser.AbstractDataParser;
import org.faketri.parser.AbstractDataParserFactory;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class DataParserFactory implements AbstractDataParserFactory {

    private final Set<AbstractDataParser> parsers = new HashSet<>();

    /**
     * Registration {@link AbstractDataParser} in system
     * */
    public void register(AbstractDataParser parser){
        parsers.add(parser);
    }

    /**
     * Find best parser by {@link AbstractDataParser#canParse(BuildSystem buildSystem)}
     *
     * @return {@link AbstractDataParser} or {@code null}
     * */
    @Override
    public AbstractDataParser getParser(BuildSystem buildSystem) {
        return parsers
                .stream()
                .max(Comparator.comparingInt(a -> a.canParse(buildSystem)))
                .orElseThrow();
    }
}
