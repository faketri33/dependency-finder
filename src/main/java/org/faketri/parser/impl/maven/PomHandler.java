package org.faketri.parser.impl.maven;

import org.faketri.dto.Dependency;
import org.faketri.dto.Version;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

class PomHandler extends DefaultHandler {
    private final StringBuilder elementValue = new StringBuilder();
    private final StringBuilder deepsFullName = new StringBuilder();
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
        elementValue.append(ch, start, length);
        if (version.isEmpty() && Objects.equals(elementStack.peek(), "version"))
            version = elementValue.toString().trim();
        if (Objects.equals(elementStack.peek(), "groupId") && !name.isEmpty())
            deepsFullName.append(elementValue.toString().trim());
        if (Objects.equals(elementStack.peek(), "artifactId")) {
            if (name.isEmpty()) name = elementValue.toString().trim();
            else {
                depends.add(new Dependency(deepsFullName.append(":")
                        .append(elementValue.toString().trim()).toString(), null, "Dependency"));
                deepsFullName.setLength(0);
            }
        }
        if (Objects.equals(elementStack.peek(), "version") && !depends.isEmpty())
            depends.getLast().setVersion(new Version(elementValue.toString().trim()));

    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        elementStack.pop();
    }

    public String getName() { return name; }
    public String getVersion() { return version; }
    public List<Dependency> getDepends() { return depends; }
}
