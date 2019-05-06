/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {

    @Id
    private String courseId;
    @OneToMany(targetEntity = Lecture.class, mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Lecture> lectures = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;

    public String getCourseID() { return courseId; }
    public void setCourseID(String courseId) { this.courseId = courseId; }
    public List<Lecture> getLectures() { return lectures; }
    public void setLectures(List<Lecture> lectures) { this.lectures = lectures; }
    public Schedule getSchedule() { return schedule; }
    public void setSchedule(Schedule schedule) { this.schedule = schedule; }
}
