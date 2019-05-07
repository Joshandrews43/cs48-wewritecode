/**
 * @author Alan Roddick
 * @author Grant Clark
 */

package com.wewritecode.pan.schedule;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Time implements Comparable<Time> {

    @Id
    @GeneratedValue
    private int timeId;

    private int hour;

    private int minute;

    @OneToMany(targetEntity = Section.class, mappedBy = "start", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Section> sectionStarts = new ArrayList<>();

    @OneToMany(targetEntity = Section.class, mappedBy = "end", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Section> sectionEnds = new ArrayList<>();

    @OneToMany(targetEntity = Lecture.class, mappedBy = "start", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Section> lectureStarts = new ArrayList<>();

    @OneToMany(targetEntity = Lecture.class, mappedBy = "end", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Section> lectureEnds = new ArrayList<>();

    public int getTimeId() { return timeId; }
    public void setTimeId(int timeId) { this.timeId = timeId; }
    public int getHour() { return hour; }
    public void setHour(int hour) { this.hour = hour; }
    public int getMinute() { return minute; }
    public void setMinute(int minute) { this.minute = minute; }
    public List<Section> getSectionStarts() { return sectionStarts; }
    public void setSectionStarts(List<Section> sectionStarts) { this.sectionStarts = sectionStarts; }
    public List<Section> getSectionEnds() { return sectionEnds; }
    public void setSectionEnds(List<Section> sectionEnds) { this.sectionEnds = sectionEnds; }
    public List<Section> getLectureStarts() { return lectureStarts; }
    public void setLectureStarts(List<Section> lectureStarts) { this.lectureStarts = lectureStarts; }
    public List<Section> getLectureEnds() { return lectureEnds; }
    public void setLectureEnds(List<Section> lectureEnds) { this.lectureEnds = lectureEnds; }


    @Override
    public int compareTo(Time t) {
        int thisTime = (hour * 60) + minute;
        int thatTime = (t.hour * 60) + t.minute;
        return thisTime - thatTime;
    }
}
