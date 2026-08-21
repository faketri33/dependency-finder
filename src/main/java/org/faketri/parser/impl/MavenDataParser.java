package org.faketri.parser.impl;

import org.faketri.core.BuildSystemConstants;
import org.faketri.dto.BuildSystem;
import org.faketri.dto.Dependency;
import org.faketri.dto.Modules;
import org.faketri.dto.Version;
import org.faketri.logger.BaseLoggerFactory;
import org.faketri.logger.Logger;
import org.faketri.parser.AbstractDataParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

public class MavenDataParser implements AbstractDataParser {

    private static final Logger log = BaseLoggerFactory.getLogger(MavenDataParser.class);

    @Override
    public int canParse(BuildSystem buildSystem) {
        return Objects.equals(buildSystem.getName(), BuildSystemConstants.MAVEN.name()) ? 100 : 0;
    }

    @Override
    public Modules parse(ByteArrayInputStream data) {
        Objects.requireNonNull(data);
        Modules project = null;
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

            SAXParser saxParser = factory.newSAXParser();

            PomHandler handler = new PomHandler();
            saxParser.parse(data, handler);

            project = new Modules(new Version(handler.version), handler.name);
            project.setDeps(handler.getDepends());
        }catch (ParserConfigurationException | SAXException ex){
            log.error(ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return project;
    }

    private static class PomHandler extends DefaultHandler {
        private final StringBuilder elementValue = new StringBuilder();
        private final Stack<String> elementStack = new Stack<>();

        private String name = "";
        private String version = "";
        private final List<Dependency> depends = new ArrayList<>();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            elementStack.push(qName);
            elementValue.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            String string = new String(ch, start, length).trim();
            if (version.isEmpty() && Objects.equals(elementStack.peek(), "version"))
                version = string;
            if (Objects.equals(elementStack.peek(), "artifactId")) {
                if (name.isEmpty()) name = string;
                else depends.add(new Dependency(string));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            elementStack.pop();
        }

        public String getName() { return name; }
        public String getVersion() { return version; }
        public List<Dependency> getDepends() { return depends; }
    }

}
