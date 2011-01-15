package com.ttime.parse;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ttime.logic.Faculty;

public class UDonkey implements Parser {
    HashSet<Faculty> faculties;

    public UDonkey(File file) throws ParserConfigurationException,
            SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        Element dbElements = doc.getDocumentElement();
        // get faculty elements and start parsing
        NodeList facultyNodes = dbElements.getElementsByTagName("Faculty");
        faculties = parseFaculties(facultyNodes);
    }

    @Override
    public Set<Faculty> getFaculties() {
        return faculties;
    }

    private HashSet<Faculty> parseFaculties(NodeList facultyNodes) {
        HashSet<Faculty> faculties = new HashSet<Faculty>();
        if (facultyNodes != null) {
            for (int i = 0; i < facultyNodes.getLength(); i++) {
                Element el = (Element) facultyNodes.item(i);

            }
        }
        return faculties;
    }
}
