/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseID;
    private List<Lecture> lectures;

    public Course(Course c, Lecture lecture, Section section) {
        lectures = new ArrayList<>();
        Lecture courseLecture = new Lecture(lecture, section);
        this.courseID = c.courseID;
        this.lectures.add(courseLecture);

    }

    public Course(String courseID) {
        lectures = new ArrayList<>();
        this.courseID = courseID;
    }

    public int getNumLectures() { return lectures.size(); }

    public Lecture getLecture(int index) { return lectures.get(index); }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void addLecture(Lecture lecture) {
        lectures.add(lecture);
    }

    public String getCourseID() {
        return courseID;
    }

    public boolean equals(Course c) {
        return this.courseID.equalsIgnoreCase(c.courseID);
    }
}
