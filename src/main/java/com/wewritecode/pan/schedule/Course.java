/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import java.util.ArrayList;
import java.util.List;

public class Course implements ICourse<ILecture> {
    private String courseId;
    private List<ILecture> lectures;

    public Course() {
        courseId = "";
        lectures = new ArrayList<>();
    }

    public Course(String courseId) {
        this.courseId = courseId;
        lectures = new ArrayList<>();
    }

    public String getCourseID() { return courseId; }
    public void setCourseID(String courseId) { this.courseId = courseId; }

    @Override
    public List<ILecture> getLectures() { return lectures; }
    @Override
    public void setLectures(List<ILecture> lectures) { this.lectures = lectures; }
    @Override
    public void addLecture(ILecture lecture) { lectures.add(lecture); }
}
