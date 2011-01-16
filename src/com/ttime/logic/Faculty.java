package com.ttime.logic;

import java.util.HashSet;
import java.util.Set;

public class Faculty implements Comparable<Faculty> {
	Set<Course> courses = new HashSet<Course>();
	String name;
	String semester;

	public Faculty(String name, String semester) {
		this.name = name;
		this.semester = semester;
	}

	@Override
	public int compareTo(Faculty o) {
		return name.compareTo(o.getName());
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public String getName() {
		return this.name;
	}

	public String getSemester() {
		return semester;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
