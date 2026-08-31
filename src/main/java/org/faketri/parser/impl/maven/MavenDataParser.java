package org.faketri.parser.impl.maven;

import org.faketri.core.BuildSystemConstants;
import org.faketri.dto.BuildSystem;
import org.faketri.dto.Dependency;
import org.faketri.dto.Modules;
import org.faketri.dto.Version;
import org.faketri.logger.BaseLoggerFactory;
import org.faketri.logger.Logger;
import org.faketri.parser.AbstractDataParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

public class MavenDataParser implements AbstractDataParser {

    private enum MavenTags {
        GROUP_ID("groupId"),
        ARTEFACT_ID("artifactId"),
        VERSION("version");

        private final String name;

        MavenTags(String name) {
            this.name = name;
        }
    }

    private static final Logger log = BaseLoggerFactory.getLogger(MavenDataParser.class);

    private String name = "";
    private String version = "";
    private final List<Dependency> depends = new ArrayList<>();
    private final List<Modules> modules = new ArrayList<>();

    private final XPath xpath = XPathFactory.newInstance().newXPath();

    @Override
    public int canParse(BuildSystem buildSystem) {
        return Objects.equals(buildSystem.getName(), BuildSystemConstants.MAVEN.name()) ? 100 : 0;
    }

    @Override
    public Modules parse(ByteArrayInputStream data) {
        try {
            parsePom(data);
            return new Modules(name, depends, modules, new Version(version), new BuildSystem("MAVEN", ""));
        } catch (Exception ex){
            log.error(ex.getMessage());
        }
        return null;
    }

    private void parsePom(ByteArrayInputStream pomFile) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(pomFile);
        doc.getDocumentElement().normalize();

        name = xpathText("/project/artifactId", doc);
        version = xpathText("/project/version", doc);
        if (version.isEmpty()) {
            version = xpathText("/project/parent/version", doc);
        }

        collectParent(doc);
        collectModules(doc);
        collectFromNodeList("/project/dependencies/dependency", doc, "Dependency");
        collectFromNodeList("/project/dependencyManagement/dependencies/dependency", doc, "DependencyManagement");
        collectPlugins("/project/build/plugins/plugin", doc, "Plugin");
        collectPlugins("/project/build/pluginManagement/plugins/plugin", doc, "PluginManagement");

        collectProfiles(doc);
    }

    private void collectParent(Document doc) throws XPathExpressionException {
        NodeList parentNodes = (NodeList) xpath.evaluate("/project/parent", doc, XPathConstants.NODESET);
        if (parentNodes.getLength() == 0) return;

        Element parent = (Element) parentNodes.item(0);
        String groupId = childText(parent, MavenTags.GROUP_ID.name);
        String artifactId = childText(parent, MavenTags.ARTEFACT_ID.name);
        String parentVersion = childText(parent, MavenTags.VERSION.name);

        depends.add(new Dependency(groupId + ":" + artifactId,
                parentVersion.isEmpty() ? null : new Version(parentVersion), "Parent"));
    }

    private void collectModules(Document doc) throws XPathExpressionException {
        NodeList parentNodes = (NodeList) xpath.evaluate("/project/modules", doc, XPathConstants.NODESET);
        if (parentNodes.getLength() == 0) return;

        for (int i = 0; i < parentNodes.getLength(); i++){
            Element parent = (Element) parentNodes.item(i);
            String groupId = childText(parent, "module");
            log.debug("Module name {}", groupId);
            modules.add(new Modules(groupId));
        }
    }

    private void collectFromNodeList(String expression, Document doc, String type) throws XPathExpressionException {
        NodeList nodes = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            addDependency((Element) nodes.item(i), type);
        }
    }

    private void collectPlugins(String expression, Document doc, String type) throws XPathExpressionException {
        NodeList nodes = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            Element plugin = (Element) nodes.item(i);
            String groupId = childText(plugin, MavenTags.GROUP_ID.name);
            if (groupId.isEmpty()) groupId = "org.apache.maven.plugins";
            String artifactId = childText(plugin, MavenTags.ARTEFACT_ID.name);
            String pluginVersion = childText(plugin, MavenTags.VERSION.name);

            depends.add(new Dependency(groupId + ":" + artifactId,
                    pluginVersion.isEmpty() ? null : new Version(pluginVersion), type));
        }
    }

    private void collectProfiles(Document doc) throws XPathExpressionException {
        NodeList profiles = (NodeList) xpath.evaluate("/project/profiles/profile", doc, XPathConstants.NODESET);
        for (int i = 0; i < profiles.getLength(); i++) {
            Element profile = (Element) profiles.item(i);
            String profileId = childText(profile, "id");
            String tag = profileId.isEmpty() ? "Profile" : "Profile:" + profileId;

            NodeList profileDeps = (NodeList) xpath.evaluate("dependencies/dependency", profile, XPathConstants.NODESET);
            for (int j = 0; j < profileDeps.getLength(); j++) {
                addDependency((Element) profileDeps.item(j), tag + "Dependency");
            }

            NodeList profilePlugins = (NodeList) xpath.evaluate("build/plugins/plugin", profile, XPathConstants.NODESET);
            for (int j = 0; j < profilePlugins.getLength(); j++) {
                Element plugin = (Element) profilePlugins.item(j);
                String groupId = childText(plugin, MavenTags.GROUP_ID.name);
                if (groupId.isEmpty()) groupId = "org.apache.maven.plugins";
                String artifactId = childText(plugin, MavenTags.ARTEFACT_ID.name);
                String pluginVersion = childText(plugin, MavenTags.VERSION.name);

                depends.add(new Dependency(groupId + ":" + artifactId,
                        pluginVersion.isEmpty() ? null : new Version(pluginVersion), tag + "Plugin"));
            }
        }
    }

    private void addDependency(Element dep, String type) {
        String groupId = childText(dep, MavenTags.GROUP_ID.name);
        String artifactId = childText(dep, MavenTags.ARTEFACT_ID.name);
        String depVersion = childText(dep, MavenTags.VERSION.name);

        depends.add(new Dependency(groupId + ":" + artifactId,
                depVersion.isEmpty() ? null : new Version(depVersion), type));
    }

    private String xpathText(String expression, Document doc) throws XPathExpressionException {
        String value = (String) xpath.evaluate(expression, doc, XPathConstants.STRING);
        return value == null ? "" : value.trim();
    }

    private static String childText(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) return "";
        return nodes.item(0).getTextContent().trim();
    }

    public String getName() { return name; }
    public String getVersion() { return version; }
    public List<Dependency> getDepends() { return depends; }
}
