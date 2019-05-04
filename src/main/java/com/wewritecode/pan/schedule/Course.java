/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Course {

    @Id
    private String courseId;
    private List<Lecture> lectures;

    public String getCourseID() { return courseId; }
    public void setCourseID(String courseId) { this.courseId = courseId; }
    public List<Lecture> getLectures() { return lectures; }
    public void setLectures(List<Lecture> lectures) { this.lectures = lectures; }
}
