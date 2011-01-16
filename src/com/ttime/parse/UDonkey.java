package com.ttime.parse;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ttime.logic.Course;
import com.ttime.logic.Faculty;
import com.ttime.logic.Group;

public class UDonkey implements Parser {
	HashSet<Faculty> faculties;

	public UDonkey(File file) throws ParserConfigurationException,
			SAXException, IOException, XMLParseException {
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

	private Set<Course> parseCourses(NodeList courseNodes) {
		HashSet<Course> courses = new HashSet<Course>();

		if (courseNodes != null) {
			for (int i = 0; i < courseNodes.getLength(); i++) {
				Element el = (Element) courseNodes.item(i);
				int courseNumber = Integer.parseInt(el.getAttribute("number"));
				String courseName = el.getAttribute("name");
				float academicPoints = Float.parseFloat(el
						.getAttribute("courseAcademicPoints"));
				Course course = new Course(courseNumber, courseName,
						academicPoints);

				// optional components
				course.getGroups().addAll(
						parseGroups(el.getElementsByTagName("CourseEvent")));
				String lecturerInCharge = el.getAttribute("lecturerInCharge");
				String moedADate = el.getAttribute("moedADate");
				String moedBDate = el.getAttribute("moedBDate");
				int lectureHours = el.getAttribute("lectureHours");
				int tutorialHours = el.getAttribute("tutorialHours");
				int labHours = el.getAttribute("labHours");
				int projectHours = el.getAttribute("projectHours");

				courses.add(course);
			}
		}

		return courses;
	}

	private HashSet<Faculty> parseFaculties(NodeList facultyNodes)
			throws XMLParseException {
		HashSet<Faculty> faculties = new HashSet<Faculty>();
		if (facultyNodes != null) {
			for (int i = 0; i < facultyNodes.getLength(); i++) {
				Element el = (Element) facultyNodes.item(i);
				String name = el.getAttribute("name");

				if ((name == null) || name.isEmpty()) {
					throw new XMLParseException("no name attribute for faculty");
				}

				Faculty fac = new Faculty(name, null);

				// optional components
				fac.getCourses().addAll(parseCourses(facultyNodes));

				faculties.add(fac);
			}
		}
		return faculties;
	}

	private Set<Group> parseGroups(NodeList courseEvents) {
		HashSet<Group> groups = new HashSet<Group>();

		return groups;
	}
}
