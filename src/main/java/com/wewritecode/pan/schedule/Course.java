/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import java.util.ArrayList;
import java.util.List;

public class Course implements ICourse<Lecture> {
    private String courseID;
    private List<Lecture> lectures;

    public Course() {
        courseID = "";
        lectures = new ArrayList<>();
    }

    public Course(String courseId) {
        this.courseID = courseId;
        lectures = new ArrayList<>();
    }

    public Course(Course c, Lecture lecture, Section section) {
        lectures = new ArrayList<>();
        Lecture courseLecture = new Lecture(lecture, section);
        this.courseID = c.getCourseID();
        this.lectures.add(courseLecture);
    }

    @Override
    public String getCourseID() { return courseID; }
    public void setCourseID(String courseId) { this.courseID = courseId; }

    public int getNumLectures() { return lectures.size(); }
    public Lecture getLecture(int index) { return lectures.get(index); }

    @Override
    public List<Lecture> getLectures() { return lectures; }
    @Override
    public void setLectures(List<Lecture> lectures) { this.lectures = lectures; }
    @Override
    public void addLecture(Lecture lecture) { lectures.add(lecture); }

    public boolean equals(Course c) {
        return this.courseID.equalsIgnoreCase(c.courseID);
    }
}
