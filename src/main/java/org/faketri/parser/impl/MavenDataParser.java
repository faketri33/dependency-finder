package org.faketri.parser.impl;

import org.faketri.core.BuildSystemConstants;
import org.faketri.dto.BuildSystem;
import org.faketri.dto.Project;
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
import java.util.Objects;
import java.util.Stack;

public class MavenDataParser implements AbstractDataParser {

    private static final Logger log = BaseLoggerFactory.getLogger(MavenDataParser.class);

    @Override
    public int canParse(BuildSystem buildSystem) {
        return Objects.equals(buildSystem.getName(), BuildSystemConstants.MAVEN.name()) ? 100 : 0;
    }

    @Override
    public Project parse(ByteArrayInputStream data) {
        Objects.requireNonNull(data);
        Project project = null;
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

            SAXParser saxParser = factory.newSAXParser();

            PomHandler handler = new PomHandler();
            saxParser.parse(data, handler);

            project = new Project(handler.name, null, new Version(handler.version));
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

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            elementStack.push(qName);
            elementValue.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            elementValue.append(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if (!elementStack.isEmpty()) {
                elementStack.pop();
            }

            if (elementStack.size() == 1 && "project".equals(elementStack.peek())) {
                String value = elementValue.toString().trim();
                if ("artifactId".equals(qName)) {
                    name = value;
                } else if ("version".equals(qName)) {
                    version = value;
                }
            }
        }

        public String getName() { return name; }
        public String getVersion() { return version; }
    }

}
