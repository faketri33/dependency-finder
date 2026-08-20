package org.faketri;

import org.faketri.core.CoreFinderDependency;
import org.faketri.core.DataParserFactory;
import org.faketri.dto.Project;
import org.faketri.logger.BaseLoggerFactory;
import org.faketri.logger.Logger;
import org.faketri.parser.impl.MavenDataParser;
import org.faketri.reader.impl.DefaultDataReader;
import org.faketri.reader.impl.DirectoryReader;

public class Main {
    private static final Logger log = BaseLoggerFactory.getLogger(Main.class);

    private static final DataParserFactory bs = new DataParserFactory();

    static {
        bs.register(new MavenDataParser());
    }


    static void main() {
        CoreFinderDependency cfd = new CoreFinderDependency(new DefaultDataReader(), new DirectoryReader(), bs);
        Project pr =  cfd.getAll("/Users/vilkov/projects/dependency-finder/");

        log.info("Project name {}", pr.getName());
        log.info("Projects version {}", pr.getVersion().toString());
    }
}
