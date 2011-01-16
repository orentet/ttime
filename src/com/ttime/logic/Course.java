package com.ttime.logic;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.ttime.logic.Group.Type;

public class Course implements Comparable<Course> {
	String firstTestDate; // TODO needs to be represented as date
	Set<Group> groups = new HashSet<Group>();
	private HashMap<Type, LinkedList<Group>> groupsByType = null;
	int labHours;
	int lectureHours;
	String lecturerInCharge;
	String name;
	int number;
	float points;
	int projectHours;
	String secondTestDate; // TODO needs to be represented as date
	int tutorialHours;

	public Course(int number, String name, float points) {
		this.number = number;
		this.name = name;
		this.points = points;
	}

	private void addPartialSchedulingOptions(Schedule subSchedule,
			LinkedList<Schedule> results, List<Group.Type> types) {
		if (types.isEmpty()) {
			results.add(subSchedule);
			return;
		}

		Group.Type currentType = types.get(0);
		List<Group.Type> remainingTypes = types.subList(1, types.size());

		for (Group g : getGroupsByType(currentType)) {
			Schedule amendedSchedule = (Schedule) subSchedule.clone();
			amendedSchedule.addAll(g.getEvents());
			addPartialSchedulingOptions(amendedSchedule, results,
					remainingTypes);
		}
	}

	@Override
	public int compareTo(Course rhs) {
		return new Integer(number).compareTo(rhs.getNumber());
	}

	public String getFirstTestDate() {
		return firstTestDate;
	}

	public Set<Group> getGroups() {
		return this.groups;
	}

	private HashMap<Type, LinkedList<Group>> getGroupsByType() {
		if (groupsByType != null) {
			return groupsByType;
		}

		groupsByType = new HashMap<Group.Type, LinkedList<Group>>();
		for (Group g : this.groups) {
			if (!groupsByType.containsKey(g.getType())) {
				groupsByType.put(g.getType(), new LinkedList<Group>());
			}
			groupsByType.get(g.getType()).add(g);
		}

		return groupsByType;
	}

	public Collection<Group> getGroupsByType(Group.Type t) {
		HashSet<Group> result = new HashSet<Group>();
		for (Group g : groups) {
			if (g.getType() == t) {
				result.add(g);
			}
		}
		return result;
	}

	public String getHtmlInfo() {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("<h1>[%d] %s</h1>", number, name));

		String[][] items = { { String.valueOf(points), "נקודות אקדמיות" },
				{ lecturerInCharge, "מרצה אחראי" },
				{ firstTestDate, "מועד א'" }, { secondTestDate, "מועד ב'" } };

		for (String[] pair : items) {
			if (pair[0] != null) {
				sb
						.append(String.format("<div><b>%s:</b> %s", pair[1],
								pair[0]));
			}
		}

		// TODO Add group and event details

		return sb.toString();
	}

	public int getLabHours() {
		return labHours;
	}

	public int getLectureHours() {
		return lectureHours;
	}

	public String getLecturerInCharge() {
		return lecturerInCharge;
	}

	public String getName() {
		return this.name;
	}

	public int getNumber() {
		return this.number;
	}

	public float getPoints() {
		return points;
	}

	public int getProjectHours() {
		return projectHours;
	}

	public List<Schedule> getSchedulingOptions() {
		LinkedList<Schedule> schedulingOptions = new LinkedList<Schedule>();
		addPartialSchedulingOptions(new Schedule(), schedulingOptions,
				new LinkedList<Group.Type>(getGroupsByType().keySet()));
		return schedulingOptions;
	}

	public String getSecondTestDate() {
		return secondTestDate;
	}

	public int getTutorialHours() {
		return tutorialHours;
	}

	public void setFirstTestDate(String firstTestDate) {
		this.firstTestDate = firstTestDate;
	}

	public void setLabHours(int labHours) {
		this.labHours = labHours;
	}

	public void setLectureHours(int lectureHours) {
		this.lectureHours = lectureHours;
	}

	public void setLecturerInCharge(String lecturerInCharge) {
		this.lecturerInCharge = lecturerInCharge;
	}

	public void setProjectHours(int projectHours) {
		this.projectHours = projectHours;
	}

	public void setSecondTestDate(String secondTestDate) {
		this.secondTestDate = secondTestDate;
	}

	public void setTutorialHours(int tutorialHours) {
		this.tutorialHours = tutorialHours;
	}

	@Override
	public String toString() {
		return String.format("%d %s", this.number, this.name);
	}
}