/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Lecture {

    @Id
    @GeneratedValue
    private int lectureId;

    @OneToMany(targetEntity = Section.class, mappedBy = "lecture", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Section> sections = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    @ElementCollection
    private List<String> days = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "LECTURE_START")
    private Time start;

    @ManyToOne
    @JoinColumn(name = "LECTURE_END")
    private Time end;

    public int getLectureId() { return lectureId; }
    public void setLectureId(int lectureId) { this.lectureId = lectureId; }
    public List<Section> getSections() { return sections; }
    public void setSections(List<Section> sections) { this.sections = sections; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public List<String> getDays() { return days; }
    public void setDays(List<String> days) { this.days = days; }
    public Time getStart() { return start; }
    public void setStart(Time start) { this.start = start; }
    public Time getEnd() { return end; }
    public void setEnd(Time end) { this.end = end; }
}
