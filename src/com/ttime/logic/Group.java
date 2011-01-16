package com.ttime.logic;

import java.util.HashSet;
import java.util.Set;

public class Group {
	public enum Type {
		LAB, LECTURE, OTHER, SPORTS, TUTORIAL
	}

	Course course;
	Set<Event> events;
	String lecturer = null;
	int number;
	/**
	 * Title of sports group
	 *
	 * This is only really relevant if this.type = Type.SPORTS.
	 */
	String title = null;
	Type type;

	public Group(Course course, int number, Type type, String lecturer) {
		this.course = course;
		this.number = number;
		this.type = type;
		this.events = new HashSet<Event>();
	}

	public Set<Event> getEvents() {
		return this.events;
	}

	public String getLecturer() {
		return lecturer;
	}

	public String getTitle() {
		return title;
	}

	public Type getType() {
		return type;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return String.format("<Group number=%d type=%s lecturer=%s title=%s>",
				number, type, lecturer, title);
	}

	public Course getCourse() {
		return course;
	}
}
