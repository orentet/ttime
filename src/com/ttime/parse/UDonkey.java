package com.ttime.parse;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ttime.logic.Course;
import com.ttime.logic.Event;
import com.ttime.logic.Faculty;
import com.ttime.logic.Group;

public class UDonkey implements Parser {
	HashSet<Faculty> faculties;

	public UDonkey(File file) throws ParserConfigurationException,
			SAXException, IOException, ParseException {
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

	private Set<Course> parseCourses(NodeList courseNodes, Faculty faculty)
			throws ParseException {
		HashSet<Course> courses = new HashSet<Course>();

		if (courseNodes != null) {
			for (int i = 0; i < courseNodes.getLength(); i++) {
				Element el = (Element) courseNodes.item(i);
				int courseNumber = Integer.parseInt(el.getAttribute("number"));
				String courseName = el.getAttribute("name");
				if (courseName == null) {
					throw new ParseException(
							"no name attribute for Course node", 0);
				}
				float academicPoints = Float.parseFloat(el
						.getAttribute("courseAcademicPoints"));
				Course course = new Course(faculty, courseNumber, courseName,
						academicPoints);

				// optional components
				course.getGroups().addAll(
						parseGroups(el.getElementsByTagName("CourseEvent"),
								course));
				String lecturerInCharge = el.getAttribute("lecturerInCharge");
				if (lecturerInCharge != null) {
					course.setLecturerInCharge(lecturerInCharge);
				}
				String moedADate = el.getAttribute("moedADate");
				if (moedADate != null) {
					course.setFirstTestDate(moedADate);
				}
				String moedBDate = el.getAttribute("moedBDate");
				if (moedBDate != null) {
					course.setSecondTestDate(moedBDate);
				}
				String lectureHours = el.getAttribute("lectureHours");
				if (lectureHours != null) {
					course.setLectureHours(Integer.parseInt(lectureHours));
				}
				String tutorialHours = el.getAttribute("tutorialHours");
				if (tutorialHours != null) {
					course.setTutorialHours(Integer.parseInt(tutorialHours));
				}
				String labHours = el.getAttribute("labHours");
				if (labHours != null) {
					course.setLabHours(Integer.parseInt(labHours));
				}
				String projectHours = el.getAttribute("projectHours");
				if (projectHours != null) {
					course.setProjectHours(Integer.parseInt(projectHours));
				}

				courses.add(course);
			}
		}

		return courses;
	}

	private Set<Event> parseEvents(NodeList placeTimeNodes, Group group)
			throws ParseException {
		HashSet<Event> events = new HashSet<Event>();
		if (placeTimeNodes != null) {
			for (int i = 0; i < placeTimeNodes.getLength(); i++) {
				Element el = (Element) placeTimeNodes.item(i);

				String eventDay = el.getAttribute("EventDay");
				if (eventDay == null) {
					throw new ParseException(
							"no EventDay attribute for PlaceTime node", 0);
				}
				int day = ParserUtil.dayLetterToNumber(eventDay.charAt(0));

				String eventTime = el.getAttribute("EventTime");
				if (eventTime == null) {
					throw new ParseException(
							"no EventTime attribute for PlaceTime node", 0);
				}
				int startTime = ParserUtil.parseTime(eventTime);

				String eventDuration = el.getAttribute("EventDuration");
				if (eventDuration == null) {
					throw new ParseException(
							"no EventDuration attribute for PlaceTime node", 0);
				}
				int endTime = startTime + ParserUtil.parseTime(eventDuration);

				String eventLocation = el.getAttribute("EventLocation");
				if (eventLocation == null) {
					throw new ParseException(
							"no EventLocation attribute for PlaceTime node", 0);
				}
				Event event = new Event(group, day, startTime, endTime,
						eventLocation);
				events.add(event);
			}
		}
		return events;
	}

	private HashSet<Faculty> parseFaculties(NodeList facultyNodes)
			throws ParseException {
		HashSet<Faculty> faculties = new HashSet<Faculty>();
		if (facultyNodes != null) {
			for (int i = 0; i < facultyNodes.getLength(); i++) {
				Element el = (Element) facultyNodes.item(i);

				String name = el.getAttribute("name");
				if (name == null) {
					throw new ParseException(
							"no name attribute for Faculty node", 0);
				}

				Faculty faculty = new Faculty(name, null);

				// optional components
				faculty.getCourses()
						.addAll(
								parseCourses(el.getElementsByTagName("Course"),
										faculty));

				faculties.add(faculty);
			}
		}
		return faculties;
	}

	private Set<Group> parseGroups(NodeList courseEventsNodes, Course course)
			throws ParseException {
		HashSet<Group> groups = new HashSet<Group>();
		if (courseEventsNodes != null) {
			for (int i = 0; i < courseEventsNodes.getLength(); i++) {
				Element el = (Element) courseEventsNodes.item(i);

				int number = Integer.parseInt(el.getAttribute("regNumber"));
				String lecturer = el.getAttribute("teacher");
				if (lecturer == null) {
					throw new ParseException(
							"no teacher attribute for CourseEvent node", 0);
				}
				String eventType = el.getAttribute("eventType");
				Group.Type type;
				if (eventType == null) {
					throw new ParseException(
							"no event type attribute for group", 0);
				} else if (eventType.equals("מעבדה")) {
					type = Group.Type.LAB;
				} else if (eventType.equals("הרצאה")) {
					type = Group.Type.LECTURE;
				} else if (eventType.equals("חנ'ג+כושר גופני")) {
					type = Group.Type.SPORTS;
				} else if (eventType.equals("תרגיל")) {
					type = Group.Type.TUTORIAL;
				} else {
					type = Group.Type.OTHER;
				}

				Group group = new Group(course, number, type, lecturer);

				// optional components
				group.getEvents()
						.addAll(
								parseEvents(el
										.getElementsByTagName("PlaceTime"),
										group));

				groups.add(group);
			}
		}
		return groups;
	}
}
