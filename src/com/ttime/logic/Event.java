package com.ttime.logic;

import java.util.Collection;

public class Event implements Comparable<Event> {
    int day;

    Course course;

    /**
     * Stored as seconds since midnight
     */
    int startTime;

    /**
     * Stored as seconds since midnight
     */
    int endTime;
    String place;

    public Event(Course course, int day, int startTime, int endTime,
            String place) {
        this.course = course;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
    }

    public Course getCourse() {
        return this.course;
    }

    public boolean collides(Event rhs) {
        // Momentary "collisions" do not count, we always assume an implicit
        // ten-minute break at the end of the lesson. Sadly, lecturers don't
        // always give it...
        return this.day == rhs.day
                && !(this.endTime <= rhs.startTime || this.startTime >= rhs.endTime);
    }

    public boolean collides(Collection<Event> c) {
        for (Event ce : c) {
            if (collides(ce)) {
                return true;
            }
        }
        return false;
    }

    public int getDay() {
        return day;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public String getPlace() {
        return place;
    }

    @Override
    public String toString() {
        return String.format("<Event day=%d startTime~\"%02d:%02d\" endTime~\"%02d:%02d\" place=\"%s\">",
                this.day,
                this.startTime / 3600,
                (this.startTime / 60) % 60,
                this.endTime / 3600,
                (this.endTime / 60) % 60,
                this.place);
    }

    @Override
    public int compareTo(Event o) {
        if (this.day != o.day) {
            return (new Integer(this.day).compareTo(o.day));
        } else {
            return (new Integer(this.startTime).compareTo(o.startTime));
        }
    }
}