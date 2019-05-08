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
        Lecture courseLecture = new Lecture(lecture, section);
        this.courseID = c.courseID;
        this.lectures.add(courseLecture);

    }

    public int getNumLectures() { return lectures.size(); }

    public Lecture getLecture(int index) { return lectures.get(index); }

    public List<Lecture> getLectures() {
        return lectures;
    }
}
